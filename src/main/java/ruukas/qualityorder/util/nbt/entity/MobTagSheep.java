package ruukas.qualityorder.util.nbt.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants;
import ruukas.qualityorder.util.QualityHelper;
import ruukas.qualityorder.util.nbt.QualityNBTUtils;
import ruukas.qualityorder.util.nbt.itemstack.ItemStackTag.DisplayTag;

public class MobTagSheep extends MobTag {
	public MobTagSheep() {
		super("minecraft:sheep");
	}
	
	@Override
	public void addRelevantLore(DisplayTag displayTag, String lore) {
		EnumDyeColor dye = getWoolColor();
		super.addRelevantLore(displayTag, QualityHelper.getTextFormatFromDye(dye) + lore);
		displayTag.addLore(QualityHelper.getTextFormatFromDye(dye) + "Color: " + dye.getName());
		if(getIsSheared()){
			displayTag.addLore(TextFormatting.GOLD + "Sheared");
		}
	}
	
	public static List<EntityTag> getVariants(){
		List<EntityTag> variants = new ArrayList<EntityTag>();
		for(boolean bool : QualityNBTUtils.falseTrueArray){
			if(!bool)
				for(EnumDyeColor dye : EnumDyeColor.values()){
					MobTagSheep sheepTag = new MobTagSheep();
					sheepTag.setWoolColor(dye);
					variants.add(sheepTag);
				}else{
					MobTagSheep sheepTag = new MobTagSheep();
					sheepTag.setIsSheared(bool);
					variants.add(sheepTag);
				}
		}
		return variants;
	}

	public void setIsSheared(boolean sheared){
		if(sheared){
			setByte("Sheared", QualityNBTUtils.getByteFromBool(sheared));
		}else if(hasKey("Sheared", Constants.NBT.TAG_BYTE)){
			removeTag("Sheared");
		}
	}
	
	public boolean getIsSheared(){
		return QualityNBTUtils.getBoolFromByte(getByte("Sheared"));
	}
	
	public void setWoolColor(EnumDyeColor dye){
		setByte("Color", (byte) dye.getMetadata());
	}
	
	public EnumDyeColor getWoolColor(){
		if(hasKey("Color", Constants.NBT.TAG_BYTE)){
			return EnumDyeColor.byMetadata(getByte("Color"));
		}else{
			return EnumDyeColor.WHITE;
		}
	}
}
