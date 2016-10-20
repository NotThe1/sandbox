package hexEdit;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;

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
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

public class ViewPortExploration {

	private StyledDocument doc;

	private JFrame frmTemplate;
	private JButton btnOne;
	private JButton btnTwo;
	private JButton btnThree;
	private JButton btnFour;
	private JTextPane textPane;
	private JScrollPane scrollPane;
	private JTextArea logPane;
	private JSpinner spinner1;
	private JSpinner spinner2;
	private JSpinner spinner3;
//	private String fontName = "Arielcc";
//	private String fontName = "Courier New";
//	private String fontName = "Tahoma";
	private String fontName;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewPortExploration window = new ViewPortExploration();
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

	private void appClose() {
		Preferences myPrefs = Preferences.userNodeForPackage(ViewPortExploration.class);
		Dimension dim = frmTemplate.getSize();
		myPrefs.putInt("Height", dim.height);
		myPrefs.putInt("Width", dim.width);
		Point point = frmTemplate.getLocation();
		myPrefs.putInt("LocX", point.x);
		myPrefs.putInt("LocY", point.y);
		myPrefs = null;
	}// appClose
	
	private void calcViewDim(JTextPane textPane,JScrollPane scrollPane){
		
		logPane.setText(null);
		textPane.setFont(new Font("Tahoma", Font.PLAIN, (int) spinner2.getValue()));
		JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
		Font font = textPane.getFont(); 
		
		String msg = String.format("Font size = %d%n%n", font.getSize());
		logPane.append(msg);
		
		textPane.getFontMetrics(font).getHeight();
		msg = String.format("textPane.getFontMetrics(font).getHeight() = %d%n%n", textPane.getFontMetrics(font).getHeight());
		logPane.append(msg);
		
		 msg = String.format("scrollBar.getValue() = %d%n", scrollBar.getValue());
		logPane.append(msg);
		
		 msg = String.format("scrollBar.getMaximum() = %d%n%n", scrollBar.getMaximum());
		logPane.append(msg);
		
		 msg = String.format("scrollBar.getVisibleAmount() = %d%n", scrollBar.getVisibleAmount());
		logPane.append(msg);
		
//		 msg = String.format("scrollPane.getViewport().getHeight() = %d%n", scrollPane.getViewport().getHeight());
//		logPane.append(msg);
//		
		
	}

