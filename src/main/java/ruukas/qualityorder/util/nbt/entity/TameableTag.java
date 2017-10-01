package ruukas.qualityorder.util.nbt.entity;

import java.util.UUID;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants;
import ruukas.qualityorder.util.nbt.QualityNBTUtils;
import ruukas.qualityorder.util.nbt.itemstack.ItemStackTag.DisplayTag;

public class TameableTag extends MobTag{

	public TameableTag(String id) {
		super(id);
	}
	
	@Override
	public void addRelevantLore(DisplayTag displayTag, String lore) {
		if(getHasOwner()){
			displayTag.addLore(TextFormatting.GREEN + "Tame " + lore);
		}else{
			super.addRelevantLore(displayTag, "Wild " + lore);
		}
	}
	
	public boolean getHasOwner(){
		return hasKey("OwnerUUID", Constants.NBT.TAG_STRING);
	}

	public void setOwnerUUID(UUID uuid){
		setOwnerUUID(uuid.toString());
	}
	
	public void setOwnerUUID(String uuid){
		setString("OwnerUUID", uuid);
	}
	
	public void setSitting(boolean sitting){
		if(sitting){
			setByte("Sitting", QualityNBTUtils.getByteFromBool(sitting));
		}else if(hasKey("Sitting")){
			this.removeTag("Sitting");
		}
	}
}
