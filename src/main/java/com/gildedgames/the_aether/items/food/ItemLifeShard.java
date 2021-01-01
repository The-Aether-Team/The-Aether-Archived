package com.gildedgames.the_aether.items.food;

import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.items.ItemsAether;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemLifeShard extends Item
{

	public ItemLifeShard()
	{
		super();
		this.setMaxStackSize(1);
		this.setCreativeTab(AetherCreativeTabs.misc);
	}

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return ItemsAether.aether_loot;
    }

	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
	{
		IPlayerAether playerAether = AetherAPI.getInstance().get(player);
		ItemStack heldItem = player.getHeldItem(hand);

		if (!worldIn.isRemote)
		{
			playerAether.updateShardCount(0);

			if (playerAether.getShardsUsed() < playerAether.getMaxShardCount())
			{
				playerAether.updateShardCount(1);
				heldItem.shrink(1);
			}

			return new ActionResult<>(EnumActionResult.SUCCESS, heldItem);
		}
		else
		{
			if (playerAether.getShardsUsed() >= playerAether.getMaxShardCount())
			{
				Aether.proxy.sendMessage(player, new TextComponentTranslation("gui.item.life_shard.maxshards", playerAether.getMaxShardCount()));
			}
		}

		return new ActionResult<>(EnumActionResult.PASS, heldItem);
	}

}