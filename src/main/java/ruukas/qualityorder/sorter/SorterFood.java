package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import ruukas.qualityorder.util.QualityHelper;

public class SorterFood extends Sorter implements Comparator<ItemStack>{
	@Override
	public int compare(ItemStack stackNeg, ItemStack stackPos) {
		if(stackNeg.getItem() instanceof ItemFood || stackNeg.getItem() == Items.CAKE){
			if(stackPos.getItem() instanceof ItemFood || stackPos.getItem() == Items.CAKE){
				double qNeg = QualityHelper.getFoodQuality(stackNeg.getItem());
				double qPos = QualityHelper.getFoodQuality(stackPos.getItem());
				
				if(qNeg<qPos){
					return -1;
				}else if(qNeg>qPos){
					return 1;
				}
			}else return -1;
		}else if(stackPos.getItem() instanceof ItemFood || stackPos.getItem() == Items.CAKE){
			return 1;
		}
		
		return 0;
	}
}