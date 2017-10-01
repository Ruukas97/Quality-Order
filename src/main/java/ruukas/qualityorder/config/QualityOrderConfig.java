package ruukas.qualityorder.config;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.annotation.Nullable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ruukas.qualityorder.QualityOrder;
import ruukas.qualityorder.tabs.TabCustom;
import ruukas.qualityorder.tabs.TabCustomRemote;
import ruukas.qualityorder.tabs.Tabs;

public class QualityOrderConfig {

	public static Configuration file;

	public static final String categoryVanillaTabs = "vanillatabs";
	public static final String categoryQualityTabs = "qualitytabs";
	public static final String categoryQualityTabSettings = "qualitytabsettings";
	public static final String categoryCustomTabs = "customtabs";

	// TODO configurable tooltips
	// TODO custom tabs sometimes overrides vanilla tabs

	// Vanilla Tabs
	public static boolean shouldOverrideBuildingBlocksTab = true, shouldOverrideDecorationsTab = true, shouldOverrideRedstoneTab = true, shouldOverrideTransportationTab = true, shouldOverrideMiscTab = true, shouldOverrideFoodTab = true, shouldOverrideToolsTab = true, shouldOverrideCombatTab = true,
			shouldOverrideBrewingTab = true, shouldOverrideMaterialTab = true, shouldOverrideSearchTab = true;

	// Quality Order Tabs
	public static boolean shouldAddUnavailableTab = true, shouldAddSkullsTab = true, shouldAddBannersTab = true, shouldAddThievingTab = true, shouldAddFireworkTab = true, shouldAddArmorStandTab = true, shouldAddMapTab = true, shouldAddEggTab = true;

	// Quality Order Tabs Settings
	public static int cartographyNumberOfMaps = 100;
	public static boolean cartographyRemoveUnknownMaps = true;
	public static int thievingRange = 15;
	public static int skullsRange = 15;
	public static String[] skullsList = { "Friend1" };
	public static boolean skullsMhfSkulls = true;

	// Custom Tabs
	public static int customTabAmount = 1;
	public static String[] remoteTabs = { "" };

