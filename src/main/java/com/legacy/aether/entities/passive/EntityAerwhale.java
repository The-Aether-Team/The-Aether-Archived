package com.legacy.aether.entities.passive;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import com.legacy.aether.entities.ai.EntityAIUpdateState;
import com.legacy.aether.registry.sounds.SoundsAether;

public class EntityAerwhale extends EntityFlying implements IMob
{

	private double motionYaw;

    private double motionPitch;

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
        this.extinguish();
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.getEntityBoundingBox().minY);
        int k = MathHelper.floor(this.posZ);
        BlockPos position = new BlockPos(i, j, k);

        double[] distances = new double[5];

        distances[0] = this.checkForTravelableCourse(0F, 0F);
        distances[1] = this.checkForTravelableCourse(45F, 0F);
        distances[2] = this.checkForTravelableCourse(0F, 45F);
        distances[3] = this.checkForTravelableCourse(-45F, 0F);
        distances[4] = this.checkForTravelableCourse(0, -45F);

        int course = this.getCorrectCourse(distances);

        if (course == 0)
        {
            if(distances[0] == 50D)
            {
                this.motionYaw *= 0.9F;
                this.motionPitch *= 0.9F;

                if(this.posY > 100)
                {
                	this.motionPitch -= 2F;
                }
                if(this.posY < 20)
                {
                	this.motionPitch += 2F;
                }
            }
            else
            {
            	this.rotationPitch = -this.rotationPitch;
            	this.rotationYaw = -this.rotationYaw;
            }
        }
        else if (course == 1)
        {
        	this.motionYaw += 5F;
        }
        else if (course == 2)
        {
        	this.motionPitch -= 5F;
        }
        else if (course == 3)
        {
        	this.motionYaw -= 5F;
        }
        else
        {
            this.motionPitch += 5F;
        }

        this.motionYaw += 2F * this.getRNG().nextFloat() - 1F;
        this.motionPitch += 2F * this.getRNG().nextFloat() - 1F;	

        this.rotationPitch += 0.1D * this.motionPitch;
        this.rotationYaw += 0.1D * this.motionYaw;

        if(this.rotationPitch < -60F)
        {
        	this.rotationPitch = -60F;
        }

        if(this.rotationPitch > 60F)
        {
        	this.rotationPitch = 60F;
        }

        this.rotationPitch *= 0.99D;

        this.motionX += 0.005D * Math.cos((this.rotationYaw / 180D) * 3.1415926535897931D ) * Math.cos((this.rotationPitch / 180D) * 3.1415926535897931D);
        this.motionY += 0.005D * Math.sin((this.rotationPitch / 180D) * 3.1415926535897931D );
        this.motionZ += 0.005D * Math.sin((this.rotationYaw / 180D) * 3.1415926535897931D ) * Math.cos((this.rotationPitch / 180D) * 3.1415926535897931D);

        this.motionX *= 0.98D;
        this.motionY *= 0.98D;
        this.motionZ *= 0.98D;

        if(this.motionX > 0D && !this.world.isAirBlock(position.east())) 
        {
        	this.motionX = -this.motionX;
        	this.motionYaw -= 10F;
        }
        else if(this.motionX < 0D && !this.world.isAirBlock(position.west()))
        {
        	this.motionX = -this.motionX;
        	this.motionYaw += 10F;
        }

        if(this.motionY > 0D && !this.world.isAirBlock(position.up()))
        {
            this.motionY = -this.motionY;
            this.motionPitch -= 20F;
        }
        else if(this.motionY < 0D && !this.world.isAirBlock(position.down())) 
        {
        	this.motionY = -this.motionY;
        	this.motionPitch += 20F;
        }

        if(this.motionZ > 0D && !this.world.isAirBlock(position.south())) 
        {
        	this.motionZ = -this.motionZ;
        	this.motionYaw -= 10F;
        }
        else if(this.motionZ < 0D && !this.world.isAirBlock(position.north()))
        {
        	this.motionZ = -this.motionZ;
        	this.motionYaw += 10F;
        }

        if (this.posY < -64.0D)
        {
        	this.setDead();
        }

        this.world.profiler.startSection("ai");

        if (this.isMovementBlocked())
        {
            this.isJumping = false;
            this.moveStrafing = 0.0F;
            this.moveForward = 0.0F;
            this.randomYawVelocity = 0.0F;
        }

        this.world.profiler.endSection();

        this.world.profiler.startSection("push");
        this.collideWithNearbyEntities();
        this.world.profiler.endSection();

        this.world.profiler.startSection("checkDespawn");
        this.despawnEntity();
        this.world.profiler.endSection();
        this.world.profiler.startSection("goalSelector");
        this.tasks.onUpdateTasks();
        this.world.profiler.endSection();

        super.onEntityUpdate();

        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
    }

	private int getCorrectCourse(double[] distances)
	{
        int correctCourse = 0;

        for(int i = 1; i < 5; i++)
        {
            if(distances[i] > distances[correctCourse])
            {
            	correctCourse = i;
            }
        }

        return correctCourse;
	}

    private double checkForTravelableCourse(float rotationYawOffset, float rotationPitchOffset)
    {
    	double standard = 50D;
 
        float yaw = this.rotationYaw + rotationYawOffset;
        float pitch = this.rotationYaw + rotationYawOffset;

        float f3 = MathHelper.cos(-yaw * 0.01745329F - (float)Math.PI);
        float f4 = MathHelper.sin(-yaw * 0.01745329F - (float)Math.PI);
        float f5 = MathHelper.cos(-pitch * 0.01745329F);
        float f6 = MathHelper.sin(-pitch * 0.01745329F);

        float f7 = f4 * f5;
        float f8 = f6;
        float f9 = f3 * f5;

        Vec3d vec3d = new Vec3d(this.posX, this.getEntityBoundingBox().minY, this.posZ);
        Vec3d vec3d1 = vec3d.addVector((double)f7 * standard, (double)f8 * standard, (double)f9 * standard);

        RayTraceResult movingobjectposition = this.world.rayTraceBlocks(vec3d, vec3d1, true);

        if(movingobjectposition == null)
        {
            return standard;
        }

        if(movingobjectposition.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            double i = movingobjectposition.getBlockPos().getX() - this.posX;
            double j = movingobjectposition.getBlockPos().getY() - this.getEntityBoundingBox().minY;
            double k = movingobjectposition.getBlockPos().getZ() - this.posZ;
            return Math.sqrt(i * i + j * j + k * k);
        }

        return standard;
    }

    @Override
    public SoundEvent getAmbientSound()
    {
        return SoundsAether.aerwhale_call;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
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