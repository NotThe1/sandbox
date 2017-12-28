package xml;

import java.util.List;

public class TestRead {
	public static void main(String args[]) {
		StaxParser read = new StaxParser();
        List<Item> readConfig = read.readConfig("C:\\Temp\\config.xml");
        for (Item item : readConfig) {
            System.out.println(item);
        }// for
    }//main
}//class TestRead
