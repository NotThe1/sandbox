package z80Stuff;

import java.util.BitSet;

public class Adder {
	private int size;
	private BitSet augend;
	private BitSet addend;
	private BitSet sum;
	private BitSet carryOut;
	private BitSet carryIn;

	public static Adder getByteAdder(int argument1, int argument2) {
		return new Adder(8, argument1, argument2);
	}// Factory method

	public static Adder getWordAdder(int argument1, int argument2) {
		return new Adder(16, argument1, argument2);
	}// Factory method

	private Adder(int size, int argument1, int argument2) {
		int arg1,arg2,mask;
		mask = size==8 ?byteMask:wordMask;
		arg1 = argument1 & mask;
		arg2 = argument2 & mask;
		
		this.size = size;
		this.augend = new BitSet(size);
		this.addend = new BitSet(size);
		this.sum = new BitSet(size);
		this.carryOut = new BitSet(size);
		this.carryIn = new BitSet(size);
	}// Constructor
	
	
	private int byteMask = 0XFF;
	private int wordMask = 0XFFFF;

	private int bit0 = 1;
	private int bit1 = 2;
	private int bit2 = 4;
	private int bit3 = 8;
	private int bit4 = 16;
	private int bit5 = 32;
	private int bit6 = 64;
	private int bit7 = 128;

	private int bit8 = 256;
	private int bit9 = 512;
	private int bit10 = 1024;
	private int bit11 = 2048;
	private int bit12 = 4096;
	private int bit13 = 8192;
	private int bit14 = 16384;
	private int bit15 = 32768;

	private int[] bits = new int[] { bit0, bit1, bit2, bit3, bit4, bit5, bit6, bit7, bit8, bit9, bit10, bit11, bit12,
			bit13, bit14, bit15 };

}// class Adder
