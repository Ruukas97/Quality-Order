package ruukas.qualityorder.gui;

import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.IModGuiFactory;

public class QualityGuiFactory implements IModGuiFactory {

	@Override
	public void initialize(Minecraft minecraftInstance) {		
	}

	@Override
	public boolean hasConfigGui() {
		return true;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen) {
		return new GuiQualityConfig(parentScreen);
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		// TODO Auto-generated method stub
		return null;
	}
}