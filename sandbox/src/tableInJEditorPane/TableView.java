package tableInJEditorPane;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.text.BoxView;
import javax.swing.text.Element;
import javax.swing.text.View;

public class TableView extends BoxView {

	public TableView(Element elem, int axis) {
		super(elem, View.Y_AXIS);
	}// Constructor
	
	public float getMinimumSpan(int axis){
		return getPreferredSpan(axis);
	}//getMinimumSpan
	
	public float getMaximumSpan(int axis){
		return getPreferredSpan(axis);
	}//getMaximumSpan
	
	public float getAlignment(int axis){
		return 0;
	}//getAlignment
	
	protected void paintChild(Graphics g, Rectangle alloc, int index){
		super.paintChild(g, alloc, index);
		g.setColor(Color.BLACK);
		g.drawLine(alloc.x, alloc.y, alloc.x + alloc.width, alloc.y);
		int lastY = alloc.y + alloc.height;
		if (index == getViewCount()-1){
			lastY--;
		}//if
		g.drawLine(alloc.x, lastY, alloc.x + alloc.width, lastY);
	}//paintChild

}// class TableView
