package Misc;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.AttributedCharacterIterator;
import java.util.Enumeration;
import java.util.prefs.Preferences;

import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JMenuBar;
import javax.swing.JScrollPane;

import java.awt.GridBagConstraints;

import javax.swing.JTextPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.AbstractDocument.AbstractElement;
import javax.swing.text.AbstractDocument.BranchElement;
import javax.swing.text.AbstractDocument.LeafElement;
import javax.swing.text.StyleConstants.CharacterConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import java.awt.Font;

import javax.swing.JToolBar;

import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTree;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;

public class StyleExplore implements TreeSelectionListener {

	private JFrame frmReflec;
	private JTextPane txtpnPP;
	private JToolBar toolBar;
	private JButton btnOne;
	private JButton btnTwo;
	private JButton btnThree;
	private JButton btnFour;
	private JPanel panelDetails;
	private JSplitPane splitPane;
	private JScrollPane scrollPaneElements;
	private JScrollPane scrollPaneAttributes;
	private JLabel lblNewLabel;
	private JLabel lblAttributes;
	private JTree treeElements;
	private JTextArea textAttributes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StyleExplore window = new StyleExplore();
					window.frmReflec.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}// main

	// ///////////////////////////////////////////////////////////////

	private void doButton1() {

		StyledDocument doc = txtpnPP.getStyledDocument();
		Element elementRoot = doc.getDefaultRootElement();
		int rootElementCount = elementRoot.getElementCount();
		System.out.printf("rootElementCount = %d%n", rootElementCount);

		DefaultMutableTreeNode treeRoot = new DefaultMutableTreeNode(doc);
		treeRoot = createNodes(treeRoot, elementRoot);
		DefaultTreeModel treeModel = new DefaultTreeModel(treeRoot);

		treeElements.setModel(treeModel);
		treeElements.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	}// doButton2

	private DefaultMutableTreeNode createNodes(DefaultMutableTreeNode currentNode, Element currentElement) {
		DefaultMutableTreeNode newNode, childNode;
		// DefaultMutableTreeNode tempNode = new DefaultMutableTreeNode(currentNode);
		DefaultMutableTreeNode tempNode = currentNode;

		int childCount = currentElement.getElementCount();

		for (int i = 0; i < childCount; i++) {
			newNode = new DefaultMutableTreeNode(currentElement.getElement(i));
			// newNode = new DefaultMutableTreeNode(currentElement.getElement(i).toString());
			childNode = createNodes(newNode, currentElement.getElement(i));
			tempNode.add(childNode);
		}// for - child elements

		return tempNode;
	}// createNodes

	private void doButton2() {

	}// doButton1

	private void doButton3() {

	}// doButton3

	private void doButton4() {

	}// doButton4

	private void doAbstractElement(AbstractElement element) {
		// System.out.printf("%s.%n", "doAbstractElement");
		doElement(element);
	}// doAbstractElement

	private void doBranchElement(BranchElement element) {
		// System.out.printf("%s.%n", "doBranchElement");
		doElement(element);
	}// doBranchElement

	private void doLeafElement(LeafElement element) {
		// System.out.printf("%s.%n", "doLeafElement");
		doElement(element);
	}// doLeafElement

	private void doStyledDocument(DefaultStyledDocument doc) {
		// System.out.printf("%s.%n", "doStyledDocument");
		doDocument(doc);
	}// doStyledDocument

	private void doElement(AbstractElement element) {
		AttributeSet attributeSet = element.getAttributes();
		if (attributeSet.getAttributeCount() > 0) {
			doAttributes(attributeSet);
		}// if
			// System.out.printf("Attribute count: %d.%n", attributeSet.getAttributeCount());
	}// doElement

	private void doAttributes(AttributeSet attributeSet) {
		// if (attributeSet instanceof Style){
		// System.out.println("ITS A STYLE");
		// }

		String styleFormat = attributeSet instanceof Style ? "\t%-15s:   %s.%n" : "%-15s:   %s.%n";
		String styleMessage;
		Enumeration<?> attributeNames = attributeSet.getAttributeNames();
		while (attributeNames.hasMoreElements()) {
			Object attributeKey = attributeNames.nextElement();
			Object attributeValue = attributeSet.getAttribute(attributeKey);
			if (attributeKey.equals(StyleConstants.ResolveAttribute)) {
				textAttributes.append("Resolver:" + NL);
				doAttributes((AttributeSet) attributeValue);
			} else {
				styleMessage = String.format(styleFormat, attributeKey, attributeValue);
				textAttributes.append(styleMessage);
				// System.out.printf(styleMessage);
			}
		}//
			// System.out.printf("Attribute count: %d.%n", attributeSet.getAttributeCount());
	}// doElement
		// private void doResolver(AttributeSet attributeSet){
	//
	// }

	private void doDocument(DefaultStyledDocument doc) {
		Enumeration<String> styles = (Enumeration<String>) doc.getStyleNames();
		String styleHeaderFormat = "%nStyle:   %-30s it has %d AttributeSets.%n";
		while (styles.hasMoreElements()) {
			String styleName = styles.nextElement();
			Style style = doc.getStyle(styleName);
			int styleCount = style.getAttributeCount();

			String styleHeaderMessage = String.format(styleHeaderFormat, styleName, styleCount);
			// System.out.printf(styleHeaderMessage);
			textAttributes.append(styleHeaderMessage);
			doAttributes(style);

		}//
	}// doDocument

	/** Required by TreeSelectionListener interface. */
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeElements.getLastSelectedPathComponent();
		/*
		 * AbstractDocument.AbstractElement AbstractDocument.BranchElement AbstractDocument.LeafElement
		 */
		if (node == null)
			return;
		textAttributes.setText(null);

		Object nodeObject = node.getUserObject();
		if (node.getUserObject() instanceof AbstractElement) {
			// doAbstractElement((AbstractElement) nodeObject);
		}// if AbstractElement
		if (node.getUserObject() instanceof BranchElement) {
			lblAttributes.setText(BRANCH);
			doBranchElement((BranchElement) nodeObject);
		}// if BranchElement
		if (node.getUserObject() instanceof LeafElement) {
			lblAttributes.setText(LEAF);
			doLeafElement((LeafElement) nodeObject);
			System.out.printf("Element Name:%s.%n", ((LeafElement) nodeObject).getName());
		}// if LeafElement
		if (node.getUserObject() instanceof DefaultStyledDocument) {
			lblAttributes.setText(DOCUMENT);
			doStyledDocument((DefaultStyledDocument) nodeObject);
		}// if DefaultStyledDocument

	}// valueChanged
		// private void makeStyles1(StyledDocument doc){
	// SimpleAttributeSet baseAttributes = new SimpleAttributeSet();
	// StyleConstants.setFontFamily(baseAttributes, "Courier New");
	// StyleConstants.setFontSize(baseAttributes, 16);
	//
	// SimpleAttributeSet locationAttributes = new SimpleAttributeSet(baseAttributes);
	// locationAttributes.addAttribute(CharacterConstants.Foreground, Color.gray);
	//
	// SimpleAttributeSet opCodeAttributes = new SimpleAttributeSet(baseAttributes);
	// opCodeAttributes.addAttribute(CharacterConstants.Foreground, Color.red);
	//
	// SimpleAttributeSet instructionAttributes = new SimpleAttributeSet(baseAttributes);
	// instructionAttributes.addAttribute(CharacterConstants.Foreground, Color.BLUE);
	//
	// SimpleAttributeSet functionAttributes = new SimpleAttributeSet(baseAttributes);
	// functionAttributes.addAttribute(CharacterConstants.Foreground, Color.green);
	//
	// doc.addStyle("locationAttributes", null);
	// doc.addStyle("opCodeAttributes", null);
	// doc.addStyle("instructionAttributes", null);
	// doc.addStyle("functionAttributes", null);
	//
	// }

	/* Standard Stuff */

	private void appClose() {
		Preferences myPrefs = Preferences.userNodeForPackage(StyleExplore.class);
		Dimension dim = frmReflec.getSize();
		myPrefs.putInt("Height", dim.height);
		myPrefs.putInt("Width", dim.width);
		Point point = frmReflec.getLocation();
		myPrefs.putInt("LocX", point.x);
		myPrefs.putInt("LocY", point.y);
		myPrefs = null;
	}// appClose

	private void appInit() {
		Preferences myPrefs = Preferences.userNodeForPackage(StyleExplore.class);
		frmReflec.setSize(761, 722);
		frmReflec.setLocation(myPrefs.getInt("LocX", 100), myPrefs.getInt("LocY", 100));
		myPrefs = null;

		txtpnPP.setText("p 1\np 2\np 3\n");
		Style style = txtpnPP.addStyle("Sample Style", null);
		txtpnPP.getStyledDocument().setLogicalStyle(0, style);
		txtpnPP.getStyledDocument().setLogicalStyle(4, style);

		SimpleAttributeSet baseAttributes = new SimpleAttributeSet();
		StyleConstants.setFontFamily(baseAttributes, "Courier New");
		StyleConstants.setFontSize(baseAttributes, 16);
		StyleConstants.setForeground(baseAttributes, Color.GRAY);
		// location
		SimpleAttributeSet locationAttribute = new SimpleAttributeSet(baseAttributes);
		StyleConstants.setForeground(locationAttribute, Color.black);
		//
		SimpleAttributeSet locationBoldAttribute = new SimpleAttributeSet(locationAttribute);
		StyleConstants.setBold(locationBoldAttribute, true);
		// opCode
		SimpleAttributeSet opCodeAttribute = new SimpleAttributeSet(baseAttributes);
		opCodeAttribute.addAttribute(CharacterConstants.Foreground, Color.red);
		//
		SimpleAttributeSet opCodeBoldAttribute = new SimpleAttributeSet(opCodeAttribute);
		StyleConstants.setBold(opCodeBoldAttribute, true);
		// instruction
		SimpleAttributeSet instructionAttribute = new SimpleAttributeSet(baseAttributes);
		instructionAttribute.addAttribute(CharacterConstants.Foreground, Color.BLUE);
		//
		SimpleAttributeSet instructionBoldAttribute = new SimpleAttributeSet(instructionAttribute);
		StyleConstants.setBold(instructionBoldAttribute, true);
		// function
		SimpleAttributeSet functionAttribute = new SimpleAttributeSet(baseAttributes);
		functionAttribute.addAttribute(CharacterConstants.Foreground, Color.green);
		//
		SimpleAttributeSet functionBoldAttribute = new SimpleAttributeSet(functionAttribute);
		StyleConstants.setBold(functionBoldAttribute, true);

		StyleConstants.setBold(style, true);
		StyledDocument doc = txtpnPP.getStyledDocument();
		// makeStyles1( doc);
		try {
			doc.insertString(doc.getLength(), "locationAttribute\n", locationAttribute);
			doc.insertString(doc.getLength(), "locationBoldAttribute\n", locationBoldAttribute);
			doc.insertString(doc.getLength(), "opCodeAttribute\n", opCodeAttribute);
			doc.insertString(doc.getLength(), "opCodeBoldAttributes\n", opCodeBoldAttribute);
			doc.insertString(doc.getLength(), "instructionAttribute   ", instructionAttribute);
			doc.insertString(doc.getLength(), "functionAttribute\n", functionAttribute);
			doc.insertString(doc.getLength(), "instructionBoldAttribute   ", instructionBoldAttribute);
			doc.insertString(doc.getLength(), "functionBoldAttribute\n", functionAttribute);

			
			doc.insertString(doc.getLength(), "locationAttribute\n", locationAttribute);
			doc.insertString(doc.getLength(), "locationBoldAttribute\n", locationBoldAttribute);
			doc.insertString(doc.getLength(), "opCodeAttribute\n", opCodeAttribute);
			doc.insertString(doc.getLength(), "opCodeBoldAttributes\n", opCodeBoldAttribute);
			doc.insertString(doc.getLength(), "instructionAttribute   ", instructionAttribute);
			doc.insertString(doc.getLength(), "functionAttribute\n", functionAttribute);
			doc.insertString(doc.getLength(), "instructionBoldAttribute   ", instructionBoldAttribute);
			doc.insertString(doc.getLength(), "functionBoldAttribute\n", functionAttribute);

		} catch (Exception e) {
			// TODO: handle exception
		}

	}// appInit

	public StyleExplore() {
		initialize();
		appInit();
	}// Constructor

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmReflec = new JFrame();
		frmReflec.setTitle("GUItemplate");
		frmReflec.setBounds(100, 100, 450, 300);
		frmReflec.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 350, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		frmReflec.getContentPane().setLayout(gridBagLayout);

		toolBar = new JToolBar();
		GridBagConstraints gbc_toolBar = new GridBagConstraints();
		gbc_toolBar.gridwidth = 2;
		gbc_toolBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_toolBar.insets = new Insets(0, 0, 5, 0);
		gbc_toolBar.gridx = 0;
		gbc_toolBar.gridy = 0;
		frmReflec.getContentPane().add(toolBar, gbc_toolBar);

		btnOne = new JButton("1");
		btnOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doButton1();
			}
		});
		btnOne.setMinimumSize(new Dimension(30, 20));
		btnOne.setMaximumSize(new Dimension(30, 20));
		btnOne.setPreferredSize(new Dimension(30, 20));
		toolBar.add(btnOne);

		btnTwo = new JButton("2");
		btnTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				doButton2();
			}
		});
		btnTwo.setPreferredSize(new Dimension(30, 20));
		btnTwo.setMinimumSize(new Dimension(30, 20));
		btnTwo.setMaximumSize(new Dimension(30, 20));
		toolBar.add(btnTwo);

		btnThree = new JButton("3");
		btnThree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doButton3();
			}
		});
		btnThree.setPreferredSize(new Dimension(30, 20));
		btnThree.setMinimumSize(new Dimension(30, 20));
		btnThree.setMaximumSize(new Dimension(30, 20));
		toolBar.add(btnThree);

		btnFour = new JButton("4");
		btnFour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doButton4();
			}
		});
		btnFour.setPreferredSize(new Dimension(30, 20));
		btnFour.setMinimumSize(new Dimension(30, 20));
		btnFour.setMaximumSize(new Dimension(30, 20));
		toolBar.add(btnFour);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		frmReflec.getContentPane().add(scrollPane, gbc_scrollPane);

		txtpnPP = new JTextPane();
		// textPane.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtpnPP.setFont(new Font("Courier New", Font.PLAIN, 27));
		scrollPane.setViewportView(txtpnPP);

		panelDetails = new JPanel();
		GridBagConstraints gbc_panelDetails = new GridBagConstraints();
		gbc_panelDetails.insets = new Insets(0, 0, 5, 0);
		gbc_panelDetails.fill = GridBagConstraints.BOTH;
		gbc_panelDetails.gridx = 1;
		gbc_panelDetails.gridy = 1;
		frmReflec.getContentPane().add(panelDetails, gbc_panelDetails);
		GridBagLayout gbl_panelDetails = new GridBagLayout();
		gbl_panelDetails.columnWidths = new int[] { 0, 0 };
		gbl_panelDetails.rowHeights = new int[] { 0, 0 };
		gbl_panelDetails.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelDetails.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelDetails.setLayout(gbl_panelDetails);

		splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;
		panelDetails.add(splitPane, gbc_splitPane);

		scrollPaneElements = new JScrollPane();
		splitPane.setLeftComponent(scrollPaneElements);

		lblNewLabel = new JLabel("Elements");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPaneElements.setColumnHeaderView(lblNewLabel);
		// scrollPaneElements.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, JComponent(new String("0")));

		treeElements = new JTree();
		treeElements.addTreeSelectionListener(this);
		scrollPaneElements.setViewportView(treeElements);

		scrollPaneAttributes = new JScrollPane();
		splitPane.setRightComponent(scrollPaneAttributes);

		lblAttributes = new JLabel("Attributes");
		lblAttributes.setHorizontalAlignment(SwingConstants.CENTER);
		lblAttributes.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPaneAttributes.setColumnHeaderView(lblAttributes);

		textAttributes = new JTextArea();
		textAttributes.setEditable(false);
		scrollPaneAttributes.setViewportView(textAttributes);
		splitPane.setDividerLocation(200);

		JMenuBar menuBar = new JMenuBar();
		frmReflec.setJMenuBar(menuBar);
		frmReflec.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				appClose();
			}
		});
	}// initialize

	private static final String ATTRIBUTES = "Attributes";
	private static final String BRANCH = "Branch";
	private static final String DOCUMENT = "DefaultStyledDocument";
	private static final String LEAF = "Leaf";
	private static final String RESOLVER = "resolver";
	private static final String STYLES = "Styles";

	private static final String NL = System.lineSeparator();

}// class GUItemplate
