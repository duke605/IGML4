package duke605.igml4.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonParser;

public class JsonUtils {

	public static final Gson gson = new Gson();
	
	/**
	 * Parses a string into a generic JsonElement
	 * 
	 * @param jsonString The {@link JsonElement} in {@link String} form
	 * @return the {@link JsonElement} or null is error occurs
	 */
	public static JsonElement getAsJsonElementFromString(String jsonString) {
		JsonParser parser = new JsonParser();
		JsonElement element;
		
		try {
			element = parser.parse(jsonString);
			
			return element;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return JsonNull.INSTANCE;
	}
	
	/**
	 * Gets a {@link JsonArray} from a {@link String}
	 * 
	 * @param jsonString The {@link JsonArray} in {@link String} form
	 * @return the {@link JsonArray} represented by the jsonString or null if error occurs
	 */
	public static JsonArray getAsJsonArrayFromString(String jsonString) {
		JsonElement element;
		
		// Getting JsonElement from jsonString
		element = getAsJsonElementFromString(jsonString);
		
		if (element.isJsonArray())
			return (JsonArray) element;
		
		return null;
	}
	
	/**
	 * Constructs an object from a {@link JsonElement}
	 * 
	 * @param element The {@link JsonElement} that the object will be constructed from
	 * @param clazz The {@link Class} that the element will be wrapped to
	 * @return the object or null if error occured
	 */
	public static <T> T fromJsonElement(JsonElement element, Class<? extends T> clazz) {
		try {
			return gson.fromJson(element, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
