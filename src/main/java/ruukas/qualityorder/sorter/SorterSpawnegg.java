package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;

public class SorterSpawnegg extends Sorter implements Comparator<ItemStack>{
	@Override
	public int compare(ItemStack stackNeg, ItemStack stackPos) {
		if(stackNeg.getItem() instanceof ItemMonsterPlacer){
			if(stackPos.getItem() instanceof ItemMonsterPlacer){

			}else return -1;
			
		}else if(stackPos.getItem() instanceof ItemMonsterPlacer){
			return 1;
		}
		
		return 0;
	}
	
	
}