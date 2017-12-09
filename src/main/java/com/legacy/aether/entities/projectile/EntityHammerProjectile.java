package com.legacy.aether.entities.projectile;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityHammerProjectile extends EntityThrowable
{

	private int ticksInAir;

    private int ignoreTime;

    public EntityHammerProjectile(World worldIn)
    {
    	super(worldIn);

    	this.setSize(0.25F, 0.25F);
    }

	public EntityHammerProjectile(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

    public EntityHammerProjectile(World worldIn, EntityLivingBase throwerIn)
    {
    	super(worldIn, throwerIn);
    }

    @Override
    protected void entityInit()
    {
    	this.setNoGravity(true);
    }

    @Override
    public void onUpdate()
    {
		this.worldObj.spawnParticle(EnumParticleTypes.REDSTONE, this.posX, this.posY + 0.2f, this.posZ, 1.0D, 1.0D, 1.0D);

    	super.onUpdate();

    	if (!this.onGround)
    	{
    		++this.ticksInAir;
    	}

    	if (this.ticksInAir > 600)
    	{
    		this.setDead();
    	}

        Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
        Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        RayTraceResult raytraceresult = this.worldObj.rayTraceBlocks(vec3d, vec3d1);
        vec3d = new Vec3d(this.posX, this.posY, this.posZ);
        vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        Entity entity = null;
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expandXyz(8.0D));
        double d0 = 0.0D;
        boolean flag = false;

        for (int i = 0; i < list.size(); ++i)
        {
            Entity entity1 = (Entity)list.get(i);

            if (entity1.canBeCollidedWith())
            {
                if (entity1 == this.ignoreEntity)
                {
                    flag = true;
                }
                else if (this.ticksExisted < 2 && this.ignoreEntity == null)
                {
                    this.ignoreEntity = entity1;
                    flag = true;
                }
                else
                {
                    flag = false;
                    AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expandXyz(3.0D);
                    RayTraceResult raytraceresult1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);

                    if (raytraceresult1 != null)
                    {
                        double d1 = vec3d.squareDistanceTo(raytraceresult1.hitVec);

                        if (d1 < d0 || d0 == 0.0D)
                        {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }
        }

        if (this.ignoreEntity != null)
        {
            if (flag)
            {
                this.ignoreTime = 2;
            }
            else if (this.ignoreTime-- <= 0)
            {
                this.ignoreEntity = null;
            }
        }

        if (entity != null)
        {
            raytraceresult = new RayTraceResult(entity);
        }

        if (raytraceresult != null)
        {
            if (raytraceresult.typeOfHit == RayTraceResult.Type.ENTITY && raytraceresult.entityHit != this.getThrower())
            {
                if(!net.minecraftforge.common.ForgeHooks.onThrowableImpact(this, raytraceresult))
                this.onRangedImpact(raytraceresult);
            }
        }
    }

    protected void onRangedImpact(RayTraceResult result)
    {
		result.entityHit.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.getThrower()), 5);
		result.entityHit.addVelocity(this.motionX, 0.6D, this.motionZ);

        if (this.getThrower() != null && result.entityHit != this.getThrower() && result.entityHit instanceof EntityPlayer && this.getThrower() instanceof EntityPlayerMP)
        {
            ((EntityPlayerMP)this.getThrower()).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
        }
    }

	@Override
	protected void onImpact(RayTraceResult result)
	{
		if (result.typeOfHit == Type.ENTITY && result.entityHit != this.getThrower())
		{
			result.entityHit.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.getThrower()), 5);
			result.entityHit.addVelocity(this.motionX, 0.6D, this.motionZ);

            if (this.getThrower() != null && result.entityHit != this.getThrower() && result.entityHit instanceof EntityPlayer && this.getThrower() instanceof EntityPlayerMP)
            {
                ((EntityPlayerMP)this.getThrower()).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
            }
		}

        for(int j = 0; j < 8; j++)
        {
			this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			this.worldObj.spawnParticle(EnumParticleTypes.FLAME, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
        }

		this.setDead();
	}

}