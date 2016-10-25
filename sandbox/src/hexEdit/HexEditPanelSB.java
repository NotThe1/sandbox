package hexEdit;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.BoundedRangeModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class HexEditPanelSB extends JPanel implements AdjustmentListener{
	private byte[] source;
	
	private JTextPane textPane;
	private JScrollBar scrollBar;
	
	public void loadDocument(byte[] source){
		this.source= source.clone();
		setUpScrollBar(scrollBar , textPane );
		//--
		BoundedRangeModel model = scrollBar.getModel();
		System.out.printf("Max = %d%n", model.getMaximum());
		System.out.printf("Min = %d%n", model.getMinimum());
		System.out.printf("Extent = %d%n", model.getExtent());
		System.out.printf("MValue = %d%n", model.getValue());
		//-
	}
	
	
	private void setExtent(int amount, BoundedRangeModel model) {
		model.setExtent(amount);
	}// setExtent

	private void setMin(int amount, BoundedRangeModel model) {
		model.setMinimum(amount);
	}// setMin

	private void setMax(int amount, BoundedRangeModel model) {
		model.setMaximum(amount);
	}// setMax

//	private void setValue(int amount, BoundedRangeModel model) {
//		model.setValue(amount);
//	}// setValue

	
	private void setUpScrollBar(JScrollBar thisScrollBar, JTextPane thisTextPane) {
//		int fontHeight = calcFontHeight(thisTextPane);
		int max = maximumNumberOfRows(thisTextPane);
		int extent = calcExtent(textPane);

		BoundedRangeModel model = scrollBar.getModel();
		setMin(0, model);
		setMax(max, model);
		// setValue(0,model);
		setExtent(extent, model);

	}// setUpScrollBar

	private int calcUsableHeight(JTextPane thisPane) {
		Insets insets = thisPane.getInsets();
		Dimension dimension = thisPane.getSize();
		return dimension.height - (insets.bottom + insets.top);
	}// calcUsableHeight

	private int calcFontHeight(JTextPane thisPane) {
		Font font = textPane.getFont();
		return textPane.getFontMetrics(font).getHeight();
	}// calcFontHeight

	private int calcExtent(JTextPane thisPane) {
		int useableHeight = calcUsableHeight(thisPane);
		int fontHeight = calcFontHeight(thisPane);
		return (int) (useableHeight / fontHeight);
	}// calcExtent

	private int maximumNumberOfRows(JTextPane thisTextPane) {
//		return (int) spinnerRows.getValue();
		return (source.length/BYTES_PER_LINE) + 1;
	}// maximumNumberOfRows	
	
	public HexEditPanelSB() {
		initialize();
		appInit();
	}
	private void appInit(){
//		setUpScrollBar( scrollBar,  textPane);
	}//appInit
	private void initialize(){
		setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{780, 25};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("          00 01 02 03 04 05 06 07 08  09 0A 0B 0C 0D 0E 0F  ");
		lblNewLabel.setForeground(new Color(105, 105, 105));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setFont(new Font("Courier New", Font.BOLD, 16));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		
		textPane = new JTextPane();
		textPane.setText("12345678  00 01 02 03 04 05 06 07 08  09 0A 0B 0C 0D 0E 0F  !@#$%^&* ()_-+=<>\r\n123456    00 01 02 03 04 05 06 07 08  09 0A 0B 0C 0D 0E 0F  !@#$%^&* ()_-+=<>\r\n1234      00 01 02 03 04 05 06 07 08  09 0A 0B 0C 0D 0E 0F  !@#$%^&* ()_-+=<>\r\n");
		textPane.setFont(new Font("Courier New", Font.PLAIN, 16));
		GridBagConstraints gbc_textPane = new GridBagConstraints();
		gbc_textPane.insets = new Insets(0, 0, 0, 5);
		gbc_textPane.fill = GridBagConstraints.BOTH;
		gbc_textPane.gridx = 0;
		gbc_textPane.gridy = 1;
		add(textPane, gbc_textPane);
		
		scrollBar = new JScrollBar();
		scrollBar.setMaximum(0);
		scrollBar.setPreferredSize(new Dimension(25, 48));
		scrollBar.setMinimumSize(new Dimension(25, 5));
		scrollBar.setMaximumSize(new Dimension(25, 32767));
		GridBagConstraints gbc_scrollBar = new GridBagConstraints();
		gbc_scrollBar.fill = GridBagConstraints.VERTICAL;
		gbc_scrollBar.gridx = 1;
		gbc_scrollBar.gridy = 1;
		add(scrollBar, gbc_scrollBar);
		
		
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent arg0) {
		// TODO Auto-generated method stub
		
	}//adjustmentValueChanged
	
	private static final int BYTES_PER_LINE = 16;

}//class HexEditPanelSB
