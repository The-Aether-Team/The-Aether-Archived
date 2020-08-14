package com.gildedgames.the_aether.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.gildedgames.the_aether.containers.inventory.InventoryAccessories;
import com.gildedgames.the_aether.entities.passive.mountable.EntityParachute;
import com.gildedgames.the_aether.networking.AetherNetworkingManager;
import com.gildedgames.the_aether.networking.packets.*;
import com.gildedgames.the_aether.player.abilities.AbilityAccessories;
import com.gildedgames.the_aether.player.abilities.AbilityArmor;
import com.gildedgames.the_aether.player.abilities.AbilityFlight;
import com.gildedgames.the_aether.player.abilities.AbilityRepulsion;
import com.gildedgames.the_aether.player.perks.AetherRankings;
import com.gildedgames.the_aether.player.perks.util.DonatorMoaSkin;
import com.gildedgames.the_aether.player.perks.util.EnumAetherPerkType;
import com.gildedgames.the_aether.networking.packets.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.FMLCommonHandler;

import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.api.player.util.IAccessoryInventory;
import com.gildedgames.the_aether.api.player.util.IAetherAbility;
import com.gildedgames.the_aether.api.player.util.IAetherBoss;
import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.world.TeleporterAether;

public class PlayerAether implements IPlayerAether
{

	public EntityPlayer thePlayer;

	private UUID healthUUID = UUID.fromString("df6eabe7-6947-4a56-9099-002f90370706");

	private UUID extendedReachUUID = UUID.fromString("df6eabe7-6947-4a56-9099-002f90370707");

	private AttributeModifier healthModifier, reachModifier;

	public IAccessoryInventory accessories;

	public float wingSinage;

	public IAetherBoss currentBoss;

	private final ArrayList<IAetherAbility> abilities = new ArrayList<IAetherAbility>();

	public final ArrayList<Entity> clouds = new ArrayList<Entity>(2);

	private boolean isJumping;

	public int lifeShardsUsed;

	public float prevPortalAnimTime, portalAnimTime;

	public int timeInPortal;

	public boolean hasTeleported = false, inPortal = false, shouldPlayPortalSound = false;

	private String cooldownName = "Hammer of Notch";

	private int cooldown, cooldownMax;

	public boolean shouldRenderHalo, shouldRenderGlow, shouldRenderCape, shouldRenderGloves;

	public boolean seenSpiritDialog = false;

	public boolean isPoisoned = false, isCured = false;

	public int poisonTime = 0, cureTime = 0;

	public DonatorMoaSkin donatorMoaSkin;
	
	public List<Item> extendedReachItems = Arrays.asList(new Item[] {ItemsAether.valkyrie_shovel, ItemsAether.valkyrie_pickaxe, ItemsAether.valkyrie_axe});

	public PlayerAether() { }

	public PlayerAether(EntityPlayer player)
	{
		this.thePlayer = player;

		this.shouldRenderHalo = true;
		this.shouldRenderGlow = false;
		this.shouldRenderCape = true;
		this.shouldRenderGloves = true;

		this.donatorMoaSkin = new DonatorMoaSkin();
		this.accessories = new InventoryAccessories(player);
		this.reachModifier = new AttributeModifier(this.extendedReachUUID, "Aether Reach Modifier", 3.0D, 0);

		this.abilities.addAll(Arrays.<IAetherAbility>asList(new AbilityArmor(this), new AbilityAccessories(this), new AbilityFlight(this), new AbilityRepulsion(this)));
	}

