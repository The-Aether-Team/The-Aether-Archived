package com.gildedgames.the_aether.inventory;

import java.util.ArrayList;
import java.util.List;

import com.gildedgames.the_aether.api.AetherAPI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ObjectIntIdentityMap;

import com.gildedgames.the_aether.api.accessories.AccessoryType;
import com.gildedgames.the_aether.api.accessories.AetherAccessory;
import com.gildedgames.the_aether.api.player.util.IAccessoryInventory;
import com.gildedgames.the_aether.inventory.slots.SlotAccessory;
import com.gildedgames.the_aether.player.PlayerAether;

public class ContainerAccessories extends ContainerPlayer {

	public EntityPlayer player;

	public PlayerAether playerAether;

	private IAccessoryInventory inventoryInstance;

	private ObjectIntIdentityMap orderedList = AccessoryType.createCompleteList();

	@SuppressWarnings("unchecked")
	public ContainerAccessories(IAccessoryInventory inventory, EntityPlayer player) {
		super(player.inventory, !player.worldObj.isRemote, player);

		this.player = player;
		this.playerAether = PlayerAether.get(player);
		this.inventoryInstance = inventory;

		for (Slot slot : (ArrayList<Slot>) this.inventorySlots) {
			if (slot.slotNumber == 0) {
				slot.xDisplayPosition += 10;
				slot.yDisplayPosition -= 8;
			} else if (slot.slotNumber > 0 && slot.slotNumber < 5) {
				slot.xDisplayPosition += 28;
				slot.yDisplayPosition -= 8;
			}

			if (slot.slotNumber > 4 && slot.slotNumber < 9) {
				slot.xDisplayPosition += 51;
			}
		}

		int slotID = 0;

		for (int x = 1; x < 3; x++) {
			for (int y = 0; y < 4; y++) {
				AccessoryType type = (AccessoryType) this.orderedList.func_148745_a(slotID);

				this.addSlotToContainer(new SlotAccessory(inventoryInstance, slotID, type, 59 + x * 18, 8 + y * 18, player));
				slotID++;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public int getAccessorySlot(AccessoryType type) {
		int slotID = 0;

		for (Slot checkSlot : (List<Slot>) this.inventorySlots) {
			if (checkSlot instanceof SlotAccessory && !checkSlot.getHasStack()) {
				SlotAccessory accessorySlot = (SlotAccessory) checkSlot;

				if (accessorySlot.getAccessoryType() == type) {
					return slotID;
				}
			}

			slotID++;
		}

		return -1;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
		Slot slot = (Slot) this.inventorySlots.get(slotNumber);

		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();

			if (!(slot instanceof SlotAccessory) && !(slot instanceof SlotCrafting)) {
				int newSlotIndex = -1;

				if (AetherAPI.instance().isAccessory(stack)) {
					AetherAccessory accessory = AetherAPI.instance().getAccessory(stack);

					newSlotIndex = this.getAccessorySlot(accessory.getAccessoryType());

					if (newSlotIndex == -1 && accessory.getExtraType() != null) {
						newSlotIndex = this.getAccessorySlot(accessory.getExtraType());
					}
				}

				if (newSlotIndex != -1) {
					Slot accessorySlot = (SlotAccessory) this.inventorySlots.get(newSlotIndex);
					accessorySlot.putStack(stack);
					slot.putStack(null);

					return stack;
				}
			}
		}

		return super.transferStackInSlot(player, slotNumber);
	}

}