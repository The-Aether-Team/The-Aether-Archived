package com.legacy.aether.world;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.ITeleporter;

public class EmptyTeleporter implements ITeleporter
{

	protected final WorldServer world;

	protected final Random random;

	public EmptyTeleporter(WorldServer worldIn)
	{
		this.world = worldIn;
		this.random = new Random(worldIn.getSeed());
	}

	public void placeInPortal(Entity entityIn, float rotationYaw)
	{
		int i = MathHelper.floor(entityIn.posX);
		int j = MathHelper.floor(entityIn.posY) - 1;
		int k = MathHelper.floor(entityIn.posZ);
		entityIn.setLocationAndAngles((double) i, (double) j, (double) k, entityIn.rotationYaw, 0.0F);
		entityIn.motionX = 0.0D;
		entityIn.motionY = 0.0D;
		entityIn.motionZ = 0.0D;
	}

	public boolean placeInExistingPortal(Entity entityIn, float rotationYaw)
	{
		if (entityIn instanceof EntityPlayerMP)
		{
			((EntityPlayerMP) entityIn).connection.setPlayerLocation(entityIn.posX, entityIn.posY + 20.0D, entityIn.posZ, entityIn.rotationYaw, entityIn.rotationPitch);
			((EntityPlayerMP) entityIn).connection.captureCurrentPosition();
		}
		else
		{
			entityIn.setLocationAndAngles(entityIn.posX, entityIn.posY + 20.0D, entityIn.posZ, entityIn.rotationYaw, entityIn.rotationPitch);
		}
		return true;
	}

	public boolean makePortal(Entity entityIn)
	{
		return true;
	}

	@Override
	public void placeEntity(World world, Entity entity, float yaw)
	{
		if (entity instanceof EntityPlayerMP)
		{
			this.placeInPortal(entity, yaw);
		}
		else
		{
			this.placeInExistingPortal(entity, yaw);
		}
	}

}