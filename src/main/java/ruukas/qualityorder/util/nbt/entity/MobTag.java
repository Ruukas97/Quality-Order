package ruukas.qualityorder.util.nbt.entity;

import ruukas.qualityorder.util.nbt.QualityNBTUtils;

public class MobTag extends EntityTag{

	public MobTag(String id) {
		super(id);
	}

	public void setHealth(int health){
		setFloat("Health", health);
	}
	
	public void setAbsorptionAmount(int absorption){
		setFloat("AbsorptionAmount", absorption);
	}
	
	public void setFallFlying(boolean fallFlying){
		if(fallFlying){
			setByte("FallFlying", QualityNBTUtils.getByteFromBool(fallFlying));
		}else if(hasKey("FallFlying")){
			this.removeTag("FallFlying");
		}
	}
}
