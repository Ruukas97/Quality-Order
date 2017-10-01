package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;

public class SorterBow extends Sorter implements Comparator<ItemStack>{
	public int compare(ItemStack stackNeg, ItemStack stackPos) {
		if(stackNeg.getItem() instanceof ItemBow){
			if(stackPos.getItem() instanceof ItemBow){
				float qNeg = stackNeg.getMaxDamage();
				float qPos = stackPos.getMaxDamage();
				
				if(qNeg<qPos){
					return -1;
				}else if(qNeg>qPos){
					return 1;
				}
				
				//TODO implement velocity, enchantability etc.
				
			}else return -1;
			
		}else if(stackPos.getItem() instanceof ItemBow){
			return 1;
		}
		
		return 0;
	}
}