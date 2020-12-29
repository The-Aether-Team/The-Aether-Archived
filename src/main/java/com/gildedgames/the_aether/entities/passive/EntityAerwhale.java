package com.gildedgames.the_aether.entities.passive;

import javax.annotation.Nullable;

import com.gildedgames.the_aether.registry.AetherLootTables;
import com.gildedgames.the_aether.registry.sounds.SoundsAether;
import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.entities.ai.aerwhale.AerwhaleAITravelCourse;
import com.gildedgames.the_aether.entities.ai.aerwhale.AerwhaleAIUnstuck;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class EntityAerwhale extends EntityFlying
{

	private double motionYaw;

    private double motionPitch;

    public double aerwhaleRotationYaw;

    public double aerwhaleRotationPitch;

    public EntityAerwhale(World world)
    {
        super(world);

        this.setSize(3F, 3F);
        this.isImmuneToFire = true;
        this.ignoreFrustumCheck = true;
        this.rotationYaw = 360F * this.getRNG().nextFloat();
        this.rotationPitch = 90F * this.getRNG().nextFloat() - 45F;
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(1, new AerwhaleAIUnstuck(this));
    	this.tasks.addTask(5, new AerwhaleAITravelCourse(this));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D); 
    }

    @Override
    public boolean getCanSpawnHere()
    {
        BlockPos pos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY), MathHelper.floor(this.posZ));

        return this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).size() == 0 && !this.world.containsAnyLiquid(this.getEntityBoundingBox()) && this.world.getLight(pos) > 8 && super.getCanSpawnHere();
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }

    @Override
    public void onUpdate()
    {
    	super.onUpdate();
        this.extinguish();

        if (this.posY < -64.0D)
        {
            this.setDead();
        }

        this.aerwhaleRotationYaw = this.rotationYaw;
        this.aerwhaleRotationPitch = this.rotationPitch;
    }

	@Override
    public void travel(float strafe, float vertical, float forward)
	{
		Entity entity = this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);

		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;

			this.aerwhaleRotationYaw = this.motionYaw = this.prevRotationYaw = this.rotationYaw = player.rotationYaw;
			this.motionPitch = this.prevRotationPitch = this.rotationPitch = player.rotationPitch;

	        this.aerwhaleRotationYaw = this.motionYaw + 90.0F;
			this.aerwhaleRotationPitch = -this.motionPitch;

			this.motionYaw = this.rotationYawHead = player.rotationYawHead;

			strafe = player.moveStrafing;
			vertical = 0.0F;
			forward = player.moveForward;

			if (forward <= 0.0F)
			{
				forward *= 0.25F;
			}

            this.motionX += 0.05D * Math.cos((this.aerwhaleRotationYaw / 180D) * 3.1415926535897931D ) * Math.cos((this.aerwhaleRotationPitch / 180D) * 3.1415926535897931D);

            this.motionY += 0.02D * Math.sin((this.aerwhaleRotationPitch / 180D) * Math.PI);

            this.motionZ += 0.05D * Math.sin((this.aerwhaleRotationYaw / 180D) * 3.1415926535897931D ) * Math.cos((this.aerwhaleRotationPitch / 180D) * 3.1415926535897931D);

            this.motionX *= 0.98D;
            this.motionY *= 0.98D;
            this.motionZ *= 0.98D;

			if (AetherAPI.getInstance().get(player).isJumping())
			{
				this.motionX = 0.0D;
				this.motionY = 0.0D;
				this.motionZ = 0.0D;
			}

			this.stepHeight = 1.0F;

			if (!this.world.isRemote)
			{
				this.jumpMovementFactor = this.getAIMoveSpeed() * 0.6F;
				super.travel(strafe, vertical, forward);
			}

			this.prevLimbSwingAmount = this.limbSwingAmount;
			double d0 = this.posX - this.prevPosX;
			double d1 = this.posZ - this.prevPosZ;
			float f4 = MathHelper.sqrt(d0 * d0 + d1 * d1) * 4.0F;

			if (f4 > 1.0F)
			{
				f4 = 1.0F;
			}

			this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
			this.limbSwing += this.limbSwingAmount;
		}
		else
		{
			this.stepHeight = 0.5F;
			this.jumpMovementFactor = 0.02F;
			super.travel(strafe, vertical, forward);
		}
	}

    @Override
    public boolean processInteract(EntityPlayer entityplayer, EnumHand hand)
    {
    	if (entityplayer.getUniqueID().toString().equals("031025bd-0a15-439b-9c55-06a20d0de76f"))
    	{
    		entityplayer.startRiding(this);

    		if (!this.world.isRemote && FMLCommonHandler.instance().getMinecraftServerInstance() != null)
    		{
    			FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(new TextComponentString("Serenity is the queen of W(h)ales!!"), false);
    		}
    	}
    	else
    	{
        	return super.processInteract(entityplayer, hand);
    	}
    	return true;
    }

    @Override
    public SoundEvent getAmbientSound()
    {
        return SoundsAether.aerwhale_call;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source)
    {
        return SoundsAether.aerwhale_death;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundsAether.aerwhale_death;
    }

    @Override
    protected float getSoundVolume()
    {
        return 3F;
    }
    
    @Override
    public boolean canDespawn()
    {
        return true;
    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
        return AetherLootTables.aerwhale;
    }
}