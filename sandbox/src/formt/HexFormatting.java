package formt;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicSpinnerUI;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import java.awt.Dimension;
import java.text.ParseException;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JSpinner;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//import javax.swing.JFormattedTextField$AbstractFormatter;

//import javax.swing.JFormattedTextField$AbstractFormatterFactory;

public class HexFormatting {

	private JFrame frame;
	// MaskFormatter hexFormat2, hexFormat4;
	HexFormatter hexFormatter4, hexFormatter2;

	// NumberFormatter numberHex;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HexFormatting window = new HexFormatting();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void appInit0() {
		hexFormatter4 = new HexFormatter();
		hexFormatter2 = new HexFormatter(2);
		// try {
		// hexFormat4 = new MaskFormatter("HHHH");
		// hexFormat2 = new MaskFormatter("HHHH");
		// hexFormat2.setPlaceholder(" ");
		// hexFormat4.setPlaceholder(" ");
		// } catch (ParseException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }// try

	}// appInit

	private void appInit() {

	}// appInit

	/**
	 * Create the application.
	 */
	public HexFormatting() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		appInit0();
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JFormattedTextField ftf1 = new JFormattedTextField(hexFormatter4);
		ftf1.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent arg0) {

				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						ftf1.selectAll();
					}// run
				});

			}// focusGained
		});

		ftf1.addPropertyChangeListener("value", new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
				System.out.printf("Text: %s New Value: %s,  OldValue: %s%n",
						((JTextComponent) arg0.getSource()).getText(), arg0.getNewValue(), arg0.getOldValue());

			}// propertyChange
		});
		ftf1.setInputVerifier(new RegisterLimitVerifier(0XFFFF));
		ftf1.setValue(0000);
		// ftf1.setText("0000");

		ftf1.setSize(new Dimension(60, 20));
		ftf1.setPreferredSize(new Dimension(60, 20));
		ftf1.setMinimumSize(new Dimension(60, 20));
		ftf1.setBounds(39, 55, 60, 20);
		frame.getContentPane().add(ftf1);

		JFormattedTextField ftf2 = new JFormattedTextField(hexFormatter2);

		ftf2.setInputVerifier(new RegisterLimitVerifier(0XFF));
		ftf2.setValue(0000);
		ftf2.setSize(new Dimension(60, 20));
		ftf2.setPreferredSize(new Dimension(60, 20));
		ftf2.setMinimumSize(new Dimension(60, 20));
		ftf2.setBounds(174, 55, 60, 20);
		frame.getContentPane().add(ftf2);

		JFormattedTextField ftf3 = new JFormattedTextField();
		ftf3.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				ftf3.selectAll();
			}
		});
		ftf3.setSize(new Dimension(60, 20));
		ftf3.setPreferredSize(new Dimension(60, 20));
		ftf3.setMinimumSize(new Dimension(60, 20));
		ftf3.setBounds(39, 134, 60, 20);
		frame.getContentPane().add(ftf3);
		
		JRadioButton rb1 = new JRadioButton("New radio button");
		rb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String state = rb1.isSelected()?"Selected":"not selected";
				System.out.printf("Radio button is %s %n", state);
			}
		});
		rb1.setBounds(152, 102, 109, 23);
		frame.getContentPane().add(rb1);

	}// Initialize

	public class RegisterLimitVerifier extends InputVerifier {

		int Kbytes = 1024;
		private final Color INVALID_COLOR = Color.red;
		private final Color VALID_COLOR = Color.black;
		private int maxValue; // maximum value

		public RegisterLimitVerifier(int registerSize) {
			this.setMaxValue(registerSize); // Register Size is in bytes
		}// Constructor - MemoryLimitVerifier(memorySize)

		@Override
		public boolean verify(JComponent jc) {
			try {
				String text = ((JTextComponent) jc).getText();
				Integer val = Integer.valueOf(text, 16);
				if (val > maxValue) {
					jc.setForeground(INVALID_COLOR);
					return false;
				}// if
			} catch (Exception e) {
				jc.setForeground(INVALID_COLOR);
				return false;
			}// try - catch
			jc.setForeground(VALID_COLOR);
			return true;
		}// verify

		public int getMaxValue() {
			return maxValue;
		}// getMaxValue

		public void setMaxValue(int maxValue) {
			this.maxValue = maxValue;
		}// setMaxValue

	}// class MemoryLimitVerifier

	private static class HexFormatter extends DefaultFormatter {
		private static final long serialVersionUID = 1L;
		private String formatString;

		public HexFormatter(int numberOfDigits) {
			formatString = "%0" + numberOfDigits + "X";
		}// Constructor

		public HexFormatter() {
			this(4);
		}// Constructor

		public Object stringToValue(String text) throws ParseException {

			String workingText = text.length() > 4 ? text.substring(0, 4) : text;
			try {
				return Integer.valueOf(workingText, 16);
			} catch (NumberFormatException nfe) {
				throw new ParseException(text, 0);
			}// try
		}// stringToValue

		public String valueToString(Object value) throws ParseException {
			return String.format(formatString, value);
		}// valueToString
	}// class HexFormatter
}// class
