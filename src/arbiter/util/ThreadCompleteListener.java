package arbiter.util;



public interface ThreadCompleteListener
{
    void notifiedOfThreadCompleted(NotifyingThread  task);
}