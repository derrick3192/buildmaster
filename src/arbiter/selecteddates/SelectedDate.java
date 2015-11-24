package arbiter.selecteddates;

import java.text.SimpleDateFormat;
import java.util.Date;

import arbiter.util.DateUtil;

public class SelectedDate implements Comparable<SelectedDate> {

	public final int day;
	public final int month;
	public final int year;
	
	SimpleDateFormat m_sdf = new SimpleDateFormat("dd/M/yyyy");
	
	public SelectedDate()
	{
		this(new Date());
	}
	
	public SelectedDate(Date date)
	{
		this(
				dayInts(date),
				monthInts(date),
				yearInts(date)
				);
	}
	
	public Date getDate()
	{
		return DateUtil.tryParseInts(day,month,year);
	}
	
	
	public SelectedDate(int day, int month, int year)
	{
		super();
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	private static final SimpleDateFormat df = new SimpleDateFormat("dd");
	private static final SimpleDateFormat mf = new SimpleDateFormat("MM");
	private static final SimpleDateFormat yf = new SimpleDateFormat("yyyy");
	
	public static int dayInts   (Date date) 	{ return Integer.parseInt(df.format(date));}
	public static int monthInts (Date date) 	{ return Integer.parseInt(mf.format(date));}
	public static int yearInts  (Date date) 	{ return Integer.parseInt(yf.format(date)); }
	
	

	
	@Override
	public String toString()
	{
		String m = null;
		
		if (month < 10)
		{
			m = "0"+month;
		}
		else
		{
			m = month + "";
		}
		
		return day  + "/" + m + "/" + year;
	}
	
	public String getCVSDate()
	{
		return DateUtil.getCVSDate(getDate());
	}

	public static void main(String[] args) {
		System.out.println(new SelectedDate());
	}

	@Override
	public int compareTo(SelectedDate arg0) {
		return this.getDate().compareTo(arg0.getDate());
	}
	
}
