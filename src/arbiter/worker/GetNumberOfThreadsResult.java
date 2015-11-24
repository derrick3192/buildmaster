package arbiter.worker;

import java.io.Serializable;

import arbiter.bat.WORKTYPE;

public class GetNumberOfThreadsResult implements Serializable {


	private static final long serialVersionUID = 1L;
	GetNumberOfThreadsResult(WORKTYPE type, int activeCount, int maxSize, int corSize) {
		super();
		this.type = type;
		this.noThreads = activeCount;
		this.maxSize =maxSize;
		this.corSize = corSize;
	}
	
	public final WORKTYPE type;
	public final int noThreads;
	public final int maxSize;
	public final int corSize;
	
	
	
}
