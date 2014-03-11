package duke605.igml4.util;

import java.awt.Desktop;
import java.net.URL;

public class MiscUtils {

	/**
	 * Opens up the user's default browse to the given site url
	 * 
	 * @param siteUrl The URL of the site that the browser will open to
	 */
	public static void openSite(String siteUrl) {
		Desktop desktop = Desktop.getDesktop();
		
		if (!Desktop.isDesktopSupported() || !desktop.isSupported(Desktop.Action.BROWSE)) {
			System.err.println("Desktop is not supported on your system!");
			return;
		}
		
		try {
			desktop.browse(new URL(siteUrl).toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