	public void onUpdate()
	{
		if (!this.thePlayer.world.isRemote)
		{
			AetherNetworkingManager.sendToAll(new PacketPerkChanged(this.getEntity().getEntityId(), EnumAetherPerkType.Halo, this.shouldRenderHalo));
			AetherNetworkingManager.sendToAll(new PacketPerkChanged(this.getEntity().getEntityId(), EnumAetherPerkType.Glow, this.shouldRenderGlow));
			AetherNetworkingManager.sendToAll(new PacketCapeChanged(this.getEntity().getEntityId(), this.shouldRenderCape));
			AetherNetworkingManager.sendToAll(new PacketGlovesChanged(this.getEntity().getEntityId(), this.shouldRenderGloves));
			AetherNetworkingManager.sendToAll(new PacketSendPoisonTime(this.getEntity(), this.poisonTime));
			AetherNetworkingManager.sendToAll(new PacketSendSeenDialogue(this.getEntity(), this.seenSpiritDialog));
		}

		if (this.isPoisoned)
		{
			if (poisonTime > 0)
			{
				this.poisonTime--;
			}
			else
			{
				this.poisonTime = 0;
				this.isPoisoned = false;
			}
		}

		if (this.isCured)
		{
			if (cureTime > 0)
			{
				this.cureTime--;
			}
			else
			{
				this.cureTime = 0;
				this.isCured = false;
			}
		}

		if (this.inPortal && this.thePlayer.world.isRemote)
		{
			if (this.portalAnimTime == 0.0F)
			{
				this.shouldPlayPortalSound = true;
			}
		}

		for (int i = 0; i < this.abilities.size(); ++i)
		{
			IAetherAbility ability = this.abilities.get(i);

			if (ability.shouldExecute())
			{
				ability.onUpdate();
			}
		}

		for (int i = 0; i < this.clouds.size(); ++i)
		{
			Entity entity = this.clouds.get(i);

			if (entity.isDead)
			{
				this.clouds.remove(i);
			}
		}

		if (!this.thePlayer.onGround && (this.thePlayer.isRiding() && !this.thePlayer.getRidingEntity().onGround))
		{
			this.wingSinage += 0.75F;
		}
		else
		{
			this.wingSinage += 0.15F;
		}

		if (this.wingSinage > 3.141593F * 2F)
		{
			this.wingSinage -= 3.141593F * 2F;
		}
		else
		{
			this.wingSinage += 0.1F;
		}

		if (this.currentBoss instanceof EntityLiving)
		{
			EntityLiving boss = (EntityLiving) this.currentBoss;

			if (boss.getHealth() <= 0 || boss.isDead || Math.sqrt(Math.pow(boss.posX - this.thePlayer.posX, 2) + Math.pow(boss.posY - this.thePlayer.posY, 2) + Math.pow(boss.posZ - this.thePlayer.posZ, 2)) > 50.0D)
			{
				this.currentBoss = null;
			}
		}

		if (this.isInBlock(BlocksAether.aercloud))
		{
			this.thePlayer.fallDistance = 0.0F;
		}

		if (this.getHammerCooldown() > 0)
		{
			this.cooldown -= 1;
		}

		if (this.thePlayer.motionY < -2F)
		{
			this.activateParachute();
		}

		if (this.thePlayer.dimension == AetherConfig.dimension.aether_dimension_id)
		{
			if (this.thePlayer.posY < -2)
			{
				if (!this.thePlayer.isRiding())
				{
					this.teleportPlayer(false);
				}
			}
		}

		this.updatePlayerReach();

		if (this.thePlayer.world.isRemote)
		{
			this.prevPortalAnimTime = this.portalAnimTime;

			if (this.inPortal)
			{
				this.portalAnimTime += 0.0125F;
				this.inPortal = false;
			}
			else
			{
				if (this.portalAnimTime > 0.0F)
				{
					this.portalAnimTime -= 0.05F;
				}

				if (this.portalAnimTime < 0.0F)
				{
					this.portalAnimTime = 0.0F;
				}
			}
		}
		else
		{
			if (this.inPortal)
			{
				MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

				int previousDimension = this.thePlayer.dimension;
				int transferDimension = previousDimension == AetherConfig.dimension.aether_dimension_id ? 0 : AetherConfig.dimension.aether_dimension_id;

				if (this.thePlayer.timeUntilPortal <= 0)
				{
					int limit = this.thePlayer.getMaxInPortalTime();

					if (this.timeInPortal >= limit)
					{
						this.timeInPortal = 0;
						this.thePlayer.timeUntilPortal = this.thePlayer.getPortalCooldown();
						this.thePlayer.changeDimension(transferDimension, new TeleporterAether(true, server.getWorld(transferDimension)));
					}
					else
					{
						this.timeInPortal++;
					}
				}
				else
				{
					this.thePlayer.timeUntilPortal = this.thePlayer.getPortalCooldown();
				}

				this.inPortal = false;
			}
			else
			{
				if (this.timeInPortal < 0)
				{
					this.timeInPortal = 0;
				}

                if (this.timeInPortal > 0)
                {
                    this.timeInPortal -= 4;
                }
			}
		}
	}

