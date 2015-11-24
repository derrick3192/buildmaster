package arbiter.worker;

import java.io.Serializable;

import akka.actor.ActorRef;

public class CVSUserNameConfig implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final String username;

	public CVSUserNameConfig(String username, ActorRef master) {
		super();
		this.username = username;
		this.master = master;
	}
	
	public final ActorRef master;
}
