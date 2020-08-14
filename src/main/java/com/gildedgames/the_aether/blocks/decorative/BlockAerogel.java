package com.gildedgames.the_aether.blocks.decorative;

import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;

import com.gildedgames.the_aether.Aether;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAerogel extends BlockBreakable {

	public BlockAerogel() {
		super(Aether.find("aerogel"), Material.rock, false);

		this.setHardness(1.0F);
		this.setLightOpacity(3);
		this.setResistance(2000F);
		this.setStepSound(soundTypeMetal);
		this.setHarvestLevel("pickaxe", 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}

}