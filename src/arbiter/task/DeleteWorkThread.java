package arbiter.task;

import java.io.File;

import akka.actor.ActorRef;


public class DeleteWorkThread extends AbstractWorkThread {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DeleteWorkThread(CheckoutBuildDeleteTask cbdt, ActorRef master)
	{
		super(cbdt, cbdt.delete.bat, appendPaths(cbdt), master);
	}
	
	private static String[] appendPaths(CheckoutBuildDeleteTask cbdt) {
		String[] paths = new String[cbdt.delete.dirs.length];
		for (int i=0; i<cbdt.delete.dirs.length; i++)
		{
			String dir = cbdt.delete.dirs[i];
			paths[i] = cbdt.fullFolderPath() + "\\" + dir;
		}
		return paths;
	}

	@Override
	public boolean wasSuccessful()
	{
		boolean exists = false;
		for (String dir : this.cbdt.delete.dirs)
		{
			exists = exists || !(new File(dir)).exists();
		}
		return exists;
	}
	
	@Override
	public boolean isTrivial()
	{
		return false;
	}

}
