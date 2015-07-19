package box1;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.border.EmptyBorder;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JSplitPane;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Insets;

import javax.swing.JFormattedTextField;

import java.awt.Font;
import java.awt.Component;
import java.text.ParseException;

import javax.swing.JTextPane;
import javax.swing.Box;

//import box1.HexFTF.HexFormatter;

public class ShowCoreMemoryX extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JSpinner spinnerEdit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ShowCoreMemoryX dialog = new ShowCoreMemoryX();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void initApp(){
		makeSpinnerHexSmart(spinnerEdit);
	}
	
	private void makeSpinnerHexSmart(JSpinner spinner){
		JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor ) spinner.getEditor();
		JFormattedTextField spinnerFTF = editor.getTextField();
		spinnerFTF.setFormatterFactory(new MyFormatterFactory());
	
	}

	/**
	 * Create the dialog.
	 */
	public ShowCoreMemoryX() {
		setBounds(100, 100, 860, 745);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel panelHead = new JPanel();
			getContentPane().add(panelHead, BorderLayout.NORTH);
			panelHead.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		}
		contentPanel.setBackground(Color.GREEN);
		contentPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] {30, 700, 30, 0};
		gbl_contentPanel.rowHeights = new int[] {30, 550, 50, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 1.0, 1.0};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JSplitPane splitPane = new JSplitPane();
			splitPane.setBorder(new LineBorder(Color.BLUE, 2));
			splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPane.setOneTouchExpandable(true);
			splitPane.setDividerSize(15);
			GridBagConstraints gbc_splitPane = new GridBagConstraints();
			gbc_splitPane.anchor = GridBagConstraints.SOUTH;
			gbc_splitPane.fill = GridBagConstraints.BOTH;
			gbc_splitPane.insets = new Insets(0, 0, 5, 5);
			gbc_splitPane.gridx = 1;
			gbc_splitPane.gridy = 1;
			contentPanel.add(splitPane, gbc_splitPane);
			{
				JPanel panelBottom = new JPanel();
				panelBottom.setBackground(Color.LIGHT_GRAY);
				splitPane.setRightComponent(panelBottom);
				panelBottom.setLayout(null);
				{
					JSpinner spinnerBottom = new JSpinner();
					spinnerBottom.setBounds(334, 2, 100, 30);
					spinnerBottom.setAlignmentY(Component.TOP_ALIGNMENT);
					spinnerBottom.setMaximumSize(new Dimension(90, 20));
					spinnerBottom.setMinimumSize(new Dimension(90, 20));
					spinnerBottom.setFont(new Font("Courier New", Font.PLAIN, 20));
					panelBottom.add(spinnerBottom);
				}
				{
					JScrollPane scrollPane = new JScrollPane();
					scrollPane.setBounds(2, 45, 600, 22);
					scrollPane.setMinimumSize(new Dimension(200, 400));
					scrollPane.setAlignmentY(Component.BOTTOM_ALIGNMENT);
					panelBottom.add(scrollPane);
					{
						JTextPane textPane = new JTextPane();
						textPane.setMinimumSize(new Dimension(300, 300));
						scrollPane.setViewportView(textPane);
					}
				}
			}
			{
				JPanel panelTop = new JPanel();
				panelTop.setBackground(Color.DARK_GRAY);
				splitPane.setLeftComponent(panelTop);
				panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.X_AXIS));
			}
			splitPane.setDividerLocation(100);
		}
		{
			JPanel panelEdit = new JPanel();
			GridBagConstraints gbc_panelEdit = new GridBagConstraints();
			gbc_panelEdit.insets = new Insets(0, 0, 5, 5);
			gbc_panelEdit.fill = GridBagConstraints.BOTH;
			gbc_panelEdit.gridx = 1;
			gbc_panelEdit.gridy = 2;
			contentPanel.add(panelEdit, gbc_panelEdit);
			{
				spinnerEdit = new JSpinner();
				spinnerEdit.setMinimumSize(new Dimension(90, 20));
				spinnerEdit.setModel(new SpinnerNumberModel(0, 0, 65535, 1));
				spinnerEdit.setFont(new Font("Courier New", Font.PLAIN, 20));
				panelEdit.add(spinnerEdit);
			}
			
			JLabel label = new JLabel("00 01 02 03 04 05 06 07  08 09 0A 0B 0C 0D 0E 0F");
			label.setFont(new Font("Courier New", Font.PLAIN, 14));
			panelEdit.add(label);
		}
		{
			JPanel Commit = new JPanel();
			GridBagConstraints gbc_Commit = new GridBagConstraints();
			gbc_Commit.insets = new Insets(0, 0, 0, 5);
			gbc_Commit.fill = GridBagConstraints.BOTH;
			gbc_Commit.gridx = 1;
			gbc_Commit.gridy = 3;
			contentPanel.add(Commit, gbc_Commit);
			{
				JButton button = new JButton("Commit changes");
				button.setForeground(Color.BLACK);
				button.setEnabled(false);
				button.setActionCommand("Commit");
				Commit.add(button);
			}
			{
				Component horizontalStrut = Box.createHorizontalStrut(40);
				Commit.add(horizontalStrut);
			}
			{
				JButton button = new JButton("Cancel");
				button.setActionCommand("Cancel");
				Commit.add(button);
			}
			{
				JButton button = new JButton("OK");
				button.setActionCommand("OK");
				Commit.add(button);
			}
		}
		
		{
			JPanel paneBottom = new JPanel();
			getContentPane().add(paneBottom, BorderLayout.SOUTH);
			paneBottom.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
			{
				JButton okButton = new JButton("OK");
				paneBottom.add(okButton);
				okButton.setHorizontalAlignment(SwingConstants.RIGHT);
				okButton.setActionCommand("OK");
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		
		initApp();
	}
	
	
	// ----------------------------------------------------------------------------------------------------------

	private static class HexFormatter extends DefaultFormatter {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Object stringToValue(String text) throws ParseException {
			try {
				return Integer.valueOf(text, 16);
			} catch (NumberFormatException nfe) {
				throw new ParseException(text, 0);
			}
		}

		public String valueToString(Object value) throws ParseException {
			return Integer.toHexString(
					((Integer) value).intValue()).toUpperCase();
		}
	}

	private static class MyFormatterFactory extends DefaultFormatterFactory {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public AbstractFormatter getDefaultFormatter() {
			return new HexFormatter();
		}
	}
}
