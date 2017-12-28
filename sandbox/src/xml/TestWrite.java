package xml;

public class TestWrite {
	public static void main(String[] args) {
        StaxWriter configFile = new StaxWriter();
        configFile.setFile("C:\\Temp\\config2.xml");
        try {
            configFile.saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }//try
    }// main
}//class TestWrite
