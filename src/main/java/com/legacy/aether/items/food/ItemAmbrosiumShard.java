package com.legacy.aether.items.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class ItemAmbrosiumShard extends Item
{

	public ItemAmbrosiumShard()
	{
		this.setCreativeTab(AetherCreativeTabs.material);
	}

	@Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		ItemStack heldItem = playerIn.getHeldItem(hand);

    	if (worldIn.getBlockState(pos).getBlock() == BlocksAether.aether_grass)
    	{
    		worldIn.setBlockState(pos, BlocksAether.enchanted_aether_grass.getDefaultState());
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

	@Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
		ItemStack heldItem = playerIn.getHeldItem(hand);

    	if (playerIn.shouldHeal())
    	{
        	if (!playerIn.capabilities.isCreativeMode)
        	{
        		heldItem.shrink(1);
        	}
        	
    		playerIn.heal(2F);

    		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, heldItem);
    	}

    	return super.onItemRightClick(worldIn, playerIn, hand);
    }

}