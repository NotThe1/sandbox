package menus;

import java.awt.Component;
import java.io.File;

import javax.swing.AbstractButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToolBar.Separator;

/**
 * handles the adding and removing of files on the "File" menu. It will place them between the two separators named
 * respectively : "separatorFiles" ( originally not visible) and "separatorExit" usuallly placed just above the Exit
 * menu item
 * 
 * @author Frank Martyn September 2016
 *
 */

public class MenuFilesUtility {
	static Separator exitSeparator;
	static Separator fileSeparator;

	public static final String SEPARATOR_EXIT = "separatorExit";
	public static final String RECENT_FILES_START = "recentFilesStart";
	public static final String RECENT_FILES_END = "recentFiles";
	private static final String NUMBER_DELIM = ")";

	// private static final String NUMBER_DELIM_REDEX = "\\"

	public MenuFilesUtility() {
		// TODO Auto-generated constructor stub
	}//

	/**
	 * Adds a JMenuItem to the menu's recent Files list with the file's absolute path for the text. Returns the new menu
	 * item so that an action listener can be added by the calling class
	 * 
	 * @param menu
	 *            - JMenu to have the JMenuItem added to ( usually "File"
	 * @param file
	 *            - File to be added to the menu's Recent Files List
	 * @return - The new JMenuItem ( so it can be manipulated by the calling class)
	 */

	public static JMenuItem addFile(JMenu menu, File file) {// change to Integer
		int menuCount = menu.getItemCount();
		int filesMenuStart = 0;
		int filesMenuEnd = 0;

		for (int i = 0; i < menuCount; i++) {
			if (menu.getMenuComponent(i).getName() == RECENT_FILES_START) {
				menu.getMenuComponent(i).setVisible(true); // Separator start
				menu.getMenuComponent(i + 1).setVisible(true);// Separator end
				menu.getMenuComponent(i + 2).setVisible(true);// menu Empty
				filesMenuStart = i + 1;
			}// if
			if (menu.getMenuComponent(i).getName() == RECENT_FILES_END) {
				filesMenuEnd = i;
				break;
			}// if
		}// for

		int fileIndex = 2;
		String[] menuTexts;
		String menuText;
		for (int j = filesMenuStart; j < filesMenuEnd; j++) {
			menuTexts = ((AbstractButton) menu.getMenuComponent(j)).getText().split("\\" + NUMBER_DELIM);
			menuText = String.format("%2d %s%s", fileIndex++, NUMBER_DELIM, menuTexts[1]);
			((AbstractButton) menu.getMenuComponent(j)).setText(menuText);
		}// for

		menuText = String.format("%2d %s %s", 1, NUMBER_DELIM, file.getAbsolutePath());
		JMenuItem newMenu = new JMenuItem(menuText);
		menu.insert(newMenu, filesMenuStart);

		return newMenu;
	}// addFile

	/**
	 * Clears the recent File list and sets visible false for Separator Start,Separator End, and menu clearRecentFiles
	 * @param menu is the menu the recent File list is on
	 */
	public static void clearList(JMenu menu) {
		int menuCount = menu.getItemCount();
		int filesMenuStart = 0;
		int filesMenuEnd = 0;

		for (int i = 0; i < menuCount; i++) {
			if (menu.getMenuComponent(i).getName() == RECENT_FILES_START) {
				menu.getMenuComponent(i).setVisible(false); // Separator start
				filesMenuStart = i ;
			}// if
			if (menu.getMenuComponent(i).getName() == RECENT_FILES_END) {
				menu.getMenuComponent(i + 1).setVisible(false);// Separator end
				menu.getMenuComponent(i + 2).setVisible(false);// menu Empty

				filesMenuEnd = i+1;
				break;
			}// if
		}// for
		
		for (int j = filesMenuEnd; j > filesMenuStart; j--){
			menu.remove(j);
		}//

	}

}// class MenuFilesUtility
