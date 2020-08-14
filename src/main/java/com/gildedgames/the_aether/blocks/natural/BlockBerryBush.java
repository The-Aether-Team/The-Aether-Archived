package com.gildedgames.the_aether.blocks.natural;

import java.util.Random;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.CommonProxy;
import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.items.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBerryBush extends BlockAetherFlower {

	public BlockBerryBush() {
		this.setHardness(0.2F);
		this.setHarvestLevel("axe", 0);
		this.setStepSound(soundTypeGrass);
		this.setBlockTextureName(Aether.find("berry_bush"));
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return Item.getItemFromBlock(BlocksAether.berry_bush_stem);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta) {
		int min, max;

		if (world.getBlock(x, y, z) == BlocksAether.enchanted_aether_grass) {
			min = 1;
			max = 4;
		} else {
			min = 1;
			max = 3;
		}

		int randomNum = world.rand.nextInt(max - min + 1) + min;
		entityplayer.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(this)], 1);
		entityplayer.addExhaustion(0.025F);

		world.setBlock(x, y, z, BlocksAether.berry_bush_stem);

		if (randomNum != 0) {
			this.dropBlockAsItem(world, x, y, z, new ItemStack(ItemsAether.blueberry, randomNum, 0));
		}
	}

	@Override
	protected void checkAndDropBlock(World world, int x, int y, int z) {
		if (!this.canBlockStay(world, x, y, z)) {
			int min, max;

			if (world.getBlock(x, y, z) == BlocksAether.enchanted_aether_grass) {
				min = 1;
				max = 4;
			} else {
				min = 1;
				max = 3;
			}

			int randomNum = world.rand.nextInt(max - min + 1) + min;
			this.dropBlockAsItem(world, x, y, z, new ItemStack(ItemsAether.blueberry, randomNum, 0));
			world.setBlock(x, y, z, BlocksAether.berry_bush_stem);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
		return AxisAlignedBB.getBoundingBox((double) p_149668_2_ + this.minX, (double) p_149668_3_ + this.minY, (double) p_149668_4_ + this.minZ, (double) p_149668_2_ + this.maxX, (double) p_149668_3_ + this.maxY, (double) p_149668_4_ + this.maxZ);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess worldIn, int x, int y, int z, int side) {
		return true;
	}

	@Override
	public int getRenderType() {
		return CommonProxy.berryBushRenderID;
	}

}