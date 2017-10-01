package ruukas.qualityorder.tabs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FilenameUtils;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TabCustomLocal extends TabCustom{
	protected File tabFile;

    @SideOnly(Side.CLIENT)
	protected ItemStack iconStack = new ItemStack(Items.POTATO);
    protected boolean isSearchBarEnabled = true;
    protected boolean isRefreshEnabled = false;
	
    protected String[] properties = new String[]{"label", "icon", "search", "refresh"};
	
	public TabCustomLocal(File file){
		super(readLabelFromFile(file), new String[]{""});
		tabFile = file;
		loadProperties();
	}
	
	public static String readLabelFromFile(File file){
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String str = scanner.nextLine();
				if(str.startsWith("//")){
					continue;
				}
				if(str.startsWith("label:")){
					scanner.close();
					return str.substring(6);
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return FilenameUtils.getName(file.toPath().toString());
	}
	
	
	private void loadProperties(){
		Scanner scanner;
		try {
			scanner = new Scanner(tabFile);
			while (scanner.hasNextLine()) {
				String str = scanner.nextLine();
				if(str.startsWith("//")){
					continue;
				}
				for(String property : properties){
					if(str.startsWith(property + ":")){
						if ("label".equals(property)) {
							customLabel = str.substring(property.length()+1);
						} else if ("icon".equals(property)) {
							iconStack = TabCustom.getItemStackFromString(str.substring(property.length()+1));
						} else if ("search".equals(property)) {
							isSearchBarEnabled = new Boolean(str.substring(property.length()+1));
							setBackgroundImageName(isSearchBarEnabled?"item_search.png":"items.png");
						} else if ("refresh".equals(property)) {
							isRefreshEnabled = new Boolean(str.substring(property.length()+1));
						}
					}
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void loadItems(){
		Scanner scanner;
		try {
			scanner = new Scanner(tabFile);
			
			List<String> lines = new ArrayList<String>();
			
			while (scanner.hasNextLine()){
				String line = scanner.nextLine();
				boolean lineIsProperty = false;
				for(String property : properties){
					if(line.startsWith(property + ":")){
						lineIsProperty = true;
					}
				}
				if(!lineIsProperty){
					lines.add(line);
				}
			}
			
			scanner.close();

			tabItems = lines.toArray(new String[0]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public ItemStack getIconItemStack() {
		return iconStack;
	}
	
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> stackList){
		if(isRefreshEnabled){
			loadProperties();
			loadItems();
		}
		
		TabCustom.addItemsFromStringArray(stackList, tabItems);
	}
	
	@Override
	public boolean hasSearchBar() {
		return isSearchBarEnabled;
	}
	
	@Override
	public String getBackgroundImageName() {
		return super.getBackgroundImageName();
	}
}
