package arbiter.selecteddates.gui.table;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import arbiter.GUITest;
import arbiter.bat.BATBUILD;
import arbiter.bat.BATCHECKOUT;
import arbiter.bat.BATDELETE;

public class SelectedDatesTablePanel extends JPanel{

	private static final long serialVersionUID = 1L;

	public JComboBox<BATBUILD> 		m_combo_build 		= new JComboBox<BATBUILD>(BATBUILD.values());
	public JComboBox<BATCHECKOUT> 	m_combo_checkout 	= new JComboBox<BATCHECKOUT>(BATCHECKOUT.values());
	public JComboBox<BATDELETE> 	m_combo_delete 		= new JComboBox<BATDELETE>(BATDELETE.values());
	
	
	public JTable table = new JTable();
	
	public JButton clearAll = new JButton("Clear All");
	public JButton clearSelected = new JButton("Clear Selected");
	public JButton btnSendToMaster = new JButton("Send All to Master");
	
	
	public SelectedDatesTablePanel()
	{
		setLayout(new BorderLayout());
		
		// table buttons
		JPanel tableButtons = new JPanel();
		tableButtons.setLayout(new FlowLayout());
		tableButtons.add(clearAll);
		tableButtons.add(clearSelected);
		
		
		// enum buttons
		JPanel enumPanel = new JPanel();
		enumPanel.setLayout(new GridLayout(1,9));
		
		// checkout
		JPanel pnl_chk = new JPanel(new GridLayout(1,2));
		pnl_chk.add(new JLabel("Checkout:"));
		pnl_chk.add(m_combo_checkout);
		enumPanel.add(pnl_chk);
		
		// build
		JPanel pnl_build = new JPanel(new GridLayout(1,2));
		pnl_build.add(new JLabel("Build"));
		pnl_build.add(m_combo_build);
		enumPanel.add(pnl_build);
		
		// delete
		JPanel pnl_del = new JPanel(new GridLayout(1,2));
		pnl_del.add(new JLabel("Delete"));
		pnl_del.add(m_combo_delete);
		enumPanel.add(pnl_del);
		
		// bottom panel
		JPanel btm_left_pnl = new JPanel();
		btm_left_pnl.setLayout(new BorderLayout());
		btm_left_pnl.add(enumPanel, BorderLayout.CENTER);
		btm_left_pnl.add(tableButtons, BorderLayout.NORTH);
		
		JPanel btm_pnl = new JPanel();
		btm_pnl.setLayout(new BorderLayout());
		btm_pnl.add(btm_left_pnl, BorderLayout.CENTER);
		btm_pnl.add(btnSendToMaster, BorderLayout.EAST);
		
		add(btm_pnl, BorderLayout.SOUTH);
		add(new JScrollPane(table), BorderLayout.CENTER);
		table.updateUI();
		add(new JScrollPane(table) );
	}
	
	public static void main(String[] args) {
		
		SelectedDatesTablePanel p = new SelectedDatesTablePanel();
		p.table.setModel(new SelectedDatesTableModel());
		
		GUITest.showComponent(new SelectedDatesTablePanel());
	}
	
	
	
	
}
