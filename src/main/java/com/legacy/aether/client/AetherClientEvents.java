package com.legacy.aether.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.GuiScreenEvent.PotionShiftEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

import com.legacy.aether.client.gui.inventory.GuiAccessories;
import com.legacy.aether.server.containers.inventory.InventoryAccessories;
import com.legacy.aether.server.items.ItemsAether;
import com.legacy.aether.server.networking.AetherGuiHandler;
import com.legacy.aether.server.networking.AetherNetworkingManager;
import com.legacy.aether.server.networking.packets.PacketOpenContainer;
import com.legacy.aether.server.player.PlayerAether;

public class AetherClientEvents 
{

	@SubscribeEvent
	public void onInventoryKeyPressed(KeyInputEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();

		if (mc.currentScreen == null && mc.theWorld != null)
		{
			if (mc.thePlayer != null && !mc.thePlayer.capabilities.isCreativeMode && mc.gameSettings.keyBindInventory.isPressed())
			{
				if (!mc.thePlayer.hasAchievement(AchievementList.OPEN_INVENTORY))
				{
					mc.thePlayer.addStat(AchievementList.OPEN_INVENTORY);
				}

				AetherNetworkingManager.sendToServer(new PacketOpenContainer(AetherGuiHandler.accessories));
			}
		}
	}

	@SubscribeEvent
	public void onBowPulled(FOVUpdateEvent event)
	{
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;

		if (player == null || (player != null && player.getActiveItemStack() == null))
		{
			return;
		}

		Item item = player.getActiveItemStack().getItem();

		if (item == ItemsAether.phoenix_bow)
		{
	        int i = player.getItemInUseMaxCount();
	        float f1 = (float)i / 20.0F;

	        if (f1 > 1.0F)
	        {
	            f1 = 1.0F;
	        }
	        else
	        {
	            f1 = f1 * f1;
	        }

	        float original = event.getFov();

	        original *= 1.0F - f1 * 0.15F;

	        event.setNewfov(original);
		}
	}

	@SubscribeEvent
	public void onRenderHand(RenderHandEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		FirstPersonRenderer render = new FirstPersonRenderer(mc, event.getPartialTicks());

		if (render.shouldRender())
		{
			mc.entityRenderer.enableLightmap();
			render.render();
			mc.entityRenderer.disableLightmap();
		}
	}

	@SubscribeEvent
	public void onInvisibilityPlayerUpdate(RenderPlayerEvent.Pre event)
	{
		EntityPlayer player = event.getEntityPlayer();
		PlayerAether playerAether = PlayerAether.get(player);

		if (playerAether != null)
		{
			if (playerAether.wearingAccessory(ItemsAether.invisibility_cape))
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void onTextureStichedEvent(TextureStitchEvent event)
	{
		for (int i = 0; i < InventoryAccessories.EMPTY_SLOT_NAMES.length; ++i)
		{
			event.getMap().registerSprite(new ResourceLocation("aether_legacy", "items/slots/" + InventoryAccessories.EMPTY_SLOT_NAMES[i]));
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onGuiRender(InitGuiEvent.Post event)
	{
		ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());

		for (int size = 0; size < event.getButtonList().size(); ++size)
		{
			GuiButton button = event.getButtonList().get(size);

			if (Loader.isModLoaded("Baubles"))
			{
				if (button.id == 55)
				{
					button.xPosition = (resolution.getScaledWidth() / 2) - 39;
				}
			}
		}
	}

	@SubscribeEvent
	public void onStopPotionEffect(PotionShiftEvent event)
	{
		if (event.getGui() instanceof GuiAccessories)
		{
			event.setCanceled(true);
		}
	}

}