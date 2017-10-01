package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;

public class SorterEnchantedBook extends Sorter implements Comparator<ItemStack>{
	@Override
	public int compare(ItemStack stackNeg, ItemStack stackPos){
		if(stackNeg.getItem() instanceof ItemEnchantedBook){
			if(stackPos.getItem() instanceof ItemEnchantedBook){
	            return Enchantment.getEnchantmentByID(ItemEnchantedBook.getEnchantments(stackNeg).getCompoundTagAt(0).getShort("id")).getTranslatedName(1).compareTo(Enchantment.getEnchantmentByID(ItemEnchantedBook.getEnchantments(stackPos).getCompoundTagAt(0).getShort("id")).getTranslatedName(1));
			}else return -1;
			
		}else if(stackPos.getItem() instanceof ItemEnchantedBook){
			return 1;
		}
		
		return 0;
	}
}