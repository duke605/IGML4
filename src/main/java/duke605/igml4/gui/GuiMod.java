package duke605.igml4.gui;

import java.util.Arrays;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import duke605.igml4.gui.component.GuiUnicodeButton;
import duke605.igml4.lib.JsonMod;
import duke605.igml4.util.DrawingUtils;
import duke605.igml4.util.MiscUtils;

public class GuiMod extends GuiScreen {

	public final GuiModList parentGui;
	public final JsonMod mod;
	
	public GuiMod(GuiModList parentGui, JsonMod mod) {
		this.parentGui = parentGui;
		this.mod = mod;
	}
	
	@SuppressWarnings("unchecked")
	public void initGui() {
		buttonList.clear();
		
		buttonList.add(new GuiUnicodeButton(0, 5, height - 17, 100, 12, "<-- Back"));
		buttonList.add(new GuiUnicodeButton(1, 5, height - 30, 100, 12, "Mod page"));
		
		// Adding button if mod has source
		if (mod.source != null)
			buttonList.add(new GuiUnicodeButton(2, 5, height - 43, 100, 12, "Source"));
	}
	
	public void drawScreen(int mouseX, int mouseY, float noIdea) {
		this.drawBackground(0);
		
		DrawingUtils.drawScaledString(mod.name, 5, 5, 1.5F, mod.source == null ? 0xE6E600 : 0x0099FF, false);
		DrawingUtils.drawUnicodeString("\u00a77Author(s):\u00a7f " + Arrays.toString(mod.author).replaceAll("[\\[\\]]", ""), 5, 18, 0xFFFFFF);
		DrawingUtils.drawUnicodeString("\u00a77Version(s):\u00a7f " + Arrays.toString(mod.versions).replaceAll("[\\[\\]]", ""), 5, 27, 0xFFFFFF);
		DrawingUtils.drawUnicodeString("\u00a77Type(s):\u00a7f " + Arrays.toString(mod.type).replaceAll("[\\[\\]]", ""), 5, 35, 0xFFFFFF);
		DrawingUtils.drawUnicodeString("\u00a77Dependencies:\u00a7f " + Arrays.toString(mod.dependencies).replaceAll("[\\[\\]]", ""), 5, 43, 0xFFFFFF);
		DrawingUtils.drawSplitUnicodeString("\u00a77Description:\u00a7f " + mod.getDesc(), 5, 52, 0xFFFFFF, width - 10);
		
		if (mod.source != null)
			if (mod.name.equals("Project: Red"))
				DrawingUtils.drawScaledImage(5 + (int) Math.ceil(mc.fontRenderer.getStringWidth(mod.name) * 1.5) + 2, 3, 0, 0, 29, 29, .5F, GuiModList.icon, .95F, 0.0F, 0.0F);
			else
				DrawingUtils.drawScaledImage(5 + (int) Math.ceil(mc.fontRenderer.getStringWidth(mod.name) * 1.5) + 2, 3, 0, 0, 29, 29, .5F, GuiModList.icon, 0F, 0.6F, 0.8F);
				
		super.drawScreen(mouseX, mouseY, noIdea);
	}
	
	public void actionPerformed(GuiButton b) {
		 int id = b.id;
		 
		 // Returns to the modlist gui
		 if (id == 0) {
			 mc.displayGuiScreen(parentGui);
			 parentGui.enableRepeats();
		 }
		 
		 // Opens browser to mod link
		 else if (id == 1)
			 MiscUtils.openSite(mod.link);
		 
		 // Opens browser to mod source
		 else if (id == 2)
			 MiscUtils.openSite(mod.source);
	}
	
	public void keyTyped(char c, int keyCode) {
		
		// Returns to the modlist gui
	    if (keyCode == Keyboard.KEY_ESCAPE) {
	    	 mc.displayGuiScreen(parentGui);
			 parentGui.enableRepeats();
	    }
	}
}
