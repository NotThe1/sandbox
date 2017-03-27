package Misc;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class FileChooserSandBox {

	private JFrame frame;
	private JMenu mnuFile;
	private JMenuItem mnuOpen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileChooserSandBox window = new FileChooserSandBox();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}//try
			}//run
		});
	}//main
	private String getFilePathString(boolean open) {
		String currentPath = ".";
		//		String currentPath = currentInstructionSet.equals("") ? "." : currentInstructionSet;
		 Path path = Paths.get(currentPath);
		 
		JFileChooser fileChooser = new JFileChooser(currentPath);
		FileFilter filter = new FileNameExtensionFilter("Instruction file", "dat", "dat");
		fileChooser.addChoosableFileFilter(filter);
		fileChooser.setAcceptAllFileFilterUsed(true);
		int result;
		if (open) {
			result = fileChooser.showOpenDialog(null);
		} else {
			result = fileChooser.showSaveDialog(null);
		}// if

		String getFilePathString = "";
		if (result != JFileChooser.APPROVE_OPTION) {
			System.out.printf("You did not select a file%n");
			getFilePathString = "";
		} else {
			String fileName = fileChooser.getSelectedFile().getAbsolutePath();
			int dotInd = fileName.lastIndexOf('.');
			getFilePathString = (dotInd > 0) ? fileName.substring(0, dotInd) : fileName;
			System.out.printf("You select file %s:%n", getFilePathString);	
		}// if

		return getFilePathString;
	}//
	
	private void showProperties(){
		Properties properties = System.getProperties();
		Iterable<Entry<Object,Object>> propertySet = properties.entrySet();
		
		for( Object property: propertySet){
			
//			System.out.printf("%s: - %s%n", property,properties.getProperty( property.toString()));
			System.out.printf("\t%s: %n", property);
		}//for
		
	}//showProperties
	
	
	//--------------------------------
	private void appClose() {
		Preferences myPrefs = Preferences.userNodeForPackage(FileChooserSandBox.class).node(this.getClass().getSimpleName());
		Dimension dim = frame.getSize();
		myPrefs.putInt("Height", dim.height);
		myPrefs.putInt("Width", dim.width);
		Point point = frame.getLocation();
		myPrefs.putInt("LocX", point.x);
		myPrefs.putInt("LocY", point.y);
		myPrefs = null;
	}// appClose

	private void appInit() {
		// manage preferences
		Preferences myPrefs = Preferences.userNodeForPackage(FileChooserSandBox.class).node(this.getClass().getSimpleName());
		frame.setSize(myPrefs.getInt("Width", 100), myPrefs.getInt("Height", 100));
		frame.setLocation(myPrefs.getInt("LocX", 100), myPrefs.getInt("LocY", 100));
		myPrefs = null;
//		TestClass tc = new TestClass();
//		System.out.printf("[appInit] getSomething %s%n", tc.getSomething());
	}//appInit
		
	/**
	 * Create the application.
	 */
	public FileChooserSandBox() {
		initialize();
		appInit();
		showProperties();
	}//Constructor

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				appClose();
			}//windowClosing
		});
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnuFile = new JMenu("File...");
		menuBar.add(mnuFile);
		
		mnuOpen = new JMenuItem("Open...");
		mnuOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		mnuFile.add(mnuOpen);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
	}//initialize

}//class FileChooserSandBox
