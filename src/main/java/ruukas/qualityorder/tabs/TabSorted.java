package ruukas.qualityorder.tabs;

import java.util.Collections;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import ruukas.qualityorder.sorter.Sorter;

public abstract class TabSorted extends Tab{
	protected Sorter[] sorterArray;
	
	public TabSorted(String label, Sorter... sorters){
		super(label);
		sorterArray = sorters;		
	}
	
	public TabSorted(int index, String label, Sorter... sorters){
		super(index, label);
		this.sorterArray = sorters;
	}
	
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> stackList) {
		super.displayAllRelevantItems(stackList);
		
		sort(stackList, this.sorterArray);
	}	
	
	
	protected void sort(NonNullList<ItemStack> stackList){
		TabSorted.sort(stackList, sorterArray);
	}
	
	protected static void sort(NonNullList<ItemStack> stackList,Sorter... sorters){
		for(Sorter sorter : sorters){
			Collections.sort(stackList, sorter);
		}
	}
}
