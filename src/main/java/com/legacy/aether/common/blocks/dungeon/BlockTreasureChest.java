package com.legacy.aether.common.blocks.dungeon;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import com.legacy.aether.common.Aether;
import com.legacy.aether.common.blocks.BlocksAether;
import com.legacy.aether.common.items.ItemsAether;
import com.legacy.aether.common.networking.AetherGuiHandler;
import com.legacy.aether.common.tile_entities.TileEntityTreasureChest;

public class BlockTreasureChest extends BlockChest
{

    public BlockTreasureChest()
    {
        super(BlockChest.Type.TRAP);
        this.setHardness(-1.0F);
        this.setSoundType(SoundType.STONE);
    }

	@Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        TileEntityTreasureChest treasurechest = (TileEntityTreasureChest)worldIn.getTileEntity(pos);

        ItemStack guiID = heldItem;
        
        if (treasurechest.isLocked())
        {
            if (guiID == null || guiID != null && guiID.getItem() != ItemsAether.dungeon_key)
            {
                return false;
            }

            if (!worldIn.isRemote)
            {
                treasurechest.unlock(guiID.getItemDamage());
            }

            --guiID.stackSize;
        }

        int var12 = AetherGuiHandler.treasure_chest;
        playerIn.openGui(Aether.instance, var12, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        return !this.isNotBlock(world, pos, this) && !this.isNotBlock(world, pos, BlocksAether.chest_mimic) && !this.isNotBlock(world, pos, Blocks.CHEST) && !this.isNotBlock(world, pos, Blocks.TRAPPED_CHEST);
    }

    public boolean isNotBlock(World world, BlockPos pos, Block block)
    {
        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
        {
            if (world.getBlockState(pos.offset(enumfacing)).getBlock() == block)
            {
                return true;
            }
        }

        return false;
    }

    public EnumBlockRenderType getRenderType(IBlockState state)
    {
    	return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
	{
		return new TileEntityTreasureChest();
    }

	@Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
    	return null;
    }

	@Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

}