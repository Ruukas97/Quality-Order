package ruukas.qualityorder.util.nbt.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.text.TextFormatting;
import ruukas.qualityorder.util.nbt.itemstack.ItemStackTag.DisplayTag;

public class TameableTagOcelot extends TameableTag{
	public TameableTagOcelot() {
		super("minecraft:ocelot");
	}
	
	public static List<EntityTag> getVariants(){
		List<EntityTag> variants = new ArrayList<EntityTag>();
		for(EnumCatType type : EnumCatType.values()){
			TameableTagOcelot catTag = new TameableTagOcelot();
			catTag.setCatType(type);
			variants.add(catTag);
		}
		return variants;
	}
	
	@Override
	public void addRelevantLore(DisplayTag displayTag, String lore) {
		super.addRelevantLore(displayTag, TextFormatting.YELLOW + "Ocelot");
		EnumCatType type = getCatType();
		displayTag.addLore(type.getFormat() + type.getName());
	}

	public void setCatType(EnumCatType type){
		setInteger("CatType", type.getID());
	}
	
	public EnumCatType getCatType(){
		return EnumCatType.byID(getInteger("CatType"));
	}
	
	public static enum EnumCatType{
		WILD(0, "Wild", TextFormatting.YELLOW),
		TUXEDO(1, "Tuxedo", TextFormatting.DARK_GRAY),
		TABBY(2, "Tabby", TextFormatting.GOLD),
		SIAMESE(3, "Siamese", TextFormatting.WHITE);
		
	    private static final EnumCatType[] ID_LOOKUP = new EnumCatType[values().length];
		private final int id;
		private final String name;
		private final TextFormatting format;
		
		private EnumCatType(int id, String name, TextFormatting format){
			this.id = id;
			this.name = name;
			this.format = format;
		}
		
	    public static EnumCatType byID(int id)
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
	        for (EnumCatType enumcattype : values())
	        {
	            ID_LOOKUP[enumcattype.getID()] = enumcattype;
	        }
	    }
	}
}


