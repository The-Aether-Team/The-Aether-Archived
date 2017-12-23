package com.legacy.aether.blocks.container;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.legacy.aether.tile_entities.util.AetherTileEntity;

public abstract class BlockAetherContainer extends BlockContainer
{

	public BlockAetherContainer(Material materialIn) 
	{
		super(materialIn);
	}

	@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if (stack.hasDisplayName())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof AetherTileEntity)
            {
                ((AetherTileEntity)tileentity).setCustomName(stack.getDisplayName());
            }
        }
    }

}