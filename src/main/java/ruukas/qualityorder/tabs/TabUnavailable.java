package ruukas.qualityorder.tabs;

import java.util.Collections;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import ruukas.qualityorder.sorter.QualitySorter;

public class TabUnavailable extends TabSorted{

	public TabUnavailable(String label) {
		super(label, QualitySorter.sorterAlphabeticClassName);
	}
	
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> stackList) {
		for(Item item : Item.REGISTRY){
			if(item == null || item == ItemStack.EMPTY.getItem())
				continue;
			for(CreativeTabs tabs : item.getCreativeTabs())	{
				if(tabs == null || tabs == this){
					item.getSubItems(this, stackList);
				}
			}
		}

		Collections.sort(stackList, QualitySorter.sorterAlphabeticClassName);
	}
	
	@Override
	public boolean attemptToAddItem(NonNullList<ItemStack> stackList, Item item) {
		return super.attemptToAddItem(stackList, item);
	}


	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Blocks.BARRIER);
	}
}
