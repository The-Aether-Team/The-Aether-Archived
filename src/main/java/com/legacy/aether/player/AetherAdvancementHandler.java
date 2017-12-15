package com.legacy.aether.player;

import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.networking.AetherNetworkingManager;
import com.legacy.aether.networking.packets.PacketAchievement;

import net.minecraft.advancements.Advancement;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AetherAdvancementHandler
{
	@SubscribeEvent
	public void onAchievementGet(AdvancementEvent event)
	{
		Advancement advancement = event.getAdvancement();
		EntityPlayer player = event.getEntityPlayer();

		String unlocalizedName = advancement.getDisplayText().getUnformattedText();

		if (!unlocalizedName.contains("aether_legacy"))
		{
			return;
		}

		int achievementType = unlocalizedName.contains("bronze_dungeon") ? 1 : unlocalizedName.contains("silver_dungeon") ? 2 : 0;

		if (!player.world.isRemote)
		{
			if (unlocalizedName.contains("enter_aether"))
			{
				if (!player.inventory.addItemStackToInventory(new ItemStack(ItemsAether.lore_book)))
				{
					player.world.spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, new ItemStack(ItemsAether.lore_book)));
				}

				if (!player.inventory.addItemStackToInventory(new ItemStack(ItemsAether.golden_parachute)))
				{
					player.world.spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, new ItemStack(ItemsAether.golden_parachute)));
				}
			}

			AetherNetworkingManager.sendTo(new PacketAchievement(achievementType), (EntityPlayerMP) player);
		}
	}
}
