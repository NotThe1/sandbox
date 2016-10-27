package hexEdit;

import java.awt.Adjustable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

public class NoScrollPane implements AdjustmentListener {

	private StyledDocument doc;

	private JFrame frmTemplate;
	private JButton btnOne;
	private JButton btnTwo;
	private JButton btnThree;
	private JButton btnFour;
	private JSplitPane splitPane1;
	private JScrollBar scrollBar;
	private JSpinner spinnerExtent;
	private JSpinner spinnerRows;
	private JSpinner spinnerValue;
	private JTextArea textLog;
	private JTextPane textPane;
	private JSpinner spinnerMax;
	private JSpinner spinnerMin;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NoScrollPane window = new NoScrollPane();
					window.frmTemplate.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				} // try
			}// run
		});
	}// main
		// ------------------------------------

	private void logMessage(String label, String message) {
		String msg = String.format(label + " = %s%n", message);
		textLog.append(msg);
	}// logMessage

	private void logMessage(String label, int value) {
		String msg = String.format(label + " = %d%n", value);
		// System.out.println(msg);
		textLog.append(msg);
	}// logMessage

	// ---------------------------------------------------------
	private void fillTextPane() {
		int lineNumber = scrollBar.getValue();
		int numberOfRows = Math.min((int) spinnerExtent.getValue(), scrollBar.getMaximum() - lineNumber);
		doc = textPane.getStyledDocument();
		// String aLine;
		try {
			doc.remove(0, doc.getLength());
			for (int i = 0; i < numberOfRows; i++) {
				// doc.insertString(doc.getLength(), String.format("%04d%n", i +
				// 1), null);
				doc.insertString(doc.getLength(), String.format("%04d%n", lineNumber++), null);
			} // for
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // try

	}// fillTextPane()

	private void setScrollBarMertics() {
		BoundedRangeModel model = scrollBar.getModel();
		setExtent((int) spinnerExtent.getValue(), model);
		setMin((int) spinnerMin.getValue(), model);
		setMax((int) spinnerMax.getValue(), model);
		setValue((int) spinnerValue.getValue(), model);
	}// setScrollBarMertics

	private void setExtent(int amount, BoundedRangeModel model) {
		model.setExtent(amount);
	}// setExtent

	private void setMin(int amount, BoundedRangeModel model) {
		model.setMinimum(amount);
	}// setMin

	private void setMax(int amount, BoundedRangeModel model) {
		model.setMaximum(amount);
	}// setMax

	private void setValue(int amount, BoundedRangeModel model) {
		model.setValue(amount);
	}// setValue

	/* Standard Stuff */

	private void doBtnOne() {
		fillTextPane();
	}// doBtnOne

	private void doBtnTwo() {
		setScrollBarMertics();
	}// doBtnTwo

	private void doBtnThree() {
		Insets insets = textPane.getInsets();
		Dimension dimension = textPane.getSize();
		logMessage("Height", dimension.height);
		logMessage("Top", insets.top);
		logMessage("Bottom", insets.bottom);
		int useableHeight = dimension.height - (insets.bottom + insets.top);
		logMessage("Useable Height", useableHeight);

		Font font = textPane.getFont();
		int fontHeight = textPane.getFontMetrics(font).getHeight();
		logMessage("Font Height", fontHeight);

		int linesVisible = (int) (useableHeight / fontHeight);
		logMessage("Number of Lines", linesVisible);

	}// doBtnThree

	private void doBtnFour() {
		int fontHeight = calcFontHeight(textPane);

		int max = (int) spinnerRows.getValue();
		int extent = calcExtent(textPane);

		BoundedRangeModel model = scrollBar.getModel();

		setMin(0, model);
		spinnerMin.setValue(0);

		setValue((int) spinnerValue.getValue(), model);
		// spinnerValue.setValue(0);

		setMax(max, model);
		spinnerMax.setValue(max);

		setExtent(extent, model);
		spinnerExtent.setValue(extent);

	}// doBtnFour
	// -------------------------------------

	private void setUpScrollBar(JScrollBar thisScrollBar, JTextPane thisTextPane) {
		int fontHeight = calcFontHeight(thisTextPane);
		int max = maximumNumberOfRows(thisTextPane);
		int extent = calcExtent(textPane);

		BoundedRangeModel model = scrollBar.getModel();
		setMin(0, model);
		setMax(max, model);
		// setValue(0,model);
		setExtent(extent, model);

	}// setUpScrollBar

	private int calcUsableHeight(JTextPane thisPane) {
		Insets insets = thisPane.getInsets();
		Dimension dimension = thisPane.getSize();
		return dimension.height - (insets.bottom + insets.top);
	}// calcUsableHeight

	private int calcFontHeight(JTextPane thisPane) {
		Font font = textPane.getFont();
		return textPane.getFontMetrics(font).getHeight();
	}// calcFontHeight

	private int calcExtent(JTextPane thisPane) {
		int useableHeight = calcUsableHeight(thisPane);
		int fontHeight = calcFontHeight(thisPane);
		return (int) (useableHeight / fontHeight);
	}// calcExtent

	private int maximumNumberOfRows(JTextPane thisTextPane) {
		return (int) spinnerRows.getValue();
	}// maximumNumberOfRows

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

	@Override
	public void adjustmentValueChanged(AdjustmentEvent evt) {
		Adjustable source = evt.getAdjustable();
		if (evt.getValueIsAdjusting()) {
			return;
		} // if

		if (evt.getAdjustmentType() != AdjustmentEvent.TRACK) {
			return;
		}
		spinnerValue.setValue((int) evt.getValue());
		fillTextPane();

		// logMessage("Value", evt.getValue());
		// int orient = source.getOrientation();
		// if (orient == Adjustable.HORIZONTAL) {
		// System.out.println("from horizontal scrollbar");
		// } else {
		// System.out.println("from vertical scrollbar");
		// }//if Orientation

		// int type = evt.getAdjustmentType();
		// switch (type) {
		// case AdjustmentEvent.UNIT_INCREMENT:
		// System.out.println("Scrollbar was increased by one unit");
		// break;
		// case AdjustmentEvent.UNIT_DECREMENT:
		// System.out.println("Scrollbar was decreased by one unit");
		// break;
		// case AdjustmentEvent.BLOCK_INCREMENT:
		// System.out.println("Scrollbar was increased by one block");
		// break;
		// case AdjustmentEvent.BLOCK_DECREMENT:
		// System.out.println("Scrollbar was decreased by one block");
		// break;
		// case AdjustmentEvent.TRACK:
		// System.out.println("The knob on the scrollbar was dragged");
		// break;
		// }//switch

	}// adjustmentValueChanged

	private void appClose() {
		Preferences myPrefs = Preferences.userNodeForPackage(NoScrollPane.class);
		Dimension dim = frmTemplate.getSize();
		myPrefs.putInt("Height", dim.height);
		myPrefs.putInt("Width", dim.width);
		Point point = frmTemplate.getLocation();
		myPrefs.putInt("LocX", point.x);
		myPrefs.putInt("LocY", point.y);
		myPrefs.putInt("Divider", splitPane1.getDividerLocation());
		myPrefs.putInt("Rows", (int) spinnerRows.getValue());
		myPrefs.putInt("Extent", (int) spinnerExtent.getValue());
		myPrefs.putInt("Value", (int) spinnerValue.getValue());
		myPrefs.putInt("Min", (int) spinnerMin.getValue());
		myPrefs.putInt("Max", (int) spinnerMax.getValue());
		myPrefs = null;
	}// appClose

	private void appInit() {
		Preferences myPrefs = Preferences.userNodeForPackage(NoScrollPane.class);
		frmTemplate.setSize(580, 532);
		frmTemplate.setLocation(myPrefs.getInt("LocX", 100), myPrefs.getInt("LocY", 100));
		splitPane1.setDividerLocation(myPrefs.getInt("Divider", 250));
		spinnerRows.setValue(myPrefs.getInt("Rows", 250));
		spinnerExtent.setValue(myPrefs.getInt("Extent", 2500));
		spinnerValue.setValue(myPrefs.getInt("Value", 0));
		spinnerMin.setValue(myPrefs.getInt("Min", 0));
		spinnerMax.setValue(myPrefs.getInt("Max", 5000));
		myPrefs = null;
		fillTextPane();
		setScrollBarMertics();

		scrollBar.addAdjustmentListener(this);
	}// appInit

	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
	public NoScrollPane() {
		initialize();
		appInit();
	}// Constructor

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTemplate = new JFrame();
		frmTemplate.setTitle("NoScrollPane");
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

		btnOne = new JButton("Fill Text Pane");
		btnOne.setHorizontalAlignment(SwingConstants.LEFT);
		btnOne.setToolTipText("Fill Text Pane");
		btnOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doBtnOne();
			}
		});
		btnOne.setMaximumSize(new Dimension(70, 20));
		btnOne.setPreferredSize(new Dimension(50, 20));
		toolBar.add(btnOne);

		btnTwo = new JButton("Set Scroll Model");
		btnTwo.setHorizontalAlignment(SwingConstants.LEFT);
		btnTwo.setToolTipText("Set Scroll Model");
		btnTwo.setMinimumSize(new Dimension(130, 23));
		btnTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doBtnTwo();
			}
		});
		btnTwo.setPreferredSize(new Dimension(40, 20));
		btnTwo.setMaximumSize(new Dimension(70, 20));
		toolBar.add(btnTwo);

		btnThree = new JButton("Get Text Metrics");
		btnThree.setToolTipText("Get Text Metrics");
		btnThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doBtnThree();
			}
		});
		btnThree.setPreferredSize(new Dimension(30, 20));
		btnThree.setMaximumSize(new Dimension(70, 20));
		toolBar.add(btnThree);

		btnFour = new JButton("Calc Scroll Model");
		btnFour.setToolTipText("Calc Scroll Model");
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

		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;
		panelLeft.add(splitPane, gbc_splitPane);

		JPanel panelTop = new JPanel();
		splitPane.setLeftComponent(panelTop);
		GridBagLayout gbl_panelTop = new GridBagLayout();
		gbl_panelTop.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panelTop.rowHeights = new int[] { 0, 5, 0, 0, 0, 0, 0, 0 };
		gbl_panelTop.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelTop.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelTop.setLayout(gbl_panelTop);

		JLabel lblRows = new JLabel("Rows ");
		GridBagConstraints gbc_lblRows = new GridBagConstraints();
		gbc_lblRows.insets = new Insets(0, 0, 5, 5);
		gbc_lblRows.gridx = 0;
		gbc_lblRows.gridy = 0;
		panelTop.add(lblRows, gbc_lblRows);

		spinnerRows = new JSpinner();
		spinnerRows.setPreferredSize(new Dimension(60, 20));
		GridBagConstraints gbc_spinnerRows = new GridBagConstraints();
		gbc_spinnerRows.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerRows.gridx = 1;
		gbc_spinnerRows.gridy = 0;
		panelTop.add(spinnerRows, gbc_spinnerRows);

		JLabel lblExtent = new JLabel("Extent");
		GridBagConstraints gbc_lblExtent = new GridBagConstraints();
		gbc_lblExtent.insets = new Insets(0, 0, 5, 5);
		gbc_lblExtent.gridx = 0;
		gbc_lblExtent.gridy = 2;
		panelTop.add(lblExtent, gbc_lblExtent);

		spinnerExtent = new JSpinner();
		spinnerExtent.setPreferredSize(new Dimension(60, 20));
		GridBagConstraints gbc_spinnerExtent = new GridBagConstraints();
		gbc_spinnerExtent.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerExtent.gridx = 1;
		gbc_spinnerExtent.gridy = 2;
		panelTop.add(spinnerExtent, gbc_spinnerExtent);

		JLabel lblValue = new JLabel("Value");
		GridBagConstraints gbc_lblValue = new GridBagConstraints();
		gbc_lblValue.insets = new Insets(0, 0, 5, 5);
		gbc_lblValue.gridx = 0;
		gbc_lblValue.gridy = 4;
		panelTop.add(lblValue, gbc_lblValue);

		spinnerValue = new JSpinner();
		spinnerValue.setPreferredSize(new Dimension(60, 20));
		GridBagConstraints gbc_spinnerValue = new GridBagConstraints();
		gbc_spinnerValue.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerValue.gridx = 1;
		gbc_spinnerValue.gridy = 4;
		panelTop.add(spinnerValue, gbc_spinnerValue);

		JLabel label = new JLabel("Min");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 2;
		gbc_label.gridy = 5;
		panelTop.add(label, gbc_label);

		spinnerMin = new JSpinner();
		spinnerMin.setPreferredSize(new Dimension(60, 20));
		GridBagConstraints gbc_spinnerMin = new GridBagConstraints();
		gbc_spinnerMin.insets = new Insets(0, 0, 5, 0);
		gbc_spinnerMin.gridx = 3;
		gbc_spinnerMin.gridy = 5;
		panelTop.add(spinnerMin, gbc_spinnerMin);

		JLabel lblMax = new JLabel("Max");
		GridBagConstraints gbc_lblMax = new GridBagConstraints();
		gbc_lblMax.insets = new Insets(0, 0, 0, 5);
		gbc_lblMax.gridx = 2;
		gbc_lblMax.gridy = 6;
		panelTop.add(lblMax, gbc_lblMax);

		spinnerMax = new JSpinner();
		spinnerMax.setPreferredSize(new Dimension(60, 20));
		GridBagConstraints gbc_spinnerMax = new GridBagConstraints();
		gbc_spinnerMax.gridx = 3;
		gbc_spinnerMax.gridy = 6;
		panelTop.add(spinnerMax, gbc_spinnerMax);

		JPanel panelBottom = new JPanel();
		splitPane.setRightComponent(panelBottom);
		GridBagLayout gbl_panelBottom = new GridBagLayout();
		gbl_panelBottom.columnWidths = new int[] { 0, 0 };
		gbl_panelBottom.rowHeights = new int[] { 0, 0 };
		gbl_panelBottom.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelBottom.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelBottom.setLayout(gbl_panelBottom);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panelBottom.add(scrollPane, gbc_scrollPane);

		textLog = new JTextArea();
		textLog.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() > 1) {
					textLog.setText("");
				}
			}
		});
		scrollPane.setViewportView(textLog);
		splitPane.setDividerLocation(200);

		JPanel panelRight = new JPanel();
		splitPane1.setRightComponent(panelRight);
		GridBagLayout gbl_panelRight = new GridBagLayout();
		gbl_panelRight.columnWidths = new int[] { 0, 25, 0 };
		gbl_panelRight.rowHeights = new int[] { 0, 0 };
		gbl_panelRight.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelRight.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelRight.setLayout(gbl_panelRight);

		textPane = new JTextPane();
		textPane.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				// logMessage("Resized",1);
				doBtnFour();
			}
		});
		textPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.insets = new Insets(0, 0, 0, 5);
		gbc_textPane.fill = GridBagConstraints.BOTH;
		gbc_textPane.gridx = 0;
		gbc_textPane.gridy = 0;
		panelRight.add(textPane, gbc_textPane);

		scrollBar = new JScrollBar();
		GridBagConstraints gbc_scrollBar = new GridBagConstraints();
		gbc_scrollBar.fill = GridBagConstraints.VERTICAL;
		gbc_scrollBar.gridx = 1;
		gbc_scrollBar.gridy = 0;
		panelRight.add(scrollBar, gbc_scrollBar);
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

		JSeparator separator_3 = new JSeparator();
		mnuEdit.add(separator_3);

		JMenuItem mnuEditFont = new JMenuItem("Font");
		mnuEditFont.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						Font font = null;
						JFontChooser fontChooser = new JFontChooser();
						int result = fontChooser.showDialog(null);
						if (result == JFontChooser.OK_OPTION) {
							font = fontChooser.getSelectedFont();
							// System.out.println("Selected Font : " + font);
							textPane.setFont(font);
							textPane.repaint();
						} // if
					}
				});

			}// actionPerformed
		});
		mnuEdit.add(mnuEditFont);

		frmTemplate.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				appClose();
			}
		});
	}// initialize

}// class GUItemplate