package com.legacy.aether.blocks.dungeon;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import com.legacy.aether.entities.hostile.EntityMimic;
import com.legacy.aether.tile_entities.TileEntityChestMimic;

public class BlockMimicChest extends BlockChest
{

	public BlockMimicChest() 
	{
		super(BlockChest.Type.BASIC);

		this.setHardness(2.0F);
	}

	@Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
    	return new TileEntityChestMimic();
    }

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		this.spawnMimic(worldIn, playerIn, pos);
		
		int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        
        double d1 = (double)i + 0.5D;
        double d2 = (double)k + 0.5D;
		
		 worldIn.playSound((EntityPlayer)null, d1, (double)j + 0.5D, d2, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);


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
			if (!player.capabilities.isCreativeMode)
			{
				mimic.setAttackTarget(player);
			}
            mimic.setPosition((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D);
			world.spawnEntity(mimic);
		}

		world.setBlockToAir(pos);
	}

}