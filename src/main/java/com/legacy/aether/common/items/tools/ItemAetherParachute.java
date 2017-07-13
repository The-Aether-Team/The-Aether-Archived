package com.legacy.aether.common.items.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import com.legacy.aether.common.entities.passive.mountable.EntityParachute;
import com.legacy.aether.common.items.ItemsAether;
import com.legacy.aether.common.registry.creative_tabs.AetherCreativeTabs;

public class ItemAetherParachute extends Item
{

	public ItemAetherParachute()
	{
		this.setMaxDamage(20);
		this.setMaxStackSize(1);
		this.setCreativeTab(AetherCreativeTabs.misc);
    }

    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entityplayer, EnumHand hand)
    {
    	ItemStack heldItem = entityplayer.getHeldItem(hand);

    	if(EntityParachute.entityHasRoomForCloud(world, entityplayer))
    	{
			if(this == ItemsAether.golden_parachute)
			{
				heldItem.damageItem(1, entityplayer);
			} 
			else 
			{
				heldItem.shrink(1);
			}

			world.spawnEntity(new EntityParachute(world, entityplayer, this == ItemsAether.golden_parachute));

	        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, heldItem);
    	}

        return super.onItemRightClick(world, entityplayer, hand);
    }

	public int getColorFromItemStack(ItemStack stack, int renderPass)
	{
		if(this == ItemsAether.golden_parachute) return 0xffff7f;

		return 0xffffff;
	}

}