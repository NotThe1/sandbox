package Misc;

public class TestTypeSafety {

	String fred;

	public static void main(String[] args) {
		new TestTypeSafety().doIt();
	}// main

	private void doIt() {
		refresh(true);
		refresh (false);
		fred = "Fred";
		refresh(true);
		refresh(false);
	}// doIt

	private void refresh(boolean state) {
		fred = state?"Fred":null;
		String msg;
		if (fred == null) {
			msg = "null";
		} else {
			msg = " NOT NULL";
		} // if
		System.out.println(msg);

	}// refresh

}// class TestTypeSafety
