package arbiter.selecteddates.gui.main;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import arbiter.GUITest;
import arbiter.selecteddates.gui.SelectorComponent;
import arbiter.selecteddates.gui.browser.BrowserPanel;
import arbiter.selecteddates.gui.calendar.CalendarDateComponent;
import arbiter.selecteddates.gui.dialog.DialogDateSelectorPanel;
import arbiter.selecteddates.gui.table.SelectedDatesTablePanel;

public class SelectedDatesPanel extends JPanel
{

	private static final long serialVersionUID = 1L;
	
	
	final Map<String, SelectorComponent> selector_components = new HashMap<String,SelectorComponent>();
	public final  JTabbedPane tabbedPanel = new JTabbedPane();
	public final SelectedDatesTablePanel selectedDatesTablePanel = new SelectedDatesTablePanel();
	
	public final int a=3;
	

	public SelectedDatesPanel()
	{
		setLayout(new BorderLayout());
		

		// tabbed panel
		addToSelectedDatesTabbedPanel(new CalendarDateComponent());
		addToSelectedDatesTabbedPanel(new BrowserPanel());
		addToSelectedDatesTabbedPanel(new DialogDateSelectorPanel());
		
		
		JSplitPane pane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		pane.setTopComponent(tabbedPanel);
		pane.setBottomComponent(selectedDatesTablePanel);
		add(pane, BorderLayout.NORTH);
		
		//add(tabbedPanel,BorderLayout.NORTH);
		
		// table panel
		//add(selectedDatesTablePanel,BorderLayout.CENTER);
		

		
	}
	
	public void addToSelectedDatesTabbedPanel(SelectorComponent component)
	{
		selector_components.put(component.getComponentName(), component);
		tabbedPanel.add(component.getSelf(), component.getComponentName());
	}
	
	public static void main(String[] args)
	{
		GUITest.showComponent(new SelectedDatesPanel());
	}
	
	
}
