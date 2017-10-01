package ruukas.qualityorder.gui.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SlotQuality extends Slot{

	public SlotQuality(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public void onSlotChanged() {
		//Minecraft.getMinecraft().playerController.sendSlotPacket(getStack(), slotNumber-1);
		super.onSlotChanged();
		System.out.println("asdasd");
	}
}
