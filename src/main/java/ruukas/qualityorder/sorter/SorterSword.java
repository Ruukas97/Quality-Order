package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import ruukas.qualityorder.util.QualityHelper;

public class SorterSword extends Sorter implements Comparator<ItemStack>{
	public int compare(ItemStack stackNeg, ItemStack stackPos) {
		if(stackNeg.getItem() instanceof ItemSword){
			if(stackPos.getItem() instanceof ItemSword){

				
				double qNeg = QualityHelper.getStackDPS(stackNeg);
				double qPos = QualityHelper.getStackDPS(stackPos);
				
				if(qNeg<qPos){
					return -1;
				}else if(qNeg>qPos){
					return 1;
				}
				
				if(stackNeg.getMaxDamage()<stackPos.getMaxDamage()){
					return -1;
				}else if(stackNeg.getMaxDamage()>stackPos.getMaxDamage()){
					return 1;
				}	
				
				qNeg = ((ItemSword)stackNeg.getItem()).getItemEnchantability();
				qPos = ((ItemSword)stackPos.getItem()).getItemEnchantability();
				
				if(qNeg<qPos){
					return -1;
				}else if(qNeg>qPos){
					return 1;
				}
				
				
				
			}else return -1;
			
		}else if(stackPos.getItem() instanceof ItemSword){
			return 1;
		}
		
		return 0;
	}
}