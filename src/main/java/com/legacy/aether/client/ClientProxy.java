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

import com.legacy.aether.CommonProxy;
import com.legacy.aether.client.audio.AetherMusicHandler;
import com.legacy.aether.client.gui.GuiAetherInGame;
import com.legacy.aether.client.renders.AetherEntityRenderingRegistry;
import com.legacy.aether.client.renders.blocks.BlockRendering;
import com.legacy.aether.client.renders.items.ItemRendering;

public class ClientProxy extends CommonProxy
{

	@Override
	public void preInitialization()
	{
		registerEvent(new BlockRendering());
		registerEvent(new ItemRendering());
		AetherEntityRenderingRegistry.initialize();

		MinecraftForge.EVENT_BUS.register(new AetherClientEvents());
	}

	@Override
	public void initialization()
	{
		BlockRendering.registerColors();
		ItemRendering.registerColors();

		AetherEntityRenderingRegistry.initializePlayerLayers();

		MinecraftForge.EVENT_BUS.register(new GuiAetherInGame(Minecraft.getMinecraft()));

		registerEvent(new AetherMusicHandler());
	}

	@Override
	public void postInitialization() 
	{

	}

	@Override
	public void sendMessage(EntityPlayer reciever, String message)
	{
		if (this.getThePlayer() == reciever)
		{
			Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new TextComponentString(message));
		}
	}

	@Override
	public EntityPlayer getThePlayer()
	{
		return Minecraft.getMinecraft().player;
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