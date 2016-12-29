package com.legacy.aether.server.player;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandClearInventory;
import net.minecraft.command.CommandException;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

import com.legacy.aether.server.Aether;
import com.legacy.aether.server.items.ItemsAether;
import com.legacy.aether.server.networking.AetherNetworkingManager;
import com.legacy.aether.server.networking.packets.PacketAchievement;
import com.legacy.aether.server.networking.packets.PacketPerkChanged;
import com.legacy.aether.server.player.capability.PlayerAetherProvider;
import com.legacy.aether.server.player.perks.AetherPerks;
import com.legacy.aether.server.player.perks.util.EnumAetherPerkType;
import com.legacy.aether.server.registry.achievements.AchievementsAether;
import com.legacy.aether.server.registry.objects.AetherAchievement;

public class PlayerAetherEvents
{

	private static final ResourceLocation PLAYER_LOCATION = new ResourceLocation(Aether.modid, "aether_players");

	@SubscribeEvent
	@SuppressWarnings("deprecation")
	public void PlayerConstructingEvent(AttachCapabilitiesEvent.Entity event)
	{
		if ((event.getEntity() instanceof EntityPlayer))
		{
			EntityPlayer player = (EntityPlayer) event.getEntity();
			PlayerAetherProvider provider = new PlayerAetherProvider(new PlayerAether(player));

			if (PlayerAether.get(player) == null)
			{
				event.addCapability(PLAYER_LOCATION,  provider);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerCloned(Clone event)
	{
		PlayerAether original = PlayerAether.get(event.getOriginal());

		PlayerAether newPlayer = PlayerAether.get(event.getEntityPlayer());

		NBTTagCompound data = new NBTTagCompound();

		if (original != null)
		{
			original.saveNBTData(data);
			
			if (newPlayer != null)
			{
				newPlayer.loadNBTData(data);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent event)
	{
		if ((event.getEntity() instanceof EntityPlayer))
		{
			PlayerAether playerAether = PlayerAether.get((EntityPlayer) event.getEntity());

			if (playerAether != null)
			{
				playerAether.onPlayerDeath();
			}
		}
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		PlayerAether playerAether = PlayerAether.get(event.player);

		if (playerAether != null)
		{
			playerAether.onPlayerRespawn();
		}
	}

	@SubscribeEvent
	public void onPlayerUpdate(LivingUpdateEvent event)
	{
		if ((event.getEntityLiving() instanceof EntityPlayer))
		{
			PlayerAether playerAether = PlayerAether.get((EntityPlayer) event.getEntityLiving());

			if (playerAether != null)
			{
				playerAether.onUpdate();
			}
		}
	}

	@SubscribeEvent
	public void onLivingAttack(LivingAttackEvent event)
	{
		if (event.getEntityLiving() instanceof EntityPlayer)
		{
			PlayerAether playerAether = PlayerAether.get((EntityPlayer) event.getEntityLiving());

			if (playerAether != null)
			{
				event.setCanceled(playerAether.onPlayerAttacked(event.getSource()));
			}
		}
	}

	@SubscribeEvent
	public void onChangedDimension(PlayerChangedDimensionEvent event)
	{
		PlayerAether playerAether = PlayerAether.get(event.player);
		
		if (playerAether != null)
		{
			playerAether.onChangedDimension(event.toDim, event.fromDim);
		}
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		EntityPlayer player = event.player;
		PlayerAether playerAether = PlayerAether.get(player);

		if (playerAether != null)
		{
			playerAether.accessories.markDirty();

			boolean isDonator = AetherPerks.isDonator(player.getUniqueID());

			playerAether.setDonator(isDonator);

			AetherNetworkingManager.sendTo(new PacketPerkChanged(player.getEntityId(), EnumAetherPerkType.Information, isDonator), (EntityPlayerMP) player);
		}
	}

	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event)
	{
		if (event.getEntityLiving() instanceof EntityPlayer)
		{
			PlayerAether playerAether = PlayerAether.get((EntityPlayer) event.getEntityLiving());

			if (playerAether != null && playerAether.isWearingObsidianSet())
			{
				float original = event.getAmount();
				event.setAmount(original / 2);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerStrVsBlock(BreakSpeed event)
	{
		PlayerAether playerAether = PlayerAether.get(event.getEntityPlayer());

		if (playerAether != null)
		{
			event.setNewSpeed(playerAether.getCurrentPlayerStrVsBlock(event.getOriginalSpeed()));
		}
	}

	@SubscribeEvent
	public void onCommandSentEvent(CommandEvent event) throws CommandException
	{
		if (event.getCommand() instanceof CommandClearInventory)
		{
			if (event.getParameters().length <= 1)
			{
		        EntityPlayerMP entityplayermp = event.getParameters().length == 0 ? CommandBase.getCommandSenderAsPlayer(event.getSender()) : CommandBase.getPlayer(FMLCommonHandler.instance().getMinecraftServerInstance(), event.getSender(), event.getParameters()[0]);
				PlayerAether playerAether = PlayerAether.get(entityplayermp);

				if (playerAether != null)
				{
					if (playerAether.accessories.getFieldCount() != 0)
					{
						playerAether.accessories.clear();

						CommandBase.notifyCommandListener(entityplayermp, event.getCommand(), "Cleared the accessories of " + entityplayermp.getName(), new Object[] {});
					}
				}
			}
		}
	}

	@SubscribeEvent
	public void onAchievementGet(AchievementEvent event)
	{
		if (!event.getEntityPlayer().hasAchievement(event.getAchievement()) && event.getAchievement() instanceof AetherAchievement)
		{
			int achievementType = event.getAchievement() == AchievementsAether.defeat_bronze ? 1 : event.getAchievement() == AchievementsAether.defeat_silver ? 2 : 0;
			EntityPlayer player = event.getEntityPlayer();

			if (event.getAchievement() == AchievementsAether.enter_aether)
			{
				if (!player.inventory.addItemStackToInventory(new ItemStack(ItemsAether.lore_book)))
				{
					player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, new ItemStack(ItemsAether.lore_book)));
				}

				if (!player.inventory.addItemStackToInventory(new ItemStack(ItemsAether.golden_parachute)))
				{
					player.worldObj.spawnEntityInWorld(new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, new ItemStack(ItemsAether.golden_parachute)));
				}
			}

			if (!event.getEntityPlayer().worldObj.isRemote)
			{
				AetherNetworkingManager.sendTo(new PacketAchievement(achievementType), (EntityPlayerMP) player);
			}
		}
	}

}