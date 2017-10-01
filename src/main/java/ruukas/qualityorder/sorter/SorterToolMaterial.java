package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;

public class SorterToolMaterial extends Sorter implements Comparator<ItemStack>{
	@Override
	public int compare(ItemStack stackNeg, ItemStack stackPos){
		boolean negIsMat = false, posIsMat = false;
		for(ToolMaterial mat : ToolMaterial.values()){
			if(!negIsMat && mat.getRepairItemStack().getItem() == stackNeg.getItem()){
				negIsMat = true;
			}
			if(!posIsMat && mat.getRepairItemStack().getItem() == stackPos.getItem()){
				posIsMat = true;
			}
		}
		
		if(negIsMat){
			if(posIsMat){
			}else return -1;
		}else if(posIsMat){
			return 1;
		}
		
		return 0;
	}
}