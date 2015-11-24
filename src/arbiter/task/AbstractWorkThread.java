package arbiter.task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import akka.actor.ActorRef;
import arbiter.bat.BAT;
import arbiter.util.CommandLineAccessor;
import arbiter.util.DateUtil;
import arbiter.util.NotifyingThread;
import arbiter.util.Util;
import arbiter.bat.*;
public abstract class AbstractWorkThread extends NotifyingThread implements Serializable
{


	private static final long serialVersionUID = 1L;

	public final long id;
	public final Date date;
	public final BAT bat;
	public final String[] options;
	public final String directory;
	private ActorRef next = null;
	public final CheckoutBuildDeleteTask cbdt;
	
	public final List<ActorRef> actorsToNoftifyWhenComplete = new ArrayList<ActorRef>();
	
	public AbstractWorkThread(CheckoutBuildDeleteTask cbdt, BAT bat, String[] options)
	{
		this.cbdt = cbdt;
		this.id = cbdt.id;
		this.date = cbdt.date;
		this.bat = bat;
		this.options = options;
		this.directory = DateUtil.getFolderName(date);
	}
	
	public AbstractWorkThread(CheckoutBuildDeleteTask cbdt, BAT bat, String[] options, final ActorRef next)
	{
		this(cbdt,bat,options);
		this.next = next;
	}
	
	@Override
	public void doRun()
	{
		System.out.println("Task "+this.getClass()+" executing batch: "+this.bat);
		CommandLineAccessor.executeBatch(Util.concatAll(new String[]{BatUtil.getBat(bat.bat)}, options));
		recordResult();
	}
	
	@Override
	public void onComplete()
	{
		if (next != null)
		{
			next.tell(this.cbdt);
		}
	}
	protected void recordResult(){}
	

}
