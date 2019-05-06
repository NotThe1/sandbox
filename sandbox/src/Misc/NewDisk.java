package Misc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class NewDisk {
	public static String makeNewDisk() {

		String fileExtension = "F3HD";
		JFileChooser fc = new JFileChooser("C:\\Users\\admin\\VMdata\\Disks");
		if (fc.showOpenDialog(null) == JFileChooser.CANCEL_OPTION) {
			System.out.println("Bailed out of the open");
			return null;
		} // if

		// File pickedFile = fc.getSelectedFile();

		String targetRawAbsoluteFileName = fc.getSelectedFile().getAbsolutePath();
		String[] fileNameComponents = targetRawAbsoluteFileName.split("\\.");
		String targetAbsoluteFileName = fileNameComponents[0] + "." + fileExtension;

		// File selectedFile = new File(targetAbsoluteFileName);
		if (Files.exists(Paths.get(targetAbsoluteFileName))) {
			if (JOptionPane.showConfirmDialog(null, "File already exists do you want to overwrite it?",
					"YES - Continue, NO - Cancel", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
				return null;
			} else {
				try {
					Files.delete(Paths.get(targetAbsoluteFileName));
//					Files.createFile(Paths.get(targetAbsoluteFileName));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} // inner if
		} // if - file exists

		try (FileChannel fileChannel = new RandomAccessFile(targetAbsoluteFileName, "rws").getChannel();) {
			MappedByteBuffer disk = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 51200);
//
//			ByteBuffer sector = ByteBuffer.allocate(512);
//			int sectorCount = 0;
//			while (disk.hasRemaining()) {
//				sector = setUpBuffer(sector, sectorCount++);
//				disk.put(sector);
//			} // while

			fileChannel.force(true);
			fileChannel.close();
			disk.force();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return targetAbsoluteFileName;
	}// makeNewDisk

//	private static ByteBuffer setUpBuffer(ByteBuffer sector, int value) {
//		sector.clear();
//		// set value to be put into sector
//		Byte byteValue = (byte) 0x00; // default to null
//		Byte MTfileVlaue = (byte) 0xE5; // deleted file value
//		Byte workingValue;
//		while (sector.hasRemaining()) {
//			workingValue = ((sector.position() % 0x20) == 0) ? MTfileVlaue : byteValue;
//			sector.put(workingValue);
//		} // while
//		sector.flip();
//		return sector;
//	}// setUpBuffer

}
