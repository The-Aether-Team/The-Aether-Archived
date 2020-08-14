package com.gildedgames.the_aether.entities.ai.zephyr;

import com.gildedgames.the_aether.entities.hostile.EntityZephyr;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class ZephyrAITravelCourse extends EntityAIBase {

    private EntityZephyr zephyr;

    public int courseCooldown;

    public double waypointX, waypointY, waypointZ;

    public ZephyrAITravelCourse(EntityZephyr zephyr) {
        this.zephyr = zephyr;
        this.setMutexBits(2);
    }

    @Override
    public boolean shouldExecute() {
        return !this.zephyr.isDead;
    }

    @Override
    public void updateTask() {
        double x = this.waypointX - this.zephyr.posX;
        double y = this.waypointY - this.zephyr.posY;
        double z = this.waypointZ - this.zephyr.posZ;

        double distance = MathHelper.sqrt_double(x * x + y * y + z * z);

        if (distance < 1.0D || distance > 60D) {
            this.waypointX = this.zephyr.posX + ((this.zephyr.getRNG().nextFloat() * 2.0F - 1.0F) * 16F);
            this.waypointY = this.zephyr.posY + ((this.zephyr.getRNG().nextFloat() * 2.0F - 1.0F) * 16F);
            this.waypointZ = this.zephyr.posZ + ((this.zephyr.getRNG().nextFloat() * 2.0F - 1.0F) * 16F);
        }

        if (this.courseCooldown-- <= 0) {
            this.courseCooldown += this.zephyr.getRNG().nextInt(5) + 2;

            if (isCourseTraversable(distance)) {
                this.zephyr.motionX += (x / distance) * 0.10000000000000001D;
                this.zephyr.motionY += (y / distance) * 0.10000000000000001D;
                this.zephyr.motionZ += (z / distance) * 0.10000000000000001D;
            } else {
                this.waypointX = this.zephyr.posX;
                this.waypointY = this.zephyr.posY;
                this.waypointZ = this.zephyr.posZ;
            }
        }

    }

    private boolean isCourseTraversable(double distance) {
        double x = (this.waypointX - this.zephyr.posX) / distance;
        double y = (this.waypointY - this.zephyr.posY) / distance;
        double z = (this.waypointZ - this.zephyr.posZ) / distance;

        AxisAlignedBB axisalignedbb = this.zephyr.boundingBox.copy();

        for (int i = 1; (double) i < distance; i++) {
            axisalignedbb.offset(x, y, z);

            if (this.zephyr.worldObj.getCollidingBoundingBoxes(this.zephyr, axisalignedbb).size() > 0) {
                return false;
            }
        }

        return true;
    }

}