package com.legacy.aether.server.blocks.dungeon;

import javax.annotation.Nullable;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import com.legacy.aether.server.entities.hostile.EntityMimic;
import com.legacy.aether.server.tile_entities.TileEntityChestMimic;

public class BlockMimicChest extends BlockChest
{

	public BlockMimicChest() 
	{
		super(BlockChest.Type.BASIC);
	}

	@Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
    	return new TileEntityChestMimic();
    }

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		this.spawnMimic(worldIn, playerIn, pos);

		return true;
	}

	@Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
    {
    	this.spawnMimic(worldIn, player, pos);
    }

	@Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
    	return new ItemStack(Blocks.CHEST);
    }

	private void spawnMimic(World world, EntityPlayer player, BlockPos pos)
	{
		if (!world.isRemote)
		{
			EntityMimic mimic = new EntityMimic(world);
			mimic.setAttackTarget(player);
            mimic.setPosition((double)pos.getX() + 0.5D, (double)pos.getY() + 1.5D, (double)pos.getZ() + 0.5D);
			world.spawnEntityInWorld(mimic);
		}

		world.setBlockToAir(pos);
	}

}