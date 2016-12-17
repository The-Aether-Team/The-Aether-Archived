package com.legacy.aether.server.entities.ai.aerbunny;

import net.minecraft.entity.ai.EntityAIBase;

import com.legacy.aether.server.entities.passive.mountable.EntityAerbunny;

public class AerbunnyAIHop extends EntityAIBase
{

    private EntityAerbunny aerbunny;

    public AerbunnyAIHop(EntityAerbunny aerbunny)
    {
        this.aerbunny = aerbunny;
        this.setMutexBits(8);
    }

    public boolean shouldExecute()
    {
        return this.aerbunny.motionZ > 0.0D || this.aerbunny.motionX > 0.0D || this.aerbunny.onGround;
    }

    public void updateTask()
    {
        this.aerbunny.getJumpHelper().setJumping();
    }

}