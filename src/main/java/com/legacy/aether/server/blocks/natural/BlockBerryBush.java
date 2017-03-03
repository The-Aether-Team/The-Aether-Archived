package com.legacy.aether.server.blocks.natural;

import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.server.blocks.BlocksAether;
import com.legacy.aether.server.items.ItemsAether;

public class BlockBerryBush extends BlockAetherFlower
{

	public BlockBerryBush()
	{
		this.FLOWER_AABB = new AxisAlignedBB(0F, 0F, 0F, 1.0F, 1.0F, 1.0F);
		this.setHardness(1.0F);
		this.setSoundType(SoundType.PLANT);
	}

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(BlocksAether.berry_bush_stem);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World world, BlockPos pos)
	{
		return this.FLOWER_AABB;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return this.FLOWER_AABB;
    }

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
    public void harvestBlock(World world, EntityPlayer entityplayer, BlockPos pos, IBlockState state, TileEntity te, ItemStack stackIn)
	{
		int min, max;

		if (world.getBlockState(pos.down()).getBlock() == BlocksAether.enchanted_aether_grass)
		{
			min = 1;
			max = 4;
		}
		else
		{
			min = 1;
			max = 3;
		}

		int randomNum = world.rand.nextInt(max - min + 1) + min;
		entityplayer.addStat(StatList.getBlockStats(this), 1);
		entityplayer.addExhaustion(0.025F);

		world.setBlockState(pos, BlocksAether.berry_bush_stem.getDefaultState());

		if (randomNum != 0)
		{
			spawnAsEntity(world, pos, new ItemStack(ItemsAether.blue_berry, randomNum, 0));
		}
	}

	@Override
    protected void checkAndDropBlock(World world, BlockPos pos, IBlockState state)
    {
    	if(!this.canBlockStay(world, pos, state))
    	{
			spawnAsEntity(world, pos, new ItemStack(ItemsAether.blue_berry, 1, 0));
    		super.checkAndDropBlock(world, pos, state);
    	}
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
    	return true;
    }

}