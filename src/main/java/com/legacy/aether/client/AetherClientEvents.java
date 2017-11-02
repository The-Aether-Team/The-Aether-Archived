package com.legacy.aether.client;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.MouseInputEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import com.legacy.aether.client.gui.AetherLoadingScreen;
import com.legacy.aether.client.gui.button.GuiAccessoryButton;
import com.legacy.aether.common.AetherConfig;
import com.legacy.aether.common.containers.inventory.InventoryAccessories;
import com.legacy.aether.common.items.ItemsAether;
import com.legacy.aether.common.networking.AetherGuiHandler;
import com.legacy.aether.common.networking.AetherNetworkingManager;
import com.legacy.aether.common.networking.packets.PacketOpenContainer;
import com.legacy.aether.common.player.PlayerAether;

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

	private static final GuiAccessoryButton ACCESSORY_BUTTON = new GuiAccessoryButton(0, 0);

	private static int previousSelectedTabIndex = -1;

	@SubscribeEvent
	public void onGuiOpened(GuiScreenEvent.InitGuiEvent.Post event)
	{
		if (event.getGui() instanceof GuiContainer)
		{
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			Class<?> clazz = event.getGui().getClass();

			int guiLeft = ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, (GuiContainer)event.getGui(), "guiLeft", "field_147003_i");
			int guiTop = ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, (GuiContainer)event.getGui(), "guiTop", "field_147009_r");

			if (player.capabilities.isCreativeMode)
			{
				if (event.getGui() instanceof GuiContainerCreative)
				{
					if (((GuiContainerCreative)event.getGui()).getSelectedTabIndex() == CreativeTabs.INVENTORY.getTabIndex())
					{
						event.getButtonList().add(ACCESSORY_BUTTON.setPosition(guiLeft + 73, guiTop + 38));
						previousSelectedTabIndex = CreativeTabs.INVENTORY.getTabIndex();
					}
				}
			}
			else if (clazz == GuiInventory.class)
			{
				event.getButtonList().add(ACCESSORY_BUTTON.setPosition(guiLeft + 26, guiTop + 65));
			}
		}
	}

	@SubscribeEvent
	public void onMouseClicked(MouseInputEvent.Post event)
	{
		if (event.getGui() instanceof GuiContainerCreative)
		{
			GuiContainerCreative guiScreen = (GuiContainerCreative) event.getGui();
			List<GuiButton> buttonList = ObfuscationReflectionHelper.getPrivateValue(GuiScreen.class, (GuiScreen) guiScreen, 7);

			if (previousSelectedTabIndex != guiScreen.getSelectedTabIndex())
			{
				if (guiScreen.getSelectedTabIndex() == CreativeTabs.INVENTORY.getTabIndex() && !buttonList.contains(ACCESSORY_BUTTON))
				{
					int guiLeft = ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, (GuiContainer)event.getGui(), "guiLeft", "field_147003_i");
					int guiTop = ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, (GuiContainer)event.getGui(), "guiTop", "field_147009_r");

					buttonList.add(ACCESSORY_BUTTON.setPosition(guiLeft + 73, guiTop + 38));
				}
				else if (previousSelectedTabIndex == CreativeTabs.INVENTORY.getTabIndex())
				{
					buttonList.remove(ACCESSORY_BUTTON);
				}

				previousSelectedTabIndex = guiScreen.getSelectedTabIndex();
			}
		}
	}

	@SubscribeEvent
	public void onButtonPressed(GuiScreenEvent.ActionPerformedEvent.Pre event)
	{
		Class<?> clazz = event.getGui().getClass();

		if ((clazz == GuiInventory.class || clazz == GuiContainerCreative.class) && event.getButton().id == 18067)
		{
			AetherNetworkingManager.sendToServer(new PacketOpenContainer(AetherGuiHandler.accessories));
		}
	}

	@SubscribeEvent
	public void onRenderHand(RenderSpecificHandEvent event)
	{
		PlayerGloveRenderer.renderItemFirstPerson(Minecraft.getMinecraft().thePlayer, event.getPartialTicks(), event.getInterpolatedPitch(), event.getHand(), event.getSwingProgress(), event.getItemStack(), event.getEquipProgress());
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