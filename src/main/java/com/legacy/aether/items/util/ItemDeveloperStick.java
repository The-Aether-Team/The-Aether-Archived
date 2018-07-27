package com.legacy.aether.items.util;

import com.legacy.aether.Aether;
import com.legacy.aether.player.perks.AetherRankings;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class ItemDeveloperStick extends Item
{

	public ItemDeveloperStick()
	{
		this.setCreativeTab(null);
	}
	
	@Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
    {
		ItemStack heldItem = playerIn.getHeldItem(hand);
		
		if (AetherRankings.isRankedPlayer(playerIn.getUniqueID()))
		{
			playerIn.startRiding(target);
			return true;
		}
		else if (playerIn.getUniqueID().toString().equals("cf51ef47-04a8-439a-aa41-47d871b0b837"))
		{
			Aether.proxy.sendMessage(playerIn, "YOu a cheeto or somethin'?");
    		heldItem.shrink(1);
			return false;
		}
		else
		{
			Aether.proxy.sendMessage(playerIn, "You aren't skilled enough to use this item!");
    		heldItem.shrink(1);
			return false;
		}
    }

}