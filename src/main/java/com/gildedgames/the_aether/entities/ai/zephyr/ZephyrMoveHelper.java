package com.gildedgames.the_aether.entities.ai.zephyr;

import com.gildedgames.the_aether.entities.hostile.EntityZephyr;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

public class ZephyrMoveHelper extends EntityMoveHelper
{
    private final EntityZephyr zephyr;
    private int courseChangeCooldown;

    public ZephyrMoveHelper(EntityZephyr zephyr)
    {
        super(zephyr);
        this.zephyr = zephyr;
    }

    public void onUpdateMoveHelper()
    {
        if (this.action == EntityMoveHelper.Action.MOVE_TO)
        {
            double d0 = this.posX - this.zephyr.posX;
            double d1 = this.posY - this.zephyr.posY;
            double d2 = this.posZ - this.zephyr.posZ;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;

            if (this.courseChangeCooldown-- <= 0)
            {
                this.courseChangeCooldown += this.zephyr.getRNG().nextInt(5) + 2;
                d3 = (double) MathHelper.sqrt(d3);

                if (this.isNotColliding(this.posX, this.posY, this.posZ, d3))
                {
                    this.zephyr.motionX += d0 / d3 * 0.1D;
                    this.zephyr.motionY += d1 / d3 * 0.1D;
                    this.zephyr.motionZ += d2 / d3 * 0.1D;
                }
                else
                {
                    this.action = EntityMoveHelper.Action.WAIT;
                }
            }
        }
    }

    /**
     * Checks if entity bounding box is not colliding with terrain
     */
    private boolean isNotColliding(double x, double y, double z, double distance)
    {
        double d0 = (x - this.zephyr.posX) / distance;
        double d1 = (y - this.zephyr.posY) / distance;
        double d2 = (z - this.zephyr.posZ) / distance;
        AxisAlignedBB axisalignedbb = this.zephyr.getEntityBoundingBox();

        for (int i = 1; (double)i < distance; ++i)
        {
            axisalignedbb = axisalignedbb.offset(d0, d1, d2);

            if (!this.zephyr.world.getCollisionBoxes(this.zephyr, axisalignedbb).isEmpty())
            {
                return false;
            }
        }

        return true;
    }
}
