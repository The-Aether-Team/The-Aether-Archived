package com.legacy.aether.entities.passive;

import com.legacy.aether.entities.ai.aerwhale.AerwhaleAITravelCourse;
import com.legacy.aether.entities.ai.aerwhale.AerwhaleAIUnstuck;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.entities.ai.EntityAIUpdateState;

public class EntityAerwhale extends EntityFlying {

	private double motionYaw;

	private double motionPitch;

	public double aerwhaleRotationYaw;

	public double aerwhaleRotationPitch;

	public EntityAerwhale(World world) {
		super(world);

		this.setSize(3F, 3F);
		this.isImmuneToFire = true;
		this.ignoreFrustumCheck = true;
		this.rotationYaw = 360F * this.getRNG().nextFloat();
		this.rotationPitch = 90F * this.getRNG().nextFloat() - 45F;
		this.tasks.addTask(1, new AerwhaleAIUnstuck(this));
		this.tasks.addTask(5, new AerwhaleAITravelCourse(this));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.0D);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	public boolean getCanSpawnHere() {
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.boundingBox.minY);
		int k = MathHelper.floor_double(this.posZ);

		return this.worldObj.getBlock(i, j - 1, k) == BlocksAether.aether_grass && this.rand.nextInt(65) == 0 && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.isAnyLiquid(this.boundingBox) && this.worldObj.getBlockLightValue(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY), MathHelper.floor_double(this.posZ)) > 8 && super.getCanSpawnHere();
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 1;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		this.extinguish();

		if (this.posY < -64.0D)
		{
			this.setDead();
		}

		this.aerwhaleRotationYaw = this.rotationYaw;
		this.aerwhaleRotationPitch = this.rotationPitch;
	}

	@Override
	public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
	{
		this.stepHeight = 0.5F;
		this.jumpMovementFactor = 0.02F;
		super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
	}

	@Override
	public String getLivingSound() {
		return "aether_legacy:aemob.aerwhale.call";
	}

	@Override
	protected String getHurtSound() {
		return "aether_legacy:aemob.aerwhale.death";
	}

	@Override
	protected String getDeathSound() {
		return "aether_legacy:aemob.aerwhale.death";
	}

	@Override
	public boolean canDespawn() {
		return true;
	}

}