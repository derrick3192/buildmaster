package arbiter.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.Address;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.pattern.Patterns;
import akka.remote.RemoteActorRefProvider;
import akka.util.Duration;
import akka.util.Timeout;
import arbiter.GUITest;
import arbiter.bat.WORKTYPE;
import arbiter.master.AddAbstractThreadActoreWatcher;
import arbiter.master.Master;
import arbiter.master.RemoveAbstractThreadActoreWatcher;
import arbiter.selecteddates.SelectedDate;
import arbiter.selecteddates.actor.RemoveAllDates;
import arbiter.selecteddates.actor.RemoveSelectedDatesMessage;
import arbiter.selecteddates.gui.main.SelectedController;
import arbiter.selecteddates.gui.main.SetMaster;
import arbiter.tasksexecuting.TaskTableController;
import arbiter.worker.CVSUsernameConfigResult;
import arbiter.worker.ChangeThreadSize;
import arbiter.worker.GetNumberOfThreads;
import arbiter.worker.GetNumberOfThreadsResult;

@SuppressWarnings("deprecation")
public class Controller extends UntypedActor {

	final View view = new View();
	final ActorRef selectorController;
	final ActorRef taskController;
	
	ActorRef master;
	
	public final Address localAddress;
	
	public Controller() {
		
		
		// the master!! (default)
		// need to change this!
		master = this.getContext().actorOf(new Props(Master.class), "master");
		
		System.out.println("Local Master Address!");
		localAddress = ((RemoteActorRefProvider) getContext().provider()).transport().address();
		
		selectorController = this.getContext().actorOf(new Props(new UntypedActorFactory() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Actor create() {
				return new SelectedController(view.selectedDatesPanel);
			}
		}));
		
		
		taskController = this.getContext().actorOf(new Props(new UntypedActorFactory() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Actor create() {
				return new TaskTableController(view.taskView.table);
			}
		}));
		
		view.cvsusername.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String input = JOptionPane.showInputDialog(null, "Enter in new CVS user Name");
				if (input != null)
				{
					master.tell(new arbiter.worker.CVSUserNameConfig(input, self()));
				}
			}
		});
		
		view.cvsusernameView.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				master.tell(new arbiter.worker.CVSUserNameConfig(null, self()));
			}
		});
		
		view.threadsCheckout.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog(null, "Number of Checkout?");
				Integer no = null;
				try {no = Integer.parseInt(name);}
				catch(Exception exc){System.out.println("cancel");}
				if (no == null) {return;}
				master.tell(new ChangeThreadSize(no,WORKTYPE.CHECKOUT));
			}
		}
		);
		
		view.threadsBuild.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog(null, "Number of Builds?");
				Integer no = null;
				try {no = Integer.parseInt(name);}
				catch(Exception exc){System.out.println("cancel");}
				if (no == null) {return;}
				master.tell(new ChangeThreadSize(no,WORKTYPE.BUILD));
			}
		}
		);
		
		view.threadsDelete.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog(null, "Number of Deletes?");
				Integer no = null;
				try {no = Integer.parseInt(name);}
				catch(Exception exc){System.out.println("cancel");}
				if (no == null) {return;}
				master.tell(new ChangeThreadSize(no,WORKTYPE.DELETE));
			}
		}
		);
		
		view.showCheckoutConfiguration.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				master.tell(new GetNumberOfThreads(WORKTYPE.CHECKOUT, self()));
			}
		});
		
		view.showBuildConfiguration.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				master.tell(new GetNumberOfThreads(WORKTYPE.BUILD, self()));
			}
		});
		
		view.showDeleteConfiguration.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				master.tell(new GetNumberOfThreads(WORKTYPE.DELETE, self()));
			}
		});
		
		
		view.displayLocalMaster.addActionListener(new ActionListener(){

			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFrame frame = new JFrame();
				frame.setSize(600,80);
				frame.setTitle("Remote Information");
				JTextField txt = new JTextField();
				txt.setText(localAddress+"/"+master.path().toString().replace(master.path().root().toString(),""));
				frame.add(txt);
				frame.setVisible(true);
				
			}});
		
		view.createRemoteMaster.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String input = JOptionPane.showInputDialog(null, "Please Enter the Remote Master Address");
				if (input == null)
				{
					return;
				}
				ActorRef new_actor = getContext().actorFor(input);
				Timeout timeout = new Timeout(Duration.create(2, "seconds"));
				Future<Object> future = Patterns.ask(new_actor, "Hello", timeout);
				try {
					String result = (String) Await.result(future, timeout.duration());
					JOptionPane.showMessageDialog(null, result);
					newMaster(new_actor);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Response timed out");
				}
				

			}
		});
		
		view.addWindowListener(new WindowAdapter() {
			@Override
            public void windowClosing(WindowEvent we) {
               master.tell(new RemoveAbstractThreadActoreWatcher(localAddress+"", taskController.path(), taskController));
            }
		});
		
		view.selectedDatesPanel.selectedDatesTablePanel.clearAll.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				selectorController.tell(new RemoveAllDates());
			}
		});
		
		view.selectedDatesPanel.selectedDatesTablePanel.clearSelected.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int[] selectedRows = view.selectedDatesPanel.selectedDatesTablePanel.table.getSelectedRows();
				List<SelectedDate> selectedDate = new ArrayList<SelectedDate>();
				for (int row : selectedRows)
				{
					selectedDate.add((SelectedDate)view.selectedDatesPanel.selectedDatesTablePanel.table.getValueAt(row, 3));
				}
				selectorController.tell(new RemoveSelectedDatesMessage(selectedDate));
				
				// TODO refresh view
			}
		});
		
		newMaster(master);

	}
	
	public void newMaster(ActorRef master)
	{
		master.tell(new RemoveAbstractThreadActoreWatcher(localAddress+"", taskController.path(), taskController));
		this.master = master;
		selectorController.tell(new SetMaster(master));
		master.tell(new AddAbstractThreadActoreWatcher(localAddress+"", taskController.path(), taskController));
	}
	
	public static class SetVisible {}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof SetVisible)
		{
			GUITest.showComponent(view);
		}
		else if (msg instanceof GetNumberOfThreadsResult)
		{
			GetNumberOfThreadsResult gnotr = (GetNumberOfThreadsResult) msg;
			String message = "max size: "+gnotr.maxSize+ "\n current active: "+gnotr.noThreads + "\n type: "+gnotr.type+"\n core size: "+gnotr.corSize;
			JOptionPane.showMessageDialog(null, message);
		}
		else if (msg instanceof CVSUsernameConfigResult)
		{
			CVSUsernameConfigResult gnotr = (CVSUsernameConfigResult) msg;
			String message = "CVS USername "+gnotr.result;
			JOptionPane.showMessageDialog(null, message);
		}
		
	}


}
