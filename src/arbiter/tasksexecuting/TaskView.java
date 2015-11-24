package arbiter.tasksexecuting;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import arbiter.GUITest;

public class TaskView extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public final JTable table = new JTable();
	
//	public JButton removeSelected = new JButton("Clear Selected");
//	public JButton clearAll = new JButton("Clear All");
//	public JButton clearFinished = new JButton("Clear Finished");
	
	public TaskView()
	{
		setLayout(new BorderLayout());
		
		JPanel btnPanel = new JPanel();

//		btnPanel.add(removeSelected);
//		btnPanel.add(clearAll);
//		btnPanel.add(clearFinished);
		
		add(new JScrollPane(table), BorderLayout.CENTER);
		add(btnPanel, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args) {
		GUITest.showComponent(new TaskView());
	}


}
