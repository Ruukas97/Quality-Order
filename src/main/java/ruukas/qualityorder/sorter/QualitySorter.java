package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockCompressedPowered;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemRedstone;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import ruukas.qualityorder.sorter.resourcelocation.SorterResourceLocationAlphabetic;
import ruukas.qualityorder.util.QualityHelper;

public class QualitySorter {
	public static final Sorter
	sorterAlphabeticClassName = new SorterAlphabeticClassName(),
	sorterAlphabeticDisplayName = new SorterAlphabeticDisplayName(),
	sorterAlphabeticUnlocalizedName = new SorterAlphabeticUnlocalizedName(),
	sorterArmor = new SorterArmor(),
	sorterArrow = new SorterArrow(),
	sorterBlock = new SorterBlock(),
	sorterBlockColored = new SorterBlockColored(),
	sorterBlockMaterial = new SorterBlockMaterial(),
	sorterBlockOre = new SorterBlockOre(),
	sorterBow = new SorterBow(),
	sorterBucket = new SorterBucket(),
	sorterEnchantedBook = new SorterEnchantedBook(),
	sorterFood = new SorterFood(),
	sorterHorseArmor = new SorterHorseArmor(),
	sorterMusicDisc = new SorterMusicDisc(),
	sorterPotion = new SorterPotion(),
	sorterShield = new SorterShield(),
	sorterSpawnegg = new SorterSpawnegg(),
	sorterSubtypes = new SorterSubtypes(),
	sorterSword = new SorterSword(),
	sorterTool = new SorterTool(),
	sorterToolMaterial = new SorterToolMaterial();
	
	public static final SorterResourceLocationAlphabetic sorterResourceLocation = new SorterResourceLocationAlphabetic();
	
	/*BlockSorter blockSorter = new BlockSorter(),
	CombatSorter combatSorter = new CombatSorter(),
	MaterialSorter materialSorter = new MaterialSorter(),
	MiscSorter miscSorter = new MiscSorter(),
	PotionSorter potionSorter = new PotionSorter(),
	RedstoneSorter redstoneSorter = new RedstoneSorter(),
	TransportationSorter transportationSorter = new TransportationSorter();*/
	
	
	public static class BlockSorter implements Comparator<ItemStack>{
		@Override
		public int compare(ItemStack stack1, ItemStack stack2) {
			Item item1 = stack1.getItem();
			Item item2 = stack2.getItem();
			Block block1 = null;
			Block block2 = null;

			if(item1 instanceof ItemBlock){
				if(item2 instanceof ItemBlock){
					block1 = Block.getBlockFromItem(item1);
					block2 = Block.getBlockFromItem(item2);
					
					if(block1 instanceof BlockSlab && !(block2 instanceof BlockSlab))
						return -1;
					else if(!(block1 instanceof BlockSlab) && block2 instanceof BlockSlab)
						return 1;
					
					if(block1 instanceof BlockStairs && !(block2 instanceof BlockStairs))
						return -1;
					else if(!(block1 instanceof BlockStairs) && block2 instanceof BlockStairs)
						return 1;
					
					if(block1 == Blocks.BEDROCK)
						return 1;
					if(block2 == Blocks.BEDROCK)
						return -1;
					
					if((block1.getUnlocalizedName().toLowerCase().contains("ore") || block1 instanceof BlockOre) && (!block2.getUnlocalizedName().toLowerCase().contains("ore") || !(block2 instanceof BlockOre)))
						return 1;
					else if((!block1.getUnlocalizedName().toLowerCase().contains("ore") || !(block1 instanceof BlockOre)) && (block2.getUnlocalizedName().toLowerCase().contains("ore") || block2 instanceof BlockOre))
						return -1;
					
					if(block1 instanceof BlockStainedGlass && !(block2 instanceof BlockStainedGlass))
						return 1;
					else if(!(block1 instanceof BlockStainedGlass) && block2 instanceof BlockStainedGlass)
						return -1;
					
					if(block1 instanceof BlockColored && !(block2 instanceof BlockColored))
						return 1;
					else if(!(block1 instanceof BlockColored) && block2 instanceof BlockColored)
						return -1;


					Material material1 = block1.getDefaultState().getMaterial();
					Material material2 = block2.getDefaultState().getMaterial();
					
					if(material1 != null){
						if(material2 != null){
							
							
						}else return -1;
					}else if(material2 != null){
						return 1;
					}else return 0;

					/*for(Material mat : Material.)
					
					if(material1 == Material.woo.WOOD && material2 != SoundType.WOOD)
						return -1;
					else if(material1 != SoundType.WOOD && material2 == SoundType.WOOD)
						return 1;
					
					if(material1 == SoundType.GLASS && material2 != SoundType.GLASS)
						return -1;
					else if(material1 != SoundType.GLASS && material2 == SoundType.GLASS)
						return 1;
					
					if(material1 == SoundType.CLOTH && material2 != SoundType.CLOTH)
						return -1;
					else if(material1 != SoundType.CLOTH && material2 == SoundType.CLOTH)
						return 1;
					
					if(material1 == SoundType.SAND && material2 != SoundType.SAND)
						return -1;
					else if(material1 != SoundType.SAND && material2 == SoundType.SAND)
						return 1;
					
					if(material1 == SoundType.SNOW && material2 != SoundType.SNOW)
						return -1;
					else if(material1 != SoundType.SNOW && material2 == SoundType.SNOW)
						return 1;
					
					if(material1 == SoundType.PLANT && material2 != SoundType.PLANT)
						return -1;
					else if(material1 != SoundType.PLANT && material2 == SoundType.PLANT)
						return 1;
					
					if(material1 == SoundType.GROUND && material2 != SoundType.GROUND)
						return -1;
					else if(material1 != SoundType.GROUND && material2 == SoundType.GROUND)
						return 1;
					
					if(material1 == SoundType.METAL && material2 != SoundType.METAL)
						return -1;
					else if(material1 != SoundType.METAL && material2 == SoundType.METAL)
						return 1;*/
				}
				else return 1;
			}
			else if(item2 instanceof ItemBlock)
				return -1;
			
			return 0;
		}
	}
	
