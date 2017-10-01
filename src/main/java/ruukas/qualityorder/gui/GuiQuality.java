package ruukas.qualityorder.gui;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.inventory.CreativeCrafting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ruukas.qualityorder.QualityOrder;

public class GuiQuality extends InventoryEffectRenderer {

	private static final ResourceLocation background = new ResourceLocation(QualityOrder.MODID, "textures/gui/fireworkmaker.png");
	// private EntityLivingBase mob;
	private static final InventoryBasic item = new InventoryBasic("tmp", false, 1);
	private CreativeCrafting listener;

	public GuiQuality(EntityPlayer player) {
		super(new ContainerQualityItem(player));

		player.openContainer = this.inventorySlots;
		this.allowUserInput = false;

		this.xSize = 162;
		this.ySize = 40;
	}

	@Override
	public void initGui() {
		super.initGui();
		this.guiLeft = (int) (this.width - this.xSize) / 2;
		this.guiTop = (int) (this.height - this.ySize - 5);

		this.listener = new CreativeCrafting(this.mc);
		this.mc.player.inventoryContainer.addListener(this.listener);
	}

	@SideOnly(Side.CLIENT)
	public ItemStack getCurrentItem() {
		return item.getStackInSlot(0);
	}

	@SideOnly(Side.CLIENT)
	private void setCurrentItem(ItemStack stack) {
		item.setInventorySlotContents(0, stack);
	}

	/*
	 * @Override
	 * 
	 * @SideOnly(Side.CLIENT) public void drawScreen(int mouseX, int mouseY,
	 * float partialTicks) { super.drawScreen(mouseX, mouseY, partialTicks); }
	 */

	@Override
	@SideOnly(Side.CLIENT)
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.disableBlend();
		// this.fontRendererObj.drawString(I18n.format("This GUI is work in
		// progress. Currently, it only previews your firework. More features
		// will be added later.", new Object[0]), 8, 6, 0xffffff);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(background);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();

