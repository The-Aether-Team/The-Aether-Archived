package com.legacy.aether.entity.boss;

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
import net.minecraft.client.Minecraft;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class EntitySlider extends EntityFlying implements IAetherBoss
{

	public static final DataParameter<Boolean> AWAKE;

	private final EntityAISliderMove movementAI = new EntityAISliderMove(this);

	private BlockPos dungeonPosition;

	public float hurtAngle;

	public float hurtAngleX;

	public float hurtAngleZ;

	public int chatTime;
	static
	{
		AWAKE = EntityDataManager.createKey(EntitySlider.class, DataSerializers.BOOLEAN);
	}

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
		this.tasks.addTask(0, this.movementAI);
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
			this.setAttackTarget((EntityLivingBase) null);
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
				player.sendMessage(AetherTranslation.getKeyComponent("gui.slider.apple", new Object[0]));

				return false;
			}
			else if (!source.isCreativePlayer() && source.getTrueSource().getDistance(this) > 6.0F && !this.isAwake())
			{
				player.sendMessage(new TextComponentString("It seems I'm too far away to wake it."));

				return false;
			}
			else if (!stack.getItem().getToolTypes(stack).contains(ToolType.PICKAXE))
			{
				player.sendMessage(AetherTranslation.getKeyComponent("gui.slider.notpickaxe", new Object[0]));

				return false;
			}
			else
			{
				boolean flag = super.attackEntityFrom(source, Math.min(10.0F, amount));

				if (flag)
				{
					for (int j = 0; j < 4; ++j)
					{
						double randomX = this.posX + (double) (this.rand.nextFloat() - this.rand.nextFloat()) * 1.5D;
						double randomY = this.getBoundingBox().minY + 1.75D;
						double randomZ = this.posZ + (double) (this.rand.nextFloat() - this.rand.nextFloat()) * 1.5D;

						if (this.world.isRemote)
						{
							Blocks.STONE.getDefaultState().addDestroyEffects(this.world, new BlockPos(randomX, randomY, randomZ), Minecraft.getInstance().particles);
						}
					}

					if (!this.isAwake())
					{
						this.setAwake(true);
						this.setAttackTarget(player);
						this.setDungeonDoor(BlocksAether.CARVED_STONE.getDefaultState());
						this.world.playSound((EntityPlayer) null, this.posX, this.posY, this.posZ, SoundsAether.SLIDER_AWAKEN, SoundCategory.HOSTILE, 2.5F, 1.0F / (this.rand.nextFloat() * 0.2F + 0.9F));
					}

					this.movementAI.onSliderHit();
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
		}
	}

	@Override
	public void applyEntityCollision(Entity entityIn)
	{
		if (this.isAwake() && this.movementAI.isMoving())
		{
			boolean flag = entityIn.attackEntityFrom(new EntityDamageSource("crush", this), 6.0F);

			if (flag && entityIn instanceof EntityLivingBase)
			{
				entityIn.addVelocity(entityIn.motionY, 0.35D, entityIn.motionZ);

				this.world.playSound((EntityPlayer) null, this.posX, this.posY, this.posZ, SoundsAether.SLIDER_COLLIDE, SoundCategory.HOSTILE, 2.5F, 1.0F / (this.rand.nextFloat() * 0.2F + 0.9F));

				this.movementAI.resetTask();
			}
		}
	}

	@Override
	public void onDeath(DamageSource source)
	{
		super.onDeath(source);
		this.unlockDungeon(this.dungeonPosition);
		this.setDungeonDoor(Blocks.AIR.getDefaultState());

		for (int j = 0; j < 2; ++j)
		{
			double randomX = this.posX + (double) (this.rand.nextFloat() - this.rand.nextFloat()) * 1.5D;
			double randomY = this.getBoundingBox().minY + 1.75D;
			double randomZ = this.posZ + (double) (this.rand.nextFloat() - this.rand.nextFloat()) * 1.5D;
			this.world.spawnParticle(Particles.EXPLOSION, randomX, randomY, randomZ, 0.0D, 0.0D, 0.0D);
		}

		if (source.getImmediateSource() instanceof EntityPlayer)
		{
			AetherAPI.getInstance().get((EntityPlayer) source.getImmediateSource()).setFocusedBoss((IAetherBoss) null);
		}
	}

	public void resetSlider()
	{
		this.movementAI.resetTask();
		this.setAwake(false);
		this.setAttackTarget((EntityLivingBase) null);
		this.setHealth(this.getMaxHealth());
		this.setDungeonDoor(Blocks.AIR.getDefaultState());
		this.setPositionAndUpdate((double) (this.dungeonPosition.getX() + 8), (double) (this.dungeonPosition.getY() + 2), (double) (this.dungeonPosition.getZ() + 8));
	}

	private void setDungeonDoor(IBlockState state)
	{
		int x = this.dungeonPosition.getX() + 15;

		for (int y = this.dungeonPosition.getY() + 1; y < this.dungeonPosition.getY() + 5; ++y)
		{
			for (int z = this.dungeonPosition.getZ() + 6; z < this.dungeonPosition.getZ() + 10; ++z)
			{
				this.world.setBlockState(new BlockPos(x, y, z), state);
			}
		}
	}

	private void unlockDungeon(BlockPos pos)
	{
		IBlockState state = this.world.getBlockState(pos);

		if (state.getBlock() == BlocksAether.LOCKED_CARVED_STONE)
		{
			this.world.setBlockState(pos, BlocksAether.LOCKED_CARVED_STONE.getDefaultState());

			this.unlockDungeon(pos.east());
			this.unlockDungeon(pos.west());
			this.unlockDungeon(pos.up());
			this.unlockDungeon(pos.down());
			this.unlockDungeon(pos.south());
			this.unlockDungeon(pos.north());
		}

		this.world.setBlockState(this.dungeonPosition.add(7, 1, 7), (IBlockState) Blocks.OAK_TRAPDOOR.getDefaultState().with(BlockHorizontal.HORIZONTAL_FACING, EnumFacing.SOUTH));
		this.world.setBlockState(this.dungeonPosition.add(8, 1, 7), (IBlockState) Blocks.OAK_TRAPDOOR.getDefaultState().with(BlockHorizontal.HORIZONTAL_FACING, EnumFacing.SOUTH));
		this.world.setBlockState(this.dungeonPosition.add(7, 1, 8), (IBlockState) Blocks.OAK_TRAPDOOR.getDefaultState().with(BlockHorizontal.HORIZONTAL_FACING, EnumFacing.NORTH));
		this.world.setBlockState(this.dungeonPosition.add(8, 1, 8), (IBlockState) Blocks.OAK_TRAPDOOR.getDefaultState().with(BlockHorizontal.HORIZONTAL_FACING, EnumFacing.NORTH));
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

	@Override
	public String getBossTitle()
	{
		return this.getName().getFormattedText() + ", " + AetherTranslation.getTranslatedKey("title.aether_legacy.slider.name", new Object[0]);
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