package com.gildedgames.the_aether.entities.ai.zephyr;

import com.gildedgames.the_aether.entities.hostile.EntityZephyr;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;

import java.util.Random;

public class ZephyrAIRandomFly extends EntityAIBase
{
	private EntityZephyr zephyr;

    public ZephyrAIRandomFly(EntityZephyr zephyr)
    {
    	this.zephyr = zephyr;
		this.setMutexBits(1);
    }

	@Override
	public boolean shouldExecute() 
	{
        EntityMoveHelper entitymovehelper = this.zephyr.getMoveHelper();

        if (!entitymovehelper.isUpdating())
        {
            return true;
        }
        else
        {
            double d0 = entitymovehelper.getX() - this.zephyr.posX;
            double d1 = entitymovehelper.getY() - this.zephyr.posY;
            double d2 = entitymovehelper.getZ() - this.zephyr.posZ;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            return d3 < 1.0D || d3 > 3600.0D;
        }
	}

	@Override
    public boolean shouldContinueExecuting()
    {
        return false;
    }

    @Override
    public void startExecuting()
    {
        Random random = this.zephyr.getRNG();
        double d0 = this.zephyr.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double d1 = this.zephyr.posY + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double d2 = this.zephyr.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        this.zephyr.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
    }
}