package MyUndoRedo;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

public class MyUndoableEdit implements UndoableEdit {

	@Override
	public boolean addEdit(UndoableEdit anEdit) {
		// TODO Auto-generated method stub
		return false;
	}//addEdit

	@Override
	public boolean canRedo() {
		// TODO Auto-generated method stub
		return false;
	}//canRedo

	@Override
	public boolean canUndo() {
		// TODO Auto-generated method stub
		return false;
	}//canUndo

	@Override
	public void die() {
		// TODO Auto-generated method stub
	}//die

	@Override
	public String getPresentationName() {
		// TODO Auto-generated method stub
		return null;
	}//getPresentationName

	@Override
	public String getRedoPresentationName() {
		// TODO Auto-generated method stub
		return null;
	}//getRedoPresentationName

	@Override
	public String getUndoPresentationName() {
		// TODO Auto-generated method stub
		return null;
	}//getUndoPresentationName

	@Override
	public boolean isSignificant() {
		// TODO Auto-generated method stub
		return false;
	}//isSignificant

	@Override
	public void redo() throws CannotRedoException {
		// TODO Auto-generated method stub
	}//redo

	@Override
	public boolean replaceEdit(UndoableEdit anEdit) {
		// TODO Auto-generated method stub
		return false;
	}//replaceEdit

	@Override
	public void undo() throws CannotUndoException {
		// TODO Auto-generated method stub
	}//undo

}//class MyUndoableEdit
