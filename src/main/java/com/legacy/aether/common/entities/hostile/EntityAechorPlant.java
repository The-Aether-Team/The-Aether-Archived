package com.legacy.aether.common.entities.hostile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import com.legacy.aether.common.blocks.BlocksAether;
import com.legacy.aether.common.entities.ai.aechorplant.AechorPlantAIShootPlayer;
import com.legacy.aether.common.entities.passive.EntityAetherAnimal;
import com.legacy.aether.common.items.ItemsAether;
import com.legacy.aether.common.items.util.EnumSkyrootBucketType;

public class EntityAechorPlant extends EntityAetherAnimal 
{

	public float sinage;

	public int poisonRemaining, size;

	public EntityAechorPlant(World world) 
	{
		super(world);

		this.size = this.rand.nextInt(4) + 1;
		this.sinage = this.rand.nextFloat() * 6F;
		this.poisonRemaining = this.rand.nextInt(4) + 2;

		this.setPosition(this.posX, this.posY, this.posZ);
		this.setSize(0.75F + ((float)this.size * 0.125F), 0.5F + ((float)this.size * 0.075F));
	}

	@Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.setHealth(20F);
    }

	@Override
    protected void initEntityAI()
    {
    	this.tasks.addTask(0, new AechorPlantAIShootPlayer(this));
    }

    @Override
    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount)
    {
    	return type == EnumCreatureType.CREATURE;
    }

	@Override
	public void onLivingUpdate() 
	{
		if(this.getHealth() <= 0)
		{
			super.onLivingUpdate();
			return;
		}
		else
		{
			this.despawnEntity();
		}
 
        if (this.isServerWorld())
        {
            this.world.theProfiler.startSection("newAi");
            this.updateEntityActionState();
            this.world.theProfiler.endSection();
        }

		if(this.hurtTime > 0) 
		{
			this.sinage += 0.9F;
		} 
		else
		{
			if(this.getAttackTarget() != null)
			{
				this.sinage += 0.3F;
			}
			else 
			{
				this.sinage += 0.1F;
			}
		}

		if(this.sinage > 3.141593F * 2F) 
		{
			this.sinage -= (3.141593F * 2F);
		}

		if(this.getAttackTarget() == null) 
		{
			EntityPlayer player = this.world.getNearestAttackablePlayer(this, 10, 2);

			this.setAttackTarget(player);
		}

		if(this.world.getBlockState(new BlockPos.MutableBlockPos().setPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY) - 1, MathHelper.floor(this.posZ))).getBlock() != BlocksAether.aether_grass)
		{
			this.setDead();
		}

	}

	@Override
	public void knockBack(Entity entity, float strength, double xRatio, double zRatio)
	{
		if(this.getHealth() >= 0) 
		{
			return;
		}

		super.knockBack(entity, strength, xRatio, zRatio);
	}

	@Override
    public boolean processInteract(EntityPlayer player, EnumHand hand)
	{
		ItemStack heldItem = player.getHeldItem(hand);

		if(heldItem != null && !this.world.isRemote)
		{
			if (heldItem.getItem() == ItemsAether.skyroot_bucket && EnumSkyrootBucketType.getType(heldItem.getMetadata()) == EnumSkyrootBucketType.Empty && this.poisonRemaining > 0)
			{
	            if (heldItem.getCount() - 1 == 0)
	            {
	                player.setHeldItem(hand, new ItemStack(ItemsAether.skyroot_bucket, 1, EnumSkyrootBucketType.Poison.meta));
	            }
	            else if (!player.inventory.addItemStackToInventory(new ItemStack(ItemsAether.skyroot_bucket, 1, EnumSkyrootBucketType.Poison.meta)))
	            {
	                player.dropItem(new ItemStack(ItemsAether.skyroot_bucket, 1, EnumSkyrootBucketType.Poison.meta), false);
	            }

	            --this.poisonRemaining;
			}
		}

		return false;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);

		nbttagcompound.setInteger("Size", this.size);
    }

	@Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);

		this.size = nbttagcompound.getInteger("Size");
    }

    @Override
    protected void dropFewItems(boolean var1, int var2) 
    {
    	this.dropItem(ItemsAether.aechor_petal, 2);
    }

	@Override
	public EntityAgeable createChild(EntityAgeable baby) 
	{
		return null;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
        return SoundEvents.ENTITY_GENERIC_BIG_FALL;
    }

	@Override
    protected boolean canDespawn()
    {
        return true;
    }

	@Override
    public int getMaxSpawnedInChunk()
    {
        return 2;
    }

	@Override
    public boolean getCanSpawnHere()
    {
    	return this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) == 10.0F && this.world.isDaytime();
    }

}