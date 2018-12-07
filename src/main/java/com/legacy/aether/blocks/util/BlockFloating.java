package com.legacy.aether.blocks.util;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.entities.block.EntityFloatingBlock;

public class BlockFloating extends Block {

	private boolean leveled;

	public BlockFloating(Material material, boolean leveled) {
		super(material);

		this.leveled = leveled;

		this.setTickRandomly(true);
		this.setHarvestLevel("pickaxe", 2);
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, int x, int y, int z, int beaconX, int beaconY, int beaconZ) {
		return this == BlocksAether.enchanted_gravitite;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		world.scheduleBlockUpdate(x, y, z, this, 3);
	}

	@Override
	public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighborBlock) {
		worldIn.scheduleBlockUpdate(x, y, z, this, 3);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (!this.leveled || this.leveled && world.isBlockIndirectlyGettingPowered(x, y, z)) {
			this.floatBlock(world, x, y, z);
		}
	}

	private void floatBlock(World world, int x, int y, int z) {
		if (canContinue(world, x, y + 1, z) && y < world.getHeight()) {
			EntityFloatingBlock floating = new EntityFloatingBlock(world, x, y, z, world.getBlock(x, y, z), world.getBlockMetadata(x, y, z));

			if (!world.isRemote) {
				world.spawnEntityInWorld(floating);
			}

			world.setBlockToAir(x, y, z);
		}
	}

	public static boolean canContinue(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		Material material = block.getMaterial();

		if (block == Blocks.air || block == Blocks.fire) {
			return true;
		}

		if (material == Material.water || material == Material.lava) {
			return true;
		}

		return false;
	}

}