package com.legacy.aether.containers.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.tile_entities.TileEntityIncubator;

public class SlotIncubator extends Slot
{

	private TileEntityIncubator incubator;

	private EntityPlayer player;

    public SlotIncubator(TileEntityIncubator incubator, int slot, int x, int y, EntityPlayer player)
    {
        super(incubator, slot, x, y);

        this.player = player;
        this.incubator = incubator;
    }

    @Override
    public void putStack(ItemStack stack)
    {
    	super.putStack(stack);

    	this.incubator.owner = player;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem() == ItemsAether.moa_egg;
    }

    @Override
    public int getSlotStackLimit()
    {
        return 1;
    }
}