	/**
	 * Sorts the combat tab in order of:
	 * swords based on dmg
	 * shield
	 * armor
	 * bow
	 * arrows
	 * enchanted books
	 *
	 */
	public static class CombatSorter implements Comparator<ItemStack>{

		@Override
		public int compare(ItemStack stack1, ItemStack stack2) {
			Item item1 = stack1.getItem();
			Item item2 = stack2.getItem();
			
			if(item1 instanceof ItemSword){
				ItemSword sword1 = (ItemSword)item1;
				if(item2 instanceof ItemSword){
					ItemSword sword2 = (ItemSword)item2;
					if(sword1.getDamageVsEntity() == sword2.getDamageVsEntity())
						return sword1.getItemEnchantability()>sword2.getItemEnchantability()?1:-1;
					return sword1.getDamageVsEntity()>sword2.getDamageVsEntity()?1:-1;
				}
				else return -1;
			}else if(item2 instanceof ItemSword)
				return 1;

			if(item1 instanceof ItemShield && !(item2 instanceof ItemShield))
				return -1;
			else if(item2 instanceof ItemShield)
				return 1;
			
			if(item1 instanceof ItemArmor){
				ArmorMaterial mat1 = ((ItemArmor)item1).getArmorMaterial();
				if(item2 instanceof ItemArmor){
					ArmorMaterial mat2 = ((ItemArmor)item2).getArmorMaterial();
					if(mat1==mat2){
						return 0;
					}
					else if(QualityHelper.getArmorQualityFromMaterial(mat1) == QualityHelper.getArmorQualityFromMaterial(mat2)){
						return ((ItemArmor)item1).getArmorMaterial().getEnchantability()>((ItemArmor)item2).getArmorMaterial().getEnchantability()?1:-1;
					}
					return QualityHelper.getArmorQualityFromMaterial(mat1)>QualityHelper.getArmorQualityFromMaterial(mat2)?1:-1;
				}
				else return -1;
			}else if(item2 instanceof ItemArmor)
				return 1;
			
			if(item1 instanceof ItemBow && !(item2 instanceof ItemBow))
				return -1;
			else if(item2 instanceof ItemBow)
				return 1;
			
			if(item1 instanceof ItemBlock && !(item2 instanceof ItemBlock))
				return -1;
			else if(item2 instanceof ItemBlock)
				return 1;

			return 0;
		}
		
		
		public static class MiscSorter implements Comparator<ItemStack>{
			@Override
			public int compare(ItemStack stack1, ItemStack stack2) {
				Item item1 = stack1.getItem();
				Item item2 = stack2.getItem();

				if(stack1.getUnlocalizedName().toLowerCase().contains("monster")){
					if(!stack2.getUnlocalizedName().toLowerCase().contains("monster")){
						return 1;
					}
					else return stack1.getDisplayName().compareToIgnoreCase(stack2.getDisplayName());
				}
				else if(stack2.getUnlocalizedName().toLowerCase().contains("monster")) return -1;
				
				if(item1 instanceof ItemRecord){
					if(!(item2 instanceof ItemRecord)){
						return 1;
					}
					else return stack1.getDisplayName().compareToIgnoreCase(stack2.getDisplayName());
				}
				else if(item2 instanceof ItemRecord) return -1;
				
				if(item1 instanceof ItemBucketMilk){
					if(!(item2 instanceof ItemBucketMilk)){
						return 1;
					}
					else return stack1.getDisplayName().compareToIgnoreCase(stack2.getDisplayName());
				}
				else if(item2 instanceof ItemBucketMilk) return -1;
				
				if(item1 instanceof ItemBucket){
					if(!(item2 instanceof ItemBucket)){
						return 1;
					}
					else return stack1.getDisplayName().compareToIgnoreCase(stack2.getDisplayName());
				}
				else if(item2 instanceof ItemBucket) return -1;
				
				if(stack1.getUnlocalizedName().toLowerCase().contains("horsearmor")){
					if(!stack2.getUnlocalizedName().toLowerCase().contains("horsearmor")){
						return 1;
					}
					else return 0;
				}
				else if(stack2.getUnlocalizedName().toLowerCase().contains("horsearmor")) return -1;
				
				return stack1.getDisplayName().compareToIgnoreCase(stack2.getDisplayName());
			}
		}
		