	public boolean onPlayerAttacked(DamageSource source)
	{
		if (this.getAccessoryInventory().isWearingPhoenixSet() && source.isFireDamage())
		{
			return true;
		}

		return false;
	}

	public void onPlayerDeath()
	{
		if (!this.thePlayer.world.getGameRules().getBoolean("keepInventory"))
		{
			this.accessories.dropAccessories();
		}

		this.isPoisoned = false;
		this.poisonTime = 0;
		this.isCured = false;
		this.cureTime = 0;
	}

	public void onPlayerRespawn()
	{
		this.updateShardCount(0);

		this.thePlayer.setHealth(this.thePlayer.getMaxHealth());

		this.updateAccessories();
	}

	public void onChangedDimension(int toDim, int fromDim)
	{
		this.updateAccessories();
	}

	public void saveNBTData(NBTTagCompound output) 
	{
		if (AetherRankings.isRankedPlayer(this.thePlayer.getUniqueID()))
		{
			output.setBoolean("halo", this.shouldRenderHalo);
		}

		if (AetherRankings.isDeveloper(this.thePlayer.getUniqueID()))
		{
			output.setBoolean("glow", this.shouldRenderGlow);
		}

		output.setBoolean("poisoned", this.isPoisoned);
		output.setInteger("poison_time", this.poisonTime);
		output.setBoolean("cape", this.shouldRenderCape);
		output.setBoolean("gloves", this.shouldRenderGloves);
		output.setBoolean("seen_spirit_dialog", this.seenSpiritDialog);
		output.setInteger("hammer_cooldown", this.cooldown);
		output.setString("notch_hammer_name", this.cooldownName);
		output.setInteger("max_hammer_cooldown", this.cooldownMax);
		output.setFloat("shard_count", this.lifeShardsUsed);
		this.accessories.writeToNBT(output);
	}

	public void loadNBTData(NBTTagCompound input)
	{
		if (input.hasKey("halo"))
		{
			this.shouldRenderHalo = input.getBoolean("halo");
		}
		
		if (input.hasKey("glow"))
		{
			this.shouldRenderGlow = input.getBoolean("glow");
		}

		if (input.hasKey("cape"))
		{
			this.shouldRenderCape = input.getBoolean("cape");
		}

		if (input.hasKey("gloves"))
		{
			this.shouldRenderGloves = input.getBoolean("gloves");
		}

		if (input.hasKey("shards_used"))
		{
			input.setInteger("shard_count", (int) (input.getFloat("shards_used") / 2));
		}

		if (input.hasKey("seen_spirit_dialog"))
		{
			this.seenSpiritDialog = input.getBoolean("seen_spirit_dialog");
		}

		if (input.hasKey("poisoned"))
		{
			this.isPoisoned = input.getBoolean("poisoned");
		}

		if (input.hasKey("poison_time"))
		{
			this.poisonTime = input.getInteger("poison_time");
		}

		this.cooldown = input.getInteger("hammer_cooldown");
		this.cooldownName = input.getString("notch_hammer_name");
		this.cooldownMax = input.getInteger("max_hammer_cooldown");
		this.updateShardCount(input.getInteger("shard_count"));
		this.accessories.readFromNBT(input);
	}

