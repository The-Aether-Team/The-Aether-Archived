package com.legacy.aether.entities.ai;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.entities.passive.EntitySheepuff;

public class SheepuffAIEatAetherGrass extends EntityAIBase {

	private EntitySheepuff sheepuff;

	private World entityWorld;

	int eatingGrassTimer;

	public SheepuffAIEatAetherGrass(EntitySheepuff sheepuff) {
		this.sheepuff = sheepuff;
		this.entityWorld = sheepuff.worldObj;
		this.setMutexBits(7);
	}

	public boolean shouldExecute() {
		if (this.sheepuff.getRNG().nextInt(1000) != 0) {
			return false;
		} else {
			return this.entityWorld.getBlock((int) this.sheepuff.posX, (int) this.sheepuff.posY - 1, (int) this.sheepuff.posZ) == BlocksAether.aether_grass;
		}
	}

	public void startExecuting() {
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

	public void updateTask() {
		this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);

		if (this.eatingGrassTimer == 4) {

			if (this.entityWorld.getBlock((int) this.sheepuff.posX, (int) this.sheepuff.posY - 1, (int) this.sheepuff.posZ) == BlocksAether.aether_grass) {
				if (this.entityWorld.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
					this.entityWorld.playAuxSFX(2001, (int) this.sheepuff.posX, (int) this.sheepuff.posY - 1, (int) this.sheepuff.posZ, Block.getIdFromBlock(BlocksAether.aether_grass));
					this.entityWorld.setBlock((int) this.sheepuff.posX, (int) this.sheepuff.posY - 1, (int) this.sheepuff.posZ, BlocksAether.aether_dirt);
				}

				this.sheepuff.eatGrassBonus();
			}
		}
	}

}