	private void appInit() {
		Preferences myPrefs = Preferences.userNodeForPackage(ViewPortExploration.class);
		frmTemplate.setSize(816, 532);
		frmTemplate.setLocation(myPrefs.getInt("LocX", 100), myPrefs.getInt("LocY", 100));
		myPrefs = null;

		doc = textPane.getStyledDocument();
//		String aLine;
		try {
			doc.remove(0, doc.getLength());
			for (int i = 0; i < 1000; i++) {
				doc.insertString(doc.getLength(), String.format("%04d%n", i), null);
			}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}// appInit

	public ViewPortExploration() {
		initialize();
		appInit();
	}// Constructor

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTemplate = new JFrame();
		frmTemplate.setTitle("GUItemplate");
		frmTemplate.setBounds(100, 100, 450, 300);
		frmTemplate.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 200, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 450, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		frmTemplate.getContentPane().setLayout(gridBagLayout);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		GridBagConstraints gbc_toolBar = new GridBagConstraints();
		gbc_toolBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_toolBar.insets = new Insets(0, 0, 5, 0);
		gbc_toolBar.gridx = 1;
		gbc_toolBar.gridy = 1;
		frmTemplate.getContentPane().add(toolBar, gbc_toolBar);

		btnOne = new JButton("1");
		btnOne.setToolTipText("Metrics");
		btnOne.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fontName = textPane.getFont().getFontName();
				textPane.setFont(new Font(fontName, Font.PLAIN, (int) spinner2.getValue()));
				calcViewDim( textPane, scrollPane);				
				calcViewDim( textPane, scrollPane);				
			}
		});
		btnOne.setMaximumSize(new Dimension(30, 20));
		btnOne.setPreferredSize(new Dimension(30, 20));
		toolBar.add(btnOne);

		btnTwo = new JButton("2");
		btnTwo.setToolTipText("Position");
		btnTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
				scrollBar.setValue((int) spinner1.getValue());
			}
		});
		btnTwo.setPreferredSize(new Dimension(30, 20));
		btnTwo.setMaximumSize(new Dimension(30, 20));
		toolBar.add(btnTwo);

		btnThree = new JButton("3");
		btnThree.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnThree.setToolTipText("New Font");
		btnThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textPane.setFont(new Font(fontName, Font.PLAIN, (int) spinner2.getValue()));
			}
		});
		btnThree.setPreferredSize(new Dimension(30, 20));
		btnThree.setMaximumSize(new Dimension(30, 20));
		toolBar.add(btnThree);

		btnFour = new JButton("4");
		btnFour.setPreferredSize(new Dimension(30, 20));
		btnFour.setMaximumSize(new Dimension(30, 20));
		toolBar.add(btnFour);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		frmTemplate.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		spinner1 = new JSpinner();
		spinner1.setPreferredSize(new Dimension(50, 20));
		GridBagConstraints gbc_spinner1 = new GridBagConstraints();
		gbc_spinner1.anchor = GridBagConstraints.EAST;
		gbc_spinner1.insets = new Insets(0, 0, 5, 5);
		gbc_spinner1.gridx = 0;
		gbc_spinner1.gridy = 1;
		panel.add(spinner1, gbc_spinner1);
		
		JLabel lblBarPosition = new JLabel("Bar position");
		GridBagConstraints gbc_lblBarPosition = new GridBagConstraints();
		gbc_lblBarPosition.insets = new Insets(0, 0, 5, 0);
		gbc_lblBarPosition.gridx = 1;
		gbc_lblBarPosition.gridy = 1;
		panel.add(lblBarPosition, gbc_lblBarPosition);
		
		spinner2 = new JSpinner();
		spinner2.setModel(new SpinnerNumberModel(new Integer(5), new Integer(5), null, new Integer(1)));
		spinner2.setPreferredSize(new Dimension(50, 20));
		GridBagConstraints gbc_spinner2 = new GridBagConstraints();
		gbc_spinner2.anchor = GridBagConstraints.EAST;
		gbc_spinner2.insets = new Insets(0, 0, 5, 5);
		gbc_spinner2.gridx = 0;
		gbc_spinner2.gridy = 2;
		panel.add(spinner2, gbc_spinner2);
		
		JLabel lblFontSize = new JLabel("Font Size");
		GridBagConstraints gbc_lblFontSize = new GridBagConstraints();
		gbc_lblFontSize.insets = new Insets(0, 0, 5, 0);
		gbc_lblFontSize.gridx = 1;
		gbc_lblFontSize.gridy = 2;
		panel.add(lblFontSize, gbc_lblFontSize);
		
		spinner3 = new JSpinner();
		spinner3.setPreferredSize(new Dimension(50, 20));
		GridBagConstraints gbc_spinner3 = new GridBagConstraints();
		gbc_spinner3.insets = new Insets(0, 0, 5, 5);
		gbc_spinner3.gridx = 0;
		gbc_spinner3.gridy = 3;
		panel.add(spinner3, gbc_spinner3);
		
		logPane = new JTextArea();
		logPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount()>1){
					logPane.setText(null);
				}
			}
		});
		GridBagConstraints gbc_logPane = new GridBagConstraints();
		gbc_logPane.insets = new Insets(0, 0, 0, 5);
		gbc_logPane.fill = GridBagConstraints.BOTH;
		gbc_logPane.gridx = 0;
		gbc_logPane.gridy = 7;
		panel.add(logPane, gbc_logPane);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		frmTemplate.getContentPane().add(scrollPane, gbc_scrollPane);

		textPane = new JTextPane();
		textPane.setFont(new Font("Symbol", Font.PLAIN, 94));
		scrollPane.setViewportView(textPane);

		JLabel lblColumnHeader = new JLabel("Column Header");
		lblColumnHeader.setHorizontalAlignment(SwingConstants.CENTER);
//		scrollPane.setColumnHeaderView(lblColumnHeader);

		JLabel lblRowHeader = new JLabel("Row Header");
//		scrollPane.setRowHeaderView(lblRowHeader);

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