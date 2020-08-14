package com.gildedgames.the_aether.items.staffs;

import com.gildedgames.the_aether.entities.passive.EntityMiniCloud;
import com.gildedgames.the_aether.player.PlayerAether;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.items.ItemsAether;

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
		IPlayerAether playerAether = AetherAPI.getInstance().get(entityplayer);
		ItemStack heldItem = entityplayer.getHeldItem(hand);

		if (((PlayerAether)playerAether).clouds.isEmpty())
		{
			EntityMiniCloud leftCloud = new EntityMiniCloud(world, entityplayer, 0);
			EntityMiniCloud rightCloud = new EntityMiniCloud(world, entityplayer, 1);

			((PlayerAether)playerAether).clouds.add(leftCloud);
			((PlayerAether)playerAether).clouds.add(rightCloud);

			world.spawnEntity(leftCloud);
			world.spawnEntity(rightCloud);

			heldItem.damageItem(1, entityplayer);

	    	return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, heldItem);
		}

    	return new ActionResult<ItemStack>(EnumActionResult.PASS, heldItem);
    }

}