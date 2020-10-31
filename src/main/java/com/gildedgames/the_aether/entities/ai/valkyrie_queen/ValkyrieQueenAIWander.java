package com.gildedgames.the_aether.entities.ai.valkyrie_queen;

import com.gildedgames.the_aether.entities.bosses.valkyrie_queen.EntityValkyrieQueen;
import net.minecraft.entity.ai.EntityAIWander;

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