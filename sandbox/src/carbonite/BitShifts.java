package carbonite;

public class BitShifts {

	public BitShifts() {
		// TODO Auto-generated constructor stub
	}// Constructor

	static Integer baseValue = 0XA55A;

	public static void main(String[] args) {
		int value = baseValue;
		int valueNew;
		int hiBit = 0X8000;
		System.out.printf("Shift Listing for %04X%n", baseValue);
		System.out.printf("%d\t%04X\t |%16s|%n", 0, value, Integer.toBinaryString(value));
		for (int i = 0; i < 15; i++) {
			valueNew = value >> 1;
			if (value % 2 == 1) {
				valueNew = valueNew + hiBit;
			}
			value = valueNew & 0xFFFF;
			System.out.printf("%d\t%04X\t |%16s|%n", i + 1, value, Integer.toBinaryString(value));
		} // for

		System.out.printf("%n%n Set Bit for %04X%n", baseValue);
		int theBit = 1;
		for (int i = 0; i < 16; i++) {
			value = baseValue;
			valueNew = (baseValue | theBit) & 0xFFFF;
			System.out.printf("%X\t%04X\t |%16s|%n", i, valueNew, Integer.toBinaryString(valueNew));
			theBit = 2 * theBit;
		} // for

		System.out.printf("%n%n ReSet Bit for %04X%n", baseValue);
		theBit = 1;
		for (int i = 0; i < 16; i++) {
			value = baseValue;
			valueNew = (baseValue & ~theBit) & 0xFFFF;
			System.out.printf("%X\t%04X\t |%16s|%n", i, valueNew, Integer.toBinaryString(valueNew));
			theBit = 2 * theBit;
		} // for

	}// main

}// class BitShifts
