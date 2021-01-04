package com.gildedgames.the_aether.entities.hostile;

import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.entities.projectile.EntityZephyrSnowball;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import com.gildedgames.the_aether.blocks.BlocksAether;

public class EntityZephyr extends EntityFlying implements IMob {

	public int courseChangeCooldown;

	public double waypointX, waypointY, waypointZ;

	public int prevAttackCounter;

	public int attackCounter;

	private final float base;

	public EntityZephyr(World world) {
		super(world);

		this.setSize(4F, 4F);

		this.attackCounter = 0;
		this.base = (this.getRNG().nextFloat() - this.getRNG().nextFloat()) * 0.2F + 1.0F;
	}

	@Override
	public boolean getCanSpawnHere() {
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.boundingBox.minY);
		int k = MathHelper.floor_double(this.posZ);

		return this.worldObj.getBlock(i, j - 1, k) == BlocksAether.aether_grass && this.rand.nextInt(AetherConfig.getZephyrSpawnrate()) == 0 && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.isAnyLiquid(this.boundingBox) && this.worldObj.getBlockLightValue(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY), MathHelper.floor_double(this.posZ)) > 8 && super.getCanSpawnHere();
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 1;
	}

	@Override
	protected void updateEntityActionState()
	{
		if (!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL)
		{
			this.setDead();
		}

		this.despawnEntity();
		this.prevAttackCounter = this.attackCounter;
		double d0 = this.waypointX - this.posX;
		double d1 = this.waypointY - this.posY;
		double d2 = this.waypointZ - this.posZ;
		double d3 = d0 * d0 + d1 * d1 + d2 * d2;

		if (d3 < 1.0D || d3 > 3600.0D)
		{
			this.waypointX = this.posX + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.waypointY = this.posY + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.waypointZ = this.posZ + (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
		}

		if (this.courseChangeCooldown-- <= 0)
		{
			this.courseChangeCooldown += this.rand.nextInt(5) + 2;
			d3 = (double)MathHelper.sqrt_double(d3);

			if (this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d3))
			{
				this.motionX += d0 / d3 * 0.1D;
				this.motionY += d1 / d3 * 0.1D;
				this.motionZ += d2 / d3 * 0.1D;
			}
			else
			{
				this.waypointX = this.posX;
				this.waypointY = this.posY;
				this.waypointZ = this.posZ;
			}
		}

		this.prevAttackCounter = this.attackCounter;

		if (this.getAttackTarget() == null) {
			if (this.attackCounter > 0) {
				this.attackCounter--;
			}

			this.setAttackTarget(this.worldObj.getClosestVulnerablePlayerToEntity(this, 100D));
		} else {
			if (this.getAttackTarget() instanceof EntityPlayer && (((EntityPlayer) this.getAttackTarget()).capabilities.isCreativeMode)) {
				this.setAttackTarget(null);
				return;
			}

			if (this.getAttackTarget().getDistanceSqToEntity(this) < 4096.0D && this.canEntityBeSeen(this.getAttackTarget())) {
				double x = this.getAttackTarget().posX - this.posX;
				double y = (this.getAttackTarget().boundingBox.minY + (this.getAttackTarget().height / 2.0F)) - (this.posY + (this.height / 2.0F));
				double z = this.getAttackTarget().posZ - this.posZ;

				this.rotationYaw = (-(float) Math.atan2(x, z) * 180F) / 3.141593F;

				++this.attackCounter;

				if (this.attackCounter == 10) {
					this.playSound("aether_legacy:aemob.zephyr.call", 3F, this.base);
				} else if (this.attackCounter == 20) {
					this.playSound("aether_legacy:aemob.zephyr.call", 3F, this.base);

					EntityZephyrSnowball projectile = new EntityZephyrSnowball(this.worldObj, this, x, y, z);
					Vec3 lookVector = this.getLook(1.0F);

					projectile.posX = this.posX + lookVector.xCoord * 4D;
					projectile.posY = this.posY + (double) (this.height / 2.0F) + 0.5D;
					projectile.posZ = this.posZ + lookVector.zCoord * 4D;

					if (!this.worldObj.isRemote) {
						projectile.setThrowableHeading(x, y, z, 1.2F, 1.0F);
						this.worldObj.spawnEntityInWorld(projectile);
					}

					this.attackCounter = -40;
				}
			} else if (this.attackCounter > 0) {
				this.attackCounter--;
			}
		}
	}

	private boolean isCourseTraversable(double p_70790_1_, double p_70790_3_, double p_70790_5_, double p_70790_7_)
	{
		double d4 = (this.waypointX - this.posX) / p_70790_7_;
		double d5 = (this.waypointY - this.posY) / p_70790_7_;
		double d6 = (this.waypointZ - this.posZ) / p_70790_7_;
		AxisAlignedBB axisalignedbb = this.boundingBox.copy();

		for (int i = 1; (double)i < p_70790_7_; ++i)
		{
			axisalignedbb.offset(d4, d5, d6);

			if (!this.worldObj.getCollidingBoundingBoxes(this, axisalignedbb).isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	@Override
	protected String getLivingSound() {
		return "aether_legacy:aemob.zephyr.call";
	}

	@Override
	protected String getHurtSound() {
		return "aether_legacy:aemob.zephyr.call";
	}

	@Override
	protected String getDeathSound() {
		return null;
	}

	@Override
	protected void dropFewItems(boolean var1, int var2) {
		this.dropItem(Item.getItemFromBlock(BlocksAether.aercloud), 1);
	}

	@Override
	public boolean canDespawn() {
		return true;
	}

	@Override
	protected float getSoundVolume() {
		return 1F;
	}

}