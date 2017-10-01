package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBlockSpecial;
import net.minecraft.item.ItemStack;

public class SorterBlock extends Sorter implements Comparator<ItemStack>{
	@Override
	public int compare(ItemStack stackNeg, ItemStack stackPos) {
		if(stackNeg.getItem() instanceof ItemBlock || stackNeg.getItem() instanceof ItemBlockSpecial){
			if(stackPos.getItem() instanceof ItemBlock || stackPos.getItem() instanceof ItemBlockSpecial){
				return 0;
			}else{
				return -1;
			}
			
		}else if(stackPos.getItem() instanceof ItemBlock || stackPos.getItem() instanceof ItemBlockSpecial){
			return 1;
		}
		
		return 0;
	}
}