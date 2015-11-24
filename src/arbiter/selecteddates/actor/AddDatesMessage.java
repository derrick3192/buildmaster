package arbiter.selecteddates.actor;

import java.io.Serializable;
import java.util.Collection;

import arbiter.selecteddates.SelectedDate;

public class AddDatesMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final Collection<SelectedDate>dates;

	public AddDatesMessage(Collection<SelectedDate> dates) {
		super();
		this.dates = dates;
	}
	
}
