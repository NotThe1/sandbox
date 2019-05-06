package carbonite;

import java.io.File;

public class Try1 {
	
	public static void main(String[] args) {
		String start = "C:\\TEMP";
		new Try1().doDirectory(new File(start));
	}//main
	
	
	private void doDirectory(File dir) {
		File[] files = dir.listFiles();
		if(files==null) {
			return;
		}//if
		for(File f:files) {
			
			displayFile( f);
			if (f.isDirectory()) {
				doDirectory(f);
			}else {
				doFile(f);
			}//if directory
		}//for
		
	}//doDirectory

	private void doFile(File file) {
		displayFile( file);
//		int a = 0;
	}//doFile
	
	private void displayFile(File file) {
		String readOnly = file.canRead()?" .":"RO";
//		String readOnly = "X";
		String hidden = file.isHidden()?"H":".";
		String type = file.isDirectory()?"< dir >":"<file>";
		String message = String.format("%s %s %s %s%n", readOnly,hidden,type,file.getAbsolutePath());
		System.out.print(message);
	}//displayFile

}//class Try1
