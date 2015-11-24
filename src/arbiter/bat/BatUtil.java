package arbiter.bat;

public enum BatUtil {
	
	INSTANCE;

	
	public static String getBat(String bat)
	{
		System.out.println(bat);
		return BatUtil.class.getResource(bat).getPath();
	}
	
	public static String getBat(BAT bat)
	{
		return getBat(bat.bat);
	}
	
	
	public static void main(String[] args) {

//		
		getBat("build_plugins.bat");
		
		String b = BAT.CHECKOUT_PLUGINS.bat;
		getBat("checkout_plugins.bat");
		getBat(b);
		
		
	}
	
	
}
