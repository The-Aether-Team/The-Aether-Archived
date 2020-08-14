package com.gildedgames.the_aether.blocks.natural;

import com.gildedgames.the_aether.CommonProxy;
import com.gildedgames.the_aether.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockAetherFlower extends BlockBush {

	public AxisAlignedBB FLOWER_AABB = AxisAlignedBB.getBoundingBox(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 0.6000000238418579D, 0.699999988079071D);

	public BlockAetherFlower() {
		this.setHardness(0.0F);
		this.setTickRandomly(true);
		this.setStepSound(soundTypeGrass);
		this.setBlockBounds(0.5F - 0.2F, 0.0F, 0.5F - 0.2F, 0.5F + 0.2F, 0.2F * 3.0F, 0.5F + 0.2F);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		Block soil = world.getBlock(x, y - 1, z);
		return soil == BlocksAether.aether_grass || soil == BlocksAether.aether_dirt || soil == BlocksAether.enchanted_aether_grass;
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		Block soil = world.getBlock(x, y - 1, z);
		return (soil != null && this.canPlaceBlockAt(world, x, y, z));
	}

	@Override
	public int getRenderType() {
		return CommonProxy.aetherFlowerRenderID;
	}

}