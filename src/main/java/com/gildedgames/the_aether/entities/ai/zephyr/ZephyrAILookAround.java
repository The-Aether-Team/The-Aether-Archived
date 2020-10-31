package com.gildedgames.the_aether.entities.ai.zephyr;

import com.gildedgames.the_aether.entities.hostile.EntityZephyr;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;

public class ZephyrAILookAround extends EntityAIBase
{
    private final EntityZephyr zephyr;

    public ZephyrAILookAround(EntityZephyr zephyr)
    {
        this.zephyr = zephyr;
        this.setMutexBits(2);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return true;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        if (this.zephyr.getAttackTarget() == null)
        {
            this.zephyr.rotationYaw = -((float) MathHelper.atan2(this.zephyr.motionX, this.zephyr.motionZ)) * (180F / (float)Math.PI);
            this.zephyr.renderYawOffset = this.zephyr.rotationYaw;
        }
        else
        {
            EntityLivingBase entitylivingbase = this.zephyr.getAttackTarget();
            double d0 = 64.0D;

            if (entitylivingbase.getDistanceSq(this.zephyr) < 4096.0D)
            {
                double d1 = entitylivingbase.posX - this.zephyr.posX;
                double d2 = entitylivingbase.posZ - this.zephyr.posZ;
                this.zephyr.rotationYaw = -((float)MathHelper.atan2(d1, d2)) * (180F / (float)Math.PI);
                this.zephyr.renderYawOffset = this.zephyr.rotationYaw;
            }
        }
    }
}
