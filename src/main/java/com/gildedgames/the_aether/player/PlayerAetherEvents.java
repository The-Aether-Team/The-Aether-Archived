package com.gildedgames.the_aether.player;

import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.advancements.AetherAdvancements;
import com.gildedgames.the_aether.enchantment.AetherEnchantmentHelper;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandClearInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.entity.player.PlayerEvent.Visibility;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.api.player.IPlayerAetherProvider;
import com.gildedgames.the_aether.items.ItemsAether;

public class PlayerAetherEvents
{

	private static final ResourceLocation PLAYER_LOCATION = new ResourceLocation(Aether.modid, "aether_players");

	@SubscribeEvent
	public void PlayerConstructingEvent(AttachCapabilitiesEvent<Entity> event)
	{
		if (event.getObject() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getObject();
			IPlayerAetherProvider provider = new IPlayerAetherProvider(new PlayerAether(player));

			if (AetherAPI.getInstance().get(player) == null)
			{
				event.addCapability(PLAYER_LOCATION,  provider);
			}
		}
	}

	@SubscribeEvent
	public void checkPlayerVisibility(Visibility event)
	{
		IPlayerAether playerAether = AetherAPI.getInstance().get(event.getEntityPlayer());

		if (playerAether.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.invisibility_cape)))
		{
			event.modifyVisibility(0.0D);
		}
	}

	@SubscribeEvent
	public void onPlayerCloned(Clone event)
	{
		IPlayerAether original = AetherAPI.getInstance().get(event.getOriginal());

		IPlayerAether newPlayer = AetherAPI.getInstance().get(event.getEntityPlayer());

		NBTTagCompound data = new NBTTagCompound();

		if (original instanceof PlayerAether)
		{
			original.saveNBTData(data);
			
			if (newPlayer instanceof PlayerAether)
			{
				newPlayer.loadNBTData(data);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerPickupXp(PlayerPickupXpEvent event)
	{
		ItemStack itemstack = AetherEnchantmentHelper.getEnchantedAccessory(Enchantments.MENDING, AetherAPI.getInstance().get(event.getEntityPlayer()));

        if (!itemstack.isEmpty() && itemstack.isItemDamaged())
        {
            int i = Math.min((event.getOrb().xpValue * 2), itemstack.getItemDamage());

            event.getOrb().xpValue -= (i / 2);

            itemstack.setItemDamage(itemstack.getItemDamage() - i);
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
			IPlayerAether playerAether = AetherAPI.getInstance().get((EntityPlayer) event.getEntity());

			if (playerAether != null)
			{
				((PlayerAether) playerAether).onPlayerDeath();
			}
		}
	}

	@SubscribeEvent
	public void onPlayerDrops(LivingDropsEvent event)
	{
		if (event.getEntity() instanceof EntityPlayer)
		{
			IPlayerAether playerAether = AetherAPI.getInstance().get((EntityPlayer) event.getEntity());

			if (playerAether != null)
			{
				((PlayerAether) playerAether).dropAccessories();
			}
		}
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		IPlayerAether playerAether = AetherAPI.getInstance().get(event.player);

		if (playerAether != null)
		{
			((PlayerAether) playerAether).onPlayerRespawn();
		}
	}

	@SubscribeEvent
	public void onPlayerUpdate(LivingUpdateEvent event)
	{
		if ((event.getEntityLiving() instanceof EntityPlayer))
		{
			IPlayerAether playerAether = AetherAPI.getInstance().get((EntityPlayer) event.getEntityLiving());

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
			IPlayerAether playerAether = AetherAPI.getInstance().get((EntityPlayer) event.getEntityLiving());

			if (playerAether != null)
			{
				event.setCanceled(((PlayerAether) playerAether).onPlayerAttacked(event.getSource()));
			}
		}
	}

	@SubscribeEvent
	public void onChangedDimension(PlayerChangedDimensionEvent event)
	{
		IPlayerAether playerAether = AetherAPI.getInstance().get(event.player);
		
		if (playerAether != null)
		{
			((PlayerAether) playerAether).onChangedDimension(event.toDim, event.fromDim);
		}
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event)
	{
		EntityPlayer player = event.player;
		IPlayerAether playerAether = AetherAPI.getInstance().get(player);

		if (playerAether != null)
		{
			playerAether.getAccessoryInventory().markDirty();
			((PlayerAether) playerAether).updateAccessories();

			if (!AetherConfig.gameplay_changes.aether_start)
			{
				((PlayerAether) playerAether).shouldGetPortal = false;
			}
			else
			{
				((PlayerAether) playerAether).givePortalFrame();
			}
		}
	}

	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event)
	{
		if (event.getEntityLiving() instanceof EntityPlayer)
		{
			IPlayerAether playerAether = AetherAPI.getInstance().get((EntityPlayer) event.getEntityLiving());

			if (playerAether.getAccessoryInventory().isWearingObsidianSet())
			{
				float original = event.getAmount();

				event.setAmount(original / 2);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerStrVsBlock(BreakSpeed event)
	{
		IPlayerAether playerAether = AetherAPI.getInstance().get(event.getEntityPlayer());

		if (playerAether != null)
		{
			event.setNewSpeed(((PlayerAether) playerAether).getCurrentPlayerStrVsBlock(event.getNewSpeed()));
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

				IPlayerAether playerAether = AetherAPI.getInstance().get(entityplayermp);

				if (playerAether != null)
				{
					if (playerAether.getAccessoryInventory().getFieldCount() != 0)
					{
						playerAether.getAccessoryInventory().clear();

						CommandBase.notifyCommandListener(entityplayermp, event.getCommand(), "Cleared the accessories of " + entityplayermp.getName(), new Object[] {});
					}
				}
			}
		}
	}
}