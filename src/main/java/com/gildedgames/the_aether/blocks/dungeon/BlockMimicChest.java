package com.gildedgames.the_aether.blocks.dungeon;

import com.gildedgames.the_aether.entities.hostile.EntityMimic;
import com.gildedgames.the_aether.tile_entities.TileEntityChestMimic;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

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

		 if (worldIn.isRemote)
	        {
	            for (int e = 0; e < 20; ++e)
	            {
	                double e0 = worldIn.rand.nextGaussian() * 0.02D;
	                double e1 = worldIn.rand.nextGaussian() * 0.02D;
	                double e2 = worldIn.rand.nextGaussian() * 0.02D;
	                worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, i + (double)(worldIn.rand.nextFloat() * 1.0F * 2.0F) - (double)1.0F - e0 * 10.0D, j + (double)(worldIn.rand.nextFloat() * 2.0F) - e1 * 10.0D, k + (double)(worldIn.rand.nextFloat() * 1.0F * 2.0F) - (double)1.0F - e2 * 10.0D, e0, e1, e2);
	            }
	        }
		 
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