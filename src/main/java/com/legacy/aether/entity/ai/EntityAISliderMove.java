package com.legacy.aether.entity.ai;

import com.legacy.aether.block.dungeon.BlockDungeon;
import com.legacy.aether.entity.boss.EntitySlider;
import com.legacy.aether.sound.SoundsAether;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;

public class EntityAISliderMove extends EntityAIBase
{

	public int moveTime;

	public boolean blockCrushed;

	public boolean moving;

	public EnumFacing direction;

	private final EntitySlider slider;

	public EntityAISliderMove(EntitySlider slider)
	{
		this.slider = slider;
		this.setMutexBits(1);
	}

	@Override
	public boolean isInterruptible()
	{
		return false;
	}

	@Override
	public boolean shouldExecute()
	{
		if (this.slider.getAttackTarget() == null)
		{
			return false;
		}

		double x = Math.abs(this.slider.posX - this.slider.getAttackTarget().posX);
		double y = Math.abs(this.slider.getBoundingBox().minY - this.slider.getAttackTarget().getBoundingBox().minY);
		double z = Math.abs(this.slider.posZ - this.slider.getAttackTarget().posZ);

		if (x > z)
		{
			this.direction = EnumFacing.EAST;

			if (this.slider.posX > this.slider.getAttackTarget().posX)
			{
				this.direction = EnumFacing.WEST;
			}
		}
		else
		{
			this.direction = EnumFacing.SOUTH;

			if (this.slider.posZ > this.slider.getAttackTarget().posZ)
			{
				this.direction = EnumFacing.NORTH;
			}
		}
		if (y > x && y > z || y > 0.25D && this.slider.world.rand.nextInt(5) == 0)
		{
			this.direction = EnumFacing.UP;

			if (this.slider.posY > this.slider.getAttackTarget().posY)
			{
				this.direction = EnumFacing.DOWN;
			}
		}

		this.slider.world.playSound((EntityPlayer) null, this.slider.posX, this.slider.posY, this.slider.posZ, SoundsAether.SLIDER_MOVE, SoundCategory.HOSTILE, 2.5F, 1.0F / (this.slider.world.rand.nextFloat() * 0.2F + 0.9F));

		this.moving = true;

		return this.shouldContinueExecuting();
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		return --this.moveTime <= 0 && this.slider.getAttackTarget() != null && this.slider.isAwake();
	}

