package com.legacy.aether.server.player.movement;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

import com.legacy.aether.server.player.PlayerAether;

public class AetherPoisonMovement 
{

    public int poisonTime  = 0;

    public double rotD, motD;

	private EntityPlayer thePlayer;

	public PlayerAether player;

	public AetherPoisonMovement(PlayerAether player)
	{
		this.player = player;
		this.thePlayer = player.thePlayer;
	}

	public void onUpdate()
	{
        int timeUntilHit = this.poisonTime % 50;

        if (this.thePlayer.isDead)
        {
        	this.poisonTime = 0;

        	return;
        }

        if (this.poisonTime < 0)
        {
        	this.poisonTime++;

            return;
        }

        if (this.poisonTime == 0)
        {
            return;
        }

        this.distractEntity();

        if (timeUntilHit == 0) 
        {
            this.thePlayer.attackEntityFrom(DamageSource.generic, 1);
        }

        this.poisonTime--;
	}

    public boolean afflictPoison() 
    {
        this.poisonTime = 500;

        return true;
    }

    public boolean curePoison(int i) 
    {
        if (this.poisonTime == -500)
        {
            return false;
        }

        this.poisonTime = -500 - i;

        return true;
    }

    public void distractEntity()
    {
    	double gaussian = this.thePlayer.worldObj.rand.nextGaussian();
        double newMotD = 0.1D * gaussian;
        double newRotD = (Math.PI / 4D) * gaussian;

        this.motD = 0.2D * newMotD + (0.8D) * this.motD;
        this.thePlayer.motionX += this.motD;
        this.thePlayer.motionZ += this.motD;
        this.rotD = 0.125D * newRotD + (1.0D - 0.125D) * this.rotD;

        this.thePlayer.rotationYaw = (float)((double)this.thePlayer.rotationYaw + rotD);
        this.thePlayer.rotationPitch = (float)((double)this.thePlayer.rotationPitch + rotD);
    }

}