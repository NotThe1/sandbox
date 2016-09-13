package tableInJEditorPane;

import javax.swing.text.AbstractDocument;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.ParagraphView;
import javax.swing.text.StyleConstants;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

public class TableFactory implements ViewFactory {

	public TableFactory() {
		// TODO Auto-generated constructor stub
	}// Constructor

	@Override
	public View create(Element element) {
		String kind = element.getName();
		if (kind.equals(AbstractDocument.ContentElementName)) {
			return new LabelView(element);
		} else if (kind.equals(AbstractDocument.ParagraphElementName)) {
			return new ParagraphView(element);
		} else if (kind.equalsIgnoreCase(AbstractDocument.SectionElementName)) {
			return new BoxView(element, View.Y_AXIS);
		} else if (kind.equals(StyleConstants.ComponentElementName)) {
			return new ComponentView(element);
		} else if (kind.equals(MyStyledDocument.ELEMENT_NAME_TABLE)) {
			return new TableView(element, View.Y_AXIS);
		} else if (kind.equals(MyStyledDocument.ELEMENT_NAME_ROW)) {
			return new RowView(element, View.Y_AXIS);
		} else if (kind.equals(MyStyledDocument.ELEMENT_NAME_CELL)) {
			return new CellView(element, View.Y_AXIS);
		} else if (kind.equals(StyleConstants.IconElementName)) {
			return new IconView(element);
		} else {
			return new LabelView(element);

		}// if
	}// create

}// class TableFactory
