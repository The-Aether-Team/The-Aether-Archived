package com.gildedgames.the_aether.items.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.gildedgames.the_aether.blocks.BlocksAether;

public class ItemSwettyBall extends Item {

	public ItemSwettyBall(CreativeTabs tab) {
		this.setCreativeTab(tab);
	}

	@Override
	public boolean onItemUse(ItemStack stackIn, EntityPlayer playerIn, World worldIn, int x, int y, int z, int facing, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = playerIn.getHeldItem();

		if (worldIn.getBlock(x, y, z) == BlocksAether.aether_dirt) {
			worldIn.setBlock(x, y, z, BlocksAether.aether_grass);
		} else if (worldIn.getBlock(x, y, z) == Blocks.dirt) {
			worldIn.setBlock(x, y, z, Blocks.grass);
		} else {
			return false;
		}

		if (!playerIn.capabilities.isCreativeMode) {
			--heldItem.stackSize;
		}

		return true;
	}

}