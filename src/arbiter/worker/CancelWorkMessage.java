package arbiter.worker;

import arbiter.bat.WORKTYPE;

public class CancelWorkMessage {
	
	public CancelWorkMessage(int id, WORKTYPE type) {
		super();
		this.id = id;
		this.type = type;
	}
	public final int id;
	public final WORKTYPE type;

}
