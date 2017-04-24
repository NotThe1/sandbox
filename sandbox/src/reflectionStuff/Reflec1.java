package reflectionStuff;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.util.prefs.Preferences;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class Reflec1 {

	private JFrame frmReflec;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JTextField txtAbcdef;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Reflec1 window = new Reflec1();
					window.frmReflec.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}// main

	private void appClose() {
		Preferences myPrefs = Preferences.userNodeForPackage(Reflec1.class).node(this.getClass().getSimpleName());
		Dimension dim = frmReflec.getSize();
		myPrefs.putInt("Height", dim.height);
		myPrefs.putInt("Width", dim.width);
		Point point = frmReflec.getLocation();
		myPrefs.putInt("LocX", point.x);
		myPrefs.putInt("LocY", point.y);
		myPrefs = null;
	}

//	@SuppressWarnings("unchecked")
	private void appInit() {
		Preferences myPrefs = Preferences.userNodeForPackage(Reflec1.class).node(this.getClass().getSimpleName());
		frmReflec.setSize(435,512);
		frmReflec.setLocation(myPrefs.getInt("LocX", 100), myPrefs.getInt("LocY", 100));
		myPrefs = null;
	}//appInit

	// watchButtons = new WatchButtons();

	// window.addObserver(watchButtons);
	/**
	 * Create the application.
	 */
	public Reflec1() {
		initialize();
		appInit();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmReflec = new JFrame();
//		frmReflec.setIconImage(Toolkit.getDefaultToolkit().getImage(Reflec1.class.getResource("/reflectionStuff/images/Regex.jpg")));
		frmReflec.setTitle("Reflec1");
		frmReflec.setBounds(100, 100, 450, 300);
		frmReflec.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmReflec.getContentPane().setLayout(gridBagLayout);
		btnNewButton = new JButton("0123456789 ABCDEF");
		btnNewButton.setPreferredSize(new Dimension(400, 60));
		
		Font fontDigital7 = null;
//		URL fontUrl = Reflec1.class.getResource("/reflectionStuff/digital display tfb.ttf");
//		URL fontUrl = Reflec1.class.getResource("/reflectionStuff/DS-DIGI.ttf");
		URL fontUrl = Reflec1.class.getResource("/reflectionStuff/Digit.ttf");
//		URL fontUrl = Reflec1.class.getResource("/reflectionStuff/ufonts.com_digital-7.ttf");
		try {
			 fontDigital7 = Font.createFont(Font.TRUETYPE_FONT,fontUrl.openStream());
			 fontDigital7 = fontDigital7.deriveFont(Font.BOLD,45);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(fontDigital7);
//		g.setFont(font);		
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//try
		btnNewButton.setFont(fontDigital7);
		
		
		
//		btnNewButton.setFont(new Font("Courier New", Font.PLAIN, 16));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 1;
		frmReflec.getContentPane().add(btnNewButton, gbc_btnNewButton);
		
		btnNewButton_1 = new JButton("New button");
		Icon xIcon = new ImageIcon(Reflec1.class.getResource("/reflectionStuff/images/Button-Turn-Off-icon.png"));
		
		btnNewButton_1.setIcon(xIcon);
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 3;
		frmReflec.getContentPane().add(btnNewButton_1, gbc_btnNewButton_1);
		
		txtAbcdef = new JTextField();
		txtAbcdef.setForeground(new Color(220, 20, 60));
		txtAbcdef.setHorizontalAlignment(SwingConstants.CENTER);
		txtAbcdef.setPreferredSize(new Dimension(400, 60));
		txtAbcdef.setText("0123456789 ABCDEF");
		txtAbcdef.setFont(fontDigital7);
		GridBagConstraints gbc_txtAbcdef = new GridBagConstraints();
		gbc_txtAbcdef.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAbcdef.gridx = 0;
		gbc_txtAbcdef.gridy = 5;
		frmReflec.getContentPane().add(txtAbcdef, gbc_txtAbcdef);
		txtAbcdef.setColumns(10);
		
		JMenuBar menuBar = new JMenuBar();
		frmReflec.setJMenuBar(menuBar);
		frmReflec.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				appClose();
			}
		});
	}

}//class
