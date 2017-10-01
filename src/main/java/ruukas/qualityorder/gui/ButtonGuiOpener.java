package ruukas.qualityorder.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class ButtonGuiOpener extends GuiButton{
	GuiScreen guiToOpen;

	public ButtonGuiOpener(GuiScreen gui, int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
		guiToOpen = gui;
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY) {
		Minecraft.getMinecraft().displayGuiScreen(guiToOpen);
		super.mouseReleased(mouseX, mouseY);
	}	
	
	/*@Override
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		Minecraft.getMinecraft().displayGuiScreen(guiToOpen);
		return super.mousePressed(mc, mouseX, mouseY);
	}*/
}
