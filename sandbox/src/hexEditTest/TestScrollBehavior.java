package hexEditTest;

import java.awt.Adjustable;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import javax.swing.BoundedRangeModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

public class TestScrollBehavior implements AdjustmentListener {

	private StyledDocument doc;

	private JFrame frmTemplate;
	private JButton btnOne;
	private JButton btnTwo;
	private JButton btnThree;
	private JButton btnFour;
	private JSplitPane splitPane1;
	private JSplitPane splitPane2;
	private JTextPane textPane;
	private JScrollPane scrollPane;
	private JSpinner spinner1;
	private JTextArea logPane;
	private JSpinner spinner2;
	private JSpinner spinner3;
	private JSpinner spinner4;
	private JScrollBar myScrollBar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestScrollBehavior window = new TestScrollBehavior();
					window.frmTemplate.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				} // try
			}// run
		});
	}// main
	// javax.swing.SwingUtilities.invokeLater(new Runnable(){
	// public void run(){
	// stuff
	// }
	// });

	/* Standard Stuff */

	private void showScrollBarMetrics(JScrollBar scrollBar) {
		BoundedRangeModel model = scrollBar.getModel();
		int extent = model.getExtent();
		int max = model.getMaximum();
		int min = model.getMinimum();
		int value = model.getValue();

		spinner2.setValue(extent);
		spinner3.setValue(max);
		spinner4.setValue(value);

		// logMessage("Extent",extent);
		// logMessage("Maximum",max);
		// logMessage("Minimum",min);
		// logMessage("Value",value);

		logMessage("VisibleAmount", scrollBar.getVisibleAmount());
	}// showScrollBarMetrics

	private void setScrollBarMertics(JScrollBar scrollBar) {
		BoundedRangeModel model = scrollBar.getModel();
		setExtent((int) spinner2.getValue(), model);
		setMax((int) spinner3.getValue(), model);
		setValue((int) spinner4.getValue(), model);
	}// setScrollBarMertics

	private void setExtent(int amount, BoundedRangeModel model) {
		model.setExtent(amount);
	}// setExtent

	private void setMax(int amount, BoundedRangeModel model) {
		model.setMaximum(amount);
	}// setExtent

	private void setValue(int amount, BoundedRangeModel model) {
		model.setValue(amount);
	}// setExtent

	private void fillTextPane() {
		int numberOfRows = (int) spinner1.getValue();

		doc = textPane.getStyledDocument();
		// String aLine;
		try {
			doc.remove(0, doc.getLength());
			for (int i = 0; i < numberOfRows; i++) {
				doc.insertString(doc.getLength(), String.format("%04d%n", i + 1), null);
			} // for
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // try

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				scrollPane.getVerticalScrollBar().setValue(0);
			}
		});
		showScrollBarMetrics(scrollPane.getVerticalScrollBar());

	}// fillTextPane

	private void logMessage(String label, String message) {
		String msg = String.format(label + " = %s%n", message);
		logPane.append(msg);
	}// logMessage

	private void logMessage(String label, int value) {
		String msg = String.format(label + " = %d%n", value);
		logPane.append(msg);
	}// logMessage

	// ---------------------------------------------------------

	private void doBtnOne() {
		fillTextPane();
	}// doBtnOne

	private void doBtnTwo() {
		showScrollBarMetrics(scrollPane.getVerticalScrollBar());
	}// doBtnTwo

	private void doBtnThree() {
		setScrollBarMertics(scrollPane.getVerticalScrollBar());
	}// doBtnThree

	private void doBtnFour() {
		myScrollBar.setMaximum((int)spinner1.getValue());
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

	// ---------------------------------------------------------

	private void appClose() {
		Preferences myPrefs = Preferences.userNodeForPackage(TestScrollBehavior.class);
		Dimension dim = frmTemplate.getSize();
		myPrefs.putInt("Height", dim.height);
		myPrefs.putInt("Width", dim.width);
		Point point = frmTemplate.getLocation();
		myPrefs.putInt("LocX", point.x);
		myPrefs.putInt("LocY", point.y);
		myPrefs.putInt("Divider1", splitPane1.getDividerLocation());
		myPrefs.putInt("Divider2", splitPane2.getDividerLocation());
		myPrefs.putInt("NumberOfRows", (int) spinner1.getValue());

		myPrefs = null;
	}// appClose

	private void appInit() {
		Preferences myPrefs = Preferences.userNodeForPackage(TestScrollBehavior.class);
		frmTemplate.setSize(740, 596);
		frmTemplate.setLocation(myPrefs.getInt("LocX", 100), myPrefs.getInt("LocY", 100));
		splitPane1.setDividerLocation(myPrefs.getInt("Divider1", 250));
		splitPane2.setDividerLocation(myPrefs.getInt("Divider2", 250));
		spinner1.setValue(myPrefs.getInt("NumberOfRows", 100));

		myPrefs = null;
		scrollPane.getVerticalScrollBar().addAdjustmentListener(this);

		fillTextPane();
	}// appInit
		// ---------------------------------------------------------

	public TestScrollBehavior() {
		initialize();
		appInit();
	}// Constructor

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTemplate = new JFrame();
		frmTemplate.setTitle("TestScrollBehavior");
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

		btnOne = new JButton("Fill text");
		btnOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doBtnOne();
			}
		});
		btnOne.setMaximumSize(new Dimension(70, 20));
		btnOne.setPreferredSize(new Dimension(50, 20));
		toolBar.add(btnOne);

		btnTwo = new JButton("Get Metrics");
		btnTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doBtnTwo();
			}
		});
		btnTwo.setPreferredSize(new Dimension(30, 20));
		btnTwo.setMaximumSize(new Dimension(70, 20));
		toolBar.add(btnTwo);

		btnThree = new JButton("Set Metrics");
		btnThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doBtnThree();
			}
		});
		btnThree.setPreferredSize(new Dimension(30, 20));
		btnThree.setMaximumSize(new Dimension(70, 20));
		toolBar.add(btnThree);

		btnFour = new JButton("Set Scroll Model");
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
		gbl_panelLeft.columnWidths = new int[] { 0, 0 };
		gbl_panelLeft.rowHeights = new int[] { 0, 0 };
		gbl_panelLeft.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelLeft.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelLeft.setLayout(gbl_panelLeft);

		splitPane2 = new JSplitPane();
		splitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		GridBagConstraints gbc_splitPane2 = new GridBagConstraints();
		gbc_splitPane2.fill = GridBagConstraints.BOTH;
		gbc_splitPane2.gridx = 0;
		gbc_splitPane2.gridy = 0;
		panelLeft.add(splitPane2, gbc_splitPane2);

		JPanel panelLeftTop = new JPanel();
		splitPane2.setLeftComponent(panelLeftTop);
		GridBagLayout gbl_panelLeftTop = new GridBagLayout();
		gbl_panelLeftTop.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelLeftTop.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panelLeftTop.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelLeftTop.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelLeftTop.setLayout(gbl_panelLeftTop);

		JLabel lblNumberOfRows = new JLabel("Number of Rows");
		GridBagConstraints gbc_lblNumberOfRows = new GridBagConstraints();
		gbc_lblNumberOfRows.insets = new Insets(0, 0, 5, 5);
		gbc_lblNumberOfRows.gridx = 0;
		gbc_lblNumberOfRows.gridy = 0;
		panelLeftTop.add(lblNumberOfRows, gbc_lblNumberOfRows);

		spinner1 = new JSpinner();
		spinner1.setPreferredSize(new Dimension(60, 20));
		spinner1.setMinimumSize(new Dimension(60, 20));
		GridBagConstraints gbc_spinner1 = new GridBagConstraints();
		gbc_spinner1.insets = new Insets(0, 0, 5, 0);
		gbc_spinner1.gridx = 1;
		gbc_spinner1.gridy = 0;
		panelLeftTop.add(spinner1, gbc_spinner1);

		JLabel lblExtent = new JLabel("Extent");
		GridBagConstraints gbc_lblExtent = new GridBagConstraints();
		gbc_lblExtent.insets = new Insets(0, 0, 5, 5);
		gbc_lblExtent.gridx = 0;
		gbc_lblExtent.gridy = 1;
		panelLeftTop.add(lblExtent, gbc_lblExtent);

		spinner2 = new JSpinner();
		spinner2.setPreferredSize(new Dimension(60, 20));
		spinner2.setMinimumSize(new Dimension(60, 20));
		GridBagConstraints gbc_spinner2 = new GridBagConstraints();
		gbc_spinner2.insets = new Insets(0, 0, 5, 0);
		gbc_spinner2.gridx = 1;
		gbc_spinner2.gridy = 1;
		panelLeftTop.add(spinner2, gbc_spinner2);

		JLabel lblMax = new JLabel("Max");
		GridBagConstraints gbc_lblMax = new GridBagConstraints();
		gbc_lblMax.insets = new Insets(0, 0, 5, 5);
		gbc_lblMax.gridx = 0;
		gbc_lblMax.gridy = 2;
		panelLeftTop.add(lblMax, gbc_lblMax);

		spinner3 = new JSpinner();
		spinner3.setPreferredSize(new Dimension(60, 20));
		spinner3.setMinimumSize(new Dimension(60, 20));
		GridBagConstraints gbc_spinner3 = new GridBagConstraints();
		gbc_spinner3.insets = new Insets(0, 0, 5, 0);
		gbc_spinner3.gridx = 1;
		gbc_spinner3.gridy = 2;
		panelLeftTop.add(spinner3, gbc_spinner3);

		JLabel lblValue = new JLabel("Value");
		GridBagConstraints gbc_lblValue = new GridBagConstraints();
		gbc_lblValue.insets = new Insets(0, 0, 0, 5);
		gbc_lblValue.gridx = 0;
		gbc_lblValue.gridy = 3;
		panelLeftTop.add(lblValue, gbc_lblValue);

		spinner4 = new JSpinner();
		spinner4.setPreferredSize(new Dimension(60, 20));
		spinner4.setMinimumSize(new Dimension(60, 20));
		GridBagConstraints gbc_spinner4 = new GridBagConstraints();
		gbc_spinner4.gridx = 1;
		gbc_spinner4.gridy = 3;
		panelLeftTop.add(spinner4, gbc_spinner4);

		JPanel panelLeftBottom = new JPanel();
		splitPane2.setRightComponent(panelLeftBottom);
		GridBagLayout gbl_panelLeftBottom = new GridBagLayout();
		gbl_panelLeftBottom.columnWidths = new int[] { 0, 0 };
		gbl_panelLeftBottom.rowHeights = new int[] { 0, 0 };
		gbl_panelLeftBottom.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelLeftBottom.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelLeftBottom.setLayout(gbl_panelLeftBottom);

		JScrollPane logScroll = new JScrollPane();
		GridBagConstraints gbc_logScroll = new GridBagConstraints();
		gbc_logScroll.fill = GridBagConstraints.BOTH;
		gbc_logScroll.gridx = 0;
		gbc_logScroll.gridy = 0;
		panelLeftBottom.add(logScroll, gbc_logScroll);

		logPane = new JTextArea();
		logPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() > 1) {
					logPane.setText(null);
				} // if
			}// mouseClicked
		});
		logScroll.setViewportView(logPane);
		splitPane2.setDividerLocation(200);

		JPanel panelRight = new JPanel();
		splitPane1.setRightComponent(panelRight);
		GridBagLayout gbl_panelRight = new GridBagLayout();
		gbl_panelRight.columnWidths = new int[] { 0, 0, 0 };
		gbl_panelRight.rowHeights = new int[] { 0, 0 };
		gbl_panelRight.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panelRight.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelRight.setLayout(gbl_panelRight);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panelRight.add(scrollPane, gbc_scrollPane);

		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		
		JPanel panelTestScrollBar = new JPanel();
		GridBagConstraints gbc_panelTestScrollBar = new GridBagConstraints();
		gbc_panelTestScrollBar.fill = GridBagConstraints.BOTH;
		gbc_panelTestScrollBar.gridx = 1;
		gbc_panelTestScrollBar.gridy = 0;
		panelRight.add(panelTestScrollBar, gbc_panelTestScrollBar);
		GridBagLayout gbl_panelTestScrollBar = new GridBagLayout();
		gbl_panelTestScrollBar.columnWidths = new int[]{0, 0, 0};
		gbl_panelTestScrollBar.rowHeights = new int[]{0, 0};
		gbl_panelTestScrollBar.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panelTestScrollBar.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelTestScrollBar.setLayout(gbl_panelTestScrollBar);
		
		JTextPane textPaneTest = new JTextPane();
		GridBagConstraints gbc_textPaneTest = new GridBagConstraints();
		gbc_textPaneTest.insets = new Insets(0, 0, 0, 5);
		gbc_textPaneTest.fill = GridBagConstraints.BOTH;
		gbc_textPaneTest.gridx = 0;
		gbc_textPaneTest.gridy = 0;
		panelTestScrollBar.add(textPaneTest, gbc_textPaneTest);
		
		myScrollBar = new JScrollBar();
		GridBagConstraints gbc_myScrollBar = new GridBagConstraints();
		gbc_myScrollBar.fill = GridBagConstraints.VERTICAL;
		gbc_myScrollBar.gridx = 1;
		gbc_myScrollBar.gridy = 0;
		panelTestScrollBar.add(myScrollBar, gbc_myScrollBar);
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

	@Override
	public void adjustmentValueChanged(AdjustmentEvent evt) {
		Adjustable source = evt.getAdjustable();
		if (evt.getValueIsAdjusting()) {
			return;
		}//if

		int orient = source.getOrientation();
		if (orient == Adjustable.HORIZONTAL) {
			System.out.println("from horizontal scrollbar");
		} else {
			System.out.println("from vertical scrollbar");
		}//if Orientation
		
		 int type = evt.getAdjustmentType();
		    switch (type) {
		    case AdjustmentEvent.UNIT_INCREMENT:
		      System.out.println("Scrollbar was increased by one unit");
		      break;
		    case AdjustmentEvent.UNIT_DECREMENT:
		      System.out.println("Scrollbar was decreased by one unit");
		      break;
		    case AdjustmentEvent.BLOCK_INCREMENT:
		      System.out.println("Scrollbar was increased by one block");
		      break;
		    case AdjustmentEvent.BLOCK_DECREMENT:
		      System.out.println("Scrollbar was decreased by one block");
		      break;
		    case AdjustmentEvent.TRACK:
		      System.out.println("The knob on the scrollbar was dragged");
		      break;
		    }//switch
		

	}//adjustmentValueChanged

}// class GUItemplate