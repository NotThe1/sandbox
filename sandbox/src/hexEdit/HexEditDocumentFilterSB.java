package hexEdit;

import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.Element;
import javax.swing.text.StyledDocument;

public class HexEditDocumentFilterSB extends DocumentFilter {

	// public HexEditDocumentFilter() {
	// // TODO Auto-generated constructor stub
	// }
	private StyledDocument doc;
	private int dataEnd, asciiEnd;// addressEnd,
	private int row, column, elementPosition;
	private boolean isAddress, isData, isASCII;
	private boolean isInsert;
	private Pattern hexPattern;
	private int[] columnTypeTable;
	private Integer[] dataToAsciiTable;

	private AttributeSet attrData;
	private AttributeSet attrASCII;

	private HexEditMetrics hexMetrics;

	private boolean innerFlag = false;

	public HexEditDocumentFilterSB(StyledDocument doc, HexEditMetrics hexMetrics) {
		this.doc = doc;
		this.hexMetrics = hexMetrics;

		this.attrData = null;
		this.attrASCII = null;
		// this.addressEnd = addressSize + 2;
		// this.dataEnd = addressEnd + (BYTES_PER_LINE * 3) + 2;
		this.asciiEnd = dataEnd + BYTES_PER_LINE + 4;
		appInit();

	}// Constructor

	public void setDataAttributes(AttributeSet attrData) {
		this.attrData = attrData;
	}// setDataAttributes

	public void setAsciiAttributes(AttributeSet attrASCII) {
		this.attrASCII = attrASCII;
	}// setDataAttributes

	public void appInit() {
		hexPattern = Pattern.compile(PATTERN_HEX);
		columnTypeTable = makeColumnTable();
		dataToAsciiTable = makeDataToAsciiTable();

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
		System.out.printf("[replace]\toffset: %d ,length: %d, text: %s %n", offset, length, text);
		int netLength = length == 0 ? 1 : length;// length of 0 equals a insert;
													// length of 1 equals a
													// replace

		int columnPosition = getColumnPosition(offset);
		Integer newCharacterIndex = null;
		Integer newDataIndex = null;
		int columnType = columnTypeTable[columnPosition];
		System.out.printf("replace:: position: offset: %d, netLength: %d, text: %s, columnType: %d, innerFlag %s%n",
				offset, netLength, text, columnType, innerFlag);

		switch (columnType) {
		case ADDR: // do nothing
		case EOL: // ignore
			return;
		case BLANK:
			break;
		case HEX1:
		case HEX2:
			if (!isTextHex(text)) {
				return;
			} // if - want only hex characters
			text = text.toUpperCase();
			newCharacterIndex = dataToAsciiTable[columnPosition];
			break;
		case ASCII:
			newDataIndex = dataToAsciiTable[columnPosition];
			innerFlag = true;
			break;

		default:

		}// switch - columnType
		fb.replace(offset, netLength, text, attrs);

		// handle simultaneous change of data/ASCII display
		if (newCharacterIndex != null) {
			int targetOffset = (columnType == HEX1) ? offset : offset - 1;
			String newChar = convertToPrintable(targetOffset);
			int docOffset = offset + newCharacterIndex;
			fb.replace(docOffset, 1, newChar, attrASCII);
			System.out.printf("Add - \"%s\" into location %d%n", newChar, docOffset);
			System.out.printf("offset: %d ,newCharacterIndex: %d ,docOffset %d %n", offset, newCharacterIndex,
					docOffset);
		} // if newCharacterIndex

		if (columnType == ASCII) {
			byte[] hexValues = text.getBytes();
			String hexData = String.format("%02X", hexValues[0]);
			int docOffset = offset + newDataIndex;

			fb.replace(docOffset, 2, hexData, attrData);
			System.out.printf("hexData = %s %n", hexData);

		} // if newDataIndex

	}// replace

	private int getColumnPosition(int offset) {
		Element rootElement = doc.getDefaultRootElement();
		row = rootElement.getElementIndex(offset);
		Element paragraphElement = rootElement.getElement(row);
		return offset - paragraphElement.getStartOffset();
	}// getColumnPosition

	public String convertToPrintable(int offset) {
		String HexString = null;
		try {
			HexString = doc.getText(offset, 2).trim();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
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
	private final static int ASCII_WRAP = 11;

	private final static int BYTES_PER_LINE = HexEditPanelSB.BYTES_PER_LINE;

}// class HexEditDocumentFilter
