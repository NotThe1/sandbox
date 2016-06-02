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
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import java.awt.Insets;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSpinner;

public class TestMenus {

	private JFrame frame;
	private JMenu mnuFile;
	private String defaultDirectory = ".";

	private HashMap<File, Point> fileList;
	private HashMap<File, String> listings;
	private JTextField txtFormat;
	private JSpinner spinValue;

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
		return getFileChooser(directory, filterDescription, filterExtensions, false);

	}// getFileChooser - single select

	private JFileChooser getFileChooser(String directory, String filterDescription, String filterExtensions,
			boolean multiSelect) {

		JFileChooser chooser = new JFileChooser(directory);
		chooser.setMultiSelectionEnabled(multiSelect);
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

			fileList.put(newFile, new Point(1, 2));
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

	// -----------------------------------------------------------------------------------
	private void appInit() {
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
		gridBagLayout.columnWidths = new int[] { 0, 100, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblResult = new JLabel("Result");
		lblResult.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() > 1){
					double value = 1.234;
					value = 1234567890/10000;
					lblResult.setText(String.format(txtFormat.getText(), value));
				}
			}
		});
		GridBagConstraints gbc_lblResult = new GridBagConstraints();
		gbc_lblResult.insets = new Insets(0, 0, 5, 0);
		gbc_lblResult.gridx = 3;
		gbc_lblResult.gridy = 0;
		frame.getContentPane().add(lblResult, gbc_lblResult);
		
		txtFormat = new JTextField();
		txtFormat.setText("00");
		txtFormat.setPreferredSize(new Dimension(40, 20));
		txtFormat.setMinimumSize(new Dimension(80, 20));
		txtFormat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtFormat.setColumns(10);
		GridBagConstraints gbc_txtFormat = new GridBagConstraints();
		gbc_txtFormat.insets = new Insets(0, 0, 5, 0);
		gbc_txtFormat.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFormat.gridx = 3;
		gbc_txtFormat.gridy = 1;
		frame.getContentPane().add(txtFormat, gbc_txtFormat);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);
		
		JTextArea txtrOneTwoThree = new JTextArea();
		txtrOneTwoThree.setText("One\r\nTwo\r\nThree\r\nFour\r\nFive\r\nSix\r\nSeven\r\nEight\r\nNine\r\nTen");
		scrollPane.setViewportView(txtrOneTwoThree);
		
		spinValue = new JSpinner();
		spinValue.setPreferredSize(new Dimension(80, 20));
		spinValue.setMinimumSize(new Dimension(60, 20));
		GridBagConstraints gbc_spinValue = new GridBagConstraints();
		gbc_spinValue.insets = new Insets(0, 0, 5, 0);
		gbc_spinValue.gridx = 3;
		gbc_spinValue.gridy = 2;
		frame.getContentPane().add(spinValue, gbc_spinValue);

		// GridBagConstraints gbc_list = new GridBagConstraints();
		// gbc_list.fill = GridBagConstraints.BOTH;
		// gbc_list.gridx = 1;
		// gbc_list.gridy = 3;
		// frame.getContentPane().add(list, gbc_list);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		mnuFile = new JMenu("File");
		menuBar.add(mnuFile);

		JMenuItem mnuFileAdd = new JMenuItem("Add");
		mnuFileAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = getFileChooser(defaultDirectory, "Listing Files", "List", true);
				if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
					System.out.printf("You cancelled the Load File...%n", "");
				} else {
					File[] newFiles = fc.getSelectedFiles();
					for (File newFile : newFiles) {
						newFile = fc.getSelectedFile();
						if (addFileToApp(newFile)) {
							defaultDirectory = newFile.getParent();

							// fileList.put(sourceFile, new Point(0,0));
							String thisFileName = newFile.getName();
							JCheckBoxMenuItem mnuNew = new JCheckBoxMenuItem(thisFileName);
							mnuNew.setName(thisFileName);
							mnuNew.setActionCommand(newFile.getAbsolutePath());
							mnuFile.add(mnuNew);
						}// only add if we built the HashMaps ok
					}// for each newFile
				}// if
			}
		});
		mnuFile.add(mnuFileAdd);

		JMenuItem mnuFIleRemove = new JMenuItem("Remove");
		mnuFIleRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int itemCount = mnuFile.getItemCount();
				for (int i = 0; i < itemCount; i++) {
					if (mnuFile.getItem(i) instanceof JCheckBoxMenuItem) {
						JCheckBoxMenuItem mi = (JCheckBoxMenuItem) mnuFile.getItem(i);
						String selected;
						if (mi.getState()) {
							selected = "will be removed";
						} else {
							selected = "will remain";
						}// inner if
						System.out.printf("Item %s %s%n", mnuFile.getItem(i).getName(), selected);
					}// outer if
				}// for

			}// actionPerformed
		});
		mnuFile.add(mnuFIleRemove);
	}

}
