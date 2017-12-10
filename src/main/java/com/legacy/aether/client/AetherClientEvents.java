package com.legacy.aether.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.legacy.aether.AetherConfig;
import com.legacy.aether.client.gui.AetherLoadingScreen;
import com.legacy.aether.client.gui.button.GuiAccessoryButton;
import com.legacy.aether.containers.inventory.InventoryAccessories;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.networking.AetherGuiHandler;
import com.legacy.aether.networking.AetherNetworkingManager;
import com.legacy.aether.networking.packets.PacketOpenContainer;
import com.legacy.aether.player.PlayerAether;

public class AetherClientEvents 
{

	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) throws Exception
	{
		Minecraft mc = Minecraft.getMinecraft();
		TickEvent.Phase phase = event.phase;
		TickEvent.Type type = event.type;

		if (phase == TickEvent.Phase.END)
		{
			if (type.equals(TickEvent.Type.CLIENT))
			{
				if (!AetherConfig.triviaDisabled())
				{
					if (!(mc.loadingScreen instanceof AetherLoadingScreen))
					{
						mc.loadingScreen = new AetherLoadingScreen(mc);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onBowPulled(FOVUpdateEvent event)
	{
		EntityPlayer player = Minecraft.getMinecraft().player;

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
	public void onGuiOpened(GuiScreenEvent.InitGuiEvent.Post event)
	{
		if (!(event.getGui() instanceof InventoryEffectRenderer))
		{
			return;
		}

		int guiLeft = ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, (GuiContainer)event.getGui(), "guiLeft", "field_147003_i");
		int guiTop = ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, (GuiContainer)event.getGui(), "guiTop", "field_147009_r");

		if (event.getGui().getClass() == GuiInventory.class)
		{
			event.getButtonList().add(new GuiAccessoryButton(guiLeft + 26, guiTop + 65));
		}
		else if (event.getGui() instanceof GuiContainerCreative)
		{
			GuiContainerCreative gui = (GuiContainerCreative) event.getGui();

			if (gui.getSelectedTabIndex() == CreativeTabs.INVENTORY.getTabIndex())
			{
				event.getButtonList().add(new GuiAccessoryButton(guiLeft + 73, guiTop + 38));
			}
		}
	}

	@SubscribeEvent
	public void onButtonPressed(GuiScreenEvent.ActionPerformedEvent.Pre event)
	{
		if ((event.getGui().getClass() ==  GuiInventory.class || event.getGui() instanceof GuiContainerCreative) && event.getButton().id == 18067)
		{
			AetherNetworkingManager.sendToServer(new PacketOpenContainer(AetherGuiHandler.accessories));
		}
	}

	@SubscribeEvent
	public void onRenderHand(RenderSpecificHandEvent event)
	{
		PlayerGloveRenderer.renderItemFirstPerson(Minecraft.getMinecraft().player, event.getPartialTicks(), event.getInterpolatedPitch(), event.getHand(), event.getSwingProgress(), event.getItemStack(), event.getEquipProgress());
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

}