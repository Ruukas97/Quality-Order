package ruukas.qualityorder.util.nbt.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.util.Constants;
import ruukas.qualityorder.util.QualityHelper;
import ruukas.qualityorder.util.nbt.itemstack.ItemStackTag.DisplayTag;

public class TameableTagwolf extends TameableTag{

	public TameableTagwolf() {
		super("minecraft:wolf");
	}
	
	public static List<EntityTag> getVariants(){
		List<EntityTag> variants = new ArrayList<EntityTag>();
		variants.add(new TameableTagwolf());
		for(EnumDyeColor dye : EnumDyeColor.values()){
			TameableTagwolf wolfTag = new TameableTagwolf();
			wolfTag.setCollarColor(dye);
			variants.add(wolfTag);
		}
		return variants;
	}
	
	@Override
	public void addRelevantLore(DisplayTag displayTag, String lore) {
		super.addRelevantLore(displayTag, TextFormatting.GRAY + lore);
		EnumDyeColor dye = getCollarColor();
		displayTag.addLore(QualityHelper.getTextFormatFromDye(dye) + "Collar: " + dye.getName());
	}

	public void setCollarColor(EnumDyeColor dye){
		setByte("CollarColor", (byte) dye.getDyeDamage());
	}
	
	public EnumDyeColor getCollarColor(){
		if(hasKey("CollarColor", Constants.NBT.TAG_BYTE)){
			return EnumDyeColor.byDyeDamage(getByte("CollarColor"));
		}else{
			return EnumDyeColor.RED;
		}
	}
}
