package arbiter.selecteddates.gui.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import arbiter.bat.BATBUILD;
import arbiter.bat.BATCHECKOUT;
import arbiter.historian.BuildHistorian;
import arbiter.selecteddates.SelectedDate;
import arbiter.task.BuildWorkThread;
import arbiter.task.CheckoutBuildDeleteTask;
import arbiter.task.CheckoutWorkThread;


public class SelectedDatesTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	List<SelectedDate> selected_dates = new ArrayList<SelectedDate>();
	
	public void updateSelection(List<SelectedDate> selected_dates)
	{
		this.selected_dates = selected_dates;
		fireTableDataChanged();
		System.out.println(getRowCount());
	}
	
	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return selected_dates.size();
	}

	
	
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex)
	{
		SelectedDate selected_date = selected_dates.get(rowIndex);
		
		switch(columnIndex)
		{
			case 0:
				return selected_date.getCVSDate();
			//case 1:
				
				// this code is not thread safe
//				for (TableItem  created_task : created_tasks)
//				{
//					if (created_task.getDate().equals(selected_date.getCVSDate()))
//					{
//						return created_task.getStatus().toString();
//					}
//				}
				// TODO
				//return "N\\A";
				// end of dangerous code
			case 1:
				return BuildHistorian.getResult(selected_date.getCVSDate()).getFullName();
			case 2:
				CheckoutBuildDeleteTask cbdt = new CheckoutBuildDeleteTask(null, selected_date.getDate(), BATCHECKOUT.PLUGINS, BATBUILD.PLUGINS, null);
				if (new BuildWorkThread(cbdt,null).isTrivial()&&
						new CheckoutWorkThread(cbdt,null,"").isTrivial())
				{
					return "have src,exe";
				}
				else if (new BuildWorkThread(cbdt,null).isTrivial())
				{
					return "have exe";
				}
				else if (new CheckoutWorkThread(cbdt,null,"").isTrivial())
				{
					return "have src";
				}
				else
				{
					return "nill";
				}
			case 3:
				return selected_date;
			default:
				return null;
		}
	}
	
	
	
	
	@Override
	public String getColumnName(int i)
	{
		switch(i)
		{
		case (0):
			return "Date";
		case (1):
			return "History";
		case (2):
			return "State";
		case (3):
			return "--";
		default:
			return null;
		}
	}

}
