package arbiter.util;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;




// http://stackoverflow.com/questions/702415/how-to-know-if-other-threads-have-finished

public abstract class NotifyingThread implements Runnable
{

	/** Each task maintains its own set of listiners **/
	protected final Set<ThreadCompleteListener> m_listeners = new CopyOnWriteArraySet<ThreadCompleteListener>();
	
	public void removeListiner(ThreadCompleteListener listiner)
	{
		m_listeners.remove(listiner);
	}
	
	
	/** The current status of the task, not this is a volatile variable **/
	private volatile STATUS m_status = STATUS.WAITING;

	public synchronized STATUS getStatus()
	{
		return m_status;
	}

	public synchronized void setStatus(STATUS status)
	{
		m_status = status;
	}



	public final void addListener(final ThreadCompleteListener listener)
	{
		m_listeners.add(listener);
		notifyListeners();
	}

	public final void removeListener(final ThreadCompleteListener listener)
	{
		m_listeners.remove(listener);
	}

	private void notifyListenersDecorator(STATUS status)
	{
		m_status = status;	
		notifyListeners();
	}
	

	@Override
	public final void run()
	{
		try
		{
			if (isTrivial())
			{
				notifyListenersDecorator(STATUS.NOT_NEEDED);
				return;
			}
			
			notifyListenersDecorator(STATUS.EXECUTING);
			long start_time = System.currentTimeMillis();
			doRun();
			long end_time = System.currentTimeMillis();
			long total_time = end_time - start_time;
			
			
			
			
			System.out.println();
			System.out.println();
			System.out.println("--------------");
			System.out.println("Time taken to do task: "+total_time);
			System.out.println("--------------");
			System.out.println();
			System.out.println();

			if (wasSuccessful())
			{
				notifyListenersDecorator(STATUS.COMPLETED);
			}
			else
			{
				notifyListenersDecorator(STATUS.FAILED);
				return;
			}
		}
		catch (Exception e)
		{
			notifyListenersDecorator(STATUS.FAILED);
		}
		finally
		{
			onComplete();
		}

	}
	
	/** Check if the same task has succeeded in the past. **/
	//public abstract boolean isHistoricallyImpossible();
	
	/** Record the result of the task. **/
	//public abstract void recordHistoricResult();
	
	/** Notify the listiners when a change of state in the thread occurs **/
	public void notifyListeners()
	{
		for (ThreadCompleteListener l : this.m_listeners)
		{
			l.notifiedOfThreadCompleted(this);
		}
	}
	
	/** The task itself **/
	public abstract void doRun();
	
	/** Check if the task was successful **/
	public abstract boolean wasSuccessful();
	
	/** Check if the task is already completed or needed**/
	public abstract boolean isTrivial();
	
	
	protected void onComplete(){}
	
	
	//public static final int MIN_TIME = 5 * 1000;
}