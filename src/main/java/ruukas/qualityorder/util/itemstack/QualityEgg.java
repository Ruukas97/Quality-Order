package ruukas.qualityorder.util.itemstack;

import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import ruukas.qualityorder.util.nbt.entity.EntityTag;
import ruukas.qualityorder.util.nbt.itemstack.ItemStackTagEgg;

public class QualityEgg{
	private ItemStack egg;
	private ItemStackTagEgg eggTag;
	
	public QualityEgg(String id){
		this(id, EntityTag.getLoreFromID(id));
	}
	
	public QualityEgg(String id, String lore){
		egg = new ItemStack(Items.SPAWN_EGG);
		eggTag = new ItemStackTagEgg(id, lore);
		egg.setTagCompound(eggTag);
	}
	
	public QualityEgg(EntityTag entityTag){
		egg = new ItemStack(Items.SPAWN_EGG);
		eggTag = new ItemStackTagEgg(entityTag);
		egg.setTagCompound(eggTag);
	}
	
	public ItemStackTagEgg getQualityEggTag(){
		return eggTag;
	}
	
	public void addToList(List<ItemStack> stackList){
		stackList.add(egg);
	}
}
