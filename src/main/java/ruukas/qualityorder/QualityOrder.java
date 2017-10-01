package ruukas.qualityorder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ruukas.qualityorder.config.QualityOrderConfig;
import ruukas.qualityorder.event.QualityOrderEvents;
import ruukas.qualityorder.sorter.QualitySorter;
import ruukas.qualityorder.tabs.TabCustomLocal;
import ruukas.qualityorder.tabs.Tabs;

@Mod(name = "Quality Order", modid = QualityOrder.MODID, version = QualityOrder.VERSION, updateJSON = "https://gist.githubusercontent.com/Zerotiger/e3d6f9334e77f282616d1ac753959136/raw", guiFactory = "ruukas.qualityorder.gui.QualityGuiFactory", clientSideOnly = true)
public class QualityOrder {

	public static final String MODID = "qualityorder";
	public static final String VERSION = "0.12";

	public static final String[][] noteWorthyUsernames = { { "Ruukas", "Author of Quality Order" } };

	public static final Logger logger = LogManager.getLogger(MODID);

	public static File configDirectory;

	public static File tabDirectory;

	public static boolean hasLoadedTabs = false;

	// TODO Gui keybindings
	// TODO Rework fireworks tab
	// TODO Make a Quality Order GUI combining the features of the mod -
	// essentially moving it out of the Creative Menu - Also add a button to get
	// from Creative Menu to Quality Order GUI
	// TODO add a recoloring gui
	// TODO naming gui
	// TODO nbt gui
	// TODO custom tabs - sorting them, add custom stuff like thieving, change
	// null item
	// TODO redoing the creative menu altogether
	// TODO storage gui saved on itemstack nbt
	// TODO tools show dps
	// TODO fix nearby banners not showing correctly on tab
	// TODO a lot of stuff listed in spawnegg tab
	// TODO armor stand gui
	// TODO spawn egg gui
	// TODO brewing
	// TODO steal items from mobs
	// TODO thieving note for items stolen from chat.
	// TODO chest gui, for saving items
	// TODO clean up code

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		configDirectory = event.getModConfigurationDirectory();
		tabDirectory = new File(QualityOrder.configDirectory.getPath() + "\\QualityOrderCustomTabs");

		MinecraftForge.EVENT_BUS.register(new QualityOrderEvents());

		QualityOrderConfig.initConfig(event);
	}

	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		Tabs.overrideVanillaTabs();
		Tabs.registerTabs();
		
		if (QualityOrderConfig.shouldAddUnavailableTab) {
			for (Item item : Item.REGISTRY) {
				if (item != null && item != Items.AIR && item != ItemStack.EMPTY.getItem() && item.getCreativeTab() == null) {
					item.setCreativeTab(Tabs.UNAVAILABLE);
					logger.info("Item: " + item.getUnlocalizedName() + " was not added to a tab. Adding it to Unavailable.");
				}
			}

			for (Block block : Block.REGISTRY) {
				if (block != null && block.getCreativeTabToDisplayOn() == null) {
					block.setCreativeTab(Tabs.UNAVAILABLE);
					logger.info("Block: " + block.getUnlocalizedName() + " was not added to a tab. Adding it to Unavailable.");
				}
			}
		}

		if (QualityOrderConfig.shouldOverrideSearchTab) {
			/*try {
				Field searchTabField = ReflectionHelper.findField(CreativeTabs.class, new String[] { "SEARCH", "field_78027_g" });
				// Useful source for getting SRG names: mcpbot.bspk.rs
				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(searchTabField, searchTabField.getModifiers() & ~Modifier.FINAL);

				searchTabField.set(null, new TabSearch());
			}
			catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
			catch (UnableToFindFieldException e) {
				e.printStackTrace();
			}
			catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
			catch (SecurityException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}*/
		}

		if (tabDirectory.listFiles() != null) {
			for (File tabDirFile : tabDirectory.listFiles()) {
				if (tabDirFile != null && tabDirFile.toString().endsWith(".qotab")) {
					TabCustomLocal tab = new TabCustomLocal(tabDirFile);
					logger.info("Loaded tab: \"" + tab.getTranslatedTabLabel() + "\" from file: \"" + tabDirFile.getName() + "\".");
				}
				else {
					logger.error(tabDirFile + " was not loaded succesfully.");
				}
			}
		}

		hasLoadedTabs = true;

		QualityOrderConfig.syncCustomTabs();

		List<ResourceLocation> itemKeys = new ArrayList<ResourceLocation>();

		for (ResourceLocation itemKey : Item.REGISTRY.getKeys()) {
			itemKeys.add(itemKey);
		}

		Collections.sort(itemKeys, QualitySorter.sorterResourceLocation);

		try {
			FileUtils.writeLines(new File(tabDirectory, "items.txt"), itemKeys);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
