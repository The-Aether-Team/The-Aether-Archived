package com.gildedgames.the_aether.entities.passive.mountable;

import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.moa.AetherMoaType;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.gildedgames.the_aether.entities.util.EntitySaddleMount;
import com.gildedgames.the_aether.items.ItemMoaEgg;
import com.gildedgames.the_aether.items.ItemsAether;

public class EntityMoa extends EntitySaddleMount {

    public float wingRotation, destPos, prevDestPos, prevWingRotation;

    protected int ticksOffGround, ticksUntilFlap, secsUntilFlying, secsUntilWalking, secsUntilHungry, secsUntilEgg;

    public EntityMoa(World world) {
        super(world);

        this.initAI();

        this.setSize(1.0F, 2.0F);
        this.stepHeight = 1.0F;

        this.secsUntilEgg = this.getRandomEggTime();
    }

    public EntityMoa(World world, AetherMoaType type) {
        this(world);

        this.setMoaType(type);
    }

    @Override
    public void moveEntity(double x, double y, double z) {
        if (!this.isSitting()) {
            super.moveEntity(x, y, z);
        } else {
            super.moveEntity(0, y, 0);
        }
    }

    public int getRandomEggTime() {
        return 775 + this.rand.nextInt(50);
    }

    public void initAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIWander(this, 0.30F));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.tasks.addTask(6, new EntityAIMate(this, 0.25F));
    }

    @Override
    public void entityInit() {
        super.entityInit();

        AetherMoaType moaType = AetherAPI.instance().getRandomMoaType();

        this.dataWatcher.addObject(20, new Short((short) AetherAPI.instance().getMoaTypeId(moaType)));
        this.dataWatcher.addObject(21, new Byte((byte) moaType.getMoaProperties().getMaxJumps()));
        this.dataWatcher.addObject(22, new Byte((byte) 0));
        this.dataWatcher.addObject(23, new Byte((byte) 0));
        this.dataWatcher.addObject(24, new Byte((byte) 0));
        this.dataWatcher.addObject(25, new Byte((byte) 0));

    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(35.0D);
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.rand.nextInt(AetherConfig.getMoaSpawnrate()) == 0 && super.getCanSpawnHere();
    }

    public boolean isSitting() {
        return this.dataWatcher.getWatchableObjectByte(25) == (byte) 1;
    }

    public void setSitting(boolean isSitting) {
        this.dataWatcher.updateObject(25, (byte) (isSitting ? 1 : 0));
    }

    public boolean isHungry() {
        return this.dataWatcher.getWatchableObjectByte(24) == (byte) 1;
    }

    public void setHungry(boolean hungry) {
        this.dataWatcher.updateObject(24, (byte) (hungry ? 1 : 0));
    }

    public byte getAmountFed() {
        return this.dataWatcher.getWatchableObjectByte(23);
    }

    public void setAmountFed(int amountFed) {
        this.dataWatcher.updateObject(23, (byte) amountFed);
    }

    public void increaseAmountFed(int amountFed) {
        int amount = (int) this.getAmountFed();

        this.setAmountFed(amount + amountFed);
    }

    public boolean isPlayerGrown() {
        return this.dataWatcher.getWatchableObjectByte(22) == (byte) 1;
    }

    public void setPlayerGrown(boolean playerGrown) {
        this.dataWatcher.updateObject(22, (byte) (playerGrown ? 1 : 0));
    }

    public int getMaxJumps() {
        return this.getMoaType().getMoaProperties().getMaxJumps();
    }

    public int getRemainingJumps() {
        return (int) this.dataWatcher.getWatchableObjectByte(21);
    }


    public void setRemainingJumps(int jumps) {
        this.dataWatcher.updateObject(21, (byte) jumps);
    }

    public int getMoaTypeId() {
        return (int) this.dataWatcher.getWatchableObjectShort(20);
    }

    public AetherMoaType getMoaType() {
        int id = (int) this.dataWatcher.getWatchableObjectShort(20);

        return AetherAPI.instance().getMoaType(id);
    }

    public void setMoaType(AetherMoaType type) {
        this.dataWatcher.updateObject(20, new Short((short) AetherAPI.instance().getMoaTypeId(type)));
        this.dataWatcher.updateObject(21, new Byte((byte) type.getMoaProperties().getMaxJumps()));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.isJumping) {
            this.motionY += 0.05F;
        }

        this.updateWingRotation();
        this.fall();

        if (this.secsUntilHungry > 0) {
            if (this.ticksExisted % 20 == 0) {
                this.secsUntilHungry--;
            }
        } else if (!this.isHungry()) {
            this.setHungry(true);
        }

        if (!this.worldObj.isRemote && !this.isChild() && this.riddenByEntity == null) {
            if (this.secsUntilEgg > 0) {
                if (this.ticksExisted % 20 == 0) {
                    this.secsUntilEgg--;
                }
            } else {
                this.playSound("mob.chicken.plop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
                this.entityDropItem(ItemMoaEgg.getStackFromType(this.getMoaType()), 0);

                this.secsUntilEgg = this.getRandomEggTime();
            }
        }

        this.fallDistance = 0.0F;
    }

    public void resetHunger() {
        if (!this.worldObj.isRemote) {
            this.setHungry(false);
        }

        this.secsUntilHungry = 40 + this.rand.nextInt(40);
    }

    public void updateWingRotation() {
        if (!this.onGround) {
            if (this.ticksUntilFlap == 0) {
                this.worldObj.playSoundAtEntity(this, "mob.bat.takeoff", 0.15F, MathHelper.clamp_float(this.rand.nextFloat(), 0.7f, 1.0f) + MathHelper.clamp_float(this.rand.nextFloat(), 0f, 0.3f));

                this.ticksUntilFlap = 8;
            } else {
                this.ticksUntilFlap--;
            }
        }

        this.prevWingRotation = this.wingRotation;
        this.prevDestPos = this.destPos;

        this.destPos += 0.2D;
        this.destPos = minMax(0.01F, 1.0F, this.destPos);

        if (this.isOnGround()) {
            this.destPos = 0.0F;
        }

        this.wingRotation += 1.233F;
    }

    public static float minMax(float min, float max, float value) {
        return Math.min(max, Math.max(min, value));
    }

    @Override
    public void onMountedJump(float par1, float par2) {
        if (this.getRemainingJumps() > 0 && this.motionY < 0.0D) {
            if (!this.isOnGround()) {
                this.motionY = 0.7D;
                this.worldObj.playSoundAtEntity(this, "mob.bat.takeoff", 0.15F, MathHelper.clamp_float(this.rand.nextFloat(), 0.7f, 1.0f) + MathHelper.clamp_float(this.rand.nextFloat(), 0f, 0.3f));

                if (!this.worldObj.isRemote) {
                    this.setRemainingJumps(this.getRemainingJumps() - 1);
                }

                this.spawnExplosionParticle();
            } else {
                this.motionY = 0.89D;
            }
        }
    }

    @Override
    public float getMountedMoveSpeed() {
        return this.getMoaType().getMoaProperties().getMoaSpeed();
    }

    public void setToAdult() {
        this.setGrowingAge(0);
    }

    @Override
    public boolean interact(EntityPlayer player) {
        ItemStack stack = player.getCurrentEquippedItem();

        if (stack != null && this.isPlayerGrown() && !this.worldObj.isRemote) {
            Item currentItem = stack.getItem();

            if (this.isChild() && this.isHungry()) {
                if (this.getAmountFed() < 3 && currentItem == ItemsAether.aechor_petal) {
                    if (!player.capabilities.isCreativeMode) {
                        --stack.stackSize;
                    }

                    this.increaseAmountFed(1);

                    if (this.getAmountFed() >= 3) {
                        this.setToAdult();
                    } else {
                        this.resetHunger();
                    }
                }
            }

            if (currentItem == ItemsAether.nature_staff) {
                stack.damageItem(2, player);

                this.setSitting(this.isSitting() ? false : true);

                return true;
            }
        }

        return super.interact(player);
    }

    @Override
    public boolean canSaddle() {
        return !this.isChild() && this.isPlayerGrown();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);

        nbt.setBoolean("playerGrown", this.isPlayerGrown());
        nbt.setInteger("remainingJumps", this.getRemainingJumps());
        nbt.setInteger("moaTypeId", this.getMoaTypeId());
        nbt.setByte("amountFed", this.getAmountFed());
        nbt.setBoolean("isHungry", this.isHungry());
        nbt.setBoolean("isSitting", this.isSitting());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);

        this.setPlayerGrown(nbt.getBoolean("playerGrown"));
        this.setRemainingJumps(nbt.getInteger("remainingJumps"));
        this.setMoaType(AetherAPI.instance().getMoaType(nbt.getInteger("moaTypeId")));
        this.setAmountFed(nbt.getByte("amountFed"));
        this.setHungry(nbt.getBoolean("isHungry"));
        this.setSitting(nbt.getBoolean("isSitting"));
    }

    @Override
    protected String getLivingSound() {
        return "aether_legacy:aemob.moa.say";
    }

    @Override
    protected String getHurtSound() {
        return "aether_legacy:aemob.moa.say";
    }

    @Override
    protected String getDeathSound() {
        return "aether_legacy:aemob.moa.say";
    }

    @Override
    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
        this.playSound("mob.pig.step", 0.15F, 1.0F);
    }

    @Override
    protected void dropFewItems(boolean recentlyHit, int lootLevel) {
        super.dropFewItems(recentlyHit, lootLevel);

        this.dropItem(Items.feather, 3);
    }

    public void fall() {
        int x = MathHelper.floor_double(this.posX);
        int y = MathHelper.floor_double(this.posY);
        int z = MathHelper.floor_double(this.posZ);
        boolean blockBeneath = !this.worldObj.isAirBlock(x, y - 1, z);

        if (this.motionY < 0.0D && !this.isRiderSneaking()) {
            this.motionY *= 0.6D;
        }

        if (blockBeneath) {
            this.setRemainingJumps(this.getMaxJumps());
        }
    }

    @Override
    public void jump() {
        if (!this.isSitting() && this.riddenByEntity == null) {
            super.jump();
        }
    }

    @Override
    public double getMountedYOffset() {
        return 1.25D;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable matingAnimal) {
        return new EntityMoa(this.worldObj, this.getMoaType());
    }

}