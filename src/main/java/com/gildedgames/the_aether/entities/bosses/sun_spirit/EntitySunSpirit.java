package com.gildedgames.the_aether.entities.bosses.sun_spirit;

import java.util.List;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.player.util.IAetherBoss;
import com.gildedgames.the_aether.entities.bosses.EntityFireMinion;
import com.gildedgames.the_aether.entities.projectile.crystals.EntityCrystal;
import com.gildedgames.the_aether.entities.projectile.crystals.EnumCrystalType;
import com.gildedgames.the_aether.entities.util.AetherNameGen;
import com.gildedgames.the_aether.entities.util.EntityAetherItem;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.player.PlayerAether;
import com.gildedgames.the_aether.registry.achievements.AchievementsAether;
import com.gildedgames.the_aether.world.AetherData;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.blocks.dungeon.BlockDungeonBase;
import net.minecraft.world.WorldProvider;

public class EntitySunSpirit extends EntityFlying implements IMob, IAetherBoss {

    public int originPointX, originPointY, originPointZ;

    public int motionTimer;
    public int flameCount;
    public int ballCount;
    public int chatLog;
    public int chatCount;

    public int direction;
    public double rotary;
    public double velocity;

    public EntitySunSpirit(World worldIn) {
        super(worldIn);

        this.setSize(2.5F, 2.8F);
        this.dataWatcher.updateObject(20, AetherNameGen.gen());
    }

    public EntitySunSpirit(World worldIn, int posX, int posY, int posZ, int var6) {
        this(worldIn);

        this.noClip = true;
        this.direction = var6;
        this.rotary = (double) this.rand.nextFloat() * 360.0D;
        ;
        this.rotationYaw = this.rotationYawHead = var6 == 3 ? 0 : var6 == 0 ? 90 : var6 == 2 ? 180 : 270;

        this.setPosition((double) posX + 0.5D, (double) posY, (double) posZ + 0.5D);
        this.setOriginPosition((int) posX, (int) posY, (int) posZ);
    }

    @Override
    public void entityInit() {
        super.entityInit();

        this.dataWatcher.addObject(18, new Byte((byte) 0));
        this.dataWatcher.addObject(19, new Byte((byte) 0));
        this.dataWatcher.addObject(20, AetherNameGen.gen());
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
    }

    public boolean isPotionApplicable(PotionEffect par1PotionEffect) {
        return false;
    }

    public boolean canDespawn() {
        return false;
    }

    @Override
    protected String getHurtSound() {
        return null;
    }

    @Override
    protected String getDeathSound() {
        return null;
    }