	@Override
	public void tick()
	{
		BlockPos position = new BlockPos(MathHelper.floor(this.slider.posX), MathHelper.floor(this.slider.getBoundingBox().minY) - 1, MathHelper.floor(this.slider.posZ));

		if (this.slider.collidedHorizontally && !this.moving && !this.slider.world.isAirBlock(position) && !this.blockCrushed)
		{
			this.slider.motionY = 1.2000000476837158D;
		}

		double x;
		double y;
		double z;

		if (this.moving)
		{
			if (this.slider.collided)
			{
				x = this.slider.posX - 0.5D;
				y = this.slider.getBoundingBox().minY + 0.75D;
				z = this.slider.posZ - 0.5D;

				this.blockCrushed = false;

				if (y < 124.0D && y > 4.0D)
				{
					MutableBlockPos mutablePos = new MutableBlockPos();
					int i;
					double a;
					double b;

					if (this.direction == EnumFacing.UP)
					{
						for (i = 0; i < 25; ++i)
						{
							a = (double) (i / 5 - 2) * 0.75D;
							b = (double) (i % 5 - 2) * 0.75D;
							this.startBreakingBlock(mutablePos.setPos(x + a, y + 1.5D, z + b));
						}
					}
					else if (this.direction == EnumFacing.DOWN)
					{
						for (i = 0; i < 25; ++i)
						{
							a = (double) (i / 5 - 2) * 0.75D;
							b = (double) (i % 5 - 2) * 0.75D;
							this.startBreakingBlock(mutablePos.setPos(x + a, y - 1.5D, z + b));
						}
					}
					else if (this.direction == EnumFacing.EAST)
					{
						for (i = 0; i < 25; ++i)
						{
							a = (double) (i / 5 - 2) * 0.75D;
							b = (double) (i % 5 - 2) * 0.75D;
							this.startBreakingBlock(mutablePos.setPos(x + 1.5D, y + a, z + b));
						}
					}
					else if (this.direction == EnumFacing.WEST)
					{
						for (i = 0; i < 25; ++i)
						{
							a = (double) (i / 5 - 2) * 0.75D;
							b = (double) (i % 5 - 2) * 0.75D;
							this.startBreakingBlock(mutablePos.setPos(x - 1.5D, y + a, z + b));
						}
					}
					else if (this.direction == EnumFacing.SOUTH)
					{
						for (i = 0; i < 25; ++i)
						{
							a = (double) (i / 5 - 2) * 0.75D;
							b = (double) (i % 5 - 2) * 0.75D;
							this.startBreakingBlock(mutablePos.setPos(x + a, y + b, z + 1.5D));
						}
					}
					else if (this.direction == EnumFacing.NORTH)
					{
						for (i = 0; i < 25; ++i)
						{
							a = (double) (i / 5 - 2) * 0.75D;
							b = (double) (i % 5 - 2) * 0.75D;
							this.startBreakingBlock(mutablePos.setPos(x + a, y + b, z - 1.5D));
						}
					}
				}

				if (this.blockCrushed)
				{
					this.slider.world.playSound((EntityPlayer) null, this.slider.posX, this.slider.posY, this.slider.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.HOSTILE, 3.0F, (0.625F + (this.slider.world.rand.nextFloat() - this.slider.world.rand.nextFloat()) * 0.2F) * 0.7F);
					this.slider.world.playSound((EntityPlayer) null, this.slider.posX, this.slider.posY, this.slider.posZ, SoundsAether.SLIDER_COLLIDE, SoundCategory.HOSTILE, 2.5F, 1.0F / (this.slider.world.rand.nextFloat() * 0.2F + 0.9F));
				}

				this.resetTask();
			}
			else
			{
				if (this.slider.getAIMoveSpeed() < 2.0F)
				{
					this.slider.setAIMoveSpeed(this.slider.getAIMoveSpeed() + (this.hasCriticalHealth() ? 0.07F : 0.035F));
				}

				this.slider.motionX = this.slider.motionY = this.slider.motionZ = 0.0D;

				if (this.direction == EnumFacing.UP)
				{
					this.slider.motionY = (double) this.slider.getAIMoveSpeed();

					if (this.slider.getBoundingBox().minY > this.slider.getAttackTarget().getBoundingBox().minY + 0.35D)
					{
						this.resetTask();
					}
				}
				else if (this.direction == EnumFacing.DOWN)
				{
					this.slider.motionY = (double) (-this.slider.getAIMoveSpeed());

					if (this.slider.getBoundingBox().minY < this.slider.getAttackTarget().getBoundingBox().minY - 0.25D)
					{
						this.resetTask();
					}
				}
				else if (this.direction == EnumFacing.EAST)
				{
					this.slider.motionX = (double) this.slider.getAIMoveSpeed();

					if (this.slider.posX > this.slider.getAttackTarget().posX + 0.125D)
					{
						this.resetTask();
					}
				}
				else if (this.direction == EnumFacing.WEST)
				{
					this.slider.motionX = (double) (-this.slider.getAIMoveSpeed());

					if (this.slider.posX < this.slider.getAttackTarget().posX - 0.125D)
					{
						this.resetTask();
					}
				}
				else if (this.direction == EnumFacing.SOUTH)
				{
					this.slider.motionZ = (double) this.slider.getAIMoveSpeed();

					if (this.slider.posZ > this.slider.getAttackTarget().posZ + 0.125D)
					{
						this.resetTask();
					}
				}
				else if (this.direction == EnumFacing.NORTH)
				{
					this.slider.motionZ = (double) (-this.slider.getAIMoveSpeed());

					if (this.slider.posZ < this.slider.getAttackTarget().posZ - 0.125D)
					{
						this.resetTask();
					}
				}
			}
		}
		/*else if (this.moveTime > 0)
		{
			--this.moveTime;

			if (this.hasCriticalHealth() && this.slider.world.rand.nextInt(2) == 0)
			{
				--this.moveTime;
			}

			this.slider.motionX = this.slider.motionY = this.slider.motionZ = 0.0D;
		}*/
	}

	public void resetTask()
	{
		this.moving = false;
		this.direction = EnumFacing.UP;
		this.moveTime = 6;
		//this.moveTime = this.hasCriticalHealth() ? 3 : 5;
		this.slider.motionX = this.slider.motionY = this.slider.motionZ = 0.0D;
		this.slider.setAIMoveSpeed(0.0F);
	}

	public void startBreakingBlock(BlockPos pos)
	{
		IBlockState state = this.slider.world.getBlockState(pos);

		if (!state.isAir(this.slider.world, pos) && !(state.getBlock() instanceof BlockDungeon))
		{
			this.blockCrushed = true;

			state.dropBlockAsItem(this.slider.world, pos, 0);

			this.slider.world.setBlockState(pos, Blocks.AIR.getDefaultState());

			if (this.slider.world.isRemote)
			{
				state.addDestroyEffects(this.slider.world, pos, net.minecraft.client.Minecraft.getInstance().particles);
			}

			this.slider.spawnExplosionParticle();
		}
	}

	public void onSliderHit()
	{
		this.slider.setAIMoveSpeed(this.slider.getAIMoveSpeed() * 0.75F);
	}

	public boolean hasCriticalHealth()
	{
		return this.slider.getHealth() <= 60.0F;
	}

	public boolean isMoving()
	{
		return this.moving;
	}

}