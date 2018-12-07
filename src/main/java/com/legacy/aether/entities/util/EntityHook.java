package com.legacy.aether.entities.util;

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

import com.legacy.aether.AetherConfig;
import com.legacy.aether.api.player.util.IAetherBoss;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.player.PlayerAether;
import com.legacy.aether.world.TeleporterAether;

import cpw.mods.fml.common.FMLCommonHandler;

public class EntityHook implements IExtendedEntityProperties {

	private EntityLivingBase entity;

	private boolean inPortal;

	public int teleportDirection;

	@Override
	public void init(Entity entity, World world) {
		this.entity = (EntityLivingBase) entity;
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
				this.teleportEntity(false);
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

		if (this.entity.getAITarget() instanceof EntityPlayer) {
			PlayerAether playerAether = PlayerAether.get((EntityPlayer) this.entity.getAITarget());

			if (playerAether.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.invisibility_cape))) {
				this.entity.setRevengeTarget(null);
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