    public boolean isDead() {
        return this.getHealth() <= 0.0F || this.isDead;
    }

    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);

        tag.setInteger("originPointX", this.originPointX);
        tag.setInteger("originPointY", this.originPointY);
        tag.setInteger("originPointZ", this.originPointZ);
        tag.setInteger("dungeonDirection", this.direction);

        tag.setInteger("chatLog", this.getChatLine());
        tag.setString("bossName", this.getName());
        tag.setBoolean("isFreezing", this.isFreezing());
    }

    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);

        this.originPointX = tag.getInteger("originPointX");
        this.originPointY = tag.getInteger("originPointY");
        this.originPointZ = tag.getInteger("originPointZ");
        this.direction = tag.getInteger("dungeonDirection");

        this.setChatLine(tag.getInteger("chatLog"));
        this.setBossName(tag.getString("bossName"));
        this.setFreezing(tag.getBoolean("isFreezing"));
    }

    public void onUpdate() {
        super.onUpdate();

        this.velocity = 0.5D - (double) this.getHealth() / 70.0D * 0.2D;
        this.width = this.height = 2.0F;

        if (this.getAttackTarget() instanceof EntityPlayer) {
            List<?> dungeonPlayers = this.getPlayersInDungeon();
            EntityPlayer dungeonTarget = (EntityPlayer) this.getAttackTarget();
            PlayerAether playerAether = PlayerAether.get(dungeonTarget);

            for (int i = 0; i < dungeonPlayers.size(); ++i) {
                Entity entity = (Entity) dungeonPlayers.get(i);

                if (entity instanceof EntityPlayer) {
                    PlayerAether dungeonPA = PlayerAether.get((EntityPlayer) entity);

                    if (dungeonPA.getFocusedBoss() != this) {
                        dungeonPA.setFocusedBoss(this);
                    }
                }
            }

            if (dungeonTarget.isDead) {
                for (int i = 0; i < dungeonPlayers.size(); ++i) {
                    Entity entity = (Entity) dungeonPlayers.get(i);

                    if (entity instanceof EntityPlayer) {
                        PlayerAether dungeonPA = PlayerAether.get((EntityPlayer) entity);

                        dungeonPA.setFocusedBoss(null);
                    }
                }

                this.setPosition((double) this.originPointX + 0.5D, (double) this.originPointY, (double) this.originPointZ + 0.5D);

                this.chatLog = 10;

                this.motionX = this.motionY = this.motionZ = 0.0D;

                this.chatLine(dungeonTarget, "\u00a7cSuch is the fate of a being who opposes the might of the sun.");
                this.chatCount = 100;

                this.setPosition((double) this.originPointX + 0.5D, (double) this.originPointY, (double) this.originPointZ + 0.5D);
                this.setDoor(Blocks.air);

                this.setFreezing(false);
                this.setAttackTarget(null);
                this.setHealth(this.getMaxHealth());
            } else {
                playerAether.setFocusedBoss(this);
            }

            if (this.isDead()) {
                this.setFreezing(true);
                this.chatLine(dungeonTarget, "\u00a7bSuch bitter cold... is this the feeling... of pain?");
                this.chatCount = 100;

                for (int i = 0; i < dungeonPlayers.size(); ++i) {
                    Entity entity = (Entity) dungeonPlayers.get(i);

                    if (entity instanceof EntityPlayer) {
                        ((EntityPlayer) entity).triggerAchievement(AchievementsAether.defeat_gold);
                    }
                }

                dungeonTarget.triggerAchievement(AchievementsAether.defeat_gold);

                if (!this.worldObj.isRemote)
                {
                    if (!AetherData.getInstance(this.worldObj).isEternalDay())
                    {
                        AetherData.getInstance(this.worldObj).setEternalDay(true);
                    }
                }

                this.setDoor(Blocks.air);
                this.unlockTreasure();
            }
        }

        this.setFreezing(this.hurtTime > 0);

        if (this.getHealth() > 0) {
            double xCoord = this.posX + (this.rand.nextFloat() - 0.5F) * this.rand.nextFloat();
            double yCoord = this.boundingBox.minY + this.rand.nextFloat() - 0.5D;
            double zCoord = this.posZ + (this.rand.nextFloat() - 0.5F) * this.rand.nextFloat();

            this.worldObj.spawnParticle("flame", xCoord, yCoord, zCoord, 0.0D, -0.07500000298023224D, 0.0D);

            this.burnEntities();
            this.evapWater();
        }

        if (this.chatCount > 0) {
            --this.chatCount;
        }
    }

    @Override
    protected void updateEntityActionState() {
        super.updateEntityActionState();

        if (this.getAttackTarget() != null) {
            this.motionY = 0.0D;
            this.renderYawOffset = this.rotationYaw;

            this.setPosition(this.posX, (double) this.originPointY, this.posZ);

            boolean changedCourse = false;

            if (this.motionX >= 0.0D && this.posX > (double) this.originPointX + 8.5D) {
                this.rotary = 360.0D - this.rotary;
                changedCourse = true;
            } else if (this.motionX <= 0.0D && this.posX < (double) this.originPointX - 10D) {
                this.rotary = 360.0D - this.rotary;
                changedCourse = true;
            }

            if (this.motionZ >= 0.0D && this.posZ > (double) this.originPointZ + 10.0D) {
                this.rotary = 180.0D - this.rotary;
                changedCourse = true;
            } else if (this.motionZ <= 0.0D && this.posZ < (double) this.originPointZ - 9.0D) {
                this.rotary = 180.0D - this.rotary;
                changedCourse = true;
            }

            if (this.rotary > 360.0D) {
                this.rotary -= 360.0D;
            } else if (this.rotary < 0.0D) {
                this.rotary += 360.0D;
            }

            this.faceEntity(this.getAttackTarget(), 20.0F, 20.0F);

            double angle = this.rotary / (180D / Math.PI);

            this.motionX = Math.sin(angle) * this.velocity;
            this.motionZ = Math.cos(angle) * this.velocity;

            ++this.motionTimer;

            if (this.motionTimer >= 20 || changedCourse) {
                if (this.rand.nextInt(3) == 0) {
                    this.rotary += (double) (this.rand.nextFloat() - this.rand.nextFloat()) * 60.0D;
                }

                this.motionTimer = 0;
            }

            ++this.flameCount;

            if (this.flameCount == 40) {
                this.summonFire();
            } else if (this.flameCount >= 55 + this.getHealth() / 2) {
                this.makeFireBall(1);
                this.flameCount = 0;
            }
        }
    }

    @Override
    public void updateAITasks() {
        super.updateAITasks();
    }

    public void burnEntities() {
        List<?> entityList = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(0.0D, 4.0D, 0.0D));

        for (int ammount = 0; ammount < entityList.size(); ++ammount) {
            Entity entity = (Entity) entityList.get(ammount);

            if (entity instanceof EntityLivingBase && !entity.isImmuneToFire()) {
                entity.attackEntityFrom(new EntityDamageSource("incineration", this), 10);
                entity.setFire(15);
            }
        }
    }

    public void evapWater() {
        int var1 = MathHelper.floor_double(this.boundingBox.minX + (this.boundingBox.maxX - this.boundingBox.minX) / 2.0D);
        int var2 = MathHelper.floor_double(this.boundingBox.minZ + (this.boundingBox.maxZ - this.boundingBox.minZ) / 2.0D);

        byte radius = 10;

        for (int var4 = var1 - radius; var4 <= var1 + radius; ++var4) {
            for (int var5 = var2 - radius; var5 <= var2 + radius; ++var5) {
                for (int var6 = 0; var6 < 8; ++var6) {
                    int var7 = this.originPointY - 2 + var6;

                    if (this.worldObj.getBlock(var4, var7, var5).getMaterial() == Material.water) {
                        this.worldObj.setBlock(var4, var7, var5, Blocks.air);

                        this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "random.fizz", 2.0F, this.rand.nextFloat() - this.rand.nextFloat() * 0.2F + 1.2F);

                        for (int var8 = 0; var8 < 8; ++var8) {
                            this.worldObj.spawnParticle("largesmoke", (double) var4 + Math.random(), (double) var7 + 0.75D, (double) var5 + Math.random(), 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
            }
        }
    }

    public void makeFireBall(int var1) {
        this.worldObj.playSoundAtEntity(this, "mob.ghast.fireball", this.rand.nextFloat() - this.rand.nextFloat() * 0.2F + 1.2F, 1.0F);

        boolean shootIceyBall = false;

        ++this.ballCount;

        if (this.ballCount >= 2 + this.rand.nextInt(3)) {
            shootIceyBall = true;
            this.ballCount = 0;
        }

        for (int var3 = 0; var3 < var1; ++var3) {
            EntityCrystal crystal = new EntityCrystal(this.worldObj, this.posX - this.motionX / 2.0D, this.posY, this.posZ - this.motionZ / 2.0D, shootIceyBall ? EnumCrystalType.ICE : EnumCrystalType.FIRE);

            if (!this.worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld(crystal);
            }
        }
    }

    public void summonFire() {
        int x = MathHelper.floor_double(this.posX);
        int z = MathHelper.floor_double(this.posZ);
        int y = this.originPointY - 2;

        if (this.worldObj.isAirBlock(x, y, z)) {
            this.worldObj.setBlock(x, y, z, Blocks.fire);
        }
    }

    private void chatLine(EntityPlayer player, String s) {
        Side side = FMLCommonHandler.instance().getEffectiveSide();

        if (this.chatCount <= 0 || (!AetherConfig.repeatSunSpiritDialogue() && ((PlayerAether) AetherAPI.get(player)).seenSpiritDialog)) {
            if (side == Side.CLIENT)
            {
                Aether.proxy.sendMessage(player, s);
            }
        }
    }

    public boolean chatWithMe(EntityPlayer entityPlayer) {
        if (this.chatCount <= 0) {
            if (!AetherConfig.repeatSunSpiritDialogue() && !((PlayerAether) AetherAPI.get(entityPlayer)).seenSpiritDialog)
            {
                if (this.getChatLine() == 0) {
                    this.chatLine(entityPlayer, "\u00a7cYou are certainly a brave soul to have entered this chamber.");
                    this.setChatLine(1);
                } else if (this.getChatLine() == 1) {
                    this.chatLine(entityPlayer, "\u00a7cBegone human, you serve no purpose here.");
                    this.setChatLine(2);
                } else if (this.getChatLine() == 2) {
                    this.chatLine(entityPlayer, "\u00a7cYour presence annoys me. Do you not fear my burning aura?");
                    this.setChatLine(3);
                } else if (this.getChatLine() == 3) {
                    this.chatLine(entityPlayer, "\u00a7cI have nothing to offer you, fool. Leave me at peace.");
                    this.setChatLine(4);
                } else if (this.getChatLine() == 4) {
                    this.chatLine(entityPlayer, "\u00a7cPerhaps you are ignorant. Do you wish to know who I am?");
                    this.setChatLine(5);
                } else if (this.getChatLine() == 5) {
                    this.chatLine(entityPlayer, "\u00a7cI am a sun spirit, embodiment of Aether's eternal daylight. As ");
                    this.chatLine(entityPlayer, "\u00a7clong as I am alive, the sun will never set on this world.");
                    this.setChatLine(6);
                } else if (this.getChatLine() == 6) {
                    this.chatLine(entityPlayer, "\u00a7cMy body burns with the anger of a thousand beasts. No man, ");
                    this.chatLine(entityPlayer, "\u00a7chero, or villain can harm me. You are no exception.");
                    this.setChatLine(7);
                } else if (this.getChatLine() == 7) {
                    this.chatLine(entityPlayer, "\u00a7cYou wish to challenge the might of the sun? You are mad. ");
                    this.chatLine(entityPlayer, "\u00a7cDo not further insult me or you will feel my wrath.");
                    this.setChatLine(8);
                } else if (this.getChatLine() == 8) {
                    this.chatLine(entityPlayer, "\u00a7cThis is your final warning. Leave now, or prepare to burn.");
                    this.setChatLine(9);
                } else {
                    if (this.getChatLine() == 9) {
                        this.chatLine(entityPlayer, "\u00a76As you wish, your death will be slow and agonizing.");
                        this.setChatLine(10);
                        return true;
                    }

                    if (this.getChatLine() == 10 && this.getAttackTarget() == null) {
                        this.chatLine(entityPlayer, "\u00a7cDid your previous death not satisfy your curiosity, human?");
                        this.setChatLine(9);
                    }
                }
            }
            else if (!AetherConfig.repeatSunSpiritDialogue() && ((PlayerAether) AetherAPI.get(entityPlayer)).seenSpiritDialog)
            {
                this.setChatLine(9);

                if (this.getChatLine() == 9) {
                    this.chatLine(entityPlayer, "\u00a76As you wish, your death will be slow and agonizing.");
                    this.setChatLine(10);
                    return true;
                }

                if (this.getChatLine() == 10 && this.getAttackTarget() == null) {
                    this.chatLine(entityPlayer, "\u00a7cDid your previous death not satisfy your curiosity, human?");
                    this.setChatLine(9);
                }
            }
        }

        return false;
    }

    @Override
    public boolean interact(EntityPlayer player) {
        if (this.chatWithMe(player)) {
            this.rotary = (180D / Math.PI) * Math.atan2(this.posX - player.posX, this.posZ - player.posZ);
            this.setAttackTarget(player);
            this.setDoor(BlocksAether.locked_hellfire_stone);

            return true;
        }

        return false;
    }

    @Override
    public void addVelocity(double x, double y, double z) {

    }

    @Override
    public void knockBack(Entity entity, float strength, double xRatio, double zRatio) {

    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.getEntity() instanceof EntityCrystal && ((EntityCrystal) source.getEntity()).getCrystalType() == EnumCrystalType.ICE) {
            this.velocity = 0.5D - (double) this.getHealth() / 70.0D * 0.2D;
            boolean flag = super.attackEntityFrom(source, amount);

            if (flag) {
                EntityFireMinion minion = new EntityFireMinion(this.worldObj);
                minion.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                minion.setAttackTarget(this.getAttackTarget());

                if (!this.worldObj.isRemote) {
                    this.worldObj.spawnEntityInWorld(minion);
                }
            }
            return flag;
        } else {
            return false;
        }
    }

    @Override
    protected void dropFewItems(boolean var1, int var2) {
        this.entityDropItem(new ItemStack(ItemsAether.dungeon_key, 1, 2), 0.5F);
    }

    @Override
    public EntityItem entityDropItem(ItemStack stack, float offsetY) {
        if (stack.stackSize != 0 && stack.getItem() != null) {
            EntityAetherItem entityitem = new EntityAetherItem(this.worldObj, this.posX, this.posY + (double) offsetY, this.posZ, stack);

            if (this.captureDrops)
                this.capturedDrops.add(entityitem);
            else
                this.worldObj.spawnEntityInWorld(entityitem);

            return entityitem;
        } else {
            return null;
        }
    }

    private void setDoor(Block block) {
        int x, y, z;

        if (this.direction / 2 == 0) {
            for (y = this.originPointY - 1; y < this.originPointY + 2; ++y) {
                for (z = this.originPointZ - 1; z < this.originPointZ + 2; ++z) {
                    if (this.worldObj.getBlock(this.originPointX + (this.direction == 0 ? -13 : 13), y, z) != block) {
                        this.worldObj.setBlock(this.originPointX + (this.direction == 0 ? -13 : 13), y, z, block);
                    }
                }
            }
        } else {
            for (y = this.originPointY - 1; y < this.originPointY + 2; ++y) {
                for (x = this.originPointX - 1; x < this.originPointX + 2; ++x) {
                    if (this.worldObj.getBlock(x, y, this.originPointZ + (this.direction == 3 ? 13 : -13)) != block) {
                        this.worldObj.setBlock(x, y, this.originPointZ + (this.direction == 3 ? 13 : -13), block);
                    }
                }
            }
        }
    }

    private void unlockTreasure() {
        int x, y, z;

        if (this.direction / 2 == 0) {
            for (y = this.originPointY - 1; y < this.originPointY + 2; ++y) {
                for (z = this.originPointZ - 1; z < this.originPointZ + 2; ++z) {
                    this.worldObj.setBlock(this.originPointX + (this.direction == 0 ? 13 : -13), y, z, Blocks.air);
                }
            }
        } else {
            for (y = this.originPointY - 1; y < this.originPointY + 2; ++y) {
                for (x = this.originPointX - 1; x < this.originPointX + 2; ++x) {
                    this.worldObj.setBlock(x, y, this.originPointZ + (this.direction == 3 ? -13 : 13), Blocks.air);
                }
            }
        }

        for (x = this.originPointX - 20; x < this.originPointX + 20; ++x) {
            for (y = this.originPointY - 3; y < this.originPointY + 6; ++y) {
                for (z = this.originPointZ - 20; z < this.originPointZ + 20; ++z) {
                    Block unlock_block = this.worldObj.getBlock(x, y, z);

                    if (unlock_block == BlocksAether.locked_hellfire_stone || unlock_block == BlocksAether.locked_light_hellfire_stone) {
                        this.worldObj.setBlock(x, y, z, ((BlockDungeonBase) unlock_block).getUnlockedBlock());
                    }
                }
            }
        }
    }

    public List<?> getPlayersInDungeon() {
        return this.worldObj.getEntitiesWithinAABBExcludingEntity(this.getAttackTarget(), AxisAlignedBB.getBoundingBox(this.originPointX, this.originPointY, this.originPointZ, this.originPointX, this.originPointY, this.originPointZ).expand(20, 3, 20));
    }

    public void setOriginPosition(int x, int y, int z) {
        this.originPointX = x;
        this.originPointY = y;
        this.originPointZ = z;
    }

    public int getChatLine() {
        return (int) this.dataWatcher.getWatchableObjectByte(18);
    }

    public void setChatLine(int lineNumber) {
        this.chatCount = 100;
        this.dataWatcher.updateObject(18, new Byte((byte) lineNumber));
    }

    public boolean isFreezing() {
        return this.dataWatcher.getWatchableObjectByte(19) == (byte) 1;
    }

    public void setFreezing(boolean isFreezing) {
        this.dataWatcher.updateObject(19, new Byte(isFreezing ? (byte) 1 : (byte) 0));
    }

    public void setBossName(String name) {
        this.dataWatcher.updateObject(20, name);
    }

    public String getName() {
        return this.dataWatcher.getWatchableObjectString(20);
    }

    @Override
    public String getBossName() {
        return this.dataWatcher.getWatchableObjectString(20) + ", the Sun Spirit";
    }

    @Override
    public float getBossHealth() {
        return this.getHealth();
    }

    @Override
    public float getMaxBossHealth() {
        return this.getMaxHealth();
    }

}