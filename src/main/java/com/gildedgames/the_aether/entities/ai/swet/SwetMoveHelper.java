package com.gildedgames.the_aether.entities.ai.swet;

import com.gildedgames.the_aether.entities.passive.mountable.EntitySwet;
import com.gildedgames.the_aether.registry.sounds.SoundsAether;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;

public class SwetMoveHelper extends EntityMoveHelper
{
    private float yRot;
    private int jumpDelay;
    private final EntitySwet swet;
    private boolean isAggressive;

    public SwetMoveHelper(EntitySwet swetEntity)
    {
        super(swetEntity);
        this.swet = swetEntity;
        this.yRot = 180.0F * swetEntity.rotationYaw / (float)Math.PI;
    }

    public void setDirection(float p_179920_1_, boolean p_179920_2_)
    {
        this.yRot = p_179920_1_;
        this.isAggressive = p_179920_2_;
    }

    public void setSpeed(double speedIn)
    {
        this.speed = speedIn;
        this.action = EntityMoveHelper.Action.MOVE_TO;
    }

    @Override
    public void onUpdateMoveHelper()
    {
        this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, this.yRot, 90.0F);
        this.entity.rotationYawHead = this.entity.rotationYaw;
        this.entity.renderYawOffset = this.entity.rotationYaw;

        if (this.action != EntityMoveHelper.Action.MOVE_TO)
        {
            this.entity.setMoveForward(0.0F);
        }
        else
        {
            this.action = EntityMoveHelper.Action.WAIT;

            if (this.entity.onGround)
            {
                boolean flag = true;

                if (this.swet.isFriendly())
                {
                    if (this.swet.motionX == 0 || this.swet.motionZ == 0)
                    {
                        flag = false;
                    }
                }

                if (flag)
                {
                    this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));

                    if (this.jumpDelay-- <= 0)
                    {
                        this.jumpDelay = this.swet.getJumpDelay();

                        if (this.isAggressive)
                        {
                            this.jumpDelay /= 3;
                        }

                        this.swet.getJumpHelper().setJumping();

                        this.swet.playSound(SoundsAether.swet_jump, 1.0F, ((this.swet.getRNG().nextFloat() - this.swet.getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
                    }
                    else
                    {
                        this.swet.moveStrafing = 0.0F;
                        this.swet.moveForward = 0.0F;
                        this.entity.setAIMoveSpeed(0.0F);
                    }
                }
            }
            else
            {
                this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
            }
        }
    }
}
