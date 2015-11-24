package arbiter.bat;

import java.io.Serializable;


public enum BAT implements Serializable
{ 
	
	
	
	// checkout bats
	CHECKOUT_TS(		"checkout_tool_studio.bat", WORKTYPE.CHECKOUT),
	CHECKOUT_PLUGINS(	"checkout_plugins.bat",		WORKTYPE.CHECKOUT),
	
	// build bats
	BUILD_TS(			"build_ts.bat",				WORKTYPE.BUILD),
	BUILD_PLUGINS(		"build_plugins.bat", 		WORKTYPE.BUILD),
	
	// delete bat
	DELETE(				"delete_stuff.bat", 		WORKTYPE.DELETE),
	
	
	NULL( null, null);
	
	
	;
	public final String bat;
	public final WORKTYPE type;
	
	BAT(String bat, WORKTYPE type)
	{
		//this.bat = BatUtil.getBat(bat);
		this.bat = bat;
		this.type = type;
	}
}
