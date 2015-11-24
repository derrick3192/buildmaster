package arbiter.main;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import arbiter.GUITest;
import arbiter.selecteddates.gui.main.SelectedDatesPanel;
import arbiter.tasksexecuting.TaskView;

public class View extends JFrame{
	

	private static final long serialVersionUID = 1L;
	
	public JMenuItem createRemoteMaster = new JMenuItem("Configure Remote Master");
	public JMenuItem displayLocalMaster = new JMenuItem("Show Local Master Details");
	
	
	public JMenuItem threadsCheckout = new JMenuItem("No asychronized Checkouts");
	public JMenuItem threadsBuild = new JMenuItem("No asychronized Builds");
	public JMenuItem threadsDelete = new JMenuItem("No asychronized Deletes");
	
	
	public JMenuItem showCheckoutConfiguration = new JMenuItem("Show Checkout Configuration");
	public JMenuItem showBuildConfiguration = new JMenuItem("Show Build Configuration");
	public JMenuItem showDeleteConfiguration = new JMenuItem("Show Delete Configuration");
	
	public JMenuItem cvsusername = new JMenuItem("CVS Username");
	public JMenuItem cvsusernameView = new JMenuItem("Show Username");
	
	final SelectedDatesPanel selectedDatesPanel = new SelectedDatesPanel();
	final TaskView	taskView = new TaskView();

	public View()
	{
		this.setSize(800,800);
		
		JMenuBar bar = new JMenuBar();
		
		JMenu masterconfig = new JMenu("Master");
		bar.add(masterconfig);
		masterconfig.add(createRemoteMaster);
		masterconfig.add(displayLocalMaster);
		
		JMenu defauls = new JMenu("Defaults");
		bar.add(defauls);
		
		defauls.add(cvsusername);
		
		defauls.add(threadsCheckout);
		defauls.add(threadsBuild);
		defauls.add(threadsDelete);
		
		
		defauls.add(showCheckoutConfiguration);
		defauls.add(showBuildConfiguration);
		defauls.add(showDeleteConfiguration);
		
		defauls.add(cvsusernameView);
		
		
		setLayout(new GridLayout(1, 2));
		setJMenuBar(bar);
		add(selectedDatesPanel);
		add(taskView);		
	}
	
	public static void main(String[] args) {
		GUITest.showComponent(new View());
	}

}
