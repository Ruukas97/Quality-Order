package ruukas.qualityorder.util.nbt.itemstack;

import ruukas.qualityorder.util.nbt.entity.EntityTag;

public class ItemStackTagEgg extends ItemStackTag{
	
	public ItemStackTagEgg(String id){
		this(id, EntityTag.getLoreFromID(id));
	}
	
	public ItemStackTagEgg(String id, String lore){
		this(new EntityTag(id), lore);
	}
	
	public ItemStackTagEgg(EntityTag entityTag, String lore){
		setTag(EntityTag.getKeyName(), entityTag);
		
		DisplayTag displayTag = new DisplayTag();
		
		entityTag.addRelevantLore(displayTag, lore);
		
		setTag(DisplayTag.getKeyName(), displayTag);
	}
	
	public ItemStackTagEgg(EntityTag entityTag){
		setTag(EntityTag.getKeyName(), entityTag);
		
		DisplayTag displayTag = new DisplayTag();
		entityTag.addRelevantLore(displayTag);
		
		setTag(DisplayTag.getKeyName(), displayTag);
	}
	
	public EntityTag getEntityTag(){
		return (EntityTag) getCompoundTag(EntityTag.getKeyName());
	}
}
