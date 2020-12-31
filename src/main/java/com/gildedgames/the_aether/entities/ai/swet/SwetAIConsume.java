package com.gildedgames.the_aether.entities.ai.swet;

import com.gildedgames.the_aether.entities.passive.mountable.EntitySwet;
import com.gildedgames.the_aether.registry.sounds.SoundsAether;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public class SwetAIConsume extends EntityAIBase
{
    private final EntitySwet swet;
    private int jumps = 0;

    private float chosenDegrees;
    private int nextRandomizeTime;

    public SwetAIConsume(EntitySwet swetEntity)
    {
        this.swet = swetEntity;
        this.setMutexBits(2);
    }

    @Override
    public boolean shouldExecute()
    {
        return this.swet.hasPrey() && this.swet.getPassengers().get(0) instanceof EntityPlayer && !this.swet.isPlayerFriendly((EntityPlayer) this.swet.getPassengers().get(0));
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return this.swet.hasPrey() && this.swet.getPassengers().get(0) instanceof EntityPlayer && !this.swet.isPlayerFriendly((EntityPlayer) this.swet.getPassengers().get(0));
    }

    @Override
    public void updateTask()
    {
        if (this.jumps <= 3)
        {
            if (this.swet.onGround)
            {
                this.swet.playSound(SoundsAether.swet_jump, 1.0F, ((this.swet.getRNG().nextFloat() - this.swet.getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);

                this.chosenDegrees = (float)this.swet.getRNG().nextInt(360);

                if (this.jumps == 0)
                {
                    this.swet.motionY += 0.64999999403953552D;
                }
                else if (this.jumps == 1)
                {
                    this.swet.motionY += 0.74999998807907104D;
                }
                else if (this.jumps == 2)
                {
                    this.swet.motionY += 1.55D;
                }
                else
                {
                    this.swet.getPassengers().get(0).dismountRidingEntity();
                    this.swet.dissolveSwet();
                }

                if (!this.swet.midJump)
                {
                    this.jumps++;
                }
            }

            if (!this.swet.wasOnGround)
            {
                if (this.swet.getJumpTimer() < 6)
                {
                    if (this.jumps == 1)
                    {
                        this.moveXY(0.0F, 0.2F, this.chosenDegrees);
                    }
                    else if (this.jumps == 2)
                    {
                        this.moveXY(0.0F, 0.3F, this.chosenDegrees);
                    }
                    else if (this.jumps == 3)
                    {
                        this.moveXY(0.0F, 0.6F, this.chosenDegrees);
                    }
                }
            }
        }
    }

    public void moveXY(float strafe, float forward, float rotation)
    {
        float f = strafe * strafe + forward * forward;

        f = MathHelper.sqrt(f);
        if (f < 1.0F) f = 1.0F;
        strafe = strafe * f;
        forward = forward * f;
        float f1 = MathHelper.sin(rotation * 0.017453292F);
        float f2 = MathHelper.cos(rotation * 0.017453292F);

        this.swet.motionX += (strafe * f2 - forward * f1);
        this.swet.motionZ += (forward * f2 + strafe * f1);
    }
}
