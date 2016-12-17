package com.legacy.aether.server.world;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

import com.legacy.aether.server.blocks.BlocksAether;
import com.legacy.aether.server.world.util.AetherPortalPosition;
import com.legacy.aether.server.world.util.Direction;

@SuppressWarnings("deprecation")
public class TeleporterAether extends Teleporter
{

	private boolean portalSpawn;

    private final WorldServer worldServerInstance;

    private final Random random;

    private final Long2ObjectMap<AetherPortalPosition> destinationCoordinateCache = new Long2ObjectOpenHashMap<AetherPortalPosition>(4096);

    public TeleporterAether(boolean portalSpawn, WorldServer world)
    {
    	super(world);
    	this.portalSpawn = portalSpawn;
        this.worldServerInstance = world;
        this.random = new Random(world.getSeed());
    }

    @Override
    public void placeInPortal(Entity entity, float rotation)
    {
    	if (!this.portalSpawn)
    	{
    		return;
    	}

        if (!this.placeInExistingPortal(entity, rotation))
        {
            this.makePortal(entity);
            this.placeInExistingPortal(entity, rotation);
        }
    }

	@Override
    public boolean placeInExistingPortal(Entity entity, float rotation)
    {
        double d0 = -1.0D;
        int j = MathHelper.floor_double(entity.posX);
        int k = MathHelper.floor_double(entity.posZ);
        BlockPos pos = BlockPos.ORIGIN;
        long l = ChunkPos.asLong(j, k);
        boolean flag = true;

        if (this.destinationCoordinateCache.containsKey(l))
        {
            AetherPortalPosition portalposition = (AetherPortalPosition)this.destinationCoordinateCache.get(l);
            d0 = 0.0D;
            pos = portalposition;
            flag = false;
        }
        else
        {
        	BlockPos blockpos3 = new BlockPos(entity);

             for (int i1 = -128; i1 <= 128; ++i1)
             {
                 BlockPos blockpos2;

                 for (int j1 = -128; j1 <= 128; ++j1)
                 {
                     for (BlockPos blockpos1 = blockpos3.add(i1, this.worldServerInstance.getActualHeight() - 1 - blockpos3.getY(), j1); blockpos1.getY() >= 0; blockpos1 = blockpos2)
                     {
                         blockpos2 = blockpos1.down();

                         if (this.worldServerInstance.getBlockState(blockpos1).getBlock() == BlocksAether.aether_portal)
                         {
                             while (this.worldServerInstance.getBlockState(blockpos2 = blockpos1.down()).getBlock() == BlocksAether.aether_portal)
                             {
                                 blockpos1 = blockpos2;
                             }

                             double d1 = blockpos1.distanceSq(blockpos3);

                             if (d0 < 0.0D || d1 < d0)
                             {
                                 d0 = d1;
                                 pos = blockpos1;
                             }
                         }
                     }
                 }
             }
        }

        if (d0 >= 0.0D)
        {
            if (flag)
            {
                this.destinationCoordinateCache.put(l, new AetherPortalPosition(pos, this.worldServerInstance.getTotalWorldTime()));
            }

            double d11 = (double)pos.getX() + 0.5D;
            double d6 = (double)pos.getY() + 0.5D;
            double d7 = (double)pos.getZ() + 0.5D;
            int i4 = -1;

            if (this.worldServerInstance.getBlockState(pos.west()).getBlock() == BlocksAether.aether_portal)
            {
                i4 = 2;
            }

            if (this.worldServerInstance.getBlockState(pos.east()).getBlock() == BlocksAether.aether_portal)
            {
                i4 = 0;
            }

            if (this.worldServerInstance.getBlockState(pos.north()).getBlock() == BlocksAether.aether_portal)
            {
                i4 = 3;
            }

            if (this.worldServerInstance.getBlockState(pos.south()).getBlock() == BlocksAether.aether_portal)
            {
                i4 = 1;
            }

            int j2 = Direction.getMovementDirection(entity.prevPosX - entity.posX, entity.prevPosZ - entity.posZ);

            if (i4 > -1)
            {
                int k2 = Direction.rotateLeft[i4];
                int l2 = Direction.offsetX[i4];
                int i3 = Direction.offsetZ[i4];
                int j3 = Direction.offsetX[k2];
                int k3 = Direction.offsetZ[k2];
                boolean flag1 = !this.worldServerInstance.isAirBlock(new BlockPos(pos.getX() + l2 + j3, pos.getY(), pos.getZ() + i3 + k3)) || !this.worldServerInstance.isAirBlock(new BlockPos(pos.getX() + l2 + j3, pos.getY() + 1, pos.getZ() + i3 + k3));
                boolean flag2 = !this.worldServerInstance.isAirBlock(new BlockPos(pos.getX() + l2, pos.getY(), pos.getZ() + i3)) || !this.worldServerInstance.isAirBlock(new BlockPos(pos.getX() + l2, pos.getY() + 1, pos.getZ() + i3));

                if (flag1 && flag2)
                {
                    i4 = Direction.rotateOpposite[i4];
                    k2 = Direction.rotateOpposite[k2];
                    l2 = Direction.offsetX[i4];
                    i3 = Direction.offsetZ[i4];
                    j3 = Direction.offsetX[k2];
                    k3 = Direction.offsetZ[k2];
                    int l3 = pos.getX() - j3;
                    d11 -= (double)j3;
                    int k1 = pos.getZ() - k3;
                    d7 -= (double)k3;
                    flag1 = !this.worldServerInstance.isAirBlock(new BlockPos(l3 + l2 + j3, pos.getY(), k1 + i3 + k3)) || !this.worldServerInstance.isAirBlock(new BlockPos(l3 + l2 + j3, pos.getY() + 1, k1 + i3 + k3));
                    flag2 = !this.worldServerInstance.isAirBlock(new BlockPos(l3 + l2, pos.getY(), k1 + i3)) || !this.worldServerInstance.isAirBlock(new BlockPos(l3 + l2, pos.getY() + 1, k1 + i3));
                }

                float f1 = 0.5F;
                float f2 = 0.5F;

                if (!flag1 && flag2)
                {
                    f1 = 1.0F;
                }
                else if (flag1 && !flag2)
                {
                    f1 = 0.0F;
                }
                else if (flag1 && flag2)
                {
                    f2 = 0.0F;
                }

                d11 += (double)((float)j3 * f1 + f2 * (float)l2);
                d7 += (double)((float)k3 * f1 + f2 * (float)i3);
                float f3 = 0.0F;
                float f4 = 0.0F;
                float f5 = 0.0F;
                float f6 = 0.0F;

                if (i4 == j2)
                {
                    f3 = 1.0F;
                    f4 = 1.0F;
                }
                else if (i4 == Direction.rotateOpposite[j2])
                {
                    f3 = -1.0F;
                    f4 = -1.0F;
                }
                else if (i4 == Direction.rotateRight[j2])
                {
                    f5 = 1.0F;
                    f6 = -1.0F;
                }
                else
                {
                    f5 = -1.0F;
                    f6 = 1.0F;
                }

                double d9 = entity.motionX;
                double d10 = entity.motionZ;
                entity.motionX = d9 * (double)f3 + d10 * (double)f6;
                entity.motionZ = d9 * (double)f5 + d10 * (double)f4;
                entity.rotationYaw = rotation - (float)(j2 * 90) + (float)(i4 * 90);
                entity.setLocationAndAngles(d11, d6, d7, entity.rotationYaw, entity.rotationPitch);
            }

            return true;
        }
        else
        {
        	return false;
        }
    }

