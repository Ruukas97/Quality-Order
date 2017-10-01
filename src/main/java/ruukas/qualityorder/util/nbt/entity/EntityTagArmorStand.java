package ruukas.qualityorder.util.nbt.entity;

import net.minecraftforge.common.util.Constants;
import ruukas.qualityorder.util.nbt.QualityNBTUtils;

public class EntityTagArmorStand extends EntityTag{

	public EntityTagArmorStand() {
		super("ArmorStand");
	}
	
	public void setBasePlate(boolean basePlate){
		if(!basePlate){
			setByte("NoBasePlate", QualityNBTUtils.getByteFromBool(!basePlate));
		}else if(hasKey("NoBasePlate", Constants.NBT.TAG_BYTE)){
			removeTag("NoBasePlate");
		}
	}
	
	public boolean getBasePlate(){
		return !QualityNBTUtils.getBoolFromByte(getByte("NoBasePlate"));
	}
	
	public void setShowArms(boolean showArms){
		if(showArms){
			setByte("ShowArms", QualityNBTUtils.getByteFromBool(showArms));
		}else if(hasKey("ShowArms", Constants.NBT.TAG_BYTE)){
			removeTag("ShowArms");
		}
	}
	
	public boolean getShowArms(){
		return QualityNBTUtils.getBoolFromByte(getByte("ShowArms"));
	}
	
	public void setSmall(boolean small){
		if(small){
			setByte("Small", QualityNBTUtils.getByteFromBool(small));
		}else if(hasKey("Small", Constants.NBT.TAG_BYTE)){
			removeTag("Small");
		}
	}
	
	public boolean getIsSmall(){
		return QualityNBTUtils.getBoolFromByte(getByte("Small"));
	}
	
	
}
