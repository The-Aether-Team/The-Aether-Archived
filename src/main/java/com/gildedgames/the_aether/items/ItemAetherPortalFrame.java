package com.gildedgames.the_aether.items;

import com.gildedgames.the_aether.blocks.BlocksAether;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemAetherPortalFrame extends Item
{
    public ItemAetherPortalFrame()
    {
        this.setMaxStackSize(1);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int facing, float hitX, float hitY, float hitZ)
    {
        ItemStack heldItem = player.getHeldItem();

        int i1 = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (world.isRemote)
        {
            return true;
        }
        else
        {
            if (!player.canPlayerEdit(x, y, z, facing, heldItem))
            {
                return false;
            }
            else
            {
                if (this.createPortalFrame(world, x, y, z, i1))
                {
                    if (!player.capabilities.isCreativeMode)
                    {
                        heldItem.stackSize--;
                    }
                }

                return true;
            }
        }
    }

    private boolean createPortalFrame(World world, int x, int y, int z, int facing)
    {
        final int posX = x;
        final int posY = world.getBlock(x, y, z).isReplaceable(world,x, y, z) ? y + 1 : y + 2;
        final int posZ = z;

        for (int zi = 0; zi < 4; ++zi)
        {
            for (int yi = -1; yi < 4; ++yi)
            {
                final int blockX = posX + (facing == 2 || facing == 0 ? zi - 1 : 0);
                final int blockY = posY + yi;
                final int blockZ = posZ + (facing == 2 || facing == 0 ? 0 : zi - 1);

                if (world.getBlock(blockX, blockY, blockZ) != Blocks.air && !world.getBlock(blockX, blockY, blockZ).isReplaceable(world, blockX, blockY, blockZ))
                {
                    return false;
                }
            }
        }

        final Block frameBlock = Blocks.glowstone;

        final Block portalBlock = BlocksAether.aether_portal;

        for (int zi = 1; zi < 3; ++zi)
        {
            for (int yi = -1; yi < 3; ++yi)
            {
                final int blockX = posX + (facing == 2 || facing == 0 ? zi - 1 : 0);
                final int blockY = posY + yi;
                final int blockZ = posZ + (facing == 2 || facing == 0 ? 0 : zi - 1);
                world.setBlock(blockX, blockY, blockZ, Blocks.air);
            }
        }

        for (int zi = 0; zi < 4; ++zi)
        {
            for (int yi = -1; yi < 4; ++yi)
            {
                final int blockX = posX + (facing == 2 || facing == 0 ? zi - 1 : 0);
                final int blockY = posY + yi;
                final int blockZ = posZ + (facing == 2 || facing == 0 ? 0 : zi - 1);
                world.setBlock(blockX, blockY, blockZ, frameBlock);
            }
        }

        for (int zi = 1; zi < 3; ++zi)
        {
            for (int yi = 0; yi < 3; ++yi)
            {
                final int blockX = posX + (facing == 2 || facing == 0 ? zi - 1 : 0);
                final int blockY = posY + yi;
                final int blockZ = posZ + (facing == 2 || facing == 0 ? 0 : zi - 1);
                world.setBlock(blockX, blockY, blockZ, portalBlock);
            }
        }

        return true;
    }
}
