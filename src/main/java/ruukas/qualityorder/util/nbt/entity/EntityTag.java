package ruukas.qualityorder.util.nbt.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import ruukas.qualityorder.util.nbt.QualityNBTUtils;
import ruukas.qualityorder.util.nbt.itemstack.ItemStackTag.DisplayTag;

public class EntityTag extends NBTTagCompound{
	public EntityTag(String id){
		setID(id);
	}
	
	public void addRelevantLore(DisplayTag displayTag, String lore){
		displayTag.addLore(TextFormatting.GOLD + lore);
	}
	
	public void addRelevantLore(DisplayTag displayTag){
		addRelevantLore(displayTag, getLoreFromID(getID()));
	}
	
	public static String getLoreFromID(String id){
		if(id.length() < 1){
			return id;
		}
		String[] splittedResource = id.split(":");
		String[] splittedName = splittedResource[1].split("_");
		String result = "something";
		for(String str : splittedName){
			if(result == "something"){
				result = str.substring(0, 1).toUpperCase() + str.substring(1);
			}else{
				result = result + " " + str.substring(0, 1).toUpperCase() + str.substring(1);
			}
		}
		
		return result;
	}
	
	/*public static List<QualityEntityTag> getVariants(){
		return new ArrayList<QualityEntityTag>();
	}*/
	
	
	public static String getKeyName() {
		return "EntityTag";
	}
	
	public void setID(String id){
		setString("id", id);
	}
	
	public String getID(){
		return getString("id");
	}
	
	public void setFire(short time){
		setShort("Fire", time);
	}
	
	public void setGravity(boolean hasGravity){
		if(!hasGravity){
			setByte("NoGravity", QualityNBTUtils.getByteFromBool(!hasGravity));
		}else{
			if(hasKey("NoGravity")){
				this.removeTag("NoGravity");
			}
		}
	}
	
	public void setInvulnerable(boolean invulnerable){
		if(invulnerable){
			setByte("Invulnerable", QualityNBTUtils.getByteFromBool(invulnerable));
		}else{
			if(hasKey("Invulnerable")){
				this.removeTag("Invulnerable");
			}
		}
	}
	
	public void setCustomName(String name){
		setString("CustomName", name);
	}
	
	public void setCustomNameVisible(boolean visible){
		if(visible){
			setByte("CustomNameVisible", QualityNBTUtils.getByteFromBool(visible));
		}else{
			if(hasKey("CustomNameVisible")){
				this.removeTag("CustomNameVisible");
			}
		}
	}
	
	public void setGlowing(boolean glowing){
		if(glowing){
			setByte("Glowing", QualityNBTUtils.getByteFromBool(glowing));
		}else{
			if(hasKey("Glowing")){
				this.removeTag("Glowing");
			}
		}
	}
}
