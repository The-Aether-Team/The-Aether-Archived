package com.gildedgames.the_aether.client;

import java.io.*;
import java.util.Random;

import com.gildedgames.the_aether.client.audio.AetherMusicHandler;
import com.gildedgames.the_aether.client.gui.GuiAetherInGame;
import com.gildedgames.the_aether.client.gui.GuiSunAltar;
import com.gildedgames.the_aether.client.gui.toast.GuiAetherToast;
import com.gildedgames.the_aether.client.renders.AetherEntityRenderingRegistry;
import com.gildedgames.the_aether.client.renders.blocks.BlockRendering;
import com.gildedgames.the_aether.client.renders.items.ItemRendering;
import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.CommonProxy;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;

public class ClientProxy extends CommonProxy
{
	@Override
	public void preInitialization()
	{
		GuiAetherToast.overrideToastGui();

		registerEvent(new BlockRendering());
		registerEvent(new ItemRendering());
		AetherEntityRenderingRegistry.initialize();

		MinecraftForge.EVENT_BUS.register(new AetherClientEvents());
	}

	@Override
	public void initialization()
	{
		generateTexturePack();

		BlockRendering.registerColors();
		ItemRendering.registerColors();

		AetherEntityRenderingRegistry.initializePlayerLayers();

		AetherKeybinds.initialization();

		MinecraftForge.EVENT_BUS.register(new GuiAetherInGame(Minecraft.getMinecraft()));

		registerEvent(new AetherMusicHandler());
	}

	public void generateTexturePack()
	{
		try
		{
			File resourcePacks = Minecraft.getMinecraft().getResourcePackRepository().getDirResourcepacks().getCanonicalFile();

			File buckets = new File(resourcePacks + "/Aether b1.7.3 Textures/assets/aether_legacy/textures/items/misc/buckets");
			File weapons = new File(resourcePacks + "/Aether b1.7.3 Textures/assets/aether_legacy/textures/items/weapons");
			File armor = new File(resourcePacks + "/Aether b1.7.3 Textures/assets/aether_legacy/textures/items/armor");
			File accessories = new File(resourcePacks + "/Aether b1.7.3 Textures/assets/aether_legacy/textures/items/accessories");

			File[] directories = new File[] {buckets, weapons, armor, accessories};

			if (AetherConfig.visual_options.install_resourcepack)
			{
				for (File file : directories)
				{
					if (!file.exists())
					{
						file.mkdirs();
					}
				}

				generateFile("data/Aether_b1.7.3/pack.mcmeta", "pack.mcmeta", resourcePacks.getAbsolutePath() + "/Aether b1.7.3 Textures");
				generateFile("data/Aether_b1.7.3/pack.png", "pack.png", resourcePacks.getAbsolutePath() + "/Aether b1.7.3 Textures");
				generateFile("data/Aether_b1.7.3/skyroot_remedy_bucket.png", "skyroot_remedy_bucket.png", buckets.getAbsolutePath());
				generateFile("data/Aether_b1.7.3/weapons/bow_pulling_0.png", "bow_pulling_0.png", weapons.getAbsolutePath());
				generateFile("data/Aether_b1.7.3/weapons/bow_pulling_1.png", "bow_pulling_1.png", weapons.getAbsolutePath());
				generateFile("data/Aether_b1.7.3/weapons/bow_pulling_2.png", "bow_pulling_2.png", weapons.getAbsolutePath());
				generateFile("data/Aether_b1.7.3/weapons/flaming_sword.png", "flaming_sword.png", weapons.getAbsolutePath());
				generateFile("data/Aether_b1.7.3/weapons/holy_sword.png", "holy_sword.png", weapons.getAbsolutePath());
				generateFile("data/Aether_b1.7.3/weapons/lightning_sword.png", "lightning_sword.png", weapons.getAbsolutePath());
				generateFile("data/Aether_b1.7.3/weapons/phoenix_bow.png", "phoenix_bow.png", weapons.getAbsolutePath());
				generateFile("data/Aether_b1.7.3/armor/phoenix_boots.png", "phoenix_boots.png", armor.getAbsolutePath());
				generateFile("data/Aether_b1.7.3/armor/phoenix_leggings.png", "phoenix_leggings.png", armor.getAbsolutePath());
				generateFile("data/Aether_b1.7.3/armor/phoenix_chestplate.png", "phoenix_chestplate.png", armor.getAbsolutePath());
				generateFile("data/Aether_b1.7.3/armor/phoenix_helmet.png", "phoenix_helmet.png", armor.getAbsolutePath());
				generateFile("data/Aether_b1.7.3/accessories/phoenix_gloves.png", "phoenix_gloves.png", accessories.getAbsolutePath());
				generateFile("data/Aether_b1.7.3/accessories/agility_cape.png", "agility_cape.png", accessories.getAbsolutePath());
			}
		}
		catch (IOException ignore) { }
	}

	public void generateFile(String input, String name, String path)
	{
		try {
			File file = new File(path + "/" + name);

			if (!file.exists())
			{
				InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(input);
				FileOutputStream outputStream = new FileOutputStream(file);

				if (inputStream != null)
				{
					int i;
					while ((i = inputStream.read()) != -1)
					{
						outputStream.write(i);
					}

					inputStream.close();
					outputStream.close();
				}
			}
		}
		catch (IOException ignore) { }
	}

	@Override
	public void postInitialization() 
	{

	}

	@Override
	public void sendMessage(EntityPlayer reciever, ITextComponent message)
	{
		if (this.getThePlayer() == reciever)
		{
			Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(message);
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
	public void spawnSplode(World world, double x, double y, double z)
	{
		Random rand = new Random();
		double var2 = ((rand.nextFloat() - 0.5F) * 0.5F);
		double var4 = ((rand.nextFloat() - 0.5F) * 0.5F);
		double var6 = ((rand.nextFloat() - 0.5F) * 0.5F);
		var2 *= 0.5D;
		var4 *= 0.5D;
		var6 *= 0.5D;
		world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, x, y, z, var2, var4 + 0.125D, var6);
	}

	@Override
	public void spawnBlockBrokenFX(IBlockState state, BlockPos pos)
	{
		FMLClientHandler.instance().getClient().effectRenderer.addBlockDestroyEffects(pos, state);
	}

	@Override
	public void openSunAltar()
	{
		FMLClientHandler.instance().getClient().displayGuiScreen(new GuiSunAltar());
	}
}