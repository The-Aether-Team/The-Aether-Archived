package com.gildedgames.the_aether.entities.passive.mountable;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.entities.ai.swet.*;
import com.gildedgames.the_aether.entities.util.EnumSwetType;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.networking.AetherNetworkingManager;
import com.gildedgames.the_aether.networking.packets.PacketSwetJump;
import com.gildedgames.the_aether.registry.AetherLootTables;
import com.gildedgames.the_aether.registry.sounds.SoundsAether;
import io.netty.buffer.ByteBuf;

import com.gildedgames.the_aether.entities.util.EntityMountable;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import javax.annotation.Nullable;

public class EntitySwet extends EntityMountable implements IEntityAdditionalSpawnData
{
	private int swetType;

	public boolean wasOnGround;
	public boolean midJump;
	public int jumpTimer;

	public float swetHeight;
	public float swetWidth;

	public EntitySwet(World world)
	{
		super(world);
		this.moveHelper = new SwetMoveHelper(this);

		this.setSize(0.8F, 0.8F);
		this.setPosition(this.posX, this.posY, this.posZ);
	}

	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(0, new SwetAIConsume(this));
		this.tasks.addTask(1, new SwetAIHunt(this));
		this.tasks.addTask(2, new SwetAIFaceRandom(this));
		this.tasks.addTask(4, new SwetAIHop(this));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.5D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(25.0D);
		this.setHealth(25.0F);
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
	{
		this.setType(this.rand.nextInt(2));
		this.swetHeight = 1.0F;
		this.swetWidth = 1.0F;
		return super.onInitialSpawn(difficulty, livingdata);
	}

