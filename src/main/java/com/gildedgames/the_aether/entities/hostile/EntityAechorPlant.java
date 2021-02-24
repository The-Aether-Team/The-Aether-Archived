package com.gildedgames.the_aether.entities.hostile;

import com.gildedgames.the_aether.AetherConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.entities.passive.EntityAetherAnimal;
import com.gildedgames.the_aether.entities.projectile.EntityPoisonNeedle;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.items.util.EnumSkyrootBucketType;

public class EntityAechorPlant extends EntityAetherAnimal {

	public float sinage;

	public int poisonRemaining;

	private int reloadTime;

	public EntityAechorPlant(World world) {
		super(world);

		this.sinage = this.rand.nextFloat() * 6F;
		this.poisonRemaining = this.rand.nextInt(4) + 2;

		this.setSize(this.rand.nextInt(4) + 1);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.setSize(0.75F + ((float) this.getSize() * 0.125F), 0.5F + ((float) this.getSize() * 0.075F));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
	}

	@Override
	public void entityInit() {
		super.entityInit();

		this.dataWatcher.addObject(20, new Byte((byte) 0));
	}

	@Override
	public boolean getCanSpawnHere()
	{
		return this.rand.nextInt(AetherConfig.getAechorPlantSpawnrate()) == 0 && super.getCanSpawnHere();
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		if (this.hurtTime > 0) {
			this.sinage += 0.9F;
		} else {
			if (this.getEntityToAttack() != null) {
				this.sinage += 0.3F;
			} else {
				this.sinage += 0.1F;
			}
		}

		if (this.sinage > 3.141593F * 2F) {
			this.sinage -= (3.141593F * 2F);
		}

		if (this.getEntityToAttack() == null) {
			EntityPlayer player = this.worldObj.getClosestVulnerablePlayerToEntity(this, 10.0F);

			this.setTarget(player);
		}

		if (!this.isDead && this.getEntityToAttack() != null) {
			double distanceToPlayer = this.getEntityToAttack().getDistanceToEntity(this);
			double lookDistance = 5.5D + ((double) this.getSize() / 2D);

			if (this.getEntityToAttack().isDead || distanceToPlayer > lookDistance) {
				this.setTarget(null);
				this.reloadTime = 0;
			}

			if (this.reloadTime == 20 && this.canEntityBeSeen(this.getEntityToAttack())) {
				this.shootAtPlayer();
				this.reloadTime = -10;
			}

			if (this.reloadTime != 20) {
				++this.reloadTime;
			}
		}

		if (this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) - 1, MathHelper.floor_double(this.posZ)) != BlocksAether.aether_grass) {
			this.setHealth(0.0F);
		}
	}

	public void shootAtPlayer() {
		if (this.worldObj.difficultySetting.equals(EnumDifficulty.PEACEFUL)) {
			return;
		}

		double x = this.getEntityToAttack().posX - this.posX;
		double z = this.getEntityToAttack().posZ - this.posZ;
		double y = 0.1D + (Math.sqrt((x * x) + (z * z) + 0.1D) * 0.5D) + ((this.posY - this.getEntityToAttack().posY) * 0.25D);

		double distance = 1.5D / Math.sqrt((x * x) + (z * z) + 0.1D);

		x = x * distance;
		z = z * distance;

		EntityPoisonNeedle poisonNeedle = new EntityPoisonNeedle(this.worldObj, this, 0.5F);

		poisonNeedle.posY = this.posY + 1D;

		this.playSound("random.bow", 1.0F, 1.2F / (this.getRNG().nextFloat() * 0.2F + 0.9F));
		this.worldObj.spawnEntityInWorld(poisonNeedle);

		poisonNeedle.setThrowableHeading(x, y, z, 0.285F + ((float) y * 0.05F), 1.0F);
	}

	@Override
	public void knockBack(Entity entity, float strength, double xRatio, double zRatio) {
		if (this.getHealth() >= 0) {
			return;
		}

		super.knockBack(entity, strength, xRatio, zRatio);
	}

	@Override
	public boolean interact(EntityPlayer player) {
		ItemStack heldItem = player.getCurrentEquippedItem();

		if (heldItem != null && !this.worldObj.isRemote) {
			if (heldItem.getItem() == ItemsAether.skyroot_bucket && EnumSkyrootBucketType.getType(heldItem.getItemDamage()) == EnumSkyrootBucketType.Empty && this.poisonRemaining > 0) {
				if (--heldItem.stackSize == 0) {
					player.setCurrentItemOrArmor(0, new ItemStack(ItemsAether.skyroot_bucket, 1, EnumSkyrootBucketType.Poison.meta));
				} else if (!player.inventory.addItemStackToInventory(new ItemStack(ItemsAether.skyroot_bucket, 1, EnumSkyrootBucketType.Poison.meta))) {
					player.entityDropItem(new ItemStack(ItemsAether.skyroot_bucket, 1, EnumSkyrootBucketType.Poison.meta), 1.0F);
				}

				--this.poisonRemaining;
			}
		}

		return false;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);

		compound.setByte("size", this.getSize());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);

		this.setSize(compound.getByte("size"));
	}

	public void setSize(int size) {
		this.dataWatcher.updateObject(20, (byte) size);
	}

	public byte getSize() {
		return this.dataWatcher.getWatchableObjectByte(20);
	}

	@Override
	protected void dropFewItems(boolean var1, int var2) {
		this.dropItem(ItemsAether.aechor_petal, 2);
	}

	@Override
	public void applyEntityCollision(Entity entity) {

	}

	@Override
	public void addVelocity(double x, double y, double z) {

	}

	@Override
	protected boolean isMovementBlocked() {
		return true;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable baby) {
		return null;
	}

	@Override
	protected String getHurtSound() {
		return "game.player.hurt";
	}

	@Override
	protected String getDeathSound() {
		return "game.player.hurt.fall.big";
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	protected boolean canDespawn() {
		return true;
	}

}