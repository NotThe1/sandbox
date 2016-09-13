package tableInJEditorPane;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JMenuBar;
import javax.swing.JToolBar;

import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;

public class TableInText {

	private JFrame frmReflec;
	private JButton btnOne;
	private JButton btnTwo;
	private JButton btnThree;
	private JButton btnFour;
	private JSplitPane splitPane;
	private JPanel panelLeft;
	private JSplitPane splitPaneRight;
	private JScrollPane scrollPane;
	private JTextPane textPane1;
	MyStyledDocument doc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TableInText window = new TableInText();
					window.frmReflec.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}// main

	/* Standard Stuff */

	private void appClose() {
		Preferences myPrefs = Preferences.userNodeForPackage(TableInText.class);
		Dimension dim = frmReflec.getSize();
		myPrefs.putInt("Height", dim.height);
		myPrefs.putInt("Width", dim.width);
		Point point = frmReflec.getLocation();
		myPrefs.putInt("LocX", point.x);
		myPrefs.putInt("LocY", point.y);
		myPrefs = null;
	}//appClose

	private void appInit() {
		Preferences myPrefs = Preferences.userNodeForPackage(TableInText.class);
		frmReflec.setSize(myPrefs.getInt("Width", 500), myPrefs.getInt("Height", 500));
		frmReflec.setLocation(myPrefs.getInt("LocX", 100), myPrefs.getInt("LocY", 100));
		myPrefs = null;
		appInit1();
	}// appInit
	private void appInit1(){
		doc = (MyStyledDocument) textPane1.getStyledDocument();
	}

	public TableInText() {
		initialize();
		appInit();
	}// Constructor

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmReflec = new JFrame();
		frmReflec.setTitle("GUItemplate");
		frmReflec.setBounds(100, 100, 450, 300);
		frmReflec.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		frmReflec.getContentPane().setLayout(gridBagLayout);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		GridBagConstraints gbc_toolBar = new GridBagConstraints();
		gbc_toolBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_toolBar.insets = new Insets(0, 0, 5, 0);
		gbc_toolBar.gridx = 0;
		gbc_toolBar.gridy = 0;
		frmReflec.getContentPane().add(toolBar, gbc_toolBar);
		
		btnOne = new JButton("1");
		btnOne.setMaximumSize(new Dimension(30, 20));
		btnOne.setPreferredSize(new Dimension(30, 20));
		toolBar.add(btnOne);
		
		btnTwo = new JButton("2");
		btnTwo.setPreferredSize(new Dimension(30, 20));
		btnTwo.setMaximumSize(new Dimension(30, 20));
		toolBar.add(btnTwo);
		
		btnThree = new JButton("3");
		btnThree.setPreferredSize(new Dimension(30, 20));
		btnThree.setMaximumSize(new Dimension(30, 20));
		toolBar.add(btnThree);
		
		btnFour = new JButton("4");
		btnFour.setPreferredSize(new Dimension(30, 20));
		btnFour.setMaximumSize(new Dimension(30, 20));
		toolBar.add(btnFour);
		
		splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.insets = new Insets(0, 0, 5, 0);
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 1;
		frmReflec.getContentPane().add(splitPane, gbc_splitPane);
		
		panelLeft = new JPanel();
		splitPane.setLeftComponent(panelLeft);
		GridBagLayout gbl_panelLeft = new GridBagLayout();
		gbl_panelLeft.columnWidths = new int[]{0, 0};
		gbl_panelLeft.rowHeights = new int[]{0, 0};
		gbl_panelLeft.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelLeft.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelLeft.setLayout(gbl_panelLeft);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panelLeft.add(scrollPane, gbc_scrollPane);
		
		textPane1 = new JTextPane();
		scrollPane.setViewportView(textPane1);
		
		splitPaneRight = new JSplitPane();
		splitPaneRight.setOneTouchExpandable(true);
		splitPaneRight.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(splitPaneRight);
		splitPaneRight.setDividerLocation(300);
		splitPane.setDividerLocation(300);

		JMenuBar menuBar = new JMenuBar();
		frmReflec.setJMenuBar(menuBar);
		frmReflec.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				appClose();
			}
		});
	}// initialize

}// class GUItemplate