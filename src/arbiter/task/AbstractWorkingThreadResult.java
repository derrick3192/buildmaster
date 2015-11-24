package arbiter.task;

import java.io.Serializable;

import arbiter.bat.BAT;
import arbiter.util.STATUS;

public class AbstractWorkingThreadResult implements Serializable {

	

	private static final long serialVersionUID = 1L;
	public AbstractWorkingThreadResult(CheckoutBuildDeleteTask cbt, STATUS status, BAT bat) {
		super();
		this.cbt = cbt;
		this.status = status;
		this.bat = bat;
	}
	public final CheckoutBuildDeleteTask cbt;
	public final STATUS status;
	public final BAT bat;
	
}
