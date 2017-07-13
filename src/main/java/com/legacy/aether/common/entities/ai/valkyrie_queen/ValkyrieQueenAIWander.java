package com.legacy.aether.common.entities.ai.valkyrie_queen;

import net.minecraft.entity.ai.EntityAIWander;

import com.legacy.aether.common.entities.bosses.valkyrie_queen.EntityValkyrieQueen;

public class ValkyrieQueenAIWander extends EntityAIWander
{

	private EntityValkyrieQueen theQueen;

	public ValkyrieQueenAIWander(EntityValkyrieQueen creatureIn, double speedIn)
	{
		super(creatureIn, speedIn);

		this.theQueen = creatureIn;
		this.setExecutionChance(50);
	}

	@Override
    public boolean shouldExecute()
    {
    	return super.shouldExecute() && this.theQueen.isBossReady();
    }

}