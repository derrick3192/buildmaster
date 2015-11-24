package arbiter.task;

import java.io.Serializable;

public enum TaskStatus implements Serializable
{
	WAITING,
	COMPLETING,
	FAILED,
	COMPLETED,
	NOT_NEEDED;
}
