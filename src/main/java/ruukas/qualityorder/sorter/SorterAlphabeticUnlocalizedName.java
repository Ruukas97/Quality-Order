package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.item.ItemStack;

public class SorterAlphabeticUnlocalizedName extends Sorter implements Comparator<ItemStack>{
	@Override
	public int compare(ItemStack stackNeg, ItemStack stackPos) {
		return stackNeg.getUnlocalizedName().compareToIgnoreCase(stackPos.getUnlocalizedName());
	}
}