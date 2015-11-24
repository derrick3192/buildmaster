package arbiter.bat;

import java.io.Serializable;


public enum BATCHECKOUT implements BATABLE, Serializable
{
	NONE(BAT.NULL, true),
	TS(BAT.CHECKOUT_TS, 			false),
	PLUGINS(BAT.CHECKOUT_PLUGINS, 	false);
	
	public final BAT bat;
	public final boolean isNull;
	BATCHECKOUT(BAT bat, boolean isNull) {
		this.bat = bat;
		this.isNull= isNull;
	}
	
	@Override
	public boolean isNull() {
		return isNull;
	}
	
	
	


}

