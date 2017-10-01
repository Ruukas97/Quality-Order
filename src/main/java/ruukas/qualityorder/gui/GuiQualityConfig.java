package ruukas.qualityorder.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import ruukas.qualityorder.QualityOrder;
import ruukas.qualityorder.config.QualityOrderConfig;

public class GuiQualityConfig extends GuiConfig {
	public GuiQualityConfig(GuiScreen parent) {
		super(parent, getConfigElements(), QualityOrder.MODID, false, false, "Quality Order Configuration");
	}

	/** Compiles a list of config elements */
	private static List<IConfigElement> getConfigElements() {
		List<IConfigElement> list = new ArrayList<IConfigElement>();

		list.add(categoryElement(QualityOrderConfig.categoryQualityTabs, "Quality Order Tabs",
				"qualityorder.configcategory.qualityordertabs"));
		list.add(categoryElement(QualityOrderConfig.categoryQualityTabSettings, "Quality Order Tabs - Configurations",
				"qualityorder.configcategory.qualityordertabs"));
		list.add(categoryElement(QualityOrderConfig.categoryVanillaTabs, "Vanilla Tabs",
				"qualityorder.configcategory.vanillatabs"));
		list.add(categoryElement(QualityOrderConfig.categoryCustomTabs, "Custom Tabs",
				"qualityorder.configcategory.customtabs"));

		return list;
	}

	/**
	 * Creates a button linking to another screen where all options of the
	 * category are available
	 */
	private static IConfigElement categoryElement(String category, String name, String tooltip_key) {
		return new DummyConfigElement.DummyCategoryElement(name, tooltip_key,
				new ConfigElement(QualityOrderConfig.file.getCategory(category)).getChildElements());
	}
}