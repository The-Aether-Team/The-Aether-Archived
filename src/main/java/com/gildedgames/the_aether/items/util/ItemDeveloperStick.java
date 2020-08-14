package com.gildedgames.the_aether.items.util;

import com.gildedgames.the_aether.player.perks.AetherRankings;
import com.gildedgames.the_aether.Aether;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;

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
			Aether.proxy.sendMessage(playerIn, new TextComponentTranslation("YOu a cheeto or somethin'?"));
    		heldItem.shrink(1);
			return false;
		}
		else
		{
			Aether.proxy.sendMessage(playerIn, new TextComponentTranslation("gui.item.developer_stick.notdev"));
    		heldItem.shrink(1);
			return false;
		}
    }

}