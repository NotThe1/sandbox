package pipes;

import java.io.IOException;
import java.io.PipedOutputStream;

public class Sender implements Runnable {
	PipedOutputStream output; // = new PipedOutputStream();

	public Sender(PipedOutputStream output) {
		this.output = output;
	}// Constructor

	@Override
	public void run() {
		try {
			output.write("Hello world, pipe!".getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // try

	}// run

}// class Sender
