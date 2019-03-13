package com.legacy.aether.entity.boss;

import com.legacy.aether.Aether;
import com.legacy.aether.api.AetherAPI;
import com.legacy.aether.api.player.util.IAetherBoss;
import com.legacy.aether.block.BlocksAether;
import com.legacy.aether.entity.EntityTypesAether;
import com.legacy.aether.entity.ai.EntityAISliderMove;
import com.legacy.aether.entity.boss.name.BossNameGenerator;
import com.legacy.aether.sound.SoundsAether;
import com.legacy.aether.util.AetherTranslation;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.Particles;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class EntitySlider extends EntityFlying implements IAetherBoss
{

	public static final DataParameter<Boolean> AWAKE = EntityDataManager.createKey(EntitySlider.class, DataSerializers.BOOLEAN);

	private EntityAISliderMove movementAI;

	private BlockPos dungeonPosition;

	public float hurtAngle;

	public float hurtAngleX;

	public float hurtAngleZ;

	public int chatTime;

	public EntitySlider(World world)
	{
		super(EntityTypesAether.SLIDER, world);

		this.setSize(2.0F, 2.0F);
		this.setCustomNameVisible(false);
		this.setCustomName(new TextComponentString(BossNameGenerator.createName()));
	}

	@Override
	protected void registerData()
	{
		super.registerData();

		this.dataManager.register(AWAKE, false);
	}

	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(0, this.getMovementAI());
	}

	@Override
	protected void registerAttributes()
	{
		super.registerAttributes();

		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(500.0D);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0D);
	}

	@Override
	public void tick()
	{
		super.tick();

		if (this.hurtAngle > 0.01F)
		{
			this.hurtAngle *= 0.8F;
		}

		if (this.chatTime > 0)
		{
			--this.chatTime;
		}

		if (!this.isAwake())
		{
			this.setAttackTarget(null);
		}
		else
		{
			if (!this.world.isRemote && (this.getAttackTarget() == null || this.getAttackTarget().getHealth() <= 0.0F))
			{
				this.resetSlider();
			}

			this.renderYawOffset = this.rotationPitch = this.rotationYaw = 0.0F;
		}
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (!(source.getImmediateSource() instanceof EntityPlayer))
		{
			return false;
		}
		else
		{
			EntityPlayer player = (EntityPlayer) source.getImmediateSource();
			ItemStack stack = player.getHeldItemMainhand();

			if (stack.isEmpty())
			{
				return false;
			}
			else if (stack.getItem() == Items.APPLE)
			{
				player.sendMessage(AetherTranslation.getKeyComponent("gui.slider.apple"));
			}
			else if (!source.isCreativePlayer() && source.getTrueSource().getDistance(this) > 6.0F && !this.isAwake())
			{
				player.sendMessage(new TextComponentString("It seems I'm too far away to wake it."));
			}
			else if (!stack.getItem().getToolTypes(stack).contains(ToolType.PICKAXE))
			{
				player.sendMessage(AetherTranslation.getKeyComponent("gui.slider.notpickaxe"));
			}
			else
			{
				boolean flag = super.attackEntityFrom(source, Math.min(10.0F, amount));

				if (flag)
				{
					for (int j = 0; j < 4; ++j)
					{
						double randomX = this.posX + (this.rand.nextFloat() - this.rand.nextFloat()) * 1.5D;
						double randomY = this.getBoundingBox().minY + 1.75D;
						double randomZ = this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()) * 1.5D;

						if (this.world.isRemote)
						{
							Blocks.STONE.getDefaultState().addDestroyEffects(this.world, new BlockPos(randomX, randomY, randomZ), net.minecraft.client.Minecraft.getInstance().particles);
						}
					}

					if (!this.isAwake())
					{
						this.setAwake(true);
						this.setAttackTarget(player);
						this.setDungeonDoor(BlocksAether.CARVED_STONE.getDefaultState());
						this.world.playSound(null, this.posX, this.posY, this.posZ, SoundsAether.SLIDER_AWAKEN, SoundCategory.HOSTILE, 2.5F, 1.0F / (this.rand.nextFloat() * 0.2F + 0.9F));
					}

					this.getMovementAI().onSliderHit();
				}

				double distanceX = Math.abs(this.posX - player.posX);
				double distanceZ = Math.abs(this.posZ - player.posZ);

				if (distanceX > distanceZ)
				{
					this.hurtAngleX = 0.0F;
					this.hurtAngleZ = 1.0F;

					if (this.posX > player.posX)
					{
						this.hurtAngleZ = -1.0F;
					}
				}
				else
				{
					this.hurtAngleZ = 0.0F;
					this.hurtAngleX = 1.0F;

					if (this.posZ > player.posZ)
					{
						this.hurtAngleX = -1.0F;
					}
				}

				this.hurtAngle = 0.7F - this.getHealth() / 875.0F;

				AetherAPI.getInstance().get(player).setFocusedBoss(this);

				return flag;
			}

			return false;
		}
	}

	@Override
	protected void collideWithEntity(Entity entityIn)
	{
		if (!entityIn.isRidingSameEntity(this) && !this.noClip && !entityIn.noClip)
		{
			double d0 = this.posX - entityIn.posX;
			double d1 = this.posZ - entityIn.posZ;
			double d2 = MathHelper.absMax(d0, d1);

			if (d2 >= 0.009999999776482582D)
			{
				d2 = (double) MathHelper.sqrt(d2);
				d0 /= d2;
				d1 /= d2;

				double d3 = 1.0D / d2;

				if (d3 > 1.0D)
				{
					d3 = 1.0D;
				}

				d0 *= d3;
				d1 *= d3;
				d0 *= 0.05000000074505806D;
				d1 *= 0.05000000074505806D;
				d0 *= (double) (1.0F - entityIn.entityCollisionReduction);
				d1 *= (double) (1.0F - entityIn.entityCollisionReduction);

				if (!entityIn.isBeingRidden())
				{
					entityIn.addVelocity(-d0 * 2.5D, 0.0D, -d1 * 2.5D);
				}
			}
		}
	}

	@Override
	public void applyEntityCollision(Entity entityIn)
	{
		if (this.isAwake() && this.getMovementAI().isMoving())
		{
			if (entityIn instanceof EntityLivingBase)
			{
				boolean flag = ((EntityLivingBase)entityIn).attackEntityFrom(new EntityDamageSource("crush", this), 6.0F);

				if (flag)
				{
					entityIn.addVelocity(entityIn.motionY, 0.35D, entityIn.motionZ);
					entityIn.velocityChanged = true;

					this.world.playSound(null, this.posX, this.posY, this.posZ, SoundsAether.SLIDER_COLLIDE, SoundCategory.HOSTILE, 2.5F, 1.0F / (this.rand.nextFloat() * 0.2F + 0.9F));

					this.getMovementAI().resetTask();
				}
			}
		}
	}

	@Override
	public void knockBack(Entity entity, float strength, double xRatio, double zRatio)
	{
		if (this.getHealth() < 0.0F)
		{
			super.knockBack(entity, strength, xRatio, zRatio);
		}
	}

	@Override
	public void onDeath(DamageSource source)
	{
		super.onDeath(source);
		this.unlockDungeon(this.dungeonPosition);
		this.setDungeonDoor(Blocks.AIR.getDefaultState());

		this.world.setBlockState(this.dungeonPosition.add(7, 1, 7), Blocks.OAK_TRAPDOOR.getDefaultState().with(BlockHorizontal.HORIZONTAL_FACING, EnumFacing.SOUTH));
		this.world.setBlockState(this.dungeonPosition.add(8, 1, 7), Blocks.OAK_TRAPDOOR.getDefaultState().with(BlockHorizontal.HORIZONTAL_FACING, EnumFacing.SOUTH));
		this.world.setBlockState(this.dungeonPosition.add(7, 1, 8), Blocks.OAK_TRAPDOOR.getDefaultState().with(BlockHorizontal.HORIZONTAL_FACING, EnumFacing.NORTH));
		this.world.setBlockState(this.dungeonPosition.add(8, 1, 8), Blocks.OAK_TRAPDOOR.getDefaultState().with(BlockHorizontal.HORIZONTAL_FACING, EnumFacing.NORTH));

		for (int j = 0; j < 2; ++j)
		{
			double randomX = this.posX + (double) (this.rand.nextFloat() - this.rand.nextFloat()) * 1.5D;
			double randomY = this.getBoundingBox().minY + 1.75D;
			double randomZ = this.posZ + (double) (this.rand.nextFloat() - this.rand.nextFloat()) * 1.5D;
			this.world.spawnParticle(Particles.EXPLOSION, randomX, randomY, randomZ, 0.0D, 0.0D, 0.0D);
		}

		if (source.getImmediateSource() instanceof EntityPlayer)
		{
			AetherAPI.getInstance().get((EntityPlayer) source.getImmediateSource()).setFocusedBoss(null);
		}
	}

	public void resetSlider()
	{
		this.getMovementAI().resetTask();

		this.setAwake(false);
		this.setAttackTarget(null);
		this.setHealth(this.getMaxHealth());
		this.setDungeonDoor(Blocks.AIR.getDefaultState());
		this.setPositionAndUpdate(this.dungeonPosition.getX() + 8, this.dungeonPosition.getY() + 2, this.dungeonPosition.getZ() + 8);
	}

	private void setDungeonDoor(IBlockState state)
	{
		for (int y = this.dungeonPosition.getY() + 1; y < this.dungeonPosition.getY() + 5; ++y)
		{
			for (int z = this.dungeonPosition.getZ() + 6; z < this.dungeonPosition.getZ() + 10; ++z)
			{
				this.world.setBlockState(new BlockPos(this.dungeonPosition.getX() + 15, y, z), state);
			}
		}
	}

	private void unlockDungeon(BlockPos pos)
	{
		if (this.world.getBlockState(pos).getBlock() == BlocksAether.LOCKED_CARVED_STONE)
		{
			this.world.setBlockState(pos, BlocksAether.LOCKED_CARVED_STONE.getDefaultState());

			this.unlockDungeon(pos.east());
			this.unlockDungeon(pos.west());
			this.unlockDungeon(pos.up());
			this.unlockDungeon(pos.down());
			this.unlockDungeon(pos.south());
			this.unlockDungeon(pos.north());
		}
	}

	@Override
	public void writeAdditional(NBTTagCompound compound)
	{
		super.writeAdditional(compound);

		compound.setBoolean("awake", this.isAwake());
		compound.setLong("dungeonPosition", this.dungeonPosition.toLong());
	}

	@Override
	public void readAdditional(NBTTagCompound compound)
	{
		super.readAdditional(compound);

		this.setAwake(compound.getBoolean("awake"));
		this.dungeonPosition = BlockPos.fromLong(compound.getLong("dungeonPosition"));
	}

	@Override
	public boolean isPotionApplicable(PotionEffect effect)
	{
		return false;
	}

	@Override
	public boolean isPushedByWater()
	{
		return false;
	}

	public void setAwake(boolean awake)
	{
		this.dataManager.set(AWAKE, awake);
	}

	public boolean isAwake()
	{
		return (Boolean) this.dataManager.get(AWAKE);
	}

	public void setDungeon(double posX, double posY, double posZ)
	{
		this.dungeonPosition = new BlockPos(posX, posY, posZ);
	}

	public EntityAISliderMove getMovementAI()
	{
		if (this.movementAI == null)
		{
			this.movementAI = new EntityAISliderMove(this);
		}

		return this.movementAI;
	}

	@Override
	public String getBossTitle()
	{
		return this.getName().getFormattedText() + ", " + AetherTranslation.getTranslatedKey("title.aether_legacy.slider.name");
	}

	@Override
	public float getBossHealth()
	{
		return this.getHealth();
	}

	@Override
	public float getMaxBossHealth()
	{
		return this.getMaxHealth();
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.AMBIENT_CAVE;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.BLOCK_STONE_STEP;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundsAether.SLIDER_DEATH;
	}

	@Override
	public SoundCategory getSoundCategory()
	{
		return SoundCategory.HOSTILE;
	}

}