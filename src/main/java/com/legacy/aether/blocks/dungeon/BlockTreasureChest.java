package com.legacy.aether.blocks.dungeon;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.legacy.aether.Aether;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.networking.AetherGuiHandler;
import com.legacy.aether.tile_entities.TileEntityTreasureChest;

public class BlockTreasureChest extends BlockContainer
{

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    protected static final AxisAlignedBB NORTH_CHEST_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0D, 0.9375D, 0.875D, 0.9375D);
    protected static final AxisAlignedBB SOUTH_CHEST_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.875D, 1.0D);
    protected static final AxisAlignedBB WEST_CHEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0625D, 0.9375D, 0.875D, 0.9375D);
    protected static final AxisAlignedBB EAST_CHEST_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 1.0D, 0.875D, 0.9375D);
    protected static final AxisAlignedBB NOT_CONNECTED_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.875D, 0.9375D);

    public BlockTreasureChest()
    {
        super(Material.ROCK);

        this.setHardness(-1.0F);
        this.setSoundType(SoundType.STONE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     */
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return source.getBlockState(pos.north()).getBlock() == this ? NORTH_CHEST_AABB : (source.getBlockState(pos.south()).getBlock() == this ? SOUTH_CHEST_AABB : (source.getBlockState(pos.west()).getBlock() == this ? WEST_CHEST_AABB : (source.getBlockState(pos.east()).getBlock() == this ? EAST_CHEST_AABB : NOT_CONNECTED_AABB)));
    }

    /**
     * Called after the block is set in the Chunk data, but before the Tile Entity is set
     */
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.checkForSurroundingChests(worldIn, pos, state);

        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
        {
            BlockPos blockpos = pos.offset(enumfacing);
            IBlockState iblockstate = worldIn.getBlockState(blockpos);

            if (iblockstate.getBlock() == this)
            {
                this.checkForSurroundingChests(worldIn, blockpos, iblockstate);
            }
        }
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
     * IBlockstate
     */
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        EnumFacing enumfacing = EnumFacing.getHorizontal(MathHelper.floor((double)(placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3).getOpposite();
        state = state.withProperty(FACING, enumfacing);
        BlockPos blockpos = pos.north();
        BlockPos blockpos1 = pos.south();
        BlockPos blockpos2 = pos.west();
        BlockPos blockpos3 = pos.east();
        boolean flag = this == worldIn.getBlockState(blockpos).getBlock();
        boolean flag1 = this == worldIn.getBlockState(blockpos1).getBlock();
        boolean flag2 = this == worldIn.getBlockState(blockpos2).getBlock();
        boolean flag3 = this == worldIn.getBlockState(blockpos3).getBlock();

        if (!flag && !flag1 && !flag2 && !flag3)
        {
            worldIn.setBlockState(pos, state, 3);
        }
        else if (enumfacing.getAxis() != EnumFacing.Axis.X || !flag && !flag1)
        {
            if (enumfacing.getAxis() == EnumFacing.Axis.Z && (flag2 || flag3))
            {
                if (flag2)
                {
                    worldIn.setBlockState(blockpos2, state, 3);
                }
                else
                {
                    worldIn.setBlockState(blockpos3, state, 3);
                }

                worldIn.setBlockState(pos, state, 3);
            }
        }
        else
        {
            if (flag)
            {
                worldIn.setBlockState(blockpos, state, 3);
            }
            else
            {
                worldIn.setBlockState(blockpos1, state, 3);
            }

            worldIn.setBlockState(pos, state, 3);
        }

        if (stack.hasDisplayName())
        {
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityTreasureChest)
            {
                ((TileEntityTreasureChest)tileentity).setCustomName(stack.getDisplayName());
            }
        }
    }

    public IBlockState checkForSurroundingChests(World worldIn, BlockPos pos, IBlockState state)
    {
        if (worldIn.isRemote)
        {
            return state;
        }
        else
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (iblockstate.getBlock() != this && iblockstate1.getBlock() != this)
            {
                boolean flag = iblockstate.isFullBlock();
                boolean flag1 = iblockstate1.isFullBlock();

                if (iblockstate2.getBlock() == this || iblockstate3.getBlock() == this)
                {
                    BlockPos blockpos1 = iblockstate2.getBlock() == this ? pos.west() : pos.east();
                    IBlockState iblockstate7 = worldIn.getBlockState(blockpos1.north());
                    IBlockState iblockstate6 = worldIn.getBlockState(blockpos1.south());
                    enumfacing = EnumFacing.SOUTH;
                    EnumFacing enumfacing2;

                    if (iblockstate2.getBlock() == this)
                    {
                        enumfacing2 = (EnumFacing)iblockstate2.getValue(FACING);
                    }
                    else
                    {
                        enumfacing2 = (EnumFacing)iblockstate3.getValue(FACING);
                    }

                    if (enumfacing2 == EnumFacing.NORTH)
                    {
                        enumfacing = EnumFacing.NORTH;
                    }

                    if ((flag || iblockstate7.isFullBlock()) && !flag1 && !iblockstate6.isFullBlock())
                    {
                        enumfacing = EnumFacing.SOUTH;
                    }

                    if ((flag1 || iblockstate6.isFullBlock()) && !flag && !iblockstate7.isFullBlock())
                    {
                        enumfacing = EnumFacing.NORTH;
                    }
                }
            }
            else
            {
                BlockPos blockpos = iblockstate.getBlock() == this ? pos.north() : pos.south();
                IBlockState iblockstate4 = worldIn.getBlockState(blockpos.west());
                IBlockState iblockstate5 = worldIn.getBlockState(blockpos.east());
                enumfacing = EnumFacing.EAST;
                EnumFacing enumfacing1;

                if (iblockstate.getBlock() == this)
                {
                    enumfacing1 = (EnumFacing)iblockstate.getValue(FACING);
                }
                else
                {
                    enumfacing1 = (EnumFacing)iblockstate1.getValue(FACING);
                }

                if (enumfacing1 == EnumFacing.WEST)
                {
                    enumfacing = EnumFacing.WEST;
                }

                if ((iblockstate2.isFullBlock() || iblockstate4.isFullBlock()) && !iblockstate3.isFullBlock() && !iblockstate5.isFullBlock())
                {
                    enumfacing = EnumFacing.EAST;
                }

                if ((iblockstate3.isFullBlock() || iblockstate5.isFullBlock()) && !iblockstate2.isFullBlock() && !iblockstate4.isFullBlock())
                {
                    enumfacing = EnumFacing.WEST;
                }
            }

            state = state.withProperty(FACING, enumfacing);
            worldIn.setBlockState(pos, state, 3);
            return state;
        }
    }

    public IBlockState correctFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        EnumFacing enumfacing = null;

        for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.offset(enumfacing1));

            if (iblockstate.getBlock() == this)
            {
                return state;
            }

            if (iblockstate.isFullBlock())
            {
                if (enumfacing != null)
                {
                    enumfacing = null;
                    break;
                }

                enumfacing = enumfacing1;
            }
        }

        if (enumfacing != null)
        {
            return state.withProperty(FACING, enumfacing.getOpposite());
        }
        else
        {
            EnumFacing enumfacing2 = (EnumFacing)state.getValue(FACING);

            if (worldIn.getBlockState(pos.offset(enumfacing2)).isFullBlock())
            {
                enumfacing2 = enumfacing2.getOpposite();
            }

            if (worldIn.getBlockState(pos.offset(enumfacing2)).isFullBlock())
            {
                enumfacing2 = enumfacing2.rotateY();
            }

            if (worldIn.getBlockState(pos.offset(enumfacing2)).isFullBlock())
            {
                enumfacing2 = enumfacing2.getOpposite();
            }

            return state.withProperty(FACING, enumfacing2);
        }
    }

    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        BlockPos blockpos = pos.west();
        BlockPos blockpos1 = pos.east();
        BlockPos blockpos2 = pos.north();
        BlockPos blockpos3 = pos.south();

        if (worldIn.getBlockState(blockpos).getBlock() == this)
        {
        	return false;
        }

        if (worldIn.getBlockState(blockpos1).getBlock() == this)
        {
        	return false;
        }

        if (worldIn.getBlockState(blockpos2).getBlock() == this)
        {
        	return false;
        }

        if (worldIn.getBlockState(blockpos3).getBlock() == this)
        {
        	return false;
        }

        return true;
    }

    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
    @SuppressWarnings("deprecation")
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);

        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof TileEntityTreasureChest)
        {
        	((TileEntityTreasureChest)tileentity).updateContainingBlockInfo();
        }
    }

    /**
     * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
     */
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof IInventory)
        {
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
            worldIn.updateComparatorOutputLevel(pos, this);
        }

        super.breakBlock(worldIn, pos, state);
    }

	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntityTreasureChest treasurechest = (TileEntityTreasureChest)worldIn.getTileEntity(pos);

        ItemStack guiID = playerIn.getHeldItem(hand);
        
        if (treasurechest.isLocked())
        {
            if (guiID == ItemStack.EMPTY || guiID.getItem() != ItemsAether.dungeon_key)
            {
                return false;
            }

            if (!worldIn.isRemote)
            {
                treasurechest.unlock(guiID.getItemDamage());
            }

            guiID.shrink(1);
        }
        else
        {
            playerIn.openGui(Aether.instance, AetherGuiHandler.treasure_chest, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityTreasureChest();
    }

    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        if (!blockState.canProvidePower())
        {
            return 0;
        }
        else
        {
            int i = 0;
            TileEntity tileentity = blockAccess.getTileEntity(pos);

            if (tileentity instanceof TileEntityTreasureChest)
            {
                i = ((TileEntityTreasureChest)tileentity).numPlayersUsing;
            }

            return MathHelper.clamp(i, 0, 15);
        }
    }

    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return side == EnumFacing.UP ? blockState.getWeakPower(blockAccess, pos, side) : 0;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

	@Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

}