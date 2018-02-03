package Misc;

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
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

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
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.text.JTextComponent;

public class BellyButton {

	private JFrame frmBellyButton;
	private JButton btnOne;
	private JButton btnTwo;
	private JButton btnThree;
	private JButton btnFour;
	private JSplitPane splitPane1;
	private JTextArea txtLog;
	private JLabel lblLog;

	private HashMap<String, Integer> linesCode;
	private HashMap<String, Integer> linesEmpty;
	private Set<String> fileNames;
	private String rootDirectoryName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BellyButton window = new BellyButton();
					window.frmBellyButton.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				} // try
			}// run
		});
	}// main

	/* Standard Stuff */

	private void log(String line) {
		txtLog.append(line + System.lineSeparator());

	}// log

	private void doBtnOne() throws IOException {
		linesCode.clear();
		linesEmpty.clear();
		fileNames.clear();
		Path start = Paths.get(lblLog.getText());

		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {

				String fileName = path.toFile().getName();
				fileNames.add(fileName);
				Integer code = 0, empty = 0;
				Scanner scanner;
				try {
					scanner = new Scanner(path);
					while (scanner.hasNext()) {
						String nl = scanner.nextLine();
						if (nl.matches("\\s") | nl.equals("")) {
							empty++;
						} else {
							code++;
						} // if
					} // while
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				} // try

				linesCode.put(fileName, code);
				linesEmpty.put(fileName, empty);

				String line = String.format("  %,10d %,10d%,10d\t%s", code + empty, code, empty, fileName);
				log(line);

				return FileVisitResult.CONTINUE;
			}

			@Override

			public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
				if (e == null) {
					log("dir - " + dir.toFile().getName());
					return FileVisitResult.CONTINUE;
				} else {
					// directory iteration failed
					throw e;
				}
			}
		});

		Integer totalCodeLines = 0, totalEmptyLines = 0, totalLines = 0;

		for (String s : fileNames) {
			totalCodeLines += linesCode.get(s);
			totalEmptyLines += linesEmpty.get(s);
		} //

		log(String.format("%n%nFile Count : %,d", fileNames.size()));
		log(String.format("Code Lines:  %,d", totalCodeLines));
		log(String.format("Empty Lines: %,d", totalEmptyLines));
		log(String.format("%nTotal Lines: %,d", totalCodeLines + totalEmptyLines));

	}// doBtnOne

	private void doBtnTwo() {
		Integer code = 0, empty = 0;
		Scanner scanner;
		try {
			scanner = new Scanner(Paths.get("C:\\Temp\\ArgumentType.java"));
			while (scanner.hasNext()) {
				String nl = scanner.nextLine();
				if (nl.matches("\\s") | nl.equals("")) {
					empty++;
				} else {
					code++;
				} // if
			} // while
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		log("Code = " + code);
		log("Empty = " + empty);
	}// doBtnTwo

	private void doBtnThree() {
		Date startDateTime = new Date(System.currentTimeMillis() - 123456789);
		Date endDateTime = new Date();
		log(getElapsedTimeToString(startDateTime, endDateTime));
		// Map<TimeUnit, Long> times = getElapsedTime(startDateTime,endDateTime);
		//
		// List<TimeUnit> timeUnits = new ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit.class));
		// Collections.reverse(timeUnits); // Days to Nanoseconds
		//
		// log("");
		// for(TimeUnit timeUnit:timeUnits) {
		// log(timeUnit.toString() + " = " + times.get(timeUnit));
		// }// for each time unit

	}// doBtnThree

	private void doBtnFour() {
		String regexString = System.getProperty("file.separator").equals("\\")?
				"\\\\" :System.getProperty("file.separator");

		String targetDir = "C:\\target\\sub\\";
		log(targetDir);
		String xx = "C:\\Users\\admin\\Dropbox\\Real Estate\\95 Pine St\\Purchase And Sale.pdf";
		String[] parts = new String[] { "C:", "Primary", "Secondary" };

		StringJoiner sj = new StringJoiner(regexString, "", regexString);
//		StringJoiner sj = new StringJoiner("/", "", "/");
		for (int i = 0; i < parts.length; i++) {
			sj.add(parts[i]);
		} // for
		log(sj.toString());
		
	}// doBtnFour

	// ---------------------------------------------------------

	private static String getElapsedTimeToString(Date startDate, Date endDate) {
		String result = "";
		Map<TimeUnit, Long> times = getElapsedTime(startDate, endDate);
		List<TimeUnit> timeUnits = new ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit.class));
		long duration;
		boolean nonZeroFlag = false;

		for (TimeUnit timeUnit : timeUnits) {
			duration = times.get(timeUnit);
			if (duration == 0) {
				if (!nonZeroFlag) {
					continue;
				} // if skip zero value
			} else {
				nonZeroFlag = true;
			} // outer if
			result = String.format("%s = %,d, %s ", timeUnit.toString(), times.get(timeUnit), result);
		} // for time Unit

		return result;
	}// getElapsedTimeToString

	private static Map<TimeUnit, Long> getElapsedTime(Date startDate, Date endDate) {
		Map<TimeUnit, Long> result = new HashMap<TimeUnit, Long>();
		long diffInMilliseconds = endDate.getTime() - startDate.getTime();
		List<TimeUnit> timeUnits = new ArrayList<TimeUnit>(EnumSet.allOf(TimeUnit.class));
		Collections.reverse(timeUnits); // Days to Nanoseconds
		long difference, milliSecondsLeftPerUnit;
		for (TimeUnit timeUnit : timeUnits) {
			difference = timeUnit.convert(diffInMilliseconds, TimeUnit.MILLISECONDS);
			result.put(timeUnit, difference);
			milliSecondsLeftPerUnit = timeUnit.toMillis(difference);
			diffInMilliseconds -= milliSecondsLeftPerUnit;
		} // for each time unit
		return result;
	}// getElapsedTime

	private void doFileNew() {

	}// doFileNew

	private void doFileOpen() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (fileChooser.showOpenDialog(frmBellyButton) == JFileChooser.APPROVE_OPTION) {
			lblLog.setText(fileChooser.getSelectedFile().getAbsolutePath());
		}
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
		Preferences myPrefs = Preferences.userNodeForPackage(BellyButton.class).node(this.getClass().getSimpleName());
		Dimension dim = frmBellyButton.getSize();
		myPrefs.putInt("Height", dim.height);
		myPrefs.putInt("Width", dim.width);
		Point point = frmBellyButton.getLocation();
		myPrefs.putInt("LocX", point.x);
		myPrefs.putInt("LocY", point.y);
		myPrefs.putInt("Divider", splitPane1.getDividerLocation());
		myPrefs = null;

	}// appClose

	private void appInit() {
		Preferences myPrefs = Preferences.userNodeForPackage(BellyButton.class).node(this.getClass().getSimpleName());
		frmBellyButton.setSize(myPrefs.getInt("Width", 761), myPrefs.getInt("Height", 693));
		frmBellyButton.setLocation(myPrefs.getInt("LocX", 100), myPrefs.getInt("LocY", 100));
		splitPane1.setDividerLocation(myPrefs.getInt("Divider", 250));
		txtLog.append(String.format("myPrefs.absolutePath() - %s%n", myPrefs.absolutePath()));
		myPrefs = null;

		lblLog.setText("C:\\Users\\admin\\git\\tools\\tools\\src");

		linesCode = new HashMap<String, Integer>();
		linesEmpty = new HashMap<String, Integer>();
		fileNames = new HashSet<String>();
		rootDirectoryName = "";
		;

	}// appInit

	public BellyButton() {
		initialize();
		appInit();
	}// Constructor

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBellyButton = new JFrame();
		frmBellyButton.setTitle("Belly Button");
		frmBellyButton.setBounds(100, 100, 450, 300);
		frmBellyButton.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBellyButton.addWindowListener(new WindowAdapter() {
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
		frmBellyButton.getContentPane().setLayout(gridBagLayout);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		frmBellyButton.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		btnOne = new JButton("Button 1");
		btnOne.setMinimumSize(new Dimension(100, 20));
		GridBagConstraints gbc_btnOne = new GridBagConstraints();
		gbc_btnOne.insets = new Insets(0, 0, 0, 5);
		gbc_btnOne.gridx = 0;
		gbc_btnOne.gridy = 0;
		panel.add(btnOne, gbc_btnOne);
		btnOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					doBtnOne();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		panel.add(btnTwo, gbc_btnTwo);
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
		gbc_btnThree.gridx = 2;
		gbc_btnThree.gridy = 0;
		panel.add(btnThree, gbc_btnThree);
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
		gbc_btnFour.anchor = GridBagConstraints.NORTH;
		gbc_btnFour.gridx = 3;
		gbc_btnFour.gridy = 0;
		panel.add(btnFour, gbc_btnFour);
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
		frmBellyButton.getContentPane().add(splitPane1, gbc_splitPane1);

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

		txtLog = new JTextArea();
		txtLog.setFont(new Font("Courier New", Font.PLAIN, 15));
		txtLog.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() > 1) {
					((JTextComponent) arg0.getComponent()).setText("");
					txtLog.setText("");
				} // if
			}// mouseClicked
		});
		scrollPane.setViewportView(txtLog);

		lblLog = new JLabel("New label");
		lblLog.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblLog);
		splitPane1.setDividerLocation(250);

		JPanel panelStatus = new JPanel();
		panelStatus.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GridBagConstraints gbc_panelStatus = new GridBagConstraints();
		gbc_panelStatus.fill = GridBagConstraints.BOTH;
		gbc_panelStatus.gridx = 0;
		gbc_panelStatus.gridy = 2;
		frmBellyButton.getContentPane().add(panelStatus, gbc_panelStatus);

		JMenuBar menuBar = new JMenuBar();
		frmBellyButton.setJMenuBar(menuBar);

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

	// @Override
	// public FileVisitResult postVisitDirectory(Object arg0, IOException arg1) throws IOException {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public FileVisitResult preVisitDirectory(Object arg0, BasicFileAttributes arg1) throws IOException {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public FileVisitResult visitFile(Object arg0, BasicFileAttributes arg1) throws IOException {
	// Path p = (Path)arg0;
	// log(p.toString());
	// return null;
	// }
	//
	//
	// @Override
	// public FileVisitResult visitFileFailed(Object arg0, IOException arg1) throws IOException {
	// // TODO Auto-generated method stub
	// return null;
	// }

}// class GUItemplate