package ruukas.qualityorder.tabs;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import ruukas.qualityorder.QualityOrder;

public class TabCustomRemote extends TabCustomLocal{
	public static File tabCachedDirectory = new File(QualityOrder.tabDirectory.getPath()+"\\Cached");
	public URL tabUrl;
	
	public TabCustomRemote(File file, URL url){
		super(file);
		this.tabUrl = url;
	}

	public void redownloadFile() throws IOException{
		FileUtils.copyURLToFile(tabUrl, tabFile);
		loadItems();
	}

	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> stackList){
		if(isRefreshEnabled){
			try {
				redownloadFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		super.displayAllRelevantItems(stackList);
	}
}