	@Override
	protected void collideWithEntity(Entity entityIn)
	{
		super.collideWithEntity(entityIn);

		if (!this.hasPrey())
		{
			if (entityIn instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) entityIn;

				if (this.getAttackTarget() != null)
				{
					if (this.getAttackTarget() == player)
					{
						if (!player.isCreative())
						{
							this.capturePrey((EntityPlayer) entityIn);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand)
	{
		if (!this.world.isRemote)
		{
			if (!this.hasPrey())
			{
				this.capturePrey(player);
			}
		}

		return true;
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();

		if (this.getAttackTarget() instanceof EntityPlayer)
		{
			if (this.isPlayerFriendly((EntityPlayer) this.getAttackTarget()) || this.isFriendly())
			{
				this.setAttackTarget(null);
			}
		}
	}

	public void capturePrey(EntityPlayer entity)
	{
		this.playSound(SoundsAether.swet_attack, 0.5F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);

		this.prevPosX = this.posX = entity.posX;
		this.prevPosY = this.posY = entity.posY + 0.01;
		this.prevPosZ = this.posZ = entity.posZ;
		this.prevRotationYaw = this.rotationYaw = entity.rotationYaw;
		this.prevRotationPitch = this.rotationPitch = entity.rotationPitch;
		this.motionX = entity.motionX;
		this.motionY = entity.motionY;
		this.motionZ = entity.motionZ;

		this.setSize(entity.width, entity.height);
		this.setPosition(this.posX, this.posY, this.posZ);

		entity.startRiding(this);

		this.rotationYaw = this.rand.nextFloat() * 360F;
	}

	public void onUpdate()
	{
		if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
		{
			this.isDead = true;
		}

		if (this.handleWaterMovement())
		{
			this.dissolveSwet();
		}

		super.onUpdate();

		if (!this.hasPrey())
		{
			for (int i = 0; i < 5; i++)
			{
				double d = (float) this.posX + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.3F;
				double d1 = (float) this.posY + this.height;
				double d2 = (float) this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.3F;
				this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, d, d1 - 0.25D, d2, 0.0D, 0.0D, 0.0D);
			}
		}

		if (this.onGround && !this.wasOnGround)
		{
			this.playSound(SoundsAether.swet_squish, this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
		}

		if (!this.world.isRemote)
		{
			this.midJump = !this.onGround;
			AetherNetworkingManager.sendToAll(new PacketSwetJump(this.getEntityId(), !this.onGround));
		}

		if (this.world.isRemote)
		{
			if (this.midJump)
			{
				this.jumpTimer++;
			}
			else
			{
				this.jumpTimer = 0;
			}

			if (this.onGround)
			{
				this.swetHeight = this.swetHeight < 1.0F ? this.swetHeight += 0.25F : 1.0F;
				this.swetWidth = this.swetWidth > 1.0F ? this.swetWidth -= 0.25F : 1.0F;
			}
			else
			{
				this.swetHeight = 1.425F;
				this.swetWidth = 0.875F;

				if (this.getJumpTimer() > 3)
				{
					float scale = Math.min(this.getJumpTimer(), 10);
					this.swetHeight -= 0.05F * scale;
					this.swetWidth += 0.05F * scale;
				}
			}
		}

		this.wasOnGround = this.onGround;
	}

	@Override
	public void travel(float strafe, float vertical, float forward)
	{
		if (this.hasPrey())
		{
			if (this.isFriendly())
			{
				EntityPlayer rider = (EntityPlayer) this.getPassengers().get(0);
				IPlayerAether aetherRider = AetherAPI.getInstance().get(rider);

				if (aetherRider.isJumping() && this.onGround)
				{
					this.jump();
					this.onGround = false;
					this.motionY = 1.0f;
				}
			}
		}

		super.travel(strafe, vertical, forward);
	}

	@Override
	public void fall(float distance, float damageMultiplier)
	{
		if (!this.isFriendly())
		{
			super.fall(distance, damageMultiplier);
		}
	}

	@Override
	protected void jump()
	{
		this.motionY = 0.41999998688697815D;
		this.isAirBorne = true;
	}

	public int getJumpTimer()
	{
		return this.jumpTimer;
	}

	public int getJumpDelay()
	{
		if (this.isFriendly())
		{
			return 2;
		}
		else
		{
			return this.rand.nextInt(20) + 10;
		}
	}

	@Override
	public int getVerticalFaceSpeed()
	{
		return 0;
	}

	@Override
	public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio)
	{
		if (!this.hasPrey())
		{
			super.knockBack(entityIn, strength, xRatio, zRatio);
		}
	}

	public boolean hasPrey()
	{
		return !this.getPassengers().isEmpty() && this.getPassengers().get(0) != null;
	}

	public boolean isPlayerFriendly(EntityPlayer player)
	{
		IPlayerAether iPlayerAether = AetherAPI.getInstance().get(player);
		return iPlayerAether.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.swet_cape));
	}

	public boolean isFriendly()
	{
		return this.hasPrey() && this.getPassengers().get(0) instanceof EntityPlayer && isPlayerFriendly((EntityPlayer) this.getPassengers().get(0));
	}

	public void dissolveSwet()
	{
		for (int i = 0; i < 50; i++)
		{
			float f = this.rand.nextFloat() * 3.141593F * 2.0F;
			float f1 = this.rand.nextFloat() * 0.5F + 0.25F;
			float f2 = MathHelper.sin(f) * f1;
			float f3 = MathHelper.cos(f) * f1;

			this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (double) f2, this.getEntityBoundingBox().minY + 1.25D, this.posZ + (double) f3, (double) f2 * 1.5D + this.motionX, 4D, (double) f3 * 1.5D + this.motionZ);
		}

		if (this.getDeathSound() != null) this.playSound(this.getDeathSound(), this.getSoundVolume(), this.getSoundPitch());

		this.setDead();
	}

	@Override
	public float getEyeHeight()
	{
		return 0.625F * this.height;
	}

	@Nullable
	protected ResourceLocation getLootTable()
	{
		if (this.getType() == EnumSwetType.Gold.getMeta())
		{
			return AetherLootTables.swet_gold;
		}
		else
		{
			return AetherLootTables.swet_blue;
		}
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable)
	{
		return null;
	}

	@Override
	protected float getSoundVolume()
	{
		return 0.6F;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundsAether.swet_squish;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundsAether.swet_death;
	}

	@Override
	public SoundCategory getSoundCategory()
	{
		return SoundCategory.HOSTILE;
	}

	public int getType()
	{
		return this.swetType;
	}

	private void setType(int type)
	{
		this.swetType = type;
	}

	@Override
	public boolean getCanSpawnHere()
	{
		return this.rand.nextInt(10) == 0 && super.getCanSpawnHere();
	}

	@Override
	public int getMaxSpawnedInChunk()
	{
		return 3;
	}

	@Override
	public boolean canDespawn()
	{
		return this.isFriendly();
	}

	@Override
	public void writeSpawnData(ByteBuf buffer)
	{
		buffer.writeInt(this.swetType);
	}

	@Override
	public void readSpawnData(ByteBuf buffer)
	{
		this.swetType = buffer.readInt();
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

		compound.setInteger("SwetType", this.getType());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		this.setType(compound.getInteger("SwetType"));
	}
}