package com.gildedgames.the_aether.entities.bosses.sun_spirit;

import java.util.List;

import com.gildedgames.the_aether.advancements.AetherAdvancements;
import com.gildedgames.the_aether.entities.projectile.crystals.EntityFireBall;
import com.gildedgames.the_aether.entities.projectile.crystals.EntityIceyBall;
import com.gildedgames.the_aether.registry.sounds.SoundsAether;
import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.api.player.util.IAetherBoss;
import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.blocks.dungeon.BlockDungeonBase;
import com.gildedgames.the_aether.blocks.util.EnumStoneType;
import com.gildedgames.the_aether.entities.bosses.EntityFireMinion;
import com.gildedgames.the_aether.entities.util.AetherNameGen;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.player.PlayerAether;

import com.gildedgames.the_aether.world.AetherWorldProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class EntitySunSpirit extends EntityFlying implements IMob, IAetherBoss, IEntityMultiPart
{

    public static final DataParameter<String> SUN_SPIRIT_NAME = EntityDataManager.<String>createKey(EntitySunSpirit.class, DataSerializers.STRING);

    public static final DataParameter<Integer> CHAT_LINE = EntityDataManager.<Integer>createKey(EntitySunSpirit.class, DataSerializers.VARINT);

    public static final DataParameter<Boolean> FROZEN = EntityDataManager.<Boolean>createKey(EntitySunSpirit.class, DataSerializers.BOOLEAN);

    public int originPointX, originPointY, originPointZ;

    public int motionTimer;
    public int flameCount;
    public int ballCount;
    public int chatLog;
    public int chatCount;

    public int direction;
    public double rotary;
    public double velocity;

    public MultiPartEntityPart SpiritPartHead = new MultiPartEntityPart(this, "head", 6.0F, 6.0F);
    
    public EntitySunSpirit(World world)
    {
        super(world);
    }

    public EntitySunSpirit(World world, int posX, int posY, int posZ, int var6)
    {
        super(world);
        this.setSize(2.5F, 2.8F);
        this.setPosition((double)posX + 0.5D, (double)posY, (double)posZ + 0.5D);
        this.setOriginPosition(posX, posY, posZ);
        this.setBossName(AetherNameGen.gen());

        this.noClip = true;
        this.rotary = (double)this.rand.nextFloat() * 360.0D;;
        this.direction = var6;
        
        this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F, 200.0F));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50.0D);
        this.setHealth(50.0F);
    }

    public boolean isPotionApplicable(PotionEffect par1PotionEffect)
    {
        return false;
    }
    
    @Override
    public void move(MoverType type, double x, double y, double z)
    {
        if (this.getAttackTarget() != null)
        {
            super.move(type, x, 0, z);
        }
        else
        {
            super.move(type, 0, 0, 0);
        }
    }

    public boolean canDespawn()
    {
        return false;
    }
    
    public boolean isNonBoss()
    {
        return false;
    }

    protected SoundEvent getHurtSound()
    {
        return null;
    }

    protected SoundEvent getDeathSound()
    {
        return null;
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(SUN_SPIRIT_NAME, AetherNameGen.gen());
        this.dataManager.register(CHAT_LINE, 0);
        this.dataManager.register(FROZEN, false);
    }

    public boolean isDead()
    {
        return this.getHealth() <= 0.0F || this.isDead;
    }

    public void writeEntityToNBT(NBTTagCompound tag)
    {
        super.writeEntityToNBT(tag);

        tag.setInteger("originPointX", this.originPointX);
        tag.setInteger("originPointY", this.originPointY);
        tag.setInteger("originPointZ", this.originPointZ);
        tag.setInteger("dungeonDirection", this.direction);

        tag.setInteger("chatLog", this.getChatLine());
        tag.setString("bossName", this.getBossName());
        tag.setBoolean("isFreezing", this.isFreezing());
    }

    public void readEntityFromNBT(NBTTagCompound tag)
    {
        super.readEntityFromNBT(tag);

        this.originPointX = tag.getInteger("originPointX");
        this.originPointY = tag.getInteger("originPointY");
        this.originPointZ = tag.getInteger("originPointZ");
        this.direction = tag.getInteger("dungeonDirection");

        this.setChatLine(tag.getInteger("chatLog"));
        this.setBossName(tag.getString("bossName"));
        this.setFreezing(tag.getBoolean("isFreezing"));
    }

    public void onUpdate()
    {
        this.noClip = true;
        super.onUpdate();
        this.noClip = false;

        this.velocity = 0.5D - (double)this.getHealth() / 70.0D * 0.2D;
        this.width = this.height = 2.0F;

        if (this.getAttackTarget() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) this.getAttackTarget();
            IPlayerAether playerAether = AetherAPI.getInstance().get(player);

            if (player.isDead)
            {
                this.setPosition((double)this.originPointX + 0.5D, (double)this.originPointY, (double)this.originPointZ + 0.5D);

                this.chatLog = 10;

                this.motionX = this.motionY = this.motionZ = 0.0D;

                this.chatLine(player, new TextComponentTranslation("gui.spirit.playerdied").setStyle(new Style().setColor(TextFormatting.RED)));
                this.chatCount = 100;

                this.setPosition((double)this.originPointX + 0.5D, (double)this.originPointY, (double)this.originPointZ + 0.5D);
                this.setDoor(Blocks.AIR.getDefaultState());

                this.setFreezing(false);
                this.setAttackTarget(null);
                this.setHealth(this.getMaxHealth());
            }
            else
            {
                playerAether.setFocusedBoss(this);
            }

            if (this.isDead())
            {
                this.chatLine(player, new TextComponentTranslation("gui.spirit.dead").setStyle(new Style().setColor(TextFormatting.AQUA)));
                this.chatCount = 100;

                if (player instanceof EntityPlayerMP)
                {
                    AetherAdvancements.DEFEAT_SUN_SPIRIT_TRIGGER.trigger(((EntityPlayerMP)player));
                }

                this.setDoor(Blocks.AIR.getDefaultState());
                this.unlockTreasure();
            }
        }

        this.setFreezing(this.hurtTime > 0);

        if (this.getHealth() > 0)
        {
            double xCoord = this.posX + (this.rand.nextFloat() - 0.5F) * this.rand.nextFloat();
            double yCoord = this.getEntityBoundingBox().minY + this.rand.nextFloat() - 0.5D;
            double zCoord = this.posZ + (this.rand.nextFloat() - 0.5F) * this.rand.nextFloat();

            this.world.spawnParticle(EnumParticleTypes.FLAME, xCoord, yCoord, zCoord, 0.0D, -0.07500000298023224D, 0.0D);

            this.burnEntities();
            this.evapWater();
        }

        if (this.chatCount > 0)
        {
            --this.chatCount;
        }

    }

    @Override
    public void updateAITasks()
    {
        super.updateAITasks();

        if (this.getAttackTarget() != null)
        {
            this.motionY = 0.0D;
            this.renderYawOffset = this.rotationYaw;

            this.setPosition(this.posX, (double)this.originPointY, this.posZ);

            boolean changedCourse = false;

            if (this.motionX >= 0.0D && this.posX >= (double)this.originPointX + 11D)
            {
                this.rotary = 360.0D - this.rotary;
                changedCourse = true;
            }
            else if (this.motionX <= 0.0D && this.posX <= (double)this.originPointX - 11D)
            {
                this.rotary = 360.0D - this.rotary;
                changedCourse = true;
            }

            if (this.motionZ >= 0.0D && this.posZ >= (double)this.originPointZ + 11D)
            {
                this.rotary = 180.0D - this.rotary;
                changedCourse = true;
            }
            else if (this.motionZ <= 0.0D && this.posZ <= (double)this.originPointZ - 11D)
            {
                this.rotary = 180.0D - this.rotary;
                changedCourse = true;
            }

            if (this.rotary > 360.0D)
            {
                this.rotary -= 360.0D;
            }
            else if (this.rotary < 0.0D)
            {
                this.rotary += 360.0D;
            }

            this.faceEntity(this.getAttackTarget(), 20.0F, 20.0F);

            double angle = this.rotary / (180D / Math.PI);

            this.motionX = Math.sin(angle) * this.velocity;
            this.motionZ = Math.cos(angle) * this.velocity;

            ++this.motionTimer;

            if (this.motionTimer >= 20 || changedCourse)
            {
                if (this.rand.nextInt(3) == 0)
                {
                    this.rotary += (double)(this.rand.nextFloat() - this.rand.nextFloat()) * 60.0D;
                }

                this.motionTimer = 0;
            }

            ++this.flameCount;

            if (this.flameCount == 40)
            {
                this.summonFire();
            }
            else if (this.flameCount >= 55 + this.getHealth() / 2)
            {
                this.makeFireBall(1);
                this.flameCount = 0;
            }
        }
    }

    public void burnEntities()
    {
        List<?> entityList = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.0D, 4.0D, 0.0D));

        for (int ammount = 0; ammount < entityList.size(); ++ammount)
        {
            Entity entity = (Entity)entityList.get(ammount);

            if (entity instanceof EntityLivingBase && !entity.isImmuneToFire())
            {
                entity.attackEntityFrom(new EntityDamageSource("incineration", this), 10);
                entity.setFire(15);
            }
        }
    }

    public void evapWater()
    {
        int var1 = MathHelper.floor(this.getEntityBoundingBox().minX + (this.getEntityBoundingBox().maxX - this.getEntityBoundingBox().minX) / 2.0D);
        int var2 = MathHelper.floor(this.getEntityBoundingBox().minZ + (this.getEntityBoundingBox().maxZ - this.getEntityBoundingBox().minZ) / 2.0D);

        byte radius = 10;

        for (int var4 = var1 - radius; var4 <= var1 + radius; ++var4)
        {
            for (int var5 = var2 - radius; var5 <= var2 + radius; ++var5)
            {
                for (int var6 = 0; var6 < 8; ++var6)
                {
                    int var7 = this.originPointY - 2 + var6;

                    if (this.world.getBlockState(new BlockPos(var4, var7, var5)).getMaterial() == Material.WATER)
                    {
                        this.world.setBlockState(new BlockPos(var4, var7, var5), Blocks.AIR.getDefaultState());

                        this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.HOSTILE, 2.0F, this.rand.nextFloat() - this.rand.nextFloat() * 0.2F + 1.2F, false);

                        for (int var8 = 0; var8 < 8; ++var8)
                        {
                            this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double)var4 + Math.random(), (double)var7 + 0.75D, (double)var5 + Math.random(), 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            }
        }
    }

    public void makeFireBall(int var1)
    {
        this.world.playSound(null, this.getPosition(), SoundsAether.sun_spirit_shoot, SoundCategory.HOSTILE, this.rand.nextFloat() - this.rand.nextFloat() * 0.2F + 1.2F,1.0F);

        boolean shootIceyBall = false;

        ++this.ballCount;

        if (this.ballCount >= 3 + this.rand.nextInt(3))
        {
            shootIceyBall = true;
            this.ballCount = 0;
        }

        for (int var3 = 0; var3 < var1; ++var3)
        {
            if (shootIceyBall == false)
            {
                EntityFireBall fireball = new EntityFireBall(this.world, this.posX - this.motionX / 2.0D, this.posY, this.posZ - this.motionZ / 2.0D);

                if (!this.world.isRemote)
                {
                	fireball.shootingEntity = this;
                    this.world.spawnEntity(fireball);
                }
            }
            
            if (shootIceyBall == true)
            {
                EntityIceyBall iceBall = new EntityIceyBall(this.world, this.posX - this.motionX / 2.0D, this.posY, this.posZ - this.motionZ / 2.0D, false);

                if (!this.world.isRemote)
                {
                	iceBall.shootingEntity = this;
                    this.world.spawnEntity(iceBall);
                }
            }
        }
    }

    public void summonFire()
    {
        int x = MathHelper.floor(this.posX);
        int z = MathHelper.floor(this.posZ);
        int y = this.originPointY - 2;

        if (this.world.isAirBlock(new BlockPos(x, y, z)))
        {
            this.world.setBlockState(new BlockPos(x, y, z), Blocks.FIRE.getDefaultState());
        }
    }

    private void chatLine(EntityPlayer player, ITextComponent s)
    {
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        IPlayerAether playerAether = AetherAPI.getInstance().get(player);

        if (this.chatCount <= 0 || (!AetherConfig.gameplay_changes.repeat_sun_spirit_dialog && ((PlayerAether)playerAether).seenSpiritDialog))
        {
            if (side.isClient())
            {
                Aether.proxy.sendMessage(player, s);
            }
        }
    }

    public boolean chatWithMe(EntityPlayer entityPlayer)
    {
        IPlayerAether playerAether = AetherAPI.getInstance().get(entityPlayer);
        
        if (this.chatCount <= 0)
        {
        	if (!AetherConfig.gameplay_changes.repeat_sun_spirit_dialog && !((PlayerAether)playerAether).seenSpiritDialog)
        	{
                if (this.getChatLine() == 0)
                {
                    this.chatLine(entityPlayer, new TextComponentTranslation("gui.spirit.line0").setStyle(new Style().setColor(TextFormatting.RED)));
                    this.setChatLine(1);
                }
                else if (this.getChatLine() == 1)
                {
                    this.chatLine(entityPlayer, new TextComponentTranslation("gui.spirit.line1").setStyle(new Style().setColor(TextFormatting.RED)));
                    this.setChatLine(2);
                }
                else if (this.getChatLine() == 2)
                {
                    this.chatLine(entityPlayer, new TextComponentTranslation("gui.spirit.line2").setStyle(new Style().setColor(TextFormatting.RED)));
                    this.setChatLine(3);
                }
                else if (this.getChatLine() == 3)
                {
                    this.chatLine(entityPlayer, new TextComponentTranslation("gui.spirit.line3").setStyle(new Style().setColor(TextFormatting.RED)));
                    this.setChatLine(4);
                }
                else if (this.getChatLine() == 4)
                {
                    this.chatLine(entityPlayer, new TextComponentTranslation("gui.spirit.line4").setStyle(new Style().setColor(TextFormatting.RED)));
                    this.setChatLine(5);
                }
                else if (this.getChatLine() == 5)
                {
                    this.chatLine(entityPlayer, new TextComponentTranslation("gui.spirit.line5.1").setStyle(new Style().setColor(TextFormatting.RED)));
                    this.chatLine(entityPlayer, new TextComponentTranslation("gui.spirit.line5.2").setStyle(new Style().setColor(TextFormatting.RED)));
                    this.setChatLine(6);
                }
                else if (this.getChatLine() == 6)
                {
                    this.chatLine(entityPlayer, new TextComponentTranslation("gui.spirit.line6.1").setStyle(new Style().setColor(TextFormatting.RED)));
                    this.chatLine(entityPlayer, new TextComponentTranslation("gui.spirit.line6.2").setStyle(new Style().setColor(TextFormatting.RED)));
                    this.setChatLine(7);
                }
                else if (this.getChatLine() == 7)
                {
                    this.chatLine(entityPlayer, new TextComponentTranslation("gui.spirit.line7.1").setStyle(new Style().setColor(TextFormatting.RED)));
                    this.chatLine(entityPlayer, new TextComponentTranslation("gui.spirit.line7.2").setStyle(new Style().setColor(TextFormatting.RED)));
                    this.setChatLine(8);
                }
                else if (this.getChatLine() == 8)
                {
                    this.chatLine(entityPlayer, new TextComponentTranslation("gui.spirit.line8").setStyle(new Style().setColor(TextFormatting.RED)));
                    this.setChatLine(9);
                }
                else
                {
                    if (this.getChatLine() == 9)
                    {
                        this.chatLine(entityPlayer, new TextComponentTranslation("gui.spirit.line9").setStyle(new Style().setColor(TextFormatting.GOLD)));
                        this.setChatLine(10);
                        return true;
                    }

                    if (this.getChatLine() == 10 && this.getAttackTarget() == null)
                    {
                        this.chatLine(entityPlayer, new TextComponentTranslation("gui.spirit.line10").setStyle(new Style().setColor(TextFormatting.RED)));
                        this.setChatLine(9);
                    }
                }
            }
        	else if (!AetherConfig.gameplay_changes.repeat_sun_spirit_dialog && ((PlayerAether)playerAether).seenSpiritDialog)
            {
                this.setChatLine(9);

                if (this.getChatLine() == 9)
                {
                    this.chatLine(entityPlayer, new TextComponentTranslation("gui.spirit.line9").setStyle(new Style().setColor(TextFormatting.GOLD)));
                    this.setChatLine(10);
                    return true;
                }

                if (this.getChatLine() == 10 && this.getAttackTarget() == null)
                {
                    this.chatLine(entityPlayer, new TextComponentTranslation("gui.spirit.line10").setStyle(new Style().setColor(TextFormatting.RED)));
                    this.setChatLine(9);
                }
            }
        }

        return false;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        if (this.chatWithMe(player))
        {
            this.setAttackTarget(player);
            this.setDoor(BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Hellfire));

            IPlayerAether playerAether = AetherAPI.getInstance().get(player);
            ((PlayerAether) playerAether).seenSpiritDialog = true;

            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void addVelocity(double x, double y, double z)
    {

    }

    @Override
    public void knockBack(Entity entity, float strength, double xRatio, double zRatio) 
    {

    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (source.getImmediateSource() instanceof EntityIceyBall)
        {
        	//EntityIceyBall ball = (EntityIceyBall)source.getImmediateSource();
            this.velocity = 0.5D - (double)this.getHealth() / 70.0D * 0.2D;
            boolean flag = super.attackEntityFrom(source, amount);

            if (flag)
            {
                EntityFireMinion minion = new EntityFireMinion(this.world);
                minion.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                minion.setAttackTarget(this.getAttackTarget());
                
                if (!this.world.isRemote)
                {
                    this.world.spawnEntity(minion);
                }
            }
            return flag;
        }
        else
        {
            return false;
        }
    }

    @Override
    protected void dropFewItems(boolean var1, int var2)
    {
        this.entityDropItem(new ItemStack(ItemsAether.dungeon_key, 1, 2), 0.5F);
        this.entityDropItem(new ItemStack(BlocksAether.sun_altar), 0.5F);
    }

    @Override
    public EntityItem entityDropItem(ItemStack stack, float offsetY)
    {
        if (stack.getCount() != 0 && stack.getItem() != null)
        {
            EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY + (double)offsetY, this.posZ, stack);
            entityitem.setEntityInvulnerable(true);
            entityitem.setDefaultPickupDelay();
            if (captureDrops)
                this.capturedDrops.add(entityitem);
            else
                this.world.spawnEntity(entityitem);
            return entityitem;
        }
        else
        {
            return null;
        }
    }

    @Override
    protected void onDeathUpdate()
    {
        WorldProvider provider = this.world.provider;

        if (provider instanceof AetherWorldProvider)
        {
            AetherWorldProvider aetherProvider = (AetherWorldProvider) provider;

            if (aetherProvider.getEternalDayManager() != null)
            {
                if (aetherProvider.getEternalDayManager().isEternalDay())
                {
                    aetherProvider.getEternalDayManager().setEternalDay(false);
                }
            }
        }

        this.setFreezing(true);

        super.onDeathUpdate();
    }

    private void setDoor(IBlockState block)
    {
        int x, y, z;

        if (this.direction / 2 == 0)
        {
            for (y = this.originPointY - 1; y < this.originPointY + 2; ++y)
            {
                for (z = this.originPointZ - 1; z < this.originPointZ + 2; ++z)
                {
                    BlockPos pos = new BlockPos(this.originPointX + (this.direction == 0 ? -13 : 13), y, z);

                    this.world.setBlockState(pos, block, 2);
                }
            }
        }
        else
        {
            for (y = this.originPointY - 1; y < this.originPointY + 2; ++y)
            {
                for (x = this.originPointX - 1; x < this.originPointX + 2; ++x)
                {
                    BlockPos pos = new BlockPos(x, y, this.originPointZ + (this.direction != 3 ? 13 : -13));

                    this.world.setBlockState(pos, block, 2);
                }
            }
        }
    }

    private void unlockTreasure()
    {
        int x, y, z;

        if (this.direction / 2 == 0)
        {
            for (y = this.originPointY - 1; y < this.originPointY + 2; ++y)
            {
                for (z = this.originPointZ - 1; z < this.originPointZ + 2; ++z)
                {
                    this.world.setBlockState(new BlockPos(this.originPointX + (this.direction == 0 ? 13 : -13), y, z), Blocks.AIR.getDefaultState());
                }
            }
        }
        else
        {
            for (y = this.originPointY - 1; y < this.originPointY + 2; ++y)
            {
                for (x = this.originPointX - 1; x < this.originPointX + 2; ++x)
                {
                    this.world.setBlockState(new BlockPos(x, y, this.originPointZ + (this.direction != 3 ? -13 : 13)), Blocks.AIR.getDefaultState());
                }
            }
        }

        for (x = this.originPointX - 20; x < this.originPointX + 20; ++x)
        {
            for (y = this.originPointY  - 3; y < this.originPointY + 6; ++y)
            {
                for (z = this.originPointZ - 20; z < this.originPointZ + 20; ++z)
                {
                    BlockPos newPos = new BlockPos(x, y, z);
                    IBlockState unlock_block = this.world.getBlockState(newPos);

                    if (unlock_block == BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Hellfire))
                    {
                        this.world.setBlockState(newPos, BlocksAether.dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.getType(unlock_block.getBlock().getMetaFromState(unlock_block))), 2);
                    }
                    else if (unlock_block == BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_hellfire))
                    {
                        this.world.setBlockState(newPos, BlocksAether.dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.getType(unlock_block.getBlock().getMetaFromState(unlock_block))), 2);
                    }
                }
            }
        }
        
    }

    public void setOriginPosition(int x, int y, int z)
    {
        this.originPointX = x;
        this.originPointY = y;
        this.originPointZ = z;
    }

    public String getBossName()
    {
        return this.dataManager.get(SUN_SPIRIT_NAME);
    }

    public void setBossName(String name)
    {
        this.dataManager.set(SUN_SPIRIT_NAME, name);
    }

    public String getBossTitle()
    {
        return this.getBossName() + ", " + new TextComponentTranslation("title.aether_legacy.sun_spirit.name", new Object[0]).getFormattedText();
    }

    public int getChatLine()
    {
        return this.dataManager.get(CHAT_LINE).intValue();
    }

    public void setChatLine(int lineNumber)
    {
        this.chatCount = 100;
        this.dataManager.set(CHAT_LINE, lineNumber);
    }

    public boolean isFreezing()
    {
        return this.dataManager.get(FROZEN).booleanValue();
    }

    public void setFreezing(boolean isFreezing)
    {
        this.dataManager.set(FROZEN, isFreezing);
    }
    
    @Override
    public float getEyeHeight()
    {
        return 2.1F;
    }
    
    @Override
    public boolean canBeLeashedTo(final EntityPlayer player)
    {
        return false;
    }

    @Override
    public float getBossHealth()
    {
        return this.getHealth();
    }

    @Override
    public float getMaxBossHealth()
    {
        return this.getMaxHealth();
    }

	@Override
	public World getWorld()
	{
		return this.getEntityWorld();
	}

	@Override
	public boolean attackEntityFromPart(MultiPartEntityPart spiritPart, DamageSource source, float damage)
	{
		return this.attackEntityFrom(source, damage);
	}
}