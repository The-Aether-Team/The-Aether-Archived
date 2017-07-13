package com.legacy.aether.common.containers.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.common.containers.inventory.InventoryAccessories;
import com.legacy.aether.common.containers.util.AccessoryType;
import com.legacy.aether.common.items.accessories.ItemAccessory;

public class SlotAccessory extends Slot
{

	private AccessoryType accessoryType;

	public SlotAccessory(IInventory inventory, int slotID, AccessoryType accessoryType, int x, int y)
	{
		super(inventory, slotID, x, y);
		
		this.accessoryType = accessoryType;
	}

	@Override
	public boolean isItemValid(ItemStack itemstack)
	{
		if (itemstack.getItem() instanceof ItemAccessory)
		{
			ItemAccessory accessory = (ItemAccessory)itemstack.getItem();
			
			return accessory.getType() == this.accessoryType;
		}
		else
		{
			return false;
		}
	}

    @SideOnly(Side.CLIENT)
    public String getSlotTexture()
    {
        return "aether_legacy:items/slots/" + InventoryAccessories.EMPTY_SLOT_NAMES[this.getSlotIndex()];
    }

	public AccessoryType getAccessoryType()
	{
		return this.accessoryType;
	}

}