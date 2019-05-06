package MyUndoRedo;

import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.UndoableEdit;

public class MyUndoableEditEvent extends UndoableEditEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyUndoableEditEvent(Object arg0, UndoableEdit arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}//class MyUndoableEditEvent
