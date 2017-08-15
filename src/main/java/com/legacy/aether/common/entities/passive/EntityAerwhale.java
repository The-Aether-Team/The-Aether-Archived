package com.legacy.aether.common.entities.passive;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import com.legacy.aether.common.entities.ai.EntityAIUpdateState;
import com.legacy.aether.common.entities.ai.aerwhale.AerwhaleAITravelCourse;
import com.legacy.aether.common.registry.sounds.SoundsAether;

public class EntityAerwhale extends EntityFlying implements IMob
{

    public EntityAerwhale(World world)
    {
        super(world);

        this.setSize(4F, 4F);
        this.isImmuneToFire = true;
        this.ignoreFrustumCheck = true;
        this.rotationYaw = 360F * this.getRNG().nextFloat();
        this.rotationPitch = 90F * this.getRNG().nextFloat() - 45F;
    }

    @Override
    protected void initEntityAI()
    {
    	this.tasks.addTask(0, new EntityAIUpdateState(this));
    	this.tasks.addTask(1, new AerwhaleAITravelCourse(this));
        this.tasks.addTask(6, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D); 
    }

    @Override
    public boolean getCanSpawnHere()
    {
    	BlockPos pos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY), MathHelper.floor(this.posZ));

    	return this.rand.nextInt(65) == 0 && this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).size() == 0 && !this.world.containsAnyLiquid(this.getEntityBoundingBox()) && this.world.getLight(pos) > 8 && super.getCanSpawnHere();
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }

    @Override
    public void onUpdate()
    {
    	super.onUpdate();

        this.extinguish();
    }

    @Override
    public SoundEvent getAmbientSound()
    {
        return SoundsAether.aerwhale_call;
    }

    @Override
    protected SoundEvent getHurtSound()
    {
        return SoundsAether.aerwhale_death;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundsAether.aerwhale_death;
    }

    @Override
    public boolean canDespawn()
    {
        return true;
    }

}