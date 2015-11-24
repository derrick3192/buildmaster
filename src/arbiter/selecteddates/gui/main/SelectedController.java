package arbiter.selecteddates.gui.main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import akka.dispatch.Await;
import akka.dispatch.Future;
import akka.pattern.Patterns;
import akka.util.Duration;
import akka.util.Timeout;

import arbiter.GUITest;
import arbiter.bat.BATBUILD;
import arbiter.bat.BATCHECKOUT;
import arbiter.bat.BATDELETE;
import arbiter.selecteddates.SelectedDate;
import arbiter.selecteddates.actor.AddSelectedDatesListinerActorMessage;
import arbiter.selecteddates.actor.CurrentSelectedDatesResponse;
import arbiter.selecteddates.actor.RemoveAllDates;
import arbiter.selecteddates.actor.RemoveSelectedDatesMessage;
import arbiter.selecteddates.actor.RequestSelectedDatesMessage;
import arbiter.selecteddates.actor.SelectedDatesContainerActor;
import arbiter.selecteddates.actor.AddDatesMessage;
import arbiter.selecteddates.gui.SelectorComponent;
import arbiter.selecteddates.gui.table.SelectedDatesTableModel;
import arbiter.task.CheckoutBuildDeleteTask;

public class SelectedController extends UntypedActor {

	ActorRef master = null;
	private final SelectedDatesPanel view;
	private final SelectedDatesTableModel tableModel;
	public final ActorRef selectedActor;

	public SelectedController(ActorRef master, SelectedDatesPanel view) {
		this(view);
		this.master = master;
	}

	public static class SetVisibleMessage {

	}
	


