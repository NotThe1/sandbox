package Misc;

import java.awt.Component;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import myComponents.AppLogger;

public class TableExporation {
	AppLogger log = AppLogger.getInstance();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TableExporation window = new TableExporation();
					window.frmTemplate.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				} // try
			}// run
		});
	}// main

	/* Standard Stuff */

	private void doBtnOne() {

	}// doBtnOne

	private void doBtnTwo() {

	}// doBtnTwo

	private void doBtnThree() {

	}// doBtnThree

	private void doBtnFour() {

	}// doBtnFour

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

	//////////////////////////////////////////////

	private void loadTableModel(MyTableModel tableModel) {
		Object[] row = new Object[8];
		int rows = 0;
		for (int i = 0; i < 100; i++) {
			row[0] = true;
			row[1] = "test.jpg";
			row[2] = "C:\\A";
			row[3] = 10L;
			row[4] = new Date().toString();
			row[5] = (i % 2) == 0;
			row[6] = rows++;
			tableModel.addRow(row);
		} // for rows

	}// loadTableModel

	private void appClose() {
		Preferences myPrefs = Preferences.userNodeForPackage(TableExporation.class)
				.node(this.getClass().getSimpleName());
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
		Preferences myPrefs = Preferences.userNodeForPackage(TableExporation.class)
				.node(this.getClass().getSimpleName());
		frmTemplate.setSize(myPrefs.getInt("Width", 761), myPrefs.getInt("Height", 693));
		frmTemplate.setLocation(myPrefs.getInt("LocX", 100), myPrefs.getInt("LocY", 100));
		splitPane1.setDividerLocation(myPrefs.getInt("Divider", 250));
		myPrefs = null;
		log.setDoc(tpLog.getStyledDocument());
		MyTableModel tableModel = new MyTableModel();
		loadTableModel(tableModel);
		table.setModel(tableModel);
	}// appInit

	public TableExporation() {
		initialize();
		appInit();
	}// Constructor

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTemplate = new JFrame();
		frmTemplate.setTitle("WinTemplate_Log");
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
		gridBagLayout.rowHeights = new int[] { 0, 0, 25, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		frmTemplate.getContentPane().setLayout(gridBagLayout);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		frmTemplate.getContentPane().add(panel, gbc_panel);
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
				doBtnOne();
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
		frmTemplate.getContentPane().add(splitPane1, gbc_splitPane1);

		JPanel panelLeft = new JPanel();
		splitPane1.setLeftComponent(panelLeft);
		GridBagLayout gbl_panelLeft = new GridBagLayout();
		gbl_panelLeft.columnWidths = new int[] { 0, 0 };
		gbl_panelLeft.rowHeights = new int[] { 0, 0 };
		gbl_panelLeft.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelLeft.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelLeft.setLayout(gbl_panelLeft);

		JSplitPane splitPaneLeft = new JSplitPane();
		splitPaneLeft.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		JPopupMenu popupMenu_1 = new JPopupMenu();
		addPopup(splitPaneLeft, popupMenu_1);
		GridBagConstraints gbc_splitPaneLeft = new GridBagConstraints();
		gbc_splitPaneLeft.fill = GridBagConstraints.BOTH;
		gbc_splitPaneLeft.gridx = 0;
		gbc_splitPaneLeft.gridy = 0;
		panelLeft.add(splitPaneLeft, gbc_splitPaneLeft);

		JScrollPane scrollPane_1 = new JScrollPane();
		splitPaneLeft.setLeftComponent(scrollPane_1);

		table = new JTable();

		ListSelectionModel selectionModel = table.getSelectionModel();
		selectionModel.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent lse) {
				if(lse.getValueIsAdjusting()){
					return;
				}
				if (!selectionModel.isSelectionEmpty()) {
					String msg = String.format("[valueChanged]index0: %d, index1: %d", lse.getFirstIndex(), lse.getLastIndex());
					log.addInfo(msg);
				}
			}

		});

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(table, popupMenu);

		JMenuItem mntmPopUpMenu1 = new JMenuItem();
		mntmPopUpMenu1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String msg = String.format("[actionPerformed 1]index0: %d, index1: %d",
						selectionModel.getMinSelectionIndex(), selectionModel.getMaxSelectionIndex());
				log.addInfo(msg);

			}
		});
		mntmPopUpMenu1.setText("Pop Up Menu1");
		popupMenu.add(mntmPopUpMenu1);

		JMenuItem mntmPopUpMenu2 = new JMenuItem();
		mntmPopUpMenu2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String msg = String.format("[actionPerformed 2]index0: %d, index1: %d",
						selectionModel.getMinSelectionIndex(), selectionModel.getMaxSelectionIndex());
				log.addInfo(msg);

			}
		});

		mntmPopUpMenu2.setText("Pop Up Menu2");
		popupMenu.add(mntmPopUpMenu2);

		scrollPane_1.setViewportView(table);
		splitPaneLeft.setDividerLocation(300);

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

		tpLog = new JTextPane();
		scrollPane.setViewportView(tpLog);

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

	}// initialize

	private JFrame frmTemplate;
	private JButton btnOne;
	private JButton btnTwo;
	private JButton btnThree;
	private JButton btnFour;
	private JSplitPane splitPane1;
	private JTextPane tpLog;
	private JLabel lblLog;
	private JTable table;

	// -------------------------------------------------------------------------------------
	class MyTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private Map<Point, Object> lookup = new HashMap<Point, Object>();
		private int rows = 0;

		public MyTableModel() {
		}// Constructor

		public void clear() {
			lookup.clear();
			rows = 0;
		}// clear()

		@Override
		public int getColumnCount() {
			return columns;
		}// getColumnCount

		public String getColumnName(int column) {
			return headers[column];
		}// getColumnName

		@Override
		public int getRowCount() {
			return rows;
		}// getRowCount

		@Override
		public Object getValueAt(int row, int column) {
			return lookup.get(new Point(row, column));
		}// getValueAt

		public void setValueAt(Object value, int row, int column) {
			if ((row < 0) || (column < 0)) {
				throw new IllegalArgumentException("Invalid row/column setting");
			} // if - negative
			if ((row < rows) && (column < columns)) {
				lookup.put(new Point(row, column), value);
			} //
		}// setValueAt

		public Object[] getRow(int rowNumber) {
			Object[] row = new Object[this.getColumnCount()];
			for (int i = 0; i < columns; i++) {
				row[i] = lookup.get(new Point(rowNumber, i));
			} // for
			return row;
		}// getRow

		public synchronized void removeRow(int row) {
			if (row > rows || row < 0) {
				return;
			} // if row we have

			if (row == rows - 1) {
				rows--;
				return;
			} // if last row

			/* move data down a row */
			for (int r = row; r < rows - 1; r++) {
				for (int i = 0; i < columns; i++) {
					lookup.put(new Point(r, i), lookup.get(new Point(r + 1, i)));
				} // for
			} // for r

			/* remove the last row */
			for (int i = 0; i < columns; i++) {
				lookup.remove(rows, columns);
			} // for

			rows--;
		}// removeRow

		public void addRow(Object[] values) {
			rows++;
			for (int i = 0; i < columns; i++) {
				lookup.put(new Point(rows - 1, i), values[i]);
			} // for
		}// addRow

		public void addRowForAction(Object[] values) {
			rows++;
			lookup.put(new Point(rows - 1, 0), false);
			for (int i = 0; i < values.length; i++) {
				lookup.put(new Point(rows - 1, i + 1), values[i]);
			} // for

		}// addRowForAction

		@Override
		public Class<?> getColumnClass(int columnIndex) {

			switch (columnIndex) {
			case 0:// Action
				return Boolean.class;
			case 1: // "Name"
				return super.getColumnClass(columnIndex);
			case 2:// "Directory"
				return super.getColumnClass(columnIndex);
			case 3:// "Size"
				return Long.class;
			case 4:// "Modified Date"
				return super.getColumnClass(columnIndex);
			// return FileTime.class;
			case 5:// Duplicate
				return Boolean.class;
			case 6:// File ID
				return Integer.class;
			default:
				return super.getColumnClass(columnIndex);
			}// switch
		}// getColumnClass

		public static final String ACTION = "Action";
		public static final String NAME = "Name";
		public static final String DIRECTORY = "Directory";
		public static final String SIZE = "Size";
		public static final String DATE = "ModifiedDate";
		public static final String DUP = "Dup";
		public static final String ID = "ID";

		final String[] headers = new String[] { ACTION, NAME, DIRECTORY, SIZE, DATE, DUP, ID };
		final int columns = headers.length;

	}// class MyTableModel
		// -------------------------------------------------------------------------------------

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {

					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}// class GUItemplate