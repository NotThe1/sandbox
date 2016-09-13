package tableInJEditorPane;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.text.BoxView;
import javax.swing.text.Element;
import javax.swing.text.View;

public class RowView extends BoxView {

	public RowView(Element elem, int axis) {
		super(elem, View.X_AXIS);
	}// Constructor

	public float getPreferredSpan(int axis) {
		return super.getPreferredSpan(axis);
	}// getPreferredSpan

	protected void layOut(int width, int height) {
		super.layout(width, height);
	}// layOut

	public float getMinimumSpan(int axis) {
		return getPreferredSpan(axis);
	}// getMinimumSpan

	public float getMaximumSpan(int axis) {
		return getPreferredSpan(axis);
	}// getMaximumSpan

	public float getAlignment(int axis) {
		return 0;
	}// getAlignment

	public void paintChild(Graphics g, Rectangle alloc, int index) {
		super.paintChild(g, alloc, index);
		g.setColor(Color.BLACK);
		int h = (int) getPreferredSpan(View.Y_AXIS) - 1;
		g.drawLine(alloc.x, alloc.y, alloc.x, alloc.y + h);
		g.drawLine(alloc.x + alloc.width, alloc.y, alloc.x + alloc.width, alloc.y + h);
	}// paintChild

}// class RowView
