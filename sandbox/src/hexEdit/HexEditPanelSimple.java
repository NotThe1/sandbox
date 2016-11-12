package hexEdit;

import java.nio.ByteBuffer;
import java.util.SortedMap;

public class HexEditPanelSimple extends HexEditPanelBase {
	
	private static final long serialVersionUID = 1L;

	//---------------------------------------------------------------
	public void loadData(Object src){
		this.source = ByteBuffer.wrap((byte[])src);
		changes.clear();
		setUpScrollBar();

		int srcSize = source.limit();	//sourceArray.length;
		currentLineStart = 0;
		prepareDoc(doc, (long) srcSize);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				fillPane();
			}// run
		});

		calcHexMetrics(srcSize);
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
