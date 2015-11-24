package arbiter.bat;

import java.io.Serializable;

public enum BATBUILD implements BATABLE, Serializable
{
	NONE	(	BAT.NULL, 			true, 	""),
	TS		(	BAT.BUILD_TS, 		false, "Obj/INTEL_Release/ToolStudio.exe"),
	PLUGINS(	BAT.BUILD_PLUGINS, 	false, "Plugins/Obj/INTEL_Release/ToolStudio.exe");
	
	public final BAT bat;
	public final boolean isNull;
	public final String bin;
	
	BATBUILD(BAT bat, boolean isNull, String bin)
	{
		this.bat = bat;
		this.isNull = isNull;
		this.bin = bin;
	}
	
	
	@Override
	public boolean isNull() {
		return isNull;
	}
}
