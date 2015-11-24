package arbiter.selecteddates.gui.dialog;

import java.util.Date;

public class DialogDateSelectorSettings
{
	Date 		m_from;
	Date 		m_till;
	int 		m_no_builds;
	boolean 	m_include_weekend;
	
	public Date getFrom()				{ return m_from;	}
	public Date getTill()				{ return m_till;	}
	public int getNoBuilds()			{ return m_no_builds;	}
	public boolean isIncludeWeekend()	{ return m_include_weekend;	}
	
	public DialogDateSelectorSettings(      	 Date from,
												 Date till,
												 int noBuilds,
												 boolean includeWeekend)
	{
		super();
		this.m_from = from;
		this.m_till = till;
		this.m_no_builds = noBuilds;
		this.m_include_weekend = includeWeekend;
	}

}
