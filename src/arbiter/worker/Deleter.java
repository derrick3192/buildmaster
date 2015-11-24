package arbiter.worker;

import akka.actor.ActorRef;
import arbiter.bat.WORKTYPE;
import arbiter.task.AbstractWorkThread;
import arbiter.task.CheckoutBuildDeleteTask;
import arbiter.task.DeleteWorkThread;

public class Deleter extends AbstractWorker {
	
	public Deleter(ActorRef master)
	{
		super(master);
	}

	@Override
	public ActorRef getNext() {
		return null;
	}

	@Override
	AbstractWorkThread createWorkThread(CheckoutBuildDeleteTask cbdt) {
		return new DeleteWorkThread(cbdt, null);
	}

	@Override
	public WORKTYPE getWorkType() {
		return WORKTYPE.DELETE;
	}


}
