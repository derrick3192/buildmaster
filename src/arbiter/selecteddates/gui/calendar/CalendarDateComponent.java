package arbiter.selecteddates.gui.calendar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;

import com.michaelbaranov.microba.calendar.CalendarPane;

import arbiter.selecteddates.SelectedDate;
import arbiter.selecteddates.gui.SelectorComponent;
import arbiter.selecteddates.gui.main.RemoveDateActionEvent;

public class CalendarDateComponent implements SelectorComponent {

	final CalendarDateSelector calendarDateSelector = new CalendarDateSelector();
	
	final SelectionPolicy selectionPolicy = new SelectionPolicy(); 
	
	ActionListener addToActionListiner = null;
	
	public void doAction(ActionEvent arg0)
	{
		if (addToActionListiner != null)
		{
			addToActionListiner.actionPerformed(arg0);
		}
	}
	
	
	class SelectionChangedAction implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			CalendarPane m_calendarPane = calendarDateSelector.m_calendar_pane;
			try
			{
				try
				{
					if (m_calendarPane.getDate() == null)
					{
						return;
					}
				}
				catch(Exception ex)
				{
					return;
				}
				
				if (selectionPolicy.getCoreDate().keySet().contains(new SelectedDate(m_calendarPane.getDate()).toString()))
				{
					//System.out.println("remove date");
					// TODO put this in the controller
					//selectionPolicy.getCoreDate().remove((new SelectedDate(m_calendarPane.getDate()).toString()));
					if (al != null)
					{
						al.actionPerformed(new RemoveDateActionEvent(this, 0, null, new SelectedDate(m_calendarPane.getDate())));
					}
				}else
				{
					selectionPolicy.getCoreDate().put(new SelectedDate(m_calendarPane.getDate()).toString(),new SelectedDate(m_calendarPane.getDate()));
					doAction(arg0);
				}
				
				m_calendarPane.updateUI();
			} 
			catch (Exception ess)
			{
				//System.out.println(ess.getMessage());
			}
			
			calendarDateSelector.m_calendar_pane.updateUI();
		}
	}
	
	public CalendarDateComponent()
	{
		calendarDateSelector.m_calendar_pane.setHolidayPolicy(selectionPolicy);
		calendarDateSelector.m_calendar_pane.addActionListener(new SelectionChangedAction());	
	}
	

	@Override
	public Collection<SelectedDate> getSelectedDates()
	{
		return selectionPolicy.getSelectedDates();
	}

	@Override
	public void clearSelectedDates()
	{
		selectionPolicy.clearSelectedDates();
	}

	@Override
	public void updateSelectedDates(Collection<SelectedDate> selectedDates)
	{
		selectionPolicy.clearSelectedDates();
		selectionPolicy.addSelectedDates(selectedDates);
		calendarDateSelector.m_calendar_pane.updateUI();
	}

	@Override
	public void addSelectedDatesBtnListiner(ActionListener al) {
		this.addToActionListiner = al;
	}

	@Override
	public JComponent getSelf() {
		return calendarDateSelector;
	}
	

	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200,200);
		
		
		
		final CalendarDateComponent cdc = new CalendarDateComponent();
		cdc.addSelectedDatesBtnListiner(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("here is the add button action");
				System.out.println(cdc.getSelectedDates().size());
			}
		});
		
		List<SelectedDate> selectedDates = new ArrayList<SelectedDate>();
		selectedDates.add(new SelectedDate());
		
		cdc.updateSelectedDates(selectedDates);
		
		frame.add(cdc.getSelf());
		frame.setVisible(true);
	}


	@Override
	public String getComponentName() {
		return "Calendar";
	}


	
	ActionListener al = null;
	@Override
	public void addRemoveSelectedDateListiner(ActionListener al) {
		this.al = al;
	}


	
	

}
