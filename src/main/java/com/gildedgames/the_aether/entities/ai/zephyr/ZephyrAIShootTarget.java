package com.gildedgames.the_aether.entities.ai.zephyr;

import com.gildedgames.the_aether.entities.hostile.EntityZephyr;
import com.gildedgames.the_aether.entities.projectile.EntityZephyrSnowball;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ZephyrAIShootTarget extends EntityAIBase {

    private EntityZephyr zephyr;

    private World worldObj;

    private final float base;

    public int prevAttackCounter;

    public int attackCounter;

    public ZephyrAIShootTarget(EntityZephyr zephyr) {
        this.zephyr = zephyr;
        this.worldObj = zephyr.worldObj;
        this.attackCounter = 0;
        this.base = (this.zephyr.getRNG().nextFloat() - this.zephyr.getRNG().nextFloat()) * 0.2F + 1.0F;
        this.setMutexBits(4);
    }

    @Override
    public boolean shouldExecute() {
        return !this.zephyr.isDead;
    }

    @Override
    public void updateTask() {
        this.prevAttackCounter = this.attackCounter;

        if (this.zephyr.getAttackTarget() == null) {
            if (this.attackCounter > 0) {
                this.attackCounter--;
            }

            this.zephyr.setAttackTarget(this.worldObj.getClosestVulnerablePlayerToEntity(this.zephyr, 100D));
        } else {
            if (this.zephyr.getAttackTarget() instanceof EntityPlayer && (((EntityPlayer) this.zephyr.getAttackTarget()).capabilities.isCreativeMode)) {
                this.zephyr.setAttackTarget(null);
                return;
            }

            if (this.zephyr.getAttackTarget().getDistanceSqToEntity(this.zephyr) < 4096.0D && this.zephyr.canEntityBeSeen(this.zephyr.getAttackTarget())) {
                double x = this.zephyr.getAttackTarget().posX - this.zephyr.posX;
                double y = (this.zephyr.getAttackTarget().boundingBox.minY + (this.zephyr.getAttackTarget().height / 2.0F)) - (this.zephyr.posY + (this.zephyr.height / 2.0F));
                double z = this.zephyr.getAttackTarget().posZ - this.zephyr.posZ;

                this.zephyr.rotationYaw = (-(float) Math.atan2(x, z) * 180F) / 3.141593F;

                ++this.attackCounter;

                if (this.attackCounter == 10) {
                    this.zephyr.playSound("aether_legacy:aemob.zephyr.call", 3F, this.base);
                } else if (this.attackCounter == 20) {
                    this.zephyr.playSound("aether_legacy:aemob.zephyr.call", 3F, this.base);

                    EntityZephyrSnowball projectile = new EntityZephyrSnowball(this.worldObj, this.zephyr, x, y, z);
                    Vec3 lookVector = this.zephyr.getLook(1.0F);

                    projectile.posX = this.zephyr.posX + lookVector.xCoord * 4D;
                    projectile.posY = this.zephyr.posY + (double) (this.zephyr.height / 2.0F) + 0.5D;
                    projectile.posZ = this.zephyr.posZ + lookVector.zCoord * 4D;

                    if (!this.worldObj.isRemote) {
                        projectile.setThrowableHeading(x, y, z, 1.2F, 1.0F);
                        this.worldObj.spawnEntityInWorld(projectile);
                    }

                    this.attackCounter = -40;
                }
            } else if (this.attackCounter > 0) {
                this.attackCounter--;
            }
        }
    }

}