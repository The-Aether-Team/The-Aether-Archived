package com.gildedgames.the_aether.blocks.decorative;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.blocks.BlocksAether;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSkyrootBookshelf extends Block {

	public BlockSkyrootBookshelf() {
		super(Material.wood);

		this.setHardness(2F);
		this.setResistance(5F);
		this.setHarvestLevel("axe", 0);
		this.setStepSound(soundTypeWood);
		this.setBlockTextureName(Aether.find("skyroot_bookshelf"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		return p_149691_1_ != 1 && p_149691_1_ != 0 ? super.getIcon(p_149691_1_, p_149691_2_) : BlocksAether.skyroot_planks.getBlockTextureFromSide(p_149691_1_);
	}

	@Override
	public int quantityDropped(Random random) {
		return 3;
	}

	@Override
	public float getEnchantPowerBonus(World world, int x, int y, int z) {
		return 1;
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return Items.book;
	}
}