package arbiter.worker;

import java.io.Serializable;

import arbiter.bat.WORKTYPE;

public class ChangeThreadSize implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final int no;
	public final WORKTYPE type;

	public ChangeThreadSize(int no, WORKTYPE type)
	{
		this.no = no;
		this.type = type;
	}
}
