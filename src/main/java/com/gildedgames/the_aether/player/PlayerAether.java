package com.gildedgames.the_aether.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.api.player.util.IAccessoryInventory;
import com.gildedgames.the_aether.api.player.util.IAetherAbility;
import com.gildedgames.the_aether.api.player.util.IAetherBoss;
import com.gildedgames.the_aether.entities.passive.mountable.EntityParachute;
import com.gildedgames.the_aether.inventory.InventoryAccessories;
import com.gildedgames.the_aether.network.packets.*;
import com.gildedgames.the_aether.registry.achievements.AchievementsAether;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.network.AetherNetwork;
import com.gildedgames.the_aether.player.perks.AetherRankings;
import com.gildedgames.the_aether.player.perks.util.EnumAetherPerkType;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.items.tools.ItemValkyrieTool;
import com.gildedgames.the_aether.player.abilities.AbilityAccessories;
import com.gildedgames.the_aether.player.abilities.AbilityArmor;
import com.gildedgames.the_aether.player.abilities.AbilityFlight;
import com.gildedgames.the_aether.player.abilities.AbilityRepulsion;
import com.gildedgames.the_aether.player.perks.util.DonatorMoaSkin;
import com.gildedgames.the_aether.world.TeleporterAether;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class PlayerAether implements IPlayerAether {

	private EntityPlayer player;

	private IAetherBoss focusedBoss;

	private IAccessoryInventory accessories = new InventoryAccessories(this);

	private final ArrayList<IAetherAbility> abilities = new ArrayList<IAetherAbility>();

	public final ArrayList<Entity> clouds = new ArrayList<Entity>(2);

	public int shardCount;

	public DonatorMoaSkin donatorMoaSkin = new DonatorMoaSkin();

	public boolean shouldRenderHalo, shouldRenderGlow, shouldRenderCape;

	public boolean seenSpiritDialog = false;

	private boolean isJumping;

	private boolean isMountSneaking;

	private boolean inPortal;

	private int portalCounter;

	public int teleportDirection;

	private String hammerName = "Hammer of Notch";

	private int cooldown;

	private int cooldownMax;

	public float wingSinage;

    public float timeInPortal;

    public float prevTimeInPortal;

    public Entity riddenEntity;

    private ChunkCoordinates bedLocation;

	public boolean isPoisoned = false, isCured = false;

	public boolean shouldGetPortal;

	public int poisonTime = 0, cureTime = 0;

	private UUID uuid = UUID.fromString("df6eabe7-6947-4a56-9099-002f90370706");

	private AttributeModifier healthModifier;

	public PlayerAether() {
		this.shouldRenderHalo = true;
		this.shouldRenderGlow = false;
		this.shouldRenderCape = true;
		this.shouldGetPortal = true;
		this.abilities.addAll(Arrays.<IAetherAbility>asList(new AbilityAccessories(this), new AbilityArmor(this), new AbilityFlight(this), new AbilityRepulsion(this)));
	}

	public static PlayerAether get(EntityPlayer player) {
		return (PlayerAether) player.getExtendedProperties("aether_legacy:player_aether");
	}

	@Override
	public void init(Entity entity, World world) {
		this.player = (EntityPlayer) entity;
	}

	@Override
	public void onUpdate() {
		System.out.println("s " + this.shardCount);

		if (!this.player.worldObj.isRemote)
		{
			AetherNetwork.sendToAll(new PacketPerkChanged(this.getEntity().getEntityId(), EnumAetherPerkType.Halo, this.shouldRenderHalo));
			AetherNetwork.sendToAll(new PacketPerkChanged(this.getEntity().getEntityId(), EnumAetherPerkType.Glow, this.shouldRenderGlow));
			AetherNetwork.sendToAll(new PacketCapeChanged(this.getEntity().getEntityId(), this.shouldRenderCape));
			AetherNetwork.sendToAll(new PacketSendPoisonTime(this.getEntity(), this.poisonTime));
			AetherNetwork.sendToAll(new PacketSendSeenDialogue(this.getEntity(), this.seenSpiritDialog));
			AetherNetwork.sendToAll(new PacketPortalItem(this.getEntity(), this.shouldGetPortal));
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

		for (int i = 0; i < this.getAbilities().size(); ++i) {
			IAetherAbility ability = this.getAbilities().get(i);

			if (ability.shouldExecute()) {
				ability.onUpdate();
			}
		}

		for (int i = 0; i < this.clouds.size(); ++i) {
			Entity entity = this.clouds.get(i);

			if (entity.isDead) {
				this.clouds.remove(i);
			}
		}

		if (this.cooldown > 0) {
			this.cooldown -= 2;
		}

		if (this.isInsideBlock(BlocksAether.aercloud)) {
			this.getEntity().fallDistance = 0.0F;
		}

		if (this.getEntity().motionY < -2F)
		{
			this.activateParachute();
		}

		if (!this.getEntity().onGround) {
			this.wingSinage += 0.75F;
		} else {
			this.wingSinage += 0.15F;
		}

		if (this.wingSinage > 3.141593F * 2F) {
			this.wingSinage -= 3.141593F * 2F;
		} else {
			this.wingSinage += 0.1F;
		}

		boolean hasJumped = ReflectionHelper.getPrivateValue(EntityLivingBase.class, this.getEntity(), "isJumping", "field_70703_bu");

		this.setJumping(hasJumped);

		this.getEntity().worldObj.theProfiler.startSection("portal");

		if (this.getEntity().dimension == AetherConfig.getAetherDimensionID()) {
			if (this.getEntity().posY < -2) {
				this.teleportPlayer(false);

				if (this.riddenEntity != null)
				{
					this.getEntity().mountEntity(this.riddenEntity);
					this.riddenEntity = null;
				}
			}
		}

		if (this.inPortal) {
			if (this.getEntity().timeUntilPortal <= 0) {
				int limit = this.getEntity().getMaxInPortalTime();

				if (this.getEntity().ridingEntity == null) {
					if (this.portalCounter >= limit)
					{
						this.portalCounter = 0;
						this.getEntity().timeUntilPortal = this.getEntity().getPortalCooldown();

						if (!this.getEntity().worldObj.isRemote) {
							this.teleportPlayer(true);
							this.getEntity().triggerAchievement(AchievementsAether.enter_aether);
						}
					}
					else
					{
						this.portalCounter++;
					}
				}
			}
			else
			{
				this.getEntity().timeUntilPortal = this.getEntity().getPortalCooldown();
			}

			if (this.getEntity().worldObj.getBlock((int) this.getEntity().posX, (int) this.getEntity().posY - 1, (int) this.getEntity().posZ) != Blocks.air)
			{
				AxisAlignedBB playerBounding = this.getEntity().boundingBox;

				if (this.getEntity().worldObj.getBlock((int) playerBounding.minX, (int) playerBounding.minY, (int) playerBounding.minZ) != BlocksAether.aether_portal
						&& this.getEntity().worldObj.getBlock((int) playerBounding.minX, (int) playerBounding.minY, (int) playerBounding.minZ) != BlocksAether.aether_portal)
				{
					this.inPortal = false;
				}
			}
		} else {
			if (this.portalCounter > 0) {
				this.portalCounter -= 4;
			}

			if (this.portalCounter < 0) {
				this.portalCounter = 0;
			}
		}

		this.getEntity().worldObj.theProfiler.endSection();

		if (!this.getEntity().worldObj.isRemote) {
			ItemStack stack = this.getEntity().getCurrentEquippedItem();

			double distance = this.getEntity().capabilities.isCreativeMode ? 5.0D : 4.5D;

			if (stack != null && stack.getItem() instanceof ItemValkyrieTool) {
				distance = 8.0D;
			}

			((EntityPlayerMP) this.getEntity()).theItemInWorldManager.setBlockReachDistance(distance);
		}
		else {
            this.prevTimeInPortal = this.timeInPortal;

            if (this.isInsideBlock(BlocksAether.aether_portal))
            {
                this.timeInPortal += 0.0125F;

                if (this.timeInPortal >= 1.0F)
                {
                    this.timeInPortal = 1.0F;
                }
            }
            else if (this.getEntity().isPotionActive(Potion.confusion) && this.getEntity().getActivePotionEffect(Potion.confusion).getDuration() > 60)
            {
                this.timeInPortal += 0.006666667F;

                if (this.timeInPortal > 1.0F)
                {
                    this.timeInPortal = 1.0F;
                }
            }
            else
            {
                if (this.timeInPortal > 0.0F)
                {
                    this.timeInPortal -= 0.05F;
                }

                if (this.timeInPortal < 0.0F)
                {
                    this.timeInPortal = 0.0F;
                }
            }
		}

		if (!player.worldObj.isRemote)
		{
			if (this.bedLocation != null)
			{
				if (player.dimension == AetherConfig.getAetherDimensionID())
				{
					if (player.worldObj.getBlock(this.bedLocation.posX, this.bedLocation.posY, this.bedLocation.posZ) != BlocksAether.skyroot_bed)
					{
						this.setBedLocation(null);
					}
				}
			}
		}
	}

	@Override
	public void setInPortal() {
		double d0 = this.getEntity().prevPosX - this.getEntity().posX;
		double d1 = this.getEntity().prevPosZ - this.getEntity().posZ;

		if (!this.getEntity().worldObj.isRemote && !this.inPortal) {
			this.teleportDirection = Direction.getMovementDirection(d0, d1);
		}

		this.inPortal = true;
	}

	private void activateParachute()
	{
		if (!this.player.capabilities.isCreativeMode)
		{
			EntityParachute parachute = null;

			ItemStack itemstack = null;

			for (int i = 0; i < this.getEntity().inventory.getSizeInventory(); i++)
			{
				ItemStack stackInSlot = this.getEntity().inventory.getStackInSlot(i);

				if(stackInSlot != null && stackInSlot.getItem() == ItemsAether.cloud_parachute)
				{
					itemstack = stackInSlot;
					break;
				}
				else
				{
					if (stackInSlot != null && stackInSlot.getItem() == ItemsAether.golden_parachute)
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
					parachute = new EntityParachute(this.getEntity().worldObj, this.getEntity(), false);
					parachute.setPosition(this.getEntity().posX, this.getEntity().posY, this.getEntity().posZ);
					this.getEntity().worldObj.spawnEntityInWorld(parachute);
					this.getEntity().inventory.consumeInventoryItem(itemstack.getItem());
				}
				else
				{
					if (itemstack.getItem() == ItemsAether.golden_parachute)
					{
						itemstack.damageItem(1, this.getEntity());
						parachute = new EntityParachute(this.getEntity().worldObj, this.getEntity(), true);
						parachute.setPosition(this.getEntity().posX, this.getEntity().posY, this.getEntity().posZ);
						this.getEntity().worldObj.spawnEntityInWorld(parachute);
					}
				}
			}
		}
	}

	public boolean isInsideBlock(Block block) {
		AxisAlignedBB boundingBox = this.getEntity().boundingBox;
		int i = MathHelper.floor_double(boundingBox.minX);
		int j = MathHelper.floor_double(boundingBox.maxX + 1.0D);
		int k = MathHelper.floor_double(boundingBox.minY);
		int l = MathHelper.floor_double(boundingBox.maxY + 1.0D);
		int i1 = MathHelper.floor_double(boundingBox.minZ);
		int j1 = MathHelper.floor_double(boundingBox.maxZ + 1.0D);

		for (int k1 = i; k1 < j; ++k1) {
			for (int l1 = k; l1 < l; ++l1) {
				for (int i2 = i1; i2 < j1; ++i2) {
					if (this.getEntity().worldObj.getBlock(k1, l1, i2) == block) {
						return true;
					}
				}
			}
		}

		return false;
	}

	/*
	 * The teleporter which sends the player to the Aether/Overworld
	 */
	private void teleportPlayer(boolean shouldSpawnPortal) {
		if (this.getEntity() instanceof EntityPlayerMP) {
			int previousDimension = this.getEntity().dimension;
			int transferDimension = previousDimension == AetherConfig.getAetherDimensionID() ? AetherConfig.getTravelDimensionID() : AetherConfig.getAetherDimensionID();
			MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
			TeleporterAether teleporter = new TeleporterAether(shouldSpawnPortal, server.worldServerForDimension(transferDimension));

			if (this.getEntity().ridingEntity != null) {
				this.getEntity().ridingEntity.mountEntity(null);
			}

			if (this.getEntity().riddenByEntity != null) {
				this.getEntity().riddenByEntity.mountEntity(null);
			}

			if (server != null && server.getConfigurationManager() != null)
			{
				server.getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) this.getEntity(), transferDimension, teleporter);	
			}
		}
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound aetherTag = new NBTTagCompound();

		if (AetherRankings.isRankedPlayer(this.player.getUniqueID()))
		{
			aetherTag.setBoolean("halo", this.shouldRenderHalo);
		}

		if (AetherRankings.isDeveloper(this.player.getUniqueID()))
		{
			aetherTag.setBoolean("glow", this.shouldRenderGlow);
		}

		aetherTag.setBoolean("poisoned", this.isPoisoned);
		aetherTag.setInteger("poison_time", this.poisonTime);
		aetherTag.setBoolean("cape", this.shouldRenderCape);
		aetherTag.setInteger("shardCount", this.shardCount);
		aetherTag.setTag("accessories", this.getAccessoryInventory().writeToNBT(aetherTag));
		aetherTag.setBoolean("seen_spirit_dialog", this.seenSpiritDialog);
		aetherTag.setBoolean("get_portal", this.shouldGetPortal);

		if (this.bedLocation != null)
		{
			aetherTag.setInteger("bedX", this.bedLocation.posX);
			aetherTag.setInteger("bedY", this.bedLocation.posY);
			aetherTag.setInteger("bedZ", this.bedLocation.posZ);
		}

		compound.setTag("aetherI", aetherTag);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound aetherTag = compound.getCompoundTag("aetherI");

		if (aetherTag.hasKey("halo"))
		{
			this.shouldRenderHalo = aetherTag.getBoolean("halo");
		}

		if (aetherTag.hasKey("glow"))
		{
			this.shouldRenderGlow = aetherTag.getBoolean("glow");
		}

		if (aetherTag.hasKey("cape"))
		{
			this.shouldRenderCape = aetherTag.getBoolean("cape");
		}

		if (aetherTag.hasKey("poisoned"))
		{
			this.isPoisoned = aetherTag.getBoolean("poisoned");
		}

		if (aetherTag.hasKey("poison_time"))
		{
			this.poisonTime = aetherTag.getInteger("poison_time");
		}

		if (aetherTag.hasKey("seen_spirit_dialog"))
		{
			this.seenSpiritDialog = aetherTag.getBoolean("seen_spirit_dialog");
		}

		if (aetherTag.hasKey("get_portal"))
		{
			this.shouldGetPortal = aetherTag.getBoolean("get_portal");
		}

		if (aetherTag.hasKey("shardCount"))
		{
			this. shardCount = aetherTag.getInteger("shardCount");
		}

		this.getAccessoryInventory().readFromNBT(aetherTag.getTagList("accessories", 10));
		this.setBedLocation(new ChunkCoordinates(aetherTag.getInteger("bedX"), aetherTag.getInteger("bedY"), aetherTag.getInteger("bedZ")));
	}

	@Override
	public void setFocusedBoss(IAetherBoss boss) {
		this.focusedBoss = boss;
	}

	@Override
	public IAetherBoss getFocusedBoss() {
		return this.focusedBoss;
	}

	@Override
	public void setAccessoryInventory(IAccessoryInventory inventory) {
		this.accessories = inventory;
	}

	@Override
	public IAccessoryInventory getAccessoryInventory() {
		return this.accessories;
	}

	@Override
	public ArrayList<IAetherAbility> getAbilities() {
		return this.abilities;
	}

	@Override
	public EntityPlayer getEntity() {
		return this.player;
	}

	@Override
	public void updateShardCount(int amount) {

		if (!this.getEntity().worldObj.isRemote)
		{
			if (this.getShardsUsed() < this.getMaxShardCount())
			{
				this.shardCount += amount;
				AetherNetwork.sendToAll(new PacketUpdateLifeShardCount(this.player, this.shardCount));

				this.healthModifier = new AttributeModifier(uuid, "Aether Health Modifier", (this.shardCount * 2.0D), 0);

				if (this.player.getEntityAttribute(SharedMonsterAttributes.maxHealth).getModifier(this.uuid) != null)
				{
					this.player.getEntityAttribute(SharedMonsterAttributes.maxHealth).removeModifier(this.healthModifier);
				}

				this.player.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(this.healthModifier);
			}
		}
	}

	@Override
	public int getShardsUsed() {
		return this.shardCount;
	}

	@Override
	public int getMaxShardCount() {
		return AetherConfig.getMaxLifeShards();
	}

	@Override
	public void setJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}

	@Override
	public boolean isJumping() {
		return this.isJumping;
	}

	@Override
	public void setMountSneaking(boolean isSneaking) {
		this.isMountSneaking = isSneaking;
	}

	@Override
	public boolean isMountSneaking() {
		return this.isMountSneaking;
	}

	@Override
	public boolean isDonator() {
		return true;
	}

	public boolean setHammerCooldown(int cooldown, String hammerName) {
		if (this.cooldown <= 0) {
			this.cooldown = cooldown;
			this.cooldownMax = cooldown;
			this.hammerName = hammerName;

			return true;
		}

		return false;
	}

	@Override
	public String getHammerName() {
		return this.hammerName;
	}

	@Override
	public int getHammerCooldown() {
		return this.cooldown;
	}

	@Override
	public int getHammerMaxCooldown() {
		return this.cooldownMax;
	}

	public void setBedLocation(ChunkCoordinates bedLocation)
	{
		this.bedLocation = bedLocation;
	}

	public ChunkCoordinates getBedLocation()
	{
		return bedLocation;
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

	public void givePortalFrame()
	{
		if (this.shouldGetPortal)
		{
			this.player.inventory.addItemStackToInventory(new ItemStack(ItemsAether.aether_portal_frame));
			this.shouldGetPortal = false;
		}
	}
}