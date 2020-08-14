package com.gildedgames.the_aether.items;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.gildedgames.the_aether.entities.EntitiesAether;
import com.gildedgames.the_aether.entities.EntitiesAether.AetherEggInfo;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAetherSpawnEgg extends Item {

	public static HashMap<Integer, AetherEggInfo> entityEggs = new LinkedHashMap<Integer, AetherEggInfo>();

	@SideOnly(Side.CLIENT)
	private IIcon theIcon;

	public ItemAetherSpawnEgg() {
		this.setHasSubtypes(true);
		this.setCreativeTab(AetherCreativeTabs.misc);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		String s = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
		String s1 = EntitiesAether.getStringFromID(stack.getItemDamage());

		if (s1 != null) {
			s = s + " " + StatCollector.translateToLocal("entity." + s1 + ".name");
		}

		return s;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack p_82790_1_, int p_82790_2_) {
		AetherEggInfo entityegginfo = entityEggs.get(Integer.valueOf(p_82790_1_.getItemDamage()));

		return entityegginfo != null ? (p_82790_2_ == 0 ? entityegginfo.primaryColor : entityegginfo.secondaryColor) : 16777215;
	}

	@Override
	public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		if (p_77648_3_.isRemote) {
			return true;
		} else {
			Block block = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
			p_77648_4_ += Facing.offsetsXForSide[p_77648_7_];
			p_77648_5_ += Facing.offsetsYForSide[p_77648_7_];
			p_77648_6_ += Facing.offsetsZForSide[p_77648_7_];
			double d0 = 0.0D;

			if (p_77648_7_ == 1 && block.getRenderType() == 11) {
				d0 = 0.5D;
			}

			Entity entity = spawnCreature(p_77648_3_, p_77648_1_.getItemDamage(), (double) p_77648_4_ + 0.5D, (double) p_77648_5_ + d0, (double) p_77648_6_ + 0.5D);

			if (entity != null) {
				if (entity instanceof EntityLivingBase && p_77648_1_.hasDisplayName()) {
					((EntityLiving) entity).setCustomNameTag(p_77648_1_.getDisplayName());
				}

				if (!p_77648_2_.capabilities.isCreativeMode) {
					--p_77648_1_.stackSize;
				}
			}

			return true;
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		if (p_77659_2_.isRemote) {
			return p_77659_1_;
		} else {
			MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(p_77659_2_, p_77659_3_, true);

			if (movingobjectposition == null) {
				return p_77659_1_;
			} else {
				if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					int i = movingobjectposition.blockX;
					int j = movingobjectposition.blockY;
					int k = movingobjectposition.blockZ;

					if (!p_77659_2_.canMineBlock(p_77659_3_, i, j, k)) {
						return p_77659_1_;
					}

					if (!p_77659_3_.canPlayerEdit(i, j, k, movingobjectposition.sideHit, p_77659_1_)) {
						return p_77659_1_;
					}

					if (p_77659_2_.getBlock(i, j, k) instanceof BlockLiquid) {
						Entity entity = spawnCreature(p_77659_2_, p_77659_1_.getItemDamage(), (double) i, (double) j, (double) k);

						if (entity != null) {
							if (entity instanceof EntityLivingBase && p_77659_1_.hasDisplayName()) {
								((EntityLiving) entity).setCustomNameTag(p_77659_1_.getDisplayName());
							}

							if (!p_77659_3_.capabilities.isCreativeMode) {
								--p_77659_1_.stackSize;
							}
						}
					}
				}

				return p_77659_1_;
			}
		}
	}

	public static Entity spawnCreature(World p_77840_0_, int p_77840_1_, double p_77840_2_, double p_77840_4_, double p_77840_6_) {
		if (!entityEggs.containsKey(Integer.valueOf(p_77840_1_))) {
			return null;
		} else {
			Entity entity = null;

			for (int j = 0; j < 1; ++j) {
				entity = EntitiesAether.createEntityByID(p_77840_1_, p_77840_0_);

				if (entity != null && entity instanceof EntityLivingBase) {
					EntityLiving entityliving = (EntityLiving) entity;
					entity.setLocationAndAngles(p_77840_2_, p_77840_4_, p_77840_6_, MathHelper.wrapAngleTo180_float(p_77840_0_.rand.nextFloat() * 360.0F), 0.0F);
					entityliving.rotationYawHead = entityliving.rotationYaw;
					entityliving.renderYawOffset = entityliving.rotationYaw;
					entityliving.onSpawnWithEgg((IEntityLivingData) null);
					p_77840_0_.spawnEntityInWorld(entity);
					entityliving.playLivingSound();
				}
			}

			return entity;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int p_77618_2_) {
		return p_77618_2_ > 0 ? this.theIcon : super.getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_) {
		Iterator<AetherEggInfo> iterator = entityEggs.values().iterator();

		while (iterator.hasNext()) {
			AetherEggInfo entityegginfo = iterator.next();
			p_150895_3_.add(new ItemStack(p_150895_1_, 1, entityegginfo.spawnedID));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister p_94581_1_) {
		super.registerIcons(p_94581_1_);

		this.theIcon = p_94581_1_.registerIcon(this.getIconString() + "_overlay");
	}

}