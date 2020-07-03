package com.legacy.aether.blocks;

import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.tile_entities.TileEntitySkyrootBed;
import net.minecraft.block.BlockBed;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockSkyrootBed extends BlockBed
{
    public BlockSkyrootBed()
    {
        super();

        this.setSoundType(SoundType.WOOD);
        this.setHardness(0.2F);

        this.disableStats();
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return state.getValue(PART) == EnumPartType.FOOT ? Items.AIR : ItemsAether.skyroot_bed_item;
    }

    @Override
    public boolean isBed(IBlockState state, IBlockAccess world, BlockPos pos, Entity player)
    {
        return true;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(ItemsAether.skyroot_bed_item);
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        if (state.getValue(PART) == BlockBed.EnumPartType.HEAD)
        {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsAether.skyroot_bed_item));
        }
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
    {
        if (state.getValue(PART) == BlockBed.EnumPartType.HEAD && te instanceof TileEntitySkyrootBed)
        {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsAether.skyroot_bed_item));
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, null, stack);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntitySkyrootBed();
    }
}
