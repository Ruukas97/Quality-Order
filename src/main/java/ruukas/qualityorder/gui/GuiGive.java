package ruukas.qualityorder.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.CreativeCrafting;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ruukas.qualityorder.tabs.TabCustom;

@SideOnly(Side.CLIENT)
public class GuiGive extends GuiScreen
{
    /** Text field containing the command block's command. */
    private GuiTextField itemTextField;
    private GuiTextField errorTextField;
    /** "Done" button for the GUI. */
    private GuiButton giveButton;
    
    private CreativeCrafting listener;


    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen(){
        this.itemTextField.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui(){
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.giveButton = this.addButton(new GuiButton(0, this.width / 2 - 150, this.height / 4 + 120 + 12, 300, 20, "Give"));
        this.itemTextField = new GuiTextField(2, this.fontRenderer, this.width / 2 - 150, 50, 300, 20);
        this.itemTextField.setMaxStringLength(32500);
        this.itemTextField.setFocused(true);
        this.errorTextField = new GuiTextField(3, this.fontRenderer, this.width / 2 - 150, 135, 300, 20);
        this.errorTextField.setMaxStringLength(32500);
        this.errorTextField.setEnabled(false);
        this.errorTextField.setText("-");
        this.giveButton.enabled = true;
        
        this.listener = new CreativeCrafting(this.mc);
        this.mc.player.inventoryContainer.addListener(this.listener);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed(){
        Keyboard.enableRepeatEvents(false);
        
        if (this.mc.player != null && this.mc.player.inventory != null)
        {
            this.mc.player.inventoryContainer.removeListener(this.listener);
        }
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException{
        if (button.enabled && !itemTextField.getText().isEmpty()){
        	if (button.id == 0){
        		ItemStack stack = TabCustom.getItemStackFromString(itemTextField.getText());
        		if(stack.getItem() == Items.PAPER && stack.getDisplayName().toLowerCase().contains("error")){
        			if(stack.hasTagCompound()){
        				if(stack.getTagCompound().hasKey("display", Constants.NBT.TAG_COMPOUND)){
        					if(stack.getTagCompound().getCompoundTag("display").hasKey("Lore", Constants.NBT.TAG_LIST)){
        						if(!stack.getTagCompound().getCompoundTag("display").getTagList("Lore", Constants.NBT.TAG_STRING).hasNoTags()){
        							String str = ""+stack.getTagCompound().getCompoundTag("display").getTagList("Lore", Constants.NBT.TAG_STRING).get(0);
        							str = str.replaceAll("\\\\", "");
        							str = str.replaceAll(TextFormatting.YELLOW+"", "");
        							errorTextField.setText(str);
        						}
        					}
        				}
        			}
        		}else{
            		int slot = Minecraft.getMinecraft().player.inventory.getFirstEmptyStack();
            		if(slot != -1){
                        this.mc.player.inventory.setInventorySlotContents(slot, stack);
                        this.mc.player.inventoryContainer.detectAndSendChanges();
                        errorTextField.setText("Successfully gave " + stack.getDisplayName() + ".");
            		}else{
            			errorTextField.setText("Couldn't find an empty slot in your inventory.");
            		}
        		}
            }
        }
    }
    
    

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException{
        this.itemTextField.textboxKeyTyped(typedChar, keyCode);

        if (keyCode != 28 && keyCode != 156){
            if (keyCode == 1){
                this.mc.displayGuiScreen(new GuiContainerCreative(Minecraft.getMinecraft().player));
            }
        }
        else{
            this.actionPerformed(this.giveButton);
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.itemTextField.mouseClicked(mouseX, mouseY, mouseButton);
        this.errorTextField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, "Quality Order - Give Gui", this.width / 2, 20, 16777215);
        this.drawString(this.fontRenderer, "Item to give", this.width / 2 - 150, 37, 10526880);
        this.itemTextField.drawTextBox();
        int i = 75;
        int j = 0;
        this.drawString(this.fontRenderer, "Using this gui, you can spawn items for yourself.", this.width / 2 - 150, i + j++ * this.fontRenderer.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRenderer, "This works similarly to the /give command.", this.width / 2 - 150, i + j++ * this.fontRenderer.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRenderer, "<item> [data] [dataTag]", this.width / 2 - 150, i + j++ * this.fontRenderer.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRenderer, "Please be aware that Raw JSON text does not work.", this.width / 2 - 150, i + j++ * this.fontRenderer.FONT_HEIGHT, 10526880);
        this.drawString(this.fontRenderer, "", this.width / 2 - 150, i + j++ * this.fontRenderer.FONT_HEIGHT, 10526880);

        if (!this.errorTextField.getText().isEmpty()){
            i = i + j * this.fontRenderer.FONT_HEIGHT + 1;
            this.drawString(this.fontRenderer, "Let's pray for no errors!", this.width / 2 - 150, i, 10526880);
            this.errorTextField.drawTextBox();
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}