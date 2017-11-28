package com.legacy.aether.world.dungeon.util;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class AetherDungeon extends WorldGenerator
{

	protected final Random random = new Random();

    public int chance;

    public IBlockState airState = Blocks.AIR.getDefaultState(), blockState, extraBlockState;

    public boolean replaceAir, replaceSolid;

    public AetherDungeon()
    {
    	super();
    }

    public void setBlocks(IBlockState blockState)
    {
        this.blockState = blockState;
        this.extraBlockState = null;
        this.chance = 0;
    }

    public void setBlocks(IBlockState blockState, IBlockState extraBlockState, int chances)
    {
        this.blockState = blockState;
        this.extraBlockState = extraBlockState;
        this.chance = chances;

        if (this.chance < 1)
        {
            this.chance = 1;
        }
    }

    public void addLineX(World world, Random rand, PositionData pos, int radius)
    {
    	for (int lineX = pos.getX(); lineX < pos.getX() + radius; lineX++)
        {
    		BlockPos newPos = new BlockPos(lineX, pos.getY(), pos.getZ());
        	Block block = world.getBlockState(newPos).getBlock();

            if ((this.replaceAir || block != Blocks.AIR) && (this.replaceSolid || block == Blocks.AIR))
            {
            	if (this.chance == 0)
            	{
            		this.setBlockAndNotifyAdequately(world, newPos, this.blockState);

            		return;
            	}

                if (rand.nextInt(this.chance) == 0)
                {
            		this.setBlockAndNotifyAdequately(world, newPos, this.extraBlockState);
                }
                else
                {
            		this.setBlockAndNotifyAdequately(world, newPos, this.blockState);
                }
            }
        }
    }

    public void addLineY(World world, Random rand, PositionData pos, int radius)
    {
        for (int lineY = pos.getY(); lineY < pos.getY() + radius; lineY++)
        {
    		BlockPos newPos = new BlockPos(pos.getX(), lineY, pos.getZ());
        	Block block = world.getBlockState(newPos).getBlock();

            if ((this.replaceAir || block != Blocks.AIR) && (this.replaceSolid || block == Blocks.AIR))
            {
            	if (this.chance == 0)
            	{
            		this.setBlockAndNotifyAdequately(world, newPos, this.blockState);

            		return;
            	}

                if (rand.nextInt(this.chance) == 0)
                {
            		this.setBlockAndNotifyAdequately(world, newPos, this.extraBlockState);
                }
                else
                {
            		this.setBlockAndNotifyAdequately(world, newPos, this.blockState);
                }
            }
        }
    }

    public void addLineZ(World world, Random rand, PositionData pos, int radius)
    {
    	for (int lineZ = pos.getZ(); lineZ < pos.getZ() + radius; lineZ++)
        {
    		BlockPos newPos = new BlockPos(pos.getX(), pos.getY(), lineZ);
        	Block block = world.getBlockState(newPos).getBlock();

            if ((this.replaceAir || block != Blocks.AIR) && (this.replaceSolid || block == Blocks.AIR))
            {
            	if (this.chance == 0)
            	{
            		this.setBlockAndNotifyAdequately(world, newPos, this.blockState);

            		return;
            	}

                if (rand.nextInt(this.chance) == 0)
                {
            		this.setBlockAndNotifyAdequately(world, newPos, this.extraBlockState);
                }
                else
                {
            		this.setBlockAndNotifyAdequately(world, newPos, this.blockState);
                }
            }
        }
    }

    public void addPlaneX(World world, Random rand, PositionData pos, PositionData radius)
    {
    	for (int lineY = pos.getY(); lineY < pos.getY() + radius.getY(); lineY++)
        {
            for (int lineZ = pos.getZ(); lineZ < pos.getZ() + radius.getZ(); lineZ++)
            {
        		BlockPos newPos = new BlockPos(pos.getX(), lineY, lineZ);
            	Block block = world.getBlockState(newPos).getBlock();

                if ((this.replaceAir || block != Blocks.AIR) && (this.replaceSolid || block == Blocks.AIR))
                {
                	if (this.chance == 0)
                	{
                		this.setBlockAndNotifyAdequately(world, newPos, this.blockState);

                		return;
                	}

                    if (rand.nextInt(this.chance) == 0)
                    {
                		this.setBlockAndNotifyAdequately(world, newPos, this.extraBlockState);
                    }
                    else
                    {
                		this.setBlockAndNotifyAdequately(world, newPos, this.blockState);
                    }
                }
            }
        }
    }

    public void addPlaneY(World world, Random rand, PositionData pos, PositionData radius)//, int radiusX, int radiusZ)
    {
    	for (int lineX = pos.getX(); lineX < pos.getX() + radius.getX(); lineX++)
        {
            for (int lineZ = pos.getZ(); lineZ < pos.getZ() + radius.getZ(); lineZ++)
            {
        		BlockPos newPos = new BlockPos(lineX, pos.getY(), lineZ);
            	Block block = world.getBlockState(newPos).getBlock();

                if ((this.replaceAir || block != Blocks.AIR) && (this.replaceSolid || block == Blocks.AIR))
                {
                	if (this.chance == 0)
                	{
                		this.setBlockAndNotifyAdequately(world, newPos, this.blockState);

                		return;
                	}

                    if (rand.nextInt(this.chance) == 0)
                    {
                		this.setBlockAndNotifyAdequately(world, newPos, this.extraBlockState);
                    }
                    else
                    {
                		this.setBlockAndNotifyAdequately(world, newPos, this.blockState);
                    }
                }
            }
        }
    }

    public void addPlaneZ(World world, Random rand, PositionData pos, PositionData radius)//, int radiusX, int radiusY)
    {
    	for (int lineX = pos.getX(); lineX < pos.getX() + radius.getX(); lineX++)
        {
            for (int lineY = pos.getY(); lineY < pos.getY() + radius.getY(); lineY++)
            {
        		BlockPos newPos = new BlockPos(lineX, lineY, pos.getZ());
            	Block block = world.getBlockState(newPos).getBlock();

                if ((this.replaceAir || block != Blocks.AIR) && (this.replaceSolid || block == Blocks.AIR))
                {
                	if (this.chance == 0)
                	{
                		this.setBlockAndNotifyAdequately(world, newPos, this.blockState);

                		return;
                	}

                    if (rand.nextInt(this.chance) == 0)
                    {
                		this.setBlockAndNotifyAdequately(world, newPos, this.extraBlockState);
                    }
                    else
                    {
                		this.setBlockAndNotifyAdequately(world, newPos, this.blockState);
                    }
                }
            }
        }
    }

    public void addHollowBox(World world, Random rand, PositionData pos, PositionData radius)
    {
    	IBlockState temp1 = this.blockState;
    	IBlockState temp2 = this.extraBlockState;

        this.setBlocks(this.airState, this.airState, this.chance);
        this.addSolidBox(world, rand, pos, radius);
        this.setBlocks(temp1, temp2, this.chance);
        this.addPlaneY(world, rand, pos, radius);
        this.addPlaneY(world, rand, new PositionData(pos.getX(), pos.getY() + (radius.getY() - 1), pos.getZ()), radius);
        this.addPlaneX(world, rand, pos, radius);
        this.addPlaneX(world, rand, new PositionData(pos.getX() + (radius.getX() - 1), pos.getY(), pos.getZ()), radius);
        this.addPlaneZ(world, rand, pos, radius);
        this.addPlaneZ(world, rand, new PositionData(pos.getX(), pos.getY(), pos.getZ() + (radius.getZ() - 1)), radius);
    }

    public void addSquareTube(World world, Random rand, PositionData pos, PositionData radius, int angel)
    {
    	IBlockState temp1 = this.blockState;
    	IBlockState temp2 = this.extraBlockState;

        this.setBlocks(this.airState, this.airState, this.chance);
        this.addSolidBox(world, rand, pos, radius);
        this.setBlocks(temp1, temp2, this.chance);

        if (angel == 0 || angel == 2)
        {
            this.addPlaneY(world, rand, pos, radius);
            this.addPlaneY(world, rand, new PositionData(pos.getX(), pos.getY() + (radius.getY() - 1), pos.getZ()), radius);
        }

        if (angel == 1 || angel == 2)
        {
            this.addPlaneX(world, rand, pos, radius);
            this.addPlaneX(world, rand, new PositionData(pos.getX() + (radius.getX() - 1), pos.getY(), pos.getZ()), radius);
        }

        if (angel == 0 || angel == 1)
        {
            this.addPlaneZ(world, rand, pos, radius);
            this.addPlaneZ(world, rand, new PositionData(pos.getX(), pos.getY(), pos.getZ() + (radius.getZ() - 1)), radius);
        }
    }

    public void addSolidBox(World world, Random rand, PositionData pos, PositionData radius)
    {
    	for (int lineX = pos.getX(); lineX < pos.getX() + radius.getX(); lineX++)
        {
            for (int lineY = pos.getY(); lineY < pos.getY() + radius.getY(); lineY++)
            {
                for (int lineZ = pos.getZ(); lineZ < pos.getZ() + radius.getZ(); lineZ++)
                {
            		BlockPos newPos = new BlockPos(lineX, lineY, lineZ);
                	Block block = world.getBlockState(newPos).getBlock();

                    if ((this.replaceAir || block != Blocks.AIR) && (this.replaceSolid || block == Blocks.AIR))
                    {
                    	if (this.chance == 0)
                    	{
                    		this.setBlockAndNotifyAdequately(world, newPos, this.blockState);

                    		return;
                    	}

                        if (rand.nextInt(this.chance) == 0)
                        {
                    		this.setBlockAndNotifyAdequately(world, newPos, this.extraBlockState);
                        }
                        else
                        {
                    		this.setBlockAndNotifyAdequately(world, newPos, this.blockState);
                        }
                    }
                }
            }
        }
    }

    public boolean isBoxSolid(World world, PositionData pos, PositionData radius)
    {
        boolean flag = true;

        for (int lineX = pos.getX(); lineX < pos.getX() + radius.getX(); lineX++)
        {
            for (int lineY = pos.getY(); lineY < pos.getY() + radius.getY(); lineY++)
            {
                for (int lineZ = pos.getZ(); lineZ < pos.getZ() + radius.getZ(); lineZ++)
                {
                    if (world.getBlockState(new BlockPos.MutableBlockPos().setPos(lineX, lineY, lineZ)).getBlock() == Blocks.AIR)
                    {
                    	flag = false;
                    }
                }
            }
        }

        return flag;
    }

    public boolean isBoxEmpty(World world, PositionData pos, PositionData radius)
    {
        boolean flag = true;

        for (int lineX = pos.getX(); lineX < pos.getX() + radius.getX(); lineX++)
        {
            for (int lineY = pos.getY(); lineY < pos.getY() + radius.getY(); lineY++)
            {
                for (int lineZ = pos.getZ(); lineZ < pos.getZ() + radius.getZ(); lineZ++)
                {
                    if (world.getBlockState(new BlockPos.MutableBlockPos().setPos(lineX, lineY, lineZ)).getBlock() != Blocks.AIR)
                    {
                    	flag = false;
                    }
                }
            }
        }

        return flag;
    }

	protected void setBlock(World world, Random random, BlockPos pos, IBlockState state, IBlockState extraState, int chance)
	{
		if(random.nextInt(chance) == 0)
		{
			this.setBlockAndNotifyAdequately(world, pos, extraState);
		}
		else 
		{
			this.setBlockAndNotifyAdequately(world, pos, state);
		}
	}

}