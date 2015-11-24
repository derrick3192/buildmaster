package arbiter.master;

import java.io.Serializable;

import akka.actor.ActorPath;
import akka.actor.ActorRef;

public class AddAbstractThreadActoreWatcher implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final String actorAddress;
	public final ActorPath ref;
	public final ActorRef aref;
	
	public AddAbstractThreadActoreWatcher(String local, ActorPath ref, ActorRef aref)
	{
//		System.out.println("Local: \t" + local);
//		System.out.println("Actor Path: \t" + ref);
//		System.out.println("Path Root: \t" + ref.root());
//		System.out.println();
		this.ref = ref;
		this.aref = aref;
		actorAddress = local + "/" + ref.toString().replace(ref.root()+"", "");
		System.out.println(actorAddress);
	}

}
