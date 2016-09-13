package box1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import java.awt.GridBagLayout;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class TwoButtonTest implements ActionListener {

	private JFrame frame;
	Color colorOfTheDay;
	boolean runState;
	private JLabel lbl1;
	private JProgressBar progressBar;
	private JTextArea textArea;

	private void doStart() {
		int counter = 0;
		while (runState) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//try
			
			colorOfTheDay = colorOfTheDay.equals(Color.BLACK) ? Color.RED : Color.BLACK;
			lbl1.setForeground(colorOfTheDay);
			lbl1.setText(String.format("Count: %3d", counter++));
			
			if (counter > 6) {
				runState = false;
			}//if
		}//while

	}//doStart

	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (ae.getActionCommand()) {
		case "btnStart":
			
//			runState = true;
//			doStart();
			break;
		case "btnStop":
			runState = false;
			break;
		}

	}// actionPerformed

	private void appInit() {
		colorOfTheDay = Color.BLACK;
		runState = false;
	}// appInit
		// -------------------------------------------------------------------------------

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TwoButtonTest window = new TwoButtonTest();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TwoButtonTest() {
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

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(this);
		
		progressBar = new JProgressBar(0,100);
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.insets = new Insets(0, 0, 5, 5);
		gbc_progressBar.gridx = 3;
		gbc_progressBar.gridy = 0;
		frame.getContentPane().add(progressBar, gbc_progressBar);
		btnStart.setActionCommand("btnStart");
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.insets = new Insets(0, 0, 5, 5);
		gbc_btnStart.anchor = GridBagConstraints.NORTH;
		gbc_btnStart.gridx = 1;
		gbc_btnStart.gridy = 1;
		frame.getContentPane().add(btnStart, gbc_btnStart);

		lbl1 = new JLabel("Test label");
		GridBagConstraints gbc_lbl1 = new GridBagConstraints();
		gbc_lbl1.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lbl1.insets = new Insets(0, 0, 5, 5);
		gbc_lbl1.gridx = 3;
		gbc_lbl1.gridy = 1;
		frame.getContentPane().add(lbl1, gbc_lbl1);

		JButton btnEnd = new JButton("End");
		btnEnd.addActionListener(this);
		btnEnd.setActionCommand("btnEnd");
		GridBagConstraints gbc_btnEnd = new GridBagConstraints();
		gbc_btnEnd.insets = new Insets(0, 0, 5, 0);
		gbc_btnEnd.fill = GridBagConstraints.BOTH;
		gbc_btnEnd.gridx = 5;
		gbc_btnEnd.gridy = 1;
		frame.getContentPane().add(btnEnd, gbc_btnEnd);
		
		textArea = new JTextArea();
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.insets = new Insets(0, 0, 0, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 3;
		gbc_textArea.gridy = 3;
		frame.getContentPane().add(textArea, gbc_textArea);
	}

}
