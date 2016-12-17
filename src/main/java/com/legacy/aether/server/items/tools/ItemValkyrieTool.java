package com.legacy.aether.server.items.tools;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import com.legacy.aether.server.items.util.EnumAetherToolType;

public class ItemValkyrieTool extends ItemAetherTool 
{

	public ItemValkyrieTool(EnumAetherToolType toolType) 
	{
		super(ToolMaterial.DIAMOND, toolType);
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
	{
		if (!(entityLiving instanceof EntityPlayer))
		{
			return false;
		}

		EntityPlayer player = (EntityPlayer) entityLiving;

		Vec3d playerVision = player.getLookVec();
		AxisAlignedBB reachDistance = player.getEntityBoundingBox().expand(10.0F, 10.0F, 10.0F);

		List<Entity> locatedEntities = player.worldObj.getEntitiesWithinAABB(Entity.class, reachDistance);

		Entity found = null;
		double foundLen = 0.0D;

		for (Object o : locatedEntities)
		{
			if (o == player)
			{
				continue;
			}

			Entity ent = (Entity) o;

			if (!ent.canBeCollidedWith())
			{
				continue;
			}

			Vec3d vec = new Vec3d(ent.posX - player.posX, ent.getEntityBoundingBox().minY + ent.height / 2f - player.posY - player.getEyeHeight(), ent.posZ - player.posZ);
			double len = vec.lengthVector();

			if (len > 10.0F)
			{
				continue;
			}

			vec = vec.normalize();
			double dot = playerVision.dotProduct(vec);

			if (dot < 1.0 - 0.125 / len || !player.canEntityBeSeen(ent))
			{
				continue;
			}

			if (foundLen == 0.0 || len < foundLen)
			{
				found = ent;
				foundLen = len;
			}
		}

		if (found != null && player.getRidingEntity() != found)
		{
			stack.damageItem(1, player);

			player.attackTargetEntityWithCurrentItem(found);
		}

		return false;
	}

}