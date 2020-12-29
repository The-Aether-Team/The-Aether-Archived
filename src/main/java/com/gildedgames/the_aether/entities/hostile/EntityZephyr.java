package com.gildedgames.the_aether.entities.hostile;

import javax.annotation.Nullable;

import com.gildedgames.the_aether.registry.AetherLootTables;
import com.gildedgames.the_aether.registry.sounds.SoundsAether;
import com.gildedgames.the_aether.entities.ai.zephyr.ZephyrAILookAround;
import com.gildedgames.the_aether.entities.ai.zephyr.ZephyrAIShootTarget;
import com.gildedgames.the_aether.entities.ai.zephyr.ZephyrAIRandomFly;
import com.gildedgames.the_aether.entities.ai.zephyr.ZephyrMoveHelper;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityZephyr extends EntityFlying implements IMob
{

    public ZephyrAIShootTarget shootingAI;

    public EntityZephyr(World world)
    {
        super(world);

        this.setSize(4F, 4F);

        this.moveHelper = new ZephyrMoveHelper(this);

        this.tasks.addTask(7, this.shootingAI = new ZephyrAIShootTarget(this));
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(5, new ZephyrAIRandomFly(this));
        this.tasks.addTask(7, new ZephyrAILookAround(this));
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
    }
    
    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5D);
		this.setHealth(5);
    }

    @Override
    public boolean getCanSpawnHere()
    {
        BlockPos pos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY), MathHelper.floor(this.posZ));

        return this.rand.nextInt(25) == 0 && this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).size() == 0 && !this.world.containsAnyLiquid(this.getEntityBoundingBox()) && this.world.getLight(pos) > 8 && super.getCanSpawnHere();
     }

	@Override
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }

    @Override
    public void onEntityUpdate()
    {
    	super.onEntityUpdate();

    	if (this.world.isRemote)
    	{
    		this.shootingAI.updateTask();
    	}

		if(this.posY < -2D || this.posY > 255D)
		{
			this.despawnEntity();
		}

        if(this.getAttackTarget() != null && this.getAttackTarget().isDead)
        {
        	this.setAttackTarget(null);
        }

        if(!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
        {
        	this.setDead();
        }
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
    	return SoundsAether.zephyr_call;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return SoundsAether.zephyr_call;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
    	return SoundsAether.zephyr_call;
    }

	@Override
	protected void dropFewItems(boolean var1, int var2) 
	{
		//this.dropItem(Item.getItemFromBlock(BlocksAether.aercloud), 1);
	}

    @Override
    public boolean canDespawn()
    {
    	return true;
    }

    @Override
    protected float getSoundVolume()
    {
        return 3F;
    }
    
    @Nullable
    protected ResourceLocation getLootTable()
    {
        return AetherLootTables.zephyr;
    }

}