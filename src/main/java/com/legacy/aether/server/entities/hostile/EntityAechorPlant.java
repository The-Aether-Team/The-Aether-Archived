package com.legacy.aether.server.entities.hostile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
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

import com.legacy.aether.server.blocks.BlocksAether;
import com.legacy.aether.server.entities.ai.aechorplant.AechorPlantAIShootPlayer;
import com.legacy.aether.server.entities.passive.EntityAetherAnimal;
import com.legacy.aether.server.items.ItemsAether;

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
            this.worldObj.theProfiler.startSection("newAi");
            this.updateEntityActionState();
            this.worldObj.theProfiler.endSection();
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
			EntityPlayer player = this.worldObj.getNearestAttackablePlayer(this, 10, 2);

			this.setAttackTarget(player);
		}

		if(this.worldObj.getBlockState(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(getEntityBoundingBox().minY) - 1, MathHelper.floor_double(this.posZ))).getBlock() != BlocksAether.aether_grass)
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
    public boolean processInteract(EntityPlayer player, EnumHand hand, ItemStack itemStack)
	{
		ItemStack stack = itemStack;

		if(stack != null && !this.worldObj.isRemote)
		{
			if(stack.getItem() == ItemsAether.skyroot_bucket && stack.getItemDamage() == 0 && this.poisonRemaining > 0) 
			{
				player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(ItemsAether.skyroot_bucket, 1, 3));
				this.poisonRemaining--;

				return true;
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
    	return this.getBlockPathWeight(new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ)) == 10.0F;
    }

}