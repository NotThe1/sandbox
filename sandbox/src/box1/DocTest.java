package box1;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyleContext.NamedStyle;
import javax.swing.text.StyledDocument;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JSpinner;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTree;

public class DocTest implements DocumentListener {

	private JFrame frame;
	private JTextPane txtTarget1;
	private JScrollPane scrollPane;

	private String rawSource;
	private byte[] byteSource;
	private JTextArea txtTarget2;
	private HashMap<Integer, Byte> memoryImage;
	private StringBuilder lineBuilder;
	byte[] pmm;
	private StyledDocument doc;
	Style styleDefault, styleHistory, styleCurrent, styleFuture;
	Style styleCurrentLocation, styleCurrentInstruction, styleCurrentComments;
	Style styleFutureLocation, styleFutureInstruction, styleFutureComments;

	// /////////////////////////////////////
	HashMap<Byte, OperationStructure> opcodeMap;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DocTest window = new DocTest();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}// main

	private void doNewButton() {
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Root Node");
		DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
		tree.setModel(treeModel);

		Element docRoot = doc.getDefaultRootElement();
		int childCount = docRoot.getElementCount();
		Element childElement;
		for (int branchIndex = 0; branchIndex < childCount; branchIndex++) {
			childElement = docRoot.getElement(branchIndex);
			if ((childElement.getEndOffset() - childElement.getStartOffset()) > 5) {
				rootNode.add(addBranch(childElement));
			}// if not empty
		}// for child elements
		tree.scrollRowToVisible(25);
	}// doNewButton

	private DefaultMutableTreeNode addBranch(Element childElement) {
		String name = childElement.getName();
		int start = childElement.getStartOffset();
		int end = childElement.getEndOffset();
		String id = "";
		try {
			id = childElement.getDocument().getText(start, 5);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String branchName = String.format("%s BranchElement(%s) %d,%d", id, name, start, end);
		DefaultMutableTreeNode branch = new DefaultMutableTreeNode(branchName);
		Element grandchildElement;
		if (childElement.getElementCount() != 0) {
			for (int leafIndex = 0; leafIndex < childElement.getElementCount(); leafIndex++) {
				grandchildElement = childElement.getElement(leafIndex);
				branch.add(addLeaf(grandchildElement));
			}// grandchild Element
		}// if leaves
		return branch;
	}// addBranch

	private DefaultMutableTreeNode addLeaf(Element grandchildElement) {
		String name = grandchildElement.getName();
		int start = grandchildElement.getStartOffset();
		int end = grandchildElement.getEndOffset();
		String leafName = String.format("\tLeaf Element(%s) %d,%d", name, start, end);
		DefaultMutableTreeNode leaf = new DefaultMutableTreeNode(leafName);
		return leaf;
	}// addLeaf

	private void createNodes(DefaultMutableTreeNode top) {
		DefaultMutableTreeNode category = null;
		DefaultMutableTreeNode book = null;
		category = new DefaultMutableTreeNode("Books for Java Programmers");
		top.add(category);
	}// createNodes

	private void walkDocument(StyledDocument doc, JTextArea txtTarget) {
		javax.swing.text.Element docRoot = doc.getDefaultRootElement();
		int childCount = docRoot.getElementCount();
		String rootInfo = String.format("Root:  Leaf - %s, number of chilld elements - %d%n", docRoot.isLeaf(),
				childCount);
		txtTarget.append(rootInfo);
		String childInfo;
		int elementStart;
		String lineID = "";
		int childElementCount;
		javax.swing.text.Element childElement, grandchildElement;
		for (int i = 0; i < childCount; i++) {
			childElement = docRoot.getElement(i);
			elementStart = childElement.getStartOffset();
			try {
				lineID = doc.getText(elementStart, 5);
			} catch (BadLocationException e) {
				break;
				// e.printStackTrace();
			}
			childElementCount = childElement.getElementCount();
			childInfo = String.format("   %3d\t%-4d - %4d\tID:%s\tName: %s%n",
					i, elementStart, childElement.getEndOffset(), lineID, childElement.getName());
			txtTarget.append(childInfo);

			for (int j = 0; j < childElementCount; j++) {
				grandchildElement = childElement.getElement(j);
				elementStart = grandchildElement.getStartOffset();

				childInfo = String.format("\t   %3d\t%-4d - %4d\tName: %s%n",
						j, elementStart, grandchildElement.getEndOffset(), grandchildElement.getName());
				txtTarget.append(childInfo);

				AttributeSet attributeSet = grandchildElement.getAttributes();
				Enumeration<Object> keys = (Enumeration<Object>) attributeSet.getAttributeNames();

				String attributeInfo;
				Object value, key;
				while (keys.hasMoreElements()) {
					key = keys.nextElement();
					value = (Object) attributeSet.getAttribute(key);
					attributeInfo = String.format("\t\t%-25s - %s%n", key.toString(), value.toString());
					txtTarget.append(attributeInfo);
				}// while
				txtTarget.append(System.lineSeparator());
			}// for grandChild

		}// for child
		txtTarget.setCaretPosition(0);
	}// walkDocument

	private SimpleAttributeSet[] attributeSet() {
		SimpleAttributeSet[] attrs = new SimpleAttributeSet[3];
		attrs[0] = new SimpleAttributeSet();
		StyleConstants.setForeground(attrs[0], Color.black);

		attrs[1] = new SimpleAttributeSet();
		StyleConstants.setForeground(attrs[1], Color.RED);
		StyleConstants.setBold(attrs[1], true);

		attrs[2] = new SimpleAttributeSet();
		StyleConstants.setForeground(attrs[2], Color.BLUE);
		return attrs;
	}

	private SimpleAttributeSet[] attrs = attributeSet();
	private JPanel panel;
	private JSpinner spinner;
	private JButton btnNewButton;
	private JMenuBar menuBar;
	private JMenu mnuFile;
	private JMenuItem mnuFileAdd;
	private JMenuItem mnuFileRemove;
	private JPopupMenu popMnuTest1;
	private JCheckBoxMenuItem chckbxmntmNewCheckItem;
	private JScrollPane scrollPane_1;
	private JPanel panelRaw;
	private JTabbedPane tabbedPane;
	private JPanel panelTree;
	private JScrollPane scrollPane_2;
	private JTree tree;
	private JLabel lblNewLabel;

	private void line0() {
		txtTarget1.setCaretPosition(0);

		// scrollPane.getVerticalScrollBar().getValue();
		scrollPane.getVerticalScrollBar().setValue(0);

	}// line0

	private void appInit() {
		makeOpcodeMap();
		OperationStructure test = opcodeMap.get((byte) 0X03);
		doc = (StyledDocument) txtTarget1.getDocument();
		makeStyles(doc);
		try {
			byte codeByte;
			for (int code = 0; code <= 0XFF; code++) {
				codeByte = (byte) code;
				doc.insertString(doc.getLength(), String.format("%04X: ", code), styleCurrentInstruction);
				// doc.insertString(doc.getLength(), String.format("%04X: ", code), attrs[1]);
				switch (opcodeMap.get(codeByte).getSize()) {
				case 1:
					doc.insertString(doc.getLength(), opcodeMap.get((byte) code).getAssemblerCode(), null);
					break;
				case 2:
					doc.insertString(doc.getLength(), opcodeMap.get((byte) code).getAssemblerCode((byte) 0X5A), null);
					break;
				case 3:
					doc.insertString(doc.getLength(),
							opcodeMap.get((byte) code).getAssemblerCode((byte) 0X34, (byte) 0XAB), null);
					break;
				}// switch

			}// for
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// try
		line0();
		walkDocument(doc, txtTarget2);

	}// appInit
		// ------------------------------------------------------------------

	private void makeStyles(StyledDocument doc) {
		Color colorLocation = Color.BLUE;
		Color colorInstruction = Color.RED;
		Color colorComments = Color.GREEN;

		String DEFAULT = StyleContext.DEFAULT_STYLE;
		styleDefault = (Style) doc.getStyle(StyleContext.DEFAULT_STYLE);
		StyleConstants.setFontFamily(styleDefault, "Courier New");
		StyleConstants.setFontSize(styleDefault, 16);
		doc.addStyle(DEFAULT, null);

		String HISTORY = "History";
		styleHistory = doc.getStyle(DEFAULT);
		StyleConstants.setForeground(styleHistory, Color.GRAY);
		doc.addStyle(HISTORY, null);

		String CURRENT = "Current";
		styleCurrent = doc.getStyle(DEFAULT);
		StyleConstants.setBold(styleCurrent, true);
		StyleConstants.setBackground(styleDefault, Color.YELLOW);
		doc.addStyle(CURRENT, null);

		String CURRENT_LOCATION = "CurrentLocation";
		styleCurrentLocation = doc.getStyle(CURRENT);
		StyleConstants.setForeground(styleCurrentLocation, colorLocation);
		doc.addStyle(CURRENT_LOCATION, null);

		String CURRENT_INSTRUCTION = "CurrentInstruction";
		styleCurrentInstruction = doc.getStyle(CURRENT);
		StyleConstants.setForeground(styleCurrentLocation, colorInstruction);
		doc.addStyle(CURRENT_INSTRUCTION, null);

		String CURRENT_COMMENTS = "CurrentComments";
		styleCurrentComments = doc.getStyle(CURRENT);
		StyleConstants.setForeground(styleCurrentLocation, colorComments);
		doc.addStyle(CURRENT_COMMENTS, null);

		String FUTURE = "Future";
		styleFuture = doc.getStyle(DEFAULT);
		doc.addStyle(FUTURE, null);

		String FUTURE_LOCATION = "FutureLocation";
		styleFutureLocation = doc.getStyle(FUTURE);
		StyleConstants.setForeground(styleFutureLocation, colorLocation);
		doc.addStyle(FUTURE_LOCATION, null);

		String FUTURE_INSTRUCTION = "FutureInstruction";
		styleFutureInstruction = doc.getStyle(FUTURE);
		StyleConstants.setForeground(styleFutureInstruction, colorInstruction);
		doc.addStyle(FUTURE_INSTRUCTION, null);

		String FUTURE_COMMENTS = "FutureComments";
		styleFutureComments = doc.getStyle(FUTURE);
		StyleConstants.setForeground(styleFutureComments, colorComments);
		doc.addStyle(FUTURE_COMMENTS, null);

		// StyleConstants.setForeground(style,Color.GREEN);
		// doc.addStyle("styleBlack", null);
		// Style x = doc.getStyle(BOLD_ITALIC);

	}//
		// ------------------------------------------------------------------

	// check the rotates (not) thru carry

	private void makeOpcodeMap() {
		opcodeMap = new HashMap<Byte, OperationStructure>();
		opcodeMap.put((byte) 0X00, new OperationStructure((byte) 0X00, 1, "NOP", "", "", ""));
		opcodeMap.put((byte) 0X01, new OperationStructure((byte) 0X01, 3, "LXI", "B", "D16", "B<- byte3, C<- byte2"));
		opcodeMap.put((byte) 0X02, new OperationStructure((byte) 0X02, 1, "STAX", "B", "", "(BC)<-A"));
		opcodeMap.put((byte) 0X03, new OperationStructure((byte) 0X03, 1, "INX", "B", "", "BC<-BC+1"));
		opcodeMap.put((byte) 0X04, new OperationStructure((byte) 0X04, 1, "INR", "B", "", "B<-B+1"));
		opcodeMap.put((byte) 0X05, new OperationStructure((byte) 0X05, 1, "DCR", "B", "", "B<-B-1"));
		opcodeMap.put((byte) 0X06, new OperationStructure((byte) 0X06, 2, "MVI", "B", "D8", "B<-byte2"));
		opcodeMap.put((byte) 0X07, new OperationStructure((byte) 0X07, 1, "RLC", "", "", "A=A << not thru carry"));
		opcodeMap.put((byte) 0X08, new OperationStructure((byte) 0X08, 1, "NONE", "", "", ""));
		opcodeMap.put((byte) 0X09, new OperationStructure((byte) 0X09, 1, "DAD", "B", "", "HL = HL + BC"));
		opcodeMap.put((byte) 0X0A, new OperationStructure((byte) 0X0A, 1, "LDAX", "B", "", "A<-(BC)"));
		opcodeMap.put((byte) 0X0B, new OperationStructure((byte) 0X0B, 1, "DCX", "B", "", "BC = BC-1"));
		opcodeMap.put((byte) 0X0C, new OperationStructure((byte) 0X0C, 1, "INR", "C", "", "C <-C+1"));
		opcodeMap.put((byte) 0X0D, new OperationStructure((byte) 0X0D, 1, "DCR", "C", "", "C <-C-1"));
		opcodeMap.put((byte) 0X0E, new OperationStructure((byte) 0X0E, 2, "MVI", "C", "D8", "C,-byte2"));
		opcodeMap.put((byte) 0X0F, new OperationStructure((byte) 0X0F, 1, "RRC", "", "", "A = A>> not thru carry"));

		opcodeMap.put((byte) 0X10, new OperationStructure((byte) 0X10, 1, "NONE", "", "", ""));
		opcodeMap.put((byte) 0X11, new OperationStructure((byte) 0X11, 3, "LXI", "D", "D16", "D<-byte3,E<-byte2"));
		opcodeMap.put((byte) 0X12, new OperationStructure((byte) 0X12, 1, "STAX", "D", "", "(DE)<-A"));
		opcodeMap.put((byte) 0X13, new OperationStructure((byte) 0X13, 1, "INX", "D", "", "DE<-DE + 1"));
		opcodeMap.put((byte) 0X14, new OperationStructure((byte) 0X14, 1, "INR", "D", "", "D<-D+1"));
		opcodeMap.put((byte) 0X15, new OperationStructure((byte) 0X15, 1, "DCR", "D", "", "D<-D-1"));
		opcodeMap.put((byte) 0X16, new OperationStructure((byte) 0X16, 2, "MVI", "D", "D8", "D<-byte2"));
		opcodeMap.put((byte) 0X17, new OperationStructure((byte) 0X17, 1, "RAL", "D", "", "A=A << thru carry"));
		opcodeMap.put((byte) 0X18, new OperationStructure((byte) 0X18, 1, "NONE", "", "", ""));
		opcodeMap.put((byte) 0X19, new OperationStructure((byte) 0X19, 1, "DAD", "D", "", "HL = HL + DE"));
		opcodeMap.put((byte) 0X1A, new OperationStructure((byte) 0X1A, 1, "LDAX", "D", "", "A<-(DE)"));
		opcodeMap.put((byte) 0X1B, new OperationStructure((byte) 0X1B, 1, "DCX", "D", "", "DE = DE-1"));
		opcodeMap.put((byte) 0X1C, new OperationStructure((byte) 0X1C, 1, "INR", "E", "", "E <-E+1"));
		opcodeMap.put((byte) 0X1D, new OperationStructure((byte) 0X1D, 1, "DCR", "E", "", "E <-E-1"));
		opcodeMap.put((byte) 0X1E, new OperationStructure((byte) 0X1E, 2, "MVI", "E", "D8", "E,-byte2"));
		opcodeMap.put((byte) 0X1F, new OperationStructure((byte) 0X1F, 1, "RAR", "", "", "A = A>> not thru carry"));

		opcodeMap.put((byte) 0X20, new OperationStructure((byte) 0X20, 1, "NONE", "", "", ""));// special
		opcodeMap.put((byte) 0X21, new OperationStructure((byte) 0X21, 3, "LXI", "H", "D16", "H<-byte3,L<-byte2"));
		opcodeMap.put((byte) 0X22,
				new OperationStructure((byte) 0X22, 3, "SHLD", "addr", "", "(addr)<-L;(addr+1)<-H"));
		opcodeMap.put((byte) 0X23, new OperationStructure((byte) 0X23, 1, "INX", "H", "", "HL<-HL + 1"));
		opcodeMap.put((byte) 0X24, new OperationStructure((byte) 0X24, 1, "INR", "H", "", "H<-H+1"));
		opcodeMap.put((byte) 0X25, new OperationStructure((byte) 0X25, 1, "DCR", "H", "", "H<-H-1"));
		opcodeMap.put((byte) 0X26, new OperationStructure((byte) 0X26, 2, "MVI", "H", "D8", "H<-byte2"));
		opcodeMap.put((byte) 0X27, new OperationStructure((byte) 0X27, 1, "DAA", "", "", "")); // special
		opcodeMap.put((byte) 0X28, new OperationStructure((byte) 0X28, 1, "NONE", "", "", ""));
		opcodeMap.put((byte) 0X29, new OperationStructure((byte) 0X29, 1, "DAD", "H", "", "HL = HL + HL"));
		opcodeMap.put((byte) 0X2A,
				new OperationStructure((byte) 0X2A, 3, "LHLD", "addr", "", "L<-(addr);H<-(addr+1)"));
		opcodeMap.put((byte) 0X2B, new OperationStructure((byte) 0X2B, 1, "DCX", "H", "", "HL = HL-1"));
		opcodeMap.put((byte) 0X2C, new OperationStructure((byte) 0X2C, 1, "INR", "L", "", "L <-L+1"));
		opcodeMap.put((byte) 0X2D, new OperationStructure((byte) 0X2D, 1, "DCR", "L", "", "L <-L-1"));
		opcodeMap.put((byte) 0X2E, new OperationStructure((byte) 0X2E, 2, "MVI", "L", "D8", "L,-byte2"));
		opcodeMap.put((byte) 0X2F, new OperationStructure((byte) 0X2F, 1, "CMA", "", "", "A = !A"));

		opcodeMap.put((byte) 0X30, new OperationStructure((byte) 0X30, 1, "SIM", "", "", "")); // special
		opcodeMap.put((byte) 0X31, new OperationStructure((byte) 0X31, 3, "LXI", "SP", "D16",
				"SP.hi<-byte3;SP.lo<-byte2"));
		opcodeMap.put((byte) 0X32, new OperationStructure((byte) 0X32, 3, "STA", "addr", "", "(addr)<-A"));
		opcodeMap.put((byte) 0X33, new OperationStructure((byte) 0X33, 1, "INX", "SP", "", "SP<-SP + 1"));
		opcodeMap.put((byte) 0X34, new OperationStructure((byte) 0X34, 1, "INR", "M", "", "(HL)<-(HL)+1"));
		opcodeMap.put((byte) 0X35, new OperationStructure((byte) 0X35, 1, "DCR", "M", "", "(HL)<-(HL)-1"));
		opcodeMap.put((byte) 0X36, new OperationStructure((byte) 0X36, 2, "MVI", "M", "D8", "(HL)<-byte2"));
		opcodeMap.put((byte) 0X37, new OperationStructure((byte) 0X37, 1, "STC", "", "", "CY=1"));
		opcodeMap.put((byte) 0X38, new OperationStructure((byte) 0X38, 1, "NONE", "", "", ""));
		opcodeMap.put((byte) 0X39, new OperationStructure((byte) 0X39, 1, "DAD", "SP", "", "HL = HL + SP"));
		opcodeMap.put((byte) 0X3A, new OperationStructure((byte) 0X3A, 3, "LDA", "addr", "", "A<-(addr)"));
		opcodeMap.put((byte) 0X3B, new OperationStructure((byte) 0X3B, 1, "DCX", "SP", "", "SP = SP-1"));
		opcodeMap.put((byte) 0X3C, new OperationStructure((byte) 0X3C, 1, "INR", "A", "", "A <-A+1"));
		opcodeMap.put((byte) 0X3D, new OperationStructure((byte) 0X3D, 1, "DCR", "A", "", "A <-A-1"));
		opcodeMap.put((byte) 0X3E, new OperationStructure((byte) 0X3E, 2, "MVI", "A", "D8", "A,-byte2"));
		opcodeMap.put((byte) 0X3F, new OperationStructure((byte) 0X3F, 1, "CMC", "", "", "CY=!CY"));

		opcodeMap.put((byte) 0X40, new OperationStructure((byte) 0X40, 1, "MOV", "B", ",B", "B <- B"));
		opcodeMap.put((byte) 0X41, new OperationStructure((byte) 0X41, 1, "MOV", "B", ",C", "B <- C"));
		opcodeMap.put((byte) 0X42, new OperationStructure((byte) 0X42, 1, "MOV", "B", ",D", "B <- D"));
		opcodeMap.put((byte) 0X43, new OperationStructure((byte) 0X43, 1, "MOV", "B", ",E", "B <- E"));
		opcodeMap.put((byte) 0X44, new OperationStructure((byte) 0X44, 1, "MOV", "B", ",H", "B <- H"));
		opcodeMap.put((byte) 0X45, new OperationStructure((byte) 0X45, 1, "MOV", "B", ",L", "B <- L"));
		opcodeMap.put((byte) 0X46, new OperationStructure((byte) 0X46, 1, "MOV", "B", ",M", "B <- (HL)"));
		opcodeMap.put((byte) 0X47, new OperationStructure((byte) 0X47, 1, "MOV", "B", ",A", "B <- A"));

		opcodeMap.put((byte) 0X48, new OperationStructure((byte) 0X48, 1, "MOV", "C", ",B", "C <- B"));
		opcodeMap.put((byte) 0X49, new OperationStructure((byte) 0X49, 1, "MOV", "C", ",C", "C <- C"));
		opcodeMap.put((byte) 0X4A, new OperationStructure((byte) 0X4A, 1, "MOV", "C", ",D", "C <- D"));
		opcodeMap.put((byte) 0X4B, new OperationStructure((byte) 0X4B, 1, "MOV", "C", ",E", "C <- E"));
		opcodeMap.put((byte) 0X4C, new OperationStructure((byte) 0X4C, 1, "MOV", "C", ",H", "C <- H"));
		opcodeMap.put((byte) 0X4D, new OperationStructure((byte) 0X4D, 1, "MOV", "C", ",L", "C <- L"));
		opcodeMap.put((byte) 0X4E, new OperationStructure((byte) 0X4E, 1, "MOV", "C", ",M", "C <- (HL)"));
		opcodeMap.put((byte) 0X4F, new OperationStructure((byte) 0X4F, 1, "MOV", "C", ",A", "C <- A"));

		opcodeMap.put((byte) 0X50, new OperationStructure((byte) 0X50, 1, "MOV", "D", ",B", "D <- B"));
		opcodeMap.put((byte) 0X51, new OperationStructure((byte) 0X51, 1, "MOV", "D", ",C", "D <- C"));
		opcodeMap.put((byte) 0X52, new OperationStructure((byte) 0X52, 1, "MOV", "D", ",D", "D <- D"));
		opcodeMap.put((byte) 0X53, new OperationStructure((byte) 0X53, 1, "MOV", "D", ",E", "D <- E"));
		opcodeMap.put((byte) 0X54, new OperationStructure((byte) 0X54, 1, "MOV", "D", ",H", "D <- H"));
		opcodeMap.put((byte) 0X55, new OperationStructure((byte) 0X55, 1, "MOV", "D", ",L", "D <- L"));
		opcodeMap.put((byte) 0X56, new OperationStructure((byte) 0X56, 1, "MOV", "D", ",M", "D <- (HL)"));
		opcodeMap.put((byte) 0X57, new OperationStructure((byte) 0X57, 1, "MOV", "D", ",A", "D <- A"));

		opcodeMap.put((byte) 0X58, new OperationStructure((byte) 0X58, 1, "MOV", "E", ",B", "E <- B"));
		opcodeMap.put((byte) 0X59, new OperationStructure((byte) 0X59, 1, "MOV", "E", ",C", "E <- C"));
		opcodeMap.put((byte) 0X5A, new OperationStructure((byte) 0X5A, 1, "MOV", "E", ",D", "E <- D"));
		opcodeMap.put((byte) 0X5B, new OperationStructure((byte) 0X5B, 1, "MOV", "E", ",E", "E <- E"));
		opcodeMap.put((byte) 0X5C, new OperationStructure((byte) 0X5C, 1, "MOV", "E", ",H", "E <- H"));
		opcodeMap.put((byte) 0X5D, new OperationStructure((byte) 0X5D, 1, "MOV", "E", ",L", "E <- L"));
		opcodeMap.put((byte) 0X5E, new OperationStructure((byte) 0X5E, 1, "MOV", "E", ",M", "E <- (HL)"));
		opcodeMap.put((byte) 0X5F, new OperationStructure((byte) 0X5F, 1, "MOV", "E", ",A", "E <- A"));

		opcodeMap.put((byte) 0X60, new OperationStructure((byte) 0X60, 1, "MOV", "H", ",B", "H <- B"));
		opcodeMap.put((byte) 0X61, new OperationStructure((byte) 0X61, 1, "MOV", "H", ",C", "H <- C"));
		opcodeMap.put((byte) 0X62, new OperationStructure((byte) 0X62, 1, "MOV", "H", ",D", "H <- D"));
		opcodeMap.put((byte) 0X63, new OperationStructure((byte) 0X63, 1, "MOV", "H", ",E", "H <- E"));
		opcodeMap.put((byte) 0X64, new OperationStructure((byte) 0X64, 1, "MOV", "H", ",H", "H <- H"));
		opcodeMap.put((byte) 0X65, new OperationStructure((byte) 0X65, 1, "MOV", "H", ",L", "H <- L"));
		opcodeMap.put((byte) 0X66, new OperationStructure((byte) 0X66, 1, "MOV", "H", ",M", "H <- (HL)"));
		opcodeMap.put((byte) 0X67, new OperationStructure((byte) 0X67, 1, "MOV", "H", ",A", "H <- A"));

		opcodeMap.put((byte) 0X68, new OperationStructure((byte) 0X68, 1, "MOV", "L", ",B", "L <- B"));
		opcodeMap.put((byte) 0X69, new OperationStructure((byte) 0X69, 1, "MOV", "L", ",C", "L <- C"));
		opcodeMap.put((byte) 0X6A, new OperationStructure((byte) 0X6A, 1, "MOV", "L", ",D", "L <- D"));
		opcodeMap.put((byte) 0X6B, new OperationStructure((byte) 0X6B, 1, "MOV", "L", ",E", "L <- E"));
		opcodeMap.put((byte) 0X6C, new OperationStructure((byte) 0X6C, 1, "MOV", "L", ",H", "L <- H"));
		opcodeMap.put((byte) 0X6D, new OperationStructure((byte) 0X6D, 1, "MOV", "L", ",L", "L <- L"));
		opcodeMap.put((byte) 0X6E, new OperationStructure((byte) 0X6E, 1, "MOV", "L", ",M", "L <- (HL)"));
		opcodeMap.put((byte) 0X6F, new OperationStructure((byte) 0X6F, 1, "MOV", "L", ",A", "L <- A"));

		opcodeMap.put((byte) 0X70, new OperationStructure((byte) 0X70, 1, "MOV", "M", ",B", "(HL) <- B"));
		opcodeMap.put((byte) 0X71, new OperationStructure((byte) 0X71, 1, "MOV", "M", ",C", "(HL) <- C"));
		opcodeMap.put((byte) 0X72, new OperationStructure((byte) 0X72, 1, "MOV", "M", ",D", "(HL) <- D"));
		opcodeMap.put((byte) 0X73, new OperationStructure((byte) 0X73, 1, "MOV", "M", ",E", "(HL) <- E"));
		opcodeMap.put((byte) 0X74, new OperationStructure((byte) 0X74, 1, "MOV", "M", ",H", "(HL) <- H"));
		opcodeMap.put((byte) 0X75, new OperationStructure((byte) 0X75, 1, "MOV", "M", ",L", "(HL) <- L"));
		opcodeMap.put((byte) 0X76, new OperationStructure((byte) 0X76, 1, "HLT", "", "", "Halt")); // Special
		opcodeMap.put((byte) 0X77, new OperationStructure((byte) 0X77, 1, "MOV", "M", ",A", "(HL) <- A"));

		opcodeMap.put((byte) 0X78, new OperationStructure((byte) 0X78, 1, "MOV", "A", ",B", "A <- B"));
		opcodeMap.put((byte) 0X79, new OperationStructure((byte) 0X79, 1, "MOV", "A", ",C", "A <- C"));
		opcodeMap.put((byte) 0X7A, new OperationStructure((byte) 0X7A, 1, "MOV", "A", ",D", "A <- D"));
		opcodeMap.put((byte) 0X7B, new OperationStructure((byte) 0X7B, 1, "MOV", "A", ",E", "A <- E"));
		opcodeMap.put((byte) 0X7C, new OperationStructure((byte) 0X7C, 1, "MOV", "A", ",H", "A <- H"));
		opcodeMap.put((byte) 0X7D, new OperationStructure((byte) 0X7D, 1, "MOV", "A", ",L", "A <- L"));
		opcodeMap.put((byte) 0X7E, new OperationStructure((byte) 0X7E, 1, "MOV", "A", ",M", "A <- (HL)"));
		opcodeMap.put((byte) 0X7F, new OperationStructure((byte) 0X7F, 1, "MOV", "A", ",A", "A <- A"));

		opcodeMap.put((byte) 0X80, new OperationStructure((byte) 0X80, 1, "ADD", "B", "", "A <- A+B"));
		opcodeMap.put((byte) 0X81, new OperationStructure((byte) 0X81, 1, "ADD", "C", "", "A <- A+C"));
		opcodeMap.put((byte) 0X82, new OperationStructure((byte) 0X82, 1, "ADD", "D", "", "A <- A+D"));
		opcodeMap.put((byte) 0X83, new OperationStructure((byte) 0X83, 1, "ADD", "E", "", "A <- A+E"));
		opcodeMap.put((byte) 0X84, new OperationStructure((byte) 0X84, 1, "ADD", "H", "", "A <- A+H"));
		opcodeMap.put((byte) 0X85, new OperationStructure((byte) 0X85, 1, "ADD", "L", "", "A <- A+L"));
		opcodeMap.put((byte) 0X86, new OperationStructure((byte) 0X86, 1, "ADD", "M", "", "A <- A+(HL)"));
		opcodeMap.put((byte) 0X87, new OperationStructure((byte) 0X87, 1, "ADD", "A", "", "A <- A+A"));

		opcodeMap.put((byte) 0X88, new OperationStructure((byte) 0X88, 1, "ADC", "B", "", "A <- A+B + CY"));
		opcodeMap.put((byte) 0X89, new OperationStructure((byte) 0X89, 1, "ADC", "C", "", "A <- A+C + CY"));
		opcodeMap.put((byte) 0X8A, new OperationStructure((byte) 0X8A, 1, "ADC", "D", "", "A <- A+D + CY"));
		opcodeMap.put((byte) 0X8B, new OperationStructure((byte) 0X8B, 1, "ADC", "E", "", "A <- A+E + CY"));
		opcodeMap.put((byte) 0X8C, new OperationStructure((byte) 0X8C, 1, "ADC", "H", "", "A <- A+H + CY"));
		opcodeMap.put((byte) 0X8D, new OperationStructure((byte) 0X8D, 1, "ADC", "L", "", "A <- A+L + CY"));
		opcodeMap.put((byte) 0X8E, new OperationStructure((byte) 0X8E, 1, "ADC", "M", "", "A <- A+(HL) + CY"));
		opcodeMap.put((byte) 0X8F, new OperationStructure((byte) 0X8F, 1, "ADC", "A", "", "A <- A+A + CY"));

		opcodeMap.put((byte) 0X90, new OperationStructure((byte) 0X90, 1, "SUB", "B", "", "A <- A-B"));
		opcodeMap.put((byte) 0X91, new OperationStructure((byte) 0X91, 1, "SUB", "C", "", "A <- A-C"));
		opcodeMap.put((byte) 0X92, new OperationStructure((byte) 0X92, 1, "SUB", "D", "", "A <- A-D"));
		opcodeMap.put((byte) 0X93, new OperationStructure((byte) 0X93, 1, "SUB", "E", "", "A <- A-E"));
		opcodeMap.put((byte) 0X94, new OperationStructure((byte) 0X94, 1, "SUB", "H", "", "A <- A-H"));
		opcodeMap.put((byte) 0X95, new OperationStructure((byte) 0X95, 1, "SUB", "L", "", "A <- A-L"));
		opcodeMap.put((byte) 0X96, new OperationStructure((byte) 0X96, 1, "SUB", "M", "", "A <- A-(HL)"));
		opcodeMap.put((byte) 0X97, new OperationStructure((byte) 0X97, 1, "SUB", "A", "", "A <- A-A"));

		opcodeMap.put((byte) 0X98, new OperationStructure((byte) 0X98, 1, "SBB", "B", "", "A <- A-B - CY"));
		opcodeMap.put((byte) 0X99, new OperationStructure((byte) 0X99, 1, "SBB", "C", "", "A <- A-C - CY"));
		opcodeMap.put((byte) 0X9A, new OperationStructure((byte) 0X9A, 1, "SBB", "D", "", "A <- A-D - CY"));
		opcodeMap.put((byte) 0X9B, new OperationStructure((byte) 0X9B, 1, "SBB", "E", "", "A <- A-E - CY"));
		opcodeMap.put((byte) 0X9C, new OperationStructure((byte) 0X9C, 1, "SBB", "H", "", "A <- A-H - CY"));
		opcodeMap.put((byte) 0X9D, new OperationStructure((byte) 0X9D, 1, "SBB", "L", "", "A <- A-L - CY"));
		opcodeMap.put((byte) 0X9E, new OperationStructure((byte) 0X9E, 1, "SBB", "M", "", "A <- A-(HL) - CY"));
		opcodeMap.put((byte) 0X9F, new OperationStructure((byte) 0X9F, 1, "SBB", "A", "", "A <- A-A - CY"));

		opcodeMap.put((byte) 0XA0, new OperationStructure((byte) 0XA0, 1, "ANA", "B", "", "A <- A&B"));
		opcodeMap.put((byte) 0XA1, new OperationStructure((byte) 0XA1, 1, "ANA", "C", "", "A <- A&C"));
		opcodeMap.put((byte) 0XA2, new OperationStructure((byte) 0XA2, 1, "ANA", "D", "", "A <- A&D"));
		opcodeMap.put((byte) 0XA3, new OperationStructure((byte) 0XA3, 1, "ANA", "E", "", "A <- A&E"));
		opcodeMap.put((byte) 0XA4, new OperationStructure((byte) 0XA4, 1, "ANA", "H", "", "A <- A&H"));
		opcodeMap.put((byte) 0XA5, new OperationStructure((byte) 0XA5, 1, "ANA", "L", "", "A <- A&L"));
		opcodeMap.put((byte) 0XA6, new OperationStructure((byte) 0XA6, 1, "ANA", "M", "", "A <- A&(HL)"));
		opcodeMap.put((byte) 0XA7, new OperationStructure((byte) 0XA7, 1, "ANA", "A", "", "A <- A&A"));

		opcodeMap.put((byte) 0XA8, new OperationStructure((byte) 0XA8, 1, "XRA", "B", "", "A <- A^B"));
		opcodeMap.put((byte) 0XA9, new OperationStructure((byte) 0XA9, 1, "XRA", "C", "", "A <- A^C"));
		opcodeMap.put((byte) 0XAA, new OperationStructure((byte) 0XAA, 1, "XRA", "D", "", "A <- A^D"));
		opcodeMap.put((byte) 0XAB, new OperationStructure((byte) 0XAB, 1, "XRA", "E", "", "A <- A^E"));
		opcodeMap.put((byte) 0XAC, new OperationStructure((byte) 0XAC, 1, "XRA", "H", "", "A <- A^H"));
		opcodeMap.put((byte) 0XAD, new OperationStructure((byte) 0XAD, 1, "XRA", "L", "", "A <- A^L"));
		opcodeMap.put((byte) 0XAE, new OperationStructure((byte) 0XAE, 1, "XRA", "M", "", "A <- A^(HL)"));
		opcodeMap.put((byte) 0XAF, new OperationStructure((byte) 0XAF, 1, "XRA", "A", "", "A <- A^A"));

		opcodeMap.put((byte) 0XB0, new OperationStructure((byte) 0XB0, 1, "ORA", "B", "", "A <- A|B"));
		opcodeMap.put((byte) 0XB1, new OperationStructure((byte) 0XB1, 1, "ORA", "C", "", "A <- A|C"));
		opcodeMap.put((byte) 0XB2, new OperationStructure((byte) 0XB2, 1, "ORA", "D", "", "A <- A|D"));
		opcodeMap.put((byte) 0XB3, new OperationStructure((byte) 0XB3, 1, "ORA", "E", "", "A <- A|E"));
		opcodeMap.put((byte) 0XB4, new OperationStructure((byte) 0XB4, 1, "ORA", "H", "", "A <- A|H"));
		opcodeMap.put((byte) 0XB5, new OperationStructure((byte) 0XB5, 1, "ORA", "L", "", "A <- A|L"));
		opcodeMap.put((byte) 0XB6, new OperationStructure((byte) 0XB6, 1, "ORA", "M", "", "A <- A|(HL)"));
		opcodeMap.put((byte) 0XB7, new OperationStructure((byte) 0XB7, 1, "ORA", "A", "", "A <- A|A"));

		opcodeMap.put((byte) 0XB8, new OperationStructure((byte) 0XB8, 1, "CMP", "B", "", "A - B"));
		opcodeMap.put((byte) 0XB9, new OperationStructure((byte) 0XB9, 1, "CMP", "C", "", "A - C"));
		opcodeMap.put((byte) 0XBA, new OperationStructure((byte) 0XBA, 1, "CMP", "D", "", "A - D"));
		opcodeMap.put((byte) 0XBB, new OperationStructure((byte) 0XBB, 1, "CMP", "E", "", "A - E"));
		opcodeMap.put((byte) 0XBC, new OperationStructure((byte) 0XBC, 1, "CMP", "H", "", "A - H"));
		opcodeMap.put((byte) 0XBD, new OperationStructure((byte) 0XBD, 1, "CMP", "L", "", "A - L"));
		opcodeMap.put((byte) 0XBE, new OperationStructure((byte) 0XBE, 1, "CMP", "M", "", "A - (HL)"));
		opcodeMap.put((byte) 0XBF, new OperationStructure((byte) 0XBF, 1, "CMP", "A", "", "A - A"));

		opcodeMap.put((byte) 0XC0, new OperationStructure((byte) 0XC0, 1, "RNZ", "", "", "if NZ, ret"));
		opcodeMap.put((byte) 0XC1, new OperationStructure((byte) 0XC1, 1, "POP", "B", "", ""));
		opcodeMap.put((byte) 0XC2, new OperationStructure((byte) 0XC2, 3, "JNZ", "addr", "", "if NZ,PC<-addr"));
		opcodeMap.put((byte) 0XC3, new OperationStructure((byte) 0XC3, 3, "JMP", "addr", "", "PC<-addr"));
		opcodeMap.put((byte) 0XC4, new OperationStructure((byte) 0XC4, 3, "CNZ", "addr", "", "if NZ,CALL addr"));
		opcodeMap.put((byte) 0XC5, new OperationStructure((byte) 0XC5, 1, "PUSH", "B", "", ""));
		opcodeMap.put((byte) 0XC6, new OperationStructure((byte) 0XC6, 2, "ADI", "D8", "", "A<-A + byte2"));
		opcodeMap.put((byte) 0XC7, new OperationStructure((byte) 0XC7, 1, "RST", "0", "", "CALL $0"));
		opcodeMap.put((byte) 0XC8, new OperationStructure((byte) 0XC8, 1, "RZ", "", "", "if Z, ret"));
		opcodeMap.put((byte) 0XC9, new OperationStructure((byte) 0XC9, 1, "RET", "", "", "PC<-(SP); SP<-SP+2"));
		opcodeMap.put((byte) 0XCA, new OperationStructure((byte) 0XCA, 3, "JZ", "addr", "", "if Z,PC<-addr"));
		opcodeMap.put((byte) 0XCB, new OperationStructure((byte) 0XCB, 1, "NONE", "", "", ""));
		opcodeMap.put((byte) 0XCC, new OperationStructure((byte) 0XCC, 3, "CZ", "addr", "", "if Z,CALL addr"));
		opcodeMap.put((byte) 0XCD, new OperationStructure((byte) 0XCD, 3, "CALL", "addr", "", "CALL addr"));
		opcodeMap.put((byte) 0XCE, new OperationStructure((byte) 0XCE, 2, "ACI", "D8", "", "A<- A + data + cy"));
		opcodeMap.put((byte) 0XCF, new OperationStructure((byte) 0XCF, 1, "RST", "1", "", "CALL $8"));

		opcodeMap.put((byte) 0XD0, new OperationStructure((byte) 0XD0, 1, "RNC", "", "", "if NCY, ret"));
		opcodeMap.put((byte) 0XD1, new OperationStructure((byte) 0XD1, 1, "POP", "D", "", ""));
		opcodeMap.put((byte) 0XD2, new OperationStructure((byte) 0XD2, 3, "JNC", "addr", "", "if NCY,PC<-addr"));
		opcodeMap.put((byte) 0XD3, new OperationStructure((byte) 0XD3, 2, "OUT", "D8", "", "i/O")); // Special
		opcodeMap.put((byte) 0XD4, new OperationStructure((byte) 0XD4, 3, "CNC", "addr", "", "if NC,CALL addr"));
		opcodeMap.put((byte) 0XD5, new OperationStructure((byte) 0XD5, 1, "PUSH", "D", "", ""));
		opcodeMap.put((byte) 0XD6, new OperationStructure((byte) 0XD6, 2, "SUI", "D8", "", "A<-A - byte2"));
		opcodeMap.put((byte) 0XD7, new OperationStructure((byte) 0XD7, 1, "RST", "2", "", "CALL $10"));
		opcodeMap.put((byte) 0XD8, new OperationStructure((byte) 0XD8, 1, "RC", "", "", "if CY, ret"));
		opcodeMap.put((byte) 0XD9, new OperationStructure((byte) 0XD9, 1, "NONE", "", "", ""));
		opcodeMap.put((byte) 0XDA, new OperationStructure((byte) 0XDA, 3, "JC", "addr", "", "if CY,PC<-addr"));
		opcodeMap.put((byte) 0XDB, new OperationStructure((byte) 0XDB, 2, "IN", "D8", "", "i/O")); // Special
		opcodeMap.put((byte) 0XDC, new OperationStructure((byte) 0XDC, 3, "CC", "addr", "", "if CY,CALL addr"));
		opcodeMap.put((byte) 0XDD, new OperationStructure((byte) 0XDD, 1, "NONE", "", "", ""));
		opcodeMap.put((byte) 0XDE, new OperationStructure((byte) 0XDE, 2, "SBI", "D8", "", "A<- A - data - cy"));
		opcodeMap.put((byte) 0XDF, new OperationStructure((byte) 0XDF, 1, "RST", "3", "", "CALL $18"));

		opcodeMap.put((byte) 0XE0, new OperationStructure((byte) 0XE0, 1, "RPO", "", "", "if PO, ret"));
		opcodeMap.put((byte) 0XE1, new OperationStructure((byte) 0XE1, 1, "POP", "H", "", ""));
		opcodeMap.put((byte) 0XE2, new OperationStructure((byte) 0XE2, 3, "JPO", "addr", "", "if PO,PC<-addr"));
		opcodeMap.put((byte) 0XE3, new OperationStructure((byte) 0XE3, 1, "XTHL", "", "", "L<->(SP);H<->(SP+1)"));
		opcodeMap.put((byte) 0XE4, new OperationStructure((byte) 0XE4, 3, "CPO", "addr", "", "if PO,CALL addr"));
		opcodeMap.put((byte) 0XE5, new OperationStructure((byte) 0XE5, 1, "PUSH", "H", "", ""));
		opcodeMap.put((byte) 0XE6, new OperationStructure((byte) 0XE6, 2, "ANI", "D8", "", "A<-A & byte2"));
		opcodeMap.put((byte) 0XE7, new OperationStructure((byte) 0XE7, 1, "RST", "4", "", "CALL $20"));
		opcodeMap.put((byte) 0XE8, new OperationStructure((byte) 0XE8, 1, "RPE", "", "", "if PE, ret"));
		opcodeMap.put((byte) 0XE9, new OperationStructure((byte) 0XE9, 1, "PCHL", "", "", "PC.hi<-H;PC.lo<-L"));
		opcodeMap.put((byte) 0XEA, new OperationStructure((byte) 0XEA, 3, "JPE", "addr", "", "if PE,PC<-addr"));
		opcodeMap.put((byte) 0XEB, new OperationStructure((byte) 0XEB, 1, "XCHG", "", "", "H<->D;L<->E")); // Special
		opcodeMap.put((byte) 0XEC, new OperationStructure((byte) 0XEC, 3, "CPE", "addr", "", "if PE,CALL addr"));
		opcodeMap.put((byte) 0XED, new OperationStructure((byte) 0XED, 1, "NONE", "", "", ""));
		opcodeMap.put((byte) 0XEE, new OperationStructure((byte) 0XEE, 2, "XRI", "D8", "", "A<- A ^ data"));
		opcodeMap.put((byte) 0XEF, new OperationStructure((byte) 0XEF, 1, "RST", "5", "", "CALL $28"));

		opcodeMap.put((byte) 0XF0, new OperationStructure((byte) 0XF0, 1, "RP", "", "", "if P, ret"));
		opcodeMap.put((byte) 0XF1, new OperationStructure((byte) 0XF1, 1, "POP", "PSW", "",
				"flags<-(SP);A<-(SP+1); SP<-SP+2"));
		opcodeMap.put((byte) 0XF2, new OperationStructure((byte) 0XF2, 3, "JP", "addr", "", "if P,PC<-addr"));
		opcodeMap.put((byte) 0XF3, new OperationStructure((byte) 0XF3, 1, "DI", "", "", "")); // Special
		opcodeMap.put((byte) 0XF4, new OperationStructure((byte) 0XF4, 3, "CP", "addr", "", "if P,CALL addr"));
		opcodeMap.put((byte) 0XF5, new OperationStructure((byte) 0XF5, 1, "PUSH", "PSW", "", ""));
		opcodeMap.put((byte) 0XF6, new OperationStructure((byte) 0XF6, 2, "ORI", "D8", "", "A<-A | byte2"));
		opcodeMap.put((byte) 0XF7, new OperationStructure((byte) 0XF7, 1, "RST", "6", "", "CALL $30"));
		opcodeMap.put((byte) 0XF8, new OperationStructure((byte) 0XF8, 1, "RM", "", "", "if M, ret"));
		opcodeMap.put((byte) 0XF9, new OperationStructure((byte) 0XF9, 1, "SPHL", "", "", "SP=HL"));
		opcodeMap.put((byte) 0XFA, new OperationStructure((byte) 0XFA, 3, "JM", "addr", "", "if M,PC<-addr"));
		opcodeMap.put((byte) 0XFB, new OperationStructure((byte) 0XFB, 1, "EI", "", "", "")); // Special
		opcodeMap.put((byte) 0XFC, new OperationStructure((byte) 0XFC, 3, "CM", "addr", "", "if M,CALL addr"));
		opcodeMap.put((byte) 0XFD, new OperationStructure((byte) 0XFD, 1, "NONE", "", "", ""));
		opcodeMap.put((byte) 0XFE, new OperationStructure((byte) 0XFE, 2, "CPI", "D8", "", "A - data"));
		opcodeMap.put((byte) 0XFF, new OperationStructure((byte) 0XFF, 1, "RST", "7", "", "CALL $38"));

	}

	class OperationStructure {
		private byte opCode;
		private int size;
		private String instruction;
		private String source;
		private String destination;
		private String function;

		OperationStructure(byte opCode, int size,
				String instruction, String destination, String source, String function) {
			this.opCode = opCode;
			this.size = size;
			this.instruction = instruction;
			this.source = source;
			this.destination = destination;
			this.function = function;
		}// CONSTRUCTOR

		public byte getOpCode() {
			return this.opCode;
		}// getOpCode

		public int getSize() {
			return this.size;
		}// getSize

		public String getInstruction() {
			return this.instruction;
		}// getInstruction

		public String getSource() {
			return this.source;
		}// getSource

		public String getDestination() {
			return this.destination;
		}// getDestination

		public String getFunction() {
			return this.function;
		}// getFunction

		public String getAssemblerCode() {
			return String.format("%02X  %-4s %s%s\t%-15s%n",
					getOpCode(), getInstruction(), getDestination(), getSource(), getFunction());
		}

		public String getAssemblerCode(byte plusOne) {
			String ans;
			if (getDestination().equals("D8")) {
				ans = String.format("%02X  %-4s %02X\t%-15s%n",
						getOpCode(), getInstruction(), plusOne, getFunction());
			} else {
				ans = String.format("%02X  %-4s %s, %02X\t%-15s%n",
						getOpCode(), getInstruction(), getDestination(), plusOne, getFunction());
			}
			return ans;
		}

		public String getAssemblerCode(byte plusOne, byte plusTwo) {
			String ans;
			if (getDestination().equals("addr")) {
				ans = String.format("%02X  %-4s %02X%02X\t%-15s%n",
						getOpCode(), getInstruction(), plusTwo, plusOne, getFunction());
			} else {
				ans = String.format("%02X  %-4s %s,%02X%02X %-15s%n",
						getOpCode(), getInstruction(), getDestination(), plusTwo, plusOne, getFunction());
			}
			return ans;
		}

	}// class operationStructure

	private void appInit0() {

		LMI lmi = new LMI(64 * 1024);
		lmi.doIt();
		memoryImage = lmi.getMemoryImage();
		lmi = null;
		pmm = new byte[64 * 1024];

		Set<Integer> locations = memoryImage.keySet();
		for (Integer location : locations) {
			pmm[location] = memoryImage.get(location);
		}//
		locations = null;
		memoryImage = null;
		lineBuilder = new StringBuilder();

		Document doc = txtTarget1.getDocument();
		doc.addDocumentListener(this);
		txtTarget1.setDocument(doc);
		txtTarget2.setDocument(doc);
		System.out.println("one");

		System.out.println("two");

		try {
			doc.insertString(0, getDisplayForLine(63872), null);
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}// appInit

	/**
	 * Create the application. ..2..#N #~......#
	 */
	public DocTest() {
		initialize();
		appInit();
	}

	private String getDisplayForLine(int firstLocation) {
		int startLocation = firstLocation & 0XFFF0;
		lineBuilder.setLength(0);

		char[] printables = new char[16];
		for (int i = 0; i < 16; i++) {
			printables[i] = ((pmm[startLocation + i] >= 0X20) && pmm[startLocation + i] <= 0X7F) ?
					(char) pmm[startLocation + i] : '.';
		}//

		lineBuilder.append(String.format(
				"%04X: %02X %02X %02X %02X %02X %02X %02X %02X  %02X %02X %02X %02X %02X %02X %02X %02X ",
				startLocation,
				pmm[startLocation++], pmm[startLocation++], pmm[startLocation++], pmm[startLocation++],
				pmm[startLocation++], pmm[startLocation++], pmm[startLocation++], pmm[startLocation++],
				pmm[startLocation++], pmm[startLocation++], pmm[startLocation++], pmm[startLocation++],
				pmm[startLocation++], pmm[startLocation++], pmm[startLocation++], pmm[startLocation++]));
		lineBuilder.append(printables);
		lineBuilder.append("\n");
		return lineBuilder.toString();
	}

	private String getMemoryDIsplayImage() {
		byte[] source = new byte[] { (byte) 0X38, (byte) 0X00, (byte) 0X00, (byte) 0X43,
				(byte) 0X50, (byte) 0X2F, (byte) 0X4D, (byte) 0X20,
				(byte) 0X32, (byte) 0X2E, (byte) 0X32, (byte) 0X2E,
				(byte) 0X30, (byte) 0X30, (byte) 0X20, (byte) 0X30 };
		StringBuilder sb = new StringBuilder();

		sb.append(String.format("%02X %02X %02X %02X %02X %02X %02X %02X  %02X %02X %02X %02X %02X %02X %02X %02X ",
				source[0], source[1], source[2], source[3], source[4], source[5], source[6], source[7],
				source[8], source[9], source[10], source[11], source[12], source[13], source[14], source[15]));

		char[] printables = new char[16];
		for (int i = 0; i < 16; i++) {
			printables[i] = ((source[i] >= 0X20) && source[i] <= 0X7F) ? (char) source[i] : '.';
		}//
		sb.append(printables);
		sb.append("\n");

		sb.toString();
		return sb.toString();
	}// getMemoryDIsplayImage

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 944, 783);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 400, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 70, 590, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Panel", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		frame.getContentPane().add(panel, gbc_panel);

		spinner = new JSpinner();
		panel.add(spinner);

		btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// String s = "FB2B";
				// Integer t = Integer.parseUnsignedInt(s,16);
				// System.out.printf("X = %s, %04X %n",s,t);
				doNewButton();
			}
		});

		popMnuTest1 = new JPopupMenu();
		popMnuTest1.setLabel("Test1");
		addPopup(btnNewButton, popMnuTest1);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 0;
		frame.getContentPane().add(btnNewButton, gbc_btnNewButton);

		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);

		txtTarget1 = new JTextPane();

		txtTarget1.setFont(new Font("Courier New", Font.PLAIN, 14));
		scrollPane.setViewportView(txtTarget1);

		JLabel lblTestOfDocument = new JLabel("Test of Document");
		lblTestOfDocument.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblTestOfDocument);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 0);
		gbc_tabbedPane.gridx = 1;
		gbc_tabbedPane.gridy = 1;
		frame.getContentPane().add(tabbedPane, gbc_tabbedPane);

		panelTree = new JPanel();
		tabbedPane.addTab("Tree", null, panelTree, null);
		GridBagLayout gbl_panelTree = new GridBagLayout();
		gbl_panelTree.columnWidths = new int[] { 0, 0 };
		gbl_panelTree.rowHeights = new int[] { 0, 0 };
		gbl_panelTree.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelTree.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelTree.setLayout(gbl_panelTree);

		scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 0;
		gbc_scrollPane_2.gridy = 0;
		panelTree.add(scrollPane_2, gbc_scrollPane_2);

		tree = new JTree();
		tree.setShowsRootHandles(true);
		scrollPane_2.setViewportView(tree);

		panelRaw = new JPanel();
		tabbedPane.addTab("Raw", null, panelRaw, null);
		GridBagLayout gbl_panelRaw = new GridBagLayout();
		gbl_panelRaw.columnWidths = new int[] { 0, 0 };
		gbl_panelRaw.rowHeights = new int[] { 590, 0 };
		gbl_panelRaw.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelRaw.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panelRaw.setLayout(gbl_panelRaw);

		scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panelRaw.add(scrollPane_1, gbc_scrollPane_1);

		txtTarget2 = new JTextArea();
		scrollPane_1.setViewportView(txtTarget2);

		lblNewLabel = new JLabel("Testing for Styles");
		scrollPane_1.setColumnHeaderView(lblNewLabel);

		JButton btnOne = new JButton("One");
		btnOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				scrollPane.getVerticalScrollBar().setValue(0);
			}
		});
		GridBagConstraints gbc_btnOne = new GridBagConstraints();
		gbc_btnOne.insets = new Insets(0, 0, 0, 5);
		gbc_btnOne.gridx = 0;
		gbc_btnOne.gridy = 2;
		frame.getContentPane().add(btnOne, gbc_btnOne);

		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		mnuFile = new JMenu("File");
		menuBar.add(mnuFile);

		mnuFileAdd = new JMenuItem("Add");
		mnuFile.add(mnuFileAdd);

		mnuFileRemove = new JMenuItem("Remove");
		mnuFile.add(mnuFileRemove);

		chckbxmntmNewCheckItem = new JCheckBoxMenuItem("New check item");
		mnuFile.add(chckbxmntmNewCheckItem);
	}

	@Override
	public void changedUpdate(DocumentEvent de) {
		scrollPane.getVerticalScrollBar().setValue(0);

	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		System.out.printf("insertUpdate%n");

	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		System.out.printf("removeUpdate%n");

	}

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
}
