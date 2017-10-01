package ruukas.qualityorder.tabs;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ruukas.qualityorder.util.QualityBookHelper;

public class TabBanner extends Tab{
	public static ItemStack iconBanner = generateIconBanner();

	public TabBanner(String label) {
		super(label);
		//setBackgroundImageName("items.png");
	}
	
	/*@Override
	public boolean hasSearchBar() {
		return false;
	}*/
	
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> stackList) {
		//stackList.add(generateIconBanner());
		
		stackList.add(QualityBookHelper.generateGuiNote());
		
		EntityPlayer player = Minecraft.getMinecraft().player;
		
		int i = 0;
		NonNullList<ItemStack> banners = NonNullList.<ItemStack>create();
		Items.BANNER.getSubItems(CreativeTabs.DECORATIONS, banners);
		ItemStack currentBanner = null;
		
		for(ItemStack stack : player.inventory.mainInventory){
			if(stack != null && (stack.getItem() == Items.SHIELD || stack.getItem() == Items.BANNER)){
				currentBanner = stack;
				iconBanner = currentBanner.copy();
				break;
			}
			i++;
			if(!InventoryPlayer.isHotbar(i))
				break;
		}
		if (currentBanner == null){
			iconBanner = generateIconBanner();
			Items.BANNER.getSubItems(CreativeTabs.DECORATIONS, stackList);
			ItemStack baseShield = new ItemStack(Items.SHIELD);
			
			for(ItemStack banner : banners){
				ItemStack newShield = baseShield.copy();
				
                NBTTagCompound nbttagcompound = banner.getSubCompound("BlockEntityTag");
                NBTTagCompound nbttagcompound1 = nbttagcompound == null ? new NBTTagCompound() : nbttagcompound.copy();
                nbttagcompound1.setInteger("Base", banner.getMetadata() & 15);
                newShield.setTagInfo("BlockEntityTag", nbttagcompound1);
                
				stackList.add(newShield);
			}
			
			for(TileEntity tilee : player.world.loadedTileEntityList){
				if(tilee instanceof TileEntityBanner){
			        stackList.add(((TileEntityBanner)tilee).getItem());
				}
			}
			
		}else{
			if(!currentBanner.hasTagCompound())
				currentBanner.setTagCompound(new NBTTagCompound());
				
			ItemStack otherVariant = null;
			if(currentBanner.getItem().equals(Items.BANNER)){
				otherVariant = new ItemStack(Items.SHIELD);
			}else if(currentBanner.getItem().equals(Items.SHIELD)){
				System.out.println(currentBanner.getTagCompound().toString());
				otherVariant = new ItemStack(Items.BANNER);
				if(currentBanner.hasTagCompound()){
					if(currentBanner.getTagCompound().hasKey("BlockEntityTag", Constants.NBT.TAG_COMPOUND)){
						if(currentBanner.getTagCompound().getCompoundTag("BlockEntityTag").hasKey("Base", Constants.NBT.TAG_INT)){
							otherVariant.setItemDamage(currentBanner.getTagCompound().getCompoundTag("BlockEntityTag").getInteger("Base"));
						}
					}
				}
			}
			otherVariant.setTagCompound(currentBanner.getTagCompound().copy());
			stackList.add(otherVariant);
			
			for(BannerPattern loopPattern : BannerPattern.values()){
				if(loopPattern == BannerPattern.BASE){
					continue;
				}
				for(EnumDyeColor color : EnumDyeColor.values()){
	                ItemStack newBanner = currentBanner.copy();
	                
	                NBTTagCompound nbtTag = newBanner.getOrCreateSubCompound("BlockEntityTag");
	                if (!nbtTag.hasKey("Patterns", 9)){
	                	nbtTag.setTag("Patterns", new NBTTagList());
					}
	                
	                NBTTagList nbtPatterns = nbtTag.getTagList("Patterns", 10);
	                
	                NBTTagCompound nbtPattern = new NBTTagCompound();
	                nbtPattern.setString("Pattern", loopPattern.getHashname());
	                nbtPattern.setInteger("Color", color.getDyeDamage());             
	                
	                nbtPatterns.appendTag(nbtPattern);
	                stackList.add(newBanner);
				}
			}
		}
	}

	private static final ItemStack generateIconBanner(){
		ItemStack icon = new ItemStack(Items.BANNER, 1, 14);
        
        NBTTagCompound nbtTag = icon.getOrCreateSubCompound("BlockEntityTag");
        if (!nbtTag.hasKey("Patterns", 9)){
        	nbtTag.setTag("Patterns", new NBTTagList());
		}
        
        NBTTagList nbtPatterns = nbtTag.getTagList("Patterns", 10);
        
        NBTTagCompound nbtPattern = new NBTTagCompound();
        nbtPattern.setString("Pattern", BannerPattern.CROSS.getHashname());
        nbtPatterns.appendTag(nbtPattern);
        
        NBTTagCompound nbtPattern1 = new NBTTagCompound();
        nbtPattern1.setString("Pattern", BannerPattern.CURLY_BORDER.getHashname());
        nbtPatterns.appendTag(nbtPattern1);
        
        NBTTagCompound nbtPattern2 = new NBTTagCompound();
        nbtPattern2.setString("Pattern", BannerPattern.STRAIGHT_CROSS.getHashname());
        nbtPatterns.appendTag(nbtPattern2);
        
        NBTTagCompound nbtPattern3 = new NBTTagCompound();
        nbtPattern3.setString("Pattern", BannerPattern.FLOWER.getHashname());
        nbtPattern3.setInteger("Color", 14);;
        nbtPatterns.appendTag(nbtPattern3);
        
		return icon;
	}
		
	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Items.BANNER);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack getIconItemStack() {
		return iconBanner;
	}
}
