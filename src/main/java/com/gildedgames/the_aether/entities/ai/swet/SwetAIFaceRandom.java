package com.gildedgames.the_aether.entities.ai.swet;

import com.gildedgames.the_aether.entities.passive.mountable.EntitySwet;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.MobEffects;

public class SwetAIFaceRandom extends EntityAIBase
{
    private final EntitySwet swet;
    private float chosenDegrees;
    private int nextRandomizeTime;

    public SwetAIFaceRandom(EntitySwet swetEntity)
    {
        this.swet = swetEntity;
        this.setMutexBits(2);
    }

    @Override
    public boolean shouldExecute()
    {
        return this.swet.getAttackTarget() == null && (this.swet.onGround || this.swet.isPotionActive(MobEffects.LEVITATION));
    }

    @Override
    public void updateTask()
    {
        if (--this.nextRandomizeTime <= 0)
        {
            this.nextRandomizeTime = 40 + this.swet.getRNG().nextInt(60);
            this.chosenDegrees = (float)this.swet.getRNG().nextInt(360);
        }

        ((SwetMoveHelper)this.swet.getMoveHelper()).setDirection(this.chosenDegrees, false);
    }
}
