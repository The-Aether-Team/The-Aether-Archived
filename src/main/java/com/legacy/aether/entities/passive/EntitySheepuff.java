package com.legacy.aether.entities.passive;

import java.util.Random;

import javax.annotation.Nullable;

import com.legacy.aether.entities.ai.SheepuffAIEatAetherGrass;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.registry.AetherLootTables;
import com.legacy.aether.registry.sounds.SoundsAether;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySheepuff extends EntityAetherAnimal implements net.minecraftforge.common.IShearable
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
    	this.entityAIEatGrass = new SheepuffAIEatAetherGrass(this);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.25D, ItemsAether.blue_berry, false));
        this.tasks.addTask(5, this.entityAIEatGrass);
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

	/*@Override
	protected void dropFewItems(boolean var1, int ammount) 
	{
        if(!this.getSheared())
        {
            this.entityDropItem(new ItemStack(Blocks.WOOL, 1 + rand.nextInt(2), getFleeceColor()), 0.0F);
        }
	}*/

	@Override
    protected void updateAITasks()
    {
        this.sheepTimer = this.entityAIEatGrass.getEatingGrassTimer();
        super.updateAITasks();
    }

    @Override
    public void onLivingUpdate()
    {
        if (this.world.isRemote)
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
		this.world.playSound((EntityPlayer) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_SHEEP_STEP, SoundCategory.NEUTRAL, 0.15F, 1.0F);
	}

	@Override
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        /*if(itemstack.getItem() == Items.SHEARS && !this.getSheared()) // Forge: move to onSheared
        {
            if(!this.world.isRemote)
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
					EntityItem entityitem = this.entityDropItem(new ItemStack(Blocks.WOOL, 1, this.getFleeceColor().getMetadata()), 1.0F);
					entityitem.motionY += this.rand.nextFloat() * 0.05F;
					entityitem.motionX += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
					entityitem.motionZ += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F;
				}
				this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
            }

            itemstack.damageItem(1, player);
        }*/

        if (itemstack.getItem() == Items.DYE && !this.getSheared())
        {
        	EnumDyeColor color = EnumDyeColor.byDyeDamage(itemstack.getItemDamage());

            if (this.getFleeceColor() != color)
            {
                if (this.getPuffed() && itemstack.getCount() >= 2)
                {
                    this.setFleeceColor(color);
                    itemstack.shrink(2);
                }
                else if (!this.getPuffed())
                {
                    this.setFleeceColor(color);
                    itemstack.shrink(1);
                }
            }
        }

        return super.processInteract(player, hand);
    }
	
	@Override public boolean isShearable(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos){ return !this.getSheared() && !this.isChild(); }
    @Override
    public java.util.List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
        this.setSheared(true);
        int i = 1 + this.rand.nextInt(3);

        java.util.List<ItemStack> ret = new java.util.ArrayList<ItemStack>();
        for (int j = 0; j < i; ++j)
            ret.add(new ItemStack(Item.getItemFromBlock(Blocks.WOOL), 1, this.getFleeceColor().getMetadata()));

        this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        return ret;
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

		if(this.amountEaten >= 2 && !this.getSheared() && !this.getPuffed())
		{
			this.setPuffed(true);
			this.amountEaten = 0;
		}

		if(this.amountEaten == 1 && this.getSheared() && !this.getPuffed())
		{
			this.setSheared(false);
			this.setFleeceColor(EnumDyeColor.WHITE);
			this.amountEaten = 0;
		}
	}

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Sheared", this.getSheared());
		nbttagcompound.setBoolean("Puffed", this.getPuffed());
		nbttagcompound.setByte("Color", (byte)this.getFleeceColor().getMetadata());
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);

        this.setSheared(nbttagcompound.getBoolean("Sheared"));
        this.setPuffed(nbttagcompound.getBoolean("Puffed"));
        this.setFleeceColor(EnumDyeColor.byMetadata(nbttagcompound.getByte("Color")));
    }

    protected SoundEvent getAmbientSound()
    {
        return SoundsAether.sheepuff_say;
    }

    protected SoundEvent getHurtSound(DamageSource source)
    {
        return SoundsAether.sheepuff_hurt;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundsAether.sheepuff_death;
    }

	public EnumDyeColor getFleeceColor()
    {
        return EnumDyeColor.byMetadata(((Byte)this.dataManager.get(FLEECE_COLOR)).byteValue() & 15);
    }

    public void setFleeceColor(EnumDyeColor color)
    {
        byte byte0 = this.dataManager.get(FLEECE_COLOR);
        this.dataManager.set(FLEECE_COLOR, Byte.valueOf((byte)(byte0 & 240 | color.getMetadata() & 15)));
    }

    public boolean getSheared()
    {
        return this.dataManager.get(SHEARED).booleanValue();
    }

    public void setSheared(boolean flag)
    {
        this.dataManager.set(SHEARED, flag);
    }

	public boolean getPuffed()
    {
        return this.dataManager.get(PUFFY).booleanValue();
    }

	public void setPuffed(boolean flag)
    {
        this.dataManager.set(PUFFY, flag);
    }

	public static EnumDyeColor getRandomFleeceColor(Random random)
    {
        int i = random.nextInt(100);

        if(i < 5) { return EnumDyeColor.LIGHT_BLUE; }
        if(i < 10) { return EnumDyeColor.CYAN; }
        if(i < 15) { return EnumDyeColor.LIME; }
        if(i < 18) { return EnumDyeColor.PINK; }

        return random.nextInt(500) != 0 ? EnumDyeColor.WHITE : EnumDyeColor.PURPLE;
    }

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable)
	{
		return new EntitySheepuff(world);
	}
	
	public float getEyeHeight()
    {
        return 0.95F * this.height;
    }

	@Nullable
    protected ResourceLocation getLootTable()
    {
		if (this.getSheared())
    	{
    		return AetherLootTables.sheepuff;
    	}
    	else
    	{
    		switch (this.getFleeceColor())
    		{
    			case WHITE:
    			default:
    				return AetherLootTables.sheepuff_white;
    			case ORANGE:
    				return AetherLootTables.sheepuff_orange;
    			case MAGENTA:
    				return AetherLootTables.sheepuff_magenta;
    			case LIGHT_BLUE:
    				return AetherLootTables.sheepuff_light_blue;
    			case YELLOW:
    				return AetherLootTables.sheepuff_yellow;
    			case LIME:
    				return AetherLootTables.sheepuff_lime;
    			case PINK:
    				return AetherLootTables.sheepuff_pink;
    			case GRAY:
    				return AetherLootTables.sheepuff_gray;
    			case SILVER:
    				return AetherLootTables.sheepuff_silver;
    			case CYAN:
    				return AetherLootTables.sheepuff_cyan;
    			case PURPLE:
    				return AetherLootTables.sheepuff_purple;
    			case BLUE:
    				return AetherLootTables.sheepuff_blue;
    			case BROWN:
    				return AetherLootTables.sheepuff_brown;
    			case GREEN:
    				return AetherLootTables.sheepuff_green;
    			case RED:
    				return AetherLootTables.sheepuff_red;
    			case BLACK:
    				return AetherLootTables.sheepuff_black;
    		}
    	}
    }
	
}