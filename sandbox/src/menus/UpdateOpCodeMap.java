package menus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateOpCodeMap {

	public UpdateOpCodeMap() {
		// TODO Auto-generated constructor stub
	}// Constructor

	public static void main(String[] args) {
		new UpdateOpCodeMap().doIt();

	}// main

	private void doIt() {
		// InputStream inputStream = this.getClass().getResourceAsStream("C:\\Temp\\temp.txt");
		// InputStream inputStream = this.getClass().getResourceAsStream("/daaTemp.txt");
		Pattern pattern = Pattern.compile("( 0X[0123456789ABCDEF]{2})");
		Matcher matcher;
		try {
			Scanner scanner = new Scanner(new File("C:\\Temp\\temp.txt"));
			FileWriter fw = new FileWriter("C:\\Temp\\OpCodeMap.txt");
			String line;
			// scanner.nextLine();
			while (scanner.hasNextLine()) {
				line = scanner.nextLine() ;
				matcher = pattern.matcher(line);
				if (matcher.find()) {
					String fix = "\"" + matcher.group(1).substring(3) +"\"";
					line = matcher.replaceAll(fix);
					
					line = line.replace("(byte)", "");
				}//
				
				fw.write(line+ System.lineSeparator());
			} // while
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}// doIt

}//