	public static void syncCustomTabs() {
		if (QualityOrder.hasLoadedTabs) {
			file.setCategoryRequiresMcRestart(categoryCustomTabs, false);
			customTabAmount = file.getInt("Amount of Custom Tabs", categoryCustomTabs, 1, 0, 999, "Set this to the amount of Custom Tabs, you would like to make");

			// This removes previous Custom tabs and removes the need to restart
			// the game.
			// If any other mods adds creative tabs to the game, after it has
			// started, this might remove their tab instead... :/
			if (Tabs.CUSTOM != null && Tabs.CUSTOM.length > 0) {
				CreativeTabs[] tmp = new CreativeTabs[CreativeTabs.CREATIVE_TAB_ARRAY.length - Tabs.CUSTOM.length];
				for (int i = 0; i < tmp.length; i++) {
					tmp[i] = CreativeTabs.CREATIVE_TAB_ARRAY[i];
					System.out.println("Added " + CreativeTabs.CREATIVE_TAB_ARRAY[i].getTranslatedTabLabel());
				}
				CreativeTabs.CREATIVE_TAB_ARRAY = tmp;
			}

			ArrayList<TabCustom> customTabList = new ArrayList<TabCustom>();

			if (customTabAmount > 0) {
				for (int i = 1; i <= customTabAmount; i++) {
					if (i == 1) {
						customTabList.add(new TabCustom(file.getString("Custom Tab " + getZeroedNumberFromID(i) + " - Title", categoryCustomTabs, getTabLabelFromID(1) + " - Change in config", "The Title/Label of the Custom Tab"),
								file.getStringList("Custom Tab " + getZeroedNumberFromID(i) + " - Items", categoryCustomTabs,
										new String[] { "paper 0 {display:{Name:\"New Feauture\",Lore:[\"This is a custom tab!\", \"You can change it or add your own, in the in-game mod config menu.\"]}}", "minecraft:ghast_tear", "stone 2", "minecraft:emerald_ore" },
										"Specify them like you would in a /give command\n")));
					}
					else {
						customTabList.add(new TabCustom(file.getString("Custom Tab " + getZeroedNumberFromID(i) + " - Title", categoryCustomTabs, getTabLabelFromID(i), "The Title/Label of the Custom Tab"),
								file.getStringList("Custom Tab " + getZeroedNumberFromID(i) + " - Items", categoryCustomTabs, new String[] { "" }, "Specify them like you would in a /give command")));
					}
				}
			}

			remoteTabs = file.getStringList("A list of Remote Tab URLs", categoryCustomTabs, new String[] { "" }, "See example at https://gist.github.com/Zerotiger");
			if (remoteTabs.length > 0) {
				for (String str : remoteTabs) {
					if (str != null && !str.equals("") && !str.equals(" ")) {
						URL tabUrl;
						try {
							tabUrl = new URL(str);

							if (FilenameUtils.getExtension(tabUrl.getPath()).equals("qotab")) {
								File cachedTabFile = new File(TabCustomRemote.tabCachedDirectory, FilenameUtils.getName(tabUrl.getPath()));
								try {
									FileUtils.copyURLToFile(tabUrl, cachedTabFile);

									TabCustomRemote remoteTab = new TabCustomRemote(cachedTabFile, tabUrl);
									QualityOrder.logger.info("Loaded tab: \"" + remoteTab.getTranslatedTabLabel() + "\" from url: \"" + cachedTabFile.getName() + "\". Cached in \"" + cachedTabFile.getName() + "\".");
									customTabList.add(remoteTab);
								}
								catch (IOException e) {
									e.printStackTrace();
									QualityOrder.logger.error("Could not access URL: \"" + tabUrl.toString() + "\". Checking for cached file...");
									TabCustom cachedTab = loadRemoteTabFromCachedFile(tabUrl);
									if (cachedTab != null) {
										customTabList.add(cachedTab);
									}
								}
							}
							else {
								QualityOrder.logger.error(str + " was not loaded succesfully.");
							}
						}
						catch (MalformedURLException e) {
							e.printStackTrace();
							QualityOrder.logger.error(str + " was not loaded succesfully.");
						}
					}
				}
			}

			Tabs.CUSTOM = customTabList.toArray(new TabCustom[0]);

			if (file.hasChanged()) {
				file.save();
			}
		}
	}

	@Nullable
	private static TabCustom loadRemoteTabFromCachedFile(URL url) {
		File cachedTabFile = new File(TabCustomRemote.tabCachedDirectory, FilenameUtils.getName(url.getPath()));

		for (File cachedFile : TabCustomRemote.tabCachedDirectory.listFiles()) {
			if (FilenameUtils.getName(url.getPath()).equals(FilenameUtils.getName(cachedFile.getPath()))) {
				TabCustomRemote remoteTab = new TabCustomRemote(cachedTabFile, url);
				QualityOrder.logger.info("Failed to load tab: \"" + remoteTab.getTranslatedTabLabel() + "\" from url: \"" + cachedTabFile.getName() + "\". Succesfully loaded it from cached file: \"" + cachedTabFile.getName() + "\" instead.");
				return remoteTab;
			}
		}
		return null;
	}

