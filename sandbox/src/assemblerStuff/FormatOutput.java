package assemblerStuff;

public class FormatOutput {

	public FormatOutput() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		doIt1("");
		doIt1("  ");
		doIt1("A C         ");
//		doIt("bigSymbol:    LD A,B ; comment");
//		doIt("bigSymbol:");
//		doIt(" LD A,B ; comment");
//		doIt("    LD A,B");
//		doIt(" ; comment");
//		doIt("; comment");
//		doIt("  LD A,B ; comment");

	}//main
	public static void doIt1(String line) {
//		int index = line.length();
//		String ans = line;
		int firstTrailingSpace = line.length()-1;
		while (true) {
			if (!line.endsWith(" ")) {
				break;
			}//  done
			line = line.substring(0,firstTrailingSpace);
			firstTrailingSpace--;	
		}//while
		
		
		
//		String ans = line.substring(0,firstTrailingSpace);
		int a =0;
	}//
	public static void doIt(String line) {
		System.out.println();
		System.out.println(line);
		
		String com = "";
		String symbol = "";
		String cmd = "";

		if (line.contains(";")) {
			String[] lineParts = line.split(";");
			com = lineParts.length == 2 ? "; " + lineParts[1] : "";
			line = lineParts[0];
		} // comments

		if (line.startsWith(" ")) {
			symbol = "";
		} else {
			int symbolEnd = line.indexOf(" ");
			symbol = symbolEnd== -1?line:line.substring(0, symbolEnd);
			line = symbolEnd== -1?"":line.substring(symbolEnd);
		} // if symbol

		line = line.trim();
		if (line.length() == 0) {
			cmd = "";
		} else {
			int cmdEnd = line.indexOf(" ");
			cmd = line.substring(0, cmdEnd);
			cmd = cmd + "  " + line.substring(cmdEnd);

		} // inner if

		int cmdPos = 12;
		int comPos = 45;
		int adjPos = comPos - cmdPos;
		String fmtString = "%-" + cmdPos + "s%-" + adjPos + "s%s";

//		System.out.println();
//		System.out.println(fmtString);

		String target = String.format(fmtString, symbol, cmd, com);
		System.out.println(target);

		System.out.println();
	}//main

}//class
