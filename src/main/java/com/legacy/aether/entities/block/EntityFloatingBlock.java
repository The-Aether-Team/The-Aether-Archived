package com.legacy.aether.entities.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.legacy.aether.blocks.util.BlockFloating;
import com.legacy.aether.entities.DataSerializerRegistry;

public class EntityFloatingBlock extends Entity
{

    public static final DataParameter<IBlockState> STATE = EntityDataManager.<IBlockState>createKey(EntityFloatingBlock.class, DataSerializerRegistry.BLOCK_STATE_SERIALIZER);

	private int timeFloated = 0;

	public EntityFloatingBlock(World worldIn)
	{
		super(worldIn);
	}

	public EntityFloatingBlock(World world, BlockPos pos, IBlockState state)
	{
		super(world);
        this.preventEntitySpawning = true;
		this.motionX = this.motionY = this.motionZ = 0;

        this.setSize(0.98F, 0.98F);
        this.setState(state);
		this.setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
	}

	@Override
	protected void entityInit()
	{
		this.dataManager.register(STATE, Blocks.AIR.getDefaultState());
    }

	@Override
	public void onUpdate()
	{
        if (this.getBlockState() == null)
        {
            this.setDead();
            return;
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        ++this.timeFloated;
        this.motionY += 0.04D;
        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;
        BlockPos pos = new BlockPos(this);
        Block block = this.getBlockState().getBlock();

        List<?> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(0.0D, 1.0D, 0.0D));

        for (int stack = 0; stack < list.size(); ++stack)
        {
            if (list.get(stack) instanceof EntityFallingBlock && block.canPlaceBlockAt(this.world, pos))
            {
                this.world.setBlockState(pos.up(), this.getBlockState(), 2);
                this.setDead();
            }
        }

        if (this.isCollidedVertically && !this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
            this.motionY *= -0.5D;
            this.setDead();

            if (!block.canPlaceBlockAt(this.world, pos) || BlockFloating.canContinue(this.world, pos.up()) || !this.world.setBlockState(pos, this.getBlockState(), 2))
            {
            	block.dropBlockAsItem(this.world, pos, this.getBlockState(), 0);
            }
        }
        else if (this.timeFloated > 100)
        {
        	block.dropBlockAsItem(this.world, pos, this.getBlockState(), 0);

            this.setDead();
        }
	}

	public void setState(IBlockState state)
	{
		this.dataManager.set(STATE, state);
	}

	public IBlockState getBlockState()
	{
		return this.dataManager.get(STATE);
	}

	@Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public void setDead()
    {
        super.setDead();
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompund)
	{

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) 
	{

	}

}