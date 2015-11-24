package arbiter.util;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class DateUtil
{
	
	public static String ARBITER_ROOT="C:\\Arbiter";
	
	private static final String m_sep = "_";
	
	private final static int SUNDAY = 1;
	private final static int SATURDAY = 7;
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy");

	public static String getFolderName(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String date_string = sdf.format(date);
		String[] date_array = date_string.toString().split("/");

		if (date_array[1].length() < 2)
		{
			date_array[1] = "0" + date_array[1];
		}
		
		if (date_array[2].length() < 2)
		{
			date_array[2] = "0" + date_array[2];
		}

		String src_dir_name = "Src_Plugins" + m_sep + date_array[0] + m_sep
				+ date_array[1] + m_sep + date_array[2]+"_DNBU";

		return src_dir_name;
	}
	
	public static String getDateFormatted(Date date)
	{
		return dateFormat.format(date);
	}
	

	
	@SuppressWarnings("deprecation")
	public static String getCVSDate(Date date)
	{
		return new SimpleDateFormat("dd").format(date)
		+ " "+ new DateFormatSymbols().getMonths()[date.getMonth()].substring(0, 3)
		+ " "+ new SimpleDateFormat("yyyy").format(date);
	}
	
	
	 public static List<Date> allDaysBetween(Date fechaInicial, Date fechaFinal)
	 {
	     List<Date> dates = new ArrayList<Date>();
	     Calendar calendar = new GregorianCalendar();
	     calendar.setTime(fechaInicial);

	     while (calendar.getTime().before(fechaFinal))
	     {
	         Date resultado = calendar.getTime();
	         dates.add(resultado);
	         calendar.add(Calendar.DATE, 1);
	     }
	     dates.add(fechaFinal);
	     return dates;
	 }
	 
	 public static List<Date> allWeekDaysBetween(Date start_date, Date end_date)
	 {
		 List<Date> all_dates = allDaysBetween(start_date, end_date);
		 List<Date> week_dates = new ArrayList<Date>();
		 for (Date date : all_dates)
		 {
			 if (!(getDay(date) == SUNDAY || 
			     getDay(date) == SATURDAY))	
			 {
				 week_dates.add(date);
			 }
		 }
		 return week_dates;
	 }
	 
	 
	public static int getDay(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	 
	public static List<Date> allWeekDaysBetween(Date start_date, Date end_date, int no)
	{
		
		if (start_date.after(end_date))
		{
			return allWeekDaysBetween(end_date, start_date, no);
		}
		
		if (no < 1)
		{
			return new ArrayList<Date>();
		}
		
		if (no == 1)
		{
			List<Date> small_list = new ArrayList<Date>();
			small_list.add(start_date);
			return small_list;
		}
		
		if (no == 2)
		{
			List<Date> small_list = new ArrayList<Date>();
			small_list.add(start_date);
			small_list.add(end_date);
			return small_list;
		}
		
		List<Date> all_week_dates = allDaysBetween(start_date, end_date);
		
		if (no >= all_week_dates.size())
		{
			return all_week_dates;
		}
		
		List<Date> new_week_days = new ArrayList<Date>();
		
		new_week_days.add(all_week_dates.get(0));
		
		int no_to_devide = all_week_dates.size() - 2; // start date and end get included
		int no_to_select = no - 2;
		int divisble = no_to_devide / no_to_select;
		for (int i = 1; i < all_week_dates.size()-1; i++)
		{
			if (i % divisble == 0)
			{
				new_week_days.add(all_week_dates.get(i));
			}
		}
		
		new_week_days.add(all_week_dates.get(all_week_dates.size()-1));
		Collections.sort(new_week_days);
		
		if (new_week_days.size() > no)
		{
			new_week_days.remove(no/2);
		}
		
		return new_week_days;
	}
	
	public static Date tryParseInts(int day, int month, int year)
	{
		try
		{
			String day_s = String.format("%02d", day);
			String month_s = String.format("%02d", month);
			
			return dateFormat.parse(day_s+"_"+month_s+"_"+year);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static void main(String[] args) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		Date d1 = sdf.parse("01-01-2014"); // Mon
		Date d2 = sdf.parse("29-06-2014"); // Sun
		
		List<Date> dates = allWeekDaysBetween(d1,d2,20);
		
		for (Date date : dates)
		{
			System.out.println(date);
		}
		System.out.println(dates.size());
		
		System.out.println(tryParseInts(12,12,2012));
	}

	
	
	public static String dayInts(Date date)	     			{ return new SimpleDateFormat("dd").format(date); }
	public static String monthInts(Date date)					{ return new SimpleDateFormat("MM").format(date); }
	public static String yearInts(Date date)					{ return new SimpleDateFormat("yyyy").format(date); }
	
	@SuppressWarnings("deprecation")
	public static String monthChars(Date date)
	{
		return new DateFormatSymbols().getMonths()[date.getMonth()]
				.substring(0, 3);
	}
	
	public static String fullFolderPath(Date date)
	{
		return ARBITER_ROOT+"\\"+getFolderName(date);
	}
	
	
	
	
	
	
}
