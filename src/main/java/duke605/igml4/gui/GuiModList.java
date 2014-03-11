package duke605.igml4.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import duke605.igml4.IGML4;
import duke605.igml4.gui.component.GuiMovableTextField;
import duke605.igml4.gui.component.GuiUnicodeButton;
import duke605.igml4.handler.SearchHandler;
import duke605.igml4.lib.LibMods;
import duke605.igml4.lib.Reference;
import duke605.igml4.util.DrawingUtils;

public class GuiModList extends GuiScreen {
	
	public static final ResourceLocation icon = new ResourceLocation(Reference.GUI_DOMAIN + "open_source_icon.png");

	public int scroll;
	public int maxButtons;
	
	/** Search options */
	public GuiMovableTextField searchAll;
	
	public GuiModList() {
		this.scroll = 0;
		
		// Setting up searchAll field
		this.searchAll = new GuiMovableTextField(5, height - 17, width - 10, 12);
		this.searchAll.setMaxStringLength(80);
		this.searchAll.setEnableBackgroundDrawing(true);
		this.searchAll.setFocused(true);
		this.searchAll.setCanLoseFocus(false);
		
		enableRepeats();
	}
	
	@SuppressWarnings("unchecked")
	public void initGui() {
		buttonList.clear();
		
		// Moving searchAll into correct potition
		searchAll.updateLocationAndDimensions(5, height - 17, width - 10, 12);
		
		// Calculating how many buttons should fit on the screen
		maxButtons = (int) Math.floor(((height - 50.0) / 13.0));
		
		// Updating scroll if list was scrolled all the way down
		// and needs updating to decrement scroll
		for (;scroll > 0 && LibMods.searchedJsonMods.size() < (scroll + maxButtons);scroll--);

		// Adding JSON mod buttons to buttonList
		for (int i = scroll, count = 0;i < LibMods.searchedJsonMods.size() && count < maxButtons;count++, i++) {
			buttonList.add(new GuiUnicodeButton(i, 5, 5 + (count * 13), 200, 12, LibMods.searchedJsonMods.get(i).name));
		}
		
		// Adding extra buttons
		buttonList.add(new GuiUnicodeButton(-1, width - 105, height - 32, 100, 12, IGML4.showDetails ? "Hide details" : "Show details"));
		buttonList.add(new GuiUnicodeButton(-2, width - 105, height - 45, 100, 12, "Help"));
	}
	
	protected void keyTyped(char c, int keyCode) {
		// Is escape pressed close gui
		if (keyCode == Keyboard.KEY_ESCAPE) {
			super.keyTyped(c, keyCode);
		}
		
		// If up key pressed decrement scroll
		else if (keyCode == Keyboard.KEY_UP) {
			if (scroll > 0) scroll--;
			initGui();
		}
		
		// If down key pressed increase scroll
		else if (keyCode == Keyboard.KEY_DOWN) {
			scroll++;
			initGui();
		}
		
		// If page up key pressed decrement scroll by maxButtons
		else if (keyCode == 201) {
			scroll -= maxButtons;
			if (scroll < 0) scroll = 0;
			initGui();
		}

		// If page down key pressed increase scroll by maxButtons
		else if (keyCode == 209) {
			scroll += maxButtons;
			initGui();
		}
		
		// Sending key input to search text field
		else {
			searchAll.textboxKeyTyped(c, keyCode);
			SearchHandler.search(searchAll.getText());
			initGui();
		}
	}
	
	protected void actionPerformed(GuiButton b) {
		 int id = b.id;
		 
		 // Go to gui for mod
		 if (id >= 0) {
			 mc.displayGuiScreen(new GuiMod(this, LibMods.searchedJsonMods.get(id)));
		 }
		 
		 // Show/hides details beside the button
		 else if (b.id == -1) {
			 IGML4.showDetails = !IGML4.showDetails;
			 initGui();
		 }
		 
		 // Showing help gui
		 else if (b.id == -2)
			 mc.displayGuiScreen(new GuiHelp(this));
	}
	
	public void handleMouseInput() {
		super.handleMouseInput();
		
		if (!Mouse.hasWheel())
			return;

		// Called when scroll wheel is scrolled up
		if (Mouse.getEventDWheel() > 0) {
			scroll--;
			if (scroll < 0) scroll = 0;
			initGui();
			
		}
		
		// Called when scroll wheel is scrolled down
		else if (Mouse.getEventDWheel() < 0) {
			scroll++;
			initGui();
		}
	}
	
	public void drawScreen(int mouseX, int mouseY, float par3) {
		this.drawBackground(0);
		super.drawScreen(mouseX, mouseY, par3);	
		this.drawIcons();
		
		// Drawing text above search filed
		drawString(mc.fontRenderer, "Search:", 5, height - 29, 0xFFFFFF);
		
		// Drawing searchAll text field
		this.searchAll.drawTextBox();
	}
	
	public void updateScreen() {
		
		// Makes the insertion point cursor blink
		searchAll.updateCursorCounter();
	}
	 
	/**
	 * Used to draw icons beside the mod buttons
	 */
	public void drawIcons() {
		GuiButton b;
		int xShift;
		int colour;
		String[] tempArray;
		
		for (Object o : buttonList) {
			b = (GuiButton) o;
			
			// Continuing if button is not a mod button
			if (b.id < 0)
				continue;
			
			// Setting xShift to beside the button
			xShift = b.xPosition + b.getButtonWidth() + 1;
			
			// Drawing globe if mod is open source
			if (LibMods.searchedJsonMods.get(b.id).source != null) {
				DrawingUtils.drawScaledImage(xShift, b.yPosition + 1, 0, 0, 29, 29, 0.3448275862068966F, icon, 0F, 0.6F, 0.8F);
				xShift += 12;
			}
			
			// Stops rendering if the user doesn't want to see the details
			if (!IGML4.showDetails)
				continue;
			
			// Getting type
			tempArray = LibMods.searchedJsonMods.get(b.id).type;
			
			// Drawing rounded rectangles for types
			for (String s : tempArray) {
				DrawingUtils.drawRoundedRect(xShift, b.yPosition + 2, DrawingUtils.getUnicodeStringWidth(s) + 6, 8, 0xFFB2B2B2, s);
				xShift += DrawingUtils.getUnicodeStringWidth(s) + 8;
			}
			
			// Getting dependencies
			tempArray = LibMods.searchedJsonMods.get(b.id).dependencies;
			
			// Continuing if dependency array is null (Really should never be)
			if (tempArray == null)
				continue;
			
			// Drawing rounded rectangles for dependencies
			for (String s : tempArray) {
				// Breaking if no room left on screen
				if (xShift + DrawingUtils.getUnicodeStringWidth(s) + 6 > width)
					break;
				
				// Getting colour based on dependency
				if (s.equalsIgnoreCase("forge required"))
					colour = 0xFF00B800;
				else if (s.equalsIgnoreCase("forge compatible"))
					colour = 0xFF002EB8;
				else if (s.equalsIgnoreCase("not forge compatible") || s.equalsIgnoreCase("base edit"))
					colour = 0xFFE60000;
				else
					colour = 0xFFCCA300;
				
				DrawingUtils.drawRoundedRect(xShift, b.yPosition + 2, DrawingUtils.getUnicodeStringWidth(s) + 6, 9, colour, s);
				xShift += DrawingUtils.getUnicodeStringWidth(s) + 8;
			}
		}
	}
	
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}
	
	/**
	 * Called when IGML4 key is pressed or when returns from a mod gui
	 */
	public void enableRepeats() {
		Keyboard.enableRepeatEvents(true);
	}
}
