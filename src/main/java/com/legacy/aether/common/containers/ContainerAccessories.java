package com.legacy.aether.common.containers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

import com.legacy.aether.common.containers.inventory.InventoryAccessories;
import com.legacy.aether.common.containers.slots.SlotAccessory;
import com.legacy.aether.common.containers.util.AccessoryType;
import com.legacy.aether.common.items.accessories.ItemAccessory;
import com.legacy.aether.common.player.PlayerAether;

public class ContainerAccessories extends ContainerPlayer
{

	public EntityPlayer player;

	public PlayerAether playerAether;

	private InventoryAccessories inventoryInstance;

	public ContainerAccessories(InventoryAccessories inventory, EntityPlayer player)
	{
		super(player.inventory, !player.worldObj.isRemote, player);

		this.player = player;
		this.playerAether = PlayerAether.get(player);
		this.inventoryInstance = inventory;

		for (Slot slot : (ArrayList<Slot>) this.inventorySlots)
		{
			if (slot.slotNumber > 0 && slot.slotNumber < 5)
			{
				slot.xDisplayPosition += 18;
			}

			if (slot.slotNumber > 4 && slot.slotNumber < 9)
			{
				slot.xDisplayPosition += 51;
			}
			else if (slot.slotNumber == 45)
			{
				slot.xDisplayPosition += 39;
			}
		}

		int slotID = 0;

		for (int x = 1; x < 3; x++)
		{
			for (int y = 0; y < 4; y++)
			{
				AccessoryType type = this.playerAether.accessories.slotTypes[slotID];

				this.addSlotToContainer(new SlotAccessory(inventoryInstance, slotID, type, 59 + x * 18, 8 + y * 18));
				slotID++;
			}
		}
	}

	public int getAccessorySlotID(AccessoryType type)
	{
		int slotID = 0;

		for (Slot checkSlot : (List<Slot>) this.inventorySlots)
		{
			if (type.isSlotValid(checkSlot) && checkSlot.getStack() == null)
			{
				return slotID;
			}

			slotID++;
		}

		return -1;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber)
	{
		Slot slot = (Slot) this.inventorySlots.get(slotNumber);

		if (slot != null && slot.getHasStack())
		{
			ItemStack stack = slot.getStack();

			if (!(slot instanceof SlotAccessory) && !(slot instanceof SlotCrafting))
			{
				int newSlotIndex = -1;

				if (stack.getItem() instanceof ItemAccessory)
				{
					ItemAccessory accessory = (ItemAccessory) stack.getItem();

					newSlotIndex = this.getAccessorySlotID(accessory.getType());
				}

				if (newSlotIndex != -1)
				{
					Slot accessorySlot = (SlotAccessory) this.inventorySlots.get(newSlotIndex);
					accessorySlot.putStack(stack);
					slot.putStack((ItemStack) null);

					return stack;
				}
			}
		}

		return super.transferStackInSlot(player, slotNumber);
	}

}