package ruukas.qualityorder.util;

import java.util.Map.Entry;

import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import ruukas.qualityorder.QualityOrder;
import ruukas.qualityorder.tabs.TabSpawnegg;

public class QualityHelper {

	public static final boolean getIsNoteworthy() {
		for (int i = 0; i < QualityOrder.noteWorthyUsernames.length; i++) {
			if (QualityOrder.noteWorthyUsernames[i][0].equalsIgnoreCase(Minecraft.getMinecraft().player.getDisplayNameString())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Accumulates all of the armor for the particular ArmorMaterial
	 * 
	 * @param material
	 * @return
	 */
	public static int getArmorQualityFromMaterial(ArmorMaterial material) {
		int quality = 0;
		for (EntityEquipmentSlot slot : EntityEquipmentSlot.values())
			quality += material.getDamageReductionAmount(slot);
		return quality;
	}

	public static int compareBoolean(boolean bool1, boolean bool2) {
		return bool1 ? (bool2 ? 0 : -1) : (bool2 ? 1 : 0);
	}

	public static boolean getBlockInstanceof(Item item, Class<?> blockclass) {
		if (item instanceof ItemBlock) {
			return blockclass.isInstance(Block.getBlockFromItem(item));
		}
		return false;
	}

	public static boolean getIsItemEqualToBlock(Item item, Block block) {
		if (item instanceof ItemBlock) {
			return Block.getBlockFromItem(item).equals(block);
		}
		return false;
	}

	public static String getItemType(Item item) {
		return (item instanceof ItemBlock) ? ((ItemBlock) item).getBlock().getClass().getSimpleName() : item.getClass().getSimpleName();
	}

	/**
	 * Returns food quality based on how long it will last:
	 * http://minecraft.gamepedia.com/Hunger
	 * 
	 * @param item
	 * @return
	 */
	public static double getFoodQuality(Item item) {
		if (item == Items.CAKE) {
			return 16.4;
		}
		else if (item instanceof ItemFood) {
			ItemStack foodStack = new ItemStack(item);
			ItemFood food = (ItemFood) item;
			return (food.getHealAmount(foodStack) + food.getHealAmount(foodStack)) * food.getSaturationModifier(foodStack) * 2;
		}
		else return 0.0f;
	}

	public static double getStackDPS(ItemStack stack) {
		// System.out.println(stack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND));
		// System.out.println(stack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND).get("generic.attackSpeed"));
		Multimap<String, AttributeModifier> multimap = stack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND);

		// System.out.println(multimap);
		double dmg = 0;
		double as = 0;
		if (!multimap.isEmpty()) {
			for (Entry<String, AttributeModifier> entry : multimap.entries()) {
				AttributeModifier attributemodifier = (AttributeModifier) entry.getValue();

				if (attributemodifier.getID().toString().equalsIgnoreCase("FA233E1C-4180-4865-B01B-BCCE9785ACA3")) {
					as = attributemodifier.getAmount();
					if (Minecraft.getMinecraft().player != null) as += Minecraft.getMinecraft().player.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getBaseValue();// AS
				}
				else if (attributemodifier.getID().toString().equalsIgnoreCase("CB3F55D3-645C-4F38-A497-9C13A33DB5CF")) {// AD
					dmg = attributemodifier.getAmount();
					if (Minecraft.getMinecraft().player != null) dmg += Minecraft.getMinecraft().player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue();
					dmg += (double) EnchantmentHelper.getModifierForCreature(stack, EnumCreatureAttribute.UNDEFINED);
				}
			}
		}
		else {
			return 0;
		}
		return dmg * as;
	}

	/**
	 * Returns the ItemPickaxe that can be used the most times before breaking.
	 * Not considering enchantments.
	 * 
	 * @return
	 */
	public static final Item getMostDurablePickaxe() {
		Item bestPickaxe = null;
		for (Item item : Item.REGISTRY) {
			if (item == null) {
				continue;
			}
			if (item instanceof ItemPickaxe) {
				if (bestPickaxe == null || !(bestPickaxe instanceof ItemPickaxe)) {
					bestPickaxe = item;
					continue;
				}
				if (new ItemStack(item).getMaxDamage() > new ItemStack(bestPickaxe).getMaxDamage()) {
					bestPickaxe = item;
				}
			}
		}
		return bestPickaxe;
	}

	public static final Item getBestFood() {
		Item bestFood = null;
		for (Item item : Item.REGISTRY) {
			if (item == null) {
				continue;
			}
			if (item instanceof ItemFood) {
				if (bestFood == null || !(bestFood instanceof ItemFood)) {
					bestFood = item;
					continue;
				}
				if (getFoodQuality(item) > getFoodQuality(bestFood)) {
					bestFood = item;
				}
			}
		}
		return bestFood;
	}

	/**
	 * Returns the ItemSword that has deals the highest amount of damage. Not
	 * considering enchantments.
	 * 
	 * @return
	 */
	public static final Item getStrongestSword() {
		Item bestSword = null;
		for (Item item : Item.REGISTRY) {
			if (item == null) {
				continue;
			}
			if (item instanceof ItemSword) {
				if (bestSword == null || !(bestSword instanceof ItemSword)) {
					bestSword = item;
					continue;
				}
				else if (((ItemSword) item).getDamageVsEntity() > ((ItemSword) bestSword).getDamageVsEntity()) {
					bestSword = item;
				}
			}
		}
		return bestSword;
	}

	public static TextFormatting getTextFormatFromDye(EnumDyeColor dye) {
		switch (dye) {
		case BLACK:
			// return TextFormatting.BLACK; -- Invisible in tooltips
			return TextFormatting.DARK_GRAY;
		case BLUE:
			return TextFormatting.DARK_BLUE;
		case BROWN:
			return TextFormatting.GOLD;
		case CYAN:
			return TextFormatting.DARK_AQUA;
		case GRAY:
			return TextFormatting.DARK_GRAY;
		case GREEN:
			return TextFormatting.DARK_GREEN;
		case LIGHT_BLUE:
			return TextFormatting.BLUE;
		case LIME:
			return TextFormatting.GREEN;
		case MAGENTA:
			// return TextFormatting.AQUA; -- This doesn't look accurate
			return TextFormatting.LIGHT_PURPLE;
		case ORANGE:
			return TextFormatting.GOLD;
		case PINK:
			return TextFormatting.LIGHT_PURPLE;
		case PURPLE:
			return TextFormatting.DARK_PURPLE;
		case RED:
			return TextFormatting.DARK_RED;
		case SILVER:
			return TextFormatting.GRAY;
		case WHITE:
			return TextFormatting.WHITE;
		case YELLOW:
			return TextFormatting.YELLOW;

		default:
			return TextFormatting.RESET;
		}
	}

	public static boolean hasItem(Item item) {
		InventoryPlayer inventory = Minecraft.getMinecraft().player.inventory;
		NonNullList<NonNullList<ItemStack>> allInventories = NonNullList.<NonNullList<ItemStack>> create();
		allInventories.add(inventory.mainInventory);
		allInventories.add(inventory.armorInventory);
		allInventories.add(inventory.offHandInventory);

		for (NonNullList<ItemStack> aitemstack : allInventories) {
			for (ItemStack itemstack : aitemstack) {
				if (itemstack != null && itemstack.getItem() != null && itemstack.getItem().equals(item)) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean hasItemInMainInventory(Item item) {
		for (ItemStack stack : Minecraft.getMinecraft().player.inventory.mainInventory) {
			if (stack != null && stack.getItem() != null && stack.getItem().equals(item)) {
				return true;
			}
		}
		return false;
	}

	public static ItemStack getFirstStackOfItemInMainInventory(Item item) {
		for (ItemStack stack : Minecraft.getMinecraft().player.inventory.mainInventory) {
			if (stack != null && stack.getItem() != null && stack.getItem().equals(item)) {
				return stack;
			}
		}
		return null;
	}

	public static ItemStack[] getSpecialNameTags() {
		ItemStack[] stacks = new ItemStack[TabSpawnegg.specialMobNames.length];
		for (int i = 0; i < stacks.length; i++) {
			ItemStack nameTag = new ItemStack(Items.NAME_TAG);
			nameTag.setStackDisplayName(TabSpawnegg.specialMobNames[i][0]);
			stacks[i] = nameTag;
		}
		return stacks;
	}

	/**
	 * Convenience method copied from @Item's isInCreativeTab. However, that
	 * method is protected.
	 * 
	 * @param item
	 * @param targetTab
	 * @return
	 */
	public static boolean isItemInCreativeTab(Item item, CreativeTabs targetTab) {
		for (CreativeTabs tab : item.getCreativeTabs())
			if (tab == targetTab) return true;
		CreativeTabs creativetabs = item.getCreativeTab();
		return creativetabs != null && (targetTab == CreativeTabs.SEARCH || targetTab == creativetabs);

		/*
		 * public static Item getMostSaturatingFood(){ Item food = null;
		 * for(Item item : Item.REGISTRY){ if(item == null){ continue; } if(item
		 * instanceof ItemFood){ if(food == null || !(food instanceof
		 * ItemFood)){ food = item; continue; } if(/*food1 better than food2/*){
		 * food = item; } } } return food; }
		 */
	}
}
