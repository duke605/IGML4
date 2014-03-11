package duke605.igml4.util;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class WebIO {

	/**
	 * Reads and returns all the lines in on a site
	 * 
	 * @param urlName The url where the site is located
	 * @return all the lines on the site. Will never return null
	 */
	public static List<String> getLines(String urlName) {
		URLConnection con;
		List<String> lines = new ArrayList<String>();
		
		try {
			con = new URL(urlName).openConnection();
			
			lines = IOUtils.readLines(con.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lines;
	}
	
	/**
	 * Reads a site and gets a specific line on it
	 * 
	 * @param urlName The url where the site is located
	 * @param lineNum The line that will be retrieved
	 * @return the string that is on lineNum or and empty string if error occurs
	 */
	public static String getLine(String urlName, int lineNum) {
		List<String> lines;
		
		// Getting lines from site
		lines = getLines(urlName);
		
		// Returns string on lineNum if exists
		if (lines.size() > lineNum)
			return lines.get(lineNum);
		
		return "";
	}
}
