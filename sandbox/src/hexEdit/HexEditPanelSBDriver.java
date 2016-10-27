package hexEdit;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;

public class HexEditPanelSBDriver {

	private JFrame frmTemplate;
	private JButton btnOne;
	private JButton btnTwo;
	private JButton btnThree;
	private JButton btnFour;
	private JSplitPane splitPane1;
	byte[] source;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HexEditPanelSBDriver window = new HexEditPanelSBDriver();
					window.frmTemplate.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				} // try
			}// run
		});
	}// main

	byte[] core = new byte[] { (byte) 0X00, (byte) 0X01, (byte) 0X02, (byte) 0X03, (byte) 0X04, (byte) 0X05,
			(byte) 0X06, (byte) 0X07, (byte) 0X08, (byte) 0X09, (byte) 0X0A, (byte) 0X0B, (byte) 0X0C, (byte) 0X0D,
			(byte) 0X0E, (byte) 0X0F, (byte) 0X10, (byte) 0X11, (byte) 0X12, (byte) 0X13, (byte) 0X14, (byte) 0X15,
			(byte) 0X16, (byte) 0X17, (byte) 0X18, (byte) 0X19, (byte) 0X1A, (byte) 0X1B, (byte) 0X1C, (byte) 0X1D,
			(byte) 0X1E, (byte) 0X1F, (byte) 0X21, (byte) 0X46, (byte) 0X01, (byte) 0X36, (byte) 0X01, (byte) 0X21,
			(byte) 0X47, (byte) 0X01, (byte) 0X36, (byte) 0X00, (byte) 0X7E, (byte) 0XFE, (byte) 0X09, (byte) 0XD2,
			(byte) 0X19, (byte) 0X01, (byte) 0X21, (byte) 0X46, (byte) 0X01, (byte) 0X7E, (byte) 0X17, (byte) 0XC2,
			(byte) 0X00, (byte) 0X01, (byte) 0XFF, (byte) 0X5F, (byte) 0X16, (byte) 0X00, (byte) 0X21, (byte) 0X48,
			(byte) 0X01, (byte) 0X19, (byte) 0X19, (byte) 0X4E, (byte) 0X79, (byte) 0X23, (byte) 0X46, (byte) 0X23,
			(byte) 0X96, (byte) 0X57, (byte) 0X78, (byte) 0X23, (byte) 0X9E, (byte) 0XDA, (byte) 0X3F, (byte) 0X01,
			(byte) 0XB2, (byte) 0XCA, (byte) 0X3F, (byte) 0X01, (byte) 0X56, (byte) 0X70, (byte) 0X2B, (byte) 0X5E,
			(byte) 0X06, (byte) 0X07, (byte) 0X08, (byte) 0X09, (byte) 0X0A, (byte) 0X0B, (byte) 0X0C, (byte) 0X0D,
			(byte) 0X0E, (byte) 0X0F, (byte) 0X10, (byte) 0X11, (byte) 0X12, (byte) 0X13, (byte) 0X14, (byte) 0X15,
			(byte) 0X16, (byte) 0X17, (byte) 0X18, (byte) 0X19, (byte) 0X1A, (byte) 0X1B, (byte) 0X1C, (byte) 0X1D,
			(byte) 0X1E, (byte) 0X1F, (byte) 0X21, (byte) 0X46, (byte) 0X01, (byte) 0X36, (byte) 0X01, (byte) 0X21,
			(byte) 0X47, (byte) 0X01, (byte) 0X36, (byte) 0X00, (byte) 0X7E, (byte) 0XFE, (byte) 0X09, (byte) 0XD2,
			(byte) 0X19, (byte) 0X01, (byte) 0X21, (byte) 0X46, (byte) 0X01, (byte) 0X7E, (byte) 0X17, (byte) 0XC2,
			(byte) 0X00, (byte) 0X01, (byte) 0XFF, (byte) 0X5F, (byte) 0X16, (byte) 0X00, (byte) 0X21, (byte) 0X48,
			(byte) 0X01, (byte) 0X19, (byte) 0X19, (byte) 0X4E, (byte) 0X79, (byte) 0X23, (byte) 0X46, (byte) 0X23,
			(byte) 0X96, (byte) 0X57, (byte) 0X78, (byte) 0X23, (byte) 0X9E, (byte) 0XDA, (byte) 0X3F, (byte) 0X01,
			(byte) 0XB2, (byte) 0XCA, (byte) 0X3F, (byte) 0X01, (byte) 0X56, (byte) 0X70, (byte) 0X2B, (byte) 0X5E,
			(byte) 0X06, (byte) 0X07, (byte) 0X08, (byte) 0X09, (byte) 0X0A, (byte) 0X0B, (byte) 0X0C, (byte) 0X0D,
			(byte) 0X0E, (byte) 0X0F, (byte) 0X10, (byte) 0X11, (byte) 0X12, (byte) 0X13, (byte) 0X14, (byte) 0X15,
			(byte) 0X16, (byte) 0X17, (byte) 0X18, (byte) 0X19, (byte) 0X1A, (byte) 0X1B, (byte) 0X1C, (byte) 0X1D,
			(byte) 0X1E, (byte) 0X1F, (byte) 0X21, (byte) 0X46, (byte) 0X01, (byte) 0X36, (byte) 0X01, (byte) 0X21,
			(byte) 0X47, (byte) 0X01, (byte) 0X36, (byte) 0X00, (byte) 0X7E, (byte) 0XFE, (byte) 0X09, (byte) 0XD2,
			(byte) 0X19, (byte) 0X01, (byte) 0X21, (byte) 0X46, (byte) 0X01, (byte) 0X7E, (byte) 0X17, (byte) 0XC2,
			(byte) 0X00, (byte) 0X01, (byte) 0XFF, (byte) 0X5F, (byte) 0X16, (byte) 0X00, (byte) 0X21, (byte) 0X48,
			(byte) 0X01, (byte) 0X19, (byte) 0X19, (byte) 0X4E, (byte) 0X79, (byte) 0X23, (byte) 0X46, (byte) 0X23,
			(byte) 0X96, (byte) 0X57, (byte) 0X78, (byte) 0X23, (byte) 0X9E, (byte) 0XDA, (byte) 0X3F, (byte) 0X01,
			(byte) 0XB2, (byte) 0XCA, (byte) 0X3F, (byte) 0X01, (byte) 0X56, (byte) 0X70, (byte) 0X2B, (byte) 0X5E,
			(byte) 0X06, (byte) 0X07, (byte) 0X08, (byte) 0X09, (byte) 0X0A, (byte) 0X0B, (byte) 0X0C, (byte) 0X0D,
			(byte) 0X0E, (byte) 0X0F, (byte) 0X10, (byte) 0X11, (byte) 0X12, (byte) 0X13, (byte) 0X14, (byte) 0X15,
			(byte) 0X16, (byte) 0X17, (byte) 0X18, (byte) 0X19, (byte) 0X1A, (byte) 0X1B, (byte) 0X1C, (byte) 0X1D,
			(byte) 0X1E, (byte) 0X1F, (byte) 0X21, (byte) 0X46, (byte) 0X01, (byte) 0X36, (byte) 0X01, (byte) 0X21,
			(byte) 0X47, (byte) 0X01, (byte) 0X36, (byte) 0X00, (byte) 0X7E, (byte) 0XFE, (byte) 0X09, (byte) 0XD2,
			(byte) 0X19, (byte) 0X01, (byte) 0X21, (byte) 0X46, (byte) 0X01, (byte) 0X7E, (byte) 0X17, (byte) 0XC2,
			(byte) 0X00, (byte) 0X01, (byte) 0XFF, (byte) 0X5F, (byte) 0X16, (byte) 0X00, (byte) 0X21, (byte) 0X48,
			(byte) 0X01, (byte) 0X19, (byte) 0X19, (byte) 0X4E, (byte) 0X79, (byte) 0X23, (byte) 0X46, (byte) 0X23,
			(byte) 0X96, (byte) 0X57, (byte) 0X78, (byte) 0X23, (byte) 0X9E, (byte) 0XDA, (byte) 0X3F, (byte) 0X01,
			(byte) 0XB2, (byte) 0XCA, (byte) 0X3F, (byte) 0X01, (byte) 0X56, (byte) 0X70, (byte) 0X2B, (byte) 0X5E,
			(byte) 0X06, (byte) 0X07, (byte) 0X08, (byte) 0X09, (byte) 0X0A, (byte) 0X0B, (byte) 0X0C, (byte) 0X0D,
			(byte) 0X0E, (byte) 0X0F, (byte) 0X10, (byte) 0X11, (byte) 0X12, (byte) 0X13, (byte) 0X14, (byte) 0X15,
			(byte) 0X16, (byte) 0X17, (byte) 0X18, (byte) 0X19, (byte) 0X1A, (byte) 0X1B, (byte) 0X1C, (byte) 0X1D,
			(byte) 0X1E, (byte) 0X1F, (byte) 0X21, (byte) 0X46, (byte) 0X01, (byte) 0X36, (byte) 0X01, (byte) 0X21,
			(byte) 0X47, (byte) 0X01, (byte) 0X36, (byte) 0X00, (byte) 0X7E, (byte) 0XFE, (byte) 0X09, (byte) 0XD2,
			(byte) 0X19, (byte) 0X01, (byte) 0X21, (byte) 0X46, (byte) 0X01, (byte) 0X7E, (byte) 0X17, (byte) 0XC2,
			(byte) 0X00, (byte) 0X01, (byte) 0XFF, (byte) 0X5F, (byte) 0X16, (byte) 0X00, (byte) 0X21, (byte) 0X48,
			(byte) 0X01, (byte) 0X19, (byte) 0X19, (byte) 0X4E, (byte) 0X79, (byte) 0X23, (byte) 0X46, (byte) 0X23,
			(byte) 0X96, (byte) 0X57, (byte) 0X78, (byte) 0X23, (byte) 0X9E, (byte) 0XDA, (byte) 0X3F, (byte) 0X01,
			(byte) 0XB2, (byte) 0XCA, (byte) 0X3F, (byte) 0X01, (byte) 0X56, (byte) 0X70, (byte) 0X2B, (byte) 0X5E,
			(byte) 0X71, (byte) 0X2B, (byte) 0X72, (byte) 0X2B };
	
	
	
	
	
	
	
	
	private HexEditPanelSB hexEditPanel;

	private void logMessage(String label, String message) {
		String msg = String.format(label + " = %s%n", message);
		System.out.println(msg);
		// textLog.append(msg);
	}// logMessage

	private void logMessage(String label, int value) {
		String msg = String.format(label + " = %d%n", value);
		System.out.println(msg);
		// textLog.append(msg);
	}// logMessage

	/* Standard Stuff */

	private void doBtnOne() {
		hexEditPanel.loadDocument(core);
	}// doBtnOne

	private void doBtnTwo() {

	}// doBtnTwo

	private void doBtnThree() {

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

	private void doEditCut() {

	}// doEditCut

	private void doEditCopy() {

	}// doEditCopy

	private void doEditPaste() {

	}// doEditPaste

	private void appClose() {
		Preferences myPrefs = Preferences.userNodeForPackage(HexEditPanelSBDriver.class);
		Dimension dim = frmTemplate.getSize();
		myPrefs.putInt("Height", dim.height);
		myPrefs.putInt("Width", dim.width);
		Point point = frmTemplate.getLocation();
		myPrefs.putInt("LocX", point.x);
		myPrefs.putInt("LocY", point.y);
		myPrefs.putInt("Divider", splitPane1.getDividerLocation());
		myPrefs = null;
	}// appClose

	private void appInit() {
		Preferences myPrefs = Preferences.userNodeForPackage(HexEditPanelSBDriver.class);
		frmTemplate.setSize(1096, 462);
		frmTemplate.setLocation(myPrefs.getInt("LocX", 100), myPrefs.getInt("LocY", 100));
		splitPane1.setDividerLocation(myPrefs.getInt("Divider", 250));
		myPrefs = null;
		int sourceSize =(66 * 1024) -1;
		source = new byte[sourceSize];
		
		for (int i = 0; i < sourceSize; i++){
			source[i] = (byte) (i % 256);
		}//for
		hexEditPanel.loadDocument(source);
//		hexEditPanel.loadDocument(core);
		
	}// appInit

	public HexEditPanelSBDriver() {
		initialize();
		appInit();
	}// Constructor

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTemplate = new JFrame();
		frmTemplate.setTitle("HexEditPanelSBDriver");
		frmTemplate.setBounds(100, 100, 450, 300);
		frmTemplate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 25, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		frmTemplate.getContentPane().setLayout(gridBagLayout);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		GridBagConstraints gbc_toolBar = new GridBagConstraints();
		gbc_toolBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_toolBar.insets = new Insets(0, 0, 5, 0);
		gbc_toolBar.gridx = 0;
		gbc_toolBar.gridy = 0;
		frmTemplate.getContentPane().add(toolBar, gbc_toolBar);

		btnOne = new JButton("1");
		btnOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doBtnOne();
			}
		});
		btnOne.setMaximumSize(new Dimension(70, 20));
		btnOne.setPreferredSize(new Dimension(50, 20));
		toolBar.add(btnOne);

		btnTwo = new JButton("2");
		btnTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doBtnTwo();
			}
		});
		btnTwo.setPreferredSize(new Dimension(30, 20));
		btnTwo.setMaximumSize(new Dimension(70, 20));
		toolBar.add(btnTwo);

		btnThree = new JButton("3");
		btnThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doBtnThree();
			}
		});
		btnThree.setPreferredSize(new Dimension(30, 20));
		btnThree.setMaximumSize(new Dimension(70, 20));
		toolBar.add(btnThree);

		btnFour = new JButton("4");
		btnFour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doBtnFour();
			}
		});
		btnFour.setPreferredSize(new Dimension(30, 20));
		btnFour.setMaximumSize(new Dimension(70, 20));
		toolBar.add(btnFour);

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
		gbl_panelLeft.columnWidths = new int[] { 0 };
		gbl_panelLeft.rowHeights = new int[] { 0 };
		gbl_panelLeft.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_panelLeft.rowWeights = new double[] { Double.MIN_VALUE };
		panelLeft.setLayout(gbl_panelLeft);

		JPanel panelRight = new JPanel();
		splitPane1.setRightComponent(panelRight);
		GridBagLayout gbl_panelRight = new GridBagLayout();
		gbl_panelRight.columnWidths = new int[] { 0, 0 };
		gbl_panelRight.rowHeights = new int[] { 0, 0 };
		gbl_panelRight.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panelRight.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelRight.setLayout(gbl_panelRight);

		hexEditPanel = new HexEditPanelSB();
		GridBagConstraints gbc_hexEditPanel = new GridBagConstraints();
		gbc_hexEditPanel.anchor = GridBagConstraints.WEST;
		gbc_hexEditPanel.fill = GridBagConstraints.VERTICAL;
		gbc_hexEditPanel.gridx = 0;
		gbc_hexEditPanel.gridy = 0;
		panelRight.add(hexEditPanel, gbc_hexEditPanel);
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

		JSeparator separator = new JSeparator();
		mnuFile.add(separator);

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

		JMenu mnuEdit = new JMenu("Edit");
		menuBar.add(mnuEdit);

		JMenuItem mnuEditCut = new JMenuItem("Cut");
		mnuEditCut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doEditCut();
			}
		});
		mnuEdit.add(mnuEditCut);

		JMenuItem mnuEditCopy = new JMenuItem("Copy");
		mnuEditCopy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doEditCopy();
			}
		});
		mnuEdit.add(mnuEditCopy);

		JMenuItem mnuEditPaste = new JMenuItem("Paste");
		mnuEditPaste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doEditPaste();
			}
		});
		mnuEdit.add(mnuEditPaste);

		frmTemplate.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				appClose();
			}
		});
	}// initialize

}// class GUItemplate