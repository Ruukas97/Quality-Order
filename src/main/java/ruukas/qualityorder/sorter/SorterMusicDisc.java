package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;

public class SorterMusicDisc extends Sorter implements Comparator<ItemStack>{
	@Override
	public int compare(ItemStack stackNeg, ItemStack stackPos) {
		if(stackNeg.getItem() instanceof ItemRecord){
			if(stackPos.getItem() instanceof ItemRecord){

			}else return -1;
			
		}else if(stackPos.getItem() instanceof ItemRecord){
			return 1;
		}
		
		return 0;
	}
	
	
}