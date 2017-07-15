package com.legacy.aether.client;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;

import com.legacy.aether.client.audio.AetherMusicHandler;
import com.legacy.aether.client.gui.GuiAetherInGame;
import com.legacy.aether.client.renders.AetherEntityRenderingRegistry;
import com.legacy.aether.client.renders.blocks.BlockRendering;
import com.legacy.aether.client.renders.items.ItemRendering;
import com.legacy.aether.common.ServerProxy;

public class ClientProxy extends ServerProxy
{

	@Override
	public void preInitialization()
	{
		AetherEntityRenderingRegistry.initialize();
	}

	@Override
	public void initialization()
	{
		Minecraft.getMinecraft().entityRenderer = new AetherEntityRenderer(Minecraft.getMinecraft(), Minecraft.getMinecraft().getResourceManager());

		AetherEntityRenderingRegistry.initializePlayerLayers();

		BlockRendering.initialize();
		ItemRendering.initialize();

		MinecraftForge.EVENT_BUS.register(new GuiAetherInGame(Minecraft.getMinecraft()));
		MinecraftForge.EVENT_BUS.register(new AetherClientEvents());

		registerEvent(new AetherMusicHandler());
	}

	@Override
	public void sendMessage(EntityPlayer player, String message)
	{
		if (this.getThePlayer() == player)
		{
			Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString(message));
		}
	}

	@Override
	public EntityPlayer getThePlayer()
	{
		return Minecraft.getMinecraft().thePlayer;
	}

	@Override
	public void spawnSmoke(World world, BlockPos pos)
	{
		Random rand = new Random();
		double a, b, c;
		a = pos.getX() + 0.5D + ((rand.nextFloat() - rand.nextFloat()) * 0.375D);
		b = pos.getY() + 0.5D + ((rand.nextFloat() - rand.nextFloat()) * 0.375D);
		c = pos.getZ() + 0.5D + ((rand.nextFloat() - rand.nextFloat()) * 0.375D);
		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, a, b, c, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public void spawnBlockBrokenFX(IBlockState state, BlockPos pos)
	{
		FMLClientHandler.instance().getClient().effectRenderer.addBlockDestroyEffects(pos, state);
	}

}