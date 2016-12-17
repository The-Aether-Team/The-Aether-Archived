package com.legacy.aether.server.containers.inventory;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

import com.legacy.aether.server.items.ItemsAether;
import com.legacy.aether.server.registry.achievements.AchievementsAether;

public class InventoryLore extends InventoryBasic
{

	private EntityPlayer player;

	public InventoryLore(EntityPlayer player) 
	{
		super("Lore Item", false, 1);
		this.player = player;
	}

	@Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack)
    {
    	if (player != null && stack != null && stack.getItem() == ItemsAether.lore_book)
    	{
    		player.addStat(AchievementsAether.loreception);
    	}

    	super.setInventorySlotContents(index, stack);
    }

}