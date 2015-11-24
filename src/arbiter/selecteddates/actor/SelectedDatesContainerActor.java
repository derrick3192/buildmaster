package arbiter.selecteddates.actor;

import java.util.HashMap;
import java.util.Map;

import javax.management.RuntimeErrorException;

import akka.actor.UntypedActor;
import arbiter.selecteddates.SelectedDate;

public class SelectedDatesContainerActor extends UntypedActor
{
	
	private final Map<String,SelectedDate> dates = new HashMap<String,SelectedDate>();
	
	
	@Override
	public void onReceive(Object msg) throws Exception
	{
		if (msg instanceof AddDatesMessage)
		{
			AddDatesMessage sdm = (AddDatesMessage) msg;
			for (SelectedDate date : sdm.dates)
			{
				dates.put(date.toString(), date);
			}
		}
		else if (msg instanceof RemoveSelectedDatesMessage)
		{
			RemoveSelectedDatesMessage rsdm = (RemoveSelectedDatesMessage) msg;
			
			for (SelectedDate date : rsdm.dates)
			{
				if (!dates.containsKey(date.toString()))
				{
					System.err.println("Error date not contained!");
					throw new RuntimeErrorException(null);
				}
				dates.remove(date.toString());
			}
		}
		else if (msg instanceof RequestSelectedDatesMessage)
		{
			sender().tell(new CurrentSelectedDatesResponse(dates.values()));
		}
		else if (msg instanceof RemoveAllDates)
		{
			dates.clear();
		}
	}
	

}
