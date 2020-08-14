package com.gildedgames.the_aether.blocks.decorative;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.entities.block.EntityTNTPresent;
import com.gildedgames.the_aether.items.ItemsAether;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPresent extends Block {

	@SideOnly(Side.CLIENT)
	private IIcon blockIconTop;

	public BlockPresent() {
		super(Material.grass);

		this.setHardness(0.6F);
		this.setStepSound(soundTypeGrass);
		this.setBlockTextureName(Aether.find("present_side"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registry) {
		super.registerBlockIcons(registry);

		this.blockIconTop = registry.registerIcon(Aether.find("present_top"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return (side == 1 || side == 0) ? this.blockIconTop : this.blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		if (side == 1 || side == 0) {
			return this.blockIconTop;
		}

		return this.blockIcon;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
		int randomNumber = (int) (((9 - 6 + 1) * world.rand.nextDouble()) + 6);
		int crateType = world.rand.nextInt(4);

		if (crateType == 0) {
			for (int size = 1; size <= randomNumber; ++size) {
				if (!world.isRemote) {
					world.spawnEntityInWorld(new EntityXPOrb(world, x, y, z, size));
				}
			}
		} else if (crateType == 1) {
			if (world.rand.nextInt(9) == 0) {
				this.dropBlockAsItem(world, x, y, z, new ItemStack(ItemsAether.candy_cane_sword));
			} else {
				for (int size = 1; size <= randomNumber; ++size) {
					this.dropBlockAsItem(world, x, y, z, new ItemStack(ItemsAether.gingerbread_man));
				}
			}
		} else {
			EntityTNTPresent present = new EntityTNTPresent(world, x, y, z);

			if (!world.isRemote) {
				world.spawnEntityInWorld(present);
			}

			world.playSoundAtEntity(present, "game.tnt.primed", 1.0F, 1.0F);
		}
	}

}