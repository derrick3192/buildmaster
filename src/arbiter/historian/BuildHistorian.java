package arbiter.historian;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import arbiter.BuildMaster;
import arbiter.task.TaskStatus;

public class BuildHistorian  {
	protected static final BuildHistorian instance = new BuildHistorian("build_plugins.properties");

	protected final String file_name;
	protected Properties properties = null;

	protected BuildHistorian(String file_name) {
		this.file_name = file_name;
	}
	
	String getFullFilePath()
	{
		return BuildMaster.getPath() + "\\" + file_name;
	}

	public static BuildHistorian getInstace() {
		synchronized (BuildHistorian.class) {
			return instance;
		}
	}

	public static boolean recordResult(String date, HistroialStatus result) {
		return getInstace().recordPluginsResultS(date, result);
	}

	public static HistroialStatus getResult(String date) {
		return getInstace().getResultS(date);
	}

	private boolean recordPluginsResultS(String date, HistroialStatus result) {
		try {
			getProperties().setProperty(date, result.toString());
			FileOutputStream output = new FileOutputStream(getFullFilePath());
			getProperties().store(output, "Machine Generated Do not Touch!");
			output.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private HistroialStatus getResultS(String date) {
		try {
			//date = date.replace(" ", "_");
			System.out.println(date);
			String result = getProperties().getProperty(date, "n");
			HistroialStatus status = HistroialStatus.historicalStatus(result
					.charAt(0));
			return status;
		} catch (Exception e) {
			return HistroialStatus.NOINFO;
		}
	}

	private Properties getProperties() {
		if (properties != null) {
			return properties;
		} else {
			if (!mkFileIfNotExists(mkDirIfNotExists())) {
				return null;
			} else {
				try {
					File file = new File(getFullFilePath());
					properties = new Properties();
					properties.load(new FileInputStream(file));
					return properties;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
	}

	private boolean mkDirIfNotExists() {
		if (!new File(BuildMaster.getPath()).isDirectory()) {
			new File(BuildMaster.getPath()).mkdir();
		}
		return new File(BuildMaster.getPath()).isDirectory();
	}

	private boolean mkFileIfNotExists(boolean directoryExists) {

		File file = new File(getFullFilePath());
		if (!directoryExists) {
			try {
				file.mkdir();
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			} else {
				// Don't need to do anything if the file and the directory exist
			}
		}
		System.out.println(new File(getFullFilePath()).exists());
		return new File(getFullFilePath()).exists();
	}


	public static void recordResult(String cvsDate, TaskStatus status) {
		switch (status) {
		case COMPLETED:
			recordResult(cvsDate, HistroialStatus.PASS);
			break;
		case NOT_NEEDED:
			recordResult(cvsDate, HistroialStatus.PASS);
			break;
		case FAILED:
			recordResult(cvsDate, HistroialStatus.FAIL);
			break;
		default:
			break;
		}
	}
}
