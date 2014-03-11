package duke605.igml4.gui.component;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import duke605.igml4.lib.Reference;
import duke605.igml4.util.DrawingUtils;

public class GuiUnicodeButton extends GuiButton {
	
	public static final ResourceLocation widgets = new ResourceLocation(Reference.GUI_DOMAIN + "widgets.png");

	public boolean drawBackground;
	
	public GuiUnicodeButton(int id, int xCoord, int yCoord, int width, int height, String text) {
		super(id, xCoord, yCoord, width, height, text);
		this.drawBackground = true;
	}
	
	public void drawButton(Minecraft mc, int mouseX, int mouseY) {
		if (!(this.visible))
			return;
		
		this.field_146123_n = isHovering(mouseX, mouseY);
		drawButton(mc);
		mouseDragged(mc, mouseX, mouseY);
		
		int l = 14737632;

		if (this.packedFGColour != 0) {
			l = this.packedFGColour;
		} else if (!(this.enabled)) {
			l = 10526880;
		} else if (this.field_146123_n) {
			l = 16777120;
		}

		DrawingUtils.drawCenteredUnicodeString(displayString, xPosition + width / 2, yPosition + (height - 11), l);
	}
	
	/**
	 * Draws the actual button
	 * 
	 * @param mc The instance of minecraft
	 */
	public void drawButton(Minecraft mc) {
		int k = getHoverState(this.field_146123_n);
		
		if (!drawBackground)
			return;
		
		mc.getTextureManager().bindTexture(widgets);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(3042);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glBlendFunc(770, 771);
		drawTexturedModalRect(this.xPosition, this.yPosition, 0, k * 12, this.width / 2, this.height);
		drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - (this.width / 2), k * 12, this.width / 2,	this.height);
	}
	
	/**
	 * Checks if the mouse if hovering over the button
	 * 
	 * @param mouseX The x position of the mouse
	 * @param mouseY The y position of the mouse
	 * @return true if the mouse is hovering over the button or false if not
	 */
	public boolean isHovering(int mouseX, int mouseY) {
		return ((mouseX >= this.xPosition) && (mouseY >= this.yPosition) && (mouseX < this.xPosition + this.width) && (mouseY < this.yPosition + this.height));
	}
	
	/**
	 * Sets the button to be disabled
	 * 
	 * @param isEnabled Sets the buttons to enabled or disabled
	 * @return itself for easier constructing
	 */
	public GuiUnicodeButton setEnabled(boolean isEnabled) {
		this.enabled = isEnabled;
		return this;
	}
	
	/**
	 * Sets the background daring to enabled or disabled
	 * 
	 * @param isEnabled Sets the state of the background drawing
	 * @return itself for easier constructing
	 */
	public GuiUnicodeButton setBackgroundDrawingEnabled(boolean isEnabled) {
		this.drawBackground = isEnabled;
		return this;
	}
}
