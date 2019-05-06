package undoRedo;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;


public class Redo2 {
	
	
	public static void main(String[] args) {
		Runnable runner = new Runnable() {
			public void run() {
				JFrame frame = new JFrame(" ");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				//////////////////////////////////////////////////////////////////
				JTextComponent textcomp = new JTextArea();
			    final UndoManager undo = new UndoManager();
			    Document doc = (Document) textcomp.getDocument();
			    
			    // Listen for undo and redo events
			    ((javax.swing.text.Document) doc).addUndoableEditListener(new UndoableEditListener() {
			        public void undoableEditHappened(UndoableEditEvent evt) {
			            undo.addEdit(evt.getEdit());
			        }
			    });
			    
			    // Create an undo action and add it to the text component
			    textcomp.getActionMap().put("Undo",
			        new AbstractAction("Undo") {
			            /**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public void actionPerformed(ActionEvent evt) {
			                try {
			                    if (undo.canUndo()) {
			                        undo.undo();
			                    }
			                } catch (CannotUndoException e) {
			                }
			            }
			       });
			    
			    // Bind the undo action to ctl-Z
			    textcomp.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");
			    
			    // Create a redo action and add it to the text component
			    textcomp.getActionMap().put("Redo",
			        new AbstractAction("Redo") {
			            /**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						public void actionPerformed(ActionEvent evt) {
			                try {
			                    if (undo.canRedo()) {
			                        undo.redo();
			                    }
			                } catch (CannotRedoException e) {
			                }
			            }
			        });
			    
			    // Bind the redo action to ctl-Y
			    textcomp.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "Redo");
				//////////////////////////////////////////////////////////////////
				frame.add(textcomp,BorderLayout.CENTER);
				

				frame.setSize(300, 400);
				frame.setVisible(true);
			}// run
		};// Runnable runner
		EventQueue.invokeLater(runner);
	}// main

}
