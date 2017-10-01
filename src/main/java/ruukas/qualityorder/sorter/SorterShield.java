package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;

public class SorterShield extends Sorter implements Comparator<ItemStack>{
	@Override
	public int compare(ItemStack stackNeg, ItemStack stackPos) {
		if(stackNeg.getItem() instanceof ItemShield){
			if(stackPos.getItem() instanceof ItemShield){

			}else return -1;
			
		}else if(stackPos.getItem() instanceof ItemShield){
			return 1;
		}
		
		return 0;
	}
	
	
}