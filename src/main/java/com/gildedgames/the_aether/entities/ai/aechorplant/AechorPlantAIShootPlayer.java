package com.gildedgames.the_aether.entities.ai.aechorplant;

import com.gildedgames.the_aether.entities.hostile.EntityAechorPlant;
import com.gildedgames.the_aether.entities.projectile.EntityPoisonNeedle;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.EnumDifficulty;

public class AechorPlantAIShootPlayer extends EntityAIBase {

    private EntityAechorPlant shooter;

    private int reloadTime;

    public AechorPlantAIShootPlayer(EntityAechorPlant aechorplant) {
        this.shooter = aechorplant;
        this.setMutexBits(4);
    }

    @Override
    public boolean shouldExecute() {
        return !this.shooter.isDead && this.shooter.getEntityToAttack() != null;
    }

    @Override
    public void updateTask() {
        double distanceToPlayer = this.shooter.getEntityToAttack().getDistanceToEntity(this.shooter);
        double lookDistance = 5.5D + ((double) this.shooter.getSize() / 2D);

        if (this.shooter.getEntityToAttack().isDead || distanceToPlayer > lookDistance) {
            this.shooter.setTarget(null);
            this.reloadTime = 0;
        }

        if (this.reloadTime == 20 && this.shooter.canEntityBeSeen(this.shooter.getEntityToAttack())) {
            this.shootAtPlayer();
            this.reloadTime = -10;
        }

        if (this.reloadTime != 20) {
            ++this.reloadTime;
        }
    }

    public void shootAtPlayer() {
        if (this.shooter.worldObj.difficultySetting.equals(EnumDifficulty.PEACEFUL)) {
            return;
        }

        double x = this.shooter.getEntityToAttack().posX - this.shooter.posX;
        double z = this.shooter.getEntityToAttack().posZ - this.shooter.posZ;
        double y = 0.1D + (Math.sqrt((x * x) + (z * z) + 0.1D) * 0.5D) + ((this.shooter.posY - this.shooter.getEntityToAttack().posY) * 0.25D);

        double distance = 1.5D / Math.sqrt((x * x) + (z * z) + 0.1D);

        x = x * distance;
        z = z * distance;

        EntityPoisonNeedle poisonNeedle = new EntityPoisonNeedle(this.shooter.worldObj, this.shooter, 0.5F);

        poisonNeedle.posY = this.shooter.posY + 1D;

        this.shooter.playSound("random.bow", 1.0F, 1.2F / (this.shooter.getRNG().nextFloat() * 0.2F + 0.9F));
        this.shooter.worldObj.spawnEntityInWorld(poisonNeedle);

        poisonNeedle.setThrowableHeading(x, y, z, 0.285F + ((float) y * 0.05F), 1.0F);
    }

}