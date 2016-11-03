package hexEdit;

import java.util.SortedMap;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.Element;
import javax.swing.text.StyledDocument;

public class HexEditDocumentFilterSB extends DocumentFilter {

	private StyledDocument doc;
	private int row; // , column, elementPosition;
	private int addressSize;

	private int[] columnTypeTable;
	private Integer[] dataToAsciiTable;
	private Integer[] sourceColumnTable;

	private AttributeSet attrData;
	private AttributeSet attrASCII;

	private HexEditMetrics hexMetrics;
	private SortedMap<Integer,Byte> changes;
	// private boolean innerFlag = false;

	public HexEditDocumentFilterSB(StyledDocument doc, HexEditMetrics hexMetrics,SortedMap<Integer,Byte> changes) {
		this.doc = doc;
		this.hexMetrics = hexMetrics;
		this.changes= changes;

		this.attrData = null;
		this.attrASCII = null;
		
		this.addressSize = hexMetrics.getAddressSize();
		appInit();

	}// Constructor

	public void setDataAttributes(AttributeSet attrData) {
		this.attrData = attrData;
	}// setDataAttributes

	public void setAsciiAttributes(AttributeSet attrASCII) {
		this.attrASCII = attrASCII;
	}// setDataAttributes

	private void appInit() {
		columnTypeTable = makeColumnTable();
		dataToAsciiTable = makeDataToAsciiTable();
		sourceColumnTable = makeSourceColumnTable();
	}// appInit

