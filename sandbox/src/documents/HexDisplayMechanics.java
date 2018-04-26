package documents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.Date;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import myComponents.AppLogger;
import myComponents.hdnComponents.HDNumberBox;

public class HexDisplayMechanics {

	private JFrame frmTemplate;
	private JButton btnOne;
	private JButton btnTwo;
	private JButton btnThree;
	private JButton btnFour;
	private JSplitPane splitPane1;

	private AppLogger log = AppLogger.getInstance();
	private JTextPane txtLog;
	private JPopupMenu popupLog;
	private AdapterLog logAdaper = new AdapterLog();

	private File activeFile;
	private File tempFile;
	private String currentPath;
	private FileChannel fileChannel;
	private MappedByteBuffer fileMap;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HexDisplayMechanics window = new HexDisplayMechanics();
					window.frmTemplate.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				} // try
			}// run
		});
	}// main

	/* Standard Stuff */
	private static final String TEMP_PREFIX = "Test";
	private static final String TEMP_SUFFIX = ".tmp";

	class TempFilter implements FilenameFilter {

		@Override
		public boolean accept(File dir, String name) {
			if (name.startsWith(TEMP_PREFIX) && name.endsWith(TEMP_SUFFIX)) {
				return true;
			} else {
				return false;
			} // if
		}// accept

	}// class TempFilter

	private void doBtnOne() {
		try {
			tempFile = File.createTempFile("Test", ".tmp");
			log.addInfo("Target file = " + tempFile.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // try
		// Path source = Paths.get("C:\\Temp\\A\\ASM.COM");
		// Path target = Paths.get(tempFile.getAbsolutePath());

	}// doBtnOne

	private void doBtnTwo() {
		File tempDir = new File(System.getProperty("java.io.tmpdir"));
		File[] tempFiles = tempDir.listFiles(new TempFilter());
		for (File file : tempFiles) {
			log.addInfo("Listed file: " + file.getName());
			file.delete();
		} // for

	}// doBtnTwo

	private void doBtnThree() {
		Path source = Paths.get("C:\\Temp\\A\\copyTest.txt");
		Path target = Paths.get(tempFile.getAbsolutePath());
		try {
			Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // try

	}// doBtnThree

	private void doBtnFour() {

	}// doBtnFour

	// ---------------------------------------------------------

	private void doFileNew() {

	}// doFileNew

	private void doFileOpen() {

	}// doFileOpen

	private void doFileSave() {

	}// doFileSave

	private void doFileSaveAs() {

	}// doFileSaveAs

	private void doFilePrint() {

	}// doFilePrint

	private void doFileExit() {
		appClose();
		System.exit(0);
	}// doFileExit

	public void closeFile() {
		try {
			if (fileMap != null) {
				fileMap = null;
			} // if

			if (fileChannel != null) {
				fileChannel.close();
				fileChannel = null;
			} // if
		} catch (IOException e) {
			e.printStackTrace();
		} // try
		activeFile = null;
	}// closeFile

	private void loadFile(File sourceFile) {
		activeFile = sourceFile;
		currentPath = activeFile.getParent();
		log.addInfo("Loading File -> " + sourceFile.toString());
		// setActivityStates(FILE_ACTIVE);
		closeFile();

		long sourceLength = sourceFile.length();
		if (sourceLength >= Integer.MAX_VALUE) {
			Toolkit.getDefaultToolkit().beep();
			String message = String.format("[HexEditPanelFile : loadData] file too large %,d%n", sourceLength);
			log.addWarning(message);
			return;
		} // if

		try (RandomAccessFile raf = new RandomAccessFile(sourceFile, "rw")) {
			fileChannel = raf.getChannel();
			fileMap = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileChannel.size());// this.totalBytesOnDisk);
			fileChannel.close();
		} catch (IOException ioe) {
			Toolkit.getDefaultToolkit().beep();
			log.addError("[loadFile]: " + ioe.getMessage());
		} // try

		// hexEditDisplay.setData(fileMap);
		// hexEditDisplay.run();

	}// loadFile

	private void fromByte() {
		AsciiForms asciiForms = AsciiForms.getInstance();
		asciiForms.setByte(byteValue.getValue());

		asciiValue.setText(asciiForms.getAsciiForm());
		stringValue.setText(asciiForms.getStringForm());
	}// fromByte

	private void fromString() {
		AsciiForms asciiForms = AsciiForms.getInstance();
		asciiForms.setString(stringValue.getText());

		asciiValue.setText(asciiForms.getAsciiForm());
		byteValue.setValue(asciiForms.getByteForm());
	}// fromString

	private void fromAscii() {
		AsciiForms asciiForms = AsciiForms.getInstance();
		asciiForms.setAscii(asciiValue.getText());

		stringValue.setText(asciiForms.getStringForm());
		byteValue.setValue(asciiForms.getByteForm());
	}// fromAscii

	private void appClose() {
		Preferences myPrefs = Preferences.userNodeForPackage(HexDisplayMechanics.class)
				.node(this.getClass().getSimpleName());
		Dimension dim = frmTemplate.getSize();
		myPrefs.putInt("Height", dim.height);
		myPrefs.putInt("Width", dim.width);
		Point point = frmTemplate.getLocation();
		myPrefs.putInt("LocX", point.x);
		myPrefs.putInt("LocY", point.y);
		myPrefs.putInt("Divider", splitPane1.getDividerLocation());
		myPrefs = null;
		closeFile();
	}// appClose

	private void appInit() {
		Preferences myPrefs = Preferences.userNodeForPackage(HexDisplayMechanics.class)
				.node(this.getClass().getSimpleName());
		frmTemplate.setSize(myPrefs.getInt("Width", 761), myPrefs.getInt("Height", 693));
		frmTemplate.setLocation(myPrefs.getInt("LocX", 100), myPrefs.getInt("LocY", 100));
		splitPane1.setDividerLocation(myPrefs.getInt("Divider", 250));
		myPrefs = null;

		txtLog.setText(EMPTY_STRING);

		log.setDoc(txtLog.getStyledDocument());
		log.addInfo("Starting....");

		// loadFile(new File("C:\\Temp\\A\\ASM.COM"));

	}// appInit

	public HexDisplayMechanics() {
		initialize();
		appInit();
	}// Constructor

	private void doLogClear() {
		log.clear();
	}// doLogClear

	private void doLogPrint() {

		Font originalFont = txtLog.getFont();
		try {
			// textPane.setFont(new Font("Courier New", Font.PLAIN, 8));
			txtLog.setFont(originalFont.deriveFont(8.0f));
			MessageFormat header = new MessageFormat("Identic Log");
			MessageFormat footer = new MessageFormat(new Date().toString() + "           Page - {0}");
			txtLog.print(header, footer);
			// textPane.setFont(new Font("Courier New", Font.PLAIN, 14));
			txtLog.setFont(originalFont);
		} catch (PrinterException e) {
			e.printStackTrace();
		} // try

	}// doLogPrint

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTemplate = new JFrame();
		frmTemplate.setTitle("HexDisplayMechanics    0.0");
		frmTemplate.setBounds(100, 100, 450, 300);
		frmTemplate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTemplate.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				appClose();
			}
		});
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 25, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		frmTemplate.getContentPane().setLayout(gridBagLayout);

		JPanel panelForButtons = new JPanel();
		GridBagConstraints gbc_panelForButtons = new GridBagConstraints();
		gbc_panelForButtons.anchor = GridBagConstraints.NORTH;
		gbc_panelForButtons.insets = new Insets(0, 0, 5, 0);
		gbc_panelForButtons.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelForButtons.gridx = 0;
		gbc_panelForButtons.gridy = 0;
		frmTemplate.getContentPane().add(panelForButtons, gbc_panelForButtons);
		GridBagLayout gbl_panelForButtons = new GridBagLayout();
		gbl_panelForButtons.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panelForButtons.rowHeights = new int[] { 0, 0 };
		gbl_panelForButtons.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelForButtons.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelForButtons.setLayout(gbl_panelForButtons);

		btnOne = new JButton("Button 1");
		btnOne.setMinimumSize(new Dimension(100, 20));
		GridBagConstraints gbc_btnOne = new GridBagConstraints();
		gbc_btnOne.insets = new Insets(0, 0, 0, 5);
		gbc_btnOne.gridx = 0;
		gbc_btnOne.gridy = 0;
		panelForButtons.add(btnOne, gbc_btnOne);
		btnOne.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnOne.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doBtnOne();
			}
		});
		btnOne.setMaximumSize(new Dimension(0, 0));
		btnOne.setPreferredSize(new Dimension(100, 20));

		btnTwo = new JButton("Button 2");
		btnTwo.setMinimumSize(new Dimension(100, 20));
		GridBagConstraints gbc_btnTwo = new GridBagConstraints();
		gbc_btnTwo.insets = new Insets(0, 0, 0, 5);
		gbc_btnTwo.gridx = 1;
		gbc_btnTwo.gridy = 0;
		panelForButtons.add(btnTwo, gbc_btnTwo);
		btnTwo.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doBtnTwo();
			}
		});
		btnTwo.setPreferredSize(new Dimension(100, 20));
		btnTwo.setMaximumSize(new Dimension(0, 0));

		btnThree = new JButton("Button 3");
		btnThree.setMinimumSize(new Dimension(100, 20));
		GridBagConstraints gbc_btnThree = new GridBagConstraints();
		gbc_btnThree.insets = new Insets(0, 0, 0, 5);
		gbc_btnThree.gridx = 3;
		gbc_btnThree.gridy = 0;
		panelForButtons.add(btnThree, gbc_btnThree);
		btnThree.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doBtnThree();
			}
		});
		btnThree.setPreferredSize(new Dimension(100, 20));
		btnThree.setMaximumSize(new Dimension(0, 0));

		btnFour = new JButton("Button 4");
		btnFour.setMinimumSize(new Dimension(100, 20));
		GridBagConstraints gbc_btnFour = new GridBagConstraints();
		gbc_btnFour.gridx = 4;
		gbc_btnFour.gridy = 0;
		panelForButtons.add(btnFour, gbc_btnFour);
		btnFour.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnFour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doBtnFour();
			}
		});
		btnFour.setPreferredSize(new Dimension(100, 20));
		btnFour.setMaximumSize(new Dimension(0, 0));

		splitPane1 = new JSplitPane();
		GridBagConstraints gbc_splitPane1 = new GridBagConstraints();
		gbc_splitPane1.insets = new Insets(0, 0, 5, 0);
		gbc_splitPane1.fill = GridBagConstraints.BOTH;
		gbc_splitPane1.gridx = 0;
		gbc_splitPane1.gridy = 1;
		frmTemplate.getContentPane().add(splitPane1, gbc_splitPane1);

		JPanel panelLeft = new JPanel();
		splitPane1.setLeftComponent(panelLeft);
		GridBagLayout gbl_panelLeft = new GridBagLayout();
		gbl_panelLeft.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelLeft.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panelLeft.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelLeft.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelLeft.setLayout(gbl_panelLeft);

		JButton lblByte = new JButton("Byte");
		lblByte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fromByte();
			}
		});
		lblByte.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblByte = new GridBagConstraints();
		gbc_lblByte.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblByte.insets = new Insets(0, 0, 5, 5);
		gbc_lblByte.gridx = 0;
		gbc_lblByte.gridy = 1;
		panelLeft.add(lblByte, gbc_lblByte);

		byteValue = new HDNumberBox();
		byteValue.setValue(0);
		byteValue.setMaxValue(0xFF);
		byteValue.setMinValue(-1);
		byteValue.setMinimumSize(new Dimension(60, 30));
		byteValue.setMaximumSize(new Dimension(60, 30));
		byteValue.setPreferredSize(new Dimension(60, 30));
		GridBagConstraints gbc_byteValue = new GridBagConstraints();
		gbc_byteValue.anchor = GridBagConstraints.WEST;
		gbc_byteValue.insets = new Insets(0, 0, 5, 0);
		gbc_byteValue.gridx = 1;
		gbc_byteValue.gridy = 1;
		panelLeft.add(byteValue, gbc_byteValue);

		JButton lblString = new JButton("String");
		lblString.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fromString();
			}
		});
		lblString.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblString = new GridBagConstraints();
		gbc_lblString.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblString.insets = new Insets(0, 0, 5, 5);
		gbc_lblString.gridx = 0;
		gbc_lblString.gridy = 2;
		panelLeft.add(lblString, gbc_lblString);

		stringValue = new JTextField();
		stringValue.setMinimumSize(new Dimension(60, 30));
		stringValue.setMaximumSize(new Dimension(60, 30));
		stringValue.setPreferredSize(new Dimension(60, 30));
		GridBagConstraints gbc_stringValue = new GridBagConstraints();
		gbc_stringValue.anchor = GridBagConstraints.WEST;
		gbc_stringValue.insets = new Insets(0, 0, 5, 0);
		gbc_stringValue.gridx = 1;
		gbc_stringValue.gridy = 2;
		panelLeft.add(stringValue, gbc_stringValue);
		stringValue.setColumns(10);

		JButton lblAscii = new JButton("Ascii");
		lblAscii.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fromAscii();
			}
		});
		lblAscii.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblAscii = new GridBagConstraints();
		gbc_lblAscii.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblAscii.insets = new Insets(0, 0, 0, 5);
		gbc_lblAscii.gridx = 0;
		gbc_lblAscii.gridy = 3;
		panelLeft.add(lblAscii, gbc_lblAscii);

		asciiValue = new JTextField();
		asciiValue.setMinimumSize(new Dimension(60, 30));
		asciiValue.setMaximumSize(new Dimension(60, 30));
		asciiValue.setPreferredSize(new Dimension(60, 30));
		GridBagConstraints gbc_asciiValue = new GridBagConstraints();
		gbc_asciiValue.anchor = GridBagConstraints.WEST;
		gbc_asciiValue.gridx = 1;
		gbc_asciiValue.gridy = 3;
		panelLeft.add(asciiValue, gbc_asciiValue);
		asciiValue.setColumns(10);

		JPanel panelForLog = new JPanel();
		splitPane1.setRightComponent(panelForLog);
		GridBagLayout gbl_panelForLog = new GridBagLayout();
		gbl_panelForLog.columnWidths = new int[] { 0, 0 };
		gbl_panelForLog.rowHeights = new int[] { 0, 0 };
		gbl_panelForLog.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelForLog.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelForLog.setLayout(gbl_panelForLog);

		JScrollPane scrollPaneForLog = new JScrollPane();
		GridBagConstraints gbc_scrollPaneForLog = new GridBagConstraints();
		gbc_scrollPaneForLog.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneForLog.gridx = 0;
		gbc_scrollPaneForLog.gridy = 0;
		panelForLog.add(scrollPaneForLog, gbc_scrollPaneForLog);

		txtLog = new JTextPane();
		scrollPaneForLog.setViewportView(txtLog);

		popupLog = new JPopupMenu();
		addPopup(txtLog, popupLog);

		JMenuItem popupLogClear = new JMenuItem("Clear Log");
		popupLogClear.setName(PUM_LOG_CLEAR);
		popupLogClear.addActionListener(logAdaper);
		popupLog.add(popupLogClear);

		JSeparator separator = new JSeparator();
		popupLog.add(separator);

		JMenuItem popupLogPrint = new JMenuItem("Print Log");
		popupLogPrint.setName(PUM_LOG_PRINT);
		popupLogPrint.addActionListener(logAdaper);
		popupLog.add(popupLogPrint);

		JLabel lblNewLabel = new JLabel("Application Log");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(0, 128, 0));
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		scrollPaneForLog.setColumnHeaderView(lblNewLabel);
		splitPane1.setDividerLocation(250);

		JPanel panelStatus = new JPanel();
		panelStatus.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_panelStatus = new GridBagConstraints();
		gbc_panelStatus.fill = GridBagConstraints.BOTH;
		gbc_panelStatus.gridx = 0;
		gbc_panelStatus.gridy = 2;
		frmTemplate.getContentPane().add(panelStatus, gbc_panelStatus);

		JMenuBar menuBar = new JMenuBar();
		frmTemplate.setJMenuBar(menuBar);

		JMenu mnuFile = new JMenu("File");
		menuBar.add(mnuFile);

		JMenuItem mnuFileNew = new JMenuItem("New");
		mnuFileNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doFileNew();
			}
		});
		mnuFile.add(mnuFileNew);

		JMenuItem mnuFileOpen = new JMenuItem("Open...");
		mnuFileOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doFileOpen();
			}
		});
		mnuFile.add(mnuFileOpen);

		JSeparator separator99 = new JSeparator();
		mnuFile.add(separator99);

		JMenuItem mnuFileSave = new JMenuItem("Save...");
		mnuFileSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doFileSave();
			}
		});
		mnuFile.add(mnuFileSave);

		JMenuItem mnuFileSaveAs = new JMenuItem("Save As...");
		mnuFileSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doFileSaveAs();
			}
		});
		mnuFile.add(mnuFileSaveAs);

		JSeparator separator_2 = new JSeparator();
		mnuFile.add(separator_2);

		JMenuItem mnuFilePrint = new JMenuItem("Print...");
		mnuFilePrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doFilePrint();
			}
		});
		mnuFile.add(mnuFilePrint);

		JSeparator separator_1 = new JSeparator();
		mnuFile.add(separator_1);

		JMenuItem mnuFileExit = new JMenuItem("Exit");
		mnuFileExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doFileExit();
			}
		});
		mnuFile.add(mnuFileExit);

	}// initialize

	private static final String PUM_LOG_PRINT = "popupLogPrint";
	private static final String PUM_LOG_CLEAR = "popupLogClear";

	static final String EMPTY_STRING = "";
	private JTextField stringValue;
	private JTextField asciiValue;
	private HDNumberBox byteValue;

	//////////////////////////////////////////////////////////////////////////

	class AdapterLog implements ActionListener {// , ListSelectionListener
		@Override
		public void actionPerformed(ActionEvent actionEvent) {
			String name = ((Component) actionEvent.getSource()).getName();
			switch (name) {
			case PUM_LOG_PRINT:
				doLogPrint();
				break;
			case PUM_LOG_CLEAR:
				doLogClear();
				break;
			}// switch
		}// actionPerformed
	}// class AdapterAction

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				} // if popup Trigger
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}// addPopup

}// class GUItemplate