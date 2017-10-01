package ruukas.qualityorder.tabs;

import java.util.List;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import ruukas.qualityorder.util.nbt.itemstack.ItemStackTag.DisplayTag;

public class TabCustom extends Tab {

	protected String customLabel;
	protected String[] tabItems;

	public TabCustom(String customTabName, String[] customTabItems) {
		super(customTabName);
		this.customLabel = customTabName;
		this.tabItems = customTabItems;
	}

	@Override
	public String getTranslatedTabLabel() {
		return customLabel;
	}

	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> stackList) {
		addItemsFromStringArray(stackList, tabItems);
	}

	public static ItemStack getErrorNote(String errorString) {
		ItemStack stack = new ItemStack(Items.PAPER);
		stack.setStackDisplayName(TextFormatting.RED + "Error");
		DisplayTag dTag = new DisplayTag(stack.getOrCreateSubCompound(DisplayTag.getKeyName()));
		dTag.addLore(errorString);
		stack.getTagCompound().setTag(DisplayTag.getKeyName(), dTag);
		return stack;
	}

	@Override
	public ItemStack getTabIconItem() {
		if (tabItems != null && tabItems.length > 0) {
			ResourceLocation resourcelocation = new ResourceLocation(tabItems[0]);
			Item item = (Item) Item.REGISTRY.getObject(resourcelocation);

			if (item != null) {
				return new ItemStack(item);
			}
		}
		return new ItemStack(Blocks.STONE);
	}

	public static void addItemsFromStringArray(List<ItemStack> stackList, String[] tabItems) {
		if (tabItems.length < 1) {

		}
		else for (String itemString : tabItems) {
			if (itemString.startsWith("//")) {
				continue;
			}
			ItemStack stack = getItemStackFromString(itemString);
			if (stack != null) {
				stackList.add(stack);
			}
		}
	}

	public static ItemStack getItemStackFromString(String itemString) {
		ItemStack stack;
		if (itemString.length() == 0 || itemString == " ") {
			stack = getErrorNote(TextFormatting.YELLOW + "String could not be read as item.");// TODO
																								// try
																								// air
																								// or
																								// 0
		}
		else {
			itemString = itemString.replaceAll("&", "§");
			String itemArguments[] = itemString.split(" ");

			ResourceLocation resourcelocation = new ResourceLocation(itemArguments[0]);

			Item item = (Item) Item.REGISTRY.getObject(resourcelocation);

			if (item != null) {
				if (itemArguments.length > 1) {
					int i;
					boolean noNumber = false;
					try {
						i = Integer.parseInt(itemArguments[1]);
					}
					catch (NumberFormatException e) {
						i = 0;
						noNumber = true;
					}
					if ((i < 0) || (item instanceof ItemBlock && i > 15) || (i > 32767)) {
						i = 0;
					}

					stack = new ItemStack(item, 1, i);

					if (itemArguments.length > 2) {
						String nbtString = itemArguments[noNumber ? 1 : 2];
						if (itemArguments.length > 3) {
							for (int j = 3; j < itemArguments.length; j++) {
								nbtString = nbtString + " " + itemArguments[j];
							}
						}

						try {
							stack.setTagCompound(JsonToNBT.getTagFromJson(nbtString));
						}
						catch (NBTException nbtexception) {
							stack = getErrorNote(TextFormatting.YELLOW + nbtexception.getLocalizedMessage());
						}
					}
				}
				else {
					stack = new ItemStack(item);
				}
			}
			else {// if item is null
				stack = getErrorNote(TextFormatting.YELLOW + "\"" + resourcelocation.toString() + "\" was not recognized.");
			}
		}
		return stack;
	}
}
