package com.gildedgames.the_aether.entities.util;

import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.api.player.util.IAetherBoss;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.player.PlayerAether;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.gildedgames.the_aether.world.TeleporterAether;

import cpw.mods.fml.common.FMLCommonHandler;

public class EntityHook implements IExtendedEntityProperties {

	private Entity entity;

	private boolean inPortal;

	public int teleportDirection;

	@Override
	public void init(Entity entity, World world) {
		this.entity = entity;
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {

	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {

	}

	public void onUpdate() {
		this.entity.worldObj.theProfiler.startSection("portal");

		if (this.entity.dimension == AetherConfig.getAetherDimensionID()) {
			if (this.entity.posY < -2 && this.entity.riddenByEntity == null && this.entity.ridingEntity == null) {
				if (!this.entity.worldObj.isRemote) {
					this.teleportEntity(false);
				}
			}
		}

		if (this.inPortal) {
			if (this.entity.ridingEntity == null) {
				this.entity.timeUntilPortal = this.entity.getPortalCooldown();

				if (!this.entity.worldObj.isRemote) {
					this.teleportEntity(true);
				}
			}

			this.inPortal = false;
		}

		if (this.entity.timeUntilPortal > 0) {
			--this.entity.timeUntilPortal;
		}

		this.entity.worldObj.theProfiler.endSection();

		if (this.entity instanceof EntityLiving) {
			EntityLiving livingEntity = (EntityLiving) this.entity;

			if (livingEntity.getAttackTarget() instanceof EntityPlayer) {
				PlayerAether playerAether = PlayerAether.get((EntityPlayer) livingEntity.getAttackTarget());

				if (playerAether.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.invisibility_cape))) {
					livingEntity.setAttackTarget(null);
				}
			}
		}

		if (this.entity instanceof EntityCreature) {
			EntityCreature creature = (EntityCreature) this.entity;

			if (creature.getEntityToAttack() instanceof EntityPlayer) {
				PlayerAether playerAether = PlayerAether.get((EntityPlayer) creature.getEntityToAttack());

				if (playerAether.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.invisibility_cape))) {
					creature.setTarget(null);
				}
			}
		}

		if (this.entity instanceof EntityLivingBase)
		{
			EntityLivingBase living = (EntityLivingBase) this.entity;

			if (living.getAITarget() instanceof EntityPlayer) {
				PlayerAether playerAether = PlayerAether.get((EntityPlayer) living.getAITarget());

				if (playerAether.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.invisibility_cape))) {
					living.setRevengeTarget(null);
				}
			}
		}
	}

	public void setInPortal() {
		if (this.entity instanceof IAetherBoss || this.entity instanceof IBossDisplayData) {
			return;
		}

		if (this.entity.timeUntilPortal > 0) {
			this.entity.timeUntilPortal = this.entity.getPortalCooldown();
		} else {
			double d0 = this.entity.prevPosX - this.entity.posX;
			double d1 = this.entity.prevPosZ - this.entity.posZ;

			if (!this.entity.worldObj.isRemote && !this.inPortal) {
				this.teleportDirection = Direction.getMovementDirection(d0, d1);
			}

			this.inPortal = true;
		}
	}

	private void teleportEntity(boolean shouldSpawnPortal) {
		try {
			int previousDimension = this.entity.dimension;
			int transferDimension = previousDimension == AetherConfig.getAetherDimensionID() ? AetherConfig.getTravelDimensionID() : AetherConfig.getAetherDimensionID();
			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
			WorldServer previousWorldIn = server.worldServerForDimension(previousDimension);
			WorldServer newWorldIn = server.worldServerForDimension(transferDimension);

			this.entity.dimension = newWorldIn.provider.dimensionId;
			previousWorldIn.removePlayerEntityDangerously(this.entity);
			this.entity.isDead = false;

			server.getConfigurationManager().transferEntityToWorld(this.entity, previousWorldIn.provider.dimensionId, previousWorldIn, newWorldIn, new TeleporterAether(shouldSpawnPortal, newWorldIn));
		} catch (Exception e) {

		}
	}

}