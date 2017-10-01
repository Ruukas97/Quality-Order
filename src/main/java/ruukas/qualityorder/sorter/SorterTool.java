package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

public class SorterTool extends Sorter implements Comparator<ItemStack>{
	@Override
	public int compare(ItemStack stackNeg, ItemStack stackPos){
		if(stackNeg.getItem() instanceof ItemTool || stackNeg.getItem() instanceof ItemHoe){
			if(stackPos.getItem() instanceof ItemTool || stackPos.getItem() instanceof ItemHoe){
				if(stackNeg.getMaxDamage()<stackPos.getMaxDamage()){
					return -1;
				}else if(stackNeg.getMaxDamage()>stackPos.getMaxDamage()){
					return 1;
				}	
				
				if(stackNeg.getItem() instanceof ItemTool && stackPos.getItem() instanceof ItemTool){
					int qNeg = ((ItemTool)stackNeg.getItem()).getItemEnchantability();
					int qPos = ((ItemTool)stackPos.getItem()).getItemEnchantability();
					
					if(qNeg<qPos){
						return -1;
					}else if(qNeg>qPos){
						return 1;
					}
				}
				
				if(stackNeg.getItem().getRegistryName().getResourceDomain() != stackPos.getItem().getRegistryName().getResourceDomain()){
					return stackNeg.getItem().getRegistryName().getResourceDomain().compareTo(stackPos.getItem().getRegistryName().getResourceDomain());
				}
			}else return -1;
			
		}else if(stackPos.getItem() instanceof ItemTool){
			return 1;
		}
		
		return 0;
	}
}