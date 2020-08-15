package com.gildedgames.the_aether.items;

import com.gildedgames.the_aether.blocks.BlocksAether;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemAetherPortalFrame extends Item
{
    public ItemAetherPortalFrame()
    {
        this.setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack heldItem = player.getHeldItem(hand);

        int i = MathHelper.floor((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        EnumFacing enumfacing = EnumFacing.byHorizontalIndex(i);

        if (worldIn.isRemote)
        {
            return EnumActionResult.SUCCESS;
        }
        else
        {
            pos = pos.offset(facing);

            if (!player.canPlayerEdit(pos, enumfacing, heldItem))
            {
                return EnumActionResult.FAIL;
            }
            else
            {
                if (this.createPortalFrame(worldIn, pos.getX(), pos.getY(), pos.getZ(), enumfacing))
                {
                    if (!player.capabilities.isCreativeMode)
                    {
                        heldItem.shrink(1);
                    }
                }

                return EnumActionResult.SUCCESS;
            }
        }
    }

    private boolean createPortalFrame(World world, int x, int y, int z, EnumFacing facing)
    {
        final int posX = x;
        final int posY = world.getBlockState(new BlockPos(x, y - 1, z)).getBlock().isReplaceable(world, new BlockPos(x, y - 1, z)) ? y : y + 1;
        final int posZ = z;

        for (int zi = 0; zi < 4; ++zi)
        {
            for (int yi = -1; yi < 4; ++yi)
            {
                final int blockX = posX + (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH ? zi - 1 : 0);
                final int blockY = posY + yi;
                final int blockZ = posZ + (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH ? 0 : zi - 1);

                if (world.getBlockState(new BlockPos(blockX, blockY, blockZ)).getBlock() != Blocks.AIR && !world.getBlockState(new BlockPos(blockX, blockY, blockZ)).getBlock().isReplaceable(world, new BlockPos(blockX, blockY, blockZ)))
                {
                    return false;
                }
            }
        }

        final IBlockState frameBlock = Blocks.GLOWSTONE.getDefaultState();

        final IBlockState portalBlock = BlocksAether.aether_portal.getDefaultState().withProperty(BlockPortal.AXIS, facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH ? EnumFacing.Axis.X : EnumFacing.Axis.Z);

        for (int zi = 1; zi < 3; ++zi)
        {
            for (int yi = -1; yi < 3; ++yi)
            {
                final int blockX = posX + (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH ? zi - 1 : 0);
                final int blockY = posY + yi;
                final int blockZ = posZ + (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH ? 0 : zi - 1);
                world.setBlockState(new BlockPos(blockX, blockY, blockZ), Blocks.AIR.getDefaultState());
            }
        }

        for (int zi = 0; zi < 4; ++zi)
        {
            for (int yi = -1; yi < 4; ++yi)
            {
                final int blockX = posX + (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH ? zi - 1 : 0);
                final int blockY = posY + yi;
                final int blockZ = posZ + (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH ? 0 : zi - 1);
                world.setBlockState(new BlockPos(blockX, blockY, blockZ), frameBlock, 2);
            }
        }

        for (int zi = 1; zi < 3; ++zi)
        {
            for (int yi = 0; yi < 3; ++yi)
            {
                final int blockX = posX + (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH ? zi - 1 : 0);
                final int blockY = posY + yi;
                final int blockZ = posZ + (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH ? 0 : zi - 1);
                world.setBlockState(new BlockPos(blockX, blockY, blockZ), portalBlock, 2);
            }
        }

        return true;
    }
}
