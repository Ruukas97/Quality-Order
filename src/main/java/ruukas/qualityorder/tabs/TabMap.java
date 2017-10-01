package ruukas.qualityorder.tabs;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;
import ruukas.qualityorder.config.QualityOrderConfig;

public class TabMap extends Tab{

	public TabMap(String label) {
		super(label);
	}
	
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> stackList) {
		World world = Minecraft.getMinecraft().world;
		stackList.add(new ItemStack(Items.MAP));
		for(int i=0;i<QualityOrderConfig.cartographyNumberOfMaps;i++){
			ItemStack newMap = new ItemStack(Items.FILLED_MAP, 1, i);
			if(QualityOrderConfig.cartographyRemoveUnknownMaps){	
				MapData mapdata = ((ItemMap)newMap.getItem()).getMapData(newMap, world);
				if(mapdata != null){
					stackList.add(newMap);
				}
			}else{
				stackList.add(newMap);
			}
		}
	}


	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Items.MAP);
	}
}
