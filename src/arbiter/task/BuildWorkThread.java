package arbiter.task;

import java.io.File;

import akka.actor.ActorRef;
import arbiter.BuildMaster;
import arbiter.bat.BATBUILD;
import arbiter.historian.BuildHistorian;
import arbiter.historian.HistroialStatus;
import arbiter.util.DateUtil;

public class BuildWorkThread extends AbstractWorkThread {

	private static final long serialVersionUID = 1L;

	public BuildWorkThread(CheckoutBuildDeleteTask cbdt, ActorRef next) {
		super(cbdt,
				cbdt.build.bat,
				new String[]{DateUtil.fullFolderPath(cbdt.date)},
				next);
	}
	
	@Override
	public boolean wasSuccessful()
	{
		if (cbdt.build != null)
		{
			String path = BuildMaster.getRoot()+"\\"+DateUtil.getFolderName(this.cbdt.date)+ "\\" + cbdt.build.bin;
			return new File(path).exists();
		}
		else
		{
			return true;
		}
	}

	@Override
	public boolean isTrivial()
	{
		return wasSuccessful();
	}

	@Override
	protected void recordResult() {
		
		HistroialStatus status;
		
		if (cbdt.build != BATBUILD.NONE)
		{
			if (wasSuccessful())
			{
				status = HistroialStatus.PASS;
			} else {
				status = HistroialStatus.FAIL;
			}
		} else {
			status = HistroialStatus.NOINFO;
		}
		
		switch (cbdt.build)
		{
		case PLUGINS:
			BuildHistorian.recordResult(cbdt.getCVSDate(), status);
			break;
		case TS:
			//BuildHistorian.recordResult(cbdt.getCVSDate(), status);
			break;
		case NONE:
			break;
		}
	}





}