	/*
	 * Gets the custom speed at the current point in time
	 */
	public float getCurrentPlayerStrVsBlock(float original) 
	{ 
		float f = original;

		if(this.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.zanite_pendant)))
		{
			f *= (1F + ((float)(((InventoryAccessories) this.accessories).getStackFromItem(ItemsAether.zanite_pendant).getItemDamage()) / ((float)(((InventoryAccessories) this.accessories).getStackFromItem(ItemsAether.zanite_pendant).getMaxDamage()) * 3F)));
		}

		if(this.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.zanite_ring)))
		{
			f *= (1F + ((float)(((InventoryAccessories) this.accessories).getStackFromItem(ItemsAether.zanite_ring).getItemDamage()) / ((float)(((InventoryAccessories) this.accessories).getStackFromItem(ItemsAether.zanite_ring).getMaxDamage()) * 3F)));
		}

		return f == original ? original : f + original; 
	}

	/*
	 * Gets the player reach at the current point in time
	 */
	public void updatePlayerReach()
	{
		ItemStack stack = this.thePlayer.getHeldItemMainhand();

		if (!this.thePlayer.getEntityAttribute(EntityPlayer.REACH_DISTANCE).hasModifier(this.reachModifier) && this.extendedReachItems.contains(stack.getItem()))
		{
			this.thePlayer.getEntityAttribute(EntityPlayer.REACH_DISTANCE).applyModifier(this.reachModifier);
		}
		else if (this.thePlayer.getEntityAttribute(EntityPlayer.REACH_DISTANCE).hasModifier(this.reachModifier) && !this.extendedReachItems.contains(stack.getItem()))
		{
			this.thePlayer.getEntityAttribute(EntityPlayer.REACH_DISTANCE).removeModifier(this.reachModifier);
		}
	}

	/*
	 * The teleporter which sends the player to the Aether/Overworld
	 */
	public void teleportPlayer(boolean shouldSpawnPortal)
	{
		if (this.thePlayer instanceof EntityPlayerMP)
		{			
			int previousDimension = this.thePlayer.dimension;
			int transferDimension = previousDimension == AetherConfig.dimension.aether_dimension_id ? 0 : AetherConfig.dimension.aether_dimension_id;

			if (ForgeHooks.onTravelToDimension(this.thePlayer, transferDimension))
			{
				MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
				TeleporterAether teleporter = new TeleporterAether(shouldSpawnPortal, server.getWorld(transferDimension));

				for (Entity passenger : this.thePlayer.getPassengers())
				{
					transferEntity(shouldSpawnPortal, passenger, server.getWorld(previousDimension), server.getWorld(transferDimension));
					passenger.dismountRidingEntity();
				}

				server.getPlayerList().transferPlayerToDimension((EntityPlayerMP) this.thePlayer, transferDimension, teleporter);

				if (this.thePlayer.getRidingEntity() != null)
				{
					this.thePlayer.dismountRidingEntity();
				}
			}
		}
	}

	/*
	 * The teleporter which sends any extra entities to the Aether/Overworld
	 */
	private static void transferEntity(boolean shouldSpawnPortal, Entity entityIn, WorldServer previousWorldIn, WorldServer newWorldIn)
	{
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

		entityIn.dimension = newWorldIn.provider.getDimension();
		previousWorldIn.removeEntityDangerously(entityIn);
		entityIn.isDead = false;

		server.getPlayerList().transferEntityToWorld(entityIn, previousWorldIn.provider.getDimension(), previousWorldIn, newWorldIn, new TeleporterAether(shouldSpawnPortal, newWorldIn));
	}

	private void activateParachute()
	{
		if (!this.thePlayer.capabilities.isCreativeMode)
		{
			EntityParachute parachute = null;

			ItemStack itemstack = null;

			for (int i = 0; i < this.thePlayer.inventory.getSizeInventory(); i++)
			{
				ItemStack stackInSlot = this.thePlayer.inventory.getStackInSlot(i);

				if(stackInSlot.getItem() == ItemsAether.cloud_parachute)
				{
					itemstack = stackInSlot;
					break;
				}
				else
				{
					if (stackInSlot.getItem() == ItemsAether.golden_parachute)
					{
						itemstack = stackInSlot;
						break;
					}
				}
			}

			if (itemstack != null)
			{
				if (itemstack.getItem() == ItemsAether.cloud_parachute)
				{
					parachute = new EntityParachute(this.thePlayer.world, this.thePlayer, false);
					parachute.setPosition(this.thePlayer.posX, this.thePlayer.posY, this.thePlayer.posZ);
					this.thePlayer.world.spawnEntity(parachute);
					this.thePlayer.inventory.deleteStack(itemstack);
				}
				else
				{
					if (itemstack.getItem() == ItemsAether.golden_parachute)
					{
						itemstack.damageItem(1, this.thePlayer);
						parachute = new EntityParachute(this.thePlayer.world, this.thePlayer, true);
						parachute.setPosition(this.thePlayer.posX, this.thePlayer.posY, this.thePlayer.posZ);
						this.thePlayer.world.spawnEntity(parachute);
					}
				}
			}
		}
	}

	/*
	 * A checker to see if a player is inside a block or not
	 */
	public boolean isInBlock(Block blockID)
	{
		int x = MathHelper.floor(this.thePlayer.posX);
		int y = MathHelper.floor(this.thePlayer.posY);
		int z = MathHelper.floor(this.thePlayer.posZ);
		BlockPos pos = new BlockPos(x, y, z);

		return this.thePlayer.world.getBlockState(pos).getBlock() == blockID || this.thePlayer.world.getBlockState(pos.up()).getBlock() == blockID || this.thePlayer.world.getBlockState(pos.down()).getBlock() == blockID;
	}

	/*
	 * Increases the maximum amount of HP (Caps at 10)
	 */
	@Override
	public void updateShardCount(int amount)
	{
		this.lifeShardsUsed += amount;
		this.healthModifier = new AttributeModifier(this.healthUUID, "Aether Health Modifier", (this.lifeShardsUsed * 2.0F), 0);

		if (this.thePlayer.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getModifier(this.healthUUID) != null)
		{
			this.thePlayer.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(this.healthModifier);
		}

		this.thePlayer.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(this.healthModifier);
	}

	/*
	 * Instance of the current shards the player has used
	 */
	@Override
	public int getShardsUsed()
	{
		return this.lifeShardsUsed;
	}

	/*
	 * Instance of the maximum shards the player can use
	 */
	@Override
	public int getMaxShardCount()
	{
		return AetherConfig.gameplay_changes.max_life_shards;
	}

	/*
	 * Sets the boss the player is fighting
	 */
	@Override
	public void setFocusedBoss(IAetherBoss boss)
	{
		this.currentBoss = boss;
	}

	/*
	 * Instance of the boss the player is fighting
	 */
	@Override
	public IAetherBoss getFocusedBoss()
	{
		return this.currentBoss;
	}

	/*
	 * Sets the cooldown and name of the players hammer
	 */
	@Override
	public boolean setHammerCooldown(int cooldown, String hammerName)
	{
		if (this.cooldown == 0)
		{
			this.cooldown = cooldown;
			this.cooldownMax = cooldown;
			this.cooldownName = hammerName;

			return true;
		}

		return false;
	}

	@Override
	public void shouldPortalSound(boolean playSound)
	{
		this.shouldPlayPortalSound = playSound;
	}

	@Override
	public boolean shouldPortalSound()
	{
		return this.shouldPlayPortalSound;
	}

	/*
	 * The name of the players hammer
	 */
	@Override
	public String getHammerName()
	{
		return this.cooldownName;
	}

	/*
	 * Sets the cooldown of the players hammer
	 */
	@Override
	public int getHammerCooldown()
	{
		return this.cooldown;
	}

	/*
	 * The max cooldown of the players hammer
	 */
	@Override
	public int getHammerMaxCooldown()
	{
		return this.cooldownMax;
	}

	/*
	 * Checks if the player is jumping or not
	 */
	@Override
	public boolean isJumping()
	{
		return this.isJumping;
	}

	/*
	 * Sets if the player is jumping or not
	 */
	@Override
	public void setJumping(boolean isJumping)
	{
		this.isJumping = isJumping;
	}

	/*
	 * Checks if the player is a donator or not
	 */
	public boolean isDonator()
	{
		return true;
	}

	public void setInPortal()
	{
		this.inPortal = true;
	}

	/*
	 * Updates Player accessories
	 */
	public void updateAccessories()
	{
		if (!this.thePlayer.world.isRemote)
		{
			AetherNetworkingManager.sendToAll(new PacketAccessory(this));
		}
	}

	@Override
	public void setAccessoryInventory(IAccessoryInventory inventory)
	{
		this.accessories = inventory;
	}

	@Override
	public IAccessoryInventory getAccessoryInventory()
	{
		return this.accessories;
	}

	@Override
	public ArrayList<IAetherAbility> getAbilities()
	{
		return this.abilities;
	}

	@Override
	public EntityPlayer getEntity()
	{
		return this.thePlayer;
	}

	@Override
	public boolean inPortalBlock()
	{
		return this.inPortal;
	}

	public boolean isPoisoned()
	{
		return this.isPoisoned;
	}

	public void setPoisoned()
	{
		this.isPoisoned = true;
		this.poisonTime = 500;
	}

	public boolean isCured()
	{
		return this.isCured;
	}

	public void setCured(int time)
	{
		this.isCured = true;
		this.cureTime = time;

		this.isPoisoned = false;
		this.poisonTime = 0;
	}
}