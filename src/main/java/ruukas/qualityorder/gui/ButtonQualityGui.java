package ruukas.qualityorder.gui;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiUtils;
import ruukas.qualityorder.QualityOrder;

public class ButtonQualityGui extends GuiButtonImage{
	
	private static ResourceLocation resourceLocation = new ResourceLocation(QualityOrder.MODID, "textures/gui/button.png");

	private ItemStack stack;
	private GuiScreen target;
	private String tooltip;
	
	public ButtonQualityGui(int buttonId, int x, int y, ItemStack stack, GuiScreen target, String tooltip) {
		super(buttonId, x, y, 18, 18, 0, 0, 18, resourceLocation);
		
		this.stack = stack;
		this.target = target;
		this.tooltip =  tooltip;
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY){
		Minecraft.getMinecraft().displayGuiScreen(this.target);
	}
	
    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks){
    	super.drawButton(mc, mouseX, mouseY, partialTicks);
        if (this.visible){

            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.disableLighting();
            
            mc.getRenderItem().renderItemAndEffectIntoGUI(this.stack, this.x + 1, this.y + 1);
            
            GlStateManager.enableLighting();
            RenderHelper.disableStandardItemLighting();
            
            if(this.isMouseOver()){
                ArrayList<String> tooltipList = new ArrayList<String>();
                for(String line : this.tooltip.split("\n")){
                	tooltipList.add(TextFormatting.GREEN + line);
                }
                
                GuiScreen currentScreen = Minecraft.getMinecraft().currentScreen;
                
                GuiUtils.drawHoveringText(tooltipList, mouseX, mouseY, currentScreen.width, currentScreen.height, -1, Minecraft.getMinecraft().fontRenderer);            	
            }
        }
    }
}
