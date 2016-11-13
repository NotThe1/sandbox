package Misc;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.Element;
import javax.swing.text.NavigationFilter;
import javax.swing.text.Position;
import javax.swing.text.StyledDocument;

public class Navigation {
//	private static final String START_STRING = "Start\n";
	private static final String START_STRING = 
			"0000:  3C 3F 78 6D 6C 20 76 65  72 73 69 6F 6E 3D 22 31   0123456789abcdef\n" +
	"0010:  2E 30 22 20 65 6E 63 6F  64 69 6E 67 3D 22 77 69   .0\" encoding=\"wi\n" +
	"0020:  6E 64 6F 77 73 2D 31 32  35 32 22 20 73 74 61 6E   ndows-1252\" stan\n" +
	"0030:  64 61 6C 6F 6E 65 3D 22  6E 6F 22 3F 3E 0A 3C 21   dalone=\"no\"?>.<!\" +\n ";
	private static final int START_STRING_LENGTH = START_STRING.length();

	public static void main(String args[]) {
		Runnable runner = new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Navigation Example");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				JTextPane textPane = new JTextPane();
				textPane.setText(START_STRING);
				textPane.setCaretPosition(START_STRING_LENGTH);
				JScrollPane scrollPane = new JScrollPane(textPane);
				frame.add(scrollPane, BorderLayout.CENTER);

				NavigationFilter filter = new NavigationFilter() {
					private int addressEnd = 7;
					public void setDot(NavigationFilter.FilterBypass fb, int dot, Position.Bias bias) {
						StyledDocument doc = (StyledDocument) textPane.getDocument();
						Element paragraphElement = doc.getParagraphElement(dot);
						Element dataElement = paragraphElement.getElement(0);
						int limit = dataElement.getStartOffset();
						if (dot < START_STRING_LENGTH) {
							fb.setDot(START_STRING_LENGTH, bias);
						} else {
							fb.setDot(dot, bias);
						}
						System.out.printf("[setDot] dot: %d, limit: %d%n", dot,limit);
					}

					public void moveDot(NavigationFilter.FilterBypass fb, int dot, Position.Bias bias) {
						if (dot < START_STRING_LENGTH) {
							fb.setDot(START_STRING_LENGTH, bias);
						} else {
							fb.setDot(dot, bias);
						}
					}
				};

				textPane.setNavigationFilter(filter);

				frame.setSize(550, 150);
				frame.setVisible(true);
			}
		};
		EventQueue.invokeLater(runner);
	}
}
