package com.gildedgames.the_aether.blocks.natural;

import java.util.List;

import com.gildedgames.the_aether.items.block.IColoredBlock;
import com.gildedgames.the_aether.items.block.INamedBlock;
import com.gildedgames.the_aether.registry.achievements.AchievementsAether;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAercloud extends Block implements IColoredBlock, INamedBlock {

	public BlockAercloud() {
		super(Material.ice);

		this.setHardness(0.2F);
		this.setStepSound(soundTypeCloth);
		this.setBlockTextureName("aether_legacy:aercloud");
	}

	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
		p_149666_3_.add(new ItemStack(this, 1, 0));
		p_149666_3_.add(new ItemStack(this, 1, 1));
		p_149666_3_.add(new ItemStack(this, 1, 2));
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		entity.fallDistance = 0;

		if (world.getBlockMetadata(x, y, z) == 1) {
			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;

				player.triggerAchievement(AchievementsAether.blue_cloud);

				if (player.isSneaking()) {
					if (entity.motionY < 0) {
						entity.motionY *= 0.005D;
					}

					return;
				}

				entity.motionY = 2.0D;
			} else {
				if (entity instanceof EntityArrow)
				{
					if (entity.ticksExisted >= 1200)
					{
						entity.setDead();
					}
				}
				
				entity.motionY = 2.0D;
			}

			if (world.isRemote) {
				if (!(entity instanceof net.minecraft.client.particle.EntityFX)) {
					for (int count = 0; count < 50; count++) {
						double xOffset = x + world.rand.nextDouble();
						double yOffset = y + world.rand.nextDouble();
						double zOffset = z + world.rand.nextDouble();

						world.spawnParticle("splash", xOffset, yOffset, zOffset, 0, 0, 0);
					}
				}
			}
		} else if (entity.motionY < 0) {
			entity.motionY *= 0.005D;
		}
	}

	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		if (meta == 1) {
			return 0xCCFFFF;
		} else if (meta == 2) {
			return 0xFFFF80;
		}

		return this.getBlockColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);

		return this.getRenderColor(meta);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return stack.getItemDamage() == 1 ? "blue_aercloud" : stack.getItemDamage() == 2 ? "golden_aercloud" : "cold_aercloud";
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int pass) {
		if (stack.getItemDamage() == 1) {
			return 0xCCFFFF;
		} else if (stack.getItemDamage() == 2) {
			return 0xFFFF80;
		}

		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
		Block block = p_149646_1_.getBlock(p_149646_2_, p_149646_3_, p_149646_4_);

		if (p_149646_1_.getBlockMetadata(p_149646_2_, p_149646_3_, p_149646_4_) != p_149646_1_.getBlockMetadata(p_149646_2_ - Facing.offsetsXForSide[p_149646_5_], p_149646_3_ - Facing.offsetsYForSide[p_149646_5_], p_149646_4_ - Facing.offsetsZForSide[p_149646_5_])) {
			return true;
		}

		if (block == this) {
			return false;
		}

		return super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, p_149646_5_);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z) != 1 ? AxisAlignedBB.getBoundingBox(x, y, z, x + 1.0D, y + 0.01D, z + 1.0D) : AxisAlignedBB.getBoundingBox(x, y, z, x, y, z);
	}

}