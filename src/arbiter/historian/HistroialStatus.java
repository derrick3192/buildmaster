package arbiter.historian;

public enum HistroialStatus
{
	PASS("p","Can Build"),
	NOINFO("n","No Info"),
	FAIL("f", "Cannot Build");
	
	final String r, name;
	HistroialStatus(String r, String name)
	{
		this.r = r;
		this.name = name;
	}
	
	
	@Override
	public String toString()
	{
		return r;
	}
	
	public static HistroialStatus historicalStatus(char r)
	{
		switch(r)
		{
			case ('p'): return PASS;
			case ('n'): return NOINFO;
			case ('f'): return FAIL;
			default   : return NOINFO;
		}
	}
	
	public String getFullName()
	{
		return name;
	}
	
}
