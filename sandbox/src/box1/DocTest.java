package box1;



import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JScrollPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import java.awt.Font;

public class DocTest implements DocumentListener {

	private JFrame frame;
	private JTextArea txtTarget1;
	private Document doc;

	private String rawSource;
	private byte[] byteSource;
	private JTextArea txtTarget2;
	private HashMap<Integer,Byte> memoryImage;
	private StringBuilder lineBuilder;
	byte[] pmm ;

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
	}

	private void appInit() {
		
		LMI lmi = new LMI(64* 1024);
		lmi.doIt();
		memoryImage = lmi.getMemoryImage();
		lmi = null;
		pmm = new byte[64 * 1024];
		
		Set<Integer> locations = memoryImage.keySet();
		for(Integer location:locations){
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
	
	private String getDisplayForLine(int firstLocation){
		int startLocation = firstLocation & 0XFFF0;
		lineBuilder.setLength(0);
		
		char[] printables = new char[16];
		for (int i = 0; i < 16; i++){
			printables[i] = ((pmm[startLocation + i] >= 0X20) && pmm[startLocation + i] <= 0X7F)?
					(char) pmm[startLocation + i]: '.';
		}//

		
		lineBuilder.append(String.format("%04X: %02X %02X %02X %02X %02X %02X %02X %02X  %02X %02X %02X %02X %02X %02X %02X %02X ",
				startLocation,
				pmm[startLocation++],pmm[startLocation++],pmm[startLocation++],pmm[startLocation++],
				pmm[startLocation++],pmm[startLocation++],pmm[startLocation++],pmm[startLocation++],
				pmm[startLocation++],pmm[startLocation++],pmm[startLocation++],pmm[startLocation++],
				pmm[startLocation++],pmm[startLocation++],pmm[startLocation++],pmm[startLocation++]));
		lineBuilder.append(printables);
		lineBuilder.append("\n");
	return lineBuilder.toString();	
	}

	private String getMemoryDIsplayImage() {
		byte[] source = new byte[]{(byte)0X38, (byte)0X00, (byte)0X00, (byte)0X43,
				(byte)0X50, (byte)0X2F, (byte)0X4D, (byte)0X20,
				(byte)0X32, (byte)0X2E, (byte)0X32, (byte)0X2E,
				(byte)0X30, (byte)0X30, (byte)0X20, (byte)0X30};
		StringBuilder sb = new StringBuilder();
		
		sb.append(String.format("%02X %02X %02X %02X %02X %02X %02X %02X  %02X %02X %02X %02X %02X %02X %02X %02X ",
				source[0],source[1],source[2],source[3],source[4],source[5],source[6],source[7],
				source[8],source[9],source[10],source[11],source[12],source[13],source[14],source[15]));
		
		char[] printables = new char[16];
			for (int i = 0; i < 16; i++){
				printables[i] = ((source[i] >= 0X20) && source[i] <= 0X7F)? (char) source[i]: '.';
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
		frame.setBounds(100, 100, 944, 746);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 484, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 357, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 1;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);

		txtTarget1 = new JTextArea();
		
		txtTarget1.setFont(new Font("Courier New", Font.PLAIN, 14));
		scrollPane.setViewportView(txtTarget1);

		JLabel lblTestOfDocument = new JLabel("Test of Document");
		lblTestOfDocument.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lblTestOfDocument);

		txtTarget2 = new JTextArea();
		GridBagConstraints gbc_txtTarget2 = new GridBagConstraints();
		gbc_txtTarget2.insets = new Insets(0, 0, 5, 0);
		gbc_txtTarget2.fill = GridBagConstraints.BOTH;
		gbc_txtTarget2.gridx = 2;
		gbc_txtTarget2.gridy = 1;
		frame.getContentPane().add(txtTarget2, gbc_txtTarget2);

		JButton btnOne = new JButton("One");
		GridBagConstraints gbc_btnOne = new GridBagConstraints();
		gbc_btnOne.insets = new Insets(0, 0, 5, 5);
		gbc_btnOne.gridx = 1;
		gbc_btnOne.gridy = 2;
		frame.getContentPane().add(btnOne, gbc_btnOne);
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
		System.out.printf("changeUpdate%n");
		
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		System.out.printf("insertUpdate%n");
	
		
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		System.out.printf("removeUpdate%n");

		
	}


}
