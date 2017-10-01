package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;

public class SorterBucket extends Sorter implements Comparator<ItemStack>{
	@Override
	public int compare(ItemStack stackNeg, ItemStack stackPos) {
		if(stackNeg.getItem() instanceof ItemBucket || stackNeg.getItem() instanceof ItemBucketMilk){
			if(stackPos.getItem() instanceof ItemBucket || stackPos.getItem() instanceof ItemBucketMilk){

			}else return -1;
			
		}else if(stackPos.getItem() instanceof ItemBucket || stackPos.getItem() instanceof ItemBucketMilk){
			return 1;
		}
		
		return 0;
	}
	
	
}