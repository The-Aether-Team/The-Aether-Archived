package com.legacy.aether.items;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;
import com.legacy.aether.tile_entities.TileEntitySkyrootBed;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemSkyrootBed extends Item
{
    public ItemSkyrootBed()
    {
        this.maxStackSize = 1;
        this.setCreativeTab(AetherCreativeTabs.blocks);
    }

    @Override
    public EnumActionResult onItemUse(final EntityPlayer player, final World world, BlockPos pos, final EnumHand hand, final EnumFacing facing,
                                      final float hitX, final float hitY, final float hitZ)
    {
        if (world.isRemote)
        {
            return EnumActionResult.SUCCESS;
        }
        else if (facing != EnumFacing.UP)
        {
            return EnumActionResult.FAIL;
        }
        else
        {
            IBlockState iblockstate = world.getBlockState(pos);
            Block block = iblockstate.getBlock();
            boolean flag = block.isReplaceable(world, pos);

            if (!flag)
            {
                pos = pos.up();
            }

            int i = MathHelper.floor((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            EnumFacing enumfacing = EnumFacing.byHorizontalIndex(i);
            BlockPos blockpos = pos.offset(enumfacing);
            ItemStack itemstack = player.getHeldItem(hand);

            if (player.canPlayerEdit(pos, facing, itemstack) && player.canPlayerEdit(blockpos, facing, itemstack))
            {
                IBlockState iblockstate1 = world.getBlockState(blockpos);
                boolean flag1 = iblockstate1.getBlock().isReplaceable(world, blockpos);
                boolean flag2 = flag || world.isAirBlock(pos);
                boolean flag3 = flag1 || world.isAirBlock(blockpos);

                if (flag2 && flag3 && world.getBlockState(pos.down()).isTopSolid() && world.getBlockState(blockpos.down()).isTopSolid())
                {
                    IBlockState iblockstate2 = BlocksAether.skyroot_bed.getDefaultState().withProperty(BlockBed.OCCUPIED, Boolean.FALSE).withProperty(BlockBed.FACING, enumfacing).withProperty(BlockBed.PART, BlockBed.EnumPartType.FOOT);
                    world.setBlockState(pos, iblockstate2, 10);
                    world.setBlockState(blockpos, iblockstate2.withProperty(BlockBed.PART, BlockBed.EnumPartType.HEAD), 10);
                    SoundType soundtype = iblockstate2.getBlock().getSoundType(iblockstate2, world, pos, player);
                    world.playSound(null, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    TileEntity tileentity = world.getTileEntity(blockpos);

                    if (tileentity instanceof TileEntitySkyrootBed)
                    {
                        ((TileEntitySkyrootBed)tileentity).setItemValues(itemstack);
                    }

                    TileEntity tileentity1 = world.getTileEntity(pos);

                    if (tileentity1 instanceof TileEntitySkyrootBed)
                    {
                        ((TileEntitySkyrootBed)tileentity1).setItemValues(itemstack);
                    }

                    world.notifyNeighborsRespectDebug(pos, block, false);
                    world.notifyNeighborsRespectDebug(blockpos, iblockstate1.getBlock(), false);

                    if (player instanceof EntityPlayerMP)
                    {
                        CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, itemstack);
                    }

                    itemstack.shrink(1);
                    return EnumActionResult.SUCCESS;
                }
                else
                {
                    return EnumActionResult.FAIL;
                }
            }
            else
            {
                return EnumActionResult.FAIL;
            }
        }
    }

}
