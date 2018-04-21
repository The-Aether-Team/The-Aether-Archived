package com.legacy.aether.entities.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.legacy.aether.blocks.util.BlockFloating;

public class EntityFloatingBlock extends Entity
{

	private IBlockState state;

	private int timeFloated = 0;

	public EntityFloatingBlock(World worldIn)
	{
		super(worldIn);

		this.setSize(1.0F, 1.0F);
	}

	public EntityFloatingBlock(World world, BlockPos pos, IBlockState state)
	{
		this(world);

        this.preventEntitySpawning = true;
		this.motionX = this.motionY = this.motionZ = 0;

        this.setState(state);
		this.setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
	}

	@Override
    public void setPosition(double x, double y, double z)
    {
		super.setPosition(x, y, z);

		if (this.world.isRemote && (this.state == null || this.state == Blocks.AIR.getDefaultState()))
		{
			this.state = this.world.getBlockState(this.getPosition());
		}
    }

	@Override
	protected void entityInit()
	{

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

        if (this.collidedVertically && !this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
            this.motionY *= -0.5D;
            this.setDead();

            if (!block.canPlaceBlockAt(this.world, pos) || BlockFloating.canContinue(this.world, pos.up()) || !this.world.setBlockState(pos, this.getBlockState(), 2))
            {
                if (!this.world.isRemote)
                {
                    this.dropItem(Item.getItemFromBlock(block), 1);
                }
            }
        }
        else if (this.timeFloated > 100)
        {
            if (!this.world.isRemote)
            {
                this.dropItem(Item.getItemFromBlock(block), 1);
            }

            this.setDead();
        }
	}

	public void setState(IBlockState state)
	{
		this.state = state;
	}

	public IBlockState getBlockState()
	{
		return this.state;
	}

	@Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound tagCompound)
	{
		this.state = Block.getStateById(tagCompound.getInteger("blockstateId"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tagCompound) 
	{
		tagCompound.setInteger("blockstateId", Block.getStateId(this.state));
	}

}