package pipes;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class PipeExample {

	public static void main(String[] args) throws IOException {
		final PipedOutputStream output = new PipedOutputStream();
		final PipedInputStream input = new PipedInputStream(output);

		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					int b = 0;
					while (b != 0x51) {
//						System.out.printf(String.format("b = %02X%n", b));
						b = System.in.read();
						output.write(b);
					}

//					output.write("Hello world, pipe!".getBytes());
//					output.write("12345 67890, PIPE!".getBytes());
					
				} catch (IOException e) {
					e.getMessage();
				} // try
			}// run
		});// thread1

		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					int data = input.read();
					while (data != -1) {
						System.out.print((char) data);
						data = input.read();
					} //
					input.close();
				} catch (IOException e) {
					e.getMessage();
				}
			}// run
		});// thread2

		thread1.start();
		thread2.start();

	}// main

}// class PipeExample
