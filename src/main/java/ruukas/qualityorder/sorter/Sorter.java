package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.item.ItemStack;

public abstract class Sorter implements Comparator<ItemStack> {
	protected Sorter reversedOrderSorter;
	
	public Sorter(){
		this(true);
	}
	
	public Sorter(boolean createReversed){
		if(createReversed){
			this.reversedOrderSorter = new SorterReversed(this);
		}
	}

	
	@Override
	public abstract int compare(ItemStack o1, ItemStack o2);

	public Sorter reversedOrder(){
		return reversedOrderSorter;
	}
}
