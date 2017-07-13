package com.legacy.aether.common.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.legacy.aether.common.blocks.natural.BlockAetherFlower;

public class BlockAetherSapling extends BlockAetherFlower
{

	public WorldGenerator treeGenObject = null;

	public BlockAetherSapling(WorldGenerator treeGen)
	{
		super();
		float f = 0.4F;
		this.FLOWER_AABB = new AxisAlignedBB(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
		this.treeGenObject = treeGen;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		super.updateTick(world, pos, state, random);

		if (!world.isRemote)
		{
			if (world.getLight(pos.up()) >= 9 && random.nextInt(30) == 0)
			{
				this.growTree(world, pos, random);
			}
		}
	}

	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		if (heldItem != null && heldItem.getItem() == Items.DYE && heldItem.getMetadata() == 15)
		{
            if (!worldIn.isRemote)
            {
                worldIn.playEvent(2005, pos, 0);
            }

			if (worldIn.rand.nextFloat() < 0.45D)
			{
				this.growTree(worldIn, pos, worldIn.rand);
			}

			return true;
		}

        return false;
    }

	public void growTree(World world, BlockPos pos, Random rand)
	{
		world.setBlockState(pos, Blocks.AIR.getDefaultState());

		if (!this.treeGenObject.generate(world, world.rand, pos))
		{
			world.setBlockState(pos, this.getDefaultState());
		}
	}

}