package com.legacy.aether.server.world;

import java.util.Random;

import net.minecraft.block.BlockSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.legacy.aether.server.blocks.BlocksAether;
import com.legacy.aether.server.blocks.dungeon.BlockDungeonBase;
import com.legacy.aether.server.blocks.util.EnumStoneType;
import com.legacy.aether.server.world.biome.decoration.AetherGenGoldenIsland;
import com.legacy.aether.server.world.biome.decoration.AetherGenOakTree;
import com.legacy.aether.server.world.biome.decoration.AetherGenSkyrootTree;
import com.legacy.aether.server.world.dungeon.BronzeDungeon;
import com.legacy.aether.server.world.dungeon.SilverDungeon;
import com.legacy.aether.server.world.dungeon.util.AetherDungeon;

public class DungeonGenerator 
{

    public Random rand;

    public World world;

    public BlockPos pos;

    public Biome biome;

	public static int gumCount;

    protected WorldGenerator golden_island = new AetherGenGoldenIsland();

    protected AetherDungeon dungeon_bronze = new BronzeDungeon(), dungeon_silver = new SilverDungeon();

    public DungeonGenerator(World world, Random random, BlockPos genPos)
    {
    	this.world = world;
    	this.rand = random;
    	this.pos = genPos;
    	this.biome = this.world.getBiome(genPos.add(16, 0, 16));
    }

    public void generate()
    {
    	this.started(true);

    	this.randomizeSeed();

    	if (gumCount < 800)
    	{
    		++gumCount;
    	}
    	else if (this.shouldSpawn(100))
    	{
    		boolean resetCounter = false;
    		
    		resetCounter = this.golden_island.generate(this.world, this.rand, this.pos.add(this.nextInt(16) + 8, this.nextInt(64) + 32, this.nextInt(16) + 8));

    		if (resetCounter)
    		{
    			gumCount = 0;
    		}
    	}

		if (this.shouldSpawn(15))
        {
	        this.dungeon_bronze.generate(this.world, this.rand, this.pos.add(this.nextInt(16), this.nextInt(64) + 32, this.nextInt(16)));
        }

		if(this.shouldSpawn(1300))
		{
			BlockPos newPos = this.pos.add(this.nextInt(16), this.nextInt(32) + 64, this.nextInt(16));
			
			if (this.isValidDungeonSpawn(newPos))
			{
	            this.dungeon_silver.generate(this.world, this.rand, newPos);
			}
		}

    	this.started(false);
    }

    private boolean isValidDungeonSpawn(BlockPos newPos)
    {
    	AxisAlignedBB boundingBox = new AxisAlignedBB(newPos).expand(100D, 100D, 100D);
    	IBlockState state = BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic);

    	if (this.world.getBlockState(new BlockPos(boundingBox.minX, this.world.getHeight(newPos).getY(), boundingBox.maxZ)) == state)
    	{
    		return false;
    	}

    	if (this.world.getBlockState(new BlockPos(boundingBox.minX, this.world.getHeight(newPos).getY(), boundingBox.maxZ)) == state)
    	{
    		return false;
    	}

		return true;
	}

	public int nextInt(int max)
    {
    	return this.rand.nextInt(max);
    }

    public boolean shouldSpawn(int chance)
    {
    	return this.rand.nextInt(chance) == 0;
    }

    public void started(boolean hasStarted)
    {
    	BlockSand.fallInstantly = hasStarted;
    }

    public WorldGenerator getTree()
    {
        return this.shouldSpawn(20) ? new AetherGenOakTree() : new AetherGenSkyrootTree();
    }

    public void randomizeSeed()
    {
        this.rand.setSeed(this.world.getSeed());

        long l1 = (this.rand.nextLong() / 2L) * 2L + 1L;
        long l2 = (this.rand.nextLong() / 2L) * 2L + 1L;

        this.rand.setSeed((long)this.pos.getX() * l1 + (long)this.pos.getZ() * l2 ^ this.world.getSeed());
    }

}