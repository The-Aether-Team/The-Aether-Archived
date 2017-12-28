package com.legacy.aether.blocks.container;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.legacy.aether.tile_entities.util.AetherTileEntity;

public abstract class BlockAetherContainer extends BlockContainer
{

	public static final PropertyBool powered = PropertyBool.create("powered");

	public BlockAetherContainer(Material materialIn) 
	{
		super(materialIn);

		this.setDefaultState(this.getDefaultState().withProperty(powered, false));
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

	public static void setState(World worldIn, BlockPos pos, boolean isActive)
	{
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);

        worldIn.setBlockState(pos, iblockstate.withProperty(powered, isActive), 3);

        if (tileentity != null)
        {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
	}

	@Override
    public IBlockState getStateFromMeta(int meta)
    {
		return this.getDefaultState().withProperty(powered, meta == 1);
    }

	@Override
    public int getMetaFromState(IBlockState state)
    {
    	return state.getValue(powered) ? 1 : 0;
    }

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {powered});
	}

}