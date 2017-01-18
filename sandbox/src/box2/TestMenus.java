package box2;

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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TestMenus implements ActionListener{

	private JFrame frame;
	private JMenu mnuFile;
	private String defaultDirectory = ".";

	private HashMap<File, Point> fileList;
	private HashMap<File, String> listings;
	private JTextField txtFormat;
	private JSpinner spinValue;
	DefaultComboBoxModel<String> fred = new DefaultComboBoxModel<String>(new String[] { "one", "two", "three" });
	private JComboBox comboBox;
	
	JPopupMenu popupMenu = new JPopupMenu();

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
			JOptionPane.showMessageDialog(null, newFile.getAbsolutePath() + "not found", "unable to locate",
					JOptionPane.ERROR_MESSAGE);
			return false; // exit gracefully
		} catch (IOException ie) {
			JOptionPane.showMessageDialog(null, newFile.getAbsolutePath() + ie.getMessage(), "IO error",
					JOptionPane.ERROR_MESSAGE);
			return false; // exit gracefully
		}

		return true;

	}// addFileToApp

	// -----------------------------------------------------------------------------------
	private void appInit() {
		// fileList = new HashMap<File, Point>();
		// listings = new HashMap<File, String>();
		
		SpinnerNumberModel n = (SpinnerNumberModel) spinValue.getModel();

		Component[] components = spinValue.getComponents();
		int i = 0;
		for (Component c : components) {
			if (c instanceof AbstractButton) {
				// c.setVisible(false);
				// System.out.printf("[TestMenus:appInit]component[%d]: %s%n",i++, c.toString());
				// c = null;
				// int nextNumber = 0;//(int) n.getNextValue();
				// int number = (int) n.getNumber();
				// System.out.printf("[TestMenus:appInit]Model - number : %d, next number: %d%n", number,nextNumber);

				// int nextNumberS = (int) spinValue.getValue();
				// int numberS = ((Integer) spinValue.getNextValue())==null?(int) n.getMaximum():(int)
				// spinValue.getNextValue();
				// int numberS = ((Integer) spinValue.getNextValue());
				// System.out.printf("[TestMenus:appInit]Spinner - number : %d, next number: %d%n",
				// numberS,nextNumberS);

			} // if
		} // for
		int a = 0;
		 String ACTION_KEY = "theAction";		
			KeyStroke controlAlt7 = KeyStroke.getKeyStroke("control alt 7");
			
//			InputMap im = comboBox.getInputMap();			
//			im.put(controlAlt7, ACTION_KEY);
//			
//			ActionMap am = comboBox.getActionMap();			
//			am.put(ACTION_KEY, actionListener);
//			
//			InputMap im1 = txtLog.getInputMap();
//			im1.put(controlAlt7, ACTION_KEY);
//			
//			ActionMap am1 = txtLog.getActionMap();
//			am1.put(ACTION_KEY, actionListener);
			
			InputMap im3 = txtFormat.getInputMap();
			im3.put(controlAlt7, ACTION_KEY);
			
			ActionMap am3 = txtFormat.getActionMap();
			am3.put(ACTION_KEY, actionListener);
			
			JMenuItem firstItem = new JMenuItem("control alt 7");
			firstItem.addActionListener(actionListener);
			popupMenu.add(firstItem);
			comboBox.setComponentPopupMenu(popupMenu);
		

		

	}// appInit
		Action actionListener = new AbstractAction() {
			public void actionPerformed(ActionEvent actionEvent) {
				System.out.println("Selected Index:        xxx");
				
				System.out.println("Selected Index: " + comboBox.getSelectedIndex());
			}
		};
		private JTextArea txtLog;
		private JButton btnDoSomething;

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
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JLabel lblResult = new JLabel("Result");
		lblResult.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() > 1) {
					double value = 1.234;
					value = 1234567890 / 10000;
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

		txtLog = new JTextArea();
		txtLog.setText("One");
		scrollPane.setViewportView(txtLog);		

		spinValue = new JSpinner();
		spinValue.setModel(new SpinnerNumberModel(1, 0, 1, 1));
		spinValue.setPreferredSize(new Dimension(80, 20));
		spinValue.setMinimumSize(new Dimension(60, 20));
		GridBagConstraints gbc_spinValue = new GridBagConstraints();
		gbc_spinValue.insets = new Insets(0, 0, 5, 0);
		gbc_spinValue.gridx = 3;
		gbc_spinValue.gridy = 2;
		frame.getContentPane().add(spinValue, gbc_spinValue);

		comboBox = new JComboBox(fred);
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				
				if(fred.getIndexOf(itemEvent.getItem()) == -1){
					fred.insertElementAt((String) itemEvent.getItem(), 0);
//					comboBox.repaint();
				}//if - not there
				
				itemEvent.getStateChange();
				itemEvent.paramString();
				itemEvent.getItem();
				System.out.printf("[comboBox.itemStateChanged] getStateChange() %s%n",itemEvent.getStateChange());
				System.out.printf("[comboBox.itemStateChanged] paramString() %s%n",itemEvent.paramString());
				System.out.printf("[comboBox.itemStateChanged] getItem() %s%n",itemEvent.getItem());
			}
		});
		comboBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				Object object = comboBox.getSelectedItem();
				if(fred.getIndexOf(object) == -1){
					fred.insertElementAt((String) object, 0);
					comboBox.repaint();
				}//if - not there
			}//focus Lost
		});
		comboBox.setEditable(true);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 3;
		frame.getContentPane().add(comboBox, gbc_comboBox);

		btnDoSomething = new JButton("do Something");
		btnDoSomething.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Object subject = comboBox.getSelectedItem();
				int loc = fred.getIndexOf(subject);
				System.out.printf("[btnDoSomething] subject: %s  loc: %d%n", subject, loc);
				fred.insertElementAt((String) subject, 0);
			}// actionPerformed
		});
		GridBagConstraints gbc_btnDoSomething = new GridBagConstraints();
		gbc_btnDoSomething.gridx = 3;
		gbc_btnDoSomething.gridy = 4;
		frame.getContentPane().add(btnDoSomething, gbc_btnDoSomething);

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
						} // only add if we built the HashMaps ok
					} // for each newFile
				} // if
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
						} // inner if
						System.out.printf("Item %s %s%n", mnuFile.getItem(i).getName(), selected);
					} // outer if
				} // for

			}// actionPerformed
		});
		mnuFile.add(mnuFIleRemove);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.printf("[actionPerformed]  %s%n", arg0.toString());
		
	}

}
