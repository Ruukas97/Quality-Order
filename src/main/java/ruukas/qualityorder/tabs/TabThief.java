package ruukas.qualityorder.tabs;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.event.HoverEvent.Action;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.ReflectionHelper.UnableToFindFieldException;
import ruukas.qualityorder.config.QualityOrderConfig;
import ruukas.qualityorder.util.QualityBookHelper;

public class TabThief extends Tab {

	public TabThief(String label) {
		super(label);
	}

	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> stackList) {
		if (QualityOrderConfig.thievingRange > 0) {
			AxisAlignedBB axisalignedbb = Minecraft.getMinecraft().player.getEntityBoundingBox().expand(15, 15, 15);
			List<EntityLivingBase> entityList = Minecraft.getMinecraft().world.<EntityLivingBase> getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);

			for (EntityLivingBase entity : entityList) {
				if (entity == Minecraft.getMinecraft().player) continue;
				if (entity instanceof EntityPlayer) {
					EntityPlayer playerToStealFrom = (EntityPlayer) entity;
					boolean addedNote = false;
					for (ItemStack stack : playerToStealFrom.getEquipmentAndArmor()) {
						if (stack != null && stack != ItemStack.EMPTY) {
							boolean shouldAdd = true;
							for (ItemStack stackInList : stackList) {
								if (ItemStack.areItemStacksEqual(stack, stackInList)) {
									shouldAdd = false;
									break;
								}
							}
							if (shouldAdd) {
								if(!addedNote){
									stackList.add(QualityBookHelper.generateNote("Stolen from " + playerToStealFrom.getName(), new String[]{}));
									addedNote = true;
								}
								stackList.add(stack);
							}
						}
					}
				}
			}
		}

		for (ChatLine line : getChatLines()) {
			for (ITextComponent comp : line.getChatComponent()) {
				Style style = comp.getStyle();
				if (style != null && style.getHoverEvent() != null && style.getHoverEvent().getAction() == Action.SHOW_ITEM) {
					ItemStack stack = ItemStack.EMPTY;

					try {
						NBTBase nbtbase = JsonToNBT.getTagFromJson(style.getHoverEvent().getValue().getUnformattedText());

						if (nbtbase instanceof NBTTagCompound) {
							stack = new ItemStack((NBTTagCompound) nbtbase);
						}
					}
					catch (NBTException var11) {
					}

					if (!stack.isEmpty()) {
						boolean shouldAdd = true;
						for (ItemStack stackInList : stackList) {
							if (ItemStack.areItemStacksEqual(stack, stackInList)) {
								shouldAdd = false;
								break;
							}
						}
						if(shouldAdd){
							stackList.add(stack);
						}
					}
				}
			}
		}

		if (stackList.isEmpty()) {
			stackList.add(QualityBookHelper.getThiefGuide());
		}
	}

	@SuppressWarnings("unchecked")
	@Nullable
	public static List<ChatLine> getChatLines() {
		List<ChatLine> chatLine = null;

		try {
			Field chatLinesField = ReflectionHelper.findField(GuiNewChat.class, new String[] { "chatLines", "field_146252_h" });
			// Useful source for getting SRG names: mcpbot.bspk.rs
			// field_146252_h,chatLines,0,Chat lines to be displayed in the chat
			// box
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(chatLinesField, chatLinesField.getModifiers() & ~Modifier.FINAL);

			chatLine = (List<ChatLine>) chatLinesField.get(Minecraft.getMinecraft().ingameGUI.getChatGUI());
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (UnableToFindFieldException e) {
			e.printStackTrace();
		}
		catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		catch (SecurityException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return chatLine;
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Items.FEATHER);
	}
}
