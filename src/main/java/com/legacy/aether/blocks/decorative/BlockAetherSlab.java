package com.legacy.aether.blocks.decorative;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import com.legacy.aether.blocks.BlocksAether;

public class BlockAetherSlab extends BlockSlab {

	private String name;

	public BlockAetherSlab(String name, boolean double_slab, Material materialIn) {
		super(double_slab, materialIn);
		this.name = name;

		this.setLightOpacity(0);
		this.setStepSound(materialIn == Material.wood ? soundTypeWood : soundTypeStone);
	}

	@Override
	public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
		return Item.getItemFromBlock(this.getDroppedSlab());
	}

	public Block getDroppedSlab() {
		if (this == BlocksAether.skyroot_double_slab) {
			return BlocksAether.skyroot_slab;
		} else if (this == BlocksAether.carved_double_slab) {
			return BlocksAether.carved_slab;
		} else if (this == BlocksAether.angelic_double_slab) {
			return BlocksAether.angelic_slab;
		} else if (this == BlocksAether.hellfire_double_slab) {
			return BlocksAether.hellfire_slab;
		} else if (this == BlocksAether.holystone_brick_double_slab) {
			return BlocksAether.holystone_brick_slab;
		} else if (this == BlocksAether.holystone_double_slab) {
			return BlocksAether.holystone_slab;
		} else if (this == BlocksAether.mossy_holystone_double_slab) {
			return BlocksAether.mossy_holystone_slab;
		} else {
			return this;
		}
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return Item.getItemFromBlock(this.getDroppedSlab());
	}

	@Override
	public int damageDropped(int meta) {
		return 0;
	}

	@Override
	public String func_150002_b(int meta) {
		return this.name;
	}

}
