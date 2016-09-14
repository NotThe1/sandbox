package hexEdit;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.GridBagConstraints;

import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import utilities.HexEditDocumentFilter;
import utilities.HexEditNavigationFilter;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

import javax.swing.SwingConstants;

public class HexEditPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private int addressSize;
	private int startingAddress;
	private long dataSize;

	String addressFormat;
	String dataFormat;;
	String hexCharacterFormat = "%02X ";

	public int getStartingAddress() {
		return this.startingAddress;
	}// getStartingAddress

	public int getDataSize() {
		return (int) this.dataSize;
	}// getDataSize

	public int unLoadDocument(File destinationFile) {
		byte[] data = parseDocument(doc);
		ByteBuffer dataOut = ByteBuffer.wrap(data);

		try {
			FileOutputStream fout = new FileOutputStream(destinationFile);
			FileChannel fc = fout.getChannel();
			fc.write(dataOut);
			fc.close();
			fout.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}//try block
		return -1;
	}//unLoadDocument

	public byte[] unLoadDocument() {
		return parseDocument(doc);
	}//

	private byte[] parseDocument(StyledDocument doc) {
		byte[] thisData = new byte[(int) dataSize];
		Scanner sc = null;
		try {
			sc = new Scanner(doc.getText(0, doc.getLength()));
			int index = 0;
			while (sc.hasNextLine()) {
				sc.next();
				while (sc.hasNextInt(16)) {
					thisData[index++] = (byte) sc.nextInt(16);
				}// while int
				sc.nextLine();
			}// while line
			sc.close();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		return thisData;
	}

	public void loadDocument(byte[] sourceData, int startingAddress) {
		this.startingAddress = startingAddress;
		this.dataSize = sourceData.length;
		prepareDoc(doc, dataSize);
		byte[] myBuff = new byte[BYTES_PER_LINE];
		int srcPos = 0;
		int bytesToRead = BYTES_PER_LINE;
		int originalNumberOfBytes = sourceData.length;
		int bytesRemaining = originalNumberOfBytes;

		int bufferAddress = this.startingAddress;
		while (bytesToRead == 16) {
			bytesToRead = (bytesRemaining >= bytesToRead) ? BYTES_PER_LINE : bytesRemaining;
			System.arraycopy(sourceData, srcPos, myBuff, 0, bytesToRead);
			bufferAddress = processLine(myBuff, bytesToRead, bufferAddress);
			// for (int i = 0; i < bytesToRead; i++) {
			// System.out.printf("i: %d, myBuff: %02X%n", i, myBuff[i]);
			// }// for
			srcPos += bytesToRead;
			bytesRemaining = originalNumberOfBytes - srcPos;

		}// while
		finishDoc(doc, bytesToRead);
	}// loadDocument byte[]

	public void loadDocument(File sourceFile) {
		this.startingAddress = 0;
		this.dataSize = sourceFile.length();
		prepareDoc(doc, dataSize);
		FileInputStream fin = null;

		try {
			fin = new FileInputStream(sourceFile);
		} catch (FileNotFoundException e) {
			//
			e.printStackTrace();
		}// try
		FileChannel sourceChannel = fin.getChannel();

		byte[] sourceArray = new byte[BYTES_PER_LINE];
		ByteBuffer sourceBuffer = ByteBuffer.wrap(sourceArray);
		// ++++++

		int bytesRead = 16;
		int bufferAddress = this.startingAddress;

		while (bytesRead == 16) {
			try {
				bytesRead = sourceChannel.read(sourceBuffer);
				if (bytesRead == -1) {
					break;
				}// no need to add to the doc
				bufferAddress = processLine(sourceArray, bytesRead, bufferAddress);
				sourceBuffer.clear();
			} catch (IOException e) {
				//
				e.printStackTrace();
			}// try read
		}// while
			// System.out.printf("bytesRead: %d  ", bytesRead);
		finishDoc(doc, bytesRead);
	}// loadDocument

	private void finishDoc(StyledDocument doc, int bytesRead) {
		setDocumentFilter(doc);
		setNavigationFilter(doc, bytesRead);
		textPane.setCaretPosition(0);
	}// finishDoc

	private void prepareDoc(StyledDocument doc, long srcSize) {
		setFormats(srcSize);
		resetDocumentFilter(doc);
		resetNavigationFilter();
		clearDocument(doc);

	}

	private int processLine(byte[] rawData, int bytesRead, int bufferAddress) {//
		StringBuilder sbData = new StringBuilder();
		for (int i = 0; i < bytesRead; i++) {
			if ((i % 8) == 0) {
				sbData.append(SPACE);
			}// if data extra space
				// sbData.append(String.format(hexCharacterFormat, sourceBuffer.get(i)));
			sbData.append(String.format(hexCharacterFormat, rawData[i]));
			// System.out.printf("sourceArray[%2d]: %02X%n", i, rawData[i]);
		}// for

		String bufferAddressStr = String.format(addressFormat, bufferAddress);
		String dataStr = String.format(dataFormat, sbData.toString());
		// sourceBuffer.rewind();
		String asciiStr = getASCII(rawData, bytesRead);

		try {
			doc.insertString(doc.getLength(), bufferAddressStr, addressAttributes);
			doc.insertString(doc.getLength(), dataStr, dataAttributes);
			doc.insertString(doc.getLength(), asciiStr, asciiAttributes);
		} catch (BadLocationException e) {
			//
			e.printStackTrace();
		}

		// System.out.printf("%s", bufferAddressStr);
		// System.out.printf("%s", dataStr);
		// System.out.printf("%s", asciiStr);

		// sourceBuffer.clear();
		return bufferAddress += bytesRead;

	}

	private void setFormats(long addSize) {
		adjustAddressSize(addSize);
		addressFormat = "%0" + this.addressSize + "X: ";
		dataFormat = "%-" + ((BYTES_PER_LINE * 3) + 2) + "s";
	}

	private String getASCII(byte[] rawData, int size) {
		StringBuilder sbASCII = new StringBuilder(ASCII_DATA_SEPARATOR);
		char c;
		for (int i = 0; i < size; i++) {
			c = (char) rawData[i];
			sbASCII.append((c >= 0X20) && (c <= 0X7F) ? c : NON_PRINTABLE_CHAR);
		}// for
		sbASCII.append(System.lineSeparator());
		return sbASCII.toString();
	}// getASCII

	private void adjustAddressSize(long size) {
		int addressSizeCurrent = this.addressSize;
		if (size <= ADDRESS_4_MAX) {
			setAddressSize(Math.max(ADDRESS_4, addressSizeCurrent));
		} else if (size <= ADDRESS_6_MAX) {
			setAddressSize(Math.max(ADDRESS_6, addressSizeCurrent));
		} else if (size <= ADDRESS_8_MAX) {
			setAddressSize(Math.max(ADDRESS_8, addressSizeCurrent));
		} else {
			this.addressSize = ADDRESS_4;
		}// if fileSize
	}// adjustAddressSize

	private void resetDocumentFilter(StyledDocument doc) {
		((AbstractDocument) doc).setDocumentFilter(null);
	}// resetDocumentFilter

	private void setDocumentFilter(StyledDocument doc) {
		Element rootElement = doc.getDefaultRootElement();
		Element paragraphElement = rootElement.getElement(0);
		Element element = paragraphElement.getElement(0);
		int addressEnd = element.getEndOffset();

		element = paragraphElement.getElement(1);
		int dataEnd = element.getEndOffset();

		element = paragraphElement.getElement(2);
		int asciiEnd = element.getEndOffset();

		// System.out.printf("[setDocumentFilter]\t0 address end = %d %n", addressEnd);
		// System.out.printf("[setDocumentFilter]\t1 data end = %d %n", dataEnd);
		// System.out.printf("[setDocumentFilter]\t1 ASCII end = %d %n", asciiEnd);

		HexEditDocumentFilter hexFilter = new HexEditDocumentFilter(doc, addressEnd, dataEnd, asciiEnd);
		hexFilter.setAsciiAttributes(asciiAttributes);
		hexFilter.setDataAttributes(dataAttributes);
		((AbstractDocument) doc).setDocumentFilter(hexFilter);
	}// setDocumentFilter

	private void resetNavigationFilter() {
		textPane.setNavigationFilter(null);
	}// resetDocumentFilter

	private void setNavigationFilter(StyledDocument doc, int lastDataCount) {
		HexEditNavigationFilter hexNavigationFilter = new HexEditNavigationFilter(doc, lastDataCount);
		textPane.setNavigationFilter(hexNavigationFilter);
	}// resetDocumentFilter

	public void setAddressSize(int addressSize) {

		switch (addressSize) {
		case ADDRESS_4:
		case ADDRESS_6:
		case ADDRESS_8:
			this.addressSize = addressSize;
			break;
		default:
			Object possibleValues[] = { "4 - 65 Kilobytes ", "6 - 16 Megabytes", "8 - 2 Gigabytes" };
			Object selectedValue = JOptionPane.showInputDialog(null, "Choose one", "Address Size",
					JOptionPane.INFORMATION_MESSAGE, null, possibleValues, possibleValues[0]);
			if (selectedValue != null) {
				int value = Integer.valueOf(selectedValue.toString().substring(0, 1));
				this.addressSize = value;
			}// if
		}// switch
		System.out.printf("addressSize = %d %n", this.addressSize);
		// addressFormat = "%0" + this.addressSize + "X: ";

	}// setAddressSize

	private void clearDocument(StyledDocument doc) {
		try {
			doc.remove(0, doc.getLength());
		} catch (BadLocationException e) {
			//
			e.printStackTrace();
		}
	}// clearDocument

	private void makeStyles() {
		SimpleAttributeSet baseAttributes = new SimpleAttributeSet();
		StyleConstants.setFontFamily(baseAttributes, "Courier New");
		StyleConstants.setFontSize(baseAttributes, 16);

		addressAttributes = new SimpleAttributeSet(baseAttributes);
		StyleConstants.setForeground(addressAttributes, Color.GRAY);

		dataAttributes = new SimpleAttributeSet(baseAttributes);
		StyleConstants.setForeground(dataAttributes, Color.BLACK);

		asciiAttributes = new SimpleAttributeSet(baseAttributes);
		StyleConstants.setForeground(asciiAttributes, Color.BLUE);

	}// makeStyles1

	private void appInit() {
		makeStyles();
		doc = textPane.getStyledDocument();
	}// appInit

	public HexEditPanel() {
		initialize();
		appInit();
	}// Constructor

	/**
	 * Create the panel.
	 */
	public void initialize() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 770 };
		gridBagLayout.rowWeights = new double[] { 1.0 };
		gridBagLayout.columnWeights = new double[] { 0.0 };
		setLayout(gridBagLayout);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		add(scrollPane, gbc_scrollPane);

		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);

		lblNewLabel = new JLabel("       00 01 02 03 04 05 06 07  08 09 0A 0B 0C 0D 0E 0F  ");
		lblNewLabel.setForeground(new Color(135, 206, 235));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setFont(new Font("Courier New", Font.BOLD, 16));
		scrollPane.setColumnHeaderView(lblNewLabel);
	}// initialize

	private JLabel lblNewLabel;
	private SimpleAttributeSet addressAttributes;
	private SimpleAttributeSet dataAttributes;
	private SimpleAttributeSet asciiAttributes;

	private JTextPane textPane;
	private StyledDocument doc;

	private static final String SPACE = " ";
	private static final String ASCII_DATA_SEPARATOR = SPACE + SPACE;
	private static final String NON_PRINTABLE_CHAR = ".";
	private static final int BYTES_PER_LINE = 16;
	private static final int DEFAULT_ADDRESS_SIZE = 4;

	private static final int ADDRESS_4 = 4;
	private static final int ADDRESS_6 = 6;
	private static final int ADDRESS_8 = 8;

	private static final int ADDRESS_4_MAX = 0XFFFF; // ‭FFFF 65535‬
	private static final int ADDRESS_6_MAX = 0XFFFFFF; // FF FFFF 16,777,215‬
	private static final int ADDRESS_8_MAX = Integer.MAX_VALUE; // 7FFF FFFF 2,147,483,647‬

}// class HexEditPanel
