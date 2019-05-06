package tableInJEditorPane;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import java.util.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;

public class ElementSample {
	String contents = "Hello, World\nWelcome Home\nAdios";
	public static void main(String args[]) {
		Runnable runner = new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Element Example");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				final JTextArea textArea = new JTextArea("Hello, World\nWelcome Home\nAdios");
				JScrollPane scrollPane = new JScrollPane(textArea);
				JButton button = new JButton("Show Elements");
				ActionListener actionListener = new ActionListener() {
					public void actionPerformed(ActionEvent actionEvent) {
						Document document = textArea.getDocument();
						ElementIterator iterator = new ElementIterator(document);
						Element element = iterator.first();
						while (element != null) {
							System.out.println(element.getStartOffset());
							element = iterator.next();
						}
					}
				};
				button.addActionListener(actionListener);
				frame.add(scrollPane, BorderLayout.CENTER);
				frame.add(button, BorderLayout.SOUTH);
				frame.setSize(250, 250);
				frame.setVisible(true);
			}
		};
		EventQueue.invokeLater(runner);
	}
}
