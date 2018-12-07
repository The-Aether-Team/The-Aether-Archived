package com.legacy.aether.entities.hostile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.legacy.aether.items.tools.ItemAetherTool;
import com.legacy.aether.items.util.EnumAetherToolType;

public class EntityMimic extends EntityMob
{

	public float mouth, legs;

	private float legsDirection = 1;

    public EntityMimic(World world)
    {
        super(world);
        this.setSize(1.0F, 2.0F);
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
    }
   
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.68000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);   
    }

    public void onUpdate()
    {
		super.onUpdate();

		this.mouth = (float)((Math.cos((float)ticksExisted / 10F * 3.14159265F)) + 1F) * 0.6F;
		this.legs *= 0.9F;

		if (this.prevPosX - this.posX != 0 || this.prevPosZ - this.posZ != 0)
		{
			this.legs += legsDirection * 0.2F;

			if(this.legs > 1.0F)
			{
				this.legsDirection = -1;
			}

			if(this.legs < -1.0F)
			{
				this.legsDirection = 1;
			}
		}
		else
		{
			this.legs = 0.0F;
		}
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

    protected float getSoundVolume()
    {
        return 0.6F;
    }

	@Override
	protected void dropFewItems(boolean var1, int var2) 
	{
		dropItem(Item.getItemFromBlock(Blocks.chest), 1);
	}

	@Override
	public boolean attackEntityFrom(DamageSource ds, float var2)
	{
		if (ds.getEntity() instanceof EntityMimic)
		{
			return false;
		}

		if (ds.getEntity() instanceof EntityLivingBase)
		{
			this.setAttackTarget((EntityLivingBase) ds.getEntity());
		}

		if (ds.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)ds.getEntity();
			ItemStack stack = player.inventory.getCurrentItem();

			if (stack == null || stack.getItem() == null)
			{
				return super.attackEntityFrom(ds, var2);
			}

			if (!(stack.getItem() instanceof ItemAxe) && !(stack.getItem() instanceof ItemAetherTool))
			{
				return super.attackEntityFrom(ds, var2); 
			}

			if (stack.getItem() instanceof ItemAetherTool && ((ItemAetherTool)stack.getItem()).toolType != EnumAetherToolType.AXE)
			{
				return super.attackEntityFrom(ds, var2); 
			}

			return super.attackEntityFrom(ds, var2 * 1.5F);
		}

		return super.attackEntityFrom(ds, var2);
	}

}