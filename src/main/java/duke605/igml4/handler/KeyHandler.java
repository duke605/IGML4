package duke605.igml4.handler;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import duke605.igml4.IGML4;
import duke605.igml4.gui.GuiModList;

public class KeyHandler {

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onKeyFired(KeyInputEvent event) {
		if (IGML4.keyIgml.isPressed())
			Minecraft.getMinecraft().displayGuiScreen(new GuiModList());	
	}
}
