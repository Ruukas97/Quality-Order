package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;

public class SorterArrow extends Sorter implements Comparator<ItemStack>{
	@Override
	public int compare(ItemStack stackNeg, ItemStack stackPos) {
		if(stackNeg.getItem() instanceof ItemArrow){
			if(stackPos.getItem() instanceof ItemArrow){

			}else return -1;
			
		}else if(stackPos.getItem() instanceof ItemArrow){
			return 1;
		}
		
		return 0;
	}
}