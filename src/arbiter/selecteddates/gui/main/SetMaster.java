package arbiter.selecteddates.gui.main;

import java.io.Serializable;

import akka.actor.ActorRef;

public class SetMaster implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final ActorRef ref;
	public SetMaster(ActorRef ref)
	{
		this.ref = ref;
	}
}