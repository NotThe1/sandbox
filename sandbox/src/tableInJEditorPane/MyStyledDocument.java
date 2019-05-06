package tableInJEditorPane;

import java.util.ArrayList;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleContext;

public class MyStyledDocument extends DefaultStyledDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MyStyledDocument() {
		super();
	}// Constructor

	public MyStyledDocument(StyleContext arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}// Constructor

	public MyStyledDocument(Content arg0, StyleContext arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}// Constructor

	protected void insertTable(int offset, int rowCount, int[] colWidths) {
		try {
			SimpleAttributeSet attrs = new SimpleAttributeSet();

			ArrayList<ElementSpec> tableSpecs = new ArrayList<ElementSpec>();
			tableSpecs.add(new ElementSpec(attrs, ElementSpec.EndTagType)); // close paragraph tag

			SimpleAttributeSet tableAttrs = new SimpleAttributeSet();
			tableAttrs.addAttribute(ElementNameAttribute, ELEMENT_NAME_TABLE);
			ElementSpec tableStart = new ElementSpec(tableAttrs,ElementSpec.StartTagType);
			tableSpecs.add(tableStart);
			
			fillRowSpecs(tableSpecs,rowCount, colWidths);
			
			ElementSpec tableEnd = new ElementSpec(tableAttrs,ElementSpec.EndTagType);
			tableSpecs.add(tableEnd);	//end table tag
			
			tableSpecs.add(new ElementSpec(attrs,ElementSpec.StartTagType));	//open new paragraph after table
			
			ElementSpec[] spec = new ElementSpec[tableSpecs.size()];
			tableSpecs.toArray(spec);
			
			this.insert(offset,spec);

		} catch (BadLocationException ble) {
			ble.printStackTrace();
		}// try

	}// insertTable
	
	protected void fillRowSpecs(ArrayList<ElementSpec> tableSpecs,int rowCount, int[] colWidths){
		SimpleAttributeSet rowAttrs = new SimpleAttributeSet();
		rowAttrs.addAttribute(ElementNameAttribute, ELEMENT_NAME_ROW);
		for (int i = 0; i < rowCount; i ++){
			ElementSpec rowStart = new ElementSpec(rowAttrs,ElementSpec.StartTagType);
			tableSpecs.add(rowStart);
			
			fillCellSpecs(tableSpecs,colWidths);
			
			ElementSpec rowEnd = new ElementSpec(rowAttrs,ElementSpec.EndTagType);
			tableSpecs.add(rowEnd);
		}//for
	}//fillRowSpecs
	
	protected void fillCellSpecs(ArrayList<ElementSpec> tableSpecs,int[] colWidths){
		for (int i = 0 ; i <colWidths.length; i++){
		SimpleAttributeSet cellAttrs = new SimpleAttributeSet();
		cellAttrs.addAttribute(ElementNameAttribute,ELEMENT_NAME_CELL);
		cellAttrs.addAttribute(PARAM_CELL_WIDTH, new Integer(colWidths[i]));
		ElementSpec cellStart = new ElementSpec(cellAttrs,ElementSpec.StartTagType);
		tableSpecs.add(cellStart);
		
		ElementSpec parStart = new ElementSpec(new SimpleAttributeSet(),ElementSpec.StartTagType);
		tableSpecs.add(parStart);
		ElementSpec parContent = new ElementSpec(new SimpleAttributeSet(),ElementSpec.ContentType,"\n".toCharArray(),0,1);
		tableSpecs.add(parContent);
		ElementSpec parEnd = new ElementSpec(new SimpleAttributeSet(),ElementSpec.EndTagType);
		tableSpecs.add(parEnd);
		ElementSpec cellEnd = new ElementSpec(cellAttrs,ElementSpec.EndTagType);
		tableSpecs.add(cellEnd);
		}
	}//fillCellSpecs

	/************************ Named Constants *************************************************/
	public static final String ELEMENT_NAME_TABLE = "table";
	public static final String ELEMENT_NAME_ROW = "row";
	public static final String ELEMENT_NAME_CELL = "cell";
	public static final String PARAM_CELL_WIDTH = "cell-width";

}// class myStyledDocument
