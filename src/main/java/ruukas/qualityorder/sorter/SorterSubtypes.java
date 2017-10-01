package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.item.ItemStack;

public class SorterSubtypes extends Sorter implements Comparator<ItemStack>{
	@Override
	public int compare(ItemStack stackNeg, ItemStack stackPos) {
		if(stackNeg.getHasSubtypes()){
			if(stackPos.getHasSubtypes()){
				if(stackNeg.getItem().getClass() != stackPos.getItem().getClass()){
					return stackNeg.getItem().getClass().getSimpleName().compareToIgnoreCase(stackPos.getItem().getClass().getSimpleName());
				}
			}else return -1;
		}else if(stackPos.getHasSubtypes()){
			return 1;
		}
		
		return 0;
	}
}