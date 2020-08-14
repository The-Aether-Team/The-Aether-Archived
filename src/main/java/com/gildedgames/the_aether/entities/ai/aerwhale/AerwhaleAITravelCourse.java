package com.gildedgames.the_aether.entities.ai.aerwhale;

import com.gildedgames.the_aether.entities.passive.EntityAerwhale;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

public class AerwhaleAITravelCourse extends EntityAIBase {

    private EntityAerwhale aerwhale;

    private World worldObj;

    private double motionYaw;

    private double motionPitch;

    private double origin_direction, westward_direction, eastward_direction, upward_direction, downward_direction;

    public AerwhaleAITravelCourse(EntityAerwhale aerwhale) {
        this.aerwhale = aerwhale;
        this.worldObj = aerwhale.worldObj;
        this.setMutexBits(2);
    }

    @Override
    public boolean shouldExecute() {
        return !this.aerwhale.isDead;
    }

    @Override
    public void updateTask() {
        this.origin_direction = this.checkForTravelableCourse(0F, 0F);
        this.westward_direction = this.checkForTravelableCourse(45F, 0F);
        this.upward_direction = this.checkForTravelableCourse(0F, 45F);
        this.eastward_direction = this.checkForTravelableCourse(-45F, 0F);
        this.downward_direction = this.checkForTravelableCourse(0, -45F);

        int course = this.getCorrectCourse();

        if (course == 0) {
            if (this.origin_direction == 50D) {
                this.motionYaw *= 0.9F;
                this.motionPitch *= 0.9F;

                if (this.aerwhale.posY > 100) {
                    this.motionPitch -= 2F;
                }
                if (this.aerwhale.posY < 20) {
                    this.motionPitch += 2F;
                }
            } else {
                this.aerwhale.rotationPitch = -this.aerwhale.rotationPitch;
                this.aerwhale.rotationYaw = -this.aerwhale.rotationYaw;
            }
        } else if (course == 1) {
            this.motionYaw += 5F;
        } else if (course == 2) {
            this.motionPitch -= 5F;
        } else if (course == 3) {
            this.motionYaw -= 5F;
        } else {
            this.motionPitch += 5F;
        }

        this.motionYaw += 2F * this.aerwhale.getRNG().nextFloat() - 1F;
        this.motionPitch += 2F * this.aerwhale.getRNG().nextFloat() - 1F;

        this.aerwhale.rotationPitch += 0.1D * this.motionPitch;
        this.aerwhale.rotationYaw += 0.1D * this.motionYaw;

        this.aerwhale.aerwhaleRotationPitch += 0.1D * this.motionPitch;
        this.aerwhale.aerwhaleRotationYaw += 0.1D * this.motionYaw;


        if (this.aerwhale.rotationPitch < -60F) {
            this.aerwhale.rotationPitch = -60F;
        }

        if (this.aerwhale.aerwhaleRotationPitch < -60D)
        {
            this.aerwhale.aerwhaleRotationPitch = -60D;
        }

        if (this.aerwhale.rotationPitch > 60F) {
            this.aerwhale.rotationPitch = 60F;
        }

        if (this.aerwhale.aerwhaleRotationPitch < -60D)
        {
            this.aerwhale.aerwhaleRotationPitch = -60D;
        }

        this.aerwhale.rotationPitch *= 0.99D;
        this.aerwhale.aerwhaleRotationPitch *= 0.99D;

        this.aerwhale.motionX += 0.005D * Math.cos((this.aerwhale.rotationYaw / 180D) * 3.1415926535897931D) * Math.cos((this.aerwhale.rotationPitch / 180D) * 3.1415926535897931D);
        this.aerwhale.motionY += 0.005D * Math.sin((this.aerwhale.rotationPitch / 180D) * 3.1415926535897931D);
        this.aerwhale.motionZ += 0.005D * Math.sin((this.aerwhale.rotationYaw / 180D) * 3.1415926535897931D) * Math.cos((this.aerwhale.rotationPitch / 180D) * 3.1415926535897931D);

        this.aerwhale.motionX *= 0.98D;
        this.aerwhale.motionY *= 0.98D;
        this.aerwhale.motionZ *= 0.98D;

        int x = MathHelper.floor_double(this.aerwhale.posX);
        int y = MathHelper.floor_double(this.aerwhale.boundingBox.minY);
        int z = MathHelper.floor_double(this.aerwhale.posZ);

        if (this.aerwhale.motionX > 0D && this.worldObj.getBlock(x + 1, y, z) != Blocks.air) {
            this.aerwhale.motionX = -this.aerwhale.motionX;
            this.motionYaw -= 10F;
        } else if (this.aerwhale.motionX < 0D && worldObj.getBlock(x - 1, y, z) != Blocks.air) {
            this.aerwhale.motionX = -this.aerwhale.motionX;
            this.motionYaw += 10F;
        }

        if (this.aerwhale.motionY > 0D && this.worldObj.getBlock(x, y + 1, z) != Blocks.air) {
            this.aerwhale.motionY = -this.aerwhale.motionY;
            this.motionPitch -= 10F;
        } else if (this.aerwhale.motionY < 0D && this.worldObj.getBlock(x, y - 1, z) != Blocks.air) {
            this.aerwhale.motionY = -this.aerwhale.motionY;
            this.motionPitch += 10F;
        }

        if (this.aerwhale.motionZ > 0D && worldObj.getBlock(x, y, z + 1) != Blocks.air) {
            this.aerwhale.motionZ = -this.aerwhale.motionZ;
            this.motionYaw -= 10F;
        } else if (this.aerwhale.motionZ < 0D && worldObj.getBlock(x, y, z - 1) != Blocks.air) {
            this.aerwhale.motionZ = -this.aerwhale.motionZ;
            this.motionYaw += 10F;
        }
    }

    private int getCorrectCourse() {
        double[] distances = new double[]{this.origin_direction, this.westward_direction, this.upward_direction, this.eastward_direction, this.downward_direction};

        int correctCourse = 0;

        for (int i = 1; i < 5; i++) {
            if (distances[i] > distances[correctCourse]) {
                correctCourse = i;
            }
        }

        return correctCourse;
    }

    private double checkForTravelableCourse(float rotationYawOffset, float rotationPitchOffset) {
        double standard = 50D;

        float yaw = this.aerwhale.rotationYaw + rotationYawOffset;
        float pitch = this.aerwhale.rotationPitch + rotationPitchOffset;

        float f3 = MathHelper.cos(-yaw * 0.01745329F - 3.141593F);
        float f4 = MathHelper.sin(-yaw * 0.01745329F - 3.141593F);
        float f5 = MathHelper.cos(-pitch * 0.01745329F);
        float f6 = MathHelper.sin(-pitch * 0.01745329F);

        float f7 = f4 * f5;
        float f8 = f6;
        float f9 = f3 * f5;

        Vec3 vec3d = Vec3.createVectorHelper(this.aerwhale.posX, this.aerwhale.boundingBox.minY, this.aerwhale.posZ);
        Vec3 vec3d1 = vec3d.addVector((double) f7 * standard, (double) f8 * standard, (double) f9 * standard);

        MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3d, vec3d1, true);

        if (movingobjectposition == null) {
            return standard;
        }

        if (movingobjectposition.typeOfHit == MovingObjectType.BLOCK) {
            double i = movingobjectposition.blockX - this.aerwhale.posX;
            double j = movingobjectposition.blockY - this.aerwhale.boundingBox.minY;
            double k = movingobjectposition.blockZ - this.aerwhale.posZ;
            return Math.sqrt(i * i + j * j + k * k);
        }

        return standard;
    }

}