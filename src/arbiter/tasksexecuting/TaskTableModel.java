package arbiter.tasksexecuting;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import akka.actor.Status.Status;
import arbiter.bat.BAT;
import arbiter.task.AbstractWorkingThreadResult;



public class TaskTableModel extends AbstractTableModel implements TableCellRenderer
{

	private static final long serialVersionUID = 1L;
	
	List<AbstractWorkingThreadResult> list = new ArrayList<AbstractWorkingThreadResult>();
	
	public TaskTableModel() {}

	public void update(List<AbstractWorkingThreadResult> awts)
	{
		list = awts;
		fireTableDataChanged();
	}
	
	public void update(Collection<AbstractWorkingThreadResult> awts)
	{
		update(new ArrayList<AbstractWorkingThreadResult>(awts));
	}
	
	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		AbstractWorkingThreadResult item = list.get(rowIndex);
		switch(columnIndex)
		{
		case 0:
			return item.cbt.id;
		case 1:
			return item.cbt.date;
		case 2:
			return item.bat;
		case 3:
			return item.status;
		default:
			return null;
		}
	}
	
	
	public @Override Class<?> getColumnClass(int columnIndex)
	{
		
		switch(columnIndex)
		{
		case 0:
			return Long.class;
		case 1:
			return Date.class;
		case 2:
			return BAT.class;
		case 3:
			return Status.class;
		default:
			return null;
		}
		
	}
	
	@Override
	public String getColumnName(int arg0)
	{
		
		switch(arg0)
		{
		case 0:
			return "GUID";
		case 1:
			return "Date";
		case 2:
			return "BAT";
		case 3:
			return "Status";
		default:
			return null;
		}
	}
	
	
	@Override
	public Component getTableCellRendererComponent(
			JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column){

		AbstractWorkingThreadResult item = list.get(row);
        JTextField editor = new JTextField();
        if (value != null)
        {
            editor.setText(value.toString());
        }
		
		switch(column)
		{
		case 2:
			editor.setBackground(item.bat.type.color);
			break;
		case 3:
			editor.setBackground(item.status.color);
			break;
		}
		

        
        
        
        return editor;
	}

}
