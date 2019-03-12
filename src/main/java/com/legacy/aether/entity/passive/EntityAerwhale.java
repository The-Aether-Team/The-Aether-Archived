package com.legacy.aether.entity.passive;

import com.legacy.aether.entity.EntityTypesAether;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAerwhale extends EntityAetherAnimal
{

	public EntityAerwhale(World worldIn)
	{
		super(EntityTypesAether.AERWHALE, worldIn);

		this.moveHelper = new EntityFlyHelper(this);
	}

	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(2, new EntityAIWanderAvoidWaterFlying(this, 1.0D));
	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();

		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);

		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
		this.getAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.4D);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
	}

	@Override
	protected PathNavigate createNavigator(World worldIn)
	{
		PathNavigateFlying flyingNavigation = new PathNavigateFlying(this, worldIn);

		flyingNavigation.setCanOpenDoors(false);
		flyingNavigation.setCanSwim(true);
		flyingNavigation.setCanEnterDoors(true);

		return flyingNavigation;
	}

	@Override
	public void fall(float distance, float damageMultiplier)
	{

	}

	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos)
	{

	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable)
	{
		return new EntityAerwhale(this.world);
	}

}