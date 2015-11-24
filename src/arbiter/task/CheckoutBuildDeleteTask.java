package arbiter.task;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import akka.actor.ActorRef;
import arbiter.bat.BATCHECKOUT;
import arbiter.bat.BATBUILD;
import arbiter.bat.BATDELETE;
import arbiter.util.DateUtil;


public class CheckoutBuildDeleteTask implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public final long			id;
	public final Date 			date;
	public final BATCHECKOUT	checkout;
	public final BATBUILD 		build;
	public final BATDELETE 		delete;
	public final ActorRef		master;
	



	public CheckoutBuildDeleteTask(ActorRef master, Date date, BATCHECKOUT checkout, BATBUILD build, BATDELETE delete)
	{
		super();
		
		synchronized(CheckoutBuildDeleteTask.class)
		{
			id = UUID.randomUUID().getMostSignificantBits();
		}
		
		this.date = date;
		this.checkout = checkout;
		this.build = build;
		this.delete = delete;
		this.master = master;
	}
	
	
	
	
	
	
	
	
	public String folderPath()				{ return DateUtil.getFolderName(date); }
	public String getCVSDate()				{ return DateUtil.getCVSDate(date); }
	public String dayInts()     			{ return DateUtil.dayInts(date); }
	public String monthInts()				{ return DateUtil.monthInts(date); }
	public String yearInts()				{ return DateUtil.yearInts(date); }
	public String monthChars()				{ return DateUtil.monthChars(date);	}
	public String fullFolderPath()			{ return DateUtil.fullFolderPath(date);}
	
}
