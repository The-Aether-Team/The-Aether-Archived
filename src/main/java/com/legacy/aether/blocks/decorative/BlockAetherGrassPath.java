package com.legacy.aether.blocks.decorative;

import com.legacy.aether.Aether;
import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.natural.BlockAetherDirt;
import com.legacy.aether.items.util.DoubleDropHelper;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockAetherGrassPath extends Block
{
	public static final PropertyBool double_drop = PropertyBool.create(Aether.doubleDropNotifier());

	protected static final AxisAlignedBB GRASS_PATH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);
	protected static final AxisAlignedBB PUSH_UP_AABB = new AxisAlignedBB(0.0D, 0.9375D, 0.0D, 1.0D, 1.0D, 1.0D);

	public BlockAetherGrassPath()
	{
		super(Material.GROUND);
		this.setLightOpacity(255);

		this.setHardness(0.65F);
		this.setCreativeTab(AetherCreativeTabs.blocks);
		this.setSoundType(SoundType.PLANT);
		this.setDefaultState(this.getDefaultState().withProperty(double_drop, true));
	}

	/**
     * Get the Item that this Block should drop when harvested.
     */
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(BlocksAether.aether_dirt);
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
	{
		DoubleDropHelper.dropBlock(player, state, pos, double_drop);
	}

	/**
	 * Handle block updates - becomes aether dirt if block placed above
	 */
	protected void updateBlockState(World worldIn, BlockPos pos)
    {
    	IBlockState state = worldIn.getBlockState(pos);

    	if (worldIn.getBlockState(pos.up()).getMaterial().isSolid())
        {
        	//maintain double drop state
        	boolean isDoubleDrop = state.getValue(double_drop);
            worldIn.setBlockState(pos, BlocksAether.aether_dirt.getDefaultState().withProperty(BlockAetherDirt.double_drop, isDoubleDrop));
			AxisAlignedBB axisalignedbb = PUSH_UP_AABB.offset(pos);

			for (Entity entity : worldIn.getEntitiesWithinAABBExcludingEntity((Entity)null, axisalignedbb))
			{
				double d0 = Math.min(axisalignedbb.maxY - axisalignedbb.minY, axisalignedbb.maxY - entity.getEntityBoundingBox().minY);
				entity.setPositionAndUpdate(entity.posX, entity.posY + d0 + 0.001D, entity.posZ);
			}
        }
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		world.setBlockState(pos, state.withProperty(double_drop, false));
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        super.onBlockAdded(worldIn, pos, state);
        this.updateBlockState(worldIn, pos);
    }

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(double_drop) ? 0 : 1;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(double_drop, meta == 0);
	}

	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, double_drop);
	}

	@Override
	public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
	{
		list.add(new ItemStack(this, 1, 1));
	}

	@Override
	public IBlockState getStateForPlacement(final World world, final BlockPos pos, final EnumFacing facing, final float hitX, final float hitY,
											final float hitZ, final int meta,
											final EntityLivingBase placer)
	{
		return this.getStateFromMeta(meta);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) { return false; }

	@Override
    public boolean isFullCube(IBlockState state) { return false; }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        this.updateBlockState(worldIn, pos);
    }
//
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		switch (side)
		{
			case UP:
				return true;

			case NORTH:
			case SOUTH:
			case WEST:
			case EAST:
				IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
				Block block = iblockstate.getBlock();
				return !iblockstate.isOpaqueCube() && block != Blocks.FARMLAND && block != Blocks.GRASS_PATH && block != BlocksAether.aether_grass_path;

			default:
				return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return GRASS_PATH_AABB;
    }

	@Override
	public boolean isToolEffective(String type, IBlockState state)
	{
		return type != null && type.equals("shovel");
	}
}
