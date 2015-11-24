package arbiter.worker;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActorFactory;
import arbiter.bat.WORKTYPE;
import arbiter.task.AbstractWorkThread;
import arbiter.task.BuildWorkThread;
import arbiter.task.CheckoutBuildDeleteTask;

public class Builder extends AbstractWorker{

	
	final ActorRef deleter;
	
	public Builder(final ActorRef master)
	{
		super(master);
		
		deleter = this.getContext().actorOf(
				new Props(new UntypedActorFactory() {
					private static final long serialVersionUID = 1L;

					@Override
					public Actor create() {
						return new Deleter(master);
					}
				}),
		"deleter");
	}

	@Override
	public ActorRef getNext() {
		return deleter;
	}

	@Override
	AbstractWorkThread createWorkThread(CheckoutBuildDeleteTask cbdt) {
		return new BuildWorkThread(cbdt, deleter);
	}

	@Override
	public WORKTYPE getWorkType() {
		return WORKTYPE.BUILD;
	}

}
