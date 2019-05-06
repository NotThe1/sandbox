package nioTesting;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.prefs.Preferences;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import myComponents.AppLogger;

public class DeleteingTests {

	private JFrame frmTemplate;
	private JButton btnOne;
	private JButton btnTwo;
	private JButton btnThree;
	private JButton btnFour;
	private JSplitPane splitPane1;
	private JLabel lblStartDir;
	private JTextPane txtLog;

	private AppLogger log = AppLogger.getInstance();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteingTests window = new DeleteingTests();
					window.frmTemplate.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				} // try
			}// run
		});
	}// main

	/* Standard Stuff */

	private void doBtnOne() {
		log.addNL();
		log.info("Initializing...");

		String startFolder = "C:\\Temp\\A";
		lblStartDir.setText(startFolder);
		/* @formatter:off */
		String[] folders = new String[] {startFolder,
				startFolder + "\\B3",
					startFolder + "\\B3"+ "\\E0",startFolder + "\\B3"+ "\\F0",startFolder + "\\B3"+ "\\G0",
				startFolder + "\\C0",
				startFolder + "\\D2",
				startFolder + "\\D2" + "\\I0",
				startFolder + "\\D2" + "\\H3",
						startFolder + "\\D2" + "\\H3" + "\\J1",
							startFolder + "\\D2" +"\\H3" + "\\J1" + "\\M0",
						startFolder + "\\D2" + "\\H3" + "\\K0",startFolder + "\\D2" +"\\H3" + "\\L0"
		};				
/* @formatter:on  */

		removeFolders(folders);
		log.addNL();
		makeFolders(folders);
	}// doBtnOne

	private void makeFolders(String[] folders) {
		log.info("[makeFolders]....");
		for (String f : folders) {
			Path path = Paths.get(f);
			path.toFile().mkdir();
			log.info(f);
		} // for

	}// makeFolders

	private void removeFolders(String[] folders) {
		log.info("[removeFolders]....");
		for (int i = folders.length - 1; i >= 0; i--) {
			Path path = Paths.get(folders[i]);
			//path.toFile().delete();
			log.info(deleteDirectory(path.toFile()).toString());
		} // for
	}// makeFolders

	private void doBtnTwo() {
		log.addNL();
		log.info("Walking...");

//		String startDir = lblStartDir.getText();
		log.addNL();
		log.info("Starting myWalker");
		try {
			Files.walkFileTree(Paths.get(lblStartDir.getText()), new MyWalker());
		} catch (IOException e) {
			e.printStackTrace();
		}//

	}// doBtnTwo

	private void doBtnThree() {
		log.addNL();
		log.info("Removing M0...");

		String path = "C:\\Temp\\A\\D2\\H3\\J1";
//		Path parent = Paths.get(path);
		Path target = Paths.get(path, "M0");
		target.toFile().delete();
//		deleteDirectory(target.toFile());
	}// doBtnThree

	private void doBtnFour() {
		Path pathToBeDeleted = Paths.get("C:\\Temp\\A");

		Boolean result = deleteDirectory(pathToBeDeleted.toFile());

		String msg = String.format("Result = %s", result);
		log.special(msg);
	}// doBtnFour

	Boolean deleteDirectory(File directoryToBeDeleted) {
		File[] allContents = directoryToBeDeleted.listFiles();
		if (allContents != null) {
			for (File file : allContents) {
				deleteDirectory(file);
			}
		}
		return directoryToBeDeleted.delete();
	}

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

	private void appClose() {
		Preferences myPrefs = Preferences.userNodeForPackage(DeleteingTests.class)
				.node(this.getClass().getSimpleName());
		Dimension dim = frmTemplate.getSize();
		myPrefs.putInt("Height", dim.height);
		myPrefs.putInt("Width", dim.width);
		Point point = frmTemplate.getLocation();
		myPrefs.putInt("LocX", point.x);
		myPrefs.putInt("LocY", point.y);
		myPrefs.putInt("Divider", splitPane1.getDividerLocation());
		myPrefs.put("StartDir", lblStartDir.getText());
		myPrefs = null;
	}// appClose

	private void appInit() {
		Preferences myPrefs = Preferences.userNodeForPackage(DeleteingTests.class)
				.node(this.getClass().getSimpleName());

		frmTemplate.setSize(myPrefs.getInt("Width", 740), myPrefs.getInt("Height", 512));
		frmTemplate.setLocation(myPrefs.getInt("LocX", 100), myPrefs.getInt("LocY", 100));
		splitPane1.setDividerLocation(myPrefs.getInt("Divider", 250));
		lblStartDir.setText(myPrefs.get("StartDir", "C:\\Temp\\A"));
		myPrefs = null;
		txtLog.setText(EMPTY_STRING);

		log.setDoc(txtLog.getStyledDocument());
		log.info("Starting....");

	}// appInit

	public DeleteingTests() {
		initialize();
		appInit();
	}// Constructor

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTemplate = new JFrame();
		frmTemplate.setTitle("DeleteingTests");
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
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 25, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		frmTemplate.getContentPane().setLayout(gridBagLayout);

		JPanel panelForStartDir = new JPanel();
		GridBagConstraints gbc_panelForStartDir = new GridBagConstraints();
		gbc_panelForStartDir.anchor = GridBagConstraints.NORTH;
		gbc_panelForStartDir.insets = new Insets(0, 0, 5, 0);
		gbc_panelForStartDir.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelForStartDir.gridx = 0;
		gbc_panelForStartDir.gridy = 0;
		frmTemplate.getContentPane().add(panelForStartDir, gbc_panelForStartDir);
		GridBagLayout gbl_panelForStartDir = new GridBagLayout();
		gbl_panelForStartDir.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panelForStartDir.rowHeights = new int[] { 0, 0 };
		gbl_panelForStartDir.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelForStartDir.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelForStartDir.setLayout(gbl_panelForStartDir);

		JButton btnDir = new JButton("Starting Directory ...");
		GridBagConstraints gbc_btnDir = new GridBagConstraints();
		gbc_btnDir.insets = new Insets(0, 0, 0, 5);
		gbc_btnDir.gridx = 0;
		gbc_btnDir.gridy = 0;
		panelForStartDir.add(btnDir, gbc_btnDir);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		GridBagConstraints gbc_horizontalStrut = new GridBagConstraints();
		gbc_horizontalStrut.insets = new Insets(0, 0, 0, 5);
		gbc_horizontalStrut.gridx = 1;
		gbc_horizontalStrut.gridy = 0;
		panelForStartDir.add(horizontalStrut, gbc_horizontalStrut);
		horizontalStrut.setPreferredSize(new Dimension(40, 0));

		lblStartDir = new JLabel("Starting Directory");
		GridBagConstraints gbc_lblStartDir = new GridBagConstraints();
		gbc_lblStartDir.gridx = 2;
		gbc_lblStartDir.gridy = 0;
		panelForStartDir.add(lblStartDir, gbc_lblStartDir);
		lblStartDir.setHorizontalAlignment(SwingConstants.LEFT);
		lblStartDir.setForeground(Color.BLUE);
		lblStartDir.setFont(new Font("Times New Roman", Font.BOLD, 13));
		btnDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser(lblStartDir.getText());
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (chooser.showOpenDialog(frmTemplate) == JFileChooser.APPROVE_OPTION) {
					lblStartDir.setText(chooser.getSelectedFile().getAbsolutePath().toString());
				} // if - show
			}//
		});

		JPanel panelForButtons = new JPanel();
		panelForButtons.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_panelForButtons = new GridBagConstraints();
		gbc_panelForButtons.anchor = GridBagConstraints.NORTH;
		gbc_panelForButtons.insets = new Insets(0, 0, 5, 0);
		gbc_panelForButtons.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelForButtons.gridx = 0;
		gbc_panelForButtons.gridy = 1;
		frmTemplate.getContentPane().add(panelForButtons, gbc_panelForButtons);
		GridBagLayout gbl_panelForButtons = new GridBagLayout();
		gbl_panelForButtons.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panelForButtons.rowHeights = new int[] { 0, 0 };
		gbl_panelForButtons.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panelForButtons.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelForButtons.setLayout(gbl_panelForButtons);

		btnOne = new JButton("Initialize");
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

		btnTwo = new JButton("Walk");
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

		btnThree = new JButton("Delete M0 J1");
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
		gbc_splitPane1.gridy = 2;
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
		gbl_panelRight.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelRight.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelRight.setLayout(gbl_panelRight);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panelRight.add(scrollPane, gbc_scrollPane);

		txtLog = new JTextPane();
		scrollPane.setViewportView(txtLog);

		JLabel lblNewLabel = new JLabel("Log");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.GRAY);
		lblNewLabel.setFont(new Font("Tahoma", Font.ITALIC, 14));
		scrollPane.setColumnHeaderView(lblNewLabel);
		splitPane1.setDividerLocation(250);

		JPanel panelStatus = new JPanel();
		panelStatus.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_panelStatus = new GridBagConstraints();
		gbc_panelStatus.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelStatus.anchor = GridBagConstraints.NORTH;
		gbc_panelStatus.gridx = 0;
		gbc_panelStatus.gridy = 3;
		frmTemplate.getContentPane().add(panelStatus, gbc_panelStatus);
		GridBagLayout gbl_panelStatus = new GridBagLayout();
		gbl_panelStatus.columnWidths = new int[] { 0, 0, 89, 0, 0 };
		gbl_panelStatus.rowHeights = new int[] { 23, 0 };
		gbl_panelStatus.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelStatus.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelStatus.setLayout(gbl_panelStatus);

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

	}// initialize

	static final String EMPTY_STRING = "";
	////////////////////////////////////////////////////////

	class MyWalker implements FileVisitor<Path> {

		@Override
		public FileVisitResult postVisitDirectory(Path folder, IOException exc) throws IOException {
			log.info("[postVisitDirectory] " + folder.toString());
	//		log.addSpecial("Count = " + Files.list(folder).count());
			return FileVisitResult.CONTINUE;
		}//

		@Override
		public FileVisitResult preVisitDirectory(Path folder, BasicFileAttributes attrs) throws IOException {
			// TODO Auto-generated method stub
			return FileVisitResult.CONTINUE;
		}//

		@Override
		public FileVisitResult visitFile(Path folder, BasicFileAttributes attrs) throws IOException {
			// TODO Auto-generated method stub
			return FileVisitResult.CONTINUE;
		}//

		@Override
		public FileVisitResult visitFileFailed(Path folder, IOException exc) throws IOException {
			log.error("visitFileFailed: " + folder.toString());
			log.error("  IOException = " + exc.toString());
			// UserPrincipal up = Files.getOwner(folder, LinkOption.NOFOLLOW_LINKS);
			// log.error("Owner = " + up.getName());
			return FileVisitResult.CONTINUE;
		}//

	}// class myWalker

}// class GUItemplate