package sandbox;

import java.util.concurrent.TimeUnit;

import hardware.Core;
import hardware.MemoryTrapEvent;
import hardware.MemoryTrapListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class QuickTest1 implements MemoryTrapListener {
	Core core;
	int memSize;
	//DiskControlUnit dcu;

	Logger log;
	int currentLocation;
	int controlTableLocation = 0X0100;
	int controlByteLocation = 0X0040;

	public static void main(String[] args) {
		// new QuickTest().doTrapTest();
		//new QuickTest().doDGTest();
		//new QuickTest().doFindDisks();
//		MyAppWindow window = new MyAppWindow();
//		window.frame.setVisible(true);
//		System.out.printf("Max integer = %d - 0X%X%n",Integer.MAX_VALUE,Integer.MAX_VALUE);
		
//		NewDisk.showDialog(null, null);
//		NewDisk.close();
		int a = 3;
	}// main
	
	private void doFindDisks(){
		JFileChooser fc = new JFileChooser(".");
		int result = fc.showOpenDialog(null);
		
	}//doFindDisks

	

	public void doTrapTest() {
		initTrapTest();
		currentLocation = controlTableLocation;
		writeNextByte(FLOPPY_READ_CODE);
		writeNextByte((byte) 0X02); // unit
		writeNextByte((byte) 0X03); // head
		writeNextByte((byte) 0X04); // track
		writeNextByte((byte) 0X05); // sector
		writeNextByte((byte) 0XFF); // lo byteCount
		writeNextByte((byte) 0X00); // hi byteCount
		writeNextByte((byte) 0XC8); // lo DMA
		writeNextByte((byte) 0X00); // hi DMA

		core.write(0X0041, (byte) 00);
		core.write(0X0042, (byte) 01); // point at the controlTable
		core.write(controlByteLocation, (byte) 0X80); // Set the Control byte to
														// activate the dcu
		System.out.printf("Just set the Control Byte%n");
		System.out.printf("controlByteLocation value : %02X%n",
				core.read(controlByteLocation));
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.printf("controlByteLocation value : %02X%n",
				core.read(controlByteLocation));
		byte a = core.read(200);

	}// doIt

	private void writeNextByte(byte value) {
		core.write(currentLocation++, value);
	}//

	@Override
	public void memoryTrap(MemoryTrapEvent mte) {
		System.out.printf("in the  IO trap handler location = %04X%n",
				mte.getLocation());
		// System.out.printf( "Location 255 = %02X%n",core.read(255));
		if (mte.getTrap() == Core.TRAP.IO) {
			byte value = core.read(mte.getLocation());
			System.out.printf("in the  IO trap handler. value = %02X%n", value);

			if ((value & 0X80) != 0) {
//				DiskControlUnit00 dcu = new DiskControlUnit00(core, 0X40);
//				Thread dcuThread = new Thread(dcu);
//				dcuThread.start();
//				System.out.printf("just started dcuThread%n%n");
			}
		}
		
	}// memoryTrap

	private void initTrapTest() {
		memSize = 1024; // 32 K
		core = new Core(memSize);
		for (int i = 0; i < memSize; i++) {
			core.write(i, (byte) 0);
		}
		core.addMemoryTrapListener(this);
		core.addTrapLocation(0X0040, Core.TRAP.IO);
		Logger log = LoggerFactory.getLogger(QuickTest1.class);

	}// initApp

	static byte FLOPPY_READ_CODE = 01;
	static byte FLOPPY_WRITE_CODE = 02;

	static int DISK_CONTROL_8 = 0X40;
	static int COMMAND_BLOCK_8 = 0X41;

	static int DISK_STATUS_BLOCK = 0X43;

	static int DISK_CONTROL_5 = 0X45;
	static int COMMAND_BLOCK_5 = 0X46;

	static int FDC_COMMAND = 0X00;
	static int FDC_UNIT = 0X01;
	static int FDC_HEAD = 0X02;
	static int FDC_TRACK = 0X03;
	static int FDC_SECTOR = 0X04;

	static int FDC_SByteCount = 0X05;
	static int FDC_DMA = 0X07;

	static int FDC_NEXT_STATUS = 0X09;
	static int FDC_NEXT_CONTROL = 0X011;

}// class QuickTest
