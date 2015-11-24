package arbiter.selecteddates.actor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import arbiter.selecteddates.SelectedDate;

public class CurrentSelectedDatesResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final List<SelectedDate> dates;
	public CurrentSelectedDatesResponse(Collection<SelectedDate> dates) {
		super();
		this.dates = new ArrayList<SelectedDate>(dates);
	}
}