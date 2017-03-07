package Misc;

import java.awt.Dimension;
import java.awt.EventQueue;
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
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Set;
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
import javax.swing.border.BevelBorder;

public class TempFiles {

	private JFrame frmTemplate;
	private JButton btnOne;
	private JButton btnTwo;
	private JButton btnThree;
	private JButton btnFour;
	private JSplitPane splitPane1;
	File tempDirectory;
	File oneFile;
	final static String PREFIX = "tempFiles.";
	final static String SUFFIX = ".F5DD";
	private JTextArea textPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TempFiles window = new TempFiles();
					window.frmTemplate.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				} // try
			}// run
		});
	}// main

	/* Standard Stuff */

	private void doBtnOne() {
		try {
			oneFile = File.createTempFile(PREFIX, SUFFIX);
			File.createTempFile(PREFIX, SUFFIX);
			File.createTempFile(PREFIX, SUFFIX);
			File.createTempFile(PREFIX, SUFFIX);
			File.createTempFile(PREFIX, SUFFIX);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}// doBtnOne

	private void doBtnTwo() {
		doBtnOne();
		String TEMP = "TEMP";
		String tempDir = System.getenv(TEMP);
		textPane.setText("");
		textPane.append(String.format("%-25s = %s%n%n", TEMP, tempDir));

		File targetDir = new File(tempDir);
		String xx = File.separator;
		String[] tempFiles = targetDir.list(new PrefixFilter(PREFIX));
		for (String tempFile : tempFiles) {
			textPane.append(String.format("%s%n", tempDir + xx + tempFile));
		} // for

		for (String tempFile : tempFiles) {
			Boolean result;
			try {
				result = Files.deleteIfExists(Paths.get(tempDir + xx + tempFile));
				textPane.append(String.format("%-10s,%s%n", result, tempDir + xx + tempFile));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //
		} // for

	}// doBtnTwo

	private void doBtnThree() {
		String fileName = NewDisk.makeNewDisk();
		textPane.append(String.format("New disk file name: %s%n", fileName));
		int counter = 0;
		while (!Files.isWritable(Paths.get(fileName))){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(counter++ > 60){
				break;
			}
			textPane.append(String.format("doButtonThree: counter = %d%n",counter));
		}
		updateDisk(Paths.get(fileName));
	}// doBtnThree

	private void updateDisk(Path path) {
		File workDisk = null;
		try {
			workDisk = File.createTempFile("SandboxTempFiles", "." + "stuff");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Path target = path;
		Path source = Paths.get(workDisk.getAbsolutePath());
		try {
			Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {

			e.printStackTrace();
		} // try
	}// updateDisk

	private void doBtnFour() {
		textPane.setText("");
		Map<String, String> envVars = System.getenv();
		Set<String> keys = envVars.keySet();
		for (String key : keys) {
			textPane.append(String.format("%-25s = %s%n", key, envVars.get(key)));
		} // for

		textPane.append(String.format("%n%-25s = %s%n", "TEMP", System.getenv("TEMP")));

	}// doBtnFour
		// :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

	class PrefixFilter implements FilenameFilter {
		String prefix;

		public PrefixFilter(String prefix) {
			this.prefix = prefix;
		}// Constructor

		public boolean accept(File dir, String name) {
			boolean yes = name.startsWith(prefix);
			return yes;
			// return name.endsWith(ext);
		}// accept
	}// class PrefixFilter
	// :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

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
		Preferences myPrefs = Preferences.userNodeForPackage(TempFiles.class);
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
		Preferences myPrefs = Preferences.userNodeForPackage(TempFiles.class);
		frmTemplate.setSize(696, 753);
		frmTemplate.setLocation(myPrefs.getInt("LocX", 100), myPrefs.getInt("LocY", 100));
		splitPane1.setDividerLocation(myPrefs.getInt("Divider", 250));
		myPrefs = null;
	}// appInit

	public TempFiles() {
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

		btnOne = new JButton("Create Files");
		btnOne.setToolTipText("Create Files");
		btnOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doBtnOne();
			}
		});
		btnOne.setMaximumSize(new Dimension(70, 20));
		btnOne.setPreferredSize(new Dimension(50, 20));
		toolBar.add(btnOne);

		btnTwo = new JButton("Create/Delete");
		btnTwo.setToolTipText("Create/Delete");
		btnTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doBtnTwo();
			}
		});
		btnTwo.setPreferredSize(new Dimension(30, 20));
		btnTwo.setMaximumSize(new Dimension(70, 20));
		toolBar.add(btnTwo);

		btnThree = new JButton("NewDisk");
		btnThree.setToolTipText("NewDisk");
		btnThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doBtnThree();
			}
		});
		btnThree.setPreferredSize(new Dimension(30, 20));
		btnThree.setMaximumSize(new Dimension(70, 20));
		toolBar.add(btnThree);

		btnFour = new JButton("Env Vars");
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

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panelLeft.add(scrollPane, gbc_scrollPane);

		textPane = new JTextArea();
		textPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() > 1) {
					textPane.setText("");
				}

			}
		});
		scrollPane.setViewportView(textPane);

		JPanel panelRight = new JPanel();
		splitPane1.setRightComponent(panelRight);
		GridBagLayout gbl_panelRight = new GridBagLayout();
		gbl_panelRight.columnWidths = new int[] { 0 };
		gbl_panelRight.rowHeights = new int[] { 0 };
		gbl_panelRight.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_panelRight.rowWeights = new double[] { Double.MIN_VALUE };
		panelRight.setLayout(gbl_panelRight);
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