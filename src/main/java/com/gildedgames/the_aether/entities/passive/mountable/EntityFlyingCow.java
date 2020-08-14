package com.gildedgames.the_aether.entities.passive.mountable;

import com.gildedgames.the_aether.items.ItemsAether;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.gildedgames.the_aether.entities.util.EntitySaddleMount;

public class EntityFlyingCow extends EntitySaddleMount {

    public float wingFold;

    public float wingAngle;

    private float aimingForFold;

    public int maxJumps;

    public int jumpsRemaining;

    private int ticks;

    public EntityFlyingCow(World world) {
        super(world);

        this.ticks = 0;
        this.maxJumps = 1;
        this.jumpsRemaining = 0;
        this.stepHeight = 1.0F;
        this.ignoreFrustumCheck = true;
        this.canJumpMidAir = true;

        this.setSize(0.9F, 1.3F);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 2.0D));
        this.tasks.addTask(3, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.25D, ItemsAether.blueberry, false));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.isOnGround()) {
            this.wingAngle *= 0.8F;
            this.aimingForFold = 0.1F;
            this.jumpsRemaining = this.maxJumps;
        } else {
            this.aimingForFold = 1.0F;
        }

        this.ticks++;

        this.wingAngle = this.wingFold * (float) Math.sin(this.ticks / 31.83098862F);
        this.wingFold += (this.aimingForFold - this.wingFold) / 5F;
        this.fallDistance = 0;
        this.fall();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        compound.setInteger("maxJumps", (short) this.maxJumps);
        compound.setInteger("jumpsRemaining", (short) this.jumpsRemaining);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        this.maxJumps = compound.getInteger("maxJumps");
        this.jumpsRemaining = compound.getInteger("jumpsRemaining");
    }

    @Override
    public double getMountedYOffset() {
        return 1.15D;
    }

    @Override
    public float getMountedMoveSpeed() {
        return 0.3F;
    }

    @Override
    protected void jump() {
        if (this.riddenByEntity == null) {
            super.jump();
        }
    }

    private void fall() {
        if (!this.onGround) {
            if (this.motionY < 0.0D && !this.isRiderSneaking()) {
                this.motionY *= 0.6D;
            }

            if (this.onGround && !this.worldObj.isRemote) {
                this.jumpsRemaining = this.maxJumps;
            }
        }
    }

    @Override
    public boolean interact(EntityPlayer player) {
        ItemStack stack = player.getCurrentEquippedItem();

        if (stack != null) {
            ItemStack currentStack = stack;

            if (currentStack.getItem() == Items.bucket) {
                Item milk = Items.milk_bucket;

                if (stack != null && stack.stackSize == 1) {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(milk));
                } else if (!player.inventory.addItemStackToInventory(new ItemStack(milk))) {
                    if (!this.worldObj.isRemote) {
                        this.worldObj.spawnEntityInWorld(new EntityItem(worldObj, player.posX, player.posY, player.posZ, new ItemStack(milk)));

                        if (!player.capabilities.isCreativeMode) {
                            --stack.stackSize;
                        }
                    }
                } else if (!player.capabilities.isCreativeMode) {
                    --stack.stackSize;
                }
            }
        }

        return super.interact(player);
    }

    @Override
    protected String getLivingSound() {
        return "mob.cow.say";
    }

    @Override
    protected String getHurtSound() {
        return "mob.cow.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.cow.hurt";
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    protected void func_145780_a(int x, int y, int z, Block block) {
        this.playSound("mob.cow.step", 0.15F, 1.0F);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entityageable) {
        return new EntityFlyingCow(this.worldObj);
    }

    @Override
    protected void dropFewItems(boolean recentlyHit, int lootLevel) {
        int j = this.rand.nextInt(3) + this.rand.nextInt(1 + lootLevel);
        int k;

        for (k = 0; k < j; ++k) {
            this.dropItem(Items.leather, 1);
        }

        j = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + lootLevel);

        for (k = 0; k < j; ++k) {
            if (this.isBurning()) {
                this.dropItem(Items.cooked_beef, 1);
            } else {
                this.dropItem(Items.beef, 1);
            }
        }

        super.dropFewItems(recentlyHit, lootLevel);
    }

    @Override
    protected double getMountJumpStrength() {
        return 5.0D;
    }

}