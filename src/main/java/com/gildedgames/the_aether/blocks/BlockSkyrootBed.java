package com.gildedgames.the_aether.blocks;

import com.gildedgames.the_aether.AetherConfig;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.player.PlayerAether;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockBed;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.Random;

public class BlockSkyrootBed extends BlockBed
{
    public static final int[][] field_149981_a = new int[][] {{0, 1}, { -1, 0}, {0, -1}, {1, 0}};
    @SideOnly(Side.CLIENT)
    private IIcon[] field_149980_b;
    @SideOnly(Side.CLIENT)
    private IIcon[] field_149982_M;
    @SideOnly(Side.CLIENT)
    private IIcon[] field_149983_N;

    public BlockSkyrootBed()
    {
        super();
        this.func_149978_e();
        this.disableStats();
    }

    @Override
    public boolean onBlockActivated(World p_149727_1_, int p_149727_2_, int p_149727_3_, int p_149727_4_, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (p_149727_1_.isRemote)
        {
            return true;
        }
        else
        {
            int i1 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);

            if (!isBlockHeadOfBed(i1))
            {
                int j1 = getDirection(i1);
                p_149727_2_ += field_149981_a[j1][0];
                p_149727_4_ += field_149981_a[j1][1];

                if (p_149727_1_.getBlock(p_149727_2_, p_149727_3_, p_149727_4_) != this)
                {
                    return true;
                }

                i1 = p_149727_1_.getBlockMetadata(p_149727_2_, p_149727_3_, p_149727_4_);
            }

            if (p_149727_5_.dimension == AetherConfig.getAetherDimensionID() || p_149727_5_.dimension == 0)
            {
                if (func_149976_c(i1))
                {
                    EntityPlayer entityplayer1 = null;
                    Iterator iterator = p_149727_1_.playerEntities.iterator();

                    while (iterator.hasNext())
                    {
                        EntityPlayer entityplayer2 = (EntityPlayer)iterator.next();

                        if (entityplayer2.isPlayerSleeping())
                        {
                            ChunkCoordinates chunkcoordinates = entityplayer2.playerLocation;

                            if (chunkcoordinates.posX == p_149727_2_ && chunkcoordinates.posY == p_149727_3_ && chunkcoordinates.posZ == p_149727_4_)
                            {
                                entityplayer1 = entityplayer2;
                            }
                        }
                    }

                    if (entityplayer1 != null)
                    {
                        p_149727_5_.addChatComponentMessage(new ChatComponentTranslation("tile.bed.occupied", new Object[0]));
                        return true;
                    }

                    func_149979_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, false);
                }

                EntityPlayer.EnumStatus enumstatus = p_149727_5_.sleepInBedAt(p_149727_2_, p_149727_3_, p_149727_4_);

                if (enumstatus == EntityPlayer.EnumStatus.OK)
                {
                    func_149979_a(p_149727_1_, p_149727_2_, p_149727_3_, p_149727_4_, true);
                    return true;
                }
                else
                {
                    if (enumstatus == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW)
                    {
                        p_149727_5_.addChatComponentMessage(new ChatComponentTranslation("tile.bed.noSleep", new Object[0]));

                        if (p_149727_5_.dimension == AetherConfig.getAetherDimensionID())
                        {
                            p_149727_5_.addChatMessage(new ChatComponentTranslation("gui.skyroot_bed.respawn_point"));
                            p_149727_5_.setSpawnChunk(new ChunkCoordinates(p_149727_2_, p_149727_3_, p_149727_4_), false, AetherConfig.getAetherDimensionID());
                            PlayerAether.get(p_149727_5_).setBedLocation(new ChunkCoordinates(p_149727_2_, p_149727_3_, p_149727_4_));
                        }
                    }
                    else if (enumstatus == EntityPlayer.EnumStatus.NOT_SAFE)
                    {
                        p_149727_5_.addChatComponentMessage(new ChatComponentTranslation("tile.bed.notSafe", new Object[0]));
                    }

                    return true;
                }
            }
            else
            {
                double d2 = (double)p_149727_2_ + 0.5D;
                double d0 = (double)p_149727_3_ + 0.5D;
                double d1 = (double)p_149727_4_ + 0.5D;
                p_149727_1_.setBlockToAir(p_149727_2_, p_149727_3_, p_149727_4_);
                int k1 = getDirection(i1);
                p_149727_2_ += field_149981_a[k1][0];
                p_149727_4_ += field_149981_a[k1][1];

                if (p_149727_1_.getBlock(p_149727_2_, p_149727_3_, p_149727_4_) == this)
                {
                    p_149727_1_.setBlockToAir(p_149727_2_, p_149727_3_, p_149727_4_);
                    d2 = (d2 + (double)p_149727_2_ + 0.5D) / 2.0D;
                    d0 = (d0 + (double)p_149727_3_ + 0.5D) / 2.0D;
                    d1 = (d1 + (double)p_149727_4_ + 0.5D) / 2.0D;
                }

                p_149727_1_.newExplosion((Entity)null, (double)((float)p_149727_2_ + 0.5F), (double)((float)p_149727_3_ + 0.5F), (double)((float)p_149727_4_ + 0.5F), 5.0F, true, true);
                return true;
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        if (p_149691_1_ == 0)
        {
            return BlocksAether.skyroot_planks.getBlockTextureFromSide(p_149691_1_);
        }
        else
        {
            int k = getDirection(p_149691_2_);
            int l = Direction.bedDirection[k][p_149691_1_];
            int i1 = isBlockHeadOfBed(p_149691_2_) ? 1 : 0;
            return (i1 != 1 || l != 2) && (i1 != 0 || l != 3) ? (l != 5 && l != 4 ? this.field_149983_N[i1] : this.field_149982_M[i1]) : this.field_149980_b[i1];
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.field_149983_N = new IIcon[] {p_149651_1_.registerIcon(this.getTextureName() + "_feet_top"), p_149651_1_.registerIcon(this.getTextureName() + "_head_top")};
        this.field_149980_b = new IIcon[] {p_149651_1_.registerIcon(this.getTextureName() + "_feet_end"), p_149651_1_.registerIcon(this.getTextureName() + "_head_end")};
        this.field_149982_M = new IIcon[] {p_149651_1_.registerIcon(this.getTextureName() + "_feet_side"), p_149651_1_.registerIcon(this.getTextureName() + "_head_side")};
    }

    private void func_149978_e()
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
    {
        return isBlockHeadOfBed(p_149650_1_) ? Item.getItemById(0) : ItemsAether.skyroot_bed_item;
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return ItemsAether.skyroot_bed_item;
    }

    @Override
    public boolean isBed(IBlockAccess world, int x, int y, int z, EntityLivingBase player)
    {
        return true;
    }

    @Override
    public boolean isBedFoot(IBlockAccess world, int x, int y, int z)
    {
        return BlockSkyrootBed.isBlockHeadOfBed(world.getBlockMetadata(x,  y, z));
    }
}
