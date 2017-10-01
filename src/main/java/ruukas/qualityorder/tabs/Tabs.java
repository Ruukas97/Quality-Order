package ruukas.qualityorder.tabs;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.NonNullList;
import ruukas.qualityorder.config.QualityOrderConfig;
import ruukas.qualityorder.sorter.QualitySorter;
import ruukas.qualityorder.util.QualityHelper;

public class Tabs {

	public static CreativeTabs BUILDING_BLOCKS;

	public static CreativeTabs DECORATIONS;

	public static CreativeTabs REDSTONE;

	public static CreativeTabs TRANSPORTATION;

	public static CreativeTabs MISC;

	public static CreativeTabs FOOD;

	public static CreativeTabs TOOLS;

	public static CreativeTabs COMBAT;

	public static CreativeTabs BREWING;

	public static CreativeTabs MATERIALS;

	public static CreativeTabs UNAVAILABLE;

	public static CreativeTabs SKULLS;

	public static CreativeTabs THIEVING;

	public static CreativeTabs BANNER;

	public static CreativeTabs FIREWORK;

	public static CreativeTabs ARMORSTAND;

	public static CreativeTabs MAP;

	public static CreativeTabs DEATH;

	public static CreativeTabs MEMORY;

	public static CreativeTabs EGGS;

	public static TabCustom[] CUSTOM;

	public static void overrideVanillaTabs() {
		if (QualityOrderConfig.shouldOverrideBuildingBlocksTab) {
			BUILDING_BLOCKS = new TabSortedOverride(CreativeTabs.BUILDING_BLOCKS, QualitySorter.sorterAlphabeticDisplayName, QualitySorter.sorterBlockColored.reversedOrder(), QualitySorter.sorterBlockOre, QualitySorter.sorterBlockMaterial, QualitySorter.sorterBlock) {

				@Override
				public ItemStack getTabIconItem() {
					return new ItemStack(Blocks.LAPIS_BLOCK);
				}
			};
		}

		if (QualityOrderConfig.shouldOverrideDecorationsTab) {
			DECORATIONS = new TabSortedOverride(CreativeTabs.DECORATIONS) {

				@Override
				public ItemStack getTabIconItem() {
					return new ItemStack(Blocks.DOUBLE_PLANT, 1, BlockDoublePlant.EnumPlantType.PAEONIA.getMeta());
				}
			};
		}

		if (QualityOrderConfig.shouldOverrideRedstoneTab) {
			REDSTONE = new TabSortedOverride(CreativeTabs.REDSTONE) {

				@Override
				public ItemStack getTabIconItem() {
					return new ItemStack(Items.REDSTONE);
				}
			};
		}

		if (QualityOrderConfig.shouldOverrideTransportationTab) {
			TRANSPORTATION = new TabSortedOverride(CreativeTabs.TRANSPORTATION) {

				@Override
				public ItemStack getTabIconItem() {
					return new ItemStack(Blocks.GOLDEN_RAIL);
				}
			};
		}

		if (QualityOrderConfig.shouldOverrideMiscTab) {
			MISC = new TabSortedOverride(CreativeTabs.MISC, QualitySorter.sorterAlphabeticDisplayName, QualitySorter.sorterToolMaterial, QualitySorter.sorterSubtypes.reversedOrder(), QualitySorter.sorterMusicDisc.reversedOrder(), QualitySorter.sorterBlock.reversedOrder(), QualitySorter.sorterSpawnegg.reversedOrder()) {

				@Override
				public ItemStack getTabIconItem() {
					return new ItemStack(Items.FEATHER);
				}

				public boolean attemptToAddItem(NonNullList<ItemStack> stackList, Item item) {
					NonNullList<ItemStack> itemsToAdd = NonNullList.<ItemStack> create();

					item.getSubItems(superTab, itemsToAdd);

					if (!stackList.addAll(itemsToAdd)) {
						item.getSubItems(CreativeTabs.MATERIALS, itemsToAdd);
						return stackList.addAll(itemsToAdd);
					}
					else return true;
				}
			};
		}

		if (QualityOrderConfig.shouldOverrideFoodTab) {
			FOOD = new TabSortedOverride(CreativeTabs.FOOD, QualitySorter.sorterAlphabeticDisplayName, QualitySorter.sorterFood) {

				@Override
				public ItemStack getTabIconItem() {
					return new ItemStack(QualityHelper.getBestFood());
				}
			}.setItemClassesToAdd(ItemFood.class);
		}

		if (QualityOrderConfig.shouldOverrideToolsTab) {
			TOOLS = new TabSortedOverride(CreativeTabs.TOOLS, QualitySorter.sorterAlphabeticDisplayName, QualitySorter.sorterTool, QualitySorter.sorterEnchantedBook.reversedOrder()) {

				@Override
				public ItemStack getTabIconItem() {
					return new ItemStack(QualityHelper.getMostDurablePickaxe());
				}
			}.setItemClassesToAdd(ItemTool.class);
		}

		if (QualityOrderConfig.shouldOverrideCombatTab) {
			COMBAT = new TabSortedOverride(CreativeTabs.COMBAT, QualitySorter.sorterAlphabeticDisplayName, QualitySorter.sorterArrow, QualitySorter.sorterBow, QualitySorter.sorterArmor, QualitySorter.sorterShield, QualitySorter.sorterSword, QualitySorter.sorterTool.reversedOrder(), QualitySorter.sorterEnchantedBook.reversedOrder()) {

				@Override
				public ItemStack getTabIconItem() {
					return new ItemStack(QualityHelper.getStrongestSword());
				}
			}.setItemClassesToAdd(ItemSword.class, ItemArmor.class, ItemArrow.class, ItemShield.class, ItemAxe.class);
		}

		if (QualityOrderConfig.shouldOverrideBrewingTab) {
			BREWING = new TabSortedOverride(CreativeTabs.BREWING, QualitySorter.sorterAlphabeticDisplayName, QualitySorter.sorterBlock, QualitySorter.sorterPotion.reversedOrder()) {

				@Override
				public ItemStack getTabIconItem() {
					return new ItemStack(Items.BREWING_STAND);
				}
			};
		}
	}

	public static void registerTabs() {
		
		if (QualityOrderConfig.shouldAddUnavailableTab) {
			UNAVAILABLE = new TabUnavailable("unavailable");
		}
		if (QualityOrderConfig.shouldAddSkullsTab) {
			SKULLS = new TabSkull("skulls");
		}
		if (QualityOrderConfig.shouldAddThievingTab) {
			THIEVING = new TabThief("thief");
		}
		if (QualityOrderConfig.shouldAddBannersTab) {
			BANNER = new TabBanner("banner");
		}
		if (QualityOrderConfig.shouldAddFireworkTab) {
			FIREWORK = new TabFirework("firework");
		}
		if (QualityOrderConfig.shouldAddArmorStandTab) {
			ARMORSTAND = new TabArmorStand("armorstand");
		}
		if (QualityOrderConfig.shouldAddMapTab) {
			MAP = new TabMap("map");
		}
		if (QualityOrderConfig.shouldAddEggTab) {
			EGGS = new TabSpawnegg("eggs");
		}
	}
}
