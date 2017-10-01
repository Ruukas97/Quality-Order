package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;

public class SorterPotion extends Sorter implements Comparator<ItemStack>{
	@Override
	public int compare(ItemStack stackNeg, ItemStack stackPos) {
		if(stackNeg.getItem() instanceof ItemPotion){
			if(stackPos.getItem() instanceof ItemPotion){

			}else return -1;
			
		}else if(stackPos.getItem() instanceof ItemPotion){
			return 1;
		}
		
		return 0;
	}
	
	
}