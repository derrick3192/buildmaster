package arbiter.util;

import java.awt.Color;
import java.io.Serializable;

/** The possible statuses of the task **/
public enum STATUS implements Serializable
{
	WAITING(Color.CYAN),
	SUBMITTED(Color.CYAN),
	EXECUTING(Color.ORANGE),
	FAILED(Color.RED),
	COMPLETED(Color.GREEN),
	CANCELLED(Color.GRAY),
	NOT_NEEDED(Color.YELLOW);
	
	
	public final Color color;
	STATUS(Color c)
	{
		this.color = c;
	}
	
}