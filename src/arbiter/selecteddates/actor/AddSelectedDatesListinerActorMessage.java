package arbiter.selecteddates.actor;

import java.io.Serializable;

import akka.actor.ActorRef;

public class AddSelectedDatesListinerActorMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final ActorRef actor;
	
	public AddSelectedDatesListinerActorMessage(ActorRef actor)
	{
		this.actor = actor;
	}

}
