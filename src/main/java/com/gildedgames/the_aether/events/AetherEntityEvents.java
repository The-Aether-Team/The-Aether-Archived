package com.gildedgames.the_aether.events;

import com.gildedgames.the_aether.player.PlayerAether;
import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.world.TeleporterAether;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AetherEntityEvents
{
	@SubscribeEvent
	public void onEntityUpdate(LivingEvent.LivingUpdateEvent event)
	{
		if (event.getEntity() instanceof EntityLiving)
		{
			entityUpdateEvents(event.getEntity(), event);
		}
	}
	
	private void entityUpdateEvents(Entity entity, Event event)
	{
		if (entity.isBeingRidden())
		{
			if (entity.dimension == AetherConfig.dimension.aether_dimension_id && !entity.world.isRemote)
			{
				MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
				int previousDimension = entity.dimension;
				int transferDimension = previousDimension == AetherConfig.dimension.aether_dimension_id ? 0 : AetherConfig.dimension.aether_dimension_id;
				
				if (entity.posY <= 0)
				{
					EntityPlayer rider = null;

					for (Entity passenger : entity.getPassengers())
					{
						if (passenger instanceof EntityPlayer)
						{
							rider = (EntityPlayer) passenger;
						}

						passenger.dismountRidingEntity();
					}
					
					entity.timeUntilPortal = 300;
					transferEntity(false, entity, rider, server.getWorld(previousDimension), server.getWorld(transferDimension));
					
					if (rider != null)
					{
						transferPlayer(entity, rider);
					}
				}
			}
		}
	}
	
	public static void transferEntity(boolean shouldSpawnPortal, Entity entityIn, Entity rider, WorldServer previousWorldIn, WorldServer newWorldIn) {
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

		BlockPos previousPos = entityIn.getPosition();

		entityIn.dimension = newWorldIn.provider.getDimension();
		previousWorldIn.removeEntityDangerously(entityIn);
		entityIn.isDead = false;

		server.getPlayerList().transferEntityToWorld(entityIn, previousWorldIn.provider.getDimension(), previousWorldIn, newWorldIn, new TeleporterAether(false, newWorldIn));

		entityIn.setPosition(previousPos.getX(), 255, previousPos.getZ());
	}

	public static void transferPlayer(Entity riding, EntityPlayer player)
	{
		PlayerAether playerAether = (PlayerAether) AetherAPI.getInstance().get(player);

		playerAether.teleportPlayer(false);

		playerAether.getEntity().startRiding(riding);
	}
}
