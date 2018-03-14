package pipes;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class Sender  implements Runnable {
	final PipedOutputStream output; //= new PipedOutputStream();
	final PipedInputStream input; //= new PipedInputStream();

	public Sender(PipedOutputStream output,PipedInputStream input) {
		this.output=output;
		this.input=input;
	}//Constructor

	@Override
	public void run() {
		try {
			output.write("Hello world, pipe!".getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//try

	}//run

}//class Sender
