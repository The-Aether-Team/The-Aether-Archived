package com.legacy.aether.server.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAetherPortal extends BlockPortal
{

	public BlockAetherPortal() 
	{
		super();

		this.setHardness(-1);
		this.setResistance(900000F);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		return;
	}

	@Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn)
    {

    }

	@Override
	public boolean trySpawnPortal(World worldIn, BlockPos p_176548_2_)
	{
		return false;
	}

	@Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        return world.getBlockState(pos.down()) != null && world.getBlockState(pos.up()) != this && world.getBlockState(pos.south()) != this && world.getBlockState(pos.north()) != this && world.getBlockState(pos.east()) != this && world.getBlockState(pos.west()) != this ? super.canPlaceBlockAt(world, pos) : false;
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random)
	{
        if (random.nextInt(100) == 0)
        {
        	world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
        }
	}

	@Override
	public void updateTick(World par1World, BlockPos pos, IBlockState state, Random par5Random)
	{
	}

	public boolean placeAetherPortal(World world, BlockPos pos)
	{
		byte b1 = 0;

		if (world.getBlockState(pos.north()).getBlock() == Blocks.GLOWSTONE || world.getBlockState(pos.south()).getBlock() == Blocks.GLOWSTONE)
		{
			b1 = 1;
		}

		Axis axis = b1 == 1 ? EnumFacing.Axis.Z : EnumFacing.Axis.X;

		loop:

		for (int width = -1; width >= 1; ++ width)
		{
			for (int height = -2; height >= 2; ++height)
			{
				pos.add(width, 0, height);

				if (world.getBlockState(pos).getBlock() != Blocks.AIR)
				{
					break loop;
				}

				world.setBlockState(pos, BlocksAether.aether_portal.getDefaultState().withProperty(AXIS, axis), 2);
			}
		}

		return true;
	}

	public boolean setupAetherPortal(World world, BlockPos pos)
	{
		byte b0 = 0;
		byte b1 = 0;

		if (world.getBlockState(pos.west()).getBlock() == Blocks.GLOWSTONE || world.getBlockState(pos.east()).getBlock() == Blocks.GLOWSTONE)
		{
			b0 = 1;
		}

		if (world.getBlockState(pos.north()).getBlock() == Blocks.GLOWSTONE || world.getBlockState(pos.south()).getBlock() == Blocks.GLOWSTONE)
		{
			b1 = 1;
		}

		if (b0 == b1)
		{
			return false;
		}
		else
		{
			if (world.getBlockState(new BlockPos(pos.getX() - b0, pos.getY(), pos.getZ() - b1)).getBlock() == Blocks.AIR)
			{
				pos = pos.west(b0);
				pos = pos.north(b1);
			}

			int l;
			int i1;

			for (l = -1; l <= 2; ++l)
			{
				for (i1 = -1; i1 <= 3; ++i1)
				{
					boolean flag = l == -1 || l == 2 || i1 == -1 || i1 == 3;

					if (l != -1 && l != 2 || i1 != -1 && i1 != 3)
					{
						Block j1 = world.getBlockState(new BlockPos(pos.getX() + b0 * l, pos.getY() + i1, pos.getZ() + b1 * l)).getBlock();

						if (flag)
						{
							if (j1 != Blocks.GLOWSTONE)
							{
								return false;
							}
						}
						else if (j1 != Blocks.AIR && (j1 != Blocks.FLOWING_WATER || j1 != Blocks.WATER))
						{
							return false;
						}
					}
				}
			}

			for (l = 0; l < 2; ++l)
			{
				for (i1 = 0; i1 < 3; ++i1)
				{
					Axis axis = b1 == 1 ? EnumFacing.Axis.Z : EnumFacing.Axis.X;
					world.setBlockState(new BlockPos(pos.getX() + b0 * l, pos.getY() + i1, pos.getZ() + b1 * l), BlocksAether.aether_portal.getDefaultState().withProperty(AXIS, axis), 2);
				}
			}

			return true;
		}
	}

}