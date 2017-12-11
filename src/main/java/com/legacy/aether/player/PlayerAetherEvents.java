package com.legacy.aether.player;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandClearInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.entity.player.PlayerEvent.Visibility;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

import com.legacy.aether.Aether;
import com.legacy.aether.advancements.AetherAdvancements;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.player.capability.PlayerAetherProvider;

public class PlayerAetherEvents
{

	private static final ResourceLocation PLAYER_LOCATION = new ResourceLocation(Aether.modid, "aether_players");

	@SubscribeEvent
	public void PlayerConstructingEvent(AttachCapabilitiesEvent<Entity> event)
	{
		if ((event.getObject() instanceof EntityPlayer))
		{
			EntityPlayer player = (EntityPlayer) event.getObject();
			PlayerAetherProvider provider = new PlayerAetherProvider(new PlayerAether(player));

			if (PlayerAether.get(player) == null)
			{
				event.addCapability(PLAYER_LOCATION,  provider);
			}
		}
	}

	@SubscribeEvent
	public void checkPlayerVisibility(Visibility event)
	{
		PlayerAether capability = PlayerAether.get(event.getEntityPlayer());

		if (capability != null && capability.wearingAccessory(ItemsAether.invisibility_cape))
		{
			event.modifyVisibility(0.0D);
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
				newPlayer.portalCooldown = original.portalCooldown;
				newPlayer.loadNBTData(data);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerMount(EntityMountEvent event)
	{
		if (event.getEntityMounting() instanceof EntityPlayerMP)
		{
			AetherAdvancements.MOUNT_TRIGGER.trigger(((EntityPlayerMP)event.getEntityMounting()), event.getEntityBeingMounted());
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
	public void onCommandSentEvent(CommandEvent event)
	{
		if (event.getCommand() instanceof CommandClearInventory)
		{
			if (event.getParameters().length <= 1)
			{
		        EntityPlayerMP entityplayermp = null;

				try
				{
					entityplayermp = event.getParameters().length == 0 ? CommandBase.getCommandSenderAsPlayer(event.getSender()) : CommandBase.getPlayer(FMLCommonHandler.instance().getMinecraftServerInstance(), event.getSender(), event.getParameters()[0]);
				} 
		        catch (Throwable var9)
		        {
		            return;
		        }

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

	/*@SubscribeEvent
	public void onAchievementGet(AchievementEvent event)
	{
		Achievement achievement = event.getAchievement();
		EntityPlayer player = event.getEntityPlayer();

		if (!(achievement instanceof AetherAchievement))
		{
			return;
		}

		int achievementType = achievement == AchievementsAether.defeat_bronze ? 1 : achievement == AchievementsAether.defeat_silver ? 2 : 0;

		if (!player.world.isRemote && ((EntityPlayerMP)player).getStatFile().canUnlockAchievement(achievement) && !player.hasAchievement(achievement))
		{
			if (event.getAchievement() == AchievementsAether.enter_aether)
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
	}*/

}