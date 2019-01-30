package com.legacy.aether.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

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

import com.legacy.aether.AetherConfig;
import com.legacy.aether.api.player.IPlayerAether;
import com.legacy.aether.api.player.util.IAccessoryInventory;
import com.legacy.aether.api.player.util.IAetherAbility;
import com.legacy.aether.api.player.util.IAetherBoss;
import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.containers.inventory.InventoryAccessories;
import com.legacy.aether.entities.movement.AetherPoisonMovement;
import com.legacy.aether.entities.passive.mountable.EntityParachute;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.networking.AetherNetworkingManager;
import com.legacy.aether.networking.packets.PacketAccessory;
import com.legacy.aether.player.abilities.AbilityAccessories;
import com.legacy.aether.player.abilities.AbilityArmor;
import com.legacy.aether.player.abilities.AbilityFlight;
import com.legacy.aether.player.abilities.AbilityRepulsion;
import com.legacy.aether.player.perks.AetherRankings;
import com.legacy.aether.player.perks.util.DonatorMoaSkin;
import com.legacy.aether.world.TeleporterAether;

public class PlayerAether implements IPlayerAether
{

	public EntityPlayer thePlayer;

	private UUID healthUUID = UUID.fromString("df6eabe7-6947-4a56-9099-002f90370706");

	private UUID extendedReachUUID = UUID.fromString("df6eabe7-6947-4a56-9099-002f90370707");

	private AttributeModifier healthModifier, reachModifier;

	public IAccessoryInventory accessories;

	private AetherPoisonMovement poison;

	public float wingSinage;

	public IAetherBoss currentBoss;

	private final ArrayList<IAetherAbility> abilities = new ArrayList<IAetherAbility>();

	public final ArrayList<Entity> clouds = new ArrayList<Entity>(2);

	private boolean isJumping;

	public int lifeShardsUsed;

	public float prevPortalAnimTime, portalAnimTime;

	public int timeInPortal, portalCooldown;

	public boolean hasTeleported = false, inPortal = false, shouldPlayPortalSound = false;

	private String cooldownName = "Hammer of Notch";

	private int cooldown, cooldownMax;

	public boolean shouldRenderHalo = true;

	public DonatorMoaSkin donatorMoaSkin;
	
	public List<Item> extendedReachItems = Arrays.asList(new Item[] {ItemsAether.valkyrie_shovel, ItemsAether.valkyrie_pickaxe, ItemsAether.valkyrie_axe});

	public PlayerAether() { }

	public PlayerAether(EntityPlayer player)
	{
		this.thePlayer = player;

		this.donatorMoaSkin = new DonatorMoaSkin();
		this.poison = new AetherPoisonMovement(player);
		this.accessories = new InventoryAccessories(player);
		this.reachModifier = new AttributeModifier(this.extendedReachUUID, "Aether Reach Modifier", 5.0D, 0);

		this.abilities.addAll(Arrays.<IAetherAbility>asList(new AbilityArmor(this), new AbilityAccessories(this), new AbilityFlight(this), new AbilityRepulsion(this)));
	}

	public void onUpdate()
	{
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

		this.poison.onUpdate();

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
				this.teleportPlayer(false);
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
				int limit = this.thePlayer.getMaxInPortalTime();

				if (this.timeInPortal++ >= limit)
				{
					this.timeInPortal = limit;
					this.portalCooldown = this.thePlayer.getPortalCooldown();
					this.teleportPlayer(true);
				}

				this.inPortal = false;
			}
			else
			{
				
                if (this.timeInPortal > 0)
                {
                    this.timeInPortal -= 4;
                }

                if (this.timeInPortal < 0)
                {
                    this.timeInPortal = 0;
                }

                if (this.portalCooldown > 0)
                {
                    --this.portalCooldown;
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

		if (input.hasKey("shards_used"))
		{
			input.setInteger("shard_count", (int) (input.getFloat("shards_used") / 2));
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
	private void teleportPlayer(boolean shouldSpawnPortal) 
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
					//transferEntity(shouldSpawnPortal, this.thePlayer.getRidingEntity(), server.getWorld(previousDimension), server.getWorld(transferDimension));
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
		EntityParachute parachute = null;

		if(this.thePlayer.inventory.hasItemStack(new ItemStack(ItemsAether.cloud_parachute)))
		{
			parachute = new EntityParachute(this.thePlayer.world, this.thePlayer, false);
			parachute.setPosition(this.thePlayer.posX, this.thePlayer.posY, this.thePlayer.posZ);
			this.thePlayer.world.spawnEntity(parachute);
			this.thePlayer.inventory.deleteStack(new ItemStack(ItemsAether.cloud_parachute));
		}
		else
		{
			if (this.thePlayer.inventory.hasItemStack(new ItemStack(ItemsAether.golden_parachute)))
			{
				for(int i = 0; i < this.thePlayer.inventory.getSizeInventory(); i++)
				{
					ItemStack itemstack = this.thePlayer.inventory.getStackInSlot(i);

					if(itemstack != null && itemstack.getItem() == ItemsAether.golden_parachute)
					{ 
						itemstack.damageItem(1, this.thePlayer);
						parachute = new EntityParachute(this.thePlayer.world, this.thePlayer, true);
						parachute.setPosition(this.thePlayer.posX, this.thePlayer.posY, this.thePlayer.posZ);
						this.thePlayer.inventory.setInventorySlotContents(i, itemstack);
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
	 * Instance of the poison used to move the player
	 */
	public AetherPoisonMovement poisonInstance()
	{
		return this.poison;
	}

	/*
	 * Afflicts a set amount of poison to the player
	 */
	@Override
	public void inflictPoison(int ticks)
	{
		this.poison.afflictPoison(ticks);
	}

	/*
	 * Afflicts a set amount of remedy to the player
	 */
	@Override
	public void inflictCure(int ticks)
	{
		this.poison.curePoison(ticks);
	}

	/*
	 * A checker to tell if the player is poisoned or not
	 */
	@Override
	public boolean isPoisoned()
	{
		return this.poison.poisonTime > 0;
	}

	/*
	 * A checker to tell if the player is curing or not
	 */
	@Override
	public boolean isCured()
	{
		return this.poison.poisonTime < 0;
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
		if (this.portalCooldown > 0)
		{
			this.portalCooldown = this.thePlayer.getPortalCooldown();
		}
		else
		{
			this.inPortal = true;
		}
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

}