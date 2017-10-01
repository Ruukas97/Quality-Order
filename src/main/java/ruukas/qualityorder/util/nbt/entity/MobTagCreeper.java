package ruukas.qualityorder.util.nbt.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants;
import ruukas.qualityorder.util.nbt.QualityNBTUtils;
import ruukas.qualityorder.util.nbt.itemstack.ItemStackTag.DisplayTag;

public class MobTagCreeper extends MobTag {
	public MobTagCreeper() {
		super("minecraft:creeper");
	}
	
	@Override
	public void addRelevantLore(DisplayTag displayTag, String lore) {
		super.addRelevantLore(displayTag, TextFormatting.DARK_GREEN + lore);
		if(getIsCharged()){
			displayTag.addLore(TextFormatting.AQUA + "Charged");
		}
	}
	
	public static List<EntityTag> getVariants(){
		List<EntityTag> variants = new ArrayList<EntityTag>();
		for(boolean bool : QualityNBTUtils.falseTrueArray){
			MobTagCreeper creeperTag = new MobTagCreeper();
			creeperTag.setCharged(bool);
			/*creeperTag.setExplosionRadius((byte) 127);
			creeperTag.setIgnited(true);*/
			variants.add(creeperTag);
		}
		return variants;
	}

	public void setCharged(boolean charged){
		if(charged){
			setByte("powered", QualityNBTUtils.getByteFromBool(charged));
		}else if(hasKey("powered", Constants.NBT.TAG_BYTE)){
			removeTag("powered");
		}
	}
	
	public void setIgnited(boolean ignited){
		if(ignited){
			setByte("ignited", QualityNBTUtils.getByteFromBool(ignited));
		}else if(hasKey("ignited", Constants.NBT.TAG_BYTE)){
			removeTag("ignited");
		}
	}
	
	public void setExplosionRadius(byte radius){
		setByte("ExplosionRadius", radius);
	}
	
	
	public boolean getIsCharged(){
		return QualityNBTUtils.getBoolFromByte(getByte("powered"));
	}
}
