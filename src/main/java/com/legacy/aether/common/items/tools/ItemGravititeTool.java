package com.legacy.aether.common.items.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import com.legacy.aether.common.entities.block.EntityFloatingBlock;
import com.legacy.aether.common.items.util.EnumAetherToolType;

public class ItemGravititeTool extends ItemAetherTool
{

	public ItemGravititeTool(EnumAetherToolType toolType) 
	{
		super(ToolMaterial.DIAMOND, toolType);
	}

	@Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	if ((this.getStrVsBlock(stack, world.getBlockState(pos)) == this.efficiencyOnProperMaterial || ForgeHooks.isToolEffective(world, pos, stack)) && world.isAirBlock(pos.up()))
    	{
        	if (world.getTileEntity(pos) != null)
        	{
        		return EnumActionResult.FAIL;
        	}

        	if (!world.isRemote)
        	{
            	EntityFloatingBlock entity = new EntityFloatingBlock(world, pos, world.getBlockState(pos));
        		world.spawnEntityInWorld(entity);
        		world.setBlockToAir(pos);
        	}

        	stack.damageItem(4, player);
    	}

        return EnumActionResult.SUCCESS;
    }

}