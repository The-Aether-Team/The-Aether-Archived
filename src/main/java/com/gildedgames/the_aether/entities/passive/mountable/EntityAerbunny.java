package com.gildedgames.the_aether.entities.passive.mountable;

import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.entities.passive.EntityAetherAnimal;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.gildedgames.the_aether.entities.ai.aerbunny.AerbunnyAIHop;
import com.gildedgames.the_aether.player.PlayerAether;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityAerbunny extends EntityAetherAnimal implements IEntityAdditionalSpawnData {

    private int puff;

    public int puffiness;

    private int jumpTicks;

    private int jumps;

    public EntityAerbunny(World world) {
        super(world);

        this.ignoreFrustumCheck = true;

        this.setSize(0.4F, 0.4F);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIWander(this, 2D));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
        this.tasks.addTask(6, new AerbunnyAIHop(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(5.0D);
        this.setHealth(5.0F);
    }

    @Override
    public boolean isInRangeToRenderDist(double par1) {
        return true;
    }

    @Override
    public double getYOffset() {
        return 0.4D;
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.rand.nextInt(AetherConfig.getAerbunnySpawnrate()) == 0 && super.getCanSpawnHere();
    }

    @Override
    protected void jump() {
        this.spawnExplosionParticle();
        this.setPuffiness(11);
        --this.jumps;
        super.jump();
    }

    @Override
    public void spawnExplosionParticle() {
        for (int i = 0; i < 5; ++i) {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            double d3 = 10.0D;
            this.worldObj.spawnParticle("explode", this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width - d0 * d3, this.posY + (double) (this.rand.nextFloat() * this.height) - d1 * d3, this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width - d2 * d3, d0, d1, d2);
        }
    }

    public int getPuffinessClient() {
        return this.puffiness;
    }

    public int getPuffiness() {
        return this.puff;
    }

    public void setPuffinessClient(int i) {
        this.puffiness = i;
    }

    public void setPuffiness(int i) {
        this.puff = i;
    }

    public void onUpdate() {
        this.setPuffinessClient(this.getPuffinessClient() - 1);
        this.setPuffiness(this.getPuffiness() - 1);

        if (this.getPuffinessClient() < 0) {
            this.setPuffinessClient(0);
        }

        if (this.getPuffiness() < 0) {
            this.setPuffiness(0);
        }

        super.onUpdate();
    }

    public void onLivingUpdate() {
        if (this.onGround) {
            this.jumps = 1;
            this.jumpTicks = 10;
        } else if (this.jumpTicks > 0) {
            --this.jumpTicks;
        }

        if (this.onGround) {
            this.jump();
        }

        if (this.isJumping && !this.isInWater() && !this.handleLavaMovement() && !this.onGround && this.jumpTicks == 0 && this.jumps > 0) {
            if (this.moveForward != 0.0F) {
                //this.jump();
            }

            this.jumpTicks = 10;
        }

        if (this.motionY < -0.1D) {
            this.motionY = -0.1D;
        }

        if (this.ridingEntity != null && this.ridingEntity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) this.ridingEntity;

            if (this.worldObj.isRemote) {
                for (int k = 0; k < 3; k++) {
                    double d2 = (float) this.posX + this.rand.nextFloat() * 0.25F;
                    double d5 = (float) this.posY + this.height + 0.125F;
                    double d8 = (float) this.posZ + this.rand.nextFloat() * 0.25F;
                    float f1 = this.rand.nextFloat() * 360F;
                    this.worldObj.spawnParticle("smoke", -Math.sin(0.01745329F * f1) * 0.75D, d5 - 0.25D, Math.cos(0.01745329F * f1) * 0.75D, d2, 0.125D, d8);
                }
            }

            this.getNavigator().clearPathEntity();

            this.setRotation(player.rotationYaw, player.rotationPitch);

            player.fallDistance = 0.0F;

            if (!player.onGround) {
                if (!player.capabilities.isFlying) {
                    player.motionY += 0.05000000074505806D;
                }

                player.fallDistance = 0.0F;

                if (player.motionY < -0.22499999403953552D) {
                    if (PlayerAether.get((EntityPlayer) this.ridingEntity).isJumping()) {
                        this.setPuffinessClient(11);
                        this.spawnExplosionParticle();
                        player.motionY = 0.125D;
                    }
                }
            }
        }

        super.onLivingUpdate();
    }

    @Override
    public void fall(float distance) {
    }

    @Override
    public boolean isEntityInsideOpaqueBlock() {
        return false;
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

        if (itemstack != null && (itemstack.getItem() == Items.name_tag)) {
            return super.interact(entityplayer);
        } else {
            this.worldObj.playSound(this.posX, this.posY, this.posZ, "aether_legacy:aemob.aerbunny.lift", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F, false);

            if (this.isRiding()) {
                this.mountEntity(null);
            } else {
                this.mountEntity(entityplayer);
            }

            return true;
        }
    }

    @Override
    protected void dropFewItems(boolean recentlyHit, int var2) {
        this.dropItem(Items.string, 1);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        return source.getEntity() == this.ridingEntity ? false : super.attackEntityFrom(source, damage);
    }

    @Override
    protected boolean canTriggerWalking() {
        return this.onGround;
    }

    @Override
    protected String getHurtSound() {
        return "aether_legacy:aemob.aerbunny.hurt";
    }

    @Override
    protected String getDeathSound() {
        return "aether_legacy:aemob.aerbunny.death";
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entityageable) {
        return new EntityAerbunny(this.worldObj);
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeInt(this.getPuffiness());
    }

    @Override
    public void readSpawnData(ByteBuf buffer) {
        this.setPuffiness(buffer.readInt());
    }

}