package robot;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class MT {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MT window = new MT();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void doIt() {
		JFileChooser fc = new JFileChooser("C:\\Temp");
		int keyO = KeyEvent.VK_O;
;		fc.setApproveButtonMnemonic(keyO);
		fc.setApproveButtonMnemonic('O');
		int ans = fc.showDialog(frame,"DoIt");
		System.out.printf("[MT.doIt] ans = %d%n", ans);
	}// doIt()

	/**
	 * Create the application.
	 */
	public MT() {
		initialize();
		doIt();
	}// Constructor

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 711, 601);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				// appClose();
			}// windowClosing

		});

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 153, 39, 39, 39, 0 };
		gridBagLayout.rowHeights = new int[] { 23, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JButton component1 = new JButton("1");
		GridBagConstraints gbc_component1 = new GridBagConstraints();
		gbc_component1.anchor = GridBagConstraints.NORTHWEST;
		gbc_component1.insets = new Insets(0, 0, 5, 5);
		gbc_component1.gridx = 1;
		gbc_component1.gridy = 0;
		frame.getContentPane().add(component1, gbc_component1);
		JButton component2 = new JButton("2");
		GridBagConstraints gbc_component2 = new GridBagConstraints();
		gbc_component2.anchor = GridBagConstraints.NORTHWEST;
		gbc_component2.insets = new Insets(0, 0, 5, 5);
		gbc_component2.gridx = 2;
		gbc_component2.gridy = 0;
		frame.getContentPane().add(component2, gbc_component2);
		JButton component3 = new JButton("3");
		GridBagConstraints gbc_component3 = new GridBagConstraints();
		gbc_component3.insets = new Insets(0, 0, 5, 0);
		gbc_component3.anchor = GridBagConstraints.NORTHWEST;
		gbc_component3.gridx = 3;
		gbc_component3.gridy = 0;
		frame.getContentPane().add(component3, gbc_component3);

		Component verticalStrut = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut = new GridBagConstraints();
		gbc_verticalStrut.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut.gridx = 0;
		gbc_verticalStrut.gridy = 1;
		frame.getContentPane().add(verticalStrut, gbc_verticalStrut);

		Component verticalStrut_1 = Box.createVerticalStrut(20);
		GridBagConstraints gbc_verticalStrut_1 = new GridBagConstraints();
		gbc_verticalStrut_1.insets = new Insets(0, 0, 5, 5);
		gbc_verticalStrut_1.gridx = 0;
		gbc_verticalStrut_1.gridy = 2;
		frame.getContentPane().add(verticalStrut_1, gbc_verticalStrut_1);

	}// initialize

}// class MT
