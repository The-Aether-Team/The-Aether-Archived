package com.legacy.aether;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.portal.BlockAetherPortal;
import com.legacy.aether.entities.EntitiesAether;
import com.legacy.aether.entities.bosses.EntityValkyrie;
import com.legacy.aether.entities.passive.mountable.EntityFlyingCow;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.items.dungeon.ItemDungeonKey;
import com.legacy.aether.items.util.EnumSkyrootBucketType;
import com.legacy.aether.items.weapons.ItemSkyrootSword;
import com.legacy.aether.registry.achievements.AchievementsAether;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class
AetherEventHandler {

	@SubscribeEvent
	public void checkBlockBannedEvent(PlayerInteractEvent event) {
		EntityPlayer player = event.entityPlayer;
		ItemStack currentStack = player.getCurrentEquippedItem();

		if (player.dimension == AetherConfig.getAetherDimensionID()) {
			if (currentStack != null) {
				if (currentStack.getItem() == Items.flint_and_steel || currentStack.getItem() == Item.getItemFromBlock(Blocks.torch) || currentStack.getItem() == Items.fire_charge) {
					for (int i = 0; i < 10; ++i) {
						event.world.spawnParticle("smoke", event.x, event.y, event.z, 0.0D, 0.0D, 0.0D);
					}

					event.setCanceled(true);
				}
			} else if (event.world.getBlock(event.x, event.y, event.z) == Blocks.bed) {
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onEntityInteract(EntityInteractEvent event) {
		if (event.target instanceof EntityAgeable) {
			ItemStack itemstack = event.entityPlayer.inventory.getCurrentItem();

			if (itemstack != null && itemstack.getItem() == ItemsAether.aether_spawn_egg) {
				if (!event.entityPlayer.worldObj.isRemote) {
					Class<?> oclass = EntitiesAether.getClassFromID(itemstack.getItemDamage());

					if (oclass != null && oclass.isAssignableFrom(this.getClass())) {
						EntityAgeable entityageable = ((EntityAgeable) event.target).createChild((EntityAgeable) event.target);

						if (entityageable != null) {
							entityageable.setGrowingAge(-24000);
							entityageable.setLocationAndAngles(event.target.posX, event.target.posY, event.target.posZ, 0.0F, 0.0F);
							event.entityPlayer.worldObj.spawnEntityInWorld(entityageable);

							if (itemstack.hasDisplayName()) {
								entityageable.setCustomNameTag(itemstack.getDisplayName());
							}

							if (!event.entityPlayer.capabilities.isCreativeMode) {
								--itemstack.stackSize;

								if (itemstack.stackSize <= 0) {
									event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, (ItemStack) null);
								}
							}
						}
					}
				}
			}
		}

		if (event.target instanceof EntityCow || event.target instanceof EntityFlyingCow) {
			EntityPlayer player = event.entityPlayer;
			ItemStack heldItem = player.getCurrentEquippedItem();

			if (heldItem != null && heldItem.getItem() == ItemsAether.skyroot_bucket && EnumSkyrootBucketType.getType(heldItem.getItemDamage()) == EnumSkyrootBucketType.Empty) {
				if (!player.capabilities.isCreativeMode) {
					--heldItem.stackSize;
				}

				if (heldItem.stackSize <= 0) {
					player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(ItemsAether.skyroot_bucket, 1, EnumSkyrootBucketType.Milk.meta));
				} else if (!player.inventory.addItemStackToInventory(new ItemStack(ItemsAether.skyroot_bucket, 1, EnumSkyrootBucketType.Milk.meta))) {
					player.dropPlayerItemWithRandomChoice(new ItemStack(ItemsAether.skyroot_bucket, 1, EnumSkyrootBucketType.Milk.meta), false);
				}
			}
		}
	}

	@SubscribeEvent
	public void onFillBucket(FillBucketEvent event) {
		World worldObj = event.world;
		MovingObjectPosition target = event.target;
		ItemStack stack = event.current;
		EntityPlayer player = event.entityPlayer;

		boolean isWater = (!AetherConfig.activateOnlyWithSkyroot() && stack.getItem() == Items.water_bucket) || (stack.getItem() == ItemsAether.skyroot_bucket && stack.getItemDamage() == 1);
		boolean isLava = stack.getItem() == Items.lava_bucket;

		boolean validDimension = (player.dimension == AetherConfig.getTravelDimensionID() || player.dimension == AetherConfig.getAetherDimensionID());

		if (target != null && target.typeOfHit == MovingObjectType.BLOCK && validDimension) {
			int i = target.blockX;
			int j = target.blockY;
			int k = target.blockZ;

			if (target.sideHit == 0) {
				--j;
			}

			if (target.sideHit == 1) {
				++j;
			}

			if (target.sideHit == 2) {
				--k;
			}

			if (target.sideHit == 3) {
				++k;
			}

			if (target.sideHit == 4) {
				--i;
			}

			if (isWater) {
				if (((BlockAetherPortal) BlocksAether.aether_portal).trySpawnPortal(worldObj, i, j, k)) {
					if (!player.capabilities.isCreativeMode) {
						if (stack.getItem() == ItemsAether.skyroot_bucket && stack.getItemDamage() == 1) {
							event.result = new ItemStack(ItemsAether.skyroot_bucket);
						}

						if (stack.getItem() == Items.water_bucket) {
							event.result = new ItemStack(Items.bucket);
						}
					}

					event.setResult(Event.Result.ALLOW);
				}
			}

			if (isLava && player.dimension == AetherConfig.getAetherDimensionID()) {
				if (player.capabilities.isCreativeMode && player.isSneaking()) {
					return;
				}

				if (worldObj.isAirBlock(i, j, k)) {
					worldObj.setBlock(i, j, k, BlocksAether.aerogel);

					if (!player.capabilities.isCreativeMode) {
						event.result = new ItemStack(Items.bucket);
					}
				}

				event.setResult(Event.Result.ALLOW);
			}
		}
	}

	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent event) {
		if (this.isGravititeTool(event.crafting.getItem())) {
			event.player.triggerAchievement(AchievementsAether.grav_tools);
		} else if (event.crafting.getItem() == Item.getItemFromBlock(BlocksAether.enchanter)) {
			event.player.triggerAchievement(AchievementsAether.enchanter);
		}
	}

	@SubscribeEvent
	public void onEntityDropLoot(LivingDropsEvent event) {
		if (event.source instanceof EntityDamageSource) {
			EntityLivingBase entity = event.entityLiving;
			EntityDamageSource source = (EntityDamageSource) event.source;

			if (source.getEntity() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) source.getEntity();
				ItemStack currentItem = player.inventory.getCurrentItem();

				if (currentItem != null && currentItem.getItem() instanceof ItemSkyrootSword && !(entity instanceof EntityPlayer) && !(entity instanceof EntityWither) && !(entity instanceof EntityValkyrie)) {
					for (EntityItem items : event.drops) {
						ItemStack stack = items.getEntityItem();

						if (!(stack.getItem() instanceof ItemDungeonKey) && stack.getItem() != ItemsAether.victory_medal && stack.getItem() != Items.skull) {
							EntityItem item = new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, items.getEntityItem());

							entity.worldObj.spawnEntityInWorld(item);
						}
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onEntityHurt(EntityStruckByLightningEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;

			if (player.inventory.getCurrentItem().getItem() == ItemsAether.lightning_sword || player.inventory.getCurrentItem().getItem() == ItemsAether.lightning_knife)
			{
				event.setCanceled(true);
			}
		}
	}

	public boolean isGravititeTool(Item stackID) {
		return stackID == ItemsAether.gravitite_shovel || stackID == ItemsAether.gravitite_axe || stackID == ItemsAether.gravitite_pickaxe;
	}

}