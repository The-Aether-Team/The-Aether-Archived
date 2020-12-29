package com.gildedgames.the_aether.entities.hostile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockLeaves;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.entities.particles.AetherParticle;
import com.gildedgames.the_aether.entities.particles.ParticleEvilWhirly;
import com.gildedgames.the_aether.entities.particles.ParticlePassiveWhirly;
import com.gildedgames.the_aether.player.perks.AetherRankings;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityWhirlwind extends EntityMob {

	public ArrayList<Object> particles = new ArrayList<Object>();

	public int lifeLeft;

	public int actionTimer;

	public float movementAngle;
	public float movementCurve;

	public boolean isRainbow;

	public boolean canDropItems = true;

	public EntityWhirlwind(World world) {
		super(world);

		this.setSize(0.6F, 0.8F);

		this.movementAngle = this.rand.nextFloat() * 360F;
		this.movementCurve = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;

		this.lifeLeft = this.rand.nextInt(512) + 512;

		if (this.rand.nextInt(10) == 0) {
			this.lifeLeft /= 2;
			this.setEvil(true);
		}

		this.setColorData(15);
	}

	@Override
	public float getBlockPathWeight(int x, int y, int z) {
		return this.worldObj.getBlock(x, y - 1, z) == BlocksAether.aether_grass ? 10.0F : this.worldObj.getLightBrightness(x, y, z) - 0.5F;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue((this.rand.nextDouble() * 0.025D) + 0.025D);
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35.0D);

		this.setHealth(10.0F);
	}

	@Override
	protected void entityInit() {
		super.entityInit();

		this.dataWatcher.addObject(20, new Byte((byte) 0));
		this.dataWatcher.addObject(21, new Byte((byte) 15));
	}

	public void setColorData(int data) {
		this.dataWatcher.updateObject(21, (byte) data);
	}

	public int getColorData() {
		return (int) this.dataWatcher.getWatchableObjectByte(21);
	}

	public void setEvil(boolean isEvil) {
		this.dataWatcher.updateObject(20, (byte) (isEvil ? 1 : 0));
	}

	public boolean isEvil() {
		return this.dataWatcher.getWatchableObjectByte(20) == (byte) 1;
	}

	public void onLivingUpdate() {
		EntityPlayer closestPlayer = this.findClosestPlayer();

		if (this.isEvil()) {
			if (closestPlayer != null && closestPlayer.onGround) {
				this.setAttackTarget(closestPlayer);
			}
		}

		if (this.getAttackTarget() == null) {
			this.motionX = Math.cos(0.01745329F * this.movementAngle) * this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
			this.motionZ = -Math.sin(0.01745329F * this.movementAngle) * this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
			this.movementAngle += this.movementCurve;
		} else {
			super.onLivingUpdate();
		}

		if (this.lifeLeft != -100) {
			--this.lifeLeft;
		}

		if ((this.lifeLeft <= 0 && this.lifeLeft != -100) || this.handleWaterMovement()) {
			this.setDead();
		}

		if (!this.worldObj.isRemote) {
			if (closestPlayer != null) {
				this.actionTimer++;
			}

			if (this.actionTimer >= 128) {
				if (this.isEvil()) {
					EntityCreeper entitycreeper = new EntityCreeper(this.worldObj);

					entitycreeper.setLocationAndAngles(this.posX, this.posY + 0.5D, this.posZ, this.rand.nextFloat() * 360F, 0.0F);
					entitycreeper.motionX = (double) (this.rand.nextFloat() - this.rand.nextFloat()) * 0.125D;
					entitycreeper.motionZ = (double) (this.rand.nextFloat() - this.rand.nextFloat()) * 0.125D;

					this.worldObj.spawnEntityInWorld(entitycreeper);
					this.actionTimer = 0;
					this.worldObj.playSoundAtEntity(this, "random.pop", 0.5F, 1.0F);
				} else if (this.canDropItems && this.rand.nextInt(4) == 0) {
					this.dropItem(this.getRandomDrop(), 1);
					this.actionTimer = 0;
					this.worldObj.playSoundAtEntity(this, "random.pop", 0.5F, 1.0F);
				}
			}
		} else {
			this.updateParticles();
		}

		List<?> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(2.5D, 2.5D, 2.5D));

		for (int l = 0; l < list.size(); l++) {
			Entity entity = (Entity) list.get(l);

			double d9 = (float) entity.posX;
			double d11 = (float) entity.posY - entity.getYOffset() * 0.6F;
			double d13 = (float) entity.posZ;
			double d15 = this.getDistanceToEntity(entity);
			double d17 = d11 - this.posY;

			if (d15 <= 1.5D + d17) {
				entity.motionY = 0.15000000596046448D;
				entity.fallDistance = 0.0F;

				if (d17 > 1.5D) {
					entity.motionY = -0.44999998807907104D + d17 * 0.34999999403953552D;
					d15 += d17 * 1.5D;
				} else {
					entity.motionY = 0.125D;
				}

				double d19 = Math.atan2(this.posX - d9, this.posZ - d13) / 0.01745329424738884D;
				d19 += 160D;
				entity.motionX = -Math.cos(0.01745329424738884D * d19) * (d15 + 0.25D) * 0.10000000149011612D;
				entity.motionZ = Math.sin(0.01745329424738884D * d19) * (d15 + 0.25D) * 0.10000000149011612D;

				if (entity instanceof EntityWhirlwind) {
					entity.setDead();

					if (!this.isEvil()) {
						this.lifeLeft /= 2;
						this.setEvil(true);
						this.playSound("random.fizz", this.rand.nextFloat() - this.rand.nextFloat() * 0.2F + 1.2F, 1.0F);
					}
				}
			} else {
				double d20 = Math.atan2(this.posX - d9, this.posZ - d13) / 0.01745329424738884D;
				entity.motionX += Math.sin(0.01745329424738884D * d20) * 0.0099999997764825821D;
				entity.motionZ += Math.cos(0.01745329424738884D * d20) * 0.0099999997764825821D;
			}

			if (!this.worldObj.isAirBlock((int) this.posX, (int) this.posY, (int) this.posZ)) {
				this.lifeLeft -= 50;
			}

			if (this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
				int i2 = (MathHelper.floor_double(this.posX) - 1) + this.rand.nextInt(3);
				int j2 = MathHelper.floor_double(this.posY) + this.rand.nextInt(5);
				int k2 = (MathHelper.floor_double(this.posZ) - 1) + this.rand.nextInt(3);

				if (this.worldObj.getBlock(i2, j2, k2) instanceof BlockLeaves) {
					this.worldObj.setBlock(i2, j2, k2, Blocks.air);
				}
			}
		}
	}

	@Override
	public boolean interact(EntityPlayer player) {
		ItemStack heldItem = player.getCurrentEquippedItem();

		if (heldItem != null && heldItem.getItem() == Items.dye && AetherRankings.isRankedPlayer(player.getUniqueID())) {
			this.setColorData(heldItem.getItemDamage());

			return true;
		}

		return super.interact(player);
	}

	@Override
	public boolean canTriggerWalking() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public void updateParticles() {
		if (!this.isEvil()) {
			Integer color = ItemDye.field_150922_c[this.getColorData()];

			for (int k = 0; k < 2; k++) {
				double d1 = (float) this.posX + rand.nextFloat() * 0.25F;
				double d4 = (float) posY + height + 0.125F;
				double d7 = (float) this.posZ + rand.nextFloat() * 0.25F;
				float f = rand.nextFloat() * 360F;

				AetherParticle particle = new ParticlePassiveWhirly(this.worldObj, -Math.sin(0.01745329F * f) * 0.75D, d4 - 0.25D, Math.cos(0.01745329F * f) * 0.75D, d1, 0.125D, d7);
				FMLClientHandler.instance().getClient().effectRenderer.addEffect(particle);
				this.particles.add(particle);

				particle.setRBGColorF((((color >> 16) & 0xFF) / 255F), (((color >> 8) & 0xFF) / 255F), ((color & 0xFF) / 255F));

				if (this.isRainbow) {
					int k1 = this.ticksExisted / 25 + this.getEntityId();
					int l = k1 % EntitySheep.fleeceColorTable.length;
					int i1 = (k1 + 1) % EntitySheep.fleeceColorTable.length;
					float f1 = ((float) (this.ticksExisted % 25)) / 25.0F;
					particle.setRBGColorF(EntitySheep.fleeceColorTable[l][0] * (1.0F - f1) + EntitySheep.fleeceColorTable[i1][0] * f1, EntitySheep.fleeceColorTable[l][1] * (1.0F - f1) + EntitySheep.fleeceColorTable[i1][1] * f1, EntitySheep.fleeceColorTable[l][2] * (1.0F - f1) + EntitySheep.fleeceColorTable[i1][2] * f1);
				}

				particle.setPosition(this.posX, this.posY, this.posZ);
			}
		} else {
			for (int k = 0; k < 3; k++) {
				double d2 = (float) posX + rand.nextFloat() * 0.25F;
				double d5 = (float) posY + height + 0.125F;
				double d8 = (float) posZ + rand.nextFloat() * 0.25F;
				float f1 = rand.nextFloat() * 360F;
				AetherParticle particle = new ParticleEvilWhirly(this.worldObj, -Math.sin(0.01745329F * f1) * 0.75D, d5 - 0.25D, Math.cos(0.01745329F * f1) * 0.75D, d2, 0.125D, d8, 3.5F);
				FMLClientHandler.instance().getClient().effectRenderer.addEffect(particle);
				this.particles.add(particle);

				particle.setPosition(this.posX, this.posY, this.posZ);
			}
		}

		if (this.particles.size() > 0) {
			for (int i1 = 0; i1 < this.particles.size(); i1++) {
				AetherParticle particle = (AetherParticle) this.particles.get(i1);

				if (particle.isDead) {
					this.particles.remove(particle);
				} else {
					double d10 = particle.getX();
					double d12 = particle.boundingBox.minY;
					double d14 = particle.getZ();
					double d16 = this.getDistanceToEntity(particle);
					double d18 = d12 - this.posY;
					particle.setMotionY(0.11500000208616257D);
					double d21 = Math.atan2(this.posX - d10, this.posZ - d14) / 0.01745329424738884D;
					d21 += 160D;
					particle.setMotionX(-Math.cos(0.01745329424738884D * d21) * (d16 * 2.5D - d18) * 0.10000000149011612D);
					particle.setMotionZ(Math.sin(0.01745329424738884D * d21) * (d16 * 2.5D - d18) * 0.10000000149011612D);
				}
			}
		}
	}

	public Item getRandomDrop() {
		int i = this.rand.nextInt(100) + 1;

		if (i == 100) {
			return Items.diamond;
		}

		if (i >= 96) {
			return Items.iron_ingot;
		}

		if (i >= 91) {
			return Items.gold_ingot;
		}

		if (i >= 82) {
			return Items.coal;
		}

		if (i >= 80) {
			return Item.getItemFromBlock(Blocks.pumpkin);
		}

		if (i >= 75) {
			return Item.getItemFromBlock(Blocks.gravel);
		}

		if (i >= 64) {
			return Item.getItemFromBlock(Blocks.clay);
		}

		if (i >= 52) {
			return Items.stick;
		}

		if (i >= 38) {
			return Items.flint;
		}

		if (i > 20) {
			return Item.getItemFromBlock(Blocks.log);
		} else {
			return Item.getItemFromBlock(Blocks.sand);
		}
	}

	@Override
	public boolean getCanSpawnHere() {
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.boundingBox.minY);
		int k = MathHelper.floor_double(this.posZ);

		return this.rand.nextInt(15) == 0 && this.worldObj.getBlock(i, j - 1, k) == BlocksAether.aether_grass && this.worldObj.getBlockLightValue(i, j, k) > 8 && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
	}

	public EntityPlayer findClosestPlayer() {
		return this.worldObj.getClosestPlayerToEntity(this, 16D);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);

		compound.setFloat("movementAngle", this.movementAngle);
		compound.setFloat("movementCurve", this.movementCurve);
		compound.setBoolean("isRainbow", this.isRainbow);
		compound.setBoolean("canDropItems", this.canDropItems);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);

		this.movementAngle = compound.getFloat("movementAngle");
		this.movementCurve = compound.getFloat("movementCurve");
		this.isRainbow = compound.getBoolean("isRainbow");
		this.canDropItems = compound.getBoolean("canDropItems");
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float damage) {
		return false;
	}

	@Override
	public void applyEntityCollision(Entity entity) {

	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 3;
	}

	@Override
	public boolean isOnLadder() {
		return this.isCollidedHorizontally;
	}

}