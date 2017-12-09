package com.legacy.aether.items.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.entities.block.EntityFloatingBlock;
import com.legacy.aether.items.util.EnumAetherToolType;

public class ItemGravititeTool extends ItemAetherTool
{

	public ItemGravititeTool(EnumAetherToolType toolType) 
	{
		super(ToolMaterial.DIAMOND, toolType);
	}

	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return repair.getItem() == Item.getItemFromBlock(BlocksAether.enchanted_gravitite);
	}

	@Override
	@SuppressWarnings("deprecation")
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		ItemStack heldItem = player.getHeldItem(hand);

    	if ((this.getStrVsBlock(heldItem, world.getBlockState(pos)) == this.efficiencyOnProperMaterial || ForgeHooks.isToolEffective(world, pos, heldItem)) && world.isAirBlock(pos.up()))
    	{
        	if (world.getTileEntity(pos) != null || world.getBlockState(pos).getBlock().getBlockHardness(world.getBlockState(pos), world, pos) == -1.0F)
        	{
        		return EnumActionResult.FAIL;
        	}

        	if (!world.isRemote)
        	{
            	EntityFloatingBlock entity = new EntityFloatingBlock(world, pos, world.getBlockState(pos));
        		world.spawnEntity(entity);
        		world.setBlockToAir(pos);
        	}

        	heldItem.damageItem(4, player);
    	}

        return EnumActionResult.SUCCESS;
    }

}