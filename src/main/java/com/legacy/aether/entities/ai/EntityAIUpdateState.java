package com.legacy.aether.entities.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;

public class EntityAIUpdateState extends EntityAIBase
{

	private EntityLivingBase entity;

    private boolean isStuckWarning = false;

    private long checkTime = 0L;

    private BlockPos checkPos = BlockPos.ORIGIN;

	public EntityAIUpdateState(EntityLivingBase entity)
	{
		this.entity = entity;
        this.setMutexBits(1);
	}

	@Override
    public boolean isInterruptible()
    {
        return false;
    }

	@Override
    public void updateTask()
    {
        long curtime = System.currentTimeMillis();

        if (curtime > this.checkTime + 3000L)
        {
            double diffx = this.entity.posX - this.checkPos.getX();
            double diffy = this.entity.posY - this.checkPos.getY();
            double diffz = this.entity.posZ - this.checkPos.getZ();

            double distanceTravelled = Math.sqrt((diffx * diffx) + (diffy * diffy) + (diffz * diffz));

            if (distanceTravelled < 3D)
            {
                if (!this.isStuckWarning)
                {
                	this.isStuckWarning = true;
                }
                else
                {
                    this.entity.setDead();
                }
            }

            this.checkPos = this.entity.getPosition();
            this.checkTime = curtime;
        }
    }

	@Override
	public boolean shouldExecute()
	{
		return !this.entity.isDead;
	}

}