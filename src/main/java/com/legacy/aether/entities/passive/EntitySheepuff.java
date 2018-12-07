package com.legacy.aether.entities.passive;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.legacy.aether.entities.ai.SheepuffAIEatAetherGrass;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntitySheepuff extends EntityAetherAnimal
{

	private SheepuffAIEatAetherGrass entityAIEatGrass;

	private int sheepTimer, amountEaten;

    public EntitySheepuff(World world)
    {
        super(world);

		this.amountEaten = 0;
        this.setSize(0.9F, 1.3F);
		this.setFleeceColor(getRandomFleeceColor(rand));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(5, this.entityAIEatGrass = new SheepuffAIEatAetherGrass(this));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D); 
    }

    @Override
	protected void entityInit()
    {
        super.entityInit();

        this.dataWatcher.addObject(16, new Byte((byte)0));
        this.dataWatcher.addObject(17, new Byte((byte)0));
        this.dataWatcher.addObject(18, new Byte((byte)0));
    }

	@Override
	protected void dropFewItems(boolean var1, int ammount) 
	{
        if(!this.getSheared())
        {
        	this.entityDropItem(new ItemStack(Blocks.wool, 1 + this.rand.nextInt(2), this.getFleeceColor()), 0.0F);
        }
    }

	@Override
    protected void updateAITasks()
    {
        this.sheepTimer = this.entityAIEatGrass.getEatingGrassTimer();
        super.updateAITasks();
    }

    @Override
    public void onLivingUpdate()
    {
        if (this.worldObj.isRemote)
        {
            this.sheepTimer = Math.max(0, this.sheepTimer - 1);
        }

        super.onLivingUpdate();
    }

    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte id)
    {
        if (id == 10)
        {
            this.sheepTimer = 40;
        }
        else
        {
            super.handleHealthUpdate(id);
        }
    }

    @SideOnly(Side.CLIENT)
    public float getHeadRotationPointY(float p_70894_1_)
    {
        return this.sheepTimer <= 0 ? 0.0F : (this.sheepTimer >= 4 && this.sheepTimer <= 36 ? 1.0F : (this.sheepTimer < 4 ? ((float)this.sheepTimer - p_70894_1_) / 4.0F : -((float)(this.sheepTimer - 40) - p_70894_1_) / 4.0F));
    }

    @SideOnly(Side.CLIENT)
    public float getHeadRotationAngleX(float p_70890_1_)
    {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36)
        {
            float f = ((float)(this.sheepTimer - 4) - p_70890_1_) / 32.0F;
            return ((float)Math.PI / 5F) + ((float)Math.PI * 7F / 100F) * MathHelper.sin(f * 28.7F);
        }
        else
        {
            return this.sheepTimer > 0 ? ((float)Math.PI / 5F) : this.rotationPitch * 0.017453292F;
        }
    }

    @Override
    public void eatGrassBonus()
    {
    	++this.amountEaten;
    }

	@Override
    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
    {
        this.playSound("mob.sheep.step", 0.15F, 1.0F);
    }

	@Override
    public boolean interact(EntityPlayer player)
    {
        ItemStack itemstack = player.inventory.getCurrentItem();

        if(itemstack != null && itemstack.getItem() == Items.shears && !this.getSheared())
        {
            if(!this.worldObj.isRemote)
            {
				if(this.getPuffed())
				{
					this.setPuffed(false);
				}
				else
				{
					this.setSheared(true);
				}

				int i = 2 + this.rand.nextInt(3);

				for(int j = 0; j < i; j++)
				{
					EntityItem entityitem = this.entityDropItem(new ItemStack(Blocks.wool, 1, this.getFleeceColor()), 1.0F);
					entityitem.motionY += this.rand.nextFloat() * 0.05F;
					entityitem.motionX += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
					entityitem.motionZ += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
				}
            }

            itemstack.damageItem(1, player);
        }

        if (itemstack != null && itemstack.getItem() == Items.dye && !this.getSheared())
        {
            if (this.getFleeceColor() != itemstack.getItemDamage())
            {
                if (this.getPuffed() && itemstack.stackSize >= 2)
                {
                    this.setFleeceColor(15 - itemstack.getItemDamage());
                    itemstack.stackSize -= 2;
                }
                else if (!this.getPuffed())
                {
                    this.setFleeceColor(15 - itemstack.getItemDamage());
                    --itemstack.stackSize;
                }
            }
        }

        return super.interact(player);
    }

    protected void jump()
    {
		if(this.getPuffed())
		{
			this.motionY = 1.8D;
			this.motionX += this.rand.nextGaussian() * 0.5D;
			this.motionZ += this.rand.nextGaussian() * 0.5D;
		}
		else
		{
			this.motionY = 0.41999998688697815D;
		}
    }

	public void onUpdate()
	{
		super.onUpdate();

		if(this.getPuffed())
		{
			this.fallDistance = 0;

			if(this.motionY < -0.05D)
			{
				this.motionY = -0.05D;
			}
		}

		if(this.amountEaten == 5 && !this.getSheared() && !this.getPuffed())
		{
			this.setPuffed(true);
			this.amountEaten = 0;
		}

		if(this.amountEaten == 10 && this.getSheared() && !this.getPuffed())
		{
			this.setSheared(false);
			this.setFleeceColor(15);
			this.amountEaten = 0;
		}
	}

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Sheared", this.getSheared());
		nbttagcompound.setBoolean("Puffed", this.getPuffed());
		nbttagcompound.setByte("Color", (byte)this.getFleeceColor());
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);

        this.setSheared(nbttagcompound.getBoolean("Sheared"));
        this.setPuffed(nbttagcompound.getBoolean("Puffed"));
        this.setFleeceColor(nbttagcompound.getByte("Color"));
    }

    @Override
    protected String getLivingSound()
    {
        return "mob.sheep.say";
    }

    @Override
    protected String getHurtSound()
    {
        return "mob.sheep.say";
    }

    @Override
    protected String getDeathSound()
    {
        return "mob.sheep.say";
    }

	public int getFleeceColor()
    {
        return this.dataWatcher.getWatchableObjectByte(16) & 15;
    }

    public void setFleeceColor(int i)
    {
        byte byte0 = this.dataWatcher.getWatchableObjectByte(16);
        this.dataWatcher.updateObject(16, Byte.valueOf((byte)(byte0 & 240 | i & 15)));
    }

    public boolean getSheared()
    {
        return this.dataWatcher.getWatchableObjectByte(17) == (byte)1;
    }

    public void setSheared(boolean flag)
    {
        this.dataWatcher.updateObject(17, Byte.valueOf((byte)(flag ? 1 : 0)));
    }

	public boolean getPuffed()
    {
        return this.dataWatcher.getWatchableObjectByte(18) == (byte)1;
    }

	public void setPuffed(boolean flag)
    {
        this.dataWatcher.updateObject(18, Byte.valueOf((byte)(flag ? 1 : 0)));
    }

	public static int getRandomFleeceColor(Random random)
    {
        int i = random.nextInt(100);

        if(i < 5) { return 3; }
        if(i < 10) { return 9; }
        if(i < 15) { return 5; }
        if(i < 18) { return 6; }

        return random.nextInt(500) != 0 ? 0 : 10;
    }

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable)
	{
		return new EntitySheepuff(this.worldObj);
	}

}