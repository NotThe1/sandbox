package hexEdit;

import java.nio.ByteBuffer;
import java.util.SortedMap;

public class HexEditPanelSimple extends HexEditPanelBase {
	
	private static final long serialVersionUID = 1L;

	//---------------------------------------------------------------
	public void loadData(byte[] sourceArray) {
		changes.clear();
		this.source = ByteBuffer.wrap(sourceArray);

		setUpScrollBar();

		int srcSize = sourceArray.length;
		currentLineStart = 0;
		prepareDoc(doc, (long) srcSize);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				fillPane();
			}// run
		});

		calcHexMetrics(sourceArray.length);
		setDocumentFilter(doc);
		setNavigationFilter(doc);
	}// loadDocument
		
	public byte[] unloadData(){
		return applyChanges(source.array(), source.limit(), 0);
	}//unloadDocument
	
	public boolean isDataChanged(){
		return !changes.isEmpty();
	}//isDataChanges
	
	public SortedMap<Integer, Byte> getChangedData(){
		return changes;
	}//getChangedData

}//class hexEditPanelSimple
