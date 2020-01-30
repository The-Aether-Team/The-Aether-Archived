package com.legacy.aether.blocks.util;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.decorative.BlockAetherGrassPath;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockUtil
{
	public static boolean tryPathBlock(PropertyBool doubleDrop, World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side)
	{
		ItemStack held = player.getHeldItem(hand);
    	if (held.isEmpty()) return false;

    	if (!player.canPlayerEdit(pos.offset(side), side, held)) return false;

    	int harvestLevel = held.getItem().getHarvestLevel(held, "shovel", player, state);
		if (harvestLevel == -1) return false;

		if (world.getBlockState(pos.up()).getMaterial() != Material.AIR) return false;

		world.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
		player.swingArm(hand);

    	if (!world.isRemote)
		{
			IBlockState newState = BlocksAether.aether_grass_path.getDefaultState().withProperty(BlockAetherGrassPath.double_drop, state.getValue(doubleDrop));
			world.setBlockState(pos, newState);
			held.damageItem(1, player);
		}

		return true;
	}
}
