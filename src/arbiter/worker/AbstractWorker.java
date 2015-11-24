package arbiter.worker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import arbiter.BuildMaster;
import arbiter.bat.WORKTYPE;
import arbiter.task.AbstractWorkThread;
import arbiter.task.AbstractWorkingThreadResult;
import arbiter.task.CheckoutBuildDeleteTask;
import arbiter.util.NotifyingThread;
import arbiter.util.STATUS;
import arbiter.util.ThreadCompleteListener;

public abstract class AbstractWorker extends UntypedActor {

	final protected ActorRef master;

	protected final ThreadPoolExecutor executor;

	public abstract ActorRef getNext();
	
	private String getNoThreadPath(){	return BuildMaster.getPath()+"\\"+this.getWorkType();}
	
	private Integer getPoolConfig()
	{
        // Location of file to read
        File file = new File(getNoThreadPath());
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                return Integer.parseInt(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
        }
        return null;
	}
	
	private void recordPoolSize(int size)
	{
		PrintWriter writer;
		try {
			writer = new PrintWriter(getNoThreadPath(), "UTF-8");
			writer.write(size+"");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void tellNext(Object msg)
	{
		if (getNext() != null)
		{
			getNext().tell(msg);
		}
	}

	public AbstractWorker(ActorRef master) {
		this.master = master;

		executor = new ThreadPoolExecutor(1, 20, 10000L, TimeUnit.DAYS,
				new LinkedBlockingQueue<Runnable>());

		executor.setMaximumPoolSize(100);

		Integer size = getPoolConfig();

		if (size != null)
		{
			this.setPoolSize(size);
		}
		
	}

	public void setPoolSize(int no) {
		executor.setCorePoolSize(no);
		recordPoolSize(no);
	}
	
	@Override
	public void onReceive(Object msg) throws Exception {
		
		if (msg instanceof CheckoutBuildDeleteTask)
		{
			submitWork((CheckoutBuildDeleteTask) msg);
		} else if (msg instanceof ChangeThreadSize)
		{
			ChangeThreadSize cts = (ChangeThreadSize) msg;
			
			if (cts.type == this.getWorkType())
			{
				this.setPoolSize(cts.no);
			}
			else
			{
				this.tellNext(cts);
			}
		}
		else if (msg instanceof GetNumberOfThreads)
		{
			GetNumberOfThreads gnot = (GetNumberOfThreads) msg;
			if (gnot.type == this.getWorkType())
			{
				gnot.asker.tell(new GetNumberOfThreadsResult(gnot.type, this.executor.getActiveCount(), this.executor.getMaximumPoolSize(), this.executor.getCorePoolSize()));
			}
			else
			{
				this.tellNext(msg);
			}
		}
		else {
			this.tellNext(msg);
		}
	}

	abstract AbstractWorkThread createWorkThread(CheckoutBuildDeleteTask cbdt);

	private void submitWork(CheckoutBuildDeleteTask cbdt) {

		AbstractWorkThread awt = this.createWorkThread(cbdt);
		
		awt.actorsToNoftifyWhenComplete.add(this.getSelf());

		if (awt.bat.type == null)
		{
			tellNext(cbdt);
			return;
		}
		
		// tell the next if the type is null
		switch(awt.bat.type)
		{
		case CHECKOUT:
			if (cbdt.checkout.isNull())
			{
				tellNext(cbdt);
				return;
			}
			break;
		case BUILD:
			if (cbdt.build.isNull())
			{
				tellNext(cbdt);
				return;
			}
			break;
		case DELETE:
			System.out.println(cbdt.delete);
			if (cbdt.delete.isNull())
			{
				tellNext(cbdt);
				return;
			}
			break;
		}
		
		// otherwise execute the task unless its canceled	
		handleExection(awt);
	}
	
	final Set<AbstractWorkThread> abstractWorkerThreads = new HashSet<AbstractWorkThread>();
	
	protected void handleExection(AbstractWorkThread awt)
	{
		
		// add the master as a listener
		addMasterAsConcerne(awt);
		// notify listeners
		awt.notifyListeners();
		// set the status to submitted
		awt.setStatus(STATUS.SUBMITTED);
		// add this as a listener
		abstractWorkerThreads.add(awt);
		// execute
		this.executor.execute(awt);
	}

	private void addMasterAsConcerne(final AbstractWorkThread awt) {
		ThreadCompleteListener tcl = new ThreadCompleteListener() {

			@Override
			public void notifiedOfThreadCompleted(NotifyingThread task) {
				master.tell(new AbstractWorkingThreadResult(awt.cbdt, task.getStatus(), awt.bat));
			}
		};
		awt.addListener(tcl);
	}
	
	public abstract WORKTYPE getWorkType();
}
