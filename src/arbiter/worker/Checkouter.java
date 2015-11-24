package arbiter.worker;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActorFactory;
import arbiter.bat.WORKTYPE;
import arbiter.task.AbstractWorkThread;
import arbiter.task.CheckoutBuildDeleteTask;
import arbiter.task.CheckoutWorkThread;


public class Checkouter extends AbstractWorker {

	final ActorRef builder;

	private static final String cvsuserpath = "C:\\Arbiter\\SRCINFO\\cvsroor.txt";
	private String username = readCVSName();
	private static String defaultUserName = "derrick";
	
	public Checkouter(final ActorRef master)
	{
		super(master);
		
		if (username == null)
		{
			recordCVSUserName(defaultUserName);
		}

		builder = this.getContext().actorOf(
				new Props(new UntypedActorFactory() {
					private static final long serialVersionUID = 1L;

					@Override
					public Actor create() {
						return new Builder(master);
					}
				}),
		"checkouter");

	}

	@Override
	AbstractWorkThread createWorkThread(CheckoutBuildDeleteTask cbdt) {
		return new CheckoutWorkThread(cbdt, builder, username);
	}

	@Override
	public ActorRef getNext() {
		return builder;
	}

	@Override
	public WORKTYPE getWorkType() {
		return WORKTYPE.CHECKOUT;
	}


	@Override
	public void onReceive(Object msg) throws Exception {
		
		// if there is not much memory left, pause for 10 minutes, and then try again. 
//		if (notMuchDiskLeft())
//		{
//			Thread.sleep(1000*60*60*10);
//			this.onReceive(msg);
//		}
		
		super.onReceive(msg);
		
		if (msg instanceof CVSUserNameConfig)
		{
			CVSUserNameConfig cvs = (CVSUserNameConfig)msg;
			
			System.out.println("New CVS USERNAME: "+cvs.username);
			if (cvs.username != null)
			{
				recordCVSUserName(cvs.username);
				
			}
			cvs.master.tell(new CVSUsernameConfigResult(this.username));
			
		}
	}
	
	
	private static String readCVSName()
	{
        // Location of file to read
         File file = new File(cvsuserpath);
         try {
             Scanner scanner = new Scanner(file);
             while (scanner.hasNextLine()) {
                 String line = scanner.nextLine();
                 return line;
             }
             scanner.close();
         } catch (FileNotFoundException e) {
         }
         return null;
	}
	
	private void recordCVSUserName(String username)
	{
		this.username = username;
		PrintWriter writer;
		try {
			writer = new PrintWriter(cvsuserpath, "UTF-8");
			writer.println(this.username);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println();
	}
	
	private static double minMem = 3.0;
	@SuppressWarnings("unused")
	private static boolean notMuchDiskLeft()
	{
		return (new File("C:/").getFreeSpace()/Math.pow(10, 9)-1) < minMem;
	}


}
