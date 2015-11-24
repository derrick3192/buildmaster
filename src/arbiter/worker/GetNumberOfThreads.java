package arbiter.worker;

import java.io.Serializable;

import akka.actor.ActorRef;
import arbiter.bat.WORKTYPE;

public class GetNumberOfThreads implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public final WORKTYPE type;
	public final ActorRef asker;
	
	
	public GetNumberOfThreads(WORKTYPE type, ActorRef asker) {
		super();
		this.type = type;
		this.asker = asker;
	}
	
	
	
}
