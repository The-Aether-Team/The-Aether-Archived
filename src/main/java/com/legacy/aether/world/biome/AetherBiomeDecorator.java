package com.legacy.aether.world.biome;

import java.util.Random;

import com.legacy.aether.AetherConfig;
import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.world.biome.decoration.AetherGenFloatingIsland;
import com.legacy.aether.world.biome.decoration.AetherGenFoilage;
import com.legacy.aether.world.biome.decoration.AetherGenHolidayTree;
import com.legacy.aether.world.biome.decoration.AetherGenLakes;
import com.legacy.aether.world.biome.decoration.AetherGenLiquids;
import com.legacy.aether.world.biome.decoration.AetherGenMinable;
import com.legacy.aether.world.biome.decoration.AetherGenOakTree;
import com.legacy.aether.world.biome.decoration.AetherGenQuicksoil;
import com.legacy.aether.world.biome.decoration.AetherGenSkyrootTree;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.feature.WorldGenerator;

public class AetherBiomeDecorator extends BiomeDecorator 
{

	public World world;

	public Random rand;

	public Biome aetherBiome;

	public AetherGenFoilage foilage = new AetherGenFoilage();

	public AetherGenMinable ores = new AetherGenMinable();

	public AetherGenSkyrootTree skyroot_tree = new AetherGenSkyrootTree(false);

	public AetherGenQuicksoil quicksoil_patches = new AetherGenQuicksoil();

    public AetherGenFloatingIsland crystal_island = new AetherGenFloatingIsland();

	public AetherGenLiquids liquid_overhang = new AetherGenLiquids();

	public AetherGenHolidayTree holiday_tree = new AetherGenHolidayTree();

	public AetherGenLakes aether_lakes = new AetherGenLakes();

    protected static final WorldGenDoublePlant double_grass = new WorldGenDoublePlant();

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
    	this.spawnOre(BlocksAether.gravitite_ore.getDefaultState(), 6, 8, 32);

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
		
		if (AetherConfig.tallgrassEnabled())
		{
			for (int i3 = 0; i3 < 10; ++i3)
	        {
	            int j7 = random.nextInt(16) + 8;
	            int i11 = random.nextInt(16) + 8;
	            int k14 = worldIn.getHeight(this.chunkPos.add(j7, 0, i11)).getY() * 2;

	            if (k14 > 0)
	            {
	            	int l17 = random.nextInt(k14);
	                biomeGenBaseIn.getRandomWorldGenForGrass(random).generate(worldIn, random, this.chunkPos.add(j7, l17, i11));
	            }
	        }
	        
	        double_grass.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);

	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, this.chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS))
	        for (int i = 0; i < 7; ++i)
	        {
	            int j = rand.nextInt(16) + 8;
	            int k = rand.nextInt(16) + 8;
	            int l = rand.nextInt(worldIn.getHeight(this.chunkPos.add(j, 0, k)).getY() + 32);
	            double_grass.generate(worldIn, rand, this.chunkPos.add(j, l, k));
	        }
		}

		if (this.shouldSpawn(10))
		{
            (new WorldGenLakes(Blocks.WATER)).generate(this.world, this.rand, this.chunkPos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(256), this.rand.nextInt(16) + 8));
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
        return this.shouldSpawn(13) ? new AetherGenOakTree() : new AetherGenSkyrootTree(true);
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

}