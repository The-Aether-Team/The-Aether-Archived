package com.legacy.aether.player;

import net.minecraft.advancements.Advancement;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.networking.AetherNetworkingManager;
import com.legacy.aether.networking.packets.PacketAchievement;

public class AetherAdvancementHandler
{

	@SubscribeEvent
	public void onAchievementGet(AdvancementEvent event)
	{
		Advancement advancement = event.getAdvancement();

		if (advancement.getDisplay() != null && advancement.getDisplay().getIcon().getItem() != null && advancement.getId().getResourceDomain().equals("aether_legacy"))
		{
			ItemStack stack = advancement.getDisplay().getIcon();
			EntityPlayer player = event.getEntityPlayer();

			int achievementType = 0;

			/*
			 * Hostile Paradise
			 */
			if (stack.getItem() == ItemsAether.dungeon_key)
			{
				achievementType = (stack.getMetadata() == 0 ? 1 : stack.getMetadata() == 1 ? 2 : 0);

				if (achievementType == 0)
				{
					achievementType = -1;
				}
			}

			if (!player.world.isRemote)
			{
				boolean sendJingle = stack.getItem().getRegistryName().getResourceDomain().equals("aether_legacy");

				if (advancement.getDisplay().getDescription().getFormattedText().contains("Hostile Paradise"))
				{
					if (!player.inventory.addItemStackToInventory(new ItemStack(ItemsAether.lore_book)))
					{
						player.world.spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, new ItemStack(ItemsAether.lore_book)));
					}

					if (!player.inventory.addItemStackToInventory(new ItemStack(ItemsAether.golden_parachute)))
					{
						player.world.spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, new ItemStack(ItemsAether.golden_parachute)));
					}

					sendJingle = true;
				}

				sendJingle = (achievementType != -1);
				
				if (sendJingle)
				{
					AetherNetworkingManager.sendTo(new PacketAchievement(achievementType), (EntityPlayerMP) player);
				}
			}
		}
	}
}