	public SelectedController(SelectedDatesPanel view) {
		this.view = view;
		
		// sorter and model
		this.tableModel = new SelectedDatesTableModel();
		TableRowSorter<TableModel> sorter 
	    = new TableRowSorter<TableModel>(tableModel);
		this.view.selectedDatesTablePanel.table.setModel(tableModel);
		this.view.selectedDatesTablePanel.table.setRowSorter(sorter);
		// end of sorter and table model

		selectedActor = this.getContext().actorOf(
				new Props(new UntypedActorFactory() {
					private static final long serialVersionUID = 1L;

					@Override
					public Actor create() {
						return new SelectedDatesContainerActor();
					}
				}), "selecteddatescontaineractor");

		// add the date action listiners
		for (SelectorComponent comp : this.view.selector_components.values()) {
			comp.addSelectedDatesBtnListiner(new SelectedDatesBtnListiner(comp
					.getComponentName()));
			comp.addRemoveSelectedDateListiner(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (e instanceof RemoveDateActionEvent) {
						RemoveDateActionEvent rdae = (RemoveDateActionEvent) e;
						self().tell(rdae);
					}
				}

			});
		}

		// add the controller as a listiner
		AddSelectedDatesListinerActorMessage asdlam = new AddSelectedDatesListinerActorMessage(
				getSelf());
		selectedActor.tell(asdlam);

		// schedule view updates
		
		
		/**
		this.getContext()
				.system()
				.scheduler()
				.schedule(Duration.Zero(),
						Duration.create(2, TimeUnit.SECONDS), new Runnable() {

							@Override
							public void run() {
								self().tell(new UPDATEUI());
							}
						});
		 **/
		
		// add the button listiner
		this.view.selectedDatesTablePanel.btnSendToMaster
				.addActionListener(new CreateTasksActionListiner());

	}

	class UPDATEUI {
	}

	class SelectedDatesBtnListiner implements ActionListener {

		final String comp;

		public SelectedDatesBtnListiner(String comp) {
			this.comp = comp;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			self().tell(new AddSelectedDatesRequest(comp));
		}
	}

	
	
	private List<SelectedDate> getSelectedDates()
	{
		Timeout timeout = new Timeout(Duration.create(2, "seconds"));
		Future<Object> selectedDatesFuture = Patterns.ask(selectedActor,
				new RequestSelectedDatesMessage(), timeout);
		CurrentSelectedDatesResponse sdm;
		try {
			sdm = (CurrentSelectedDatesResponse) Await
					.result(selectedDatesFuture, timeout.duration());
			return sdm.dates;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Override
	public void onReceive(Object msg) throws Exception {
		
		
		if (msg instanceof UpdateAllGUI)
		{
			updateAllGui(getSelectedDates());
		}
		
		if (msg instanceof AddSelectedDatesRequest) {
			AddSelectedDatesRequest gsdr = (AddSelectedDatesRequest) msg;
			
			SelectorComponent component = this.view.selector_components
					.get(gsdr.message);
			Collection<SelectedDate> dates = component.getSelectedDates();
			selectedActor.tell(new AddDatesMessage(dates));

			updateAllGui(getSelectedDates());
		}

		if (msg instanceof SetVisibleMessage) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					GUITest.showComponent(view);
				}
			});
		}

		if (msg instanceof RemoveDateActionEvent) {
			RemoveDateActionEvent rdae = (RemoveDateActionEvent) msg;
			
			List<SelectedDate> l = new ArrayList<SelectedDate>();
			l.add(rdae.date);
			RemoveSelectedDatesMessage rsdm = new RemoveSelectedDatesMessage(l);
			selectedActor.tell(rsdm);

			// update the componentes
			updateAllGui(getSelectedDates());
		}

		if (msg instanceof UPDATEUI) {
			// trick to get the stupid view to refresh
			Dimension size = this.view.getSize();
			size.height += 1;
			this.view.setSize(size);
			size.height -= 1;
			this.view.setSize(size);
			System.out.println("revalidate");
		}
		
		if (msg instanceof RemoveAllDates)
		{
			@SuppressWarnings("unused")
			RemoveAllDates rdae = (RemoveAllDates) msg;
			selectedActor.tell(new RemoveAllDates());
			updateAllGui(new ArrayList<SelectedDate>());
		}
		
		if (msg instanceof RemoveSelectedDatesMessage)
		{
			System.out.println("messaged was told");
			selectedActor.tell(msg);
			updateAllGui(this.getSelectedDates());
		}
		
		if (msg instanceof SetMaster)
		{
			this.master = ((SetMaster)msg).ref;
		}

	}

	private void updateAllGui(final Collection<SelectedDate> dates) {

		// Table Code
		List<SelectedDate> dateslist = new ArrayList<SelectedDate>();
		for (SelectedDate d : dates) {
			dateslist.add(d);
		}
		this.tableModel.updateSelection(dateslist);
		this.view.selectedDatesTablePanel.table.updateUI();
		// end of table code

		for (final SelectorComponent comp : this.view.selector_components
				.values()) {
			javax.swing.SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					comp.updateSelectedDates(dates);
				}
			});
		}
		this.view.revalidate();
	}

	class CreateTasksActionListiner implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			if (master == null) {
				JOptionPane.showMessageDialog(null,
						"Error, There is no master to perform the builds. Please configure"
								+ " a remote or local master in the menu bar.");
				return;
			}

			BATCHECKOUT checkout = (BATCHECKOUT) view.selectedDatesTablePanel.m_combo_checkout
					.getSelectedItem();
			BATBUILD build = (BATBUILD) view.selectedDatesTablePanel.m_combo_build
					.getSelectedItem();
			BATDELETE delete = (BATDELETE) view.selectedDatesTablePanel.m_combo_delete
					.getSelectedItem();

			Timeout timeout = new Timeout(Duration.create(2, "seconds"));
			Future<Object> selectedDatesFuture = Patterns.ask(selectedActor,
					new RequestSelectedDatesMessage(), timeout);
			try {
				CurrentSelectedDatesResponse sdm = (CurrentSelectedDatesResponse) Await
						.result(selectedDatesFuture, timeout.duration());
				for (SelectedDate d : sdm.dates) {
					master.tell(new CheckoutBuildDeleteTask(master,
							d.getDate(), checkout, build, delete),self());
				}

				selectedActor.tell(new RemoveAllDates());
				updateAllGui(new ArrayList<SelectedDate>());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		System.out.println("start");

		ActorSystem system = ActorSystem.create("build");

		final ActorRef someActor = system.actorOf(new Props(
				new UntypedActorFactory() {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public Actor create() {
						return new SelectedController(new SelectedDatesPanel());
					}
				}), "listener");

		someActor.tell(new SetVisibleMessage());
	}

}
