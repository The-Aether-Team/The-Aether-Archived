package com.gildedgames.the_aether.entities.projectile;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

import com.gildedgames.the_aether.entities.projectile.darts.EntityDartPoison;

public class EntityPoisonNeedle extends EntityDartPoison {

	public EntityPoisonNeedle(World world) {
		super(world);
	}

	public EntityPoisonNeedle(World world, EntityLiving ent, float velocity) {
		super(world, ent, velocity);
	}

	public void entityInit() {
		super.entityInit();
		this.setDamage(1);
	}

	@Override
	protected float getGravityVelocity() {
		return 0.03F;
	}

}