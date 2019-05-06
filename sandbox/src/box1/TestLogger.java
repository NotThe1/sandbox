package box1;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestLogger {
	private static final String CR = "\n";

	public TestLogger() {
		// TODO Auto-generated constructor stub
	}//Constructor

	public static void main(String[] args) {
		new TestLogger().doIt();

	}//main
	private void doIt(){
		String loggerName = "Fred";		// DocTest.class.getName()
//		String loggerName =  DocTest.class.getName();
		
		Logger logger = Logger.getLogger(loggerName);
//		try {
//			FileHandler fh = new FileHandler("C:\\temp\\fh.log");
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		
//		logger.addHandler(new ConsoleHandler());
		
		logger.log(Level.INFO, "The name of the logger is " + loggerName + CR);
		logger.warning("this one uses logger.warning" + CR);
		logger.logp(Level.INFO, "Cheese", "ratso", "Messge" + CR);
//		logger.log(Level.WARNING, �This is a warning!�);
	}

}//class TestLogger
