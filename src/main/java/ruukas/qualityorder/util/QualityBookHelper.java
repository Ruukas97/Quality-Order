package ruukas.qualityorder.util;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import ruukas.qualityorder.QualityOrder;

public class QualityBookHelper {
	//private static final ItemStack thiefGuide = generateThiefGuide();
	
	public static ItemStack getThiefGuide(){
		return generateThiefGuide();
	}
	
	public static ItemStack generateMemoryNote(){
		ItemStack stack = new ItemStack(Items.PAPER);
		stack.setStackDisplayName("Note of Memories");
		NBTTagList loreTagList = new NBTTagList();
		loreTagList.appendTag(new NBTTagString("Open a chest to memorize it in this tab!"));
		loreTagList.appendTag(new NBTTagString("This is a temporary solution."));
		stack.getTagCompound().getCompoundTag("display").setTag("Lore", loreTagList);
		return stack;
	}
	
	public static ItemStack generateGuiNote(){
		ItemStack stack = new ItemStack(Items.PAPER);
		stack.setStackDisplayName("A new perspective");
		NBTTagList loreTagList = new NBTTagList();
		loreTagList.appendTag(new NBTTagString("Reopen creative while staying at this tab to enable buttons!"));
		stack.getTagCompound().getCompoundTag("display").setTag("Lore", loreTagList);
		return stack;
	}
	
	public static ItemStack generateDeathNote(){
		ItemStack stack = new ItemStack(Items.PAPER);
		stack.setStackDisplayName("Tattered note");
		NBTTagList loreTagList = new NBTTagList();
		loreTagList.appendTag(new NBTTagString("If you should die, you can always get your items back from here!"));
		loreTagList.appendTag(new NBTTagString("But do it quick! They might disappear!"));
		stack.getTagCompound().getCompoundTag("display").setTag("Lore", loreTagList);
		return stack;
	}
	
	public static ItemStack generateSkullSearchNote(){
		ItemStack stack = new ItemStack(Items.PAPER);
		stack.setStackDisplayName("Note on targeted killing");
		NBTTagList loreTagList = new NBTTagList();
		loreTagList.appendTag(new NBTTagString("Searching for a username will add his skull to the list!"));
		loreTagList.appendTag(new NBTTagString("Also, you can stand within 15 meters of someone, to get their skull!"));
		stack.getTagCompound().getCompoundTag("display").setTag("Lore", loreTagList);
		return stack;
	}
	
	public static ItemStack generateSkullListNote(){
		ItemStack stack = new ItemStack(Items.PAPER);
		stack.setStackDisplayName("Note on beheading friends");
		NBTTagList loreTagList = new NBTTagList();
		loreTagList.appendTag(new NBTTagString("In the config, you can write a list of usernames to add to this tab!"));
		stack.getTagCompound().getCompoundTag("display").setTag("Lore", loreTagList);
		return stack;
	}
	
	public static ItemStack generateNote(String noteName, String... lore){
		ItemStack stack = new ItemStack(Items.PAPER);
		stack.setStackDisplayName(noteName);
		if(lore != null){
			NBTTagList loreTagList = new NBTTagList();
			for(String str : lore){
				loreTagList.appendTag(new NBTTagString(str));
			}
			stack.getTagCompound().getCompoundTag("display").setTag("Lore", loreTagList);
		}
		return stack;
	}
	
	/*private static ItemStack generateThiefNote(){
		ItemStack stack = new ItemStack(Items.PAPER);
		stack.setStackDisplayName("Thief's note");
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setTag("display", new NBTTagCompound());
		NBTTagList loreTagList = new NBTTagList();
		loreTagList.appendTag(new NBTTagString("Find the thieving guide in singleplayer!"));
		stack.getTagCompound().getCompoundTag("display").setTag("Lore", loreTagList);
		return stack;
	}*/

	
	private static ItemStack generateThiefGuide(){
		ItemStack stack = new ItemStack(Items.WRITTEN_BOOK);
		stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setString("title", "Thieving Guide");
		stack.getTagCompound().setString("author", QualityOrder.noteWorthyUsernames[0][0]);
		
		stack.getTagCompound().setTag("display", new NBTTagCompound());
		NBTTagList loreTagList = new NBTTagList();
		loreTagList.appendTag(new NBTTagString("A beginner's guide to thieving using Quality Order!"));
		loreTagList.appendTag(new NBTTagString("The book doesn't display right on some servers,"));
		loreTagList.appendTag(new NBTTagString("if that is the case, read it in Singleplayer!"));
		stack.getTagCompound().getCompoundTag("display").setTag("Lore", loreTagList);
		
		NBTTagList pages = new NBTTagList();
		
		pages.appendTag(new NBTTagString(
				"{text:\"Follow the steps of this guide, and you will be a master thief in no time!\n\", extra:["
				+ addOpeningLine(addBasicString("Before starting, make sure you have installed "))
				+ ",{text:'Quality Order', color:dark_purple, clickEvent:{action:open_url, value:'http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/wip-mods/2687920'}, hoverEvent:{action:show_text, value:[{text:'Click to go to the mod page!', color:green}]}}"
				+ addBasicString("!")
				+ "]}"));
		
		pages.appendTag(new NBTTagString(addOpeningLine(addString("1. Position yourself within 15 meters of the victim.\n2. Press the assigned inventory key on your keyboard.\n3. Browse to the Thief tab.\n4. Grab all the goodies, the victim has equiped!"))));
		
		stack.getTagCompound().setTag("pages", pages);

		return stack;
	}
	
	public static String addOpeningLine(String str){
		if(str.startsWith(","))
			return str.substring(1);
		return str;
	}
	
	public static String addString(String str){
		return ",{text:\"" + str + "\"}";
	}
	
	public static String addBasicString(String str){
		return ",{text:\"" + str + "\", color:reset}";
	}
	
	public static String addColoredString(String str, String color){
		return ",{text:\"" + str + "\", color:" + color + "}";
	}
	
	public static String newLine(boolean useComma){
		return useComma?",{text:\"\n\"}":"{text:\"\n\"}";
	}
}
