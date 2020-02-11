package assemblerStuff;

import java.util.HashMap;
import java.util.Set;

public class testASCIIprintable {
		static final byte MASK = (byte) 0x7F;
	public testASCIIprintable() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {

		byte b = (byte) 0xAA;
		Set<Byte> keys = printableASCIIs.keySet();
		for(Byte key:keys) {
			System.out.printf("key: %02X, char: %s char: %s%n",
					key,printableASCIIs.get(key),printables.get(key));
		}//for
	}//main
	
	static final char PERIOD = '.';
	private static HashMap<Byte,Character> printableASCIIs;
	static {
		printableASCIIs = new HashMap<Byte,Character>();
		for( byte i =0;(byte)i<0x20;i++) {
			printableASCIIs.put(i, PERIOD);
		}// for
		
		for(byte i = 0x20;i<(byte)0x7F; i++) {
			printableASCIIs.put(i, (char)i);
		}// for
		
		for (byte i = (byte)0x80;i < (byte)0;i ++) {
			printableASCIIs.put(i, PERIOD);
		}// for
	}//static
	
		private static HashMap<Byte,Character> printables;
		static {
			printables = new HashMap<Byte,Character>();
			for( byte i =(byte)0x80;i<0x1F;i++) {
				printables.put(i, PERIOD);
			}// for
			
			for(byte i = 0x20;i<(byte)0x7F; i++) {
				printables.put(i, (char)i);
			}// for
			
	}//static

}
