package ruukas.qualityorder.tabs;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import ruukas.qualityorder.util.QualityBookHelper;
import ruukas.qualityorder.util.QualityHelper;
import ruukas.qualityorder.util.itemstack.QualityEgg;
import ruukas.qualityorder.util.nbt.entity.EntityTag;
import ruukas.qualityorder.util.nbt.entity.MobTagCreeper;
import ruukas.qualityorder.util.nbt.entity.MobTagEndermite;
import ruukas.qualityorder.util.nbt.entity.MobTagPig;
import ruukas.qualityorder.util.nbt.entity.MobTagSheep;
import ruukas.qualityorder.util.nbt.entity.MobTagSkeleton;
import ruukas.qualityorder.util.nbt.entity.TameableTagOcelot;
import ruukas.qualityorder.util.nbt.entity.TameableTagwolf;

public class TabSpawnegg extends Tab{
private static List<List<QualityEgg>> eggVariantLists;

	//TODO Color all egg names
	//TODO grab lore color from egg color
	private static void hatchEggs(){
		if(QualityHelper.hasItemInMainInventory(Items.NAME_TAG)){
			mobName = QualityHelper.getFirstStackOfItemInMainInventory(Items.NAME_TAG).getDisplayName();
		}else{
			mobName = null;
		}
		
		eggVariantLists = new ArrayList<List<QualityEgg>>();
		for(ResourceLocation res : EntityList.ENTITY_EGGS.keySet()){
			eggVariantLists.add(getEggOnList(new QualityEgg(res.toString())));
		}
		
		eggVariantLists.add(getEggVariantList(MobTagCreeper.getVariants()));
		eggVariantLists.add(getEggVariantList(MobTagEndermite.getVariants()));
		if(!isTame){
			eggVariantLists.add(getEggVariantList(TameableTagOcelot.getVariants()));
		}else{
			List<EntityTag> ocelotVariants = TameableTagOcelot.getVariants();
			for(EntityTag ocelotTag : ocelotVariants){
				((TameableTagOcelot)ocelotTag).setOwnerUUID(Minecraft.getMinecraft().player.getUniqueID());
			}
			eggVariantLists.add(getEggVariantList(ocelotVariants));
		}
		eggVariantLists.add(getEggVariantList(MobTagPig.getVariants()));
		eggVariantLists.add(getEggVariantList(MobTagSheep.getVariants()));
		eggVariantLists.add(getEggVariantList(MobTagSkeleton.getVariants()));
		if(!isTame){
			eggVariantLists.add(getEggVariantList(TameableTagwolf.getVariants()));
		}else{
			List<EntityTag> wolfVariants = TameableTagwolf.getVariants();
			for(EntityTag wolfTag : wolfVariants){
				((TameableTagwolf)wolfTag).setOwnerUUID(Minecraft.getMinecraft().player.getUniqueID());
			}
			eggVariantLists.add(getEggVariantList(wolfVariants));
		}
	}
	
	private static List<QualityEgg> getEggOnList(QualityEgg egg){
		if(mobName != null){
			egg.getQualityEggTag().getDisplayTag().setName(mobName);
		}

		List<QualityEgg> eggList = new ArrayList<QualityEgg>();
		eggList.add(egg);
		return eggList;
	}
	
	private static List<QualityEgg> getEggVariantList(List<EntityTag> entityTagVariants){
		List<QualityEgg> eggVariants = new ArrayList<QualityEgg>();
		for(EntityTag tag : entityTagVariants){
			QualityEgg egg = new QualityEgg(tag);
			if(mobName != null){
				egg.getQualityEggTag().getDisplayTag().setName(mobName);
			}
			eggVariants.add(egg);
		}
		return eggVariants;
	}
	
	public TabSpawnegg(String label) {
		super(label);
		//hatchEggs();
	}
	
	private static int buttonXSize = 30;
	private static int buttonYSize = 20;
	private static int buttonSpace = 5;

	//Size of Creative GUI
    //this.ySize = 136;
    //this.xSize = 195;
	
	public static boolean isTame = false;
	public static GuiButton tameButton;
	public static GuiButton generateTameButton(){
		tameButton = new GuiButton(110, Minecraft.getMinecraft().currentScreen.width/2-195/2-buttonXSize-buttonSpace, Minecraft.getMinecraft().currentScreen.height/2-136/2, buttonXSize, buttonYSize, isTame ? "Tame" : "Wild");
		return tameButton;
	}
	
	private static String mobName;
	public static final String[][] specialMobNames = {{"Dinnerbone", "Upside-down"}, {"Grumm", "Upside-down"}, {"jeb_", "Rainbow Sheep"}, {"Toast", "Lost Rabbit Tribute"}};//Add lore
		
	
	
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> stackList){
		stackList.add(QualityBookHelper.generateGuiNote());
		hatchEggs();
		
		stackList.add(new ItemStack(Items.NAME_TAG));
		
		for(ItemStack nameTag : QualityHelper.getSpecialNameTags()){
			stackList.add(nameTag);
		}
		
		for(List<QualityEgg> variants : eggVariantLists){
			for(QualityEgg egg : variants){
				egg.addToList(stackList);
			}
		}
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Items.SPAWN_EGG);
	}
}
