package com.gildedgames.the_aether.entities.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class EntitySaddleMount extends EntityMountable {

	public EntitySaddleMount(World world) {
		super(world);
	}

	@Override
	public void entityInit() {
		super.entityInit();

		this.dataWatcher.addObject(19, new Byte((byte) 0));
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		if (!this.canSaddle()) {
			return super.interact(entityplayer);
		}

		if (!this.isSaddled()) {
			if (entityplayer.inventory.getCurrentItem() != null && (entityplayer.inventory.getCurrentItem().getItem() == Items.saddle) && !this.isChild()) {
				if (!entityplayer.capabilities.isCreativeMode) {
					entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
				}

				if (entityplayer.worldObj.isRemote) {
					entityplayer.worldObj.playSoundAtEntity(this, "mob.horse.leather", 0.5F, 1.0F);
				}

				this.setSaddled(true);

				return true;
			}
		} else if (this.riddenByEntity == null) {
			if (!entityplayer.worldObj.isRemote) {
				entityplayer.mountEntity(this);
				entityplayer.prevRotationYaw = entityplayer.rotationYaw = this.rotationYaw;
			}

			return true;
		}

		return super.interact(entityplayer);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float i) {
		if ((damagesource.getEntity() instanceof EntityPlayer) && (this.riddenByEntity == damagesource.getEntity())) {
			return false;
		}

		return super.attackEntityFrom(damagesource, i);
	}

	@Override
	protected void dropFewItems(boolean recentlyHit, int lootLevel) {
		super.dropFewItems(recentlyHit, lootLevel);

		if (this.isSaddled()) {
			this.dropItem(Items.saddle, 1);
		}
	}

	@Override
	public boolean isEntityInsideOpaqueBlock() {
		return this.riddenByEntity != null ? false : super.isEntityInsideOpaqueBlock();
	}

	@Override
	public boolean shouldRiderFaceForward(EntityPlayer player) {
		return false;
	}

	@Override
	protected boolean canTriggerWalking() {
		return this.onGround;
	}

	@Override
	public boolean canBeSteered() {
		return true;
	}

	public void setSaddled(boolean saddled) {
		this.dataWatcher.updateObject(19, (byte) (saddled ? 1 : 0));
	}

	public boolean isSaddled() {
		return this.dataWatcher.getWatchableObjectByte(19) == (byte) 1;
	}

	public boolean canSaddle() {
		return true;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);

		compound.setBoolean("isSaddled", this.isSaddled());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);

		this.setSaddled(compound.getBoolean("isSaddled"));
	}

}