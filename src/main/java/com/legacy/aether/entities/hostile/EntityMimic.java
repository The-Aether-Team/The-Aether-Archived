package com.legacy.aether.entities.hostile;

import javax.annotation.Nullable;

import com.legacy.aether.registry.AetherLootTables;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityMimic extends EntityMob
{

    public EntityMimic(World world)
    {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
    }
   
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);   
    }

    public void onUpdate()
    {
		super.onUpdate();
    }

    protected SoundEvent getHurtSound(DamageSource source)
    {
        return SoundEvents.BLOCK_WOOD_BREAK;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.BLOCK_CHEST_CLOSE;
    }

    protected float getSoundVolume()
    {
        return 1.0F;
    }

	@Override
	public boolean attackEntityFrom(DamageSource ds, float var2)
	{
		if (ds.getImmediateSource() instanceof EntityMimic)
		{
			return false;
		}

		if (ds.getImmediateSource() instanceof EntityLivingBase && this.hurtTime == 0)
		{
			if (this.world instanceof WorldServer)
	        {
	            ((WorldServer)this.world).spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX, this.posY + (double)this.height / 1.5D, this.posZ, 20, (double)(this.width / 4.0F), (double)(this.height / 4.0F), (double)(this.width / 4.0F), 0.05D, Block.getStateId(Blocks.PLANKS.getDefaultState()));
	        }
			
			EntityLivingBase attacker = (EntityLivingBase) ds.getImmediateSource();
			if (attacker instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) attacker;
				if(!player.capabilities.isCreativeMode)
				{
					this.setAttackTarget(attacker);
				}
			}
			else
			{
				this.setAttackTarget(attacker);
			}
		}
		return super.attackEntityFrom(ds, var2);
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entityIn)
	{
		super.attackEntityAsMob(entityIn);
		
		EntityLivingBase entityLiving = (EntityLivingBase) entityIn;
		if (entityIn instanceof EntityLivingBase)
		{
		
			if (entityLiving.getHealth() <= 0)
			{
				this.playSound(SoundEvents.ENTITY_PLAYER_BURP, 1.0F, this.getSoundPitch());
			}
			else
			{
				this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1.0F, this.getSoundPitch());
			}
		}
		
		return true;
	}
	
	@Nullable
    protected ResourceLocation getLootTable()
    {
        return AetherLootTables.chest_mimic;
    }

}