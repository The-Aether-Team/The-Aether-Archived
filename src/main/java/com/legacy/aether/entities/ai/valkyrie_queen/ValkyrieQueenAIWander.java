package com.legacy.aether.entities.ai.valkyrie_queen;

import net.minecraft.entity.ai.EntityAIWander;

import com.legacy.aether.entities.bosses.valkyrie_queen.EntityValkyrieQueen;

public class ValkyrieQueenAIWander extends EntityAIWander
{

	private EntityValkyrieQueen theQueen;

	public ValkyrieQueenAIWander(EntityValkyrieQueen creatureIn, double speedIn)
	{
		super(creatureIn, speedIn);

		this.theQueen = creatureIn;
	}

	@Override
    public boolean shouldExecute()
    {
    	return super.shouldExecute() && this.theQueen.isBossReady();
    }

}