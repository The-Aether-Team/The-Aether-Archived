package com.legacy.aether.blocks.dungeon;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class BlockDungeonBase extends Block {

	private Block pickBlock;

	private boolean isLit;

	public BlockDungeonBase(boolean isLit) {
		this(null, isLit);
	}

	public BlockDungeonBase(Block pickBlock, boolean isLit) {
		super(Material.rock);

		if (pickBlock != null) {
			this.pickBlock = pickBlock;
			this.setResistance(6000000.0F);
		}

		this.isLit = isLit;
		this.setStepSound(soundTypeStone);
		this.setHardness(this.pickBlock != null ? -1F : 0.5F);
		this.setCreativeTab(this.pickBlock != null ? null : AetherCreativeTabs.blocks);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);

		if (block != this) {
			return block.getLightValue(world, x, y, z);
		}

		if (this.isLit) {
			return (int) (15.0F * 0.75f);
		}

		return super.getLightValue(world, x, y, z);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		if (this.pickBlock != null) {
			return new ItemStack(this.pickBlock);
		}

		return super.getPickBlock(target, world, x, y, z, player);
	}

	public Block getUnlockedBlock() {
		return this.pickBlock == null ? this : this.pickBlock;
	}

}