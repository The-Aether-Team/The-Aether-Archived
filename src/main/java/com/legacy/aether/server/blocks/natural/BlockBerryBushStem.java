package com.legacy.aether.server.blocks.natural;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.server.blocks.BlocksAether;

public class BlockBerryBushStem extends BlockAetherFlower
{

	public BlockBerryBushStem() 
	{
		this.FLOWER_AABB = new AxisAlignedBB(0.5F - 0.4F, 0.0F, 0.5F - 0.4F, 0.5F + 0.4F, 0.4F * 2.0F, 0.5F + 0.4F);
		this.setHardness(1.0F);
		this.setSoundType(SoundType.PLANT);
	}

    @SideOnly(Side.CLIENT)
    public Block.EnumOffsetType getOffsetType()
    {
        return Block.EnumOffsetType.NONE;
    }

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		if (world.isRemote)
		{
			return;
		}

		super.updateTick(world, pos, state, random);

		if (world.getLight(pos.up()) >= 9 && random.nextInt(60) == 0)
		{
			world.setBlockState(pos, BlocksAether.berry_bush.getDefaultState());
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World world, BlockPos pos)
	{
		return null;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return this.FLOWER_AABB;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
    	return true;
    }

}