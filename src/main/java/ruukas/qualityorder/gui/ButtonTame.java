package ruukas.qualityorder.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class ButtonTame extends GuiButton{
	public static boolean bool = false;

	public ButtonTame(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY) {
		bool = !bool;
		Minecraft.getMinecraft().currentScreen.initGui();
	}	
}
