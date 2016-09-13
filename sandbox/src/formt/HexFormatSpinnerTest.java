package formt;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.plaf.basic.BasicSpinnerUI;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;

import sandbox.HexSpinner;


public class HexFormatSpinnerTest {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HexFormatSpinnerTest window = new HexFormatSpinnerTest();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}// main

	private void modifySpinner(JSpinner spinner) {
		DefaultEditor editor = (DefaultEditor) spinner.getEditor();
		JFormattedTextField ftf = editor.getTextField();
		ftf.setFormatterFactory(new MyFormatterFactory());

		hideSpinnerArrow(spinner);
	}

	public void hideSpinnerArrow(JSpinner spinner) {
		Dimension d = spinner.getPreferredSize();
		d.width = 30;
		spinner.setUI(new BasicSpinnerUI() {
			protected Component createNextButton() {
				return null;
			}

			protected Component createPreviousButton() {
				return null;
			}
		});
		spinner.setPreferredSize(d);
	}

	private void appInit0() {

	}// appInit0

	private void appInit1() {

	}// appInit1

	/**
	 * Create the application.
	 */
	public HexFormatSpinnerTest() {
		appInit0();
		initialize();
		appInit1();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0, null, 65535, 1));
		// spinner.setModel(new SpinnerNumberModel(0,0,0XFFFF,1));
		modifySpinner(spinner);

		spinner.setBounds(39, 33, 61, 20);
		frame.getContentPane().add(spinner);

		JFormattedTextField formattedTextField = new JFormattedTextField();
		formattedTextField.setBounds(156, 28, 200, 50);
		frame.getContentPane().add(formattedTextField);
		
		JSpinner spinner2 = new HexSpinner();
		hideSpinnerArrow(spinner2);
		SpinnerNumberModel smn = (SpinnerNumberModel) spinner2.getModel();
		smn.setMaximum(0XFF);
		spinner2.setBounds(39, 113, 61, 20);
		frame.getContentPane().add(spinner2);
	}// initialize
		// ----------------------------------------------------

	public class MyFormatterFactory extends DefaultFormatterFactory {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public AbstractFormatter getDefaultFormatter() {
			return new HexFormatter();
		}// AbstractFormatter

	}// class MyFormatterFactory

	public static class HexFormatter extends DefaultFormatter {
		private static final long serialVersionUID = 1L;

		public Object stringToValue(String text) throws ParseException {
			try {
				return Integer.valueOf(text, 16);
			} catch (NumberFormatException nfe) {
				throw new ParseException(text, 0);
			}// try
		}// stringToValue

		public String valueToString(Object value) throws ParseException {
			return String.format("%04X", value);
		}// valueToString
	}// class HexFormatter
}// class HexFormatSpinnerTest
