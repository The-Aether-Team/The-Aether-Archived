package com.legacy.aether.blocks.natural;

import com.legacy.aether.AetherConfig;
import com.legacy.aether.blocks.util.EnumCloudType;
import com.legacy.aether.blocks.util.IAetherMeta;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

import com.legacy.aether.registry.sounds.SoundsAether;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockAercloud extends Block implements IAetherMeta
{

	public static final PropertyEnum<EnumCloudType> cloud_type = PropertyEnum.create("aether_aercloud", EnumCloudType.class);
	public static final PropertyEnum<EnumFacing> property_facing = PropertyEnum.create("facing", EnumFacing.class);

	public BlockAercloud()
	{
		super(Material.ICE);

		this.setHardness(0.2F);
		this.setSoundType(SoundType.CLOTH);
		this.setCreativeTab(AetherCreativeTabs.blocks);
		this.setDefaultState(this.blockState.getBaseState().withProperty(cloud_type, EnumCloudType.Cold).withProperty(property_facing, EnumFacing.NORTH));
	}

	public static int getHexColor(ItemStack stack)
	{
		if (stack.getMetadata() == 1)
		{
			if (AetherConfig.visual_options.updated_aercloud_colors)
			{
				return 0x71d2ff;
			}

			else
			{
				return 0xCCFFFF;
			}
		}
		else if (stack.getMetadata() == 2)
		{
			if (AetherConfig.visual_options.updated_aercloud_colors)
			{
				return 0xffe082;
			}

			else
			{
				return 0xFFFF80;
			}
		}
		else if (stack.getMetadata() == 4)	//Green
		{
			if (AetherConfig.visual_options.updated_aercloud_colors)
			{
				return 0xb0ea6b;
			}
			else
			{
				return 0xcfff94;
			}
		}
		else if (stack.getMetadata() == 5) //Storm
		{
			if (AetherConfig.visual_options.updated_aercloud_colors)
			{
				return 0x576d90;
			}
			else
			{
				return 0x8497b6;
			}
		}
		else if (stack.getMetadata() >= 6) //Purple
		{
			if (AetherConfig.visual_options.updated_aercloud_colors)
			{
				return 0xb77ff3;
			}
			else
			{
				return 0xe0c3ff;
			}
		}

		return 16777215; //Default color
	}

	@Override
	public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		entity.fallDistance = 0;

		boolean isSneakingPlayer = false;

		if (entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;

			if (player.isSneaking())
			{
				isSneakingPlayer = true;
			}
		}

		if (state.getValue(cloud_type).equals(EnumCloudType.Blue) && !isSneakingPlayer)
		{
			entity.motionY = 2.0D;

			if (world.isRemote)
			{
				if (!(entity instanceof EntityItem))
				{
					world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundsAether.aercloud_bounce, SoundCategory.BLOCKS, 0.8f,
							0.9f + (world.rand.nextFloat() * 0.2f), false);
				}

				for (int count = 0; count < 50; count++)
				{
					double xOffset = pos.getX() + world.rand.nextDouble();
					double yOffset = pos.getY() + 1.0D;
					double zOffset = pos.getZ() + world.rand.nextDouble();

					world.spawnParticle(EnumParticleTypes.WATER_SPLASH, xOffset, yOffset, zOffset, 0, 0, 0);
				}
			}
		}
		else if ((state.getValue(cloud_type).equals(EnumCloudType.Green) || state.getValue(cloud_type).equals(EnumCloudType.Purple)) && !isSneakingPlayer)
		{
			final EnumFacing facing = state.getValue(cloud_type).equals(EnumCloudType.Green) ? EnumFacing.random(world.rand) : state.getValue(property_facing);

			entity.motionX = facing.getXOffset() * 2.5D;
			entity.motionZ = facing.getZOffset() * 2.5D;

			if (world.isRemote && !(entity instanceof EntityItem)) {
				world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundsAether.aercloud_bounce, SoundCategory.BLOCKS, 0.8f,
						0.9f + (world.rand.nextFloat() * 0.2f), false);
			}
		}
		else if (state.getValue(cloud_type).equals(EnumCloudType.Golden) && !isSneakingPlayer)
		{
			entity.motionY = -1.2D;

			if (world.isRemote && !(entity instanceof EntityItem))
			{
				world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundsAether.aercloud_bounce, SoundCategory.BLOCKS, 0.8f,
						0.9f + (world.rand.nextFloat() * 0.2f), false);
			}
		}
		else
		{
			if (state.getValue(cloud_type).equals(EnumCloudType.Pink))
			{
				if (entity.ticksExisted % 20 == 0 && entity instanceof EntityLivingBase)
				{
					((EntityLivingBase)entity).heal(1.0F);
				}
			}

			if (entity.motionY < 0)
			{
				entity.motionY *= 0.005D;
			}
		}
	}

	@Override
	public String getMetaName(ItemStack stack)
	{
		return ((EnumCloudType)this.getStateFromMeta(stack.getItemDamage()).getValue(cloud_type)).getName();
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return ((EnumCloudType)state.getValue(cloud_type)).getMeta();
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
	{
		for (int j = 0; j < EnumCloudType.values().length; ++j)
		{
			EnumCloudType cloud_type = EnumCloudType.values()[j];

			if (AetherConfig.world_gen.pink_aerclouds)
			{
				list.add(new ItemStack(this, 1, cloud_type.getMeta()));
			}
			else if (cloud_type != EnumCloudType.Pink)
			{
				list.add(new ItemStack(this, 1, cloud_type.getMeta()));
			}
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		if (meta >= EnumCloudType.Purple.getMeta()) //Purple
		{
			return this.getDefaultState().withProperty(cloud_type, EnumCloudType.Purple)
					.withProperty(property_facing, EnumFacing.byHorizontalIndex(meta - EnumCloudType.Purple.getMeta()));
		}


		return this.getDefaultState().withProperty(cloud_type, EnumCloudType.getType(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		if (state.getValue(cloud_type) == EnumCloudType.Purple)
		{
			return EnumCloudType.Purple.getMeta() + state.getValue(property_facing).getHorizontalIndex();
		}

		return ((EnumCloudType)state.getValue(cloud_type)).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {cloud_type, property_facing});
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		IBlockState iblockstate = worldIn.getBlockState(pos);
		Block block = iblockstate.getBlock();

		if (worldIn.getBlockState(pos.offset(side)) != iblockstate)
		{
			return true;
		}

		if (block == this)
		{
			return false;
		}

		return !worldIn.getBlockState(pos.offset(side)).doesSideBlockRendering(worldIn, pos.offset(side), side.getOpposite());
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		if (blockState.getValue(cloud_type) == EnumCloudType.Blue   ||
			blockState.getValue(cloud_type) == EnumCloudType.Golden ||
			blockState.getValue(cloud_type) == EnumCloudType.Green  ||
			blockState.getValue(cloud_type) == EnumCloudType.Purple)
		{
			return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		}
		else
		{
			return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.01D, 1.0D);
		}
	}

	@Override
	public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY,
											final float hitZ, final int meta,
											final EntityLivingBase placer)
	{
		return this.getStateFromMeta(meta).withProperty(property_facing, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(final IBlockState state, final World world, final BlockPos pos, final Random rand)
	{
		if (state.getValue(cloud_type) == EnumCloudType.Purple)
		{
			final float x = pos.getX() + (rand.nextFloat() * 0.7f) + 0.15f;
			final float y = pos.getY() + (rand.nextFloat() * 0.7f) + 0.15f;
			final float z = pos.getZ() + (rand.nextFloat() * 0.7f) + 0.15f;

			final EnumFacing facing = state.getValue(property_facing);

			final float motionX = facing.getXOffset() * ((rand.nextFloat() * 0.01f) + 0.05f);
			final float motionZ = facing.getZOffset() * ((rand.nextFloat() * 0.01f) + 0.05f);

			world.spawnParticle(EnumParticleTypes.CLOUD, x, y, z, motionX, 0, motionZ);
		}
	}
}