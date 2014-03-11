package duke605.igml4.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

import duke605.igml4.gui.component.GuiUnicodeButton;
import duke605.igml4.util.DrawingUtils;

public class GuiHelp extends GuiScreen {

	public GuiModList parentGui;
	public int helpID;
	
	public GuiHelp(GuiModList parentGui) {
		this.parentGui = parentGui;
		this.helpID = -1;
	}
	
	@SuppressWarnings("unchecked")
	public void initGui() {
		buttonList.clear();
		
		buttonList.add(new GuiUnicodeButton(0, 5, height - 17, 100, 12, "<-- back"));
		buttonList.add(new GuiUnicodeButton(1, 5, height - 30, 100, 12, "Searching").setEnabled(helpID != 0));
	}
	
	public void drawScreen(int mouseX, int mouseY, float noIdea) {
		this.drawBackground(0);
		
		// Drawing HELP at the top of the screen
		DrawingUtils.drawScaledString("Help", 5, 5, 1.5F, 0xE6E600, false);
		
		// Drawing the help text
		DrawingUtils.drawSplitUnicodeString(getHelpText(), 5, 18, 0xFFFFFF, width - 10);
		
		super.drawScreen(mouseX, mouseY, noIdea);
	}
	
	protected void actionPerformed(GuiButton b) {
		 int id = b.id;
		 
		 // Returns to the modlist gui
		 if (id == 0) {
			 mc.displayGuiScreen(parentGui);
			 parentGui.enableRepeats();
		 }
		 
		 // Setting help to search help id
		 if (id == 1)
			 helpID = 0;
	}
	
	public void keyTyped(char c, int keyCode) {
		
		// Returns to the modlist gui
	    if (keyCode == Keyboard.KEY_ESCAPE) {
	    	 mc.displayGuiScreen(parentGui);
			 parentGui.enableRepeats();
	    }
	}
	
	private String getHelpText() {
		if (helpID == 0)
			return "There are three ways to search for what you want. \u00a77Simple searching\u00a7f, which is when you type "
					+ "in a string and all mods will be search for that string. \u00a77Or searching\u00a7f, which is like simple searching "
					+ "but you can search for multiple strings at a time by using the pipe character (Ex. \"duke605|igml|1.7.2\" will find"
					+ "any mods with the text duke605, igml, OR 1.7.2 in it). \u00a77And searching\u00a7f, which is like or searching but the mod must contain "
					+ "all strings being searched for (Ex. \"duke605&igml&1.7.2\" will find all mods contain the text duke605, igml, AND 1.7.2)";

		return "Hello!\n"
				+ "Here you can find documentation on how to use IGML4. Use the buttons below to find the help that you "
				+ "are looking for or use the back button to go back to the mod list gui. If a topic that you are having "
				+ "trouble with is not covered in this help gui, please contact me through one of the ways listed;"
				+ "\n - Use minecraft forums messaging, my fourm name is duke605"
				+ "\n - Use IRC, GrygerFlzer or ZeroLevels my also be able to help"
				+ "\n - Use my site's comment system, duke605.x10.mx";
	}
}
