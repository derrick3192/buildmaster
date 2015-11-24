package arbiter.task;

import java.io.File;

import akka.actor.ActorRef;
import arbiter.util.DateUtil;

public class CheckoutWorkThread extends AbstractWorkThread {




	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CheckoutWorkThread(CheckoutBuildDeleteTask cbdt, ActorRef next, String username)
	{
		super(  cbdt,
				cbdt.checkout.bat, new String[]{
				cbdt.dayInts() + "",
				cbdt.monthInts() + "",
				cbdt.yearInts() + "",
				cbdt.monthChars() + "",
				username},
				next);
	}
	
	@Override
	public boolean wasSuccessful()
	{
		String folder_name = DateUtil.getFolderName(date);
		return new File("C:\\Arbiter\\"+folder_name+"\\Plugins").isDirectory();
	}

	@Override
	public boolean isTrivial()
	{

		return 	new File("C:\\Arbiter\\"+directory+"\\Plugins\\Obj\\INTEL_Release\\ToolStudio.exe").exists() ||
				new File("C:\\Arbiter\\"+directory+"\\Plugins\\ToolStudioResource").exists();
	}


}
