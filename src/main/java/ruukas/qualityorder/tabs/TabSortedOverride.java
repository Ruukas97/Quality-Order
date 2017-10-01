package ruukas.qualityorder.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import ruukas.qualityorder.sorter.Sorter;

public abstract class TabSortedOverride extends TabSorted {

	protected final CreativeTabs superTab;

	public TabSortedOverride(CreativeTabs tabToOverride, Sorter... sorters) {
		super(tabToOverride.getTabIndex(), tabToOverride.getTabLabel(), sorters);
		setRelevantEnchantmentTypes(tabToOverride.getRelevantEnchantmentTypes());
		this.superTab = tabToOverride;
	}

	@Override
	public boolean attemptToAddItem(NonNullList<ItemStack> stackList, Item item) {
		NonNullList<ItemStack> itemsToAdd = NonNullList.<ItemStack>create();

		item.getSubItems(superTab, itemsToAdd);
		
		return stackList.addAll(itemsToAdd);
	}
}
