package com.legacy.aether.entities.ai.aerwhale;

import com.google.common.collect.Lists;
import com.legacy.aether.entities.passive.EntityAerwhale;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class AerwhaleAIUnstuck extends EntityAIBase
{
    private EntityAerwhale aerwhale;

    private World worldObj;

    private boolean isStuckWarning = false;

    private long checkTime = 0L;

    private BlockPos checkPos = BlockPos.ORIGIN;

    public AerwhaleAIUnstuck(EntityAerwhale aerwhale)
    {
        this.aerwhale = aerwhale;
        this.worldObj = aerwhale.world;
        this.setMutexBits(4);
    }

    @Override
    public boolean shouldExecute()
    {
        return !this.aerwhale.isDead && isColliding();
    }

    @Override
    public void updateTask()
    {
        BlockPos posUp = this.aerwhale.getPosition().up(3);
        BlockPos posDown = this.aerwhale.getPosition().down();
        BlockPos posNorth = this.aerwhale.getPosition().north(2).up();
        BlockPos posSouth = this.aerwhale.getPosition().south(2).up();
        BlockPos posEast = this.aerwhale.getPosition().east(2).up();
        BlockPos posWest = this.aerwhale.getPosition().west(2).up();

        if (this.checkRegion(posUp.add(-1, 0, -1), posUp.add(1, 0, 1)))
        {
            this.aerwhale.motionY += 0.002;
        }
        if (this.checkRegion(posDown.add(-1, 0, -1), posDown.add(1, 0, 1)))
        {
            this.aerwhale.motionY -= 0.002;
        }
        if (this.checkRegion(posEast.add(0, -1, -1), posEast.add(0, 1, 1)))
        {
            this.aerwhale.motionX += 0.002;
            this.aerwhale.motionY += 0.002;
        }
        if (this.checkRegion(posWest.add(0, -1, -1), posWest.add(0, 1, 1)))
        {
            this.aerwhale.motionX -= 0.002;
            this.aerwhale.motionY += 0.002;
        }
        if (this.checkRegion(posSouth.add(-1, -1, 0), posSouth.add(1, 1, 0)))
        {
            this.aerwhale.motionZ += 0.002;
            this.aerwhale.motionY += 0.002;
        }
        if (this.checkRegion(posNorth.add(-1, -1, 0), posNorth.add(1, 1, 0)))
        {
            this.aerwhale.motionZ -= 0.002;
            this.aerwhale.motionY += 0.002;
        }
    }

    public boolean checkRegion(BlockPos pos1, BlockPos pos2)
    {
        ArrayList<Block> blockList = Lists.newArrayListWithCapacity(9);

        for (BlockPos position : BlockPos.getAllInBox(pos1, pos2))
        {
            blockList.add(this.worldObj.getBlockState(position).getBlock());
        }

        Set<Block> blockSet = new HashSet<>(blockList);

        if (blockSet.size() == 1)
        {
            return blockList.get(1) == Blocks.AIR;
        }

        return false;
    }

    public boolean isColliding()
    {
        long curtime = System.currentTimeMillis();

        if (curtime > this.checkTime + 1000L)
        {
            double diffx = this.aerwhale.posX - this.checkPos.getX();
            double diffy = this.aerwhale.posY - this.checkPos.getY();
            double diffz = this.aerwhale.posZ - this.checkPos.getZ();

            double distanceTravelled = Math.sqrt((diffx * diffx) + (diffy * diffy) + (diffz * diffz));

            if (distanceTravelled < 3D)
            {
                if (!this.isStuckWarning)
                {
                    this.isStuckWarning = true;
                }
                else
                {
                    return true;
                }
            }

            this.checkPos = this.aerwhale.getPosition();
            this.checkTime = curtime;
        }

        return false;
    }
}
