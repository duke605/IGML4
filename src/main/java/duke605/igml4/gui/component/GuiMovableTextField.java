package duke605.igml4.gui.component;

import java.lang.reflect.Field;

import duke605.igml4.lib.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;

public class GuiMovableTextField extends GuiTextField {

	public GuiMovableTextField(int x, int y, int width, int height) {
		super(Minecraft.getMinecraft().fontRenderer, x, y, width, height);
	}
	
	/**
	 * Updated the position and dimensions of the text field
	 * 
	 * @param xPos The new x position of the text field
	 * @param yPos The new y position of the text field
	 * @param xSize The new width of the text field
	 * @param ySize the new height of the text field
	 */
	public void updateLocationAndDimensions(int xPos, int yPos, int xSize, int ySize) {
		Field xCoord;
		Field yCoord;
		Field width;
		Field height;
		
		try {
			// Getting private fields
			xCoord = this.getClass().getSuperclass().getDeclaredFields()[1];
			yCoord = this.getClass().getSuperclass().getDeclaredFields()[2];
			width = this.getClass().getSuperclass().getDeclaredFields()[3];
			height = this.getClass().getSuperclass().getDeclaredFields()[4];
			
			// Making them the fields accessible
			xCoord.setAccessible(true);
			yCoord.setAccessible(true);
			width.setAccessible(true);
			height.setAccessible(true);
			
			// Setting the private fields
			xCoord.set(this, xPos);
			yCoord.set(this, yPos);
			width.set(this, xSize);
			height.set(this, ySize);
		} catch (Exception e) {
			System.err.println(String.format("[%s] An error occured when updating text field loactions!", Reference.MODID));
		}
	}

}
