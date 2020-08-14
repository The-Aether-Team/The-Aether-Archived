package com.gildedgames.the_aether.player.abilities;

import java.util.List;
import java.util.Random;

import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.api.player.util.IAetherAbility;
import com.gildedgames.the_aether.entities.projectile.EntityProjectileBase;
import com.gildedgames.the_aether.items.ItemsAether;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.WorldServer;

import cpw.mods.fml.common.registry.IThrowableEntity;

public class AbilityRepulsion implements IAetherAbility {

	private Random rand = new Random();

	private final IPlayerAether player;

	public AbilityRepulsion(IPlayerAether player) {
		this.player = player;
	}

	@Override
	public boolean shouldExecute() {
		return this.player.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.repulsion_shield));
	}

	@Override
	public void onUpdate() {
		if (this.player.getEntity().worldObj.isRemote) {
			return;
		}

		List<?> entities = this.player.getEntity().worldObj.getEntitiesWithinAABBExcludingEntity(this.player.getEntity(), this.player.getEntity().boundingBox.expand(3.0D, 3.0D, 3.0D));

		for (int size = 0; size < entities.size(); ++size) {
			Entity projectile = (Entity) entities.get(size);

			if (isProjectile(projectile) && this.getShooter(projectile) != this.player.getEntity()) {
				double x, y, z;

				Entity shooter = this.getShooter(projectile);

				if (shooter == null)
				{
					return;
				}

				x = this.player.getEntity().posX - shooter.posX;
				y = this.player.getEntity().boundingBox.minY - shooter.boundingBox.minY;
				z = this.player.getEntity().posZ - shooter.posZ;

				double difference = -Math.sqrt((x * x) + (y * y) + (z * z));

				x /= difference;
				y /= difference;
				z /= difference;

				projectile.setDead();

				double packX, packY, packZ;
				packX = (-projectile.motionX * 0.15F) + ((this.rand.nextFloat() - 0.5F) * 0.05F);
				packY = (-projectile.motionY * 0.15F) + ((this.rand.nextFloat() - 0.5F) * 0.05F);
				packZ = (-projectile.motionZ * 0.15F) + ((this.rand.nextFloat() - 0.5F) * 0.05F);

				((WorldServer) this.player.getEntity().worldObj).func_147487_a("flame", projectile.posX, projectile.posY, projectile.posZ, 12, packX, packY, packZ, 0.625F);

				this.player.getEntity().worldObj.playSoundAtEntity(this.player.getEntity(), "note.snare", 1.0F, 1.0F);
				this.player.getAccessoryInventory().damageWornStack(1, new ItemStack(ItemsAether.repulsion_shield));
			}
		}
	}

	public boolean onPlayerAttacked(DamageSource source) {
		if (isProjectile(source.getEntity())) {
			return true;
		}

		return false;
	}

	private Entity getShooter(Entity ent) {
		return ent instanceof EntityArrow ? ((EntityArrow) ent).shootingEntity : ent instanceof EntityThrowable ? ((EntityThrowable) ent).getThrower() : ent instanceof EntityProjectileBase ? ((EntityProjectileBase) ent).getThrower() : ent instanceof EntityFireball ? ((EntityFireball) ent).shootingEntity : null;
	}

	public static boolean isProjectile(Entity entity) {
		return entity instanceof IProjectile || entity instanceof IThrowableEntity;
	}

}