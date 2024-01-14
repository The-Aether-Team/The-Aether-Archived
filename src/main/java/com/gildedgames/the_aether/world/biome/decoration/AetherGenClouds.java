package com.gildedgames.the_aether.world.biome.decoration;

import java.util.Random;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.blocks.natural.BlockAercloud;
import com.gildedgames.the_aether.blocks.util.EnumCloudType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.blocks.natural.BlockAercloud;
import com.gildedgames.the_aether.blocks.util.EnumCloudType;

public class AetherGenClouds extends WorldGenerator
{

	private int amount;

	private EnumCloudType cloudType;

	public AetherGenClouds()
	{
		
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	public void setCloudType(EnumCloudType cloudType)
	{
		this.cloudType = cloudType;
	}

	@Override
	public boolean generate(World world, Random random, BlockPos pos)
	{
		BlockPos origin = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
        BlockPos position = new BlockPos(origin.getX() + 8, origin.getY(), origin.getZ() + 8);

        IBlockState state = BlocksAether.aercloud.getDefaultState().withProperty(BlockAercloud.cloud_type, this.cloudType);

		if (this.cloudType == EnumCloudType.Purple)
		{
			state = state.withProperty(BlockAercloud.property_facing, EnumFacing.HORIZONTALS[random.nextInt(EnumFacing.HORIZONTALS.length)]);
		}

		for (int amount = 0; amount < this.amount; ++amount)
		{
			int xOffset = random.nextInt(2);
			int yOffset = (random.nextBoolean() ? random.nextInt(3) - 1 : 0);
			int zOffset = random.nextInt(2);

			position = position.add(xOffset, yOffset, zOffset);

			for (int x = position.getX(); x < position.getX() + random.nextInt(2) + 3; ++x)
			{
				for (int y = position.getY(); y < position.getY() + random.nextInt(1) + 2; ++y)
				{
					for (int z = position.getZ(); z < position.getZ() + random.nextInt(2) + 3; ++z)
					{
						BlockPos newPosition = new BlockPos(x, y, z);

						if (world.isAirBlock(newPosition))
						{
							if (Math.abs(x - position.getX()) + Math.abs(y - position.getY()) + Math.abs(z - position.getZ()) < 4 + random.nextInt(2))
							{
								this.setBlockAndNotifyAdequately(world, newPosition, state);
							}
						}
					}
				}
			}
		}

		return true;
	}

}