		if (this.mc.player != null && this.mc.player.inventoryContainer != null) {
			this.mc.player.inventoryContainer.removeListener(this.listener);
		}
	}

	/**
	 * Called when the mouse is clicked over a slot or outside the gui.
	 */
	protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
		boolean isShift = type == ClickType.QUICK_MOVE;
		type = slotId == -999 && type == ClickType.PICKUP ? ClickType.THROW : type;
		// Drop item that mouse is holding, if no slot
		if (slotIn == null && type != ClickType.QUICK_CRAFT) {
			InventoryPlayer inventoryplayer1 = this.mc.player.inventory;

			if (inventoryplayer1.getItemStack() != null && inventoryplayer1.getItemStack() != ItemStack.EMPTY) {
				if (mouseButton == 0) {
					this.mc.player.dropItem(inventoryplayer1.getItemStack(), true);
					this.mc.playerController.sendPacketDropItem(inventoryplayer1.getItemStack());
					inventoryplayer1.setItemStack(ItemStack.EMPTY);
				}

				if (mouseButton == 1) {
					ItemStack itemstack5 = inventoryplayer1.getItemStack().splitStack(1);
					this.mc.player.dropItem(itemstack5, true);
					this.mc.playerController.sendPacketDropItem(itemstack5);

					if (inventoryplayer1.getItemStack().getCount() == 0) {
						inventoryplayer1.setItemStack(ItemStack.EMPTY);
					}
				}
			}
		}

		// In banner result inventory
		else if (type != ClickType.QUICK_CRAFT && slotIn.inventory == item) {
			InventoryPlayer inventoryplayer = this.mc.player.inventory;
			ItemStack handStack = inventoryplayer.getItemStack();
			ItemStack slotStack = slotIn.getStack();

			if (slotIn.getSlotIndex() == 0) {
				// Pressing a number 1-9
				if (type == ClickType.SWAP) {
					if (slotStack != null && slotStack != ItemStack.EMPTY && mouseButton >= 0 && mouseButton < 9) {
						this.mc.playerController.sendSlotPacket(slotStack, mouseButton);
						this.mc.player.inventory.setInventorySlotContents(mouseButton, slotStack);
						setCurrentItem(ItemStack.EMPTY);
						this.mc.player.inventoryContainer.detectAndSendChanges();
					}

					return;
				}

				// Mouse wheel
				if (type == ClickType.CLONE) {
					if ((inventoryplayer.getItemStack() == null || inventoryplayer.getItemStack() == ItemStack.EMPTY) && slotIn.getHasStack()) {
						ItemStack itemstack6 = slotIn.getStack().copy();
						itemstack6.setCount(itemstack6.getMaxStackSize());
						inventoryplayer.setItemStack(itemstack6);
					}

					return;
				}

				// Pressing Q - Mouse button is 1 if Ctrl is down
				if (type == ClickType.THROW) {
					if (slotStack != null && slotStack != ItemStack.EMPTY) {
						this.mc.player.dropItem(slotStack, true);
						this.mc.playerController.sendPacketDropItem(slotStack);
					}
					return;
				}

				if (slotStack != null && slotStack != ItemStack.EMPTY && (handStack == null || handStack == ItemStack.EMPTY)) {
					inventoryplayer.setItemStack(slotStack);
					handStack = inventoryplayer.getItemStack();

					if (isShift) {
						handStack.setCount(handStack.getMaxStackSize());
					}

					setCurrentItem(ItemStack.EMPTY);
					return;
				}
				else if (handStack != null && handStack != ItemStack.EMPTY) {
					ItemStack old2 = (slotStack != null && slotStack != ItemStack.EMPTY) ? slotStack.copy() : ItemStack.EMPTY;
					setCurrentItem(handStack);
					inventoryplayer.setItemStack(old2);
					return;
				}
			}
			return;
		}
		else {
			// Taken from "if tab == inventory"
			if (type == ClickType.THROW && slotIn != null && slotIn.getHasStack()) {
				ItemStack itemstack = slotIn.decrStackSize(mouseButton == 0 ? 1 : slotIn.getStack().getMaxStackSize());
				this.mc.player.dropItem(itemstack, true);
				this.mc.playerController.sendPacketDropItem(itemstack);
			}
			else if (type == ClickType.THROW && this.mc.player.inventory.getItemStack() != null && this.mc.player.inventory.getItemStack() != ItemStack.EMPTY) {
				this.mc.player.dropItem(this.mc.player.inventory.getItemStack(), true);
				this.mc.playerController.sendPacketDropItem(this.mc.player.inventory.getItemStack());
				this.mc.player.inventory.setItemStack(ItemStack.EMPTY);
			}
			else {
				if (isShift) {
					type = ClickType.PICKUP;
				}
				this.mc.player.inventoryContainer.slotClick((slotIn == null ? slotId : slotIn.slotNumber) + 35, mouseButton, type, this.mc.player);
				this.mc.player.inventoryContainer.detectAndSendChanges();
			}
			// End of == inventory
		}
	}

	@SideOnly(Side.CLIENT)
	static class ContainerQualityItem extends Container {

		public List<ItemStack> itemList = Lists.<ItemStack> newArrayList();

		public ContainerQualityItem(EntityPlayer player) {
			InventoryPlayer inventoryplayer = player.inventory;

			// Quality Item Slot
			this.addSlotToContainer(new Slot(GuiQuality.item, 0, 73, 3));

			// Player's actionbar
			for (int i = 0; i < 9; ++i) {
				this.addSlotToContainer(new Slot(inventoryplayer, i, i * 18 + 1, 23));
			}

		}

		@Override
		public boolean canInteractWith(EntityPlayer playerIn) {
			return true;
		}

		/**
		 * Take a stack from the specified inventory slot.
		 */
		@Override
		public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
			if (index >= this.inventorySlots.size() - 9 && index < this.inventorySlots.size()) {
				Slot slot = (Slot) this.inventorySlots.get(index);

				if (slot != null && slot.getHasStack()) {
					slot.putStack(ItemStack.EMPTY);
				}
			}

			return ItemStack.EMPTY;
		}

		/**
		 * Called to determine if the current slot is valid for the stack
		 * merging (double-click) code. The stack passed in is null for the
		 * initial slot that was double-clicked.
		 */
		@Override
		public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
			return slotIn.yPos > 90;
		}

		/**
		 * Returns true if the player can "drag-spilt" items into this slot,.
		 * returns true by default. Called to check if the slot can be added to
		 * a list of Slots to split the held ItemStack across.
		 */
		@Override
		public boolean canDragIntoSlot(Slot slotIn) {
			return slotIn.inventory instanceof InventoryPlayer || slotIn.yPos > 90 && slotIn.xPos <= 162;
		}
	}
}
