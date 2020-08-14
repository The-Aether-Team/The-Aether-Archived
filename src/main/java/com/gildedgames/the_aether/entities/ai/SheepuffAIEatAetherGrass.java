package com.gildedgames.the_aether.entities.ai;

import com.gildedgames.the_aether.entities.passive.EntitySheepuff;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.gildedgames.the_aether.blocks.BlocksAether;

public class SheepuffAIEatAetherGrass extends EntityAIBase {

	private EntitySheepuff sheepuff;

	private World entityWorld;

	int eatingGrassTimer;

	public SheepuffAIEatAetherGrass(EntitySheepuff sheepuff)
	{
		this.sheepuff = sheepuff;
		this.entityWorld = sheepuff.worldObj;
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
			int i = MathHelper.floor_double(this.sheepuff.posX);
			int j = MathHelper.floor_double(this.sheepuff.posY);
			int k = MathHelper.floor_double(this.sheepuff.posZ);

			return this.entityWorld.getBlock(i, j - 1, k) == BlocksAether.aether_grass;
		}
	}

	public void startExecuting()
	{
		this.eatingGrassTimer = 40;
		this.entityWorld.setEntityState(this.sheepuff, (byte) 10);
		this.sheepuff.getNavigator().clearPathEntity();
	}

	public void resetTask() {
		this.eatingGrassTimer = 0;
	}

	public boolean continueExecuting() {
		return this.eatingGrassTimer > 0;
	}

	public int getEatingGrassTimer() {
		return this.eatingGrassTimer;
	}

	public void updateTask()
	{
		this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);

		if (this.eatingGrassTimer == 4)
		{
			int i = MathHelper.floor_double(this.sheepuff.posX);
			int j = MathHelper.floor_double(this.sheepuff.posY);
			int k = MathHelper.floor_double(this.sheepuff.posZ);

			if (this.entityWorld.getBlock(i, j - 1, k) == BlocksAether.aether_grass)
			{
				if (this.entityWorld.getGameRules().getGameRuleBooleanValue("mobGriefing"))
				{
					this.entityWorld.playAuxSFX(2001, i, j - 1, k, Block.getIdFromBlock(BlocksAether.aether_grass));
					this.entityWorld.setBlock(i, j - 1, k, BlocksAether.aether_dirt);
				}

				this.sheepuff.eatGrassBonus();
			}
		}
	}

}