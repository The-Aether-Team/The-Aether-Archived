package com.gildedgames.the_aether.containers.slots;

import com.gildedgames.the_aether.containers.inventory.InventoryAccessories;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.accessories.AccessoryType;
import com.gildedgames.the_aether.api.accessories.AetherAccessory;
import com.gildedgames.the_aether.api.events.AetherHooks;

public class SlotAccessory extends Slot
{

	private EntityPlayer instance;

	private AccessoryType accessoryType;

	public SlotAccessory(IInventory inventory, int slotID, AccessoryType accessoryType, int x, int y, EntityPlayer instance)
	{
		super(inventory, slotID, x, y);
		
		this.instance = instance;
		this.accessoryType = accessoryType;
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		if (AetherAPI.getInstance().isAccessory(stack))
		{
			AetherAccessory accessory = AetherAPI.getInstance().getAccessory(stack);

			if (accessory.getAccessoryType() == this.getAccessoryType())
			{
				return AetherHooks.isValidAccessory(this.instance, accessory);
			}
		}

		return false;
	}

	@Override
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