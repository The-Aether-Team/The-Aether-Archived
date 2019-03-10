package com.legacy.aether.player;

import com.legacy.aether.Aether;
import com.legacy.aether.api.AetherAPI;
import com.legacy.aether.api.player.IPlayerAetherProvider;
import com.legacy.aether.network.AetherNetwork;
import com.legacy.aether.network.message.UpdatePlayerMessage;
import com.legacy.aether.world.AetherModDimension;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class PlayerAetherEvents
{

	@SubscribeEvent
	public void onCapabilityAttached(AttachCapabilitiesEvent<Entity> event)
	{
		if (event.getObject() instanceof EntityPlayer && AetherAPI.getInstance().get((EntityPlayer) event.getObject()) == null)
		{
			event.addCapability(Aether.locate("player_aether"), new IPlayerAetherProvider(new PlayerAether((EntityPlayer) event.getObject())));
		}
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		if (event.getPlayer() instanceof EntityPlayerMP)
		{
			AetherNetwork.sendToClient(new UpdatePlayerMessage(AetherAPI.getInstance().get(event.getPlayer())), (EntityPlayerMP) event.getPlayer());
		}
	}

	@SubscribeEvent
	public void onPlayerUpdate(LivingUpdateEvent event)
	{
		if (event.getEntityLiving() instanceof EntityPlayer && AetherAPI.getInstance().get((EntityPlayer) event.getEntityLiving()) != null)
		{
			AetherAPI.getInstance().get((EntityPlayer) event.getEntityLiving()).onUpdate();
		}
	}

	@SubscribeEvent
	public void onRegisteredDimension(RegisterDimensionsEvent event)
	{
		if (AetherModDimension.getDimensionType() == null)
		{
			DimensionManager.registerDimension(Aether.locate("aether"), AetherModDimension.INSTANCE, new PacketBuffer(Unpooled.buffer()));
		}
	}

}