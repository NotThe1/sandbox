package hexEdit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.nio.ByteBuffer;

import javax.swing.BoundedRangeModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import utilities.HexEditNavigationFilter;

public class HexEditPanelSB extends JPanel implements AdjustmentListener,ComponentListener {
	private ByteBuffer source;
	private static int currentLineStart;
	private int currentMax;
	private int currentExtent;
	private StyledDocument doc;

	private int addressSize;

	private String addressFormat;
	private String dataFormat = "%-" + ((BYTES_PER_LINE * 3) + 2) + "s";
	private String hexCharacterFormat = "%02X ";

	private SimpleAttributeSet addressAttributes;
	private SimpleAttributeSet dataAttributes;
	private SimpleAttributeSet asciiAttributes;

	private JTextPane textPane;
	private JScrollBar scrollBar;
	private JLabel lblDocHeader;

	private void fillPane() {
		if (currentExtent==0){
			return;
		}//if nothing to display
		
		try {
			doc.remove(0, doc.getLength());
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // try
//		System.out.printf("FillPane: currentStartLine = %d%n", currentLineStart);
		int sourceIndex = currentLineStart * BYTES_PER_LINE; // address to
																// display
		byte[] activeLine = new byte[BYTES_PER_LINE];
		int linesToDisplay = Math.min(currentExtent, currentMax - currentLineStart);

		int bytesToRead = BYTES_PER_LINE;
		source.position(sourceIndex);
		for (int i = 0; i < linesToDisplay; i++) {
			source.get(activeLine, 0, bytesToRead);
			processLine(activeLine, bytesToRead, sourceIndex);
			sourceIndex += bytesToRead;
			if (bytesToRead < BYTES_PER_LINE) {
				break;
			} // if
			bytesToRead = Math.min(source.remaining(), BYTES_PER_LINE);
		} // for
	}// fillPane

	private int processLine(byte[] rawData, int bytesRead, int bufferAddress) {//
		StringBuilder sbData = new StringBuilder();
		for (int i = 0; i < bytesRead; i++) {
			if ((i % 8) == 0) {
				sbData.append(SPACE);
			} // if data extra space
				// sbData.append(String.format(hexCharacterFormat,
				// sourceBuffer.get(i)));
			sbData.append(String.format(hexCharacterFormat, rawData[i]));
			// System.out.printf("sourceArray[%2d]: %02X%n", i, rawData[i]);
		} // for

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

	}// processLine

	private String getASCII(byte[] rawData, int size) {
		StringBuilder sbASCII = new StringBuilder(ASCII_DATA_SEPARATOR);
		char c;
		for (int i = 0; i < size; i++) {
			c = (char) rawData[i];
			sbASCII.append((c >= 0X20) && (c <= 0X7F) ? c : NON_PRINTABLE_CHAR);
		} // for
		sbASCII.append(System.lineSeparator());
		return sbASCII.toString();
	}// getASCII

	private void setFormats(long addSize) {
		adjustAddressSize(addSize);
		addressFormat = "%0" + this.addressSize + "X: ";
	}// setFormats

	private void adjustAddressSize(long size) {
		int addressSizeCurrent = this.addressSize;
		if (size <= ADDRESS_4_MAX) {
			setAddressSize(Math.max(ADDRESS_4, addressSizeCurrent));
			lblDocHeader.setText(DOC_HEADER);
		} else if (size <= ADDRESS_6_MAX) {
			setAddressSize(Math.max(ADDRESS_6, addressSizeCurrent));
			lblDocHeader.setText(SPACE + SPACE + DOC_HEADER);
		} else if (size <= ADDRESS_8_MAX) {
			setAddressSize(Math.max(ADDRESS_8, addressSizeCurrent));
			lblDocHeader.setText(SPACE + SPACE + SPACE + SPACE + DOC_HEADER);
		} else {
			this.addressSize = ADDRESS_4;
			lblDocHeader.setText(DOC_HEADER);
		} // if fileSize
	}// adjustAddressSize

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
			} // if
		}// switch
		System.out.printf("addressSize = %d %n", this.addressSize);
		// addressFormat = "%0" + this.addressSize + "X: ";

	}// setAddressSize

	private void prepareDoc(StyledDocument doc, long srcSize) {
		setFormats(srcSize);
		 resetDocumentFilter(doc);
		 resetNavigationFilter();
		// clearDocument(doc);
	}// prepareDoc
	
	private void resetDocumentFilter(StyledDocument doc) {
		((AbstractDocument) doc).setDocumentFilter(null);
	}// resetDocumentFilter

	private void setDocumentFilter(StyledDocument doc) {
		
		// System.out.printf("[setDocumentFilter]\t0 address end = %d %n", addressEnd);
		// System.out.printf("[setDocumentFilter]\t1 data end = %d %n", dataEnd);
		// System.out.printf("[setDocumentFilter]\t1 ASCII end = %d %n", asciiEnd);

		HexEditDocumentFilterSB hexFilter = new HexEditDocumentFilterSB(doc, addressSize);
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


	public void loadDocument(byte[] sourceArray) {
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
		setDocumentFilter(doc);
	}// loadDocument

	private void setExtent(int amount, BoundedRangeModel model) {
		model.setExtent(amount);
		currentExtent = amount;
	}// setExtent

	private void setMin(int amount, BoundedRangeModel model) {
		model.setMinimum(amount);
	}// setMin

	private void setMax(int amount, BoundedRangeModel model) {
		model.setMaximum(amount);
		currentMax = amount;
	}// setMax

	private void setValue(int amount, BoundedRangeModel model) {
		model.setValue(amount);
	}// setValue

	private void setUpScrollBar() {
		// int fontHeight = calcFontHeight(thisTextPane);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				int max = maximumNumberOfRows(textPane);
				int extent = calcExtent(textPane);

				BoundedRangeModel model = scrollBar.getModel();
				setMin(0, model);
				setMax(max, model);
				setValue(0, model);
				setExtent(extent, model);
				scrollBar.setBlockIncrement(extent-2);
				// -
//				System.out.printf("Max = %d%n", model.getMaximum());
//				System.out.printf("Min = %d%n", model.getMinimum());
//				System.out.printf("Extent = %d%n", model.getExtent());
//				System.out.printf("Value = %d%n", model.getValue());
				// --

			}// run
		});

	}// setUpScrollBar

	private int calcUsableHeight(JTextPane thisPane) {
		Insets insets = thisPane.getInsets();
		Dimension dimension = thisPane.getSize();
		int ans = dimension.height - (insets.bottom + insets.top);
		return dimension.height - (insets.bottom + insets.top);
	}// calcUsableHeight

	private int calcFontHeight(JTextPane thisPane) {
		Font font = textPane.getFont();
		return textPane.getFontMetrics(font).getHeight();
	}// calcFontHeight

	private int calcExtent(JTextPane thisPane) {
		int useableHeight = calcUsableHeight(thisPane);
		int fontHeight = calcFontHeight(thisPane);
		int ans = (int) (useableHeight / fontHeight);
		return (int) (useableHeight / fontHeight);
	}// calcExtent

	private int maximumNumberOfRows(JTextPane thisTextPane) {
		// return (int) spinnerRows.getValue();
		return (source.limit() / BYTES_PER_LINE) + 1;
	}// maximumNumberOfRows

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
		// -------------------------------------------------------------------------------------------------------

	public HexEditPanelSB() {
		initialize();
		appInit();
	}// Constructor

	private void appInit() {
		doc = textPane.getStyledDocument();
		makeStyles();

	}// appInit

	private void initialize() {
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 780, 25 };
		gridBagLayout.rowHeights = new int[] { 25, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		lblDocHeader = new JLabel();
		lblDocHeader.setForeground(new Color(105, 105, 105));
		lblDocHeader.setHorizontalAlignment(SwingConstants.LEFT);
		lblDocHeader.setFont(new Font("Courier New", Font.BOLD, 16));
		GridBagConstraints gbc_lblDocHeader = new GridBagConstraints();
		gbc_lblDocHeader.fill = GridBagConstraints.VERTICAL;
		gbc_lblDocHeader.anchor = GridBagConstraints.WEST;
		gbc_lblDocHeader.insets = new Insets(0, 0, 5, 5);
		gbc_lblDocHeader.gridx = 0;
		gbc_lblDocHeader.gridy = 0;
		add(lblDocHeader, gbc_lblDocHeader);

		textPane = new JTextPane();
		textPane.addComponentListener(this);

		textPane.setFont(new Font("Courier New", Font.PLAIN, 16));
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.insets = new Insets(0, 0, 0, 5);
		gbc_textPane.fill = GridBagConstraints.BOTH;
		gbc_textPane.gridx = 0;
		gbc_textPane.gridy = 1;
		add(textPane, gbc_textPane);

		scrollBar = new JScrollBar();
		scrollBar.addAdjustmentListener(this);
		scrollBar.setMaximum(0);
		scrollBar.setPreferredSize(new Dimension(25, 48));
		scrollBar.setMinimumSize(new Dimension(25, 5));
		scrollBar.setMaximumSize(new Dimension(25, 32767));
		GridBagConstraints gbc_scrollBar = new GridBagConstraints();
		gbc_scrollBar.fill = GridBagConstraints.VERTICAL;
		gbc_scrollBar.gridx = 1;
		gbc_scrollBar.gridy = 1;
		add(scrollBar, gbc_scrollBar);

	}//initialize
	
	@Override
	public void componentResized(ComponentEvent comonentEvent) {
		setUpScrollBar();
		fillPane();
	}//componentResized



	@Override
	public void adjustmentValueChanged(AdjustmentEvent adjustmentEvent) {
		if (adjustmentEvent.getValueIsAdjusting()) {
			return;
		} // if

		if (adjustmentEvent.getAdjustmentType() != AdjustmentEvent.TRACK) {
			return;
		}//if

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				currentLineStart = adjustmentEvent.getValue();
				fillPane();
			}// run
		});

	}// adjustmentValueChanged

	public static final int BYTES_PER_LINE = 16;
	private static final String SPACE = " ";
	private static final String ASCII_DATA_SEPARATOR = SPACE + SPACE;
	private static final String NON_PRINTABLE_CHAR = ".";

	private static final int ADDRESS_4 = 4;
	private static final int ADDRESS_6 = 6;
	private static final int ADDRESS_8 = 8;

	private static final int ADDRESS_4_MAX = 0XFFFF; // FFFF 65535
	private static final int ADDRESS_6_MAX = 0XFFFFFF; // FF FFFF 16,777,215
	private static final int ADDRESS_8_MAX = Integer.MAX_VALUE; // 7FFF FFFF
																// 2,147,483,647

	private static final String DOC_HEADER = "       00 01 02 03 04 05 06 07  08 09 0A 0B 0C 0D 0E 0F  ";
	private JLabel lblDocHeader_1;

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub	
	}

}// class HexEditPanelSB
