package arbiter.worker;

import java.io.Serializable;

public class CVSUsernameConfigResult implements Serializable{

	public final String result;

	private static final long serialVersionUID = 1L;

	public CVSUsernameConfigResult(String result) {
		super();
		this.result = result;
	}

}
