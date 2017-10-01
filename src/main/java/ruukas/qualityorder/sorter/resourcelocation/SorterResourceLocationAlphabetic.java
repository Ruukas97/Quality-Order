package ruukas.qualityorder.sorter.resourcelocation;

import java.util.Comparator;

import net.minecraft.util.ResourceLocation;

public class SorterResourceLocationAlphabetic implements Comparator<ResourceLocation> {
	public int compare(ResourceLocation resNeg, ResourceLocation resPos) {
		return resNeg.toString().compareToIgnoreCase(resPos.toString());
	}
}
