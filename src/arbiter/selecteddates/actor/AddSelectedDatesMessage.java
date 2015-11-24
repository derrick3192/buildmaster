package arbiter.selecteddates.actor;

import java.io.Serializable;
import java.util.Collection;

import arbiter.selecteddates.SelectedDate;

public class AddSelectedDatesMessage
implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AddSelectedDatesMessage(Collection<SelectedDate> dates) {
		super();
		this.dates = dates;
	}

	public final Collection<SelectedDate> dates;
}
