package arbiter.tasksexecuting;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import akka.actor.UntypedActor;
import arbiter.task.AbstractWorkingThreadResult;

public class TaskTableController extends UntypedActor {

	Map<Long, AbstractWorkingThreadResult> abstractWorkerThreads = new HashMap<Long, AbstractWorkingThreadResult>();
	final TaskTableModel taskTableModel = new TaskTableModel();
	
	private int count = 0;
	
	public TaskTableController(JTable table)
	{
		table.setModel(taskTableModel);
		
		TableRowSorter<TableModel> sorter 
	    = new TableRowSorter<TableModel>(taskTableModel);
		table.setRowSorter(sorter);
		
		
		table.setDefaultRenderer(Object.class, taskTableModel);
	}
	
	@Override
	public void onReceive(Object msg) throws Exception
	{
		if (msg instanceof AbstractWorkingThreadResult)
		{
			AbstractWorkingThreadResult awt = (AbstractWorkingThreadResult) msg;
			abstractWorkerThreads.put(awt.cbt.id, awt);
			taskTableModel.update(abstractWorkerThreads.values());
			count++;
			//System.out.println("Just Recieved Message: "+count);
			//System.out.println("awt.id="+awt.id+" awt.getStatus()="+awt.getStatus());
			
		}
	}

}
