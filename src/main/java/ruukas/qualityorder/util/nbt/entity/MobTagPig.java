package ruukas.qualityorder.util.nbt.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants;
import ruukas.qualityorder.util.nbt.QualityNBTUtils;
import ruukas.qualityorder.util.nbt.itemstack.ItemStackTag.DisplayTag;

public class MobTagPig extends MobTag {
	public MobTagPig() {
		super("minecraft:pig");
	}
	
	@Override
	public void addRelevantLore(DisplayTag displayTag, String lore) {
		super.addRelevantLore(displayTag, TextFormatting.RED + lore);
		if(getIsSaddled()){
			displayTag.addLore(TextFormatting.GOLD + "Saddled");
		}
	}
	
	public static List<EntityTag> getVariants(){
		List<EntityTag> variants = new ArrayList<EntityTag>();
		for(boolean bool : QualityNBTUtils.falseTrueArray){
			MobTagPig pigTag = new MobTagPig();
			pigTag.setSaddled(bool);
			variants.add(pigTag);
		}
		return variants;
	}

	public void setSaddled(boolean saddled){
		if(saddled){
			setByte("Saddle", QualityNBTUtils.getByteFromBool(saddled));
		}else if(hasKey("Saddle", Constants.NBT.TAG_BYTE)){
			removeTag("Saddle");
		}
	}
	
	public boolean getIsSaddled(){
		return QualityNBTUtils.getBoolFromByte(getByte("Saddle"));
	}
}
