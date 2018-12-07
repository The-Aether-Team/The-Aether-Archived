package com.legacy.aether.entities.projectile.crystals;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import com.legacy.aether.api.player.util.IAetherBoss;
import com.legacy.aether.entities.bosses.EntityFireMinion;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityCrystal extends EntityFlying implements IEntityAdditionalSpawnData {

    public float[] sinage = new float[3];

    public double smotionX;

    public double smotionY;

    public double smotionZ;

    public boolean wasHit;

    private EnumCrystalType type;

    public EntityCrystal(World world) {
        super(world);

        double base = 0.2F;

        this.smotionX = (base + (double) this.rand.nextFloat() * 0.15D) * (this.rand.nextInt(2) == 0 ? 1.0D : -1.0D);
        this.smotionY = (base + (double) this.rand.nextFloat() * 0.15D) * (this.rand.nextInt(2) == 0 ? 1.0D : -1.0D);
        this.smotionZ = (base + (double) this.rand.nextFloat() * 0.15D) * (this.rand.nextInt(2) == 0 ? 1.0D : -1.0D);

        this.isImmuneToFire = true;
        this.type = EnumCrystalType.get(this.rand.nextInt(2));

        for (int var2 = 0; var2 < this.sinage.length; ++var2) {
            this.sinage[var2] = this.rand.nextFloat() * 6.0F;
        }

        this.setSize(0.9F, 0.9F);
    }

    public EntityCrystal(World world, double x, double y, double z, EnumCrystalType type) {
        this(world);

        this.type = type;

        if (this.type == EnumCrystalType.ICE) {
            this.smotionX /= 3.0D;
            this.smotionY = 0.0D;
            this.smotionZ /= 3.0D;
        }

        this.setPosition(x, y, z);
    }

    public EntityCrystal(World world, double x, double y, double z, EntityLivingBase target) {
        this(world, x, y, z, EnumCrystalType.THUNDER);

        this.setAttackTarget(target);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.0D);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.type != EnumCrystalType.THUNDER && !this.worldObj.isRemote) {
            this.motionX = this.smotionX;
            this.motionY = this.smotionY;
            this.motionZ = this.smotionZ;
        }

        if (this.type == EnumCrystalType.THUNDER) {
            if (!this.worldObj.isRemote) {
                if (this.getAttackTarget() == null || (this.getAttackTarget() != null && !this.getAttackTarget().isEntityAlive())) {
                    this.setDead();
                }

                this.faceEntity(this.getAttackTarget(), 10F, 10F);
                this.moveTowardsTarget(this.getAttackTarget(), 0.02D);
            }
        } else if (this.isCollided && !this.worldObj.isRemote) {
            if (this.wasHit()) {
                this.explode();
                this.expire();
                this.setDead();
            }

            int var1 = MathHelper.floor_double(this.posX);
            int var2 = MathHelper.floor_double(this.boundingBox.minY);
            int var3 = MathHelper.floor_double(this.posZ);

            if (this.smotionX > 0.0D && !this.worldObj.isAirBlock(var1 + 1, var2, var3)) {
                this.motionX = this.smotionX = -this.smotionX;
            } else if (this.smotionX < 0.0D && !this.worldObj.isAirBlock(var1 - 1, var2, var3)) {
                this.motionX = this.smotionX = -this.smotionX;
            }

            if (this.smotionY > 0.0D && !this.worldObj.isAirBlock(var1, var2 + 1, var3)) {
                this.motionY = this.smotionY = -this.smotionY;
            } else if (this.smotionY < 0.0D && !this.worldObj.isAirBlock(var1, var2 - 1, var3)) {
                this.motionY = this.smotionY = -this.smotionY;
            }

            if (this.smotionZ > 0.0D && !this.worldObj.isAirBlock(var1, var2, var3 + 1)) {
                this.motionZ = this.smotionZ = -this.smotionZ;
            } else if (this.smotionZ < 0.0D && !this.worldObj.isAirBlock(var1, var2, var3 - 1)) {
                this.motionZ = this.smotionZ = -this.smotionZ;
            }
        }

        if (this.ticksExisted >= this.maxTicksAlive()) {
            if (this.type == EnumCrystalType.THUNDER) {
                this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, this.posX, this.posY, this.posZ));
            }

            this.expire();
            this.setDead();
        }

        for (int var1 = 0; var1 < this.sinage.length; ++var1) {
            this.sinage[var1] += 0.3F + (float) var1 * 0.13F;

            if (this.sinage[var1] > ((float) Math.PI * 2F)) {
                this.sinage[var1] -= ((float) Math.PI * 2F);
            }
        }
    }

    @Override
    public void applyEntityCollision(Entity entity) {
        super.applyEntityCollision(entity);

        if (entity instanceof EntityCrystal && this.worldObj.difficultySetting == EnumDifficulty.HARD) {
            EntityCrystal crystal = (EntityCrystal) entity;

            if (this.type != crystal.type) {
                this.explode();
                this.expire();
                this.setDead();
                crystal.explode();
                crystal.expire();
                crystal.setDead();
            }
        } else if (entity instanceof EntityLivingBase) {
            if (this.type == EnumCrystalType.FIRE && !(entity instanceof IAetherBoss) && !(entity instanceof EntityFireMinion)) {
                if (entity.attackEntityFrom(DamageSource.causeMobDamage(this), 5.0F)) {
                    this.explode();
                    this.expire();
                    this.setDead();
                    entity.setFire(100);
                }
            } else if (this.type == EnumCrystalType.ICE && this.wasHit()) {
                if (entity.attackEntityFrom(DamageSource.causeMobDamage(this), 5.0F)) {
                    this.explode();
                    this.expire();
                    this.setDead();
                    ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.weakness.id, 10));
                }
            } else if (this.type == EnumCrystalType.CLOUD && !(entity instanceof IAetherBoss)) {
                this.explode();
                this.expire();
                this.setDead();
                entity.attackEntityFrom(DamageSource.causeMobDamage(this), 5.0F);
            } else if (this.type == EnumCrystalType.THUNDER && entity == this.getAttackTarget()) {
                if (entity.attackEntityFrom(DamageSource.causeMobDamage(this), 1.0F)) {
                    this.moveTowardsTarget(entity, -0.3D);
                }
            }
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        if (source.getEntity() != null) {
            if (this.type == EnumCrystalType.THUNDER) {
                this.moveTowardsTarget(source.getEntity(), -0.15D - ((double) damage / 8D));

                return super.attackEntityFrom(source, damage);
            }

            Vec3 var3 = source.getEntity().getLookVec();

            if (var3 != null) {
                this.smotionX = var3.xCoord;
                this.smotionY = var3.yCoord;
                this.smotionZ = var3.zCoord;
            }

            this.wasHit = true;

            return true;
        }

        return false;
    }

    public void moveTowardsTarget(Entity target, double speed) {
        if (this.worldObj.isRemote) {
            return;
        }

        double angle1 = this.rotationYaw / (180F / Math.PI);

        this.motionX -= Math.sin(angle1) * speed;
        this.motionZ += Math.cos(angle1) * speed;

        double a = target.posY - 0.75F;

        if (a < this.boundingBox.minY - 0.5F) {
            this.motionY -= (speed / 2);
        } else if (a > this.boundingBox.minY + 0.5F) {
            this.motionY += (speed / 2);
        } else {
            this.motionY += (a - this.boundingBox.minY) * (speed / 2);
        }

        if (this.onGround) {
            this.onGround = false;
            this.motionY = 0.1F;
        }
    }

    public boolean wasHit() {
        return this.wasHit;
    }

    public int maxTicksAlive() {
        return this.type == EnumCrystalType.THUNDER ? 200 : 300;
    }

    private void expire() {
        this.worldObj.playSoundAtEntity(this, this.type.getDeathSound(), 2.0F, this.rand.nextFloat() - this.rand.nextFloat() * 0.2F + 1.2F);

        if (this.worldObj.isRemote) {
            return;
        }

        ((WorldServer) this.worldObj).func_147487_a(this.type.getDeathParticle(), this.posX, this.boundingBox.minY + this.height * 0.8D, this.posZ, 16, 0.25D, 0.25D, 0.25D, 0.0D);
    }

    private void explode() {
        this.worldObj.playSoundAtEntity(this, this.type.getExplosionSound(), 2.0F, this.rand.nextFloat() - this.rand.nextFloat() * 0.2F + 1.2F);

        if (this.worldObj.isRemote) {
            return;
        }

        double motionMultiplier = 0.5F;

        if (this.type == EnumCrystalType.ICE || this.type == EnumCrystalType.CLOUD) {
            motionMultiplier *= 0.5D;
        }

        ((WorldServer) this.worldObj).func_147487_a(this.type.getExplosionParticle(), this.posX, this.posY, this.posZ, 40, 0.0D, 0.0D, 0.0D, motionMultiplier);
    }

    public EnumCrystalType getCrystalType() {
        return this.type;
    }

    @Override
    public IChatComponent func_145748_c_() {
        return new ChatComponentText(this.getCommandSenderName() + this.type.toString().toLowerCase());
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeInt(this.type.getId());

        if (this.type == EnumCrystalType.THUNDER) {
            buffer.writeInt(this.getAttackTarget().getEntityId());
        }
    }

    @Override
    public void readSpawnData(ByteBuf buffer) {
        this.type = EnumCrystalType.get(buffer.readInt());

        if (this.type == EnumCrystalType.THUNDER) {
            this.setAttackTarget((EntityLivingBase) this.worldObj.getEntityByID(buffer.readInt()));
        }
    }

}