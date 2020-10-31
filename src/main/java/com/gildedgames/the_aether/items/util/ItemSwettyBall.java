package com.gildedgames.the_aether.items.util;

import com.gildedgames.the_aether.blocks.BlocksAether;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSwettyBall extends Item
{

	public ItemSwettyBall(CreativeTabs tab)
	{
		this.setCreativeTab(tab);
	}
	
	@Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		ItemStack heldItem = playerIn.getHeldItem(hand);

    	if (worldIn.getBlockState(pos).getBlock() == BlocksAether.aether_dirt)
    	{
    		worldIn.setBlockState(pos, BlocksAether.aether_grass.getDefaultState());
    	}
    	else if (worldIn.getBlockState(pos).getBlock() == Blocks.DIRT)
    	{
    		worldIn.setBlockState(pos, Blocks.GRASS.getDefaultState());
    	}
    	else
    	{
    		return EnumActionResult.FAIL;
    	}

    	if (!playerIn.capabilities.isCreativeMode)
    	{
    		heldItem.shrink(1);
    	}

        return EnumActionResult.SUCCESS;
    }

}