package ruukas.qualityorder.tabs;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TabArmorStand extends Tab{
	public TabArmorStand(String label) {
		super(label);
	}
	
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> stackList) {
		ItemStack armorStand = new ItemStack(Items.ARMOR_STAND);
		
		//stackList.add(armorStand.copy());
		
		if(!armorStand.hasTagCompound()){
			armorStand.setTagCompound(new NBTTagCompound());
		}
		armorStand.getTagCompound().setTag("EntityTag", new NBTTagCompound());
		
		//No Base Plate
		for(int i=0;i<=1;i++){
			//Show Arms
			for(int j=0;j<=1;j++){
				//Small
				for(int k=0;k<=1;k++){
					//Copy Armor
					for(int l=0;l<=1;l++){
						ItemStack newArmorStand = armorStand.copy();
						NBTTagCompound entityTag = newArmorStand.getTagCompound().getCompoundTag("EntityTag");
						if(l == 1){
							entityTag.setByte("NoBasePlate", (byte) 1);
						}
						if(k == 1){
							entityTag.setByte("ShowArms", (byte) 1);
						}
						if(j == 1){
							entityTag.setByte("Small", (byte) 1);
						}
						if(i == 1){
							NBTTagList armorItems = new NBTTagList();
							boolean hasArmor = false;
							
							for(int m=0;m < 4;m++){
								NBTTagCompound armorSlotTag = new NBTTagCompound();
								if(Minecraft.getMinecraft().player.inventory.armorItemInSlot(m) != null){
									ItemStack armorStack = Minecraft.getMinecraft().player.inventory.armorItemInSlot(m);
									
									armorSlotTag.setString("id", armorStack.getItem().getRegistryName().getResourcePath());
									armorSlotTag.setByte("Count", (byte) 1);
									if(armorStack.getItemDamage() != 0){
										armorSlotTag.setInteger("Damage", armorStack.getItemDamage());
									}
									if(armorStack.hasTagCompound()){
										armorSlotTag.setTag("tag", armorStack.getTagCompound());
									}
									if(!hasArmor){
										hasArmor = true;
										//stackList.add(armorStack.copy());
									}
								}
								armorItems.appendTag(armorSlotTag);
							}
							entityTag.setTag("ArmorItems", armorItems);
							
							
							if(hasArmor){
								newArmorStand.getTagCompound().setBoolean("QOArmor", true);
								stackList.add(newArmorStand);
							}
						}else{
							stackList.add(newArmorStand);
						}
					}
				}
			}
		}
	}
		
	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Items.ARMOR_STAND);
	}
}
