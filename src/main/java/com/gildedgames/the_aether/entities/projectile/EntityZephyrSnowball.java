package com.gildedgames.the_aether.entities.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.player.PlayerAether;

public class EntityZephyrSnowball extends EntityProjectileBase {

    public EntityZephyrSnowball(World world) {
        super(world);
    }

    public EntityZephyrSnowball(World world, EntityLivingBase thrower, double x, double y, double z) {
        super(world, thrower);

        this.setPosition(x, y, z);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);

        if (this.ticksInAir > 150) {
            this.setDead();
        } else {
            this.ticksInAir++;
        }
    }

    @Override
    protected void onImpact(MovingObjectPosition object) {
        if (object.entityHit instanceof EntityLivingBase) {
            if (object.entityHit instanceof EntityPlayer && PlayerAether.get((EntityPlayer) object.entityHit)
                .getAccessoryInventory()
                .wearingArmor(new ItemStack(ItemsAether.sentry_boots))) {
                this.setDead();

                return;
            }

            object.entityHit.motionX += this.motionX * 1.5F;
            object.entityHit.motionY += 0.5D;
            object.entityHit.motionZ += this.motionZ * 1.5F;

            if (object.entityHit instanceof EntityPlayerMP) {
                ((EntityPlayerMP) object.entityHit).playerNetServerHandler
                    .sendPacket(new S12PacketEntityVelocity(object.entityHit));
            }

            this.setDead();
        }
    }

    @Override
    protected float getGravityVelocity() {
        return 0.0F;
    }

}
