package com.legacy.aether.server.world.dungeon;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.legacy.aether.server.items.ItemsAether;

public class PlatinumDungeon extends WorldGenerator
{

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position)
	{
		return true;
	}

	public static ItemStack getPlatinumLoot(Random random)
	{
		int item = random.nextInt(8);

		switch (item)
		{
		case 1:
			return new ItemStack(ItemsAether.cloud_staff);
		case 2:
			return new ItemStack(ItemsAether.life_shard);
		case 3:
			return new ItemStack(ItemsAether.jeb_hammer);
		case 4:
			return new ItemStack(ItemsAether.phoenix_pickaxe);
		case 5:
			return new ItemStack(ItemsAether.phoenix_axe);
		case 6:
			return new ItemStack(ItemsAether.phoenix_shovel);
		case 7:
			return new ItemStack(ItemsAether.aerwhale_egg);
		}

		return new ItemStack(ItemsAether.chain_gloves);
	}

}