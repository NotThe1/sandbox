package robot;



	import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

	public class FocusAtStart {
	  public static void main(String[] argv) throws Exception {
	    JFrame frame = new JFrame();
	    JButton component1 = new JButton("1");
	    JButton component2 = new JButton("2");
	    JButton component3 = new JButton("3");

	    frame.setLayout(new FlowLayout());
	    frame.add(component1);
	    frame.add(component2);
	    frame.add(component3);

//	    InitialFocusSetter.setInitialFocus(frame, component2);

	    frame.pack();
	    frame.setVisible(true);
	  }
	}

	class InitialFocusSetter {
	  public static void setInitialFocus(Window w, Component c) {
	    w.addWindowListener(new FocusSetter(c));
	  }
	}

	class FocusSetter extends WindowAdapter {
	  Component initComp;

	  FocusSetter(Component c) {
	    initComp = c;
	  }

	  public void windowOpened(WindowEvent e) {
	    initComp.requestFocus();
	    e.getWindow().removeWindowListener(this);
	  }
	}