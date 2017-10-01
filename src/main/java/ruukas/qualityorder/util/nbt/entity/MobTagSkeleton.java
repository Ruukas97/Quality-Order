package ruukas.qualityorder.util.nbt.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.text.TextFormatting;
import ruukas.qualityorder.util.nbt.itemstack.ItemStackTag.DisplayTag;

public class MobTagSkeleton extends MobTag{
	public MobTagSkeleton() {
		super("minecraft:skeleton");
	}
	
	public static List<EntityTag> getVariants(){
		List<EntityTag> variants = new ArrayList<EntityTag>();
		for(EnumSkeletonType type : EnumSkeletonType.values()){
			MobTagSkeleton skeletonTag = new MobTagSkeleton();
			skeletonTag.setSkeletonType(type);
			variants.add(skeletonTag);
		}
		return variants;
	}
	
	@Override
	public void addRelevantLore(DisplayTag displayTag, String lore) {
		super.addRelevantLore(displayTag, TextFormatting.WHITE + lore);
		EnumSkeletonType type = getSkeletonType();
		displayTag.addLore(type.getFormat() + type.getName());
	}

	public void setSkeletonType(EnumSkeletonType type){
		setInteger("SkeletonType", type.getID());
	}
	
	public EnumSkeletonType getSkeletonType(){
		return EnumSkeletonType.byID(getInteger("SkeletonType"));
	}
	
	public static enum EnumSkeletonType{
		NORMAL(0, "Normal", TextFormatting.WHITE),
		WITHER(1, "Wither", TextFormatting.DARK_GRAY),
		STRAY(2, "Stray", TextFormatting.GRAY);
		
	    private static final EnumSkeletonType[] ID_LOOKUP = new EnumSkeletonType[values().length];
		private final int id;
		private final String name;
		private final TextFormatting format;
		
		private EnumSkeletonType(int id, String name, TextFormatting format){
			this.id = id;
			this.name = name;
			this.format = format;
		}
		
	    public static EnumSkeletonType byID(int id)
	    {
	        if (id < 0 || id >= ID_LOOKUP.length)
	        {
	        	id = 0;
	        }

	        return ID_LOOKUP[id];
	    }
		
		public int getID(){
			return this.id;
		}
		
		public String getName(){
			return this.name;
		}
		
		public TextFormatting getFormat(){
			return this.format;
		}
		
	    static
	    {
	        for (EnumSkeletonType enumskeletontype : values())
	        {
	            ID_LOOKUP[enumskeletontype.getID()] = enumskeletontype;
	        }
	    }
	}
}


