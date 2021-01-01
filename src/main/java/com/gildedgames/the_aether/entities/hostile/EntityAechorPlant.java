package com.gildedgames.the_aether.entities.hostile;

import com.gildedgames.the_aether.entities.passive.EntityAetherAnimal;
import com.gildedgames.the_aether.entities.projectile.EntityPoisonNeedle;
import com.gildedgames.the_aether.registry.AetherLootTables;
import com.gildedgames.the_aether.registry.sounds.SoundsAether;
import com.gildedgames.the_aether.world.AetherWorld;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.items.util.EnumSkyrootBucketType;

public class EntityAechorPlant extends EntityAetherAnimal implements IRangedAttackMob
{

	public float sinage;

	public int poisonRemaining, size;

	public EntityAechorPlant(World world)
	{
		super(world);

		this.size = this.rand.nextInt(4) + 1;
		this.sinage = this.rand.nextFloat() * 6F;
		this.poisonRemaining = this.rand.nextInt(4) + 2;

		this.setCanPickUpLoot(false);
		this.setPosition(this.posX, this.posY, this.posZ);
		this.setSize(0.75F + ((float) this.size * 0.125F), 0.5F + ((float) this.size * 0.075F));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
	}

	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntityAIAttackRanged(this, 0.0D, 30, 1.0F));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if (this.hurtTime > 0)
		{
			this.sinage += 0.9F;
		}
		else
		{
			if (this.getAttackTarget() != null)
			{
				this.sinage += 0.3F;
			}
			else
			{
				this.sinage += 0.1F;
			}
		}

		if (this.sinage > 3.141593F * 2F)
		{
			this.sinage -= (3.141593F * 2F);
		}
	}

	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
	{
		if (!AetherWorld.viableGrassBlocks.contains(state.getBlock()))
		{
			this.setDead();
		}
	}

	@Override
	public void knockBack(Entity entity, float strength, double xRatio, double zRatio)
	{
		if (this.getHealth() < 0.0F)
		{
			super.knockBack(entity, strength, xRatio, zRatio);
		}
	}

	@Override
	protected void collideWithEntity(Entity entityIn)
	{
		if (!entityIn.isRidingSameEntity(this))
		{
			if (!this.noClip && !entityIn.noClip)
			{
				double d0 = this.posX - entityIn.posX;
				double d1 = this.posZ - entityIn.posZ;
				double d2 = MathHelper.absMax(d0, d1);

				if (d2 >= 0.009999999776482582D)
				{
					d2 = (double) MathHelper.sqrt(d2);
					d0 = d0 / d2;
					d1 = d1 / d2;

					double d3 = 1.0D / d2;

					if (d3 > 1.0D)
					{
						d3 = 1.0D;
					}

					d0 = d0 * d3;
					d1 = d1 * d3;
					d0 = d0 * 0.05000000074505806D;
					d1 = d1 * 0.05000000074505806D;
					d0 = d0 * (double) (1.0F - entityIn.entityCollisionReduction);
					d1 = d1 * (double) (1.0F - entityIn.entityCollisionReduction);

					if (!entityIn.isBeingRidden())
					{
						entityIn.addVelocity(-d0, 0.0D, -d1);
					}
				}
			}
		}
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
	{
		if(this.world.getDifficulty().equals(EnumDifficulty.PEACEFUL)) 
		{
			return;
		}

		double x = target.posX - this.posX;
		double z = target.posZ - this.posZ;
		double y = 0.1D + (Math.sqrt((x * x) + (z * z) + 0.1D) * 0.5D) + ((this.posY - target.posY) * 0.25D);

		double distance = 1.5D / Math.sqrt((x * x) + (z * z) + 0.1D);

		x = x * distance;
		z = z * distance;

		EntityPoisonNeedle poisonNeedle = new EntityPoisonNeedle(this.world, this);

		poisonNeedle.shoot(this, this.rotationPitch, this.rotationYaw, 0.0F, 0.5F, 1.0F);
		poisonNeedle.posY = this.posY + 1D;

        this.playSound(SoundsAether.aechor_plant_attack, 1.0F, 1.2F / (this.getRNG().nextFloat() * 0.2F + 0.9F));
		this.world.spawnEntity(poisonNeedle);

		poisonNeedle.shoot(x, y, z, 0.285F + ((float)y * 0.05F), 1.0F);
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand)
	{
		ItemStack heldItem = player.getHeldItem(hand);

		if (!this.world.isRemote)
		{
			if (heldItem.getItem() == ItemsAether.skyroot_bucket && EnumSkyrootBucketType.getType(heldItem.getMetadata()) == EnumSkyrootBucketType.Empty && this.poisonRemaining > 0)
			{
				heldItem.shrink(1);

				if (heldItem.getCount() == 0)
				{
					player.setHeldItem(hand, new ItemStack(ItemsAether.skyroot_bucket, 1, EnumSkyrootBucketType.Poison.getMeta()));
				}
				else if (!player.inventory.addItemStackToInventory(new ItemStack(ItemsAether.skyroot_bucket, 1, EnumSkyrootBucketType.Poison.getMeta())))
				{
					player.dropItem(new ItemStack(ItemsAether.skyroot_bucket, 1, EnumSkyrootBucketType.Poison.getMeta()), false);
				}

				--this.poisonRemaining;
			}
		}

		return false;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

		compound.setInteger("Size", this.size);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		this.size = compound.getInteger("Size");
	}

	@Override
	public boolean canEntityBeSeen(Entity entity)
	{
		double distance = this.getDistance(entity);

		return distance <= 4.0F && super.canEntityBeSeen(entity);
	}

	@Override
	public boolean getCanSpawnHere()
	{
		IBlockState iblockstate = this.world.getBlockState((new BlockPos(this)).down());
		int i = MathHelper.floor(this.posX);
		int j = MathHelper.floor(this.getEntityBoundingBox().minY);
		int k = MathHelper.floor(this.posZ);
		BlockPos pos = new BlockPos(i, j, k);

		return this.rand.nextInt(25) == 0 && AetherWorld.viableGrassBlocks.contains(this.world.getBlockState(pos.down()).getBlock()) && this.world.getLight(pos) > 8
				&& iblockstate.canEntitySpawn(this);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entity)
	{
		return null;
	}

	@Override
	protected ResourceLocation getLootTable()
	{
		return AetherLootTables.aechor_plant;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_GENERIC_BIG_FALL;
	}

	@Override
	public void setSwingingArms(boolean swingingArms)
	{

	}

	@Override
	public boolean canBePushed()
	{
		return false;
	}

	@Override
	protected boolean canDespawn()
	{
		return true;
	}

}