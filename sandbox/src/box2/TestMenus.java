package box2;

import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.MenuElement;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;




import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;

public class TestMenus {

	private JFrame frame;
	private JMenu mnuFile;
	private String defaultDirectory = ".";
	
	private HashMap<File, Point> fileList;
	private HashMap<File, String> listings;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestMenus window = new TestMenus();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private JFileChooser getFileChooser(String directory, String filterDescription, String filterExtensions) {
		// Path sourcePath = Paths.get(directory);
		// String fp = sourcePath.resolve(defaultDirectory).toString();

		JFileChooser chooser = new JFileChooser(directory);
		chooser.setMultiSelectionEnabled(false);
		chooser.addChoosableFileFilter(new FileNameExtensionFilter(filterDescription, filterExtensions));
		chooser.setAcceptAllFileFilterUsed(false);
		return chooser;
	}// getFileChooser
	private boolean addFileToApp(File newFile) {
		try {
			FileReader fileReader = new FileReader((newFile));
			BufferedReader reader = new BufferedReader(fileReader);
			String line, adjustedLine;
			// int lineNumber = 0;
			
			Boolean displayLine = false;

			

			

			fileList.put(newFile, new Point(1,2));
			listings.put(newFile, newFile.getAbsolutePath());

			reader.close();
		} catch (FileNotFoundException fnfe) {
			JOptionPane.showMessageDialog(null, newFile.getAbsolutePath()
					+ "not found", "unable to locate",
					JOptionPane.ERROR_MESSAGE);
			return false; // exit gracefully
		} catch (IOException ie) {
			JOptionPane.showMessageDialog(null, newFile.getAbsolutePath()
					+ ie.getMessage(), "IO error",
					JOptionPane.ERROR_MESSAGE);
			return false; // exit gracefully
		}
		
		return true;

	}// addFileToApp
	
	//-----------------------------------------------------------------------------------
	private void appInit(){
		fileList = new HashMap<File, Point>();
		listings = new HashMap<File, String>();

	}

	/**
	 * Create the application.
	 */
	public TestMenus() {
		initialize();
		appInit();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0};
		gridBagLayout.columnWeights = new double[]{Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		mnuFile = new JMenu("File");
		menuBar.add(mnuFile);
		
		JMenuItem mnuFileAdd = new JMenuItem("Add");
		mnuFileAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = getFileChooser(defaultDirectory, "Listing Files", "List");
				if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
					System.out.printf("You cancelled the Load File...%n", "");
				} else {
					File newFile = fc.getSelectedFile();
					if (addFileToApp(newFile)) {
						defaultDirectory = newFile.getParent();
						
						// fileList.put(sourceFile, new Point(0,0));
						String thisFileName = newFile.getName();
						JCheckBoxMenuItem mnuNew = new JCheckBoxMenuItem(thisFileName);
						mnuNew.setName(thisFileName);
						mnuNew.setActionCommand(newFile.getAbsolutePath());
						mnuFile.add(mnuNew);
					}// only add if we built the HashMaps ok
				}// if
			}
		});
		mnuFile.add(mnuFileAdd);
		
		JMenuItem mnuFIleRemove = new JMenuItem("Remove");
		mnuFIleRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int itemCount =mnuFile.getItemCount();
				 
				for( int i = 0; i < itemCount; i++){
					if (mnuFile.getItem(i) instanceof JCheckBoxMenuItem){
						JCheckBoxMenuItem mi = (JCheckBoxMenuItem) mnuFile.getItem(i);
						String selected;
						if (mi.getState()){
							selected = "will be removed";
						}else{
							selected = "will remain";
						}
						System.out.printf("Item %s %s%n",mnuFile.getItem(i).getName(),selected);
					}
				}
				
			}
		});
		mnuFile.add(mnuFIleRemove);
	}

}
