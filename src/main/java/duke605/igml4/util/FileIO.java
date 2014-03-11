package duke605.igml4.util;

import java.io.File;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileIO {

	/**
	 * Reads and returns all lines in the file
	 * 
	 * @param fileName The path and name of the file
	 * @return all the lines in the file. Will never return null
	 */
	public static List<String> getLines(String fileName) {
		List<String> lines = new ArrayList<String>();
		File file;
		
		try {
			file = createAndGetFile(fileName);
			
			lines = FileUtils.readLines(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lines;
	}
	
	/**
	 * Gets a specific line from a file
	 * 
	 * @param fileName The path and name of the file
	 * @param lineNum The number of the line you want to retrieve
	 * @return the {@link String} that is on lineNum or and empty string if error occurs
	 */
	public static String getLine(String fileName, int lineNum) {
		// Getting lines from file
		List<String> lines = getLines(fileName);
		
		if (lines.size() > lineNum)
			return lines.get(lineNum);
		
		return "";
	}
	
	/**
	 * Writes a list of strings to a file
	 * 
	 * @param lines The list of strings that will be written
	 * @param fileName The name and path of the file
	 * @return true if the write was successful
	 */
	public static boolean writeLines(List<String> lines, String fileName) {
		File file;
		
		try {
			file = createAndGetFile(fileName);
			
			FileUtils.writeLines(file, lines);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Creates file if file does not exist and return it
	 * 
	 * @param fileName The path and name of file
	 * @return the file. will never return null
	 */
	public static File createAndGetFile(String fileName) {
		File file = new File(fileName);

		try {
			FileUtils.touch(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return file;
	}
	
	/**
	 * Gets the MD5 of a file. Creates file if it does not already exist
	 * 
	 * @param file
	 * @return the MD5 of a file
	 */
	public static String getMD5(File file) {
        StringBuffer hexString = new StringBuffer();
        String line = getLine(file.getPath(), 0);
        MessageDigest md;
        byte[] bytes = new byte[0];
        String hex;
        
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(line.getBytes());
			bytes = md.digest();
		} catch (Exception e) {
			e.printStackTrace();
		}

        // Convert the byte to hex format
        for (int i = 0;i < bytes.length;i++) {
            hex = Integer.toHexString(0xff & bytes[i]);
            
            if(hex.length() == 1) 
            	hexString.append('0');
            
            hexString.append(hex);
        }
        
        return hexString.toString();
    }
}
