package sandbox;

import javax.swing.JOptionPane;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import sandbox.Core.TRAP;

public class QuickTest {

	public static void main(String[] args) {
		Logger log = LoggerFactory.getLogger(QuickTest.class);
		try {
			doStuff(log);
		} catch (Exception e) {
			log.error("Something happened",e);
			
		}//try
		
		
		
//		for (int i = 0; i <5 ; i++){
//			log.info("i = {}, i * i = {}",i,i*i);
//		}
//		
////		Logger log = LoggerFactory.getLogger(QuickTest.class);
////		log.info("Hello World in log");
////		waitTwoSeconds(log);
////		log.info("Done");
//	
	}// main
	
	public static void doStuff(Logger log){
		log.info("1/1 = {}",1 / 1);
		log.info("1/0 = {}", 1 / 0);
	}
	
////	private static void waitTwoSeconds(Logger log){
////		try {
////			Thread.sleep(2);
////		} catch (InterruptedException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		log.info("Slept 2 seconds");
////	}//waitTwoSeconds

}// class QuickTest