		public static class PotionSorter implements Comparator<ItemStack>{
			@Override
			public int compare(ItemStack stack1, ItemStack stack2) {
				Item item1 = stack1.getItem();
				Item item2 = stack2.getItem();
				
				if(item1 instanceof ItemPotion)
					if(item2 instanceof ItemPotion){
						if(((ItemPotion)item1).hasEffect(stack1)){
							if(((ItemPotion)item2).hasEffect(stack2)){
								return 0;
							}
							else return 1;
						}	
						else if(((ItemPotion)item2).hasEffect(stack2)){
							return -1;
						}
					}
					else return 1;
				else if(item2 instanceof ItemPotion){
					return -1;
				}
				
				if(item1 == Items.GLASS_BOTTLE)
					return 1;
				if(item2 == Items.GLASS_BOTTLE)
					return -1;
				
				if(item1 == Items.BREWING_STAND)
					return -1;
				if(item2 == Items.BREWING_STAND)
					return 1;
				
				if(item1 == Items.CAULDRON)
					return -1;
				if(item2 == Items.CAULDRON)
					return 1;

				
				//String displayName1 = o1.getDisplayName();
				//String displayName2 = o2.getDisplayName();

				//int result = displayName1.compareToIgnoreCase(displayName2);
				return 0;
			}
		}
		
		public static class RedstoneSorter implements Comparator<ItemStack>{
			@Override
			public int compare(ItemStack stack1, ItemStack stack2) {
				Item item1 = stack1.getItem();
				Item item2 = stack2.getItem();

				boolean[] boolList1 = {item1 instanceof ItemRedstone, QualityHelper.getBlockInstanceof(item1, BlockCompressedPowered.class), QualityHelper.getBlockInstanceof(item1, BlockRedstoneTorch.class), QualityHelper.getBlockInstanceof(item1, BlockLever.class), QualityHelper.getBlockInstanceof(item1, BlockButton.class), QualityHelper.getBlockInstanceof(item1, BlockBasePressurePlate.class), QualityHelper.getBlockInstanceof(item1, BlockTripWireHook.class)};
				boolean[] boolList2 = {item2 instanceof ItemRedstone, QualityHelper.getBlockInstanceof(item2, BlockCompressedPowered.class), QualityHelper.getBlockInstanceof(item2, BlockRedstoneTorch.class), QualityHelper.getBlockInstanceof(item2, BlockLever.class), QualityHelper.getBlockInstanceof(item2, BlockButton.class), QualityHelper.getBlockInstanceof(item2, BlockBasePressurePlate.class), QualityHelper.getBlockInstanceof(item2, BlockTripWireHook.class)};
				for(int i=0; i<boolList1.length; i++){
					if(boolList1[i] || boolList2[i])
						return QualityHelper.compareBoolean(boolList1[i], boolList2[i]);
				}
				
				boolean[] boolBottomList1 = {item1 instanceof ItemDoor, QualityHelper.getBlockInstanceof(item1, BlockFenceGate.class), QualityHelper.getBlockInstanceof(item1, BlockTrapDoor.class)};
				boolean[] boolBottomList2 = {item2 instanceof ItemDoor, QualityHelper.getBlockInstanceof(item2, BlockFenceGate.class), QualityHelper.getBlockInstanceof(item2, BlockTrapDoor.class)};
				
				for(int i=0; i<boolBottomList1.length; i++){
					if(boolBottomList1[i] || boolBottomList2[i])
						return -QualityHelper.compareBoolean(boolBottomList1[i], boolBottomList2[i]);
				}
							
				return stack1.getDisplayName().compareToIgnoreCase(stack2.getDisplayName());
			}
		}
		
		public static class TransportationSorter implements Comparator<ItemStack>{
			@Override
			public int compare(ItemStack stack1, ItemStack stack2) {
				Item item1 = stack1.getItem();
				Item item2 = stack2.getItem();
				
				if(item1 == Items.ELYTRA)
					return -1;
				if(item2 == Items.ELYTRA)
					return 1;
				
				if(item1 instanceof ItemBoat){
					if(item2 instanceof ItemBoat){
						return 0;
					}
					else return -1;
				}
				else if(item2 instanceof ItemBoat) return 1;
				
				if(item1 instanceof ItemMinecart){
					if(item2 instanceof ItemMinecart){
						return 0;
					}
					else return -1;
				}
				else if(item2 instanceof ItemMinecart) return 1;
				
				if(Block.getBlockFromItem(item1) instanceof BlockRailBase){
					if(Block.getBlockFromItem(item2) instanceof BlockRailBase){
						return 0;
					}
					else return -1;
				}
				else if(Block.getBlockFromItem(item2) instanceof BlockRailBase) return 1;

				
				//String displayName1 = o1.getDisplayName();
				//String displayName2 = o2.getDisplayName();

				//int result = displayName1.compareToIgnoreCase(displayName2);
				return 0;
			}
		}
	}
}
