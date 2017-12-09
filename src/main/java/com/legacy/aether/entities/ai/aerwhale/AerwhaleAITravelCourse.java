package com.legacy.aether.entities.ai.aerwhale;

import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import com.legacy.aether.entities.passive.EntityAerwhale;

public class AerwhaleAITravelCourse extends EntityAIBase
{

	private EntityAerwhale aerwhale;

	private World worldObj;

	private double motionYaw;

    private double motionPitch;

	private double origin_direction, westward_direction, eastward_direction, upward_direction, downward_direction;

	public AerwhaleAITravelCourse(EntityAerwhale aerwhale)
	{
		this.aerwhale = aerwhale;
		this.worldObj = aerwhale.world;
        this.setMutexBits(4);
	}

	@Override
	public boolean shouldExecute() 
	{
		return !this.aerwhale.isDead;
	}

	@Override
    public void updateTask()
    {
        this.origin_direction = this.checkForTravelableCourse(0F, 0F);
        this.westward_direction = this.checkForTravelableCourse(45F, 0F);
        this.upward_direction = this.checkForTravelableCourse(0F, 45F);
        this.eastward_direction = this.checkForTravelableCourse(-45F, 0F);
        this.downward_direction = this.checkForTravelableCourse(0, -45F);

        int course = this.getCorrectCourse();

        if (course == 0)
        {
            if(this.origin_direction == 50D)
            {
                this.motionYaw *= 0.9F;
                this.motionPitch *= 0.9F;

                if(this.aerwhale.posY > 100)
                {
                	this.motionPitch -= 2F;
                }
                if(this.aerwhale.posY < 20)
                {
                	this.motionPitch += 2F;
                }
            }
            else
            {
            	this.aerwhale.rotationPitch = -this.aerwhale.rotationPitch;
            	this.aerwhale.rotationYaw = -this.aerwhale.rotationYaw;
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

        this.motionYaw += 2F * this.aerwhale.getRNG().nextFloat() - 1F;
        this.motionPitch += 2F * this.aerwhale.getRNG().nextFloat() - 1F;	

        this.aerwhale.rotationPitch += 0.1D * this.motionPitch;
        this.aerwhale.rotationYaw += 0.1D * this.motionYaw;

        if(this.aerwhale.rotationPitch < -60F)
        {
        	this.aerwhale.rotationPitch = -60F;
        }

        if(this.aerwhale.rotationPitch > 60F)
        {
        	this.aerwhale.rotationPitch = 60F;
        }

        this.aerwhale.rotationPitch *= 0.99D;

        this.aerwhale.motionX += 0.005D * Math.cos((this.aerwhale.rotationYaw / 180D) * 3.1415926535897931D) * Math.cos((this.aerwhale.rotationPitch / 180D) * 3.1415926535897931D);
        this.aerwhale.motionY += 0.005D * Math.sin((this.aerwhale.rotationPitch / 180D) * 3.1415926535897931D);
        this.aerwhale.motionZ += 0.005D * Math.sin((this.aerwhale.rotationYaw / 180D) * 3.1415926535897931D) * Math.cos((this.aerwhale.rotationPitch / 180D) * 3.1415926535897931D);

        this.aerwhale.motionX *= 0.98D;
        this.aerwhale.motionY *= 0.98D;
        this.aerwhale.motionZ *= 0.98D;

        if(this.aerwhale.motionX > 0D && worldObj.getBlockState(this.aerwhale.getPosition().east()).getBlock() != Blocks.AIR) 
        {
        	this.aerwhale.motionX = -this.aerwhale.motionX;
        	this.motionYaw -= 10F;
        }
        else if(this.aerwhale.motionX < 0D && worldObj.getBlockState(this.aerwhale.getPosition().west()).getBlock() != Blocks.AIR)
        {
        	this.aerwhale.motionX = -this.aerwhale.motionX;
        	this.motionYaw += 10F;
        }

        else if(this.aerwhale.motionY > 0D && this.worldObj.getBlockState(this.aerwhale.getPosition().up()).getBlock() != Blocks.AIR)
        {
            this.aerwhale.motionY = -this.aerwhale.motionY;
            this.motionPitch -= 10F;
        }
        else if(this.aerwhale.motionY < 0D && this.worldObj.getBlockState(this.aerwhale.getPosition().down()).getBlock() != Blocks.AIR) 
        {
        	this.aerwhale.motionY = -this.aerwhale.motionY;
        	this.motionPitch += 10F;
        }

        if(this.aerwhale.motionZ > 0D && worldObj.getBlockState(this.aerwhale.getPosition().south()).getBlock() != Blocks.AIR) 
        {
        	this.aerwhale.motionZ = -this.aerwhale.motionZ;
        	this.motionYaw -= 10F;
        }
        else if(this.aerwhale.motionZ < 0D && worldObj.getBlockState(this.aerwhale.getPosition().north()).getBlock() != Blocks.AIR)
        {
        	this.aerwhale.motionZ = -this.aerwhale.motionZ;
        	this.motionYaw += 10F;
        }

        this.aerwhale.move(MoverType.SELF, this.aerwhale.motionX, this.aerwhale.motionY, this.aerwhale.motionZ);
    }

	private int getCorrectCourse()
	{
        double[] distances = new double [] {this.origin_direction, this.westward_direction, this.upward_direction, this.eastward_direction, this.downward_direction};

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
 
        float yaw = this.aerwhale.rotationYaw + rotationYawOffset;
        float pitch = this.aerwhale.rotationYaw + rotationYawOffset;

        float f3 = MathHelper.cos(-yaw * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-yaw * 0.01745329F - 3.141593F);
        float f5 = MathHelper.cos(-pitch * 0.01745329F);

        float f7 = f4 * f5;
        float f8 = MathHelper.sin(-pitch * 0.01745329F);
        float f9 = f3 * f5;

        Vec3d vec3d = new Vec3d(this.aerwhale.getPosition());
        Vec3d vec3d1 = vec3d.addVector((double)f7 * standard, (double)f8 * standard, (double)f9 * standard);

        RayTraceResult movingobjectposition = this.worldObj.rayTraceBlocks(vec3d, vec3d1, false);

        if(movingobjectposition == null)
        {
            return standard;
        }

        if(movingobjectposition.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            double i = movingobjectposition.getBlockPos().getX() - this.aerwhale.posX;
            double j = movingobjectposition.getBlockPos().getY() - this.aerwhale.posY;
            double k = movingobjectposition.getBlockPos().getZ() - this.aerwhale.posZ;
            return Math.sqrt(i * i + j * j + k * k);
        }

        return standard;
    }

}