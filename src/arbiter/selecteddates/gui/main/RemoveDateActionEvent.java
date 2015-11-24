package arbiter.selecteddates.gui.main;

import java.awt.event.ActionEvent;

import arbiter.selecteddates.SelectedDate;

public class RemoveDateActionEvent extends ActionEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final SelectedDate date;
	
	public RemoveDateActionEvent(Object source, int id, String command, SelectedDate date) {
		super(source, id, command);
		this.date = date;
	}

}
