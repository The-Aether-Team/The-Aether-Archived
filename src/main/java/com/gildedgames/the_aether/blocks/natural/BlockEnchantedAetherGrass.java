package com.gildedgames.the_aether.blocks.natural;

import com.gildedgames.the_aether.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEnchantedAetherGrass extends Block {
	@SideOnly(Side.CLIENT)
	private IIcon blockIconTop;

	public BlockEnchantedAetherGrass() {
		super(Material.grass);

		this.setHardness(0.2F);
		this.setStepSound(soundTypeGrass);
		this.setHarvestLevel("shovel", 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registry) {
		this.blockIcon = registry.registerIcon("aether_legacy:enchanted_aether_grass_side");
		this.blockIconTop = registry.registerIcon("aether_legacy:enchanted_aether_grass_top");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 1 ? this.blockIconTop : (side == 0 ? BlocksAether.aether_dirt.getBlockTextureFromSide(side) : this.blockIcon);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		if (side == 1) {
			return this.blockIconTop;
		} else if (side == 0) {
			return BlocksAether.aether_dirt.getBlockTextureFromSide(side);
		}

		return this.blockIcon;
	}

}