package com.legacy.aether.containers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

import com.legacy.aether.api.AetherRegistry;
import com.legacy.aether.api.accessories.AccessoryType;
import com.legacy.aether.containers.inventory.InventoryAccessories;
import com.legacy.aether.containers.slots.SlotAccessory;
import com.legacy.aether.player.PlayerAether;

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

				this.addSlotToContainer(new SlotAccessory(this.inventoryInstance, slotID, type, 59 + x * 18, 8 + y * 18, player));
				slotID++;
			}
		}
	}

	public int getAccessorySlot(AccessoryType type)
	{
		int slotID = 0;

		for (Slot checkSlot : (List<Slot>) this.inventorySlots)
		{
			if (checkSlot instanceof SlotAccessory && !checkSlot.getHasStack())
			{
				SlotAccessory accessorySlot = (SlotAccessory) checkSlot;

				if (accessorySlot.getAccessoryType() == type)
				{
					return slotID;
				}
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

				if (AetherRegistry.getInstance().isAccessory(stack))
				{
					newSlotIndex = this.getAccessorySlot(AetherRegistry.getInstance().getAccessory(stack).getAccessoryType());
				}

				if (newSlotIndex != -1)
				{
					Slot accessorySlot = this.inventorySlots.get(newSlotIndex);

					if (accessorySlot.isItemValid(stack))
					{
						accessorySlot.putStack(stack);
						slot.putStack((ItemStack) null);
					}

					return stack;
				}
			}
		}

		return super.transferStackInSlot(player, slotNumber);
	}

}