	public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr)
			throws BadLocationException {
		System.out.printf("[insertString]\tattr: %s \toffset: %4d , string: %s %n", attr.toString(), offset, string);
		fb.insertString(offset, string, attr);
	}// insertString

	public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
		System.out.printf("[remove]\toffset: %d ,length: %d, %n", offset, length);
		// Disable the delete function
	}// remove

	public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException {
		// System.out.printf("[replace]\toffset: %d ,length: %d, text: %s %n",
		// offset, length, text);

		// length of 0 equals a insert length of 1 equals a replace
		int netLength = length == 0 ? 1 : length;

		int columnPosition = getColumnPosition(offset);
		Integer newCharacterIndex = null;
		int docOffset = 0;
		Integer sourceColumn = sourceColumnTable[columnPosition];

		int columnType = columnTypeTable[columnPosition];

		byte newValue = 0;

		// System.out.printf("replace:: position: offset: %d, netLength: %d,
		// text: %s, columnType: %d, innerFlag %s%n",
		// offset, netLength, text, columnType, innerFlag);

		switch (columnType) {
		case HEX1:
		case HEX2:
			if (!isTextHex(text)) {
				return;
			} // if - want only hex characters
			text = text.toUpperCase();
			newCharacterIndex = dataToAsciiTable[columnPosition];

			fb.replace(offset, netLength, text, attrs);

			int targetOffset = (columnType == HEX1) ? offset : offset - 1;
			// String newChar = convertToPrintable(targetOffset);
			int value = getValueAtOffset(targetOffset);
			String newChar = convertToPrintable1(value);
			newValue = (byte) (value & 0XFF);
			docOffset = offset + newCharacterIndex;

			fb.replace(docOffset, 1, newChar, attrASCII);
			break;
		case ASCII:
			newCharacterIndex = dataToAsciiTable[columnPosition];

			fb.replace(offset, netLength, text, attrs);

			byte[] hexValues = text.getBytes();
			newValue = hexValues[0];
			String hexData = String.format("%02X", newValue);
			docOffset = offset + newCharacterIndex;

			fb.replace(docOffset, 2, hexData, attrData);
			break;
		case ADDR: // do nothing
		case EOL: // ignore
		case BLANK:
			return;
		default:
		}// switch - columnType
		
//		System.out.printf("[replace] hexValue: %02X", newValue);
//		System.out.printf("[replace] columnType: %d, columnPosition: %d, sourceColumn: %d%n", columnType,
//				columnPosition, sourceColumn);
//		
//		System.out.printf("[replace] sourceIndex: %02X", getSourceIndex(offset,sourceColumn));
		changes.put(getSourceIndex(offset,sourceColumn), newValue);
		
		if (changes.size() != 0) {
			System.out.printf("[replace]: %n");
			changes.forEach((k, v) -> System.out.printf("\t\tIndex = %4d, vlaue = %02X%n", k,v));
		} // if need to update
	
	}// replace
	
	private int getSourceIndex(int docPosition,int columnIndex){
		Element rowElement = doc.getParagraphElement(docPosition);
		String addressStr = null;
		try {
			 addressStr = doc.getText(rowElement.getStartOffset(), this.addressSize);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int rowAddress =Integer.valueOf(addressStr,16);
		return rowAddress + columnIndex;
	}//

	private int getColumnPosition(int offset) {
		Element rootElement = doc.getDefaultRootElement();
		row = rootElement.getElementIndex(offset);
		Element paragraphElement = rootElement.getElement(row);
		return offset - paragraphElement.getStartOffset();
	}// getColumnPosition

	private int getValueAtOffset(int offset) {
		String HexString = null;
		try {
			HexString = doc.getText(offset, 2).trim();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return Integer.valueOf(HexString, 16);
	}// getValueAtOffset

	private String convertToPrintable1(int value) {
		char[] c = new char[1];
		c[0] = (char) (value & 0XFF);
		return ((c[0] >= 0X20) && (c[0] <= 0X7F)) ? new String(c) : NON_PRINTABLE_CHAR;
	}// convertToPrintable1

	private String convertToPrintable(int offset) {
		String HexString = null;
		try {
			HexString = doc.getText(offset, 2).trim();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		Integer i = Integer.valueOf(HexString, 16);
		char[] c = new char[1];
		c[0] = (char) (i & 0XFF);
		return ((c[0] >= 0X20) && (c[0] <= 0X7F)) ? new String(c) : NON_PRINTABLE_CHAR;
	}//

	private boolean isTextHex(String text) {
		return text.matches(PATTERN_HEX);
	}// isTextHex

	private int[] makeColumnTable() {
		int[] ans = new int[hexMetrics.getAsciiEnd() + 4];
		int colPosition = 0;
		for (; colPosition < hexMetrics.getAddressEnd() + 1; colPosition++) {
			ans[colPosition] = ADDR;
		} // for Address

		for (; colPosition < hexMetrics.getDataStart(); colPosition++) {
			ans[colPosition] = BLANK;
		} // for colon and Address PAD

		// ans[colPosition++] = BLANK; // make space after colon move the cursor

		int[] dataKinds = new int[] { HEX1, HEX2, BLANK, HEX1, HEX2, BLANK, HEX1, HEX2, BLANK, HEX1, HEX2, BLANK, HEX1,
				HEX2, BLANK, HEX1, HEX2, BLANK, HEX1, HEX2, BLANK, HEX1, HEX2, BLANK, BLANK, HEX1, HEX2, BLANK, HEX1,
				HEX2, BLANK, HEX1, HEX2, BLANK, HEX1, HEX2, BLANK, HEX1, HEX2, BLANK, HEX1, HEX2, BLANK, HEX1, HEX2,
				BLANK, HEX1, HEX2, };
		System.arraycopy(dataKinds, 0, ans, colPosition, dataKinds.length);
		colPosition += dataKinds.length;

		for (; colPosition < hexMetrics.getAsciiStart(); colPosition++) {
			ans[colPosition] = BLANK;
		} // for Data PAD

		int[] asciiKinds = new int[] { ASCII, ASCII, ASCII, ASCII, ASCII, ASCII, ASCII, ASCII, ASCII, ASCII, ASCII,
				ASCII, ASCII, ASCII, ASCII, ASCII, EOL, EOL };
		System.arraycopy(asciiKinds, 0, ans, colPosition, asciiKinds.length);

		return ans;
	}// makeColumnTable

	private Integer[] makeDataToAsciiTable() {
		Integer[] ans = new Integer[hexMetrics.getAsciiEnd() + 2];
		int colPosition = 0;
		for (; colPosition < hexMetrics.getAddressBlockEnd(); colPosition++) {
			ans[colPosition] = null;
		} // for Address

		int gap = hexMetrics.getDataBlockEnd() - hexMetrics.getDataStart() + 1;

		Integer[] dataToASCII = new Integer[] { null, gap - 0, gap - 1, null, gap - 2, gap - 3, null, gap - 4, gap - 5,
				null, gap - 6, gap - 7, null, gap - 8, gap - 9, null, gap - 10, gap - 11, null, gap - 12, gap - 13,
				null, gap - 14, gap - 15, null, null, gap - 17, gap - 18, null, gap - 19, gap - 20, null, gap - 21,
				gap - 22, null, gap - 23, gap - 24, null, gap - 25, gap - 26, null, gap - 27, gap - 28, null, gap - 29,
				gap - 30, null, gap - 31, gap - 32, null };

		for (int i = 0; i < dataToASCII.length - 1; i++) {
			ans[i + colPosition] = dataToASCII[i];
		} // for dataToASCII
		colPosition += dataToASCII.length;

		// Integer[] asciiToData = new Integer[] { null, null, -51, -49, -47,
		// -45, -43, -41, -39, -37,
		// -34, -32, -30, -28, -26, -24, -22, -20 };

		Integer[] asciiToData = new Integer[] { null, null, -51, -49, -47, -45, -43, -41, -39, -37, -34, -32, -30, -28,
				-26, -24, -22, -20 };

		// gap = gap-1;
		// Integer[] asciiToData = new Integer[] { null, null, -(gap+1),
		// -(gap-2), -(gap-3), -(gap-5), -(gap-7),
		// -(gap-9), -(gap-11), -(gap-13), -(gap-16), -(gap-18), -(gap-20),
		// -(gap-22), -(gap-24), -(gap-26),
		// -(gap-28), -(gap-30) };

		for (int j = 0; j < asciiToData.length; j++) {
			ans[j + colPosition] = asciiToData[j];
		} // for asciiToData

		return ans;
	}// makeDataToAsciiTable

	private Integer[] makeSourceColumnTable() {

		Integer[] ans = new Integer[] { null, null, null, null, null, null, null, 0, 0, null, 1, 1, null, 2, 2, null, 3,
				3, null, 4, 4, null, 5, 5, null, 6, 6, null, 7, 7, null, null, 8, 8, null, 9, 9, null, 10, 10, null, 11,
				11, null, 12, 12, null, 13, 13, null, 14, 14, null, 15, 15, null, null, null, 0, 1, 2, 3, 4, 5, 6, 7, 8,
				9, 10, 11, 12, 13, 14, 15, null, null };
		return ans;

	}

	private final static String PATTERN_HEX = "[A-F|a-f|0-9]+";
	// private static String PATTERN_PRINTABLE =
	// "^([a-zA-Z0-9!@#$%^&amp;*()-_=+;:'&quot;|~`&lt;&gt;?/{}]{1,1})$";

	private final static String NON_PRINTABLE_CHAR = ".";

	private final static int ADDR = 0;
	private final static int HEX1 = 1;
	private final static int HEX2 = 2;
	private final static int ASCII = 3;
	private final static int BLANK = 5;
	private final static int EOL = 10;

}// class HexEditDocumentFilter
