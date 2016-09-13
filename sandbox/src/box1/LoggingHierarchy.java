package box1;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class LoggingHierarchy {
	static Logger logger;
	static Logger logger1;
	static Logger logger1_2;

	public LoggingHierarchy() {
	}//Constructor

	public static void main(String[] args) {
		logger = Logger.getLogger("");
		logger1 = Logger.getLogger("1");
		logger1_2 = Logger.getLogger("1.2");
		
		logger1.addHandler(new ConsoleHandler());
		logger1_2.addHandler(new ConsoleHandler());
		
		logger.info("msg: \n");
		logger1.info("msg: 1 \n");
		logger.info("msg: 1.2 \n");
		
		// TODO Auto-generated method stub

	}//main

}//class LoggingHierarchy
