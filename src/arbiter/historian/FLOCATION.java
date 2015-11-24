package arbiter.historian;



public enum FLOCATION {
	
	PLUGINS("build_plugins.properties"),
	TOOLSTUDIO("build_toolstudio.properties");
	

	FLOCATION(String fname) {
		this.fname = fname;
	}

	//public final String path;
	public final String fname;
	
}
