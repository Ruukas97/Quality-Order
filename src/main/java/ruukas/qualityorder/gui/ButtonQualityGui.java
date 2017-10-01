package ruukas.qualityorder.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import ruukas.qualityorder.QualityOrder;

public abstract class ButtonQualityGui extends GuiButtonImage{
	
	private static ResourceLocation resourceLocation = new ResourceLocation(QualityOrder.MODID, "textures/gui/button.png");

	private ItemStack stack;
	
	public ButtonQualityGui(int buttonId, int x, int y, ItemStack stack) {
		super(buttonId, x, y, 20, 20, 0, 0, 20, resourceLocation);
		
		this.stack = stack;
	}
	
	@Override
	public abstract void mouseReleased(int mouseX, int mouseY);
	
    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks){
    	//super.drawButton(mc, mouseX, mouseY, partialTicks);
        if (this.visible){

            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.disableLighting();
            
            mc.getRenderItem().renderItemAndEffectIntoGUI(stack, this.x + 2, this.y + 2);
            
            GlStateManager.enableLighting();
            RenderHelper.disableStandardItemLighting();

        }
    }
}
