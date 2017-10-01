package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import ruukas.qualityorder.util.QualityHelper;

public class SorterArmor extends Sorter implements Comparator<ItemStack>{
	@Override
	public int compare(ItemStack stackNeg, ItemStack stackPos) {
		
		if(stackNeg.getItem() instanceof ItemArmor){
			if(stackPos.getItem() instanceof ItemArmor){
				ItemArmor armorNeg = (ItemArmor)stackNeg.getItem();
				ItemArmor armorPos = (ItemArmor)stackPos.getItem();
				int iNeg, iPos;
				
				if(armorNeg.getArmorMaterial() == armorPos.getArmorMaterial()){
					iNeg = armorNeg.armorType.getIndex();
					iPos = armorPos.armorType.getIndex();
					
					if(iNeg>iPos){
						return -1;
					}else if(iNeg<iPos){
						return 1;
					}	
				}
				
				iNeg = QualityHelper.getArmorQualityFromMaterial(armorNeg.getArmorMaterial());
				iPos = QualityHelper.getArmorQualityFromMaterial(armorPos.getArmorMaterial());
				
				if(iNeg<iPos){
					return -1;
				}else if(iNeg>iPos){
					return 1;
				}
				
				if(stackNeg.getItem().getRegistryName().getResourceDomain() != stackPos.getItem().getRegistryName().getResourceDomain()){
					return stackNeg.getItem().getRegistryName().getResourceDomain().compareTo(stackPos.getItem().getRegistryName().getResourceDomain());
				}
			
			}else return -1;
			
		}else if(stackPos.getItem() instanceof ItemArmor){
			return 1;
		}
		
		return 0;
	}
}