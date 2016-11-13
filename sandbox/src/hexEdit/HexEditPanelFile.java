package hexEdit;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.SortedMap;

public class HexEditPanelFile extends HexEditPanelBase {

	private static final long serialVersionUID = 1L;
	private FileChannel fileChannel;
	private MappedByteBuffer disk;

	// ---------------------------------------------------------------
	
	
	// ---------------------------------------------------------------
	public void loadData(Object src) {
		File sourceFile = (File) src;
		long sourceLength = sourceFile.length();
		
		System.out.printf("[loadData] sourceLength %,d%n", sourceLength);
		System.out.printf("[loadData] Max Value %,d  [%08X]%n", Integer.MAX_VALUE, Integer.MAX_VALUE);
		try {
			fileChannel = new RandomAccessFile(sourceFile, "rw").getChannel();
			disk = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileChannel.size());// this.totalBytesOnDisk);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// this.source = ByteBuffer.wrap((byte[])src);
		// changes.clear();
		// setUpScrollBar();
		//
		// int srcSize = source.limit(); //sourceArray.length;
		// currentLineStart = 0;
		// prepareDoc(doc, (long) srcSize);
		//
		// javax.swing.SwingUtilities.invokeLater(new Runnable() {
		// public void run() {
		// fillPane();
		// }// run
		// });
		//
		// calcHexMetrics(srcSize);
		// setDocumentFilter(doc);
		// setNavigationFilter(doc);
	}// loadDocument

	public byte[] unloadData() {
		return applyChanges(source.array(), source.limit(), 0);
	}// unloadDocument

	public boolean isDataChanged() {
		return !changes.isEmpty();
	}// isDataChanges

	public SortedMap<Integer, Byte> getChangedData() {
		return changes;
	}// getChangedData

}// class hexEditPanelSimple