    @Override
    public boolean makePortal(Entity entity)
    {
    	int b0 = 100;
        double d0 = -1.0D;
        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_double(entity.posY);
        int k = MathHelper.floor_double(entity.posZ);
        int l = i;
        int i1 = j;
        int j1 = k;
        int k1 = 0;
        int l1 = this.random.nextInt(4);
        int i2;
        double d1;
        int k2;
        double d2;
        int i3;
        int j3;
        int k3;
        int l3;
        int i4;
        int j4;
        int k4;
        int l4;
        int i5;
        double d3;
        double d4;

        for (i2 = i - b0; i2 <= i + b0; ++i2)
        {
            d1 = (double)i2 + 0.5D - entity.posX;

            for (k2 = k - b0; k2 <= k + b0; ++k2)
            {
                d2 = (double)k2 + 0.5D - entity.posZ;
                label274:

                for (i3 = this.worldServerInstance.getActualHeight() - 1; i3 >= 0; --i3)
                {
                    if (this.worldServerInstance.isAirBlock(new BlockPos(i2, i3, k2)))
                    {
                        while (i3 > 0 && this.worldServerInstance.isAirBlock(new BlockPos(i2, i3 - 1, k2)))
                        {
                            --i3;
                        }

                        for (j3 = l1; j3 < l1 + 4; ++j3)
                        {
                            k3 = j3 % 2;
                            l3 = 1 - k3;

                            if (j3 % 4 >= 2)
                            {
                                k3 = -k3;
                                l3 = -l3;
                            }

                            for (i4 = 0; i4 < 3; ++i4)
                            {
                                for (j4 = 0; j4 < 4; ++j4)
                                {
                                    for (k4 = -1; k4 < 4; ++k4)
                                    {
                                        l4 = i2 + (j4 - 1) * k3 + i4 * l3;
                                        i5 = i3 + k4;
                                        int j5 = k2 + (j4 - 1) * l3 - i4 * k3;

                                        BlockPos pos2 = new BlockPos(l4, i5, j5);
                                        if (k4 < 0 && !this.worldServerInstance.getBlockState(pos2).getMaterial().isSolid() || this.worldServerInstance.getBlockState(pos2).getBlock() == BlocksAether.aercloud || k4 >= 0 && !this.worldServerInstance.isAirBlock(pos2))
                                        {
                                            continue label274;
                                        }
                                    }
                                }
                            }

                            d3 = (double)i3 + 0.5D - entity.posY;
                            d4 = d1 * d1 + d3 * d3 + d2 * d2;

                            if (d0 < 0.0D || d4 < d0)
                            {
                                d0 = d4;
                                l = i2;
                                i1 = i3;
                                j1 = k2;
                                k1 = j3 % 4;
                            }
                        }
                    }
                }
            }
        }

        if (d0 < 0.0D)
        {
            for (i2 = i - b0; i2 <= i + b0; ++i2)
            {
                d1 = (double)i2 + 0.5D - entity.posX;

                for (k2 = k - b0; k2 <= k + b0; ++k2)
                {
                    d2 = (double)k2 + 0.5D - entity.posZ;
                    label222:

                    for (i3 = this.worldServerInstance.getActualHeight() - 1; i3 >= 0; --i3)
                    {
                        if (this.worldServerInstance.isAirBlock(new BlockPos(i2, i3, k2)))
                        {
                            while (i3 > 0 && this.worldServerInstance.isAirBlock(new BlockPos(i2, i3 - 1, k2)))
                            {
                                --i3;
                            }

                            for (j3 = l1; j3 < l1 + 2; ++j3)
                            {
                                k3 = j3 % 2;
                                l3 = 1 - k3;

                                for (i4 = 0; i4 < 4; ++i4)
                                {
                                    for (j4 = -1; j4 < 4; ++j4)
                                    {
                                        k4 = i2 + (i4 - 1) * k3;
                                        l4 = i3 + j4;
                                        i5 = k2 + (i4 - 1) * l3;

                                        IBlockState stateInstance = this.worldServerInstance.getBlockState(new BlockPos(k4, l4, i5));
                                        Block blockInstance = this.worldServerInstance.getBlockState(new BlockPos(k4, l4, i5)).getBlock();
                                        if (j4 < 0 && !stateInstance.getMaterial().isSolid() || blockInstance == BlocksAether.aercloud || j4 >= 0 && !this.worldServerInstance.isAirBlock(new BlockPos(k4, l4, i5)))
                                        {
                                            continue label222;
                                        }
                                    }
                                }

                                d3 = (double)i3 + 0.5D - entity.posY;
                                d4 = d1 * d1 + d3 * d3 + d2 * d2;

                                if (d0 < 0.0D || d4 < d0)
                                {
                                    d0 = d4;
                                    l = i2;
                                    i1 = i3;
                                    j1 = k2;
                                    k1 = j3 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        int k5 = l;
        int j2 = i1;
        k2 = j1;
        int l5 = k1 % 2;
        int l2 = 1 - l5;

        if (k1 % 4 >= 2)
        {
            l5 = -l5;
            l2 = -l2;
        }

        boolean flag;

        if (d0 < 0.0D)
        {
            i1 = MathHelper.clamp_int(i1, 70, this.worldServerInstance.getActualHeight() - 10);
            j2 = i1;

            for (i3 = -1; i3 <= 1; ++i3)
            {
                for (j3 = 1; j3 < 3; ++j3)
                {
                    for (k3 = -1; k3 < 3; ++k3)
                    {
                        l3 = k5 + (j3 - 1) * l5 + i3 * l2;
                        i4 = j2 + k3;
                        j4 = k2 + (j3 - 1) * l2 - i3 * l5;
                        flag = k3 < 0;
                        this.worldServerInstance.setBlockState(new BlockPos(l3, i4, j4), flag ? Blocks.GLOWSTONE.getDefaultState() : Blocks.AIR.getDefaultState());
                    }
                }
            }
        }

        for (i3 = 0; i3 < 4; ++i3)
        {
            for (j3 = 0; j3 < 4; ++j3)
            {
                for (k3 = -1; k3 < 4; ++k3)
                {
                    l3 = k5 + (j3 - 1) * l5;
                    i4 = j2 + k3;
                    j4 = k2 + (j3 - 1) * l2;
                    flag = j3 == 0 || j3 == 3 || k3 == -1 || k3 == 3;
                    this.worldServerInstance.setBlockState(new BlockPos(l3, i4, j4), flag ? Blocks.GLOWSTONE.getDefaultState() : BlocksAether.aether_portal.getDefaultState().withProperty(BlockPortal.AXIS, l5 != 0 ? EnumFacing.Axis.X : EnumFacing.Axis.Z), 2);
                }
            }

        }

        return true;
    }

}