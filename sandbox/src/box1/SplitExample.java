package box1;



	import java.awt.FlowLayout;
import java.awt.Panel;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JSplitPane;

	public class SplitExample {

		private static void createAndShowGUI() {

			// Create and set up the window.
			final JFrame frame = new JFrame("Split Pane Example");

			// Display the window.
			frame.setSize(800, 800);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			// set flow layout for the frame
			frame.getContentPane().setLayout(new FlowLayout());

			String[] options1 = { "Bird", "Cat", "Dog", "Rabbit", "Pig" };
			JComboBox<String> combo1 = new JComboBox<String>(options1);

			String[] options2 = { "Car", "Motorcycle", "Airplane", "Boat" };
			JComboBox<String> combo2 = new JComboBox<String>(options2);

			Panel panel1 = new Panel();
			panel1.add(combo1);

			Panel panel2 = new Panel();
			panel2.add(combo2);

			JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel1, panel2);
			// JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel1, panel2);
			splitPane.setOneTouchExpandable(true);
			splitPane.setBounds(0, 0, 500, 400);

			frame.getContentPane().add(splitPane);

		}

		public static void main(String[] args) {

	  //Schedule a job for the event-dispatching thread:

	  //creating and showing this application's GUI.

	  javax.swing.SwingUtilities.invokeLater(new Runnable() {

	public void run() {

	    createAndShowGUI(); 

	}

	  });
	    }

	}