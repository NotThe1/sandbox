package pipes;

import java.io.IOException;
import java.io.PipedInputStream;

public class Receiver implements Runnable {
	
	final PipedInputStream input; //= new PipedInputStream();

	public Receiver(PipedInputStream input) {
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
