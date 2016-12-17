package com.legacy.aether.server.world.biome.decoration;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.legacy.aether.server.blocks.BlocksAether;
import com.legacy.aether.server.blocks.natural.BlockHolidayLeaves;
import com.legacy.aether.server.blocks.util.EnumHolidayType;

public class AetherGenHolidayTree extends WorldGenerator
{

	public AetherGenHolidayTree()
	{
		super(true);
	}

	@Override /* No time and effort was made into fixing this ~Kino*/
	public boolean generate(World world, Random rand, BlockPos position) 
	{
		Block huhu = world.getBlockState(position.down()).getBlock();

		if (huhu == BlocksAether.aether_grass)
		{
			for(int y1 = 0; y1 <= 8; y1++)
			{
				world.setBlockState(position.up(y1), BlocksAether.aether_log.getDefaultState());
			}

			world.setBlockState(position.up(9), getBlock(rand));

			world.setBlockState(position.add(0, 2, 1), getBlock(rand));
			world.setBlockState(position.add(0, 2, 2),  getBlock(rand));
			world.setBlockState(position.add(0, 2, 3),  getBlock(rand));
			world.setBlockState(position.add(0, 2, 4),  getBlock(rand));
			world.setBlockState(position.add(0, 2, -1),  getBlock(rand));
			world.setBlockState(position.add(0, 2, -2),  getBlock(rand));
			world.setBlockState(position.add(0, 2, -3),  getBlock(rand));
			world.setBlockState(position.add(0, 2, -4),  getBlock(rand));
			world.setBlockState(position.add(1, 2, 0),  getBlock(rand));
			world.setBlockState(position.add(2, 2, 0),  getBlock(rand));
			world.setBlockState(position.add(3, 2, 0),  getBlock(rand));
			world.setBlockState(position.add(4, 2, 0),  getBlock(rand));
			world.setBlockState(position.add(-1, 2, 0),  getBlock(rand));
			world.setBlockState(position.add(-2, 2, 0),  getBlock(rand));
			world.setBlockState(position.add(-3, 2, 0),  getBlock(rand));
			world.setBlockState(position.add(-4, 2, 0),  getBlock(rand));

			world.setBlockState(position.add(1, 2, 1),  getBlock(rand));
			world.setBlockState(position.add(1, 2, 2),  getBlock(rand));
			world.setBlockState(position.add(1, 2, 3),  getBlock(rand));
			world.setBlockState(position.add(1, 2, 4),  getBlock(rand));
			world.setBlockState(position.add(-1, 2, 1),  getBlock(rand));
			world.setBlockState(position.add(-1, 2, 2),  getBlock(rand));
			world.setBlockState(position.add(-1, 2, 3),  getBlock(rand));
			world.setBlockState(position.add(-1, 2, 4),  getBlock(rand));
			world.setBlockState(position.add(1, 2, -1),  getBlock(rand));
			world.setBlockState(position.add(1, 2, -2),  getBlock(rand));
			world.setBlockState(position.add(1, 2, -3),  getBlock(rand));
			world.setBlockState(position.add(1, 2, -4),  getBlock(rand));
			world.setBlockState(position.add(-1, 2, -1),  getBlock(rand));
			world.setBlockState(position.add(-1, 2, -2),  getBlock(rand));
			world.setBlockState(position.add(-1, 2, -3),  getBlock(rand));
			world.setBlockState(position.add(-1, 2, -4),  getBlock(rand));
			world.setBlockState(position.add(1, 2, 1),  getBlock(rand));
			world.setBlockState(position.add(2, 2, 1),  getBlock(rand));
			world.setBlockState(position.add(3, 2, 1),  getBlock(rand));
			world.setBlockState(position.add(4, 2, 1),  getBlock(rand));
			world.setBlockState(position.add(1, 2, -1),  getBlock(rand));
			world.setBlockState(position.add(2, 2, -1),  getBlock(rand));
			world.setBlockState(position.add(3, 2, -1),  getBlock(rand));
			world.setBlockState(position.add(4, 2, -1),  getBlock(rand));
			world.setBlockState(position.add(-1, 2, 1),  getBlock(rand));
			world.setBlockState(position.add(-2, 2, 1),  getBlock(rand));
			world.setBlockState(position.add(-3, 2, 1),  getBlock(rand));
			world.setBlockState(position.add(-4, 2, 1),  getBlock(rand));
			world.setBlockState(position.add(-1, 2, -1),  getBlock(rand));
			world.setBlockState(position.add(-2, 2, -1),  getBlock(rand));
			world.setBlockState(position.add(-3, 2, -1),  getBlock(rand));
			world.setBlockState(position.add(-4, 2, -1),  getBlock(rand));

			world.setBlockState(position.add(2, 2, 1),  getBlock(rand));
			world.setBlockState(position.add(3, 2, 1),  getBlock(rand));
			world.setBlockState(position.add(2, 2, 1),  getBlock(rand));

			world.setBlockState(position.add(-2, 2, 2),  getBlock(rand));
			world.setBlockState(position.add(-3, 2, 2),  getBlock(rand));
			world.setBlockState(position.add(-2, 2, 3),  getBlock(rand));

			world.setBlockState(position.add(2, 2, -2),  getBlock(rand));
			world.setBlockState(position.add(3, 2, -2),  getBlock(rand));
			world.setBlockState(position.add(2, 2, -3),  getBlock(rand));

			world.setBlockState(position.add(-2, 2, -2),  getBlock(rand));
			world.setBlockState(position.add(-3, 2, -2),  getBlock(rand));
			world.setBlockState(position.add(-2, 2, -3),  getBlock(rand));

			world.setBlockState(position.add(0, 3, 1),  getBlock(rand));
			world.setBlockState(position.add(0, 3, 2),  getBlock(rand));
			world.setBlockState(position.add(0, 3, 3),  getBlock(rand));
			world.setBlockState(position.add(0, 3, 4),  getBlock(rand));
			world.setBlockState(position.add(0, 3, -1),  getBlock(rand));
			world.setBlockState(position.add(0, 3, -2),  getBlock(rand));
			world.setBlockState(position.add(0, 3, -3),  getBlock(rand));
			world.setBlockState(position.add(0, 3, -4),  getBlock(rand));
			world.setBlockState(position.add(1, 3, 0),  getBlock(rand));
			world.setBlockState(position.add(2, 3, 0),  getBlock(rand));
			world.setBlockState(position.add(3, 3, 0),  getBlock(rand));
			world.setBlockState(position.add(4, 3, 0),  getBlock(rand));
			world.setBlockState(position.add(-1, 3, 0),  getBlock(rand));
			world.setBlockState(position.add(-2, 3, 0),  getBlock(rand));
			world.setBlockState(position.add(-3, 3, 0),  getBlock(rand));
			world.setBlockState(position.add(-4, 3, 0),  getBlock(rand));

			world.setBlockState(position.add(1, 3, 1),  getBlock(rand));
			world.setBlockState(position.add(1, 3, 2),  getBlock(rand));
			world.setBlockState(position.add(1, 3, 3),  getBlock(rand));
			world.setBlockState(position.add(-1, 3, 1),  getBlock(rand));
			world.setBlockState(position.add(-1, 3, 2),  getBlock(rand));
			world.setBlockState(position.add(-1, 3, 3),  getBlock(rand));
			world.setBlockState(position.add(1, 3, -1),  getBlock(rand));
			world.setBlockState(position.add(1, 3, -2),  getBlock(rand));
			world.setBlockState(position.add(1, 3, -3),  getBlock(rand));
			world.setBlockState(position.add(-1, 3, -1),  getBlock(rand));
			world.setBlockState(position.add(-1, 3, -2),  getBlock(rand));
			world.setBlockState(position.add(-1, 3, -3),  getBlock(rand));
			world.setBlockState(position.add(1, 3, 1),  getBlock(rand));
			world.setBlockState(position.add(2, 3, 1),  getBlock(rand));
			world.setBlockState(position.add(3, 3, 1),  getBlock(rand));
			world.setBlockState(position.add(1, 3, -1),  getBlock(rand));
			world.setBlockState(position.add(2, 3, -1),  getBlock(rand));
			world.setBlockState(position.add(3, 3, -1),  getBlock(rand));
			world.setBlockState(position.add(-1, 3, 1),  getBlock(rand));
			world.setBlockState(position.add(-2, 3, 1),  getBlock(rand));
			world.setBlockState(position.add(-3, 3, 1),  getBlock(rand));
			world.setBlockState(position.add(-1, 3, -1),  getBlock(rand));
			world.setBlockState(position.add(-2, 3, -1),  getBlock(rand));
			world.setBlockState(position.add(-3, 3, -1),  getBlock(rand));

			world.setBlockState(position.add(2, 3, 2),  getBlock(rand));
			world.setBlockState(position.add(3, 3, 2),  getBlock(rand));
			world.setBlockState(position.add(2, 3, 3),  getBlock(rand));

			world.setBlockState(position.add(-2, 3, 2),  getBlock(rand));
			world.setBlockState(position.add(-3, 3, 2),  getBlock(rand));
			world.setBlockState(position.add(-2, 3, 3),  getBlock(rand));

			world.setBlockState(position.add(2, 3, -2),  getBlock(rand));
			world.setBlockState(position.add(3, 3, -2),  getBlock(rand));
			world.setBlockState(position.add(2, 3, -3),  getBlock(rand));

			world.setBlockState(position.add(-2, 3, -1),  getBlock(rand));
			world.setBlockState(position.add(-3, 3, -2),  getBlock(rand));
			world.setBlockState(position.add(-2, 3, -3),  getBlock(rand));

			world.setBlockState(position.add(0, 4, 1),  getBlock(rand));
			world.setBlockState(position.add(0, 4, 2),  getBlock(rand));
			world.setBlockState(position.add(0, 4, 3),  getBlock(rand));
			world.setBlockState(position.add(0, 4, -1),  getBlock(rand));
			world.setBlockState(position.add(0, 4, -2),  getBlock(rand));
			world.setBlockState(position.add(0, 4, -3),  getBlock(rand));
			world.setBlockState(position.add(1, 4, 0),  getBlock(rand));
			world.setBlockState(position.add(2, 4, 0),  getBlock(rand));
			world.setBlockState(position.add(3, 4, 0),  getBlock(rand));
			world.setBlockState(position.add(-1, 4, 0),  getBlock(rand));
			world.setBlockState(position.add(-2, 4, 0),  getBlock(rand));
			world.setBlockState(position.add(-3, 4, 0),  getBlock(rand));

			world.setBlockState(position.add(1, 4, 1),  getBlock(rand));
			world.setBlockState(position.add(1, 4, 2),  getBlock(rand));
			world.setBlockState(position.add(1, 4, 3),  getBlock(rand));
			world.setBlockState(position.add(-1, 4, 1),  getBlock(rand));
			world.setBlockState(position.add(-1, 4, 2),  getBlock(rand));
			world.setBlockState(position.add(-1, 4, 3),  getBlock(rand));
			world.setBlockState(position.add(1, 4, -1),  getBlock(rand));
			world.setBlockState(position.add(1, 4, -2),  getBlock(rand));
			world.setBlockState(position.add(1, 4, -3),  getBlock(rand));
			world.setBlockState(position.add(-1, 4, -1),  getBlock(rand));
			world.setBlockState(position.add(-1, 4, -2),  getBlock(rand));
			world.setBlockState(position.add(-1, 4, -3),  getBlock(rand));
			world.setBlockState(position.add(1, 4, 1),  getBlock(rand));
			world.setBlockState(position.add(2, 4, 1),  getBlock(rand));
			world.setBlockState(position.add(3, 4, 1),  getBlock(rand));
			world.setBlockState(position.add(1, 4, -1),  getBlock(rand));
			world.setBlockState(position.add(2, 4, -1),  getBlock(rand));
			world.setBlockState(position.add(3, 4, -1),  getBlock(rand));
			world.setBlockState(position.add(-1, 4, 1),  getBlock(rand));
			world.setBlockState(position.add(-2, 4, 1),  getBlock(rand));
			world.setBlockState(position.add(-3, 4, 1),  getBlock(rand));
			world.setBlockState(position.add(-1, 4, -1),  getBlock(rand));
			world.setBlockState(position.add(-2, 4, -1),  getBlock(rand));
			world.setBlockState(position.add(-3, 4, -1),  getBlock(rand));

			world.setBlockState(position.add(2, 4, 2),  getBlock(rand));

			world.setBlockState(position.add(-2, 4, 2),  getBlock(rand));

			world.setBlockState(position.add(2, 4, -2),  getBlock(rand));

			world.setBlockState(position.add(-2, 4, -2),  getBlock(rand));

			world.setBlockState(position.add(0, 5, 1),  getBlock(rand));
			world.setBlockState(position.add(0, 5, 2),  getBlock(rand));
			world.setBlockState(position.add(0, 5, 3),  getBlock(rand));
			world.setBlockState(position.add(0, 5, -1),  getBlock(rand));
			world.setBlockState(position.add(0, 5, -2),  getBlock(rand));
			world.setBlockState(position.add(0, 5, -3),  getBlock(rand));
			world.setBlockState(position.add(1, 5, 0),  getBlock(rand));
			world.setBlockState(position.add(2, 5, 0),  getBlock(rand));
			world.setBlockState(position.add(3, 5, 0),  getBlock(rand));
			world.setBlockState(position.add(-1, 5, 0),  getBlock(rand));
			world.setBlockState(position.add(-2, 5, 0),  getBlock(rand));
			world.setBlockState(position.add(-3, 5, 0),  getBlock(rand));

			world.setBlockState(position.add(1, 5, 1),  getBlock(rand));
			world.setBlockState(position.add(1, 5, 2),  getBlock(rand));
			world.setBlockState(position.add(-1, 5, 1),  getBlock(rand));
			world.setBlockState(position.add(-1, 5, 2),  getBlock(rand));
			world.setBlockState(position.add(1, 5, -1),  getBlock(rand));
			world.setBlockState(position.add(1, 5, -2),  getBlock(rand));
			world.setBlockState(position.add(-1, 5, -1),  getBlock(rand));
			world.setBlockState(position.add(-1, 5, -2),  getBlock(rand));
			world.setBlockState(position.add(1, 5, 1),  getBlock(rand));
			world.setBlockState(position.add(2, 5, 1),  getBlock(rand));
			world.setBlockState(position.add(1, 5, -1),  getBlock(rand));
			world.setBlockState(position.add(2, 5, -1),  getBlock(rand));
			world.setBlockState(position.add(-1, 5, 1),  getBlock(rand));
			world.setBlockState(position.add(-2, 5, 1),  getBlock(rand));
			world.setBlockState(position.add(-1, 5, -1),  getBlock(rand));
			world.setBlockState(position.add(-2, 5, -1),  getBlock(rand));

			world.setBlockState(position.add(2, 5, 2),  getBlock(rand));

			world.setBlockState(position.add(-2, 5, 2),  getBlock(rand));

			world.setBlockState(position.add(2, 5, -2),  getBlock(rand));

			world.setBlockState(position.add(-2, 5, -2),  getBlock(rand));

			world.setBlockState(position.add(0, 6, 1),  getBlock(rand));
			world.setBlockState(position.add(0, 6, 2),  getBlock(rand));
			world.setBlockState(position.add(0, 6, -1),  getBlock(rand));
			world.setBlockState(position.add(0, 6, -2),  getBlock(rand));
			world.setBlockState(position.add(1, 6, 0),  getBlock(rand));
			world.setBlockState(position.add(2, 6, 0),  getBlock(rand));
			world.setBlockState(position.add(-1, 6, 0),  getBlock(rand));
			world.setBlockState(position.add(-2, 6, 0),  getBlock(rand));

			world.setBlockState(position.add(1, 6, 1),  getBlock(rand));
			world.setBlockState(position.add(-1, 6, 1),  getBlock(rand));
			world.setBlockState(position.add(1, 6, -1),  getBlock(rand));
			world.setBlockState(position.add(-1, 6, -1),  getBlock(rand));
			world.setBlockState(position.add(1, 6, 1),  getBlock(rand));
			world.setBlockState(position.add(1, 6, -1),  getBlock(rand));
			world.setBlockState(position.add(-1, 6, 1),  getBlock(rand));
			world.setBlockState(position.add(-1, 6, -1),  getBlock(rand));

			world.setBlockState(position.add(0, 7, 1),  getBlock(rand));
			world.setBlockState(position.add(0, 7, -1),  getBlock(rand));
			world.setBlockState(position.add(1, 7, 0),  getBlock(rand));
			world.setBlockState(position.add(-1, 7, 0),  getBlock(rand));

			world.setBlockState(position.add(1, 7, 1),  getBlock(rand));
			world.setBlockState(position.add(-1, 7, 1),  getBlock(rand));
			world.setBlockState(position.add(1, 7, -1),  getBlock(rand));
			world.setBlockState(position.add(-1, 7, -1),  getBlock(rand));
			world.setBlockState(position.add(1, 7, 1),  getBlock(rand));
			world.setBlockState(position.add(1, 7, -1),  getBlock(rand));
			world.setBlockState(position.add(-1, 7, 1),  getBlock(rand));
			world.setBlockState(position.add(-1, 7, -1),  getBlock(rand));

			world.setBlockState(position.add(0, 8, 1),  getBlock(rand));
			world.setBlockState(position.add(0, 8, -1),  getBlock(rand));
			world.setBlockState(position.add(1, 8, 0),  getBlock(rand));
			world.setBlockState(position.add(-1, 8, 0),  getBlock(rand));

			world.setBlockState(position.down(), BlocksAether.aether_dirt.getDefaultState());

			for(int xss = -20;xss < 20;xss++)
			{
				for(int yss = -20;yss < 20;yss++)
				{ 
					int hi = rand.nextInt((int)(MathHelper.abs(yss) + MathHelper.abs(xss)+1));

					if((MathHelper.abs(yss) + MathHelper.abs(xss)) > 15)
					{
						hi += 5;
					}
					
					if(hi < 10)
					{
						BlockPos pos = new BlockPos(xss+position.getX(), world.getHeight(new BlockPos(xss + position.getX(), 0, yss + position.getZ())).getY() -1, yss + position.getZ());
						Block huhu1 = world.getBlockState(pos).getBlock();

						if(huhu1 != Blocks.AIR)
						{
							if(rand.nextInt(80) == 0 && (huhu1 == BlocksAether.aether_grass))
							{
								world.setBlockState(pos.up(), BlocksAether.present.getDefaultState());
							}
							else
							{
								world.setBlockState(pos.up(), Blocks.SNOW_LAYER.getDefaultState());
							}
						}
					}
				}
				
			}
		}

		return false;
	}

	public IBlockState getBlock(Random rand)
	{
		return rand.nextInt(4) == 0 ? BlocksAether.holiday_leaves.getDefaultState().withProperty(BlockHolidayLeaves.leaf_type, EnumHolidayType.Decorated_Leaves) : BlocksAether.holiday_leaves.getDefaultState();
	}
}