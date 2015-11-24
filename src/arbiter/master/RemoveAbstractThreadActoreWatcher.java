package arbiter.master;

import java.io.Serializable;

import akka.actor.ActorPath;
import akka.actor.ActorRef;

public class RemoveAbstractThreadActoreWatcher implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public final String actorAddress;
	public final ActorPath ref;
	public final ActorRef aref;
	
	public RemoveAbstractThreadActoreWatcher(String local, ActorPath ref, ActorRef aref)
	{
		actorAddress = local + "/" + ref.toString().replace(ref.root()+"", "");
		this.aref = aref;
		this.ref = ref;
		System.out.println(actorAddress);
	}


}
