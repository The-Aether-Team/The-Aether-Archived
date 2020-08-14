package com.gildedgames.the_aether.blocks.portal;

import java.util.Random;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.entities.particles.ParticleAetherPortal;
import com.gildedgames.the_aether.entities.util.EntityHook;
import com.gildedgames.the_aether.player.PlayerAether;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAetherPortal extends BlockPortal {

	public BlockAetherPortal() {
		super();

		this.setHardness(-1);
		this.setResistance(900000F);
		this.setStepSound(soundTypeGlass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registry) {
		this.blockIcon = registry.registerIcon(Aether.find("aether_portal"));
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity entity) {
		if (entity instanceof EntityPlayer) {
			PlayerAether.get((EntityPlayer) entity).setInPortal();
		} else if ((entity instanceof EntityLivingBase) && entity.ridingEntity == null && entity.riddenByEntity == null) {
			((EntityHook) entity.getExtendedProperties("aether_legacy:entity_hook")).setInPortal();
		}
	}

	public boolean trySpawnPortal(World worldIn, int x, int y, int z) {
		AetherPortalSize aetherportal$size = new AetherPortalSize(worldIn, x, y, z, 1);

		if (aetherportal$size.isValid() && aetherportal$size.portalBlockCount == 0) {
			aetherportal$size.placePortalBlocks();

			return true;
		} else {
			AetherPortalSize aetherportal$size1 = new AetherPortalSize(worldIn, x, y, z, 2);

			if (aetherportal$size1.isValid() && aetherportal$size1.portalBlockCount == 0) {
				aetherportal$size1.placePortalBlocks();

				return true;
			} else {
				return false;
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block blockIn) {
		int l = func_149999_b(worldIn.getBlockMetadata(x, y, z));

		if (l == 1) {
			AetherPortalSize blockportal$size = new AetherPortalSize(worldIn, x, y, z, 1);

			if (!blockportal$size.isValid() || blockportal$size.portalBlockCount < blockportal$size.width * blockportal$size.height) {
				worldIn.setBlock(x, y, z, Blocks.air);
			}
		} else if (l == 2) {
			AetherPortalSize blockportal$size1 = new AetherPortalSize(worldIn, x, y, z, 2);

			if (!blockportal$size1.isValid() || blockportal$size1.portalBlockCount < blockportal$size1.width * blockportal$size1.height) {
				worldIn.setBlock(x, y, z, Blocks.air);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		if (rand.nextInt(100) == 0) {
			world.playSound((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, "portal.portal", 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
		}

		for (int i = 0; i < 4; ++i) {
			double d0 = (double) ((float) x + rand.nextFloat());
			double d1 = (double) ((float) y + rand.nextFloat());
			double d2 = (double) ((float) z + rand.nextFloat());
			double d3 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
			double d4 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
			double d5 = ((double) rand.nextFloat() - 0.5D) * 0.5D;
			int j = rand.nextInt(2) * 2 - 1;

			if (world.getBlock(x - 1, y, z) != this && world.getBlock(x + 1, y, z) != this) {
				d0 = (double) x + 0.5D + 0.25D * (double) j;
				d3 = (double) (rand.nextFloat() * 2.0F * (float) j);
			} else {
				d2 = (double) z + 0.5D + 0.25D * (double) j;
				d5 = (double) (rand.nextFloat() * 2.0F * (float) j);
			}

			ParticleAetherPortal particle = new ParticleAetherPortal(world, d0, d1, d2, d3, d4, d5);
			FMLClientHandler.instance().getClient().effectRenderer.addEffect(particle);
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

	}

}