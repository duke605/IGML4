package duke605.igml4;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import duke605.igml4.handler.KeyHandler;
import duke605.igml4.lib.JsonMod;
import duke605.igml4.lib.LibMods;
import duke605.igml4.lib.Reference;
import duke605.igml4.util.FileIO;
import duke605.igml4.util.JsonUtils;
import duke605.igml4.util.WebIO;

@Mod(
		modid = Reference.MODID,
		name = Reference.NAME,
		version = Reference.VERSION
	)
public class IGML4 {

	@Instance(Reference.MODID)
	public static IGML4 instance;
	
	/** Json File */
	public static File jsonFile;
	
	/** IGML key */
	public static KeyBinding keyIgml;
	
	/** Used to disable or enable details for the session */
	public static boolean showDetails = true;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		String md5;
		
		// Gets MD5 from modlist site
		md5 = WebIO.getLine(Reference.MD5_URL, 0);
		
		// Setting jsonFile
		jsonFile = new File(event.getModConfigurationDirectory() + "/IGML4.json");
		
		// Determines what path mod should proceed in
		if (md5.isEmpty())
			initWithoutConnection();
		else
			initWithConnection(md5);			
	}
	
	/**
	 * Called only when connection to modlist site could not be made
	 */
	private void initWithoutConnection() {
		String jsonString = FileIO.getLine(jsonFile.getPath(), 0);
		JsonArray modArray;
		
		// Outputting error
		System.err.println(String.format("[%s] Connection to modlist.mcf.li could not be made", Reference.MODID));
		
		// If the jsonString is empty, could not retrieve
		// JSON mods from local file
		if (jsonString.isEmpty()) {
			System.err.println(String.format("[%s] Could not retrieve local copy of JSON mods!", Reference.MODID));
			return;
		}
		
		// parsing jsonString into a JsonArray
		modArray = JsonUtils.getAsJsonArrayFromString(jsonString);
		
		// Outputting error if jsonString could not be parsed
		if (modArray == null) {
			System.err.println(String.format("[%s] An error occured while parsing the jsonString to a JsonArray", Reference.MODID));
			return;
		}
		
		// Compiling all the JsonObjects in the JsonArray into JsonMods
		compileJsonModList(modArray);
	}
	
	/**
	 * Called only when connection to modlist site is successful
	 * 
	 * @param md5 The MD5 {@link String} fetched from the modlist site
	 */
	private void initWithConnection(String remoteMD5) {
		String localMD5 = FileIO.getMD5(jsonFile);
		String jsonString;
		JsonArray modArray;
		
		// Getting Json String from modlist site or local file
		if (!localMD5.equals(remoteMD5))
			jsonString = WebIO.getLine(Reference.JSON_URL, 0);
		else
			jsonString = FileIO.getLine(jsonFile.getPath(), 0);
		
		// If the jsonString is empty a connection could not be 
		// established and should initiate without connection.
		// Otherwise it will continue
		if (jsonString.isEmpty()) {
			initWithoutConnection();
			return;
		}
		
		// Parsing jsonString into a JsonArray
		modArray = JsonUtils.getAsJsonArrayFromString(jsonString);
		
		// Outputting error if jsonString could not be parsed
		if (modArray == null) {
			System.err.println(String.format("[%s] An error occured while parsing the jsonString to a JsonArray", Reference.MODID));
			return;
		}
		
		// Writing jsonString to local file if it was remote
		if (!localMD5.equals(remoteMD5))
			FileIO.writeLines(Arrays.asList(new String[] {jsonString}), jsonFile.getPath());
		
		// Compiling all the JsonObjects in the JsonArray into JsonMods
		compileJsonModList(modArray);
	}
	
	/**
	 * Called after initWithConnection or initWithoutConnection complete and comiples
	 * the {@link JsonArray} into {@link JsonMod}s
	 * 
	 * @param modArray The {@link JsonArray} the {@link JsonMod}s will be constructed from
	 */
	private void compileJsonModList(JsonArray modArray) {
		JsonElement element;
		JsonMod jsonMod;
		
		// Creating JsonMods from JsonArray
		for (int i = 0;i < modArray.size();i++) {
			element = modArray.get(i);
			
			if (!element.isJsonObject()) {
				System.err.println(String.format("[%s] Found a JsonElement in modArray that was not a JsonObject", Reference.MODID));
				continue;
			}
			
			// Parsing JsonElement into a JsonMod
			jsonMod = JsonUtils.fromJsonElement(element, JsonMod.class);
			
			if (jsonMod == null) {
				System.err.println(String.format("[%s] Element could not be parsed properly!", Reference.MODID));
				continue;
			}
			
			// Adding jsonMod to JsonMod list
			LibMods.jsonMods.add(jsonMod);
		}
		
		finishInitialization();
	}
	
	/**
	 * Called only after everything has completed without any errors
	 */
	@SuppressWarnings("unchecked")
	private void finishInitialization() {
		// Outputting success message
		System.out.println();
		System.out.println("=====================IGML4 initialized successfully!=====================");
		
		// Assigning keybinding
		keyIgml = new KeyBinding(StatCollector.translateToLocal("key.igml"), Keyboard.KEY_F12, StatCollector.translateToLocal("key.igmlDesc"));
		ClientRegistry.registerKeyBinding(keyIgml);
		
		// Registering Key event handler
		FMLCommonHandler.instance().bus().register(new KeyHandler());
		
		// Putting all JsonMods in searchJsonMods list
		LibMods.searchedJsonMods = (ArrayList<JsonMod>) LibMods.jsonMods.clone();
	}
}
