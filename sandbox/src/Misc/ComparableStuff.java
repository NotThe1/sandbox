package Misc;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

public class ComparableStuff {

	private JFrame frmTemplate;
	private JButton btnOne;
	private JButton btnTwo;
	private JButton btnThree;
	private JButton btnFour;
	private JTextArea taRight;
	private JTextArea taLeft;
	private ArrayList<String> original;
	private ArrayList<String> result;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ComparableStuff window = new ComparableStuff();
					window.frmTemplate.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				} // try
			}// run
		});
	}// main

	/* Standard Stuff */

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

	private void loadLists() {
		original = new ArrayList<String>();
		result = new ArrayList<String>();
		Scanner scanner = new Scanner(taLeft.getText());
		while (scanner.hasNextLine()) {
			String nextLine = scanner.nextLine();
			original.add(nextLine);
			result.add(nextLine);
		} // while
		// for ( String line:original){
		// taRight.append(line);
		// }//for
		sortMyList();
		for (String line : result) {
			taRight.append(line + System.lineSeparator());
		} // for

	}// loadLists

	private void sortMyList() {
		Collections.sort(result, new CaseInsensitiveSort());

	}// sortMyList

	class CaseInsensitiveSort<String> implements Comparator<String> {
		@Override
		public int compare(String arg0, String arg1) {
			return ((java.lang.String) arg0).compareToIgnoreCase((java.lang.String) arg1);
		}// compare
	}// CaseInsensitiveSort

	private void appClose() {
		Preferences myPrefs = Preferences.userNodeForPackage(ComparableStuff.class);
		Dimension dim = frmTemplate.getSize();
		myPrefs.putInt("Height", dim.height);
		myPrefs.putInt("Width", dim.width);
		Point point = frmTemplate.getLocation();
		myPrefs.putInt("LocX", point.x);
		myPrefs.putInt("LocY", point.y);
		myPrefs = null;
	}// appClose

	private void appInit() {
		Preferences myPrefs = Preferences.userNodeForPackage(ComparableStuff.class);
		frmTemplate.setSize(myPrefs.getInt("Width", 500), myPrefs.getInt("Height", 500));
		frmTemplate.setLocation(myPrefs.getInt("LocX", 100), myPrefs.getInt("LocY", 100));
		myPrefs = null;

		loadLists();
	}// appInit

	public ComparableStuff() {
		initialize();
		appInit();
	}// Constructor

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTemplate = new JFrame();
		frmTemplate.setTitle("Comparable Stuff");
		frmTemplate.setBounds(100, 100, 450, 300);
		frmTemplate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
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

		JSplitPane splitPane = new JSplitPane();
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.insets = new Insets(0, 0, 5, 0);
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 1;
		frmTemplate.getContentPane().add(splitPane, gbc_splitPane);

		JPanel panelLeft = new JPanel();
		splitPane.setLeftComponent(panelLeft);
		GridBagLayout gbl_panelLeft = new GridBagLayout();
		gbl_panelLeft.columnWidths = new int[] { 0, 0 };
		gbl_panelLeft.rowHeights = new int[] { 0, 0 };
		gbl_panelLeft.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelLeft.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelLeft.setLayout(gbl_panelLeft);

		JScrollPane scrollPaneLeft = new JScrollPane();
		GridBagConstraints gbc_scrollPaneLeft = new GridBagConstraints();
		gbc_scrollPaneLeft.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneLeft.gridx = 0;
		gbc_scrollPaneLeft.gridy = 0;
		panelLeft.add(scrollPaneLeft, gbc_scrollPaneLeft);

		taLeft = new JTextArea();
		taLeft.setText("ABLE\r\nBAKER\r\nCharlie\r\nDOG\r\ndoggie\r\nchimp\r\nbull\r\napple\r\n");
		scrollPaneLeft.setViewportView(taLeft);

		JPanel panelRight = new JPanel();
		splitPane.setRightComponent(panelRight);
		GridBagLayout gbl_panelRight = new GridBagLayout();
		gbl_panelRight.columnWidths = new int[] { 0, 0 };
		gbl_panelRight.rowHeights = new int[] { 0, 0 };
		gbl_panelRight.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelRight.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelRight.setLayout(gbl_panelRight);

		JScrollPane scrollPaneRight = new JScrollPane();
		GridBagConstraints gbc_scrollPaneRight = new GridBagConstraints();
		gbc_scrollPaneRight.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneRight.gridx = 0;
		gbc_scrollPaneRight.gridy = 0;
		panelRight.add(scrollPaneRight, gbc_scrollPaneRight);

		taRight = new JTextArea();
		scrollPaneRight.setViewportView(taRight);
		splitPane.setDividerLocation(200);

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