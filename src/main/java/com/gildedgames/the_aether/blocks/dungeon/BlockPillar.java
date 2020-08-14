package com.gildedgames.the_aether.blocks.dungeon;

import com.gildedgames.the_aether.Aether;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockPillar extends BlockRotatedPillar {

	private String topTexture;

	private String sideTexture;

	@SideOnly(Side.CLIENT)
	protected IIcon sideIcon;

	public BlockPillar(String topIcon, String sideTexture) {
		super(Material.rock);

		this.topTexture = topIcon;
		this.sideTexture = sideTexture;

		this.setHardness(0.5F);
		this.setStepSound(soundTypeMetal);
		this.setHarvestLevel("pickaxe", 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected IIcon getSideIcon(int meta) {
		return this.sideIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registry) {
		this.field_150164_N = registry.registerIcon(Aether.find(this.topTexture));
		this.sideIcon = registry.registerIcon(Aether.find(this.sideTexture));
	}

}