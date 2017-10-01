package ruukas.qualityorder.util.nbt.itemstack;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class ItemStackTagSkull extends ItemStackTag{
	public void setOwner(Owner owner){
		setTag(Owner.keyName, owner);
	}
	
	public void setSkullOwner(String name){
		setString("SkullOwner", name);
	}
	
	
	public static class Owner extends NBTTagCompound{
		private static final String keyName = "SkullOwner";
		
		public Owner(){}
		
		public Owner(EntityPlayer player){
			setId(player.getUniqueID().toString());
			setName(player.getDisplayNameString());
		}
		
		public Owner(String uuid, String texture){
			setId(uuid);
			addTexture(texture);
		}
		
		public static String getKeyName(){
			return keyName;
		}

		public void setId(String uuid){
			setString("Id", uuid);
		}
		
		public void setName(String name){
			setString("Name", name);
		}
		
		public void addTexture(String texture){
			if(!hasKey("Properties", Constants.NBT.TAG_COMPOUND)){
				setTag("Properties", new NBTTagCompound());
			}
			NBTTagCompound propertiesTag = getCompoundTag("Properties");
			if(!propertiesTag.hasKey("textures", Constants.NBT.TAG_LIST)){
				propertiesTag.setTag("textures", new NBTTagList());
			}
			NBTTagCompound skinTag = new NBTTagCompound();
			skinTag.setString("Value", texture);
			
			propertiesTag.getTagList("textures", Constants.NBT.TAG_COMPOUND).appendTag(skinTag);
		}
	}
}
