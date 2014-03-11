package duke605.igml4.lib;

public class JsonMod {
	
	public String name;
	public String other;
	public String link;
	public String desc;
	public String[] author;
	public String source;
	public String[] type;
	public String[] dependencies;
	public String[] versions;
	
	/**
	 * Checks if a string exist in this {@link JsonMod} object
	 * 
	 * @param searchString The string being searched for
	 * @return true if the string was found
	 */
	public boolean contains(String searchString) {
		boolean isFound = false;
		
		// Triming the search string
		searchString = searchString.trim();
		
		// Testing if the title contains the searched string
		if (name.toLowerCase().contains(searchString.toLowerCase()))
			isFound = true;
		
		// Testing if the description contains the searched string
		else if (desc.toLowerCase().contains(searchString.toLowerCase()))
			isFound = true;
		
		// Testing if the authors array contains the searched string
		else if (doesArrayContainString(searchString, author))
			isFound = true;
		
		// Testing if the dependencies contains the searched string
		else if (doesArrayContainString(searchString, dependencies))
			isFound = true;
		
		//Testing if the version array contains the searched string
		else if (doesArrayContainString(searchString, versions))
			isFound = true;
		
		// Testing if the type array contains searched string
		else if (doesArrayContainString(searchString, type))
			isFound = true;
		
		return isFound;
	}
	
	/**
	 * Searches an String array for a string
	 * 
	 * @param search The string being searched for
	 * @param array The array to be search
	 * @return true if the string was found in the array
	 */
	public boolean doesArrayContainString(String search, String[] array) {
		for (String s : array)
			if (s.toLowerCase().contains(search.toLowerCase()))
				return true;
		
		return false;
	}
	
	public String getDesc() {
		return desc.replace("<li>", "\n- ").replaceAll("<br>", "\n").replaceAll("<br/>", "\n").replaceAll("\\<.*?\\>", "");
	}
}
