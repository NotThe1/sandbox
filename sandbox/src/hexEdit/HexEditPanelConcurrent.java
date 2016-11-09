package hexEdit;

import java.nio.ByteBuffer;

import javax.swing.text.AbstractDocument;
import javax.swing.text.StyledDocument;

public class HexEditPanelConcurrent extends HexEditPanelBase {
	byte[] sourceArray ;

	public void loadData(byte[] sourceArray) {
		this.sourceArray = sourceArray;
		
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
	
	protected void setDocumentFilter(StyledDocument doc) {

		hexFilter = new HexEditDocumentFilter(doc, hexMetrics, changes);
		hexFilter.setAsciiAttributes(asciiAttributes);
		hexFilter.setDataAttributes(dataAttributes);
		// hexFilter = null;
		((AbstractDocument) doc).setDocumentFilter(hexFilter);
	}// setDocumentFilter

		
}//class HexEditPanelConcurrent
