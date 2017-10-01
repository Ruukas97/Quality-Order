package ruukas.qualityorder.util.nbt.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants;
import ruukas.qualityorder.util.nbt.QualityNBTUtils;
import ruukas.qualityorder.util.nbt.itemstack.ItemStackTag.DisplayTag;

public class MobTagEndermite extends MobTag {
	public MobTagEndermite() {
		super("minecraft:endermite");
	}
	
	@Override
	public void addRelevantLore(DisplayTag displayTag, String lore) {
		super.addRelevantLore(displayTag, TextFormatting.DARK_PURPLE + lore);
		if(getPlayerSpawned()){
			displayTag.addLore(TextFormatting.DARK_AQUA + "Pearlspawn");
		}
	}
	
	public static List<EntityTag> getVariants(){
		List<EntityTag> variants = new ArrayList<EntityTag>();
		for(boolean bool : QualityNBTUtils.falseTrueArray){
			MobTagEndermite endermiteTag = new MobTagEndermite();
			endermiteTag.setPlayerSpawned(bool);
			variants.add(endermiteTag);
		}
		return variants;
	}

	public void setPlayerSpawned(boolean playerSpawned){
		if(playerSpawned){
			setByte("PlayerSpawned", QualityNBTUtils.getByteFromBool(playerSpawned));
		}else if(hasKey("PlayerSpawned", Constants.NBT.TAG_BYTE)){
			removeTag("PlayerSpawned");
		}
	}
	
	public boolean getPlayerSpawned(){
		return QualityNBTUtils.getBoolFromByte(getByte("PlayerSpawned"));
	}
}
