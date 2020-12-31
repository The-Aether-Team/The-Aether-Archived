package com.gildedgames.the_aether.entities.ai.swet;

import com.gildedgames.the_aether.entities.passive.mountable.EntitySwet;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class SwetAIHunt extends EntityAIBase
{
    private final EntitySwet swet;

    public SwetAIHunt(EntitySwet swetEntity)
    {
        this.swet = swetEntity;
        this.setMutexBits(2);
    }

    @Override
    public boolean shouldExecute()
    {
        EntityLivingBase entitylivingbase = this.swet.getAttackTarget();

        if (!(entitylivingbase instanceof EntityPlayer) || !entitylivingbase.isEntityAlive())
        {
            return false;
        }
        else if (((EntityPlayer)entitylivingbase).capabilities.disableDamage)
        {
            return false;
        }
        else
        {
            return !this.swet.isPlayerFriendly((EntityPlayer) entitylivingbase) && !this.swet.isFriendly() && !this.swet.hasPrey();
        }
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        EntityLivingBase entitylivingbase = this.swet.getAttackTarget();

        if (!(entitylivingbase instanceof EntityPlayer) || !entitylivingbase.isEntityAlive())
        {
            return false;
        }
        else if (((EntityPlayer)entitylivingbase).capabilities.disableDamage)
        {
            return false;
        }
        else
        {
            return !this.swet.isPlayerFriendly((EntityPlayer) entitylivingbase) && !this.swet.isFriendly() && !this.swet.hasPrey();
        }
    }

    @Override
    public void updateTask()
    {
        if (this.swet.getAttackTarget() != null)
        {
            this.swet.faceEntity(this.swet.getAttackTarget(), 10.0F, 10.0F);
            ((SwetMoveHelper)this.swet.getMoveHelper()).setDirection(this.swet.rotationYaw, true);
        }
    }
}
