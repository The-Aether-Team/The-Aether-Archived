package com.gildedgames.the_aether.blocks.natural;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.blocks.util.EnumGrassType;
import com.gildedgames.the_aether.blocks.util.EnumTallGrassType;
import com.gildedgames.the_aether.blocks.util.IAetherMeta;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockTallAetherGrass extends BlockAetherPlant implements IShearable, IAetherMeta//, IBlockMultiName
{
//	public static final BlockVariant SHORT = new BlockVariant(0, "short"),
//			NORMAL = new BlockVariant(1, "normal"),
//			LONG = new BlockVariant(2, "long");

	public static final PropertyEnum<EnumTallGrassType> variant = PropertyEnum.create("variant", EnumTallGrassType.class);

//	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", SHORT, NORMAL, LONG);

	public static final PropertyEnum<BlockTallAetherGrass.Type> type = PropertyEnum
			.create("type", BlockTallAetherGrass.Type.class);

	public static final PropertyBool snowy = PropertyBool.create("snowy");

	private static final AxisAlignedBB GRASS_SHORT_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.3D, 0.9D);

	private static final AxisAlignedBB GRASS_NORMAL_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.6D, 0.9D);

	private static final AxisAlignedBB GRASS_LONG_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.9D, 0.9D);

	private static final IBlockState ARCTIC_GRASS = BlocksAether.aether_grass.getDefaultState()
			.withProperty(BlockAetherGrass.variant, EnumGrassType.Arctic);

	private static final IBlockState MAGNETIC_GRASS = BlocksAether.aether_grass.getDefaultState()
			.withProperty(BlockAetherGrass.variant, EnumGrassType.Magnetic);

	private static final IBlockState ENCHANTED_GRASS = BlocksAether.enchanted_aether_grass.getDefaultState();

	private static final IBlockState IRRADIATED_GRASS = BlocksAether.aether_grass.getDefaultState()
			.withProperty(BlockAetherGrass.variant, EnumGrassType.Irradiated);

	public BlockTallAetherGrass()
	{
		super(Material.PLANTS);

		this.setSoundType(SoundType.PLANT);

		this.setDefaultState(this.getBlockState().getBaseState().withProperty(variant, EnumTallGrassType.Short).withProperty(type, Type.Aether)
				.withProperty(snowy, Boolean.FALSE));
	}

	@Override
	public IBlockState getActualState(final IBlockState state, final IBlockAccess worldIn, final BlockPos pos)
	{
		IBlockState downState = worldIn.getBlockState(pos.down());

		Type theType = Type.Aether;

		if (downState.getBlock() instanceof BlockAetherGrass)
		{
			//must match double drop value or else compare will fail
			downState = downState.withProperty(BlockAetherGrass.double_drop, ARCTIC_GRASS.getValue(BlockAetherGrass.double_drop));

			if (downState == ARCTIC_GRASS)
			{
				theType = Type.Arctic;
			}
			else if (downState == MAGNETIC_GRASS)
			{
				theType = Type.Magnetic;
			}
			else if (downState == IRRADIATED_GRASS)
			{
				theType = Type.Irradiated;
			}
		}
		else if (downState == ENCHANTED_GRASS)
		{
			theType = Type.Enchanted;
		}

		return state.withProperty(type, theType);
	}

	@Override
//	@SideOnly(Side.CLIENT)
	public void getSubBlocks(final CreativeTabs tab, final NonNullList<ItemStack> list)
	{
		for (final EnumTallGrassType variant : variant.getAllowedValues())
		{
			list.add(new ItemStack(this, 1, variant.getMeta()));
		}
	}

	@Override
	public Item getItemDropped(final IBlockState state, final Random rand, final int fortune)
	{
		return null;
	}

	@Override
	public boolean isShearable(final ItemStack item, final IBlockAccess world, final BlockPos pos)
	{
		return true;
	}

	@Override
	public List<ItemStack> onSheared(final ItemStack item, final IBlockAccess world, final BlockPos pos, final int fortune)
	{
		final List<ItemStack> drops = new ArrayList<>();
		IBlockState state = world.getBlockState(pos);

		drops.add(new ItemStack(this, 1, this.getMetaFromState(state) - (state.getValue(snowy) ? variant.getAllowedValues().size() : 0)));

		return drops;
	}

	@Override
	public void onPlayerDestroy(World worldIn, BlockPos pos, IBlockState state)
	{
		if (state.getValue(snowy))
		{
			if (worldIn.getBlockState(pos.down()) != Blocks.AIR.getDefaultState())
			{
//				worldIn.setBlockState(pos, BlocksAether.highlands_snow_layer.getDefaultState().withProperty(BlockSnow.LAYERS, 1), 2);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Block.EnumOffsetType getOffsetType()
	{
		return EnumOffsetType.XZ;
	}

	@Override
	public AxisAlignedBB getBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos)
	{
		if (state.getValue(variant) == EnumTallGrassType.Short)
		{
			return GRASS_SHORT_AABB;
		}
		else if (state.getValue(variant) == EnumTallGrassType.Normal)
		{
			return GRASS_NORMAL_AABB;
		}
		else if (state.getValue(variant) == EnumTallGrassType.Long)
		{
			return GRASS_LONG_AABB;
		}

		return super.getBoundingBox(state, source, pos);
	}

	@Override
	public IBlockState getStateFromMeta(final int meta)
	{
		final boolean snowy = meta >= variant.getAllowedValues().size();
		final EnumTallGrassType theVariant = EnumTallGrassType.getType(meta - (snowy ? variant.getAllowedValues().size() : 0));

		return this.getDefaultState().withProperty(variant, theVariant).withProperty(BlockTallAetherGrass.snowy, snowy);
	}

	@Override
	public boolean isReplaceable(final IBlockAccess worldIn, final BlockPos pos)
	{
		return true;
	}

	@Override
	public int getMetaFromState(final IBlockState state)
	{
		return state.getValue(variant).getMeta() + (state.getValue(snowy) ? variant.getAllowedValues().size() : 0);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, type, variant, snowy);
	}

//	@Override
//	public String getUnlocalizedName(final ItemStack stack)
//	{
//		return EnumTallGrassType.getType(stack.getMetadata()).getName();
//	}

//	@Override
//	public String getTranslationKey(final ItemStack stack)
//	{
//		return EnumTallGrassType.getType(stack.getMetadata()).getName();
//	}

	@Override
	public Vec3d getOffset(final IBlockState state, final IBlockAccess access, final BlockPos pos)
	{
		if (state.getProperties().get(snowy).equals(true))
		{
			return Vec3d.ZERO;
		}

		return super.getOffset(state, access, pos);
	}

	@Override
	public int damageDropped(final IBlockState state)
	{
		return state.getValue(variant).getMeta();
	}

	@Override
	public String getMetaName(ItemStack stack)
	{
		return this.getStateFromMeta(stack.getItemDamage()).getValue(variant).getName();
	}

	@Override
	public boolean isSuitableSoilBlock(final IBlockState state)
	{
		return state.getBlock() == BlocksAether.aether_grass || state.getBlock() == BlocksAether.enchanted_aether_grass;
	}

	public enum Type implements IStringSerializable
	{
		Aether("aether"), Enchanted("enchanted"), Arctic("arctic"), Magnetic("magnetic"), Irradiated("irradiated");

		private final String name;

		Type(final String name)
		{
			this.name = name;
		}

		@Override
		public String getName()
		{
			return this.name;
		}
	}

}
