package arbiter.selecteddates.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;




import arbiter.GUITest;
import arbiter.selecteddates.SelectedDate;
import arbiter.selecteddates.gui.SelectorComponent;
import arbiter.util.DateUtil;

import com.michaelbaranov.microba.calendar.DatePicker;

public class DialogDateSelectorPanel extends JPanel implements SelectorComponent
{

	
	DatePicker 		m_date_begin_picker 		= new DatePicker();
	DatePicker 		m_date_till_picker 			= new DatePicker();
	JSpinner 		m_spinner_no_checkout 		= new JSpinner();
	JRadioButton 	m_radio_include_weekends 	= new JRadioButton();
	JButton			m_btn_add_to_selection 		= new JButton("Add to Selection");

	private static final long serialVersionUID = 1L;
	
	public DialogDateSelectorPanel()
	{
		this.setLayout(new BorderLayout());
		this.add(makeTitle(), BorderLayout.NORTH);
		this.add(makeDialog(), BorderLayout.CENTER);
	}
	
	
	public JComponent makeTitle()
	{
		JLabel label = new JLabel("Select Options");
		return label;
	}
	
	public JComponent makeDialog()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		addPair(panel, new JLabel("Begin Date"), 			m_date_begin_picker);
		addPair(panel, new JLabel("End Date"), 				m_date_till_picker);
		addPair(panel, new JLabel("No Sources to Build"), 	m_spinner_no_checkout);
		addPair(panel, new JLabel("Include Weekends"),		m_radio_include_weekends);
		m_radio_include_weekends.setSelected(false);
		m_radio_include_weekends.setEnabled(false);
		
		addWide(panel, 										m_btn_add_to_selection);

		return panel;
	}
	
	
	public DialogDateSelectorSettings getDialogDateSelectorSettings()
	{
		return new DialogDateSelectorSettings(
				m_date_begin_picker.getDate(),
				m_date_till_picker.getDate(),
				(Integer)m_spinner_no_checkout.getValue(),
				m_radio_include_weekends.isSelected());
	}
	
	public void addButtonAddToSelectionListiner(ActionListener al)
	{
		m_btn_add_to_selection.addActionListener(al);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void addWide(Container container,
						 JComponent comp)
	{
		GridBagConstraints g = new GridBagConstraints();
		g.gridy = yC;
		g.gridwidth = 2;
		g.fill = GridBagConstraints.HORIZONTAL;
		container.add(comp, g);
		yC++;
	}
	
	
	private void addC(Container container,
					  JComponent comp ,
					  int x,
					  int y)
	{
		GridBagConstraints g = new GridBagConstraints();
		g.gridx = x;
		g.gridy = y;
		g.fill = GridBagConstraints.HORIZONTAL;
		container.add(comp, g);
	}
	
	private void addPair(	Container container,
							JComponent comp1,
							JComponent comp2,
							int y)
	{
		addC(container, comp1,1,y);
		addC(container, comp2,2,y);
	}

	private int yC = 0;
	
	private void addPair(
			Container container,
			JComponent comp1,
			JComponent comp2)
	{
		addPair(container, comp1, comp2, yC);
		yC++;
	}
	



	@Override
	public Collection<SelectedDate> getSelectedDates() {
		DialogDateSelectorSettings settings = this.getDialogDateSelectorSettings();
		List<Date> dates = DateUtil.allWeekDaysBetween(settings.getFrom(), settings.getTill(), settings.getNoBuilds());
		
		List<SelectedDate> sd = new ArrayList<SelectedDate>();
		for (Date d : dates)
		{
			sd.add(new SelectedDate(d));
		}
		return sd;
	}


	@Override
	public void clearSelectedDates()
	{
		// Nothing needed
	}


	@Override
	public void updateSelectedDates(Collection<SelectedDate> selectedDates) {
		
		//getDialogDateSelectorSettings()
	}


	@Override
	public void addSelectedDatesBtnListiner(ActionListener al) {
		m_btn_add_to_selection.addActionListener(al);
	}


	@Override
	public JComponent getSelf() {
		return this;
	}
	
	
	public static void main(String[] args)
	{
		final DialogDateSelectorPanel dds = new DialogDateSelectorPanel();
		
		dds.addSelectedDatesBtnListiner(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				System.out.println(dds.getSelectedDates().size());
				
			}
		});
		
		GUITest.showComponent(dds);
	}


	@Override
	public String getComponentName() {
		return "Dialog";
	}


	@Override
	public void addRemoveSelectedDateListiner(ActionListener al) {
		// TODO Auto-generated method stub
		
	}


	
	
}
