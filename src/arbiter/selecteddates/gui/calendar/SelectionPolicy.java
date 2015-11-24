package arbiter.selecteddates.gui.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.michaelbaranov.microba.calendar.CalendarPane;
import arbiter.selecteddates.SelectedDate;

import com.michaelbaranov.microba.calendar.HolidayPolicy;
import com.michaelbaranov.microba.common.AbstractPolicy;

public class SelectionPolicy extends AbstractPolicy implements HolidayPolicy
{

	HashMap<String, SelectedDate> 		m_selected_dates = new HashMap<String, SelectedDate>();
	
	public SelectionPolicy()
	{
	}

	public String getHollidayName(Object source, Calendar date)
	{
	  return null;
	}

	public boolean isHolliday(Object source, Calendar date)
	{
		return m_selected_dates.keySet().contains(new SelectedDate(date.getTime()).toString());
	}

	public boolean isWeekend(Object source, Calendar date)
	{
	  return false;
	}

	

	public Map<String, SelectedDate> getCoreDate(){ return m_selected_dates;}
	
	public List<SelectedDate> getSelectedDates()
	{
		List<SelectedDate> dates = new ArrayList<SelectedDate>();
		for (SelectedDate sdate: m_selected_dates.values())
		{
			dates.add(sdate);
		}
		return dates;
	}
	
	public void clearSelectedDates()
	{
		m_selected_dates.clear();
	}
	
	public void removeDate(Date date)
	{
		removeDate(new SelectedDate(date));
	}
	
	public void removeDate(SelectedDate date)
	{
		 m_selected_dates.remove(date.toString());
	}

	public void addSelectedDates(Collection<SelectedDate> selectedDates) {
		for (SelectedDate d : selectedDates)
		{
			m_selected_dates.put(d.toString(), d);
		}
	}


}