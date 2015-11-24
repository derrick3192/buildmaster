package arbiter.master;

import java.util.HashMap;
import java.util.Map;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import arbiter.task.AbstractWorkingThreadResult;
import arbiter.task.CheckoutBuildDeleteTask;

import arbiter.worker.ChangeThreadSize;
import arbiter.worker.Checkouter;

public class Master extends UntypedActor {

	final ActorRef checkouter;

	private final Map<String, ActorRef> actorAbstractThreadWatchers = new HashMap<String,ActorRef>();
	

	public Master() {
		
		
		
		checkouter = this.getContext().actorOf(
				new Props(new UntypedActorFactory() {
					private static final long serialVersionUID = 1L;

					@Override
					public Actor create() {
						return new Checkouter(self());
					}
				}), "checkouter");
	}

	@Override
	public void onReceive(Object msg) throws Exception {

		
		
		
		if (msg instanceof CheckoutBuildDeleteTask) {
			checkouter.tell((CheckoutBuildDeleteTask) msg);
			return;
		}
		else if (msg instanceof AbstractWorkingThreadResult) {
			AbstractWorkingThreadResult nt = (AbstractWorkingThreadResult) msg;
			for (ActorRef ref : actorAbstractThreadWatchers.values())
			{
				System.out.println(ref.path());
				ref.tell(nt);
			}
			
			return;
		}
		else if (msg instanceof AddAbstractThreadActoreWatcher)
		{
			AddAbstractThreadActoreWatcher aataw = ((AddAbstractThreadActoreWatcher)msg);
			//ActorRef actor = getContext().actorFor(aataw.actorAddress);
			ActorRef actor = aataw.aref;
			System.out.println("Actor address: "+ aataw.actorAddress);
			System.out.println(actor.path());
			actorAbstractThreadWatchers.put(aataw.actorAddress, actor);
			System.out.println(actorAbstractThreadWatchers.size());
		}
		else if (msg instanceof RemoveAbstractThreadActoreWatcher)
		{
			RemoveAbstractThreadActoreWatcher ratw = (RemoveAbstractThreadActoreWatcher) msg;
			actorAbstractThreadWatchers.remove(ratw.actorAddress);
			System.out.println(actorAbstractThreadWatchers.size());
		}
		
		else if (msg instanceof ChangeThreadSize)
		{
			checkouter.tell(msg);
		}
		else if (msg instanceof String)
		{
			sender().tell("Joined!");
		} else
		{
			checkouter.tell(msg);
		}
		
	}
}