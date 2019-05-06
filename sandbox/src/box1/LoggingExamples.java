package box1;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;


//http://tutorials.jenkov.com/java-logging/

public class LoggingExamples {
	private static final Logger logger = Logger.getLogger(LoggingExamples.class.getName());
	private static final String CR = "\n";

	public LoggingExamples() {
		// TODO Auto-generated constructor stub
	}// Constructor

	public static void main(String[] args) {
		new LoggingExamples().doIt();
	}// main

	public void doIt() {
		try {
			FileHandler fh = new FileHandler("%hTestLogger%g.log", 1024, 10, true);
			logger.addHandler(fh);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Filter filter = new MyFilterImpl(); // make your own using the "Filter" interface
		logger.info("loger level is" + logger.getLevel());
		logger.info("\nlogerparent is " + logger.getParent());
		logger.entering(getClass().getName(), "doIt");
		Handler[] handlers = logger.getHandlers();
		logger.info("there are " + handlers.length + " handler(s)\n\n");

		logger.log(Level.SEVERE, "Hello logging:" + CR);
		logger.log(Level.SEVERE, "Hello logging: {0}\n", "P1");
		logger.log(Level.SEVERE, "Hello logging: {0}, {1}\n", new Object[] { "P1", "P2" });

		logger.log(Level.SEVERE, "Hello logging", new RuntimeException("My Bad"));

		// String[] dummy = new String[] {"A","B"};
		// try {
		// String ans = dummy[9];
		// } catch (Exception e) {
		// logger.log(Level.SEVERE, "Error reading a string array", e);
		// }//try

		logger.exiting(getClass().getName(), "doIt");

	}// doIt

}// class LoggingExamples
