package hexEdit;

import java.nio.ByteBuffer;

public class HexEditPanelConcurrent extends HexEditPanelBase implements HexSourceChangeListener{
	
	private static final long serialVersionUID = 1L;
	private byte[] sourceArray;

	//---------------------------------------------------------------
	
	public void loadData(Object src){
		this.sourceArray = (byte[])src;

		changes.clear();
		this.source = ByteBuffer.wrap(sourceArray);

		setUpScrollBar();

		int srcSize = source.limit();
		currentLineStart = 0;
		prepareDoc(doc, (long) srcSize);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				fillPane();
			}// run
		});

		calcHexMetrics(srcSize);
		HexEditDocumentFilter hexFilter = setDocumentFilter(doc);
		hexFilter.addHexSourceChangeListener(this);
		setNavigationFilter(doc);
	}// loadDocument
		

//	public SortedMap<Integer, Byte> getChangedData(){
//		return changes;
//	}//getChangedData
	
	@Override
	public void dataChanged(HexSourceChangeEvent hexSourceChangeEvent) {
		int location = hexSourceChangeEvent.getLocation();
		byte value = hexSourceChangeEvent.getValue();
		System.out.printf("[] location = %04X, value = %02X%n", location,value);
		sourceArray[location] = value;
		
	}//dataChanged


}//class hexEditPanelSimple
