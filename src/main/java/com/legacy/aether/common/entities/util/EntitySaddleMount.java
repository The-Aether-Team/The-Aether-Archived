package com.legacy.aether.common.entities.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public abstract class EntitySaddleMount extends EntityMountable
{

	public static final DataParameter<Boolean> SADDLED = EntityDataManager.<Boolean>createKey(EntityMountable.class, DataSerializers.BOOLEAN);

	public EntitySaddleMount(World world)
	{
		super(world);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, float i)
	{
		if ((damagesource.getEntity() instanceof EntityPlayer) && (!this.getPassengers().isEmpty()  && this.getPassengers().get(0) == damagesource.getEntity()))
		{
			return false;
		}

		return super.attackEntityFrom(damagesource, i);
	}

	@Override
	public boolean canBeSteered()
	{
		return true;
	}

	@Override
	protected boolean canTriggerWalking()
	{
		return this.onGround;
	}

	@Override
	protected void dropFewItems(boolean var1, int var2)
	{
		super.dropFewItems(var1, var2);
		
		this.dropSaddle();
	}

	protected void dropSaddle()
	{
		if (this.getSaddled())
		{
			this.dropItem(Items.SADDLE, 1);
		}
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		
		this.dataManager.register(SADDLED, false);
	}

	public boolean getSaddled()
	{
		return this.dataManager.get(SADDLED);
	}
	
	public boolean canSaddle()
	{
		return true;
	}

	@Override
    public boolean processInteract(EntityPlayer entityplayer, EnumHand hand)
	{
		ItemStack heldItem = entityplayer.getHeldItem(hand);

		if (!this.canSaddle())
		{
			return super.processInteract(entityplayer, hand);
		}
		
		if (!this.getSaddled())
		{
			if (heldItem.getItem() == Items.SADDLE && !this.isChild())
			{
				if (!entityplayer.capabilities.isCreativeMode)
				{
					entityplayer.setHeldItem(hand, ItemStack.EMPTY);
				}
				
				if (entityplayer.world.isRemote)
				{
					entityplayer.world.playSound(entityplayer, entityplayer.getPosition(), SoundEvents.ENTITY_PIG_SADDLE, SoundCategory.AMBIENT, 1.0F, 1.0F);
				}

				this.setSaddled(true);

				return true;
			}
		}
		else
		{
			if (this.getPassengers().isEmpty())
			{
				if (!entityplayer.world.isRemote)
				{
					entityplayer.startRiding(this);
					entityplayer.prevRotationYaw = entityplayer.rotationYaw = this.rotationYaw;
				}

				return true;
			}
		}
		
		return super.processInteract(entityplayer, hand);
	}

	@Override
	public boolean isEntityInsideOpaqueBlock()
	{
		if (!this.getPassengers().isEmpty())
		{
			return false;
		}

		return super.isEntityInsideOpaqueBlock();
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);

		this.setSaddled(nbttagcompound.getBoolean("getSaddled"));
	}

	public void setSaddled(boolean saddled)
	{
		this.dataManager.set(SADDLED, saddled);
	}
	
	@Override
	public boolean shouldRiderFaceForward(EntityPlayer player)
	{
		return false;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);

		nbttagcompound.setBoolean("getSaddled", this.getSaddled());
	}

}