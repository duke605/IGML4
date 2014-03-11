package duke605.igml4.handler;

import duke605.igml4.lib.JsonMod;
import duke605.igml4.lib.LibMods;

public class SearchHandler {
	
	public static void search(String searchString) {
			
		// Does an or search if the search string contains a |
		if (searchString.contains("|"))
			searchOr(searchString.split("\\|"));
		
		// Does an and search if the search string contains a &
		else if (searchString.contains("&"))
			searchAnd(searchString.split("\\&"));
		
		// Does a simple search for one string
		else
			searchSimple(searchString);
	}
	
	/**
	 * Searches for mods that contain searchString
	 * 
	 * @param searchString The string that is being looked for
	 */
	public static void searchSimple(String searchString) {
		LibMods.searchedJsonMods.clear();
		
		for (JsonMod mod : LibMods.jsonMods) {
			
			// Adding JsonMod to searhedJsonMods if canAdd is true
			if (mod.contains(searchString))
				LibMods.searchedJsonMods.add(mod);
		}
	}
	
	/**
	 * Searches for mods containing at least one of the string elements
	 * 
	 * @param searchArray The strings that will be search for
	 */
	public static void searchOr(String[] searchArray) {	
		LibMods.searchedJsonMods.clear();
		
		for (JsonMod mod : LibMods.jsonMods) {
			for (String searchString : searchArray) {
				
				// Checks if the mod contains the search string and adds
				// It to the searched mod list if it does
				if (mod.contains(searchString)) {
					LibMods.searchedJsonMods.add(mod);
					break;
				}
			}
		}
	}
	
	/**
	 * Searches for mods containing all of the searched strings
	 * 
	 * @param searchArray The strings that will be search for
	 */
	public static void searchAnd(String[] searchArray) {
		final int TOTAL_MATCHES_NEEDED = searchArray.length;
		int count;
		
		LibMods.searchedJsonMods.clear();
		
		for (JsonMod mod : LibMods.jsonMods) {
			count = 0;
			for (String searchString : searchArray) {
				
				// Checks if the mod contains the searched string
				// and adds 1 to the count if it does
				if (mod.contains(searchString))
					count++;
				
				// breaks to prevent further processing
				else
					break;
			}
			
			// If all string were found in the mod, add it to the searched mods
			if (count == TOTAL_MATCHES_NEEDED)
				LibMods.searchedJsonMods.add(mod);
		}
	}
}
