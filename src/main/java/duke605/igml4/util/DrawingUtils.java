package duke605.igml4.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class DrawingUtils {

	/**
	 * Draws a scaled image to the screen
	 * 
	 * @param xCoord The x coordinate where the image will be on the screen
	 * @param yCoord The y coordinate where the image will be drawn on the screen
	 * @param xLoc The x position of the image in the image file
	 * @param yLoc The y position of the image in the image file
	 * @param xSize The width of the image in the image file
	 * @param ySize The height of the image in the image file
	 * @param scale The scaling of the image
	 * @param image The {@link ResourceLocation} of the image
	 * @param red
	 * @param green
	 * @param blue
	 */
	public static void drawScaledImage(float xCoord, float yCoord, int xLoc, int yLoc, int xSize, int ySize, float scale, ResourceLocation image, float red, float green, float blue) {	 
		Gui gui = Minecraft.getMinecraft().currentScreen;
		
		if (gui == null)
			return;
		
		GL11.glPushMatrix();
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(image);
		GL11.glColor4f(red, green, blue, 1.0F);
		GL11.glEnable(3042);
		GL11.glTranslatef(xCoord, yCoord, 0.0F);
		GL11.glScalef(scale, scale, 1.0F);
		
		gui.drawTexturedModalRect(0, 0, xLoc, yLoc, xSize, ySize);
		GL11.glPopMatrix();
	}

	/**
	 * Draws a rounded rectangle on the screen
	 * 
	 * @param xCoord The x coordinate where the rectangle will be on the screen
	 * @param yCoord The y coordinate where the rectangle will be on the screen
	 * @param xSize The width of the rectangle
	 * @param ySize The height of the rectangle
	 * @param colour The colour of the rectangle
	 * @param text The string of text that will be drawn on the rectangle
	 */
	public static void drawRoundedRect(int xCoord, int yCoord, int xSize, int ySize, int colour, String text) {
		int width = xCoord + xSize;
		int height = yCoord + ySize;
		
		// Top rounding
		Gui.drawRect(xCoord + 1, yCoord, width - 1, height, colour);
		
		// Middle rect
		Gui.drawRect(xCoord, yCoord + 1, width, height - 1, colour);

		DrawingUtils.drawCenteredUnicodeString(text, xCoord + (xSize / 2), yCoord, 0xFFFFFF);
	}
	
	/**
	 * Draws a centered unicode string on the screen
	 * 
	 * @param text The text that will be drawn to the screen
	 * @param xCoord The x position of the text
	 * @param yCoord The y position of the text
	 * @param colour The colour of the text
	 */
	public static void drawCenteredUnicodeString(String text, int xCoord, int yCoord, int colour) {
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		boolean prevFlag;
		
		// Remembering unicode flag
		prevFlag = font.getUnicodeFlag();
		
		font.setUnicodeFlag(true);
		font.drawString(text, xCoord - (font.getStringWidth(text) / 2), yCoord, colour);
		font.setUnicodeFlag(prevFlag);
	}
	
	/**
	 * Draws a scaled string
	 * 
	 * @param text The text that will be drawn
	 * @param xCoord The x position of where the text will be drawn
	 * @param yCoord The y position of where the text will be drawn
	 * @param scale The scale of the text
	 * @param colour The colour of the text
	 * @param unicodeFlag If the text should be in unicode
	 */
	public static void drawScaledString(String text, int xCoord, int yCoord, float scale, int colour, boolean unicodeFlag) {
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		boolean prevFlag;
		
		// Remembering unicode flag
		prevFlag = font.getUnicodeFlag();
		
		font.setUnicodeFlag(unicodeFlag);
		
		GL11.glPushMatrix();
		
		// Positioning text
		GL11.glTranslated(xCoord, yCoord, 0.0);
		
		// Scaling text
		GL11.glScalef(scale, scale, 1.0F);
		
		// Drawing text
		font.drawString(text, 0, 0, colour);
		GL11.glPopMatrix();
		
		font.setUnicodeFlag(prevFlag);
	}
	
	/**
	 * Draws a small unicode string on the screen that wraps
	 * 
	 * @param text The text that will be drawn on the screen
	 * @param xCoord The x position of the text
	 * @param yCoord The y position of the text
	 * @param colour The colour of the text
	 * @param width The width of the string before it wraps
	 */
	public static void drawSplitUnicodeString(String text, int xCoord, int yCoord, int colour, int width) {
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		boolean prevFlag;
		
		// Remembering unicode flag
		prevFlag = font.getUnicodeFlag();
		
		font.setUnicodeFlag(true);
		font.drawSplitString(text, xCoord, yCoord, width, colour);
		font.setUnicodeFlag(prevFlag);
	}
	
	/**
	 * Draws a small unicode string on the screen
	 * 
	 * @param text The text that will be drawn to the screen
	 * @param xCoord The x position of the text
	 * @param yCoord The y position of the text
	 * @param colour The colour of the text
	 */
	public static void drawUnicodeString(String text, int xCoord, int yCoord, int colour) {
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		boolean prevFlag;
		
		// Remembering unicode flag
		prevFlag = font.getUnicodeFlag();
		
		font.setUnicodeFlag(true);
		font.drawString(text, xCoord, yCoord, colour);
		font.setUnicodeFlag(prevFlag);
	}
	
	/**
	 * Gets the length of a string of unicode characters
	 * 
	 * @param unicodeString a String of unicode characters
	 * @return the length of the unicodeString
	 */
	public static int getUnicodeStringWidth(String unicodeString) {
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		boolean prevFlag;
		int stringLength;
		
		// Storing previous unicode flag
		prevFlag = font.getUnicodeFlag();
		
		font.setUnicodeFlag(true);
		stringLength = font.getStringWidth(unicodeString);
		font.setUnicodeFlag(prevFlag);
		
		return stringLength;
	}
}
