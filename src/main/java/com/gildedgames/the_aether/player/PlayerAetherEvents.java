package com.gildedgames.the_aether.player;

import com.gildedgames.the_aether.inventory.InventoryAccessories;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.registry.achievements.AchievementsAether;
import com.gildedgames.the_aether.registry.achievements.AetherAchievement;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;

import com.gildedgames.the_aether.entities.util.EntityHook;
import com.gildedgames.the_aether.network.AetherNetwork;
import com.gildedgames.the_aether.network.packets.PacketAccessory;
import com.gildedgames.the_aether.network.packets.PacketAchievement;
import com.gildedgames.the_aether.player.abilities.AbilityRepulsion;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class PlayerAetherEvents {

	@SubscribeEvent
	public void onPlayerAetherConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer) {
			event.entity.registerExtendedProperties("aether_legacy:player_aether", new PlayerAether());
		} else if (event.entity instanceof EntityLivingBase) {
			event.entity.registerExtendedProperties("aether_legacy:entity_hook", new EntityHook());
		}
	}

	@SubscribeEvent
	public void onPlayerAetherLoggedIn(PlayerLoggedInEvent event) {
		if (!event.player.worldObj.isRemote) {
			PlayerAether playerAether = PlayerAether.get(event.player);

			AetherNetwork.sendTo(new PacketAccessory(playerAether), (EntityPlayerMP) event.player);
			playerAether.updateShardCount(playerAether.getShardsUsed());
		}
	}

	@SubscribeEvent
	public void onPlayerAetherClone(Clone event) {
		PlayerAether original = PlayerAether.get(event.original);
		PlayerAether playerAether = PlayerAether.get(event.entityPlayer);

		playerAether.updateShardCount(original.getShardsUsed());

		if (!event.wasDeath || event.entityPlayer.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
			playerAether.setAccessoryInventory(original.getAccessoryInventory());
		}

		if (event.wasDeath)
		{
			playerAether.setBedLocation(original.getBedLocation());
		}
	}

	@SubscribeEvent
	public void onPlayerAetherRespawn(PlayerRespawnEvent event) {
		if (!event.player.worldObj.isRemote) {
			PlayerAether playerAether = PlayerAether.get(event.player);

			AetherNetwork.sendTo(new PacketAccessory(playerAether), (EntityPlayerMP) event.player);
			playerAether.updateShardCount(playerAether.getShardsUsed());

			playerAether.isPoisoned = false;
			playerAether.poisonTime = 0;
			playerAether.isCured = false;
			playerAether.cureTime = 0;
		}
	}

	@SubscribeEvent
	public void onPlayerAetherChangedDimension(PlayerChangedDimensionEvent event) {
		if (!event.player.worldObj.isRemote) {
			PlayerAether playerAether = PlayerAether.get(event.player);

			AetherNetwork.sendTo(new PacketAccessory(playerAether), (EntityPlayerMP) event.player);
			playerAether.updateShardCount(playerAether.getShardsUsed());
		}
	}

	@SubscribeEvent
	public void onPlayerAetherDeath(LivingDeathEvent event) {
		if (event.entityLiving instanceof EntityPlayer && !event.entityLiving.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
			PlayerAether.get((EntityPlayer) event.entityLiving).getAccessoryInventory().dropAccessories();
		}
	}

	@SubscribeEvent
	public void onPlayerAetherUpdate(LivingUpdateEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			PlayerAether.get((EntityPlayer) event.entityLiving).onUpdate();
			;
		} else if (event.entityLiving instanceof EntityLivingBase) {
			((EntityHook) event.entityLiving.getExtendedProperties("aether_legacy:entity_hook")).onUpdate();
		}
	}

	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			PlayerAether playerAether = PlayerAether.get((EntityPlayer) event.entityLiving);

			if (playerAether.getAccessoryInventory().isWearingObsidianSet()) {
				float original = event.ammount;

				event.ammount = original / 2;
			}
		}
	}

	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			PlayerAether playerAether = PlayerAether.get((EntityPlayer) event.entityLiving);

			if (playerAether.getAccessoryInventory().isWearingPhoenixSet() && event.source.isFireDamage()) {
				event.setCanceled(true);
			} else if (playerAether.getAbilities().get(3).shouldExecute()) {
				event.setCanceled(((AbilityRepulsion) playerAether.getAbilities().get(3)).onPlayerAttacked(event.source));
			}
		}
	}

	@SubscribeEvent
	public void onUpdateBreakSpeed(BreakSpeed event) {
		((InventoryAccessories) PlayerAether.get(event.entityPlayer).getAccessoryInventory()).getCurrentPlayerStrVsBlock(event.newSpeed);
	}

	@SubscribeEvent
	public void onAchievementGet(AchievementEvent event) {
		Achievement achievement = event.achievement;
		EntityPlayer player = event.entityPlayer;

		if (!(achievement instanceof AetherAchievement)) {
			return;
		}

		int achievementType = achievement == AchievementsAether.defeat_bronze ? 1 : achievement == AchievementsAether.defeat_silver ? 2 : 0;

		if (!player.worldObj.isRemote && ((EntityPlayerMP) player).func_147099_x().canUnlockAchievement(achievement) && !((EntityPlayerMP) player).func_147099_x().hasAchievementUnlocked(achievement)) {
			if (event.achievement == AchievementsAether.enter_aether) {
				if (!player.inventory.addItemStackToInventory(new ItemStack(ItemsAether.lore_book))) {
					player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, new ItemStack(ItemsAether.lore_book)));
				}

				if (!player.inventory.addItemStackToInventory(new ItemStack(ItemsAether.golden_parachute))) {
					player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, new ItemStack(ItemsAether.golden_parachute)));
				}
			}

			AetherNetwork.sendTo(new PacketAchievement(achievementType), (EntityPlayerMP) player);
		}
	}

}