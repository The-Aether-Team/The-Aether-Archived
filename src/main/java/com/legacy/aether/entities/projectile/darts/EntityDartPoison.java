package com.legacy.aether.entities.projectile.darts;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import com.legacy.aether.api.player.util.IAetherBoss;
import com.legacy.aether.entities.hostile.EntityAechorPlant;
import com.legacy.aether.entities.hostile.EntityCockatrice;
import com.legacy.aether.entities.movement.AetherPoisonMovement;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.network.AetherNetwork;
import com.legacy.aether.network.packets.PacketSendPoison;
import com.legacy.aether.player.PlayerAether;

public class EntityDartPoison extends EntityDartBase {

    public EntityLivingBase victim;

    public AetherPoisonMovement poison;

    public EntityDartPoison(World worldIn) {
        super(worldIn);
    }

    public EntityDartPoison(World world, EntityLivingBase entity, float velocity) {
        super(world, entity, velocity);
    }

    public void entityInit() {
        super.entityInit();
        this.setDamage(0);
    }

    public void onUpdate() {
        super.onUpdate();

        if (this.victim != null) {
            if (this.victim.isDead || this.poison.ticks == 0) {
                this.setDead();

                return;
            }

            if (this.getThrower() != null) {
                if (this.getThrower().worldObj instanceof WorldServer) {
                    ((WorldServer) this.getThrower().worldObj).func_147487_a("iconcrack_" + Item.getIdFromItem(Items.dye) + "_" + 1, this.victim.posX, this.victim.boundingBox.minY + this.victim.height * 0.8D, this.victim.posZ, 2, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            }

            this.isDead = false;
            this.poison.onUpdate();
            this.setInvisible(true);
            this.setPosition(this.victim.posX, this.victim.posY, this.victim.posZ);
        }
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entity) {
        if (this.victim == null) {
            super.onCollideWithPlayer(entity);
        }
    }

    @Override
    public void onDartHit(MovingObjectPosition movingobjectposition) {
        super.onDartHit(movingobjectposition);

        if (movingobjectposition.entityHit instanceof EntityLivingBase) {
            Entity entity = movingobjectposition.entityHit;

            if (entity instanceof EntityPlayer) {
                EntityPlayer ent = (EntityPlayer) entity;

                if (!this.worldObj.isRemote) {
                    PlayerAether.get(ent).inflictPoison(500);
                    AetherNetwork.sendTo(new PacketSendPoison(), (EntityPlayerMP) ent);
                }
            } else if (!(entity instanceof IAetherBoss) && !(entity instanceof EntityCockatrice) && !(entity instanceof EntityAechorPlant)) {
                this.victim = (EntityLivingBase) entity;
                this.poison = new AetherPoisonMovement(this.victim);
                this.poison.inflictPoison(500);
            }

            this.isDead = false;
        }
    }

    @Override
    protected ItemStack getStack() {
        return new ItemStack(ItemsAether.dart, 1, 1);
    }

}