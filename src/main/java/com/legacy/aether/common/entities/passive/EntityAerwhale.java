package com.legacy.aether.common.entities.passive;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EnumCreatureType;
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
    public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch)
    {
        this.lastTickPosX = this.prevPosX = this.posX = x;
        this.lastTickPosY = this.prevPosY = this.posY = y + 30;
        this.lastTickPosZ = this.prevPosZ = this.posZ = z;
        this.rotationYaw = yaw;
        this.rotationPitch = pitch;

        this.setPosition(this.posX, this.posY, this.posZ);
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
        BlockPos pos = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY), MathHelper.floor_double(this.posZ));

        return this.worldObj.getLight(pos) > 8 && this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox().expand(0D, 30D, 0D)) && this.worldObj.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.containsAnyLiquid(this.getEntityBoundingBox());
    }

    @Override
    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount)
    {
    	return type == EnumCreatureType.AMBIENT;
    }

    @Override
    public void onUpdate()
    {
    	super.onUpdate();

        if (this.worldObj.getClosestPlayer(this.posX, this.posY, this.posZ, 255, false) == null)
        {
        	this.setDead();
        	return;
        }

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
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }

    @Override
    public boolean canDespawn()
    {
        return true;
    }

}