package pipes;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class Receiver implements Runnable {
	
	final PipedOutputStream output; //= new PipedOutputStream();
	final PipedInputStream input; //= new PipedInputStream();

	public Receiver(PipedOutputStream output,PipedInputStream input) {
		this.output=output;
		this.input=input;
	}//Constructor

	@Override
	public void run() {
		try {
			int data = input.read();
			while (data != -1) {
				System.out.print((char) data);
				data = input.read();
			} //
		} catch (IOException e) {
			e.getMessage();
		}
	}//run

}//class Receiver 
