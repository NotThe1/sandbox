package robot;

import java.awt.AWTException;
import java.io.IOException;

public class AutoImport {

	public AutoImport() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args)throws IOException, 
	AWTException, InterruptedException  {
		String command = "java -jar \"C:\\My\\DiskUtility.jar\""; 
		Runtime run = Runtime.getRuntime(); 
		run.exec(command); 
		try { 
			Thread.sleep(2000); 
		} 
		catch (InterruptedException e) 
		{ 
			// TODO Auto-generated catch block 
			e.printStackTrace(); 
		} 

	}

}
