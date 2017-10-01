package ruukas.qualityorder.sorter;

import java.util.Comparator;

import net.minecraft.item.ItemStack;
import ruukas.qualityorder.util.QualityHelper;

public class SorterAlphabeticClassName extends Sorter implements Comparator<ItemStack>{
	@Override
	public int compare(ItemStack stack1, ItemStack stack2){
		return QualityHelper.getItemType(stack1.getItem()).compareToIgnoreCase(QualityHelper.getItemType(stack2.getItem()));
	}
}