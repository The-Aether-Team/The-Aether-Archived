package com.legacy.aether.blocks;

import com.legacy.aether.AetherConfig;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (worldIn.isRemote)
        {
            return true;
        }
        else
        {
            if (state.getValue(PART) != BlockBed.EnumPartType.HEAD)
            {
                pos = pos.offset(state.getValue(FACING));
                state = worldIn.getBlockState(pos);

                if (state.getBlock() != this)
                {
                    return true;
                }
            }

            net.minecraft.world.WorldProvider.WorldSleepResult sleepResult = worldIn.provider.canSleepAt(playerIn, pos);
            if (sleepResult != net.minecraft.world.WorldProvider.WorldSleepResult.BED_EXPLODES)
            {
                if (sleepResult == net.minecraft.world.WorldProvider.WorldSleepResult.DENY) return true;
                if (state.getValue(OCCUPIED))
                {
                    EntityPlayer entityplayer = this.getPlayerInBed(worldIn, pos);

                    if (entityplayer != null)
                    {
                        playerIn.sendStatusMessage(new TextComponentTranslation("tile.bed.occupied"), true);
                        return true;
                    }

                    state = state.withProperty(OCCUPIED, Boolean.FALSE);
                    worldIn.setBlockState(pos, state, 4);
                }

                EntityPlayer.SleepResult entityplayer$sleepresult = playerIn.trySleep(pos);

                if (entityplayer$sleepresult == EntityPlayer.SleepResult.OK)
                {
                    state = state.withProperty(OCCUPIED, Boolean.TRUE);
                    worldIn.setBlockState(pos, state, 4);
                }
                else
                {
                    if (entityplayer$sleepresult == EntityPlayer.SleepResult.NOT_POSSIBLE_NOW)
                    {
                        playerIn.sendStatusMessage(new TextComponentTranslation("tile.bed.noSleep"), true);

                        if (playerIn.dimension ==  AetherConfig.dimension.aether_dimension_id)
                        {
                            playerIn.sendMessage(new TextComponentTranslation("gui.skyroot_bed.respawn_point"));
                            playerIn.setSpawnChunk(pos, false, AetherConfig.dimension.aether_dimension_id);
                        }
                    }
                    else if (entityplayer$sleepresult == EntityPlayer.SleepResult.NOT_SAFE)
                    {
                        playerIn.sendStatusMessage(new TextComponentTranslation("tile.bed.notSafe"), true);
                    }
                    else if (entityplayer$sleepresult == EntityPlayer.SleepResult.TOO_FAR_AWAY)
                    {
                        playerIn.sendStatusMessage(new TextComponentTranslation("tile.bed.tooFarAway"), true);
                    }

                }
            }
            else
            {
                worldIn.setBlockToAir(pos);
                BlockPos blockpos = pos.offset(state.getValue(FACING).getOpposite());

                if (worldIn.getBlockState(blockpos).getBlock() == this)
                {
                    worldIn.setBlockToAir(blockpos);
                }

                worldIn.newExplosion(null, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, 5.0F, true, true);
            }
            return true;
        }
    }

    @Nullable
    private EntityPlayer getPlayerInBed(World worldIn, BlockPos pos)
    {
        for (EntityPlayer entityplayer : worldIn.playerEntities)
        {
            if (entityplayer.isPlayerSleeping() && entityplayer.bedLocation.equals(pos))
            {
                return entityplayer;
            }
        }

        return null;
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