	public static void syncConfig() {
		file.setCategoryRequiresMcRestart(categoryVanillaTabs, true);
		shouldOverrideBuildingBlocksTab = file.getBoolean("Custom Building Blocks Tab", categoryVanillaTabs, true, "Should Quality Order override the Building Blocks tab and sort it?");
		shouldOverrideDecorationsTab = file.getBoolean("Custom Decorations Tab", categoryVanillaTabs, true, "Should Quality Order override the Decorations tab and sort it?");
		shouldOverrideRedstoneTab = file.getBoolean("Custom Redstone Tab", categoryVanillaTabs, true, "Should Quality Order override the Redstone tab and sort it?");
		shouldOverrideTransportationTab = file.getBoolean("Custom Transportation Tab", categoryVanillaTabs, true, "Should Quality Order override the Transportation tab and sort it?");
		shouldOverrideMiscTab = file.getBoolean("Custom Misc Tab", categoryVanillaTabs, true, "Should Quality Order override the Misc tab and sort it?");
		shouldOverrideFoodTab = file.getBoolean("Custom Food Tab", categoryVanillaTabs, true, "Should Quality Order override the Food tab and sort it?");
		shouldOverrideToolsTab = file.getBoolean("Custom Tool Tab", categoryVanillaTabs, true, "Should Quality Order override the Tools tab and sort it?");
		shouldOverrideCombatTab = file.getBoolean("Custom Combat Tab", categoryVanillaTabs, true, "Should Quality Order override the Combat tab and sort it?");
		shouldOverrideBrewingTab = file.getBoolean("Custom Brewing Tab", categoryVanillaTabs, true, "Should Quality Order override the Brewing tab and sort it?");
		shouldOverrideMaterialTab = file.getBoolean("Custom Material Tab", categoryVanillaTabs, true, "Should Quality Order override the Materials tab and sort it?");
		shouldOverrideSearchTab = file.getBoolean("Custom Search Tab", categoryVanillaTabs, true, "Should Quality Order override the Search tab - this uses Reflection?");

		file.setCategoryRequiresMcRestart(categoryQualityTabs, true);
		shouldAddUnavailableTab = file.getBoolean("Unavailable Tab", categoryQualityTabs, true, "Should Quality Order's Unavailable tab be enabled?");
		shouldAddSkullsTab = file.getBoolean("Skulls Tab", categoryQualityTabs, true, "Should Quality Order's Skulls tab be enabled?");
		shouldAddBannersTab = file.getBoolean("Embroidery Tab", categoryQualityTabs, true, "Should Quality Order's Embroidery tab be enabled?");
		shouldAddThievingTab = file.getBoolean("Thieving Tab", categoryQualityTabs, true, "Should Quality Order's Thieving tab be enabled?");
		shouldAddFireworkTab = file.getBoolean("Firework Tab", categoryQualityTabs, true, "Should Quality Order's Firework tab be enabled?");
		shouldAddArmorStandTab = file.getBoolean("Armor Stands Tab", categoryQualityTabs, true, "Should Quality Order's Armor Stands tab be enabled?");
		shouldAddMapTab = file.getBoolean("Cartography Tab", categoryQualityTabs, true, "Should Quality Order's Cartography tab be enabled?");

		file.setCategoryRequiresMcRestart(categoryQualityTabSettings, false);
		cartographyNumberOfMaps = file.getInt("Cartography - Max Number of Maps", categoryQualityTabSettings, 100, 0, 30000, "Select the number of maps that should be on the Cartography tab. You can also disable it, if you don't want the tab at all.");
		cartographyRemoveUnknownMaps = file.getBoolean("Cartography - Remove Unknown Maps", categoryQualityTabSettings, true, "Choose if unknown maps should be included (false) or not (true)");
		thievingRange = file.getInt("Thieving - Range", categoryQualityTabSettings, 15, 1, 100, "How close you need to be to Copy somebody's items. If you don't want this feature, you can disable the tab.");
		skullsRange = file.getInt("Skulls - Range", categoryQualityTabSettings, 15, 0, 100, "Select the range of nearby players to get the skulls of. 0 will disable the feature.");
		skullsList = file.getStringList("Skulls - List", categoryQualityTabSettings, new String[] { "Friend1" }, "A list containing usernames of players whose skull should be added to the Skulls tab.");
		skullsMhfSkulls = file.getBoolean("Skulls - Enable MHF_Skulls", categoryQualityTabSettings, true, "Should the skulls tab contain MHF skulls?");

		syncCustomTabs();

		if (file.hasChanged()) {
			file.save();
		}
	}

	public static String getTabLabelFromID(int id) {
		return "customTabName" + id;
	}

	public static String getZeroedNumberFromID(int id) {
		if (id < 10) {
			return "00" + id;
		}
		else if (id < 100) {
			return "0" + id;
		}
		else return "" + id;
	}

	public static void initConfig(FMLPreInitializationEvent event) {
		file = new Configuration(event.getSuggestedConfigurationFile());
		syncConfig();
	}
}
