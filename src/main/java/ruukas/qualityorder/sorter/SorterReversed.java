package ruukas.qualityorder.sorter;

import net.minecraft.item.ItemStack;


public class SorterReversed extends Sorter {
	
	public SorterReversed(Sorter parent) {
		super(false);
		reversedOrderSorter = parent;
	}

	
	@Override
	public int compare(ItemStack o1, ItemStack o2) {
		return -reversedOrderSorter.compare(o1, o2);
	}
}
