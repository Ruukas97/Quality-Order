package ruukas.qualityorder.event;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ruukas.qualityorder.QualityOrder;
import ruukas.qualityorder.config.QualityOrderConfig;
import ruukas.qualityorder.gui.ButtonGuiOpener;
import ruukas.qualityorder.gui.ButtonQualityGui;
import ruukas.qualityorder.gui.GuiBannerMaker;
import ruukas.qualityorder.gui.GuiFireworksMaker;
import ruukas.qualityorder.tabs.Tab;
import ruukas.qualityorder.tabs.TabSpawnegg;
import ruukas.qualityorder.tabs.Tabs;
import ruukas.qualityorder.util.QualityHelper;

public class QualityOrderEvents {
	public static int currentCreativeGuiPage = 0;
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
		if (eventArgs.getModID().equals(QualityOrder.MODID))
			QualityOrderConfig.syncConfig();
	}
	
	@SubscribeEvent
	public void onPostInitGui(GuiScreenEvent.InitGuiEvent.Post event) {
		for(GuiButton but : event.getButtonList()){
			if(but.id == 101 || but.id == 102){
				but.y -= 20;
			}
		}
				
		
		if (event.getGui() instanceof GuiContainerCreative) {
			GuiContainerCreative creativeGui = (GuiContainerCreative) event.getGui();
			
			int tabPage = 0;

			//	public ButtonQualityGui(int buttonId, int x, int y, int widthIn, int heightIn, int xTexStart, int yTexStart, int yDiffText, ResourceLocation resourceLocation) {
			if(tabPage == Tabs.BANNER.getTabPage()){
				Tab tab = (Tab) Tabs.BANNER;
				event.getButtonList().add(new ButtonQualityGui(250, tab.getXForDrawing(creativeGui), tab.getYForDrawing(creativeGui), tab.getIconItemStack()) {
					
					@Override
					public void mouseReleased(int mouseX, int mouseY) {
						creativeGui.mc.displayGuiScreen(new GuiBannerMaker(creativeGui.mc.player));
					}
				});
			}
			
			if(QualityOrderConfig.shouldAddEggTab && creativeGui.getSelectedTabIndex() == Tabs.EGGS.getTabIndex()){
				event.getButtonList().add(TabSpawnegg.generateTameButton());
			}else if(QualityOrderConfig.shouldAddBannersTab && creativeGui.getSelectedTabIndex() == Tabs.BANNER.getTabIndex()){
				event.getButtonList().add(new ButtonGuiOpener(new GuiBannerMaker(Minecraft.getMinecraft().player), 110, event.getGui().width/2-195/2-50-5, event.getGui().height/2-136/2, 50, 20, "Banner Gui"));
			}/*else if(CreativeTabs.MISC != null && creativeGui.getSelectedTabIndex() == Tabs.MISC.getTabIndex()){
				event.getButtonList().add(new ButtonGuiOpener(new GuiGive(), 110, event.getGui().width/2-195/2-50-5, event.getGui().height/2-136/2, 50, 20, "Give Gui"));
			}*/else if(QualityOrderConfig.shouldAddFireworkTab && creativeGui.getSelectedTabIndex() == Tabs.FIREWORK.getTabIndex()){
				event.getButtonList().add(new ButtonGuiOpener(new GuiFireworksMaker(Minecraft.getMinecraft().player), 110, event.getGui().width/2-195/2-50-5, event.getGui().height/2-136/2, 50, 20, "Firework Gui"));
				//event.getButtonList().add(new ButtonGuiOpener(new GuiQualityCrafting(), 110, event.getGui().width/2-195/2-50-5, 52 + event.getGui().height/2-136/2, 50, 20, "Crafting"));
			}/*else if(creativeGui.getSelectedTabIndex() == QualityOrderTabs.ARMORSTAND.getTabIndex()){
				event.getButtonList().add(new ButtonGuiOpener(new GuiCreativeChestOpener(), 110, event.getGui().width/2-195/2-50-5, event.getGui().height/2-136/2, 50, 20, "Banner Gui"));
			}*/
		}
	}
	
	@SubscribeEvent
	public void onButtonPressed(GuiScreenEvent.ActionPerformedEvent.Pre event) {
		if(!event.getButton().enabled){
			return;	
		}
		
		/*if (event.getButton() == TabSpawnegg.tameButton) {
			TabSpawnegg.isTame = !TabSpawnegg.isTame;
			event.getGui().setWorldAndResolution(Minecraft.getMinecraft(), event.getGui().width, event.getGui().height);
		}else{
			
			if(event.getGui() instanceof GuiContainerCreative){
                if(event.getButton().id == 101 && event.getButton().displayString.equals("<")){
                	currentCreativeGuiPage--;
                }else if(event.getButton().id == 102 && event.getButton().displayString.equals(">")){
                	currentCreativeGuiPage++;                	
                }
				
				//buttonList.add(new GuiButton(101, guiLeft,              guiTop - 50, 20, 20, "<"));
                //buttonList.add(new GuiButton(102, guiLeft + xSize - 20, guiTop - 50, 20, 20, ">"));
			}		
		}*/
	}

	@SubscribeEvent
	public void onMouseEvent(GuiScreenEvent.MouseInputEvent.Post event) {
		if (event.getGui() instanceof GuiContainerCreative) {
			GuiContainerCreative creativeGui = (GuiContainerCreative) event.getGui();
			if (creativeGui.getSlotUnderMouse() != null) {
				Slot slot = creativeGui.getSlotUnderMouse();
				if (QualityOrderConfig.shouldAddBannersTab && creativeGui.getSelectedTabIndex() == Tabs.BANNER.getTabIndex()) {
					if (slot.inventory.equals(creativeGui.mc.player.inventory) && slot.getHasStack()
							&& slot.getStack().getItem().equals(Items.BANNER)) {
						creativeGui.initGui();
					}

				}
				else if (QualityOrderConfig.shouldAddFireworkTab && creativeGui.getSelectedTabIndex() == Tabs.FIREWORK.getTabIndex()) {
					if (slot.inventory.equals(creativeGui.mc.player.inventory) && slot.getHasStack()) {
						creativeGui.initGui();
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onKeyboardEvent(GuiScreenEvent.KeyboardInputEvent.Post event) {
		if(!QualityOrderConfig.shouldAddSkullsTab){
			return;
		}
		
		if (event.getGui() instanceof GuiContainerCreative) {
			//GuiContainerCreative creativeGui = (GuiContainerCreative) event.getGui();
			/*if (creativeGui.getSelectedTabIndex() == Tabs.SKULLS.getTabIndex()) {
				GuiTextField searchTextField = null;

				try {
					Field f = ReflectionHelper.findField(creativeGui.getClass(),
							new String[] { "searchField", "field_147062_A" });
					f.setAccessible(true);
					searchTextField = (GuiTextField) f.get(creativeGui); // IllegalAccessException
					creativeGui.initGui();
					f.set(creativeGui, searchTextField);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (UnableToFindFieldException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

				if (searchTextField != null) {
					TabSkull.searchSkull = TabSkull.generateSearchSkull(searchTextField.getText());
				}
			}*/
		}
	}

	@SubscribeEvent
	public void updateTooltip(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		Item item = event.getItemStack().getItem();
		List<String> tooltip = event.getToolTip();
		
		boolean isAdvanced = event.getFlags().isAdvanced();

		if (item instanceof ItemFood) {
			ItemFood food = (ItemFood) item;
			tooltip.add(TextFormatting.GOLD + "Food points: " + food.getHealAmount(event.getItemStack()));
			if (isAdvanced) {
				tooltip.add(TextFormatting.GOLD + "Saturation modifier: " + ItemStack.DECIMALFORMAT.format(food.getSaturationModifier(event.getItemStack())));

				tooltip.add(TextFormatting.GOLD + "Quality: " + ItemStack.DECIMALFORMAT.format(QualityHelper.getFoodQuality(event.getItemStack().getItem())));
			}
		} else if (item == Items.CAKE) {
			tooltip.add(TextFormatting.GOLD + "Food points: 14");
			if (isAdvanced) {
				tooltip.add(TextFormatting.GOLD + "Saturation modifier: " + ItemStack.DECIMALFORMAT.format(0.1d));
				tooltip.add(TextFormatting.GOLD + "Quality: " + ItemStack.DECIMALFORMAT.format(16.8d));
			}
		} else if (item == Items.ARMOR_STAND) {
			tooltip.add(TextFormatting.DARK_PURPLE + "Quality Order info:");
			if (stack.hasTagCompound() && stack.getTagCompound().hasKey("EntityTag", Constants.NBT.TAG_COMPOUND)) {
				NBTTagCompound entityTag = stack.getTagCompound().getCompoundTag("EntityTag");

				if (entityTag.getByte("Small") == 1) {
					tooltip.add(TextFormatting.GOLD + "Small");
				} else {
					tooltip.add(TextFormatting.GOLD + "Big");
				}

				if (entityTag.getByte("ShowArms") == 1) {
					tooltip.add(TextFormatting.GOLD + "Arms");
				} else {
					tooltip.add(TextFormatting.GOLD + "No Arms");
				}

				if (entityTag.getByte("NoBasePlate") == 1) {
					tooltip.add(TextFormatting.GOLD + "No Base Plate");
				} else {
					tooltip.add(TextFormatting.GOLD + "Base Plate");
				}

				if (stack.getTagCompound().getBoolean("QOArmor")) {
					tooltip.add(TextFormatting.ITALIC + "" + TextFormatting.GOLD + "Armor");
				} else {
					tooltip.add(TextFormatting.ITALIC + "" + TextFormatting.GOLD + "No Armor");
				}
			} else {
				tooltip.add(TextFormatting.ITALIC + "Default Armor Stand");
				tooltip.add("Big");
				tooltip.add("No Arms");
				tooltip.add("Base Plate");
				tooltip.add(TextFormatting.ITALIC + "No Armor");
			}	
		}else if(item == Items.DYE){
			tooltip.add(1, "" + QualityHelper.getTextFormatFromDye(EnumDyeColor.byDyeDamage(stack.getItemDamage())) + EnumDyeColor.byDyeDamage(stack.getItemDamage()));
		}else if(item == Items.SKULL){
			if(stack.hasTagCompound() && stack.getTagCompound().hasKey("SkullOwner", Constants.NBT.TAG_COMPOUND)){
				if(stack.getTagCompound().getCompoundTag("SkullOwner").hasKey("Name", Constants.NBT.TAG_STRING)){
					tooltip.add(1, TextFormatting.BLUE + stack.getTagCompound().getCompoundTag("SkullOwner").getString("Name"));
				}else{
					tooltip.add(1, TextFormatting.GRAY + "Unavailable");
				}
			}
		}else if(item == Items.NAME_TAG){
			for(String[] specialNameArray : TabSpawnegg.specialMobNames){
				if (stack.getDisplayName() == specialNameArray[0]) {
					tooltip.add(TextFormatting.GOLD + specialNameArray[1]);
				}
			}
		}else if(QualityHelper.getStackDPS(stack) > 0){
			tooltip.add(TextFormatting.GOLD + "DPS: " + ItemStack.DECIMALFORMAT.format(QualityHelper.getStackDPS(stack)));
			if(item == QualityHelper.getStrongestSword()){
				tooltip.add("Strongest Sword");
			}
		}

		if (isAdvanced) {
			if (item instanceof ItemBlock) {
				event.getToolTip().add(TextFormatting.DARK_GRAY + "Class: "
						+ ((ItemBlock) item).getBlock().getClass().getSimpleName());
			} else {
				event.getToolTip().add(TextFormatting.DARK_GRAY + "Class: "
						+ event.getItemStack().getItem().getClass().getSimpleName());
			}
			/*if(stack.hasTagCompound()){
				if(stack.getTagCompound().hasKey("EntityTag", Constants.NBT.TAG_COMPOUND)){
					tooltip.add(TextFormatting.DARK_GRAY + "EntityTag:" + stack.getTagCompound().getCompoundTag("EntityTag"));
				}
				/*if(item == Items.SKULL){
					tooltip.add(stack.getTagCompound().toString());
				}*/
			//}
		}
	}
}
