package tableInJEditorPane;

import javax.swing.text.BoxView;
import javax.swing.text.Element;
import javax.swing.text.View;

public class CellView extends BoxView {

	public CellView(Element arg0, int arg1) {
		super(arg0, View.Y_AXIS);
		setInsets((short) 2, (short) 2, (short) 2, (short) 2);
	}// Constructor

	public float getPreferredSpan(int axis) {
		return axis == View.X_AXIS ? getCellWidth() : super.getPreferredSpan(axis);

	}// getPreferredSpan

	public float getMinimumSpan(int axis) {
		return getPreferredSpan(axis);
	}// getMinimumSpan

	public float getMaximumSpan(int axis) {
		return getPreferredSpan(axis);
	}// getMinimumSpan

	public float getAlignment(int axis) {
		return 0;
	}// getAlignment

	public int getCellWidth() {
		Integer i = (Integer) getAttributes().getAttribute(MyStyledDocument.PARAM_CELL_WIDTH);
		return i != null ? i.intValue() : 100;
	}// getCellWidth
}// Class CellView
