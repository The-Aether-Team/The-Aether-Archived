package com.legacy.aether.common.world.biome;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.legacy.aether.common.AetherConfig;
import com.legacy.aether.common.blocks.BlocksAether;
import com.legacy.aether.common.blocks.util.EnumCloudType;
import com.legacy.aether.common.world.biome.decoration.AetherGenClouds;
import com.legacy.aether.common.world.biome.decoration.AetherGenFloatingIsland;
import com.legacy.aether.common.world.biome.decoration.AetherGenFoilage;
import com.legacy.aether.common.world.biome.decoration.AetherGenHolidayTree;
import com.legacy.aether.common.world.biome.decoration.AetherGenLiquids;
import com.legacy.aether.common.world.biome.decoration.AetherGenMinable;
import com.legacy.aether.common.world.biome.decoration.AetherGenOakTree;
import com.legacy.aether.common.world.biome.decoration.AetherGenQuicksoil;
import com.legacy.aether.common.world.biome.decoration.AetherGenSkyrootTree;

public class AetherBiomeDecorator extends BiomeDecorator 
{

	public World world;

	public Random rand;

	public Biome aetherBiome;

	public AetherGenClouds aerclouds = new AetherGenClouds();

	public AetherGenFoilage foilage = new AetherGenFoilage();

	public AetherGenMinable ores = new AetherGenMinable();

	public AetherGenSkyrootTree skyroot_tree = new AetherGenSkyrootTree(false);

	public AetherGenQuicksoil quicksoil_patches = new AetherGenQuicksoil();

    public AetherGenFloatingIsland crystal_island = new AetherGenFloatingIsland();

	public AetherGenLiquids liquid_overhang = new AetherGenLiquids();

	public AetherGenHolidayTree holiday_tree = new AetherGenHolidayTree();

	public AetherBiomeDecorator()
	{
		super();
	}

	@Override
    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos)
    {
        if (this.decorating)
        {
            throw new RuntimeException("Already decorating");
        }
        else
        {
            this.chunkPos = pos;
            this.world = worldIn;
            this.rand = random;
            this.aetherBiome = biome;
            this.genDecorations(biome, worldIn, random);
            this.decorating = false;
        }
    }

	@Override
    protected void genDecorations(Biome biomeGenBaseIn, World worldIn, Random random)
    {
		if (this.shouldSpawn(37))
    	{
    		this.crystal_island.generate(this.world, this.rand, this.chunkPos.add(8, this.nextInt(64) + 32, 8));
    	}

    	if (this.shouldSpawn(3))
    	{
        	this.spawnOre(BlocksAether.aether_dirt.getDefaultState(), 32, 20, 128);
    	}

    	this.generateFoilage(BlocksAether.white_flower.getDefaultState());
    	this.generateFoilage(BlocksAether.purple_flower.getDefaultState());

    	this.spawnOre(BlocksAether.icestone.getDefaultState(), 16, 10, 128);
    	this.spawnOre(BlocksAether.ambrosium_ore.getDefaultState(), 16, 15, 128);
    	this.spawnOre(BlocksAether.zanite_ore.getDefaultState(), 8, 15, 64);
    	this.spawnOre(BlocksAether.gravitite_ore.getDefaultState(), 6, 8, 40);

    	this.generateFoilage(BlocksAether.berry_bush.getDefaultState());

		if (this.shouldSpawn(2))
		{
			this.getTree().generate(this.world, this.rand, this.world.getHeight(this.chunkPos.add(this.nextInt(16) + 8, 0, this.nextInt(16) + 8)));
		}

		if (this.shouldSpawn(1))
		{
			this.skyroot_tree.generate(this.world, this.rand, this.world.getHeight(this.chunkPos.add(this.nextInt(8) + 8, 0, this.nextInt(8) + 8)));
		}

		if (AetherConfig.shouldLoadHolidayContent())
		{
			if (this.shouldSpawn(15))
			{
				this.holiday_tree.generate(this.world, this.rand, this.world.getHeight(this.chunkPos.add(this.nextInt(16) + 8, 0, this.nextInt(16) + 8)));
			}
		}

    	this.generateClouds(EnumCloudType.Golden, 4, false, 50, this.nextInt(64) + 96);
    	this.generateClouds(EnumCloudType.Blue, 8, false, 26, this.nextInt(64) + 32);
    	this.generateClouds(EnumCloudType.Cold, 16, false, 14, this.nextInt(64) + 64);

		MutableBlockPos mutedPos = new MutableBlockPos();

		if (this.shouldSpawn(10))
		{
			for (int x = this.chunkPos.getX(); x < this.chunkPos.getX() + 16; x++)
			{
				for (int z = this.chunkPos.getZ(); z < this.chunkPos.getZ() + 16; z++)
				{
					for (int n = 0; n < 48; n++)
					{
						mutedPos.setPos(x, n, z);
						if (this.world.getBlockState(mutedPos).getBlock() == Blocks.AIR && this.world.getBlockState(mutedPos.setPos(x, n + 1, z)).getBlock() == BlocksAether.aether_grass && this.world.getBlockState(mutedPos.setPos(x, n + 2, z)).getBlock() == Blocks.AIR)
						{
							new AetherGenQuicksoil().generate(this.world, this.rand, mutedPos);
							mutedPos.setPos(x, n + 128, z);
						}
					}
				}
			}
		}
    }

	public int nextInt(int max)
    {
    	return this.rand.nextInt(max);
    }

    public boolean shouldSpawn(int chance)
    {
    	return this.nextInt(chance) == 0;
    }

    public WorldGenerator getTree()
    {
        return this.shouldSpawn(20) ? new AetherGenOakTree() : new AetherGenSkyrootTree(true);
    }

	public void generateFoilage(IBlockState block)
	{
		this.foilage.setPlantBlock(block);

        for(int n = 0; n < 2; n++)
        {
        	foilage.generate(this.world, this.rand, this.chunkPos.add(this.nextInt(16) + 8, this.nextInt(128), this.nextInt(16) + 8));
        }
	}

    public void spawnOre(IBlockState state, int size, int chance, int y)
    {
		this.ores.setSize(size);
		this.ores.setBlock(state);

    	for (int chances = 0; chances < chance; chances++)
    	{
        	this.ores.generate(this.world, this.rand, this.chunkPos.add(this.nextInt(16), this.nextInt(y), this.nextInt(16)));
    	}
    }

    public void generateClouds(EnumCloudType type, int size, boolean flat, int chance, int y)
    {
    	if (shouldSpawn(chance))
    	{
    		this.aerclouds.setCloudType(type);
    		this.aerclouds.setCloudAmmount(size);
    		this.aerclouds.setFlat(flat);

        	this.aerclouds.generate(this.world, this.rand, this.chunkPos.add(8, y, 8));
    	}
    }

}