package com.gildedgames.the_aether.world.biome.decoration;

import java.util.Random;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.blocks.natural.BlockCrystalLeaves;
import com.gildedgames.the_aether.blocks.util.EnumCrystalType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class AetherGenFloatingIsland extends WorldGenerator
{

    public AetherGenFloatingIsland()
    {
		
    }
    
	public boolean generate(World world, Random random, BlockPos pos)
	{						
		boolean cangen = true;

		for(int x1 = pos.getX() - 6; x1 < pos.getX() + 12; x1++)
		{
			for(int y1 = pos.getY() - 6; y1 < pos.getY() + 17; y1++)
			{
				for(int z1 = pos.getZ() - 6 ; z1 < pos.getZ() + 12; z1++)
				{			
					if(world.getBlockState(new BlockPos.MutableBlockPos().setPos(x1, y1, z1)).getBlock() != Blocks.AIR)
                    {
						cangen = false;
                    }
				
				}
			}
		}
		
		if(pos.getY() + 11 <= world.getHeight() && cangen)
		{
			for (int z = 1; z < 2; ++z)
			{
				world.setBlockState(pos.south(z), BlocksAether.holystone.getDefaultState());
			}

			for (int x = -1; x < 2; ++x)
			{
				world.setBlockState(pos.east(x), BlocksAether.holystone.getDefaultState());
			}

			BlockPos supportPos = pos.up();

			for (int z = -2; z < 3; ++z)
			{
				world.setBlockState(supportPos.south(z), BlocksAether.holystone.getDefaultState());
			}

			for (int x = -2; x < 3; ++x)
			{
				world.setBlockState(supportPos.east(x), BlocksAether.holystone.getDefaultState());
			}

			for (int x = -1; x < 2; ++x)
			{
				for (int z = 1; z > -2; --z)
				{
					if (x != 0 || z != 0)
					{
						world.setBlockState(supportPos.add(x, 0, z), BlocksAether.holystone.getDefaultState());
					}
				}
			}

			world.setBlockState(supportPos.add(1, 0, 1), BlocksAether.holystone.getDefaultState());
			world.setBlockState(supportPos.add(-1, 0, -1), BlocksAether.holystone.getDefaultState());

			BlockPos grassPos = pos.up(2);

			for (int z = -2; z < 3; ++z)
			{
				world.setBlockState(grassPos.south(z), BlocksAether.aether_grass.getDefaultState());
			}

			for (int x = -2; x < 3; ++x)
			{
				world.setBlockState(grassPos.east(x), BlocksAether.aether_grass.getDefaultState());
			}

			for (int x = -1; x < 2; ++x)
			{
				for (int z = 1; z > -2; --z)
				{
					if (x != 0 || z != 0)
					{
						world.setBlockState(grassPos.add(x, 0, z), BlocksAether.aether_grass.getDefaultState());
					}
				}
			}

			world.setBlockState(grassPos.add(1, 0, 1), BlocksAether.aether_grass.getDefaultState());
			world.setBlockState(grassPos.add(-1, 0, -1), BlocksAether.aether_grass.getDefaultState());

			for(int y = pos.getY() + 3; y <= pos.getY() + 9; y++)
			{
				world.setBlockState(new BlockPos(pos.getX(), y, pos.getZ()), BlocksAether.aether_log.getDefaultState());
			}

			world.setBlockState(pos.up(10), setRandomBlock(world, random));

			BlockPos newPos = pos.up(5);

			for (int z = -1; z < 2; ++z)
			{
				if (z != 0)
				world.setBlockState(newPos.south(z), BlocksAether.aether_log.getDefaultState());
			}

			for (int x = -1; x < 2; ++x)
			{
				if (x != 0)
				world.setBlockState(newPos.east(x), BlocksAether.aether_log.getDefaultState());
			}

			for (int z = -2; z < 3; ++z)
			{
				if (z != 0 || z != 1)
				world.setBlockState(newPos.south(z), setRandomBlock(world, random));
			}

			for (int x = -2; x < 3; ++x)
			{
				if (x != 0 || x != 1)
				world.setBlockState(newPos.east(x), setRandomBlock(world, random));
			}

			for (int x = -1; x < 2; ++x)
			{
				if (x != 0)
				world.setBlockState(newPos.add(x, 0, -2), setRandomBlock(world, random));
			}

			for (int x = -1; x < 2; ++x)
			{
				if (x != 0)
				world.setBlockState(newPos.add(x, 0, 2), setRandomBlock(world, random));
			}

			for (int z = -1; z < 2; ++z)
			{
				if (z != 0)
				world.setBlockState(newPos.add(-2, 0, z), setRandomBlock(world, random));
			}

			for (int z = -1; z < 2; ++z)
			{
				if (z != 0)
				world.setBlockState(newPos.add(2, 0, z), setRandomBlock(world, random));
			}

			for (int x = -1; x < 2; ++x)
			{
				for (int z = 1; z > -2; --z)
				{
					if (x != 0 || z != 0)
					{
						world.setBlockState(newPos.add(x, 0, z), setRandomBlock(world, random));
					}
				}
			}

			world.setBlockState(newPos.add(1, 0, 1), setRandomBlock(world, random));
			world.setBlockState(newPos.add(-1, 0, -1), setRandomBlock(world, random));

			newPos = pos.up(6);

			for (int z = -2; z < 3; ++z)
			{
				if (z != 0 || z != 1)
				world.setBlockState(newPos.south(z), setRandomBlock(world, random));
			}

			for (int x = -2; x < 3; ++x)
			{
				if (x != 0 || x != 1)
				world.setBlockState(newPos.east(x), setRandomBlock(world, random));
			}

			for (int x = -1; x < 2; ++x)
			{
				for (int z = 1; z > -2; --z)
				{
					if (x != 0 || z != 0)
					{
						world.setBlockState(newPos.add(x, 0, z), setRandomBlock(world, random));
					}
				}
			}

			world.setBlockState(newPos.add(1, 0, 1), setRandomBlock(world, random));
			world.setBlockState(newPos.add(-1, 0, -1), setRandomBlock(world, random));

			for (int z = -1; z < 2; ++z)
			{
				if (z != 0)
				world.setBlockState(newPos.south(z), setRandomBlock(world, random));
			}

			for (int x = -1; x < 2; ++x)
			{
				if (x != 0)
				world.setBlockState(newPos.east(x), setRandomBlock(world, random));
			}

			newPos = pos.up(7);

			for (int z = -1; z < 2; ++z)
			{
				if (z != 0)
				world.setBlockState(newPos.south(z), setRandomBlock(world, random));
			}

			for (int x = -1; x < 2; ++x)
			{
				if (x != 0)
				world.setBlockState(newPos.east(x), setRandomBlock(world, random));
			}

			newPos = pos.up(8);

			for (int z = -1; z < 2; ++z)
			{
				if (z != 0)
				world.setBlockState(newPos.south(z), BlocksAether.aether_log.getDefaultState());
			}

			for (int x = -1; x < 2; ++x)
			{
				if (x != 0)
				world.setBlockState(newPos.east(x), BlocksAether.aether_log.getDefaultState());
			}

			for (int z = -2; z < 3; ++z)
			{
				if (z != 0 || z != 1)
				world.setBlockState(newPos.south(z), setRandomBlock(world, random));
			}

			for (int x = -2; x < 3; ++x)
			{
				if (x != 0 || x != 1)
				world.setBlockState(newPos.east(x), setRandomBlock(world, random));
			}

			for (int x = -1; x < 2; ++x)
			{
				for (int z = 1; z > -2; --z)
				{
					if (x != 0 || z != 0)
					{
						world.setBlockState(newPos.add(x, 0, z), setRandomBlock(world, random));
					}
				}
			}

			world.setBlockState(newPos.add(1, 0, 1), setRandomBlock(world, random));
			world.setBlockState(newPos.add(-1, 0, -1), setRandomBlock(world, random));

			newPos = pos.up(9);

			for (int z = -1; z < 2; ++z)
			{
				if (z != 0)
				world.setBlockState(newPos.south(z), setRandomBlock(world, random));
			}

			for (int x = -1; x < 2; ++x)
			{
				if (x != 0)
				world.setBlockState(newPos.east(x), setRandomBlock(world, random));
			}

			return true;
		}		
	
		return false;
	}

	protected IBlockState setRandomBlock(World world, Random random)
	{
		int nextInt = random.nextInt(3);

		if (nextInt == 0)
		{
			return BlocksAether.crystal_leaves.getDefaultState().withProperty(BlockCrystalLeaves.leaf_type, EnumCrystalType.Crystal_Fruited);
		}

		return BlocksAether.crystal_leaves.getDefaultState();
	}

}