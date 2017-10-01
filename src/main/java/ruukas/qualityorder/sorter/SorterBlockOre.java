package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBlockSpecial;
import net.minecraft.item.ItemStack;

public class SorterBlockOre extends Sorter implements Comparator<ItemStack>{
	@Override
	public int compare(ItemStack stackNeg, ItemStack stackPos) {
		if(stackNeg.getItem() instanceof ItemBlock || stackNeg.getItem() instanceof ItemBlockSpecial){
			if(stackPos.getItem() instanceof ItemBlock || stackPos.getItem() instanceof ItemBlockSpecial){
				if(stackNeg.getUnlocalizedName().toLowerCase().contains("ore") || Block.getBlockFromItem(stackNeg.getItem()) instanceof BlockOre){
					if(stackPos.getUnlocalizedName().toLowerCase().contains("ore") || Block.getBlockFromItem(stackPos.getItem()) instanceof BlockOre){
						return 0;
					}else return -1;
				}else if(stackPos.getUnlocalizedName().toLowerCase().contains("ore") || Block.getBlockFromItem(stackPos.getItem()) instanceof BlockOre){
					return 1;
				}
				
			}else{
				return -1;
			}
			
		}else if(stackPos.getItem() instanceof ItemBlock || stackPos.getItem() instanceof ItemBlockSpecial || Block.getBlockFromItem(stackPos.getItem()) instanceof BlockOre){
			return 1;
		}
		
		return 0;
	}
}