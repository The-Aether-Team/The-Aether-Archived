package com.legacy.aether.blocks.dungeon;

import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.legacy.aether.entities.hostile.EntityMimic;
import com.legacy.aether.tileentity.TileEntityChestMimic;

public class BlockMimicChest extends BlockChest {

	public BlockMimicChest() {
		super(13);

		this.setHardness(2.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityChestMimic();
	}

	@Override
	public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer playerIn, int side, float hitX, float hitY, float hitZ) {
		this.spawnMimic(worldIn, playerIn, x, y, z);

		worldIn.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "random.chestopen", 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);

		return true;
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, int x, int y, int z, int meta) {
		this.spawnMimic(worldIn, player, x, y, z);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		return new ItemStack(Blocks.chest);
	}

	private void spawnMimic(World world, EntityPlayer player, int x, int y, int z) {
		if (!world.isRemote) {
			EntityMimic mimic = new EntityMimic(world);
			if (!player.capabilities.isCreativeMode) {
				mimic.setAttackTarget(player);
			}
			mimic.setPosition((double) x + 0.5D, (double) y + 1.5D, (double) z + 0.5D);
			world.spawnEntityInWorld(mimic);
		}

		world.setBlockToAir(x, y, z);
	}

}