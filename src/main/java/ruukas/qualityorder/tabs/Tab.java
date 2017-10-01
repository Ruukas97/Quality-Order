package ruukas.qualityorder.tabs;

import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;


public abstract class Tab extends CreativeTabs{
	private Object[] itemClassesToAdd = null;
	
	public Tab(String label){
		super(label);
	}
	
	public Tab(int index, String label){
		super(index, label);
	}
	
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> stackList) {
        for (Item item : Item.REGISTRY){
        	if(attemptToAddItem(stackList, item)){
        		///This block is for readability.
        		//If attemptToAddItem return true, items will have been added as a result of it
        		continue;
        	}else if(attemptToAddItemBasedOnClass(stackList, item)){
        		//Adds the item if it's an instance of the class given in #typeToAdd
        		continue;
        	}
        }
	}
	
	/**
	 * Checks and sets the conditions an item has to meet to be added to the CreativeTab. If the requirements are met, adds the item to the list.
	 * The default requirement is that the item has the creative tab listed as the one to display on.
	 * @param stackList - The list of items to add the item to.
	 * @param item - The item that's being attempted to add to the list
	 * @return Whether or not any items were added stackList as a result of this
	 */
	public boolean attemptToAddItem(NonNullList<ItemStack> stackList, Item item){
		NonNullList<ItemStack> itemsToAdd = NonNullList.<ItemStack>create();
		
		item.getSubItems(this, itemsToAdd);
		
		return stackList.addAll(itemsToAdd);
	}
	
	/**
	 * Checks if the item's class is an instance of the "typeToAdd" variable. If it is, attempts to add it to the stackList.
	 * @param stackList - The list of items to add the item to.
	 * @param item - The item that's being attempted to add to the list
	 * @return Whether or not any items were added stackList as a result of this
	 */
	public boolean attemptToAddItemBasedOnClass(NonNullList<ItemStack> stackList, Item item){
		NonNullList<ItemStack> itemsToAdd = NonNullList.<ItemStack>create();

		if(itemClassesToAdd == null){
			return false;
		}
		
		for(Object itemClass : itemClassesToAdd){
			if(((Class<?>) itemClass).isInstance(item)){
				item.getSubItems(item.getCreativeTab(), itemsToAdd);
				
				if(!stackList.addAll(itemsToAdd)){
					//In case item.getSubItems didn't return any items, this will forcefully add the item with meta 0 to the tab.
					//Either way, it should return true since at least one item will be added.
					stackList.add(new ItemStack(item));
				}
				
				return true;
			}
		}
		
		return false;
	}
		
	/**
	 * Sets the list of classes for which any items extending those, should be added to the creative tab.
	 * @param itemClasses - Items that are an instance of any of these classes, will be added to the creative tab.
	 * @return The modified instance of this object.
	 */
	public CreativeTabs setItemClassesToAdd(Object... itemClasses){
		NonNullList<Object> itemClassList = NonNullList.<Object>create();
		
		for(Object itemClass : itemClasses){
			if(itemClass.getClass().isInstance(itemClass)){
				itemClassList.add((Class<?>) itemClass);
			}
		}
		
		this.itemClassesToAdd = itemClassList.toArray();

		return this;
	}
	
	public Object[] getTypeToAdd(){
		return itemClassesToAdd;
	}
	
	public int getXForDrawing(GuiContainerCreative gui){
		int guiLeft = gui.getGuiLeft();
		int xSize = gui.getXSize();
		
        int i = getTabColumn();
        
        int x = guiLeft + 28 * i;
        
        if (isAlignedRight())
        {
            x = guiLeft + xSize - 28 * (6 - i);
        }
        else if (i > 0)
        {
            x += i;
        }
        
        return x;
	}
	
	public int getYForDrawing(GuiContainerCreative gui){
		int guiTop = gui.getGuiTop();
		int ySize = gui.getYSize();
		
        boolean flag1 = isTabInFirstRow();
        int y = guiTop;
        
        if (flag1)
        {
            y = y - 28;
        }
        else
        {
            y = y + (ySize - 4);
        }
        
        return y;
	}
}
