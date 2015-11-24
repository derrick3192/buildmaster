package arbiter.selecteddates.gui.browser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFileChooser;

import arbiter.GUITest;
import arbiter.selecteddates.SelectedDate;
import arbiter.selecteddates.gui.SelectorComponent;



public class BrowserPanel extends JFileChooser implements SelectorComponent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BrowserPanel()
	{
		super("C:\\Arbiter");
		setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		setMultiSelectionEnabled(true);
	}
	
	
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
	
	
	public Collection<SelectedDate> getSelectedDates()
	{
		File files[] = this.getSelectedFiles();
		List<SelectedDate> dates = new ArrayList<SelectedDate>();
		for (File f : files)
		{
			try
			{
				String name = f.getName();
				name = name.replace("Src_Plugins_", "");
				name = name.replace("_DNBU", "");
				name = name.replace("_", "/");
				name = name.trim();
				
				Date ddate = sdf.parse(name);
				dates.add(new SelectedDate(ddate));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return dates;
	}
	
	

	@Override
	public void clearSelectedDates() {
		// NOTHING NEEDED
	}


	@Override
	public void updateSelectedDates(Collection<SelectedDate> selectedDates) {
		// NOTHING NEEDED
	}


	@Override
	public void addSelectedDatesBtnListiner(ActionListener al) {
		this.addActionListener(al);
	}


	@Override
	public JComponent getSelf() {
		return this;
	}
	
	
	public static void main(String[] args) {
		final BrowserPanel bp = new BrowserPanel();
		
		bp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(bp.getSelectedDates().size());	
			}
		});
		
		GUITest.showComponent(bp);
	}



	@Override
	public String getComponentName() {
		return "Broweser";
	}



	@Override
	public void addRemoveSelectedDateListiner(ActionListener al) {
		// not needed
	}






}