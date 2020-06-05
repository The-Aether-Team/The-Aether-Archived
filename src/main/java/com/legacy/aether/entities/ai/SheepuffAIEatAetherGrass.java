package com.legacy.aether.entities.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.entities.passive.EntitySheepuff;

public class SheepuffAIEatAetherGrass extends EntityAIBase
{

    private EntitySheepuff sheepuff;

    private World entityWorld;

    int eatingGrassTimer;

    public SheepuffAIEatAetherGrass(EntitySheepuff sheepuff)
    {
        this.sheepuff = sheepuff;
        this.entityWorld = sheepuff.world;
        this.setMutexBits(7);
    }

    public boolean shouldExecute()
    {
        if (this.sheepuff.getRNG().nextInt(1000) != 0)
        {
            return false;
        }
        else
        {
            BlockPos blockpos = new BlockPos(this.sheepuff.posX, this.sheepuff.posY, this.sheepuff.posZ);
            return this.entityWorld.getBlockState(blockpos.down()).getBlock() == BlocksAether.aether_grass;
        }
    }

    public void startExecuting()
    {
        this.eatingGrassTimer = 40;
        this.entityWorld.setEntityState(this.sheepuff, (byte)10);
        this.sheepuff.getNavigator().clearPath();
    }

    public void resetTask()
    {
        this.eatingGrassTimer = 0;
    }

    public boolean continueExecuting()
    {
        return this.eatingGrassTimer > 0;
    }

    public int getEatingGrassTimer()
    {
        return this.eatingGrassTimer;
    }

    public void updateTask()
    {
        this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);

       //if (this.eatingGrassTimer == 0)
      //  {
        	
        	//System.out.println("4");
            BlockPos blockpos = new BlockPos(this.sheepuff.posX, this.sheepuff.posY, this.sheepuff.posZ);

            BlockPos blockpos1 = blockpos.down();

            if (this.entityWorld.getBlockState(blockpos1).getBlock() == BlocksAether.aether_grass)
            {
            	this.entityWorld.playEvent(2001, blockpos1, Block.getIdFromBlock(BlocksAether.aether_grass));
            	this.entityWorld.setBlockState(blockpos1, BlocksAether.aether_dirt.getDefaultState());

                this.sheepuff.eatGrassBonus();
           // }
            
        }
    }

}