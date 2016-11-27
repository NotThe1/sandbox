package seekPanel;

import java.awt.Color;
//import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.EventListenerList;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class SeekPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	SpinnerNumberModel numberModel;
	int currentValue, priorValue;
	private JFormattedTextField txtValueDisplay;
	EventListenerList seekValueChangedListenerList;

	String decimalDisplayFormat = "%d";
	String hexDisplayFormat = "%X";
	boolean showDecimal = true;
	SeekDocument displayDoc;
	
	public void setNumberModel(SpinnerNumberModel numberModel) {
		this.numberModel = numberModel;
		int newValue = (int) numberModel.getValue();
		priorValue = newValue;
		currentValue = newValue;
		setNewValue(newValue);
	}// setNumberModel

	public SpinnerNumberModel getNumberModel() {
		return this.numberModel;
	}// getNumberModel

	public int getValue() {
		return currentValue;
	}// getValue

	public int getPriorValue() {
		return (int) numberModel.getPreviousValue();
	}// getPriorValue

	public void setValue(int newValue) {
		setNewValue(newValue);
		return;
	}// setValue

	public void setDecimalDisplay() {
		showDecimal = true;
		displayDoc.displayDecimal();
		displayValue();
		txtValueDisplay.setToolTipText("Display is Decimal");
	}// setDecimalDisplay

	public void setHexDisplay() {
		showDecimal = false;
		displayDoc.displayHex();
		displayValue();
		txtValueDisplay.setToolTipText("Display is Hex");
	}// setHexDisplay

	public boolean isDecimalDisplay() {
		return showDecimal;
	}// isDecimalDisplay

	// ---------------------------------------

	private void displayValue() {
		String displayFormat = showDecimal ? decimalDisplayFormat : hexDisplayFormat;
		currentValue = (int) numberModel.getValue();

		String stringValue = String.format(displayFormat, currentValue);
		txtValueDisplay.setText(stringValue);
		txtValueDisplay.repaint();
	}// showValue

	private void setNewValue(int newValue) {
		newValue = Math.min(newValue, (int) numberModel.getMaximum()); // upper
		newValue = Math.max(newValue, (int) numberModel.getMinimum()); // lower

		priorValue = (int) numberModel.getValue();
		currentValue = (newValue);
		numberModel.setValue(newValue);
		displayValue();
		if (priorValue != currentValue) {
			fireSeekValueChanged();
		} // if
	}// newValue

	private void stepValue(int direction) {
		int changeAmount = (int) numberModel.getStepSize() * direction;

		long newValue = currentValue;
		newValue += changeAmount;

		if (newValue > (int) numberModel.getMaximum()) {
			setNewValue((int) numberModel.getMaximum());
		} else if (newValue < (int) numberModel.getMinimum()) {
			setNewValue((int) numberModel.getMinimum());
		} else {
			setNewValue((int) newValue);
		} // if
	}//

	// -------------------------------------------------------

	public SeekPanel() {
		this(new SpinnerNumberModel(12, Integer.MIN_VALUE, Integer.MAX_VALUE, 1), true);
	}// Constructor

	public SeekPanel(boolean decimalDisplay) {
		this(new SpinnerNumberModel(12, Integer.MIN_VALUE, Integer.MAX_VALUE, 1), decimalDisplay);
	}// Constructor

	public SeekPanel(SpinnerNumberModel numberModel) {
		this(numberModel, true);
	}// Constructor

	public SeekPanel(SpinnerNumberModel numberModel, boolean decimalDisplay) {
		this.numberModel = numberModel;

		appInit0();
		Initialize();
		appInit();

		if (decimalDisplay) {
			setDecimalDisplay();
		} else {
			setHexDisplay();
		} // if
	}// Constructor

	public void appInit0() {
		displayDoc = new SeekDocument(true);
	}// appInit0

	public void appInit() {
		currentValue = (int) numberModel.getValue();
		txtValueDisplay.setDocument(displayDoc);
		txtValueDisplay.setPreferredSize(new Dimension(100, 23));
		seekValueChangedListenerList = new EventListenerList();
	}// appInit

	public void Initialize() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				if (mouseEvent.getClickCount() > 1) {
					if (showDecimal) {
						setHexDisplay();
					} else {
						setDecimalDisplay();
					} // if inner
				} // if click count
			}// mouseClicked
		});

		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 59, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JButton btnNewButton = new JButton("<<");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNewValue((int) numberModel.getMinimum());
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		add(btnNewButton, gbc_btnNewButton);

		JButton btnNewButton_1 = new JButton("<");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stepValue(DOWN);
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 0;
		add(btnNewButton_1, gbc_btnNewButton_1);

		txtValueDisplay = new JFormattedTextField();
		txtValueDisplay.setFont(new Font("Courier New", Font.PLAIN, 13));
		txtValueDisplay.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (txtValueDisplay.getText().equals("")) {
					return;
				} // if null
				int radix = showDecimal ? 10 : 16;
				setNewValue(Integer.valueOf(txtValueDisplay.getText(), radix));
			}
		});

		txtValueDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		txtValueDisplay.setPreferredSize(new Dimension(50, 23));
		GridBagConstraints gbc_txtValueDisplay = new GridBagConstraints();
		gbc_txtValueDisplay.insets = new Insets(0, 0, 0, 5);
		gbc_txtValueDisplay.gridx = 2;
		gbc_txtValueDisplay.gridy = 0;
		add(txtValueDisplay, gbc_txtValueDisplay);

		JButton btnNewButton_2 = new JButton(">");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stepValue(UP);
			}
		});
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_2.gridx = 3;
		gbc_btnNewButton_2.gridy = 0;
		add(btnNewButton_2, gbc_btnNewButton_2);

		JButton btnNewButton_3 = new JButton(">>");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNewValue((int) numberModel.getMaximum());
			}
		});
		GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
		gbc_btnNewButton_3.gridx = 4;
		gbc_btnNewButton_3.gridy = 0;
		add(btnNewButton_3, gbc_btnNewButton_3);
	}// Constructor

	// ---------------------------
	public void addSeekValueChangedListener(SeekValueChangeListener seekValueChangeListener) {
		seekValueChangedListenerList.add(SeekValueChangeListener.class, seekValueChangeListener);
	}// addSeekValueChangedListener

	public void removeSeekValueChangedListener(SeekValueChangeListener seekValueChangeListener) {
		seekValueChangedListenerList.remove(SeekValueChangeListener.class, seekValueChangeListener);
	}// addSeekValueChangedListener

	protected void fireSeekValueChanged() {
		Object[] listeners = seekValueChangedListenerList.getListenerList();
		// process
		SeekValueChangeEvent seekValueChangeEvent = new SeekValueChangeEvent(this, priorValue, currentValue);

		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == SeekValueChangeListener.class) {
				((SeekValueChangeListener) listeners[i + 1]).valueChanged(seekValueChangeEvent);
			} // if
		} // for

	}// fireSeekValueChanged

	// ---------------------------
	class SeekDocument extends PlainDocument {
		private static final long serialVersionUID = 1L;
		
		private String inputPattern;

		SeekDocument(boolean decimalDisplay) {
			if (decimalDisplay == true) {
				displayDecimal();
			} else {
				displayHex();
			} // if
		}// Constructor

		public void displayDecimal() {
			inputPattern = "-??[0-9]*";

		}// displayDecimal

		public void displayHex() {
			inputPattern = "[A-F|a-f|0-9]+";
		}// displayHex

		public void insertString(int offSet, String string, AttributeSet attributeSet) throws BadLocationException {
			if (string == null) {
				return;
			} // if

			if (!string.matches(inputPattern)) {
				return;
			} // for

			super.insertString(offSet, string, attributeSet);
		}// insertString
	}// class SeekDocument
		// ______________________________

	private static final int UP = 1;
	private static final int DOWN = -1;

}// class SeekPanel
