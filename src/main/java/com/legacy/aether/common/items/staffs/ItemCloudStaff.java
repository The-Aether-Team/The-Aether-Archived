package com.legacy.aether.common.items.staffs;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import com.legacy.aether.common.entities.passive.EntityMiniCloud;
import com.legacy.aether.common.items.ItemsAether;
import com.legacy.aether.common.player.PlayerAether;
import com.legacy.aether.common.registry.creative_tabs.AetherCreativeTabs;

public class ItemCloudStaff extends Item
{

	public ItemCloudStaff()
	{
		this.setCreativeTab(AetherCreativeTabs.misc);
		this.setMaxStackSize(1);
	}

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return ItemsAether.aether_loot;
    }

	@Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entityplayer, EnumHand hand)
    {
		PlayerAether playerAether = PlayerAether.get(entityplayer);
		ItemStack heldItem = entityplayer.getHeldItem(hand);

		if (playerAether.leftCloud == null && playerAether.rightCloud == null)
		{
			playerAether.leftCloud = new EntityMiniCloud(world, entityplayer, 0);
			playerAether.rightCloud = new EntityMiniCloud(world, entityplayer, 1);
		}

		if (!playerAether.leftCloud.hasSpawned)
		{
			world.spawnEntity(playerAether.leftCloud);
			playerAether.leftCloud.hasSpawned = true;
		}

		if (!playerAether.rightCloud.hasSpawned)
		{
			world.spawnEntity(playerAether.rightCloud);
			playerAether.leftCloud.hasSpawned = true;
		}

    	return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, heldItem);
    }

}