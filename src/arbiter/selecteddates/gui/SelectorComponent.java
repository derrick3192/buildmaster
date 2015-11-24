package arbiter.selecteddates.gui;

import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.JComponent;

import arbiter.selecteddates.SelectedDate;
import arbiter.selecteddates.SelectedDatesUpdateable;


/**
 * @author Derrick
 *
 */
public interface SelectorComponent extends SelectedDatesUpdateable {
	
	public abstract Collection<SelectedDate> getSelectedDates();
	
	public abstract void clearSelectedDates();
	
	public abstract String getComponentName();
	
	public abstract void addSelectedDatesBtnListiner(ActionListener al);
	
	
	public abstract JComponent getSelf();
	
	
	public void addRemoveSelectedDateListiner(ActionListener al);

	
}
