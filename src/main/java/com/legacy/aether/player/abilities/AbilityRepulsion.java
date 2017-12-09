package com.legacy.aether.player.abilities;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

import com.legacy.aether.entities.projectile.EntityZephyrSnowball;
import com.legacy.aether.entities.projectile.darts.EntityDartBase;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.player.PlayerAether;

public class AbilityRepulsion extends Ability
{

	private Random rand = new Random();

	public AbilityRepulsion(PlayerAether player) 
	{
		super(player);
	}

	@Override
	public boolean isEnabled() 
	{
		return super.isEnabled() && this.playerAether.wearingAccessory(ItemsAether.repulsion_shield) && this.player.moveForward == 0.0F && this.player.moveStrafing == 0.0F;
	}

	@Override
	public void onUpdate() 
	{
		List<?> entities = this.player.world.getEntitiesWithinAABBExcludingEntity(this.player, this.player.getEntityBoundingBox().expand(4.0D, 4.0D, 4.0D));

		for (int size = 0; size < entities.size(); ++size)
		{
			Entity projectile = (Entity) entities.get(size);
			
			if (isProjectile(projectile) && this.getShooter(projectile) != this.player)
			{
				double x, y, z;

				if (this.getShooter(projectile) != null)
				{
					Entity shooter = this.getShooter(projectile);
					x = player.posX - shooter.posX;
					y = player.getEntityBoundingBox().minY - shooter.getEntityBoundingBox().minY;
					z = player.posZ - shooter.posZ;
				}
				else
				{
					x = player.posX - projectile.posX;
					y = player.posY - projectile.posY;
					z = player.posZ - projectile.posZ;
				}

				double difference = -Math.sqrt((x * x) + (y * y) + (z * z));

				x /= difference; y /= difference; z /= difference;

				projectile.motionX = x * 0.75F; projectile.motionY = y * 0.75F + 0.05D; projectile.motionZ = z * 0.75F;

				this.setShooter(projectile, this.player);
				player.world.playSound(this.player, new BlockPos(this.player), SoundEvents.BLOCK_NOTE_SNARE, SoundCategory.PLAYERS, 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F + 0.8F) * 1.1F);

				for (int pack = 0; pack < 12; ++pack)
				{
					double packX, packY, packZ;
					packX = (-projectile.motionX * 0.15F) + ((this.rand.nextFloat() - 0.5F) * 0.05F);
					packY = (-projectile.motionY * 0.15F) + ((this.rand.nextFloat() - 0.5F) * 0.05F);
					packZ = (-projectile.motionZ * 0.15F) + ((this.rand.nextFloat() - 0.5F) * 0.05F);
					packX *= 0.625F;
					packY *= 0.625F;
					packZ *= 0.625F;

					player.world.spawnParticle(EnumParticleTypes.FLAME, projectile.posX, projectile.posY, projectile.posZ, packX, packY, packZ);
				}
				
				this.playerAether.accessories.damageItemStackIfWearing(new ItemStack(ItemsAether.repulsion_shield));
			}
		}
	}

	public boolean onPlayerAttacked(DamageSource source)
	{
		if (isProjectile(source.getEntity()))
		{
			return false;
		}

		return true;
	}

	private Entity getShooter(Entity ent) 
	{
		return ent instanceof EntityArrow ? ((EntityArrow)ent).shootingEntity :
			ent instanceof EntityThrowable ? ((EntityThrowable)ent).getThrower() :
				ent instanceof EntityDartBase ? ((EntityDartBase)ent).shootingEntity :
					ent instanceof EntityFireball ? ((EntityFireball)ent).shootingEntity :
						ent instanceof EntityZephyrSnowball ? ((EntityZephyrSnowball)ent).shootingEntity :
							null;
	}

	private void setShooter(Entity ent, EntityLivingBase shooter) 
	{
		if (ent instanceof EntityArrow)
		{
			((EntityArrow)ent).shootingEntity = shooter;
		}
		else if (ent instanceof EntityFireball)
		{
			((EntityFireball)ent).shootingEntity = shooter;
		}
		else if (ent instanceof EntityFireball)
		{
			((EntityFireball)ent).shootingEntity = shooter;
		}
		else if (ent instanceof EntityDartBase)
		{
			((EntityDartBase)ent).shootingEntity = shooter;
		}
		else if (ent instanceof EntityZephyrSnowball)
		{
			((EntityZephyrSnowball)ent).shootingEntity = shooter;
		}
	}

	public static boolean isProjectile(Entity entity)
	{
		return entity instanceof EntityArrow || entity instanceof EntityFireball || entity instanceof EntityThrowable || entity instanceof EntityPotion || entity instanceof EntityDartBase || entity instanceof EntityZephyrSnowball;
	}

}