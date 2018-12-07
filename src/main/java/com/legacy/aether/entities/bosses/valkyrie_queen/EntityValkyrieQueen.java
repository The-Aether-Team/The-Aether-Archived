package com.legacy.aether.entities.bosses.valkyrie_queen;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import com.legacy.aether.Aether;
import com.legacy.aether.api.player.util.IAetherBoss;
import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.dungeon.BlockDungeonBase;
import com.legacy.aether.client.gui.dialogue.entity.GuiValkyrieDialogue;
import com.legacy.aether.entities.ai.EntityAIAttackContinuously;
import com.legacy.aether.entities.ai.valkyrie_queen.ValkyrieQueenAIWander;
import com.legacy.aether.entities.hostile.EntityAetherMob;
import com.legacy.aether.entities.projectile.crystals.EntityCrystal;
import com.legacy.aether.entities.util.AetherNameGen;
import com.legacy.aether.entities.util.EntityAetherItem;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.player.PlayerAether;
import com.legacy.aether.registry.achievements.AchievementsAether;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityValkyrieQueen extends EntityAetherMob implements IAetherBoss {

    private EntityAIAttackContinuously enhancedCombat = new EntityAIAttackContinuously(this, 0.65D);

    public int angerLevel;

    public int timeLeft, timeUntilTeleport, chatTime;

    public int dungeonX, dungeonY, dungeonZ;

    public int dungeonEntranceZ;

    public double safeX, safeY, safeZ;

    public float sinage;

    public double lastMotionY;

    public EntityValkyrieQueen(World world) {
        super(world);

        this.timeUntilTeleport = this.rand.nextInt(250);

        this.registerEntityAI();
        this.dataWatcher.updateObject(19, AetherNameGen.valkGen());
        this.safeX = posX;
        this.safeY = posY;
        this.safeZ = posZ;
    }

    public EntityValkyrieQueen(World world, double x, double y, double z) {
        this(world);
        this.safeX = posX = x;
        this.safeY = posY = y;
        this.safeZ = posZ = z;
    }

    @Override
    public void entityInit() {
        super.entityInit();

        this.dataWatcher.addObject(18, new Byte((byte) 0));
        this.dataWatcher.addObject(19, AetherNameGen.valkGen());
    }

    public void registerEntityAI() {
        this.targetTasks.addTask(0, this.enhancedCombat);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new ValkyrieQueenAIWander(this, 0.5D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F, 200.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(28.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.85D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(13.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500.0D);
    }

    @Override
    protected boolean isMovementBlocked() {
        return !this.isBossReady();
    }

    @Override
    public void addVelocity(double x, double y, double z) {
        if (this.isBossReady()) {
            super.addVelocity(x, y, z);
        }
    }

    public void swingArm() {
        if (!this.isSwingInProgress) {
            this.isSwingInProgress = true;
        }
    }

    private void becomeAngryAt(EntityLivingBase entity) {
        this.setTarget(entity);

        this.angerLevel = 200 + this.rand.nextInt(200);

        for (int k = this.dungeonZ + 2; k < this.dungeonZ + 23; k += 7) {
            Block block = this.worldObj.getBlock(this.dungeonX - 1, this.dungeonY, k);

            if (block != BlocksAether.locked_angelic_stone || block != BlocksAether.locked_light_angelic_stone) {
                this.dungeonEntranceZ = k;
                this.worldObj.setBlock(this.dungeonX - 1, this.dungeonY, k, BlocksAether.angelic_stone, 0, 2);
                this.worldObj.setBlock(this.dungeonX - 1, this.dungeonY, k + 1, BlocksAether.angelic_stone, 0, 2);
                this.worldObj.setBlock(this.dungeonX - 1, this.dungeonY + 1, k + 1, BlocksAether.angelic_stone, 0, 2);
                this.worldObj.setBlock(this.dungeonX - 1, this.dungeonY + 1, k, BlocksAether.angelic_stone, 0, 2);
                return;
            }
        }
    }

    public void setDungeon(int i, int j, int k) {
        this.dungeonX = i;
        this.dungeonY = j;
        this.dungeonZ = k - 19;
    }

    private void unlockDoor() {
        this.worldObj.setBlock(this.dungeonX - 1, this.dungeonY, this.dungeonEntranceZ, Blocks.air);
        this.worldObj.setBlock(this.dungeonX - 1, this.dungeonY, this.dungeonEntranceZ + 1, Blocks.air);
        this.worldObj.setBlock(this.dungeonX - 1, this.dungeonY + 1, this.dungeonEntranceZ + 1, Blocks.air);
        this.worldObj.setBlock(this.dungeonX - 1, this.dungeonY + 1, this.dungeonEntranceZ, Blocks.air);
    }

    private void unlockTreasure() {
        this.worldObj.setBlock(this.dungeonX + 16, dungeonY + 1, dungeonZ + 9, Blocks.trapdoor, 3, 2);
        this.worldObj.setBlock(this.dungeonX + 17, dungeonY + 1, dungeonZ + 9, Blocks.trapdoor, 2, 2);
        this.worldObj.setBlock(this.dungeonX + 16, dungeonY + 1, dungeonZ + 10, Blocks.trapdoor, 3, 2);
        this.worldObj.setBlock(this.dungeonX + 17, dungeonY + 1, dungeonZ + 10, Blocks.trapdoor, 2, 2);

        for (int x = this.dungeonX - 27; x < this.dungeonX + 30; x++) {
            for (int y = this.dungeonY - 1; y < this.dungeonY + 22; y++) {
                for (int z = this.dungeonZ - 6; z < this.dungeonZ + 26; z++) {
                    Block block = this.worldObj.getBlock(x, y, z);

                    if (block == BlocksAether.locked_angelic_stone || block == BlocksAether.locked_light_angelic_stone) {
                        this.worldObj.setBlock(x, y, z, ((BlockDungeonBase) block).getUnlockedBlock());
                    }
                }
            }
        }
    }

    private void chatItUp(EntityPlayer player, String s) {
        Side side = FMLCommonHandler.instance().getEffectiveSide();

        if (this.chatTime <= 0) {
            if (side.isClient()) {
                Aether.proxy.sendMessage(player, s);
            }

            this.chatTime = 60;
        }
    }

    public void makeHomeShot(int shots, EntityPlayer player) {
        for (int i = 0; i < shots; i++) {
            EntityCrystal crystal = new EntityCrystal(this.worldObj, this.posX - (this.motionX / 2D), this.posY, this.posZ - (this.motionZ / 2D), player);

            if (!this.worldObj.isRemote) {
                this.worldObj.spawnEntityInWorld(crystal);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void displayValkyrieDialogue() {
        if (this.worldObj.isRemote) {
            FMLClientHandler.instance().getClient().displayGuiScreen(new GuiValkyrieDialogue(this));
        }
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        this.faceEntity(entityplayer, 180F, 180F);

        if (this.isBossReady()) {
            this.chatItUp(entityplayer, "If you wish to challenge me, strike at any time.");
        } else if (this.worldObj.isRemote) {
            this.displayValkyrieDialogue();
        }

        return true;
    }

    @Override
    public void updateEntityActionState() {
        super.updateEntityActionState();

        if (!this.isBossReady()) {
            this.motionY *= .5f;
            this.moveStrafing = this.moveForward = 0;
        } else {
            if (this.getEntityToAttack() instanceof EntityPlayer) {
                if (this.timeUntilTeleport++ >= 450) {
                    if (this.onGround && this.rand.nextInt(10) == 0) {
                        this.makeHomeShot(1, (EntityPlayer) this.getEntityToAttack());
                    } else {
                        this.teleport(this.getEntityToAttack().posX, this.getEntityToAttack().posY, this.getEntityToAttack().posZ, 4);
                    }
                } else if (this.timeUntilTeleport < 446 && (this.posY <= 0D || this.posY <= (this.safeY - 16D))) {
                    this.timeUntilTeleport = 446;
                } else if ((this.timeUntilTeleport % 5) == 0 && !canEntityBeSeen(this.getEntityToAttack())) {
                    this.timeUntilTeleport += 100;
                }
            }
        }

        if (this.getEntityToAttack() != null && this.getEntityToAttack().isDead) {
            this.setTarget(null);
            unlockDoor();
            this.angerLevel = 0;
        }

        if (this.chatTime > 0) {
            this.chatTime--;
        }
    }

    @Override
    public void onUpdate() {
        this.lastMotionY = motionY;
        super.onUpdate();

        if (!this.onGround && this.getEntityToAttack() != null && this.lastMotionY >= 0.0D && motionY < 0.0D && getDistanceToEntity(this.getEntityToAttack()) <= 16F && canEntityBeSeen(this.getEntityToAttack())) {
            double a = this.getEntityToAttack().posX - posX;
            double b = this.getEntityToAttack().posZ - posZ;
            double angle = Math.atan2(a, b);
            this.motionX = Math.sin(angle) * 0.25D;
            this.motionZ = Math.cos(angle) * 0.25D;
        }

        if (!this.onGround && !isOnLadder() && Math.abs(this.motionY - this.lastMotionY) > 0.07D && Math.abs(this.motionY - this.lastMotionY) < 0.09D) {
            this.motionY += 0.055F;

            if (this.motionY < -0.275F) {
                this.motionY = -0.275F;
            }
        }

        if (this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL && (this.getEntityToAttack() != null || this.angerLevel > 0)) {
            this.angerLevel = 0;
            this.setTarget(null);
        }

        if (!this.onGround) {
            this.sinage += 0.75F;
        } else {
            this.sinage += 0.15F;
        }

        if (this.sinage > 3.141593F * 2F) {
            this.sinage -= (3.141593F * 2F);
        }

        if (this.getHealth() <= 0 || this.isDead) {
            this.unlockDoor();
            this.unlockTreasure();

            if (this.getEntityToAttack() instanceof EntityPlayer) {
                this.chatItUp((EntityPlayer) this.getEntityToAttack(), "You are truly... a mighty warrior...");

                ((EntityPlayer) this.getEntityToAttack()).triggerAchievement(AchievementsAether.defeat_silver);

                PlayerAether.get((EntityPlayer) this.getEntityToAttack()).setFocusedBoss(null);
            }

            this.spawnExplosionParticle();
            this.setDead();
        }

        if (!otherDimension()) {
            this.timeLeft--;
            if (this.timeLeft <= 0) {
                spawnExplosionParticle();
                this.setDead();
            }
        }
    }

    @Override
    protected Entity findPlayerToAttack() {
        return null;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setShort("Anger", (short) angerLevel);
        nbttagcompound.setShort("TimeLeft", (short) timeLeft);
        nbttagcompound.setBoolean("Duel", this.isBossReady());
        nbttagcompound.setInteger("DungeonX", this.dungeonX);
        nbttagcompound.setInteger("DungeonY", this.dungeonY);
        nbttagcompound.setInteger("DungeonZ", this.dungeonZ);
        nbttagcompound.setInteger("DungeonEntranceZ", this.dungeonEntranceZ);
        nbttagcompound.setTag("SafePos", newDoubleNBTList(new double[]{this.safeX, this.safeY, this.safeZ}));
        nbttagcompound.setString("BossName", this.getName());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        this.angerLevel = nbttagcompound.getShort("Anger");
        this.timeLeft = nbttagcompound.getShort("TimeLeft");
        this.setBossReady(nbttagcompound.getBoolean("Duel"));
        this.dungeonX = nbttagcompound.getInteger("DungeonX");
        this.dungeonY = nbttagcompound.getInteger("DungeonY");
        this.dungeonZ = nbttagcompound.getInteger("DungeonZ");
        this.dungeonEntranceZ = nbttagcompound.getInteger("DungeonEntranceZ");
        NBTTagList nbttaglist = nbttagcompound.getTagList("SafePos", 10);
        this.setBossName(nbttagcompound.getString("BossName"));

        this.safeX = nbttaglist.func_150309_d(0);
        this.safeY = nbttaglist.func_150309_d(1);
        this.safeZ = nbttaglist.func_150309_d(2);
    }

    @Override
    public boolean attackEntityFrom(DamageSource ds, float i) {
        if (ds.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) ds.getEntity();

            if (this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) {
                this.spawnExplosionParticle();
                chatItUp(player, "Sorry, I don't fight with weaklings.");
                return false;
            }

            if (!this.isBossReady()) {
                spawnExplosionParticle();
                int pokey = rand.nextInt(2);

                if (pokey == 2) {
                    chatItUp(player, "Sorry, I don't fight with weaklings.");
                } else {
                    chatItUp(player, "Try defeating some weaker valkyries first.");
                }
                return false;
            } else {
                PlayerAether playerAether = PlayerAether.get(player);
                boolean flag;

                if (playerAether != null) {
                    flag = true;

                    if (!player.isDead && flag) {
                        playerAether.setFocusedBoss(this);
                    }

                    if (this.isDead || this.getHealth() <= 0.0F) {
                        playerAether.setFocusedBoss(null);
                    }
                }

                if (this.getEntityToAttack() == null) {
                    this.chatTime = 0;
                    chatItUp(player, "This will be your final battle!");
                    if (ds.getEntity() instanceof EntityLivingBase)
                        becomeAngryAt((EntityLivingBase) ds.getEntity());
                } else {
                    this.timeUntilTeleport += 60;
                }
            }
        } else {
            extinguish();
            return false;
        }

        return super.attackEntityFrom(ds, i);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        boolean flag = false;

        this.swingArm();
        flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), 8);

        if (entity != null && this.getEntityToAttack() != null && entity == this.getEntityToAttack() && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            if (player.getHealth() <= 0 || player.isDead) {
                this.setTarget(null);
                this.angerLevel = this.chatTime = 0;
                this.chatItUp(player, "As expected of a human.");
                this.unlockDoor();
            }
        }

        return flag;
    }

    @Override
    protected void dropFewItems(boolean var1, int var2) {
        this.entityDropItem(new ItemStack(ItemsAether.dungeon_key, 1, 1), 0.5F);
        this.dropItem(Items.golden_sword, 1);
    }

    @Override
    public EntityItem entityDropItem(ItemStack stack, float offsetY) {
        if (stack.stackSize != 0 && stack.getItem() != null) {
            EntityAetherItem entityitem = new EntityAetherItem(this.worldObj, this.posX, this.posY + (double) offsetY, this.posZ, stack);

            if (captureDrops)
                this.capturedDrops.add(entityitem);
            else
                this.worldObj.spawnEntityInWorld(entityitem);
            return entityitem;
        } else {
            return null;
        }
    }

    @Override
    public void fall(float distance) {
    }

    public void teleport(double x, double y, double z, int rad) {
        int a = this.rand.nextInt(rad + 1);
        int b = this.rand.nextInt(rad / 2);
        int c = rad - a;

        a *= ((rand.nextInt(2) * 2) - 1); // Negate or Not
        b *= ((rand.nextInt(2) * 2) - 1); // Negate or Not
        c *= ((rand.nextInt(2) * 2) - 1); // Negate or Not

        x += (double) a;
        y += (double) b;
        z += (double) c;

        int newX = (int) Math.floor(x - 0.5D);
        int newY = (int) Math.floor(y - 0.5D);
        int newZ = (int) Math.floor(z - 0.5D);

        boolean flag = false;

        for (int q = 0; q < 32 && !flag; q++) {
            int i = newX + (this.rand.nextInt(rad / 2) - this.rand.nextInt(rad / 2));
            int j = newY + (this.rand.nextInt(rad / 2) - this.rand.nextInt(rad / 2));
            int k = newZ + (this.rand.nextInt(rad / 2) - this.rand.nextInt(rad / 2));

            if (this.isAirySpace(i, j, k) && this.isAirySpace(i, j + 1, k) && !this.isAirySpace(i, j - 1, k) && (i > dungeonX && i < dungeonX + 20 && j > dungeonY && j < dungeonY + 12 && k > dungeonZ && k < dungeonZ + 20)) {
                newX = i;
                newY = j;
                newZ = k;
                flag = true;
            }
        }

        if (!flag) {
            this.timeUntilTeleport -= (this.rand.nextInt(40) + 40);

            if (this.posY <= 0D) {
                this.timeUntilTeleport = 446;
            }
        } else {
            this.spawnExplosionParticle();
            this.enhancedCombat.resetTask();
            this.setPosition((double) newX + 0.5D, (double) newY + 0.5D, (double) newZ + 0.5D);

            this.isJumping = false;
            this.renderYawOffset = this.rand.nextFloat() * 360F;
            this.timeUntilTeleport = this.rand.nextInt(40);

            this.motionX = this.motionY = this.motionZ = this.moveForward = this.moveStrafing = this.rotationPitch = this.rotationYaw = 0;
        }
    }

    public boolean isAirySpace(int x, int y, int z) {
        Block block = this.worldObj.getBlock(x, y, z);

        return block == Blocks.air || block.getCollisionBoundingBoxFromPool(this.worldObj, x, y, z) == null;
    }

    public boolean otherDimension() {
        return true;
    }

    public boolean canDespawn() {
        return false;
    }

    @Override
    public boolean getCanSpawnHere() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);
        return this.worldObj.getBlockLightValue(i, j, k) > 8 && this.worldObj.checkBlockCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).size() == 0 && !this.worldObj.isAnyLiquid(this.boundingBox);
    }

    public int getMedals(EntityPlayer entityplayer) {
        int medals = 0;

        for (ItemStack item : entityplayer.inventory.mainInventory) {
            if (item != null) {
                if (item.getItem() == ItemsAether.victory_medal) {
                    medals += item.stackSize;
                }
            }
        }

        return medals;
    }

    public List<?> getPlayersInDungeon() {
        return this.worldObj.getEntitiesWithinAABBExcludingEntity(this.getEntityToAttack(), AxisAlignedBB.getBoundingBox(this.dungeonX, this.dungeonY, this.dungeonZ, this.dungeonX, this.dungeonY, this.dungeonZ).expand(20, 20, 20));
    }

    @Override
    protected String getHurtSound() {
        return "game.player.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "game.player.die";
    }

    public String getName() {
        return this.dataWatcher.getWatchableObjectString(19);
    }

    @Override
    public String getBossName() {
        return this.dataWatcher.getWatchableObjectString(19) + ", the Valkyrie Queen";
    }

    public void setBossName(String name) {
        this.dataWatcher.updateObject(19, name);
    }

    @Override
    public float getBossHealth() {
        return this.getHealth();
    }

    @Override
    public float getMaxBossHealth() {
        return this.getMaxHealth();
    }

    public void setBossReady(boolean isReady) {
        this.dataWatcher.updateObject(18, new Byte(isReady ? (byte) 1 : (byte) 0));
    }

    public boolean isBossReady() {
        return this.dataWatcher.getWatchableObjectByte(18) == (byte) 1;
    }

}