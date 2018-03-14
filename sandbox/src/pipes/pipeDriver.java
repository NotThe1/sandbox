package pipes;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class pipeDriver {

	public pipeDriver() {
		// TODO Auto-generated constructor stub
	}// Constructor

	public static void main(String[] args) {
		try {
			PipedOutputStream output = new PipedOutputStream();
			PipedInputStream input = new PipedInputStream(output);
			Sender sender = new Sender(output, input);
			Receiver reciever = new Receiver(output, input);
			Thread threadSender = new Thread(sender);
			Thread threadReceiver = new Thread(reciever);
			threadSender.start();
			threadReceiver.start();
			threadSender.join();
			threadReceiver.join();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // try

	}// main

}// class pipeDriver
