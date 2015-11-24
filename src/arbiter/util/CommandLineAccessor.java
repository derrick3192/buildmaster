package arbiter.util;


import java.io.File;
import java.io.IOException;


// https://github.com/brettwooldridge/NuProcess
public class CommandLineAccessor
{

	private static volatile int 				m_count = 0;
	private final static CommandLineAccessor 	m_instance = new CommandLineAccessor();
	
	public static void executeBatch(String[] arguements)
	{
		m_instance.executeBatchInstance(arguements);
	}
	
	// http://www.javacreed.com/running-a-batch-file-with-processbuilder/
	public void executeBatchInstance(String[] arguements)
	{

		final ProcessBuilder processBuilder = new ProcessBuilder(arguements);
		File outputFile;
		Process process = null;
		
		synchronized(this)
		{
			processBuilder.redirectErrorStream(true);
			outputFile = new File("src\\output"+m_count+".txt");
			m_count++;
			processBuilder.redirectOutput(outputFile);
		}
		 
		try
		{
			process = processBuilder.start();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		int exitStatus = 0;
		try
		{
			exitStatus = process.waitFor();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println("Processed finished with status: " + exitStatus);
	}
}
