package com.legacy.aether.common.entities.passive;

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
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.common.entities.ai.SheepuffAIEatAetherGrass;

public class EntitySheepuff extends EntityAetherAnimal
{

	public static final DataParameter<Byte> FLEECE_COLOR = EntityDataManager.<Byte>createKey(EntitySheepuff.class, DataSerializers.BYTE);

	public static final DataParameter<Boolean> SHEARED = EntityDataManager.<Boolean>createKey(EntitySheepuff.class, DataSerializers.BOOLEAN);

	public static final DataParameter<Boolean> PUFFY = EntityDataManager.<Boolean>createKey(EntitySheepuff.class, DataSerializers.BOOLEAN);

	private SheepuffAIEatAetherGrass entityAIEatGrass;

	private int sheepTimer, amountEaten;

    public EntitySheepuff(World world)
    {
        super(world);
        this.setSize(0.9F, 1.3F);
		this.setFleeceColor(getRandomFleeceColor(rand));
		this.amountEaten = 0;
    }

    @Override
    protected void initEntityAI()
    {
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D); 
    }

    @Override
	protected void entityInit()
    {
        super.entityInit();

        this.dataManager.register(FLEECE_COLOR, new Byte((byte)0));
        this.dataManager.register(SHEARED, false);
        this.dataManager.register(PUFFY, false);
    }

	@Override
	protected void dropFewItems(boolean var1, int ammount) 
	{
        if(!this.getSheared())
        {
            entityDropItem(new ItemStack(Blocks.WOOL, 1 + rand.nextInt(2), getFleeceColor()), 0.0F);
        }
        
        int i = this.rand.nextInt(2) + 1 + this.rand.nextInt(1 + ammount);

        for (int j = 0; j < i; ++j)
        {
            if (this.isBurning())
            {
                this.dropItem(Items.COOKED_MUTTON, 1);
            }
            else
            {
                this.dropItem(Items.MUTTON, 1);
            }
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
    public void handleStatusUpdate(byte id)
    {
        if (id == 10)
        {
            this.sheepTimer = 40;
        }
        else
        {
            super.handleStatusUpdate(id);
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
	protected void playStepSound(BlockPos pos, Block par4)
	{
		this.worldObj.playSound((EntityPlayer) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_SHEEP_STEP, SoundCategory.NEUTRAL, 0.15F, 1.0F);
	}

    public boolean processInteract(EntityPlayer player, EnumHand hand, ItemStack stack)
    {
        ItemStack itemstack = player.inventory.getCurrentItem();

        if(itemstack != null && itemstack.getItem() == Items.SHEARS && !this.getSheared())
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
					EntityItem entityitem = this.entityDropItem(new ItemStack(Blocks.WOOL, 1, this.getFleeceColor()), 1.0F);
					entityitem.motionY += this.rand.nextFloat() * 0.05F;
					entityitem.motionX += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
					entityitem.motionZ += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
				}
            }

            itemstack.damageItem(1, player);
        }

        if (itemstack != null && itemstack.getItem() == Items.DYE && !this.getSheared())
        {
        	EnumDyeColor color = EnumDyeColor.byDyeDamage(itemstack.getItemDamage());
        	int colorID = color.getMetadata();

            if (this.getFleeceColor() != colorID)
            {
                if (this.getPuffed() && itemstack.stackSize >= 2)
                {
                    this.setFleeceColor(colorID);
                    itemstack.stackSize -= 2;
                }
                else if (!this.getPuffed())
                {
                    this.setFleeceColor(colorID);
                    --itemstack.stackSize;
                }
            }
        }

        return false;
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
			this.setFleeceColor(0);
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

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_SHEEP_AMBIENT;
    }

    protected SoundEvent getHurtSound()
    {
        return SoundEvents.ENTITY_SHEEP_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SHEEP_DEATH;
    }

	public int getFleeceColor()
    {
        return this.dataManager.get(FLEECE_COLOR) & 15;
    }

    public void setFleeceColor(int i)
    {
        byte byte0 = this.dataManager.get(FLEECE_COLOR);
        this.dataManager.set(FLEECE_COLOR, Byte.valueOf((byte)(byte0 & 240 | i & 15)));
    }

    public boolean getSheared()
    {
        return this.dataManager.get(SHEARED);
    }

    public void setSheared(boolean flag)
    {
        this.dataManager.set(SHEARED, flag);
    }

	public boolean getPuffed()
    {
        return this.dataManager.get(PUFFY);
    }

	public void setPuffed(boolean flag)
    {
        this.dataManager.set(PUFFY, flag);
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
		return new EntitySheepuff(worldObj);
	}

}