package com.gildedgames.the_aether.inventory.slots;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.events.AetherHooks;
import com.gildedgames.the_aether.client.ClientProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.gildedgames.the_aether.api.accessories.AccessoryType;
import com.gildedgames.the_aether.api.accessories.AetherAccessory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SlotAccessory extends Slot {

	private EntityPlayer instance;

	private AccessoryType accessoryType;

	public SlotAccessory(IInventory inventory, int slotID, AccessoryType accessoryType, int x, int y, EntityPlayer instance) {
		super(inventory, slotID, x, y);

		this.instance = instance;
		this.accessoryType = accessoryType;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if (AetherAPI.instance().isAccessory(stack)) {
			AetherAccessory accessory = AetherAPI.instance().getAccessory(stack);

			if (accessory.getAccessoryType() == this.getAccessoryType() || accessory.getExtraType() == this.getAccessoryType()) {
				return AetherHooks.isValidAccessory(this.instance, accessory);
			}
		}

		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getBackgroundIconIndex() {
		return ClientProxy.ACCESSORY_ICONS[this.getSlotIndex()];
	}

	public AccessoryType getAccessoryType() {
		return this.accessoryType;
	}

}