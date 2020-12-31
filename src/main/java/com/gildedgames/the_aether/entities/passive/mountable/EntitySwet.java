package com.gildedgames.the_aether.entities.passive.mountable;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.entities.hostile.swet.EnumSwetType;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.network.AetherNetwork;
import com.gildedgames.the_aether.network.packets.PacketSwetJump;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import com.gildedgames.the_aether.entities.util.EntityMountable;

public class EntitySwet extends EntityMountable
{
    public boolean wasOnGround;
    public boolean midJump;
    public int jumpTimer;

    public float swetHeight;
    public float swetWidth;

    private int jumpDelay;

    private int jumps = 0;
    private float chosenDegrees;

    public EntitySwet(World world)
    {
        super(world);

        this.setSize(0.8F, 0.8F);
        this.setPosition(this.posX, this.posY, this.posZ);

        this.swetHeight = 1.0F;
        this.swetWidth = 1.0F;
    }

    @Override
    public void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(21, (byte) this.rand.nextInt(EnumSwetType.values().length));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(1.5D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(25.0D);
        this.setHealth(25.0F);
    }

    @Override
    protected void collideWithEntity(Entity entityIn)
    {
        super.collideWithEntity(entityIn);

        if (!this.hasPrey())
        {
            if (entityIn instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) entityIn;

                if (this.getAttackTarget() != null)
                {
                    if (this.getAttackTarget() == player)
                    {
                        if (!player.capabilities.isCreativeMode)
                        {
                            this.capturePrey((EntityPlayer) entityIn);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean interact(EntityPlayer player)
    {
        if (!this.worldObj.isRemote)
        {
            if (!this.hasPrey() && this.isPlayerFriendly(player))
            {
                this.capturePrey(player);
            }
        }

        return true;
    }

    @Override
    public void onEntityUpdate()
    {
        super.onEntityUpdate();

        if (this.getAttackTarget() instanceof EntityPlayer)
        {
            if (this.isPlayerFriendly((EntityPlayer) this.getAttackTarget()) || this.isFriendly())
            {
                this.setAttackTarget(null);
            }
        }
    }

    public void capturePrey(EntityPlayer entity)
    {
        this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.attack", 0.5F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F, false);

        this.prevPosX = this.posX = entity.posX;
        this.prevPosY = this.posY = entity.posY + 0.01;
        this.prevPosZ = this.posZ = entity.posZ;
        this.prevRotationYaw = this.rotationYaw = entity.rotationYaw;
        this.prevRotationPitch = this.rotationPitch = entity.rotationPitch;
        this.motionX = entity.motionX;
        this.motionY = entity.motionY;
        this.motionZ = entity.motionZ;

        this.setSize(entity.width, entity.height);
        this.setPosition(this.posX, this.posY, this.posZ);

        entity.mountEntity(this);

        this.rotationYaw = this.rand.nextFloat() * 360F;
    }

    public void onUpdate()
    {
        if (!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL)
        {
            this.isDead = true;
        }

        if (this.handleWaterMovement())
        {
            this.dissolveSwet();
        }

        super.onUpdate();

        if (!this.hasPrey())
        {
            for (int i = 0; i < 5; i++)
            {
                double d = (float) this.posX + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.3F;
                double d1 = (float) this.posY + this.height;
                double d2 = (float) this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.3F;
                this.worldObj.spawnParticle("splash", d, d1 - 0.25D, d2, 0.0D, 0.0D, 0.0D);
            }
        }

        if (this.onGround && !this.wasOnGround)
        {
            this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.slime.small", 0.5F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F, false);
        }

        if (!this.worldObj.isRemote)
        {
            this.midJump = !this.onGround;
            AetherNetwork.sendToAll(new PacketSwetJump(this.getEntityId(), !this.onGround));
        }

        if (this.worldObj.isRemote)
        {
            if (this.midJump)
            {
                this.jumpTimer++;
            }
            else
            {
                this.jumpTimer = 0;
            }

            if (this.onGround)
            {
                this.swetHeight = this.swetHeight < 1.0F ? this.swetHeight += 0.25F : 1.0F;
                this.swetWidth = this.swetWidth > 1.0F ? this.swetWidth -= 0.25F : 1.0F;
            }
            else
            {
                this.swetHeight = 1.425F;
                this.swetWidth = 0.875F;

                if (this.getJumpTimer() > 3)
                {
                    float scale = Math.min(this.getJumpTimer(), 10);
                    this.swetHeight -= 0.05F * scale;
                    this.swetWidth += 0.05F * scale;
                }
            }
        }

        this.wasOnGround = this.onGround;
    }

    @Override
    public void moveEntityWithHeading(float strafe, float forward)
    {
        if (this.hasPrey())
        {
            if (this.isFriendly())
            {
                EntityPlayer rider = (EntityPlayer) this.riddenByEntity;
                IPlayerAether aetherRider = AetherAPI.get(rider);

                if (aetherRider.isJumping() && this.onGround)
                {
                    this.jump();
                    this.onGround = false;
                    this.motionY = 1.0f;
                }
            }
        }

        super.moveEntityWithHeading(strafe, forward);
    }

    @Override
    protected void updateEntityActionState()
    {
        this.despawnEntity();

        EntityPlayer entityplayer = this.worldObj.getClosestVulnerablePlayerToEntity(this, 25.0D);

        if (entityplayer != null)
        {
            if (entityplayer.isEntityAlive() && !entityplayer.capabilities.disableDamage)
            {
                if (!this.isPlayerFriendly(entityplayer) && !this.isFriendly() && !this.hasPrey())
                {
                    this.setAttackTarget(entityplayer);
                    this.faceEntity(entityplayer, 10.0F, 20.0F);
                }
            }
        }

        if (this.onGround && this.jumpDelay-- <= 0)
        {
            this.jumpDelay = this.getJumpDelay();

            if (entityplayer != null)
            {
                this.jumpDelay /= 3;
            }

            this.isJumping = true;

            this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);

            this.moveStrafing = 1.0F - this.rand.nextFloat() * 2.0F;
            this.moveForward = 1.0F;
        }
        else
        {
            this.isJumping = false;

            if (this.onGround)
            {
                this.moveStrafing = this.moveForward = 0.0F;
            }
        }

        if (this.hasPrey() && this.riddenByEntity instanceof EntityPlayer && !this.isPlayerFriendly((EntityPlayer) this.riddenByEntity))
        {
            if (this.jumps <= 3)
            {
                if (this.onGround)
                {
                    this.playSound("mob.slime.small", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);

                    this.chosenDegrees = (float)this.rand.nextInt(360);

                    if (this.jumps == 0)
                    {
                        this.motionY += 0.64999999403953552D;
                    }
                    else if (this.jumps == 1)
                    {
                        this.motionY += 0.74999998807907104D;
                    }
                    else if (this.jumps == 2)
                    {
                        this.motionY += 1.55D;
                    }
                    else
                    {
                        if (this.riddenByEntity instanceof EntityPlayer)
                        {
                            ((EntityPlayer) this.riddenByEntity).dismountEntity(this);
                        }
                        this.dissolveSwet();
                    }

                    if (!this.midJump)
                    {
                        this.jumps++;
                    }
                }

                if (!this.wasOnGround)
                {
                    if (this.getJumpTimer() < 6)
                    {
                        if (this.jumps == 1)
                        {
                            this.moveXY(0.0F, 0.2F, this.chosenDegrees);
                        }
                        else if (this.jumps == 2)
                        {
                            this.moveXY(0.0F, 0.3F, this.chosenDegrees);
                        }
                        else if (this.jumps == 3)
                        {
                            this.moveXY(0.0F, 0.6F, this.chosenDegrees);
                        }
                    }
                }
            }
        }
    }

    public void moveXY(float strafe, float forward, float rotation)
    {
        float f = strafe * strafe + forward * forward;

        f = MathHelper.sqrt_float(f);
        if (f < 1.0F) f = 1.0F;
        strafe = strafe * f;
        forward = forward * f;
        float f1 = MathHelper.sin(rotation * 0.017453292F);
        float f2 = MathHelper.cos(rotation * 0.017453292F);

        this.motionX += (strafe * f2 - forward * f1);
        this.motionZ += (forward * f2 + strafe * f1);
    }

    @Override
    public void fall(float distance)
    {
        if (!this.isFriendly())
        {
            super.fall(distance);
        }
    }

    @Override
    protected void jump()
    {
        this.motionY = 0.41999998688697815D;
        this.isAirBorne = true;
    }

    public int getJumpTimer()
    {
        return this.jumpTimer;
    }

    public int getJumpDelay()
    {
        if (this.isFriendly())
        {
            return 2;
        }
        else
        {
            return this.rand.nextInt(20) + 10;
        }
    }

    @Override
    public int getVerticalFaceSpeed()
    {
        return 0;
    }

    @Override
    public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio)
    {
        if (!this.hasPrey())
        {
            super.knockBack(entityIn, strength, xRatio, zRatio);
        }
    }

    public boolean hasPrey()
    {
        return this.riddenByEntity != null;
    }

    public boolean isPlayerFriendly(EntityPlayer player)
    {
        IPlayerAether iPlayerAether = AetherAPI.get(player);
        return iPlayerAether.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.swet_cape));
    }

    public boolean isFriendly()
    {
        return this.hasPrey() && this.riddenByEntity instanceof EntityPlayer && isPlayerFriendly((EntityPlayer) this.riddenByEntity);
    }

    public void dissolveSwet()
    {
        for (int i = 0; i < 50; i++)
        {
            float f = this.rand.nextFloat() * 3.141593F * 2.0F;
            float f1 = this.rand.nextFloat() * 0.5F + 0.25F;
            float f2 = MathHelper.sin(f) * f1;
            float f3 = MathHelper.cos(f) * f1;

            this.worldObj.spawnParticle("splash", this.posX + (double) f2, this.boundingBox.minY + 1.25D, this.posZ + (double) f3, (double) f2 * 1.5D + this.motionX, 4D, (double) f3 * 1.5D + this.motionZ);
        }

        if (this.getDeathSound() != null) this.playSound(this.getDeathSound(), this.getSoundVolume(), this.getSoundPitch());

        this.setDead();
    }

    @Override
    public float getEyeHeight()
    {
        return 0.625F * this.height;
    }

    @Override
    protected void dropFewItems(boolean recentlyHit, int lootLevel)
    {
        int count = this.rand.nextInt(2);

        if (lootLevel > 0)
        {
            count += this.rand.nextInt(lootLevel + 1);
        }

        if (this.getType() == EnumSwetType.GOLDEN)
        {
            this.entityDropItem(new ItemStack(Blocks.glowstone, count), 1.0F);
        }
        else
        {
            this.entityDropItem(new ItemStack(BlocksAether.aercloud, count, 1), 1.0F);
            this.entityDropItem(new ItemStack(ItemsAether.swet_ball, count), 1.0F);
        }
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.6F;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entityageable)
    {
        return null;
    }

    protected String getJumpSound()
    {
        return "mob.slime.small";
    }

    @Override
    protected String getHurtSound()
    {
        return "mob.slime.small";
    }

    @Override
    protected String getDeathSound()
    {
        return "mob.slime.small";
    }

    public EnumSwetType getType()
    {
        int id = this.dataWatcher.getWatchableObjectByte(21);

        return EnumSwetType.get(id);
    }

    public void setType(int id)
    {
        this.dataWatcher.updateObject(21, (byte) id);
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return this.rand.nextInt(10) == 0 && super.getCanSpawnHere();
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 3;
    }

    @Override
    public boolean canDespawn()
    {
        return this.isFriendly();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        compound.setInteger("SwetType", this.getType().getId());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);

        this.setType(compound.getInteger("SwetType"));
    }
}