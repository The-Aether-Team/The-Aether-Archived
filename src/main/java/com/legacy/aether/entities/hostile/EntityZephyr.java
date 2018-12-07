package com.legacy.aether.entities.hostile;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.entities.ai.EntityAIUpdateState;
import com.legacy.aether.entities.ai.zephyr.ZephyrAIShootTarget;
import com.legacy.aether.entities.ai.zephyr.ZephyrAITravelCourse;

public class EntityZephyr extends EntityFlying implements IMob
{

    public ZephyrAIShootTarget shootingAI;

    public int courseCooldown;

    public double waypointX, waypointY, waypointZ;

    public EntityZephyr(World world)
    {
        super(world);

        this.setSize(4F, 4F);

        this.tasks.addTask(1, this.shootingAI = new ZephyrAIShootTarget(this));
        this.tasks.addTask(0, new EntityAIUpdateState(this));
        this.tasks.addTask(2, new ZephyrAITravelCourse(this));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
    }

    @Override
    protected boolean isAIEnabled()
    {
    	return true;
    }

    @Override
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);

        return this.worldObj.getBlock(i, j - 1, k) == BlocksAether.aether_grass && this.rand.nextInt(85) == 0 && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.isAnyLiquid(this.boundingBox) && this.worldObj.getBlockLightValue(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.boundingBox.minY), MathHelper.floor_double(this.posZ)) > 8 && super.getCanSpawnHere();
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }

    @Override
    public void onEntityUpdate()
    {
    	super.onEntityUpdate();

    	if (this.worldObj.isRemote)
    	{
    		this.shootingAI.updateTask();
    	}

		if(this.posY < -2D || this.posY > 255D)
		{
			this.despawnEntity();
		}

        if(this.getAttackTarget() != null && this.getAttackTarget().isDead)
        {
        	this.setAttackTarget(null);
        }

        if(!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL)
        {
        	this.setDead();
        }
    }

    @Override
    protected void updateEntityActionState()
    {
        super.updateEntityActionState();
    }

    @Override
    protected String getLivingSound()
    {
    	return "aether_legacy:aemob.zephyr.call";
    }

    @Override
    protected String getHurtSound()
    {
        return "aether_legacy:aemob.zephyr.call";
    }

    @Override
    protected String getDeathSound()
    {
        return null;
    }

	@Override
	protected void dropFewItems(boolean var1, int var2) 
	{
		this.dropItem(Item.getItemFromBlock(BlocksAether.aercloud), 1);
	}

    @Override
    public boolean canDespawn()
    {
    	return true;
    }

    @Override
    protected float getSoundVolume()
    {
        return 1F;
    }

}