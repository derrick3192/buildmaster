package arbiter.selecteddates.actor;

import java.io.Serializable;
import java.util.Collection;

import arbiter.selecteddates.SelectedDate;

public class SelectedDatesChangedMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public SelectedDatesChangedMessage(Collection<SelectedDate> date) {
		super();
		this.date = date;
	}

	public final Collection<SelectedDate> date;
	
}
