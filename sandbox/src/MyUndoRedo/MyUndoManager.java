package MyUndoRedo;

import javax.swing.undo.UndoManager;

public class MyUndoManager extends UndoManager {
	private static final long serialVersionUID = 1L;

	@Override
	public void undo() {
		System.out.println("[MyUndoManager.undo]");
	}//undo
	
	@Override
	public void redo() {
		System.out.println("[MyUndoManager.redo]");
	}//undo
	
	

	
}//class MyUndoManager
