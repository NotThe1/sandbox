package box1;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.JTextComponent;

import java.awt.BorderLayout;
import java.text.ParseException;

import javax.swing.SpinnerNumberModel;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import myComponents.Hex64KSpinner;
import java.awt.GridBagConstraints;
import myComponents.Hex64KSpinner16;
import java.awt.Insets;

public class HexFTF {

	private JFrame frame;
	private Hex64KSpinner hex64KSpinner;
	private Hex64KSpinner16 hex64KSpinner16;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HexFTF window = new HexFTF();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void appInit() {
//		JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
//		JFormattedTextField ftf = editor.getTextField();
//		ftf.setFormatterFactory(new MyFormatterFactory());
//		//((SpinnerNumberModel) spinner.getModel()).getMaximum();
//		((SpinnerNumberModel) spinner.getModel()).setMaximum(12);
	}

//	// ----------------------------------------------------------------------------------------------------------
//
//	private static class HexFormatter extends DefaultFormatter {
//		public Object stringToValue(String text) throws ParseException {
//			try {
//				return Integer.valueOf(text, 16);
//			} catch (NumberFormatException nfe) {
//				throw new ParseException(text, 0);
//			}
//		}
//
//		// public String valueToString(Object value) throws ParseException {
//		// return Integer.toHexString(
//		// ((Integer) value).intValue()).toUpperCase();
//		// }
//		public String valueToString(Object value) throws ParseException {
//			return String.format("%04X", value);
//		}
//	}
//
//	private static class MyFormatterFactory extends DefaultFormatterFactory {
//		public AbstractFormatter getDefaultFormatter() {
//			return new HexFormatter();
//		}
//	}
//
//	// ----------------------------------------------------------------------------------------------------------

	class MemoryLimitVerifier extends InputVerifier {
		private int Kbytes = 1024;
		private int maxValue;
		private final Color INVALID_COLOR = Color.RED;
		private final Color VALID_COLOR = Color.BLACK;

		public MemoryLimitVerifier(int memorySize) {
			this.maxValue = memorySize * Kbytes;
		}// Constructor

		@Override
		public boolean verify(JComponent jc) {
			boolean verify = true;
			Color thisColor = VALID_COLOR; // assume all is well
			try {
				String text = ((JTextComponent) jc).getText();
				Integer value = Integer.valueOf(text, 16);
				if (value > maxValue) {
					thisColor = INVALID_COLOR;
					verify = false;
				}// if
			} catch (Exception e) {
				thisColor = INVALID_COLOR;
				verify = false;
			}// try
			jc.setForeground(thisColor);
			return verify;
		}// verify

		public int getMaxValue() {
			return maxValue;
		}

	}

	// ----------------------------------------------------------------------------------------------------------

	/**
	 * Create the application.
	 */
	public HexFTF() {
		initialize();
		appInit();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 26));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {139, 143, 60, 60, 0};
		gridBagLayout.rowHeights = new int[] {0, 0, 40, 40, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		hex64KSpinner = new Hex64KSpinner();
		GridBagConstraints gbc_hex64KSpinner = new GridBagConstraints();
		gbc_hex64KSpinner.insets = new Insets(0, 0, 0, 5);
		gbc_hex64KSpinner.gridx = 0;
		gbc_hex64KSpinner.gridy = 1;
		frame.getContentPane().add(hex64KSpinner, gbc_hex64KSpinner);
		
		hex64KSpinner16 = new Hex64KSpinner16();
		GridBagConstraints gbc_hex64KSpinner16 = new GridBagConstraints();
		gbc_hex64KSpinner16.gridx = 1;
		gbc_hex64KSpinner16.gridy = 1;
		frame.getContentPane().add(hex64KSpinner16, gbc_hex64KSpinner16);
		frame.setBounds(100, 100, 901, 751);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
