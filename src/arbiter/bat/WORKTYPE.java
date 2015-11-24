package arbiter.bat;

import java.awt.Color;
import java.io.Serializable;

public enum WORKTYPE implements Serializable
{
	CHECKOUT(Color.PINK),
	BUILD(Color.magenta),
	DELETE(Color.LIGHT_GRAY),
	NULL(Color.WHITE);
	
	public final Color color;
	
	WORKTYPE(Color color)
	{
		this.color = color;
	}
}
