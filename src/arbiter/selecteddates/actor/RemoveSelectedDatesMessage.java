package arbiter.selecteddates.actor;

import java.io.Serializable;
import java.util.Collection;

import arbiter.selecteddates.SelectedDate;

public class RemoveSelectedDatesMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RemoveSelectedDatesMessage(Collection<SelectedDate> dates) {
		super();
		this.dates = dates;
	}

	public final Collection<SelectedDate> dates;
	
}
