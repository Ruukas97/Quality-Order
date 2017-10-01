package ruukas.qualityorder.tabs;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ruukas.qualityorder.QualityOrder;
import ruukas.qualityorder.config.QualityOrderConfig;
import ruukas.qualityorder.skulls.AlphabetSkulls;
import ruukas.qualityorder.util.QualityBookHelper;
import ruukas.qualityorder.util.QualityHelper;
import ruukas.qualityorder.util.nbt.itemstack.ItemStackTagSkull;
import ruukas.qualityorder.util.nbt.itemstack.ItemStackTagSkull.Owner;

public class TabSkull extends Tab{
	
	private static final String[] mhfSkulls = {"MHF_Alex", "MHF_Blaze", "MHF_CaveSpider", "MHF_Chicken", "MHF_Cow", "MHF_Creeper", "MHF_Enderman", "MHF_Ghast", "MHF_Golem", "MHF_Herobrine", "MHF_LavaSlime", "MHF_MushroomCow", "MHF_Ocelot", "MHF_Pig", "MHF_PigZombie", "MHF_Sheep", "MHF_Skeleton", "MHF_Slime", "MHF_Spider", "MHF_Squid", "MHF_Steve", "MHF_Villager", "MHF_Wolf", "MHF_WSkeleton", "MHF_Zombie", "MHF_Cactus", "MHF_Cake", "MHF_Chest", "MHF_CoconutB", "MHF_CoconutG", "MHF_Melon", "MHF_OakLog", "MHF_Present1", "MHF_Present2", "MHF_Pumpkin", "MHF_TNT", "MHF_TNT2", "MHF_ArrowUp", "MHF_ArrowDown", "MHF_ArrowLeft", "MHF_ArrowRight", "MHF_Exclamation", "MHF_Question"};
	public static ItemStack searchSkull = null;
	
	public TabSkull(String label) {
		super(label);
	}
	
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> stackList) {
		if(searchSkull != null){
			stackList.add(searchSkull);
		}else{
			stackList.add(QualityBookHelper.generateSkullSearchNote());
		}
		
		if(!QualityHelper.getIsNoteworthy()){
			stackList.add(clientSkull());
		}
		if (QualityOrderConfig.skullsRange > 0) {
			AxisAlignedBB axisalignedbb = Minecraft.getMinecraft().player.getEntityBoundingBox().expand(QualityOrderConfig.skullsRange, QualityOrderConfig.skullsRange, QualityOrderConfig.skullsRange);
			List<EntityLivingBase> playerList = Minecraft.getMinecraft().world.<EntityLivingBase>getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);
			
			for(int i=0;i<playerList.size();i++){
				if(playerList.get(i) instanceof EntityOtherPlayerMP){
					String owner = ((EntityOtherPlayerMP)playerList.get(i)).getDisplayNameString();
					ItemStack nearbySkull = new ItemStack(Items.SKULL, 1, 3);
					nearbySkull.setTagCompound(new NBTTagCompound());
					nearbySkull.getTagCompound().setTag("SkullOwner", new NBTTagString(owner));  
		    		stackList.add(nearbySkull);
				}
			}
		}

		
		for(int i=0;i<QualityOrder.noteWorthyUsernames.length;i++){
			ItemStack developerSkull = new ItemStack(Items.SKULL, 1, 3);
			developerSkull.setTagCompound(new NBTTagCompound());
			developerSkull.getTagCompound().setTag("SkullOwner", new NBTTagString(QualityOrder.noteWorthyUsernames[i][0]));  
			developerSkull.getTagCompound().setTag("display", new NBTTagCompound());
			NBTTagList loreTagList = new NBTTagList();
			loreTagList.appendTag(new NBTTagString(QualityOrder.noteWorthyUsernames[i][1]));
			developerSkull.getTagCompound().getCompoundTag("display").setTag("Lore", loreTagList);
    		stackList.add(developerSkull);	
		}
	
		
		if (QualityOrderConfig.skullsMhfSkulls) {
			for(String owner : mhfSkulls){
				ItemStack hardCodedSkull = new ItemStack(Items.SKULL, 1, 3);
				hardCodedSkull.setTagCompound(new NBTTagCompound());
				hardCodedSkull.getTagCompound().setString("SkullOwner", owner);  
				if(owner.contains("MHF_")){
					hardCodedSkull.getTagCompound().setTag("display", new NBTTagCompound());
					NBTTagList loreTagList = new NBTTagList();
					loreTagList.appendTag(new NBTTagString("\"Marc's Head Format\""));
					loreTagList.appendTag(new NBTTagString("This will never change,"));
					loreTagList.appendTag(new NBTTagString("as it was made by Mojang!"));
					hardCodedSkull.getTagCompound().getCompoundTag("display").setTag("Lore", loreTagList);
				}
	    		stackList.add(hardCodedSkull);
			}
		}
		
		for(String [] skullStringArray : AlphabetSkulls.skulls){
			if(skullStringArray.length == 3){
				ItemStackTagSkull skullTag = new ItemStackTagSkull();
				
				skullTag.setOwner(new Owner(skullStringArray[1], skullStringArray[2]));
			
				ItemStack skull = new ItemStack(Items.SKULL, 1, 3);
				
				skull.setTagCompound(skullTag);
				skull.setStackDisplayName(skullStringArray[0]);
				
				stackList.add(skull);
			}
		}
		
		stackList.add(QualityBookHelper.generateSkullListNote());
		for(String owner : QualityOrderConfig.skullsList){
			ItemStack configListSkull = new ItemStack(Items.SKULL, 1, 3);
			configListSkull.setTagCompound(new NBTTagCompound());
			configListSkull.getTagCompound().setString("SkullOwner", owner);  
    		stackList.add(configListSkull);
		}
	}
	
	private static final ItemStack clientSkull(){
		ItemStack skull = new ItemStack(Items.SKULL, 1, 3);
		skull.setTagCompound(new NBTTagCompound());
		skull.getTagCompound().setTag("SkullOwner", new NBTTagString(Minecraft.getMinecraft().player.getDisplayNameString()));
		return skull;
	}
	
	public static ItemStack generateSearchSkull(String searchStr){
		if(!(searchStr.length()>0))
			return null;
		ItemStack skull = new ItemStack(Items.SKULL, 1, 3);
		skull.setTagCompound(new NBTTagCompound());
		skull.getTagCompound().setTag("SkullOwner", new NBTTagString(searchStr));
		return skull;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack getTabIconItem(){
		return new ItemStack(Items.SKULL, 1, 3);
	};
	
	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack getIconItemStack(){
		return clientSkull();
	}
}
