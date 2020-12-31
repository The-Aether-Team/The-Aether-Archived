package com.gildedgames.the_aether.client;

import com.gildedgames.the_aether.AetherConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;

import com.gildedgames.the_aether.CommonProxy;
import com.gildedgames.the_aether.client.audio.AetherMusicHandler;
import com.gildedgames.the_aether.client.gui.AetherLoadingScreen;
import com.gildedgames.the_aether.client.gui.GuiAetherInGame;
import com.gildedgames.the_aether.client.gui.GuiSunAltar;
import com.gildedgames.the_aether.client.renders.AetherEntityRenderer;
import com.gildedgames.the_aether.client.renders.RendersAether;
import com.gildedgames.the_aether.compatibility.client.AetherClientCompatibility;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClientProxy extends CommonProxy {

	public static final IIcon[] ACCESSORY_ICONS = new IIcon[8];

	@Override
	public void init() {
		try
		{
			File resourcePacks = Minecraft.getMinecraft().getResourcePackRepository().getDirResourcepacks().getCanonicalFile();

			File buckets = new File(resourcePacks + "/Aether b1.7.3 Textures/assets/aether_legacy/textures/items/misc/buckets");
			File weapons = new File(resourcePacks + "/Aether b1.7.3 Textures/assets/aether_legacy/textures/items/weapons");
			File armor = new File(resourcePacks + "/Aether b1.7.3 Textures/assets/aether_legacy/textures/items/armor");
			File accessories = new File(resourcePacks + "/Aether b1.7.3 Textures/assets/aether_legacy/textures/items/accessories");

			File[] directories = new File[] {buckets, weapons, armor, accessories};

			if (AetherConfig.installResourcepack())
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

		berryBushRenderID = RenderingRegistry.getNextAvailableRenderId();
		treasureChestRenderID = RenderingRegistry.getNextAvailableRenderId();
		aetherFlowerRenderID = RenderingRegistry.getNextAvailableRenderId();

		EntityRenderer previousRenderer = Minecraft.getMinecraft().entityRenderer;

		Minecraft.getMinecraft().entityRenderer = new AetherEntityRenderer(Minecraft.getMinecraft(), previousRenderer, Minecraft.getMinecraft().getResourceManager());

		RendersAether.initialization();

		AetherKeybinds.initialization();

		registerEvent(new AetherMusicHandler());
		registerEvent(new AetherClientEvents());
		registerEvent(new GuiAetherInGame(Minecraft.getMinecraft()));

		AetherClientCompatibility.initialization();
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
	public void sendMessage(EntityPlayer player, String text) {
		if (this.getPlayer() == player)
		{
			Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(text));
		}
	}

	@Override
	public void openSunAltar() {
		FMLClientHandler.instance().getClient().displayGuiScreen(new GuiSunAltar());
	}

	@Override
	public EntityPlayer getPlayer() {
		return Minecraft.getMinecraft().thePlayer;
	}

}
