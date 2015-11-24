package arbiter.selecteddates.gui.calendar;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.michaelbaranov.microba.calendar.CalendarPane;





public class CalendarDateSelector extends JPanel {

	private static final long serialVersionUID = 1L;

	public final CalendarPane m_calendar_pane = new CalendarPane();
	
	
	public CalendarDateSelector()
	{
			JPanel panel = new JPanel();

			panel.setLayout(new GridBagLayout());
			panel.add(
					m_calendar_pane,
					new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER,
					GridBagConstraints.NONE,
					new Insets(5, 5, 5, 5), 0, 0));
			
			this.add(panel);
		}


		
		
		public CalendarPane getCalendarPane()
		{
			return m_calendar_pane;
		}
		
		
		
		
		
		public static void main(String[] args)
		{
			JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(200,200);
			frame.add(new CalendarDateSelector());
			frame.setVisible(true);
		}
		
		
	



}
