package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class SorterHorseArmor extends Sorter implements Comparator<ItemStack>{
	@Override
	public int compare(ItemStack stackNeg, ItemStack stackPos) {
		if(stackNeg.getItem() == Items.IRON_HORSE_ARMOR || stackNeg.getItem() == Items.GOLDEN_HORSE_ARMOR || stackNeg.getItem() == Items.DIAMOND_HORSE_ARMOR){
			if(stackPos.getItem() == Items.IRON_HORSE_ARMOR || stackPos.getItem() == Items.GOLDEN_HORSE_ARMOR || stackPos.getItem() == Items.DIAMOND_HORSE_ARMOR){

			}else return -1;
			
		}else if(stackPos.getItem() == Items.IRON_HORSE_ARMOR || stackPos.getItem() == Items.GOLDEN_HORSE_ARMOR || stackPos.getItem() == Items.DIAMOND_HORSE_ARMOR){
			return 1;
		}
		
		return 0;
	}
	
	
}