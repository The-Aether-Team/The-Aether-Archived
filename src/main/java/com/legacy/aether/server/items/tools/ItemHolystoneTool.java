package com.legacy.aether.server.items.tools;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.legacy.aether.server.items.ItemsAether;
import com.legacy.aether.server.items.util.EnumAetherToolType;

public class ItemHolystoneTool extends ItemAetherTool
{

	public ItemHolystoneTool(EnumAetherToolType toolType)
	{
		super(ToolMaterial.STONE, toolType);
	}

    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
	{
		if (!world.isRemote && world.rand.nextInt(100) <= 5)
		{
			EntityItem entityItem = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ());
			entityItem.setEntityItemStack(new ItemStack(ItemsAether.ambrosium_shard, 1));

			world.spawnEntityInWorld(entityItem);
		}

		return super.onBlockDestroyed(stack, world, state, pos, entityLiving);
	}

}