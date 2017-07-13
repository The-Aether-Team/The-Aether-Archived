package com.legacy.aether.common.world.dungeon;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.legacy.aether.common.blocks.BlocksAether;
import com.legacy.aether.common.blocks.dungeon.BlockDungeonBase;
import com.legacy.aether.common.blocks.util.EnumStoneType;
import com.legacy.aether.common.entities.bosses.sun_spirit.EntitySunSpirit;
import com.legacy.aether.common.items.ItemsAether;
import com.legacy.aether.common.world.dungeon.util.AetherDungeon;

public class GoldenDungeon extends AetherDungeon 
{

	public int xoff, yoff, zoff, rad, truey;

	@Override
	public boolean generate(World world, Random random, BlockPos pos) 
	{
		return generate(world, random, pos.getX(), pos.getY(), pos.getZ(), 24);
	}

	public boolean generate(World world, Random random, int x, int y, int z, int r)
	{
		r = (int)Math.floor((double)r * 0.8D);
		int wid = (int)Math.sqrt((r * r) / 2);

		for(int j = 4; j > -5; j--) 
		{
			int a = wid;

			if(j >= 3 || j <= -3) 
			{
				a--;
			}

			if(j == 4 || j == -4)
			{
				a--;
			}

			for(int i = -a; i <= a; i++)
			{
				for(int k = -a; k <= a; k++) 
				{
					if(j == 4)
					{
						setBlock(world, random, new BlockPos(x + i, y + j, z + k), this.getWallBase(), this.getCeilingBase(), 10);
					} 
					else if(j > -4)
					{
						if(i == a || -i == a || k == a || -k == a) 
						{
							setBlock(world, random, new BlockPos(x + i, y + j, z + k), this.getWallBase(), this.getCeilingBase(), 10);
						}
						else 
						{
							world.setBlockState(new BlockPos(x + i, y + j, z + k), Blocks.AIR.getDefaultState());

							if(j == -2 && (i == (a - 1) || -i == (a - 1) || k == (a - 1) || -k == (a - 1)) && (i % 3 == 0 || k % 3 == 0))
							{

							} 
						}
					} 
					else
					{
						setBlock(world, random, new BlockPos(x + i, y + j, z + k), this.getWallBase(), this.getCeilingBase(), 10);

						if((i == (a - 2) || -i == (a - 2)) && (k == (a - 2) || -k == (a - 2))) 
						{
							this.setBlockAndNotifyAdequately(world, new BlockPos(x + i, y + j + 1, z + k), Blocks.NETHERRACK.getDefaultState());
							this.setBlockAndNotifyAdequately(world, new BlockPos(x + i, y + j + 2, z + k), Blocks.FIRE.getDefaultState());
						}
					}
				}
			}
		}

		int direction = random.nextInt(4);

		for(int i = wid; i < wid + 32; i++) 
		{
			boolean flag = false;

			for(int j = -3; j < 2; j++)
			{
				//Entry Passage
				for(int k = -3; k < 4; k++) 
				{
					int a, b;
					if(direction / 2 == 0) 
					{
						a = i;
						b = k;
					}
					else
					{
						a = k;
						b = i;
					}

					if(direction % 2 == 0) 
					{
						a = -a;
						b = -b;
					}

					if(!BlocksAether.isGood(world.getBlockState(new BlockPos.MutableBlockPos().setPos(x + a, y + j, z + b)))) 
					{
						flag = true;

						if(j == -3) 
						{
							setBlock(world, random, new BlockPos(x + a, y + j, z + b), BlocksAether.holystone.getDefaultState(), BlocksAether.mossy_holystone.getDefaultState(), 5);
						}
						else if(j < 1)
						{
							if(i == wid) 
							{
								if(k < 2 && k > -2 && j < 0)
								{
									this.setBlockAndNotifyAdequately(world, new BlockPos(x + a, y + j, z + b), Blocks.AIR.getDefaultState());
								} 
								else 
								{
									setBlock(world, random, new BlockPos(x + a, y + j, z + b), this.getWallBase(), this.getCeilingBase(), 10);
								}
							}
							else
							{
								if(k == 3 || k == -3)
								{
									setBlock(world, random, new BlockPos(x + a, y + j, z + b), BlocksAether.holystone.getDefaultState(), BlocksAether.mossy_holystone.getDefaultState(), 5);
								}
								else
								{
									this.setBlockAndNotifyAdequately(world, new BlockPos(x + a, y + j, z + b), Blocks.AIR.getDefaultState());

									if(j == -1 && (k == 2 || k == -2) && (i - wid - 2) % 3 == 0) 
									{

									}
								}
							}
						} 
						else if(i == wid)
						{
							setBlock(world, random, new BlockPos(x + a, y + j, z + b), this.getWallBase(), this.getCeilingBase(), 5);
						} 
						else
						{
							setBlock(world, random, new BlockPos(x + a, y + j, z + b), BlocksAether.holystone.getDefaultState(), BlocksAether.mossy_holystone.getDefaultState(), 5);
						}
					}

					a = -a;
					b = -b;

					//Treasure Room
					if(i < wid + 6)
					{
						if(j == -3) 
						{
							setBlock(world, random, new BlockPos(x + a, y + j, z + b), this.getWallBase(), this.getCeilingBase(), 10);
						}
						else if(j < 1)
						{
							if(i == wid)
							{
								if(k < 2 && k > -2 && j < 0) 
								{
									setBlock(world, random, new BlockPos(x + a, y + j, z + b), this.getWallBase(), this.getCeilingBase(), 10); //door
								} 
								else 
								{
									setBlock(world, random, new BlockPos(x + a, y + j, z + b), this.getWallBase(), this.getCeilingBase(), 10); //wall
								}
							}
							else if(i == wid + 5)
							{
								setBlock(world, random, new BlockPos(x + a, y + j, z + b), this.getWallBase(), this.getCeilingBase(), 10);
							} 
							else 
							{
								if(i == wid + 4 && k == 0 && j == -2)
								{
									world.setBlockState(new BlockPos(x + a, y + j, z + b), BlocksAether.treasure_chest.getDefaultState());
								} 
								else if(k == 3 || k == -3) 
								{
									setBlock(world, random, new BlockPos(x + a, y + j, z + b), this.getWallBase(), this.getCeilingBase(), 10);
								} 
								else 
								{
									world.setBlockState(new BlockPos(x + a, y + j, z + b), Blocks.AIR.getDefaultState());

									if(j == -1 && (k == 2 || k == -2) && (i - wid - 2) % 3 == 0)
									{

									}
								}
							}
						}
						else 
						{
							setBlock(world, random, new BlockPos(x + a, y + j, z + b), this.getWallBase(), this.getCeilingBase(), 10);
						}
					}
				}
			}

			if(!flag) 
			{
				break;
			}
		}

		EntitySunSpirit boss = new EntitySunSpirit(world, x, y - 1, z, direction);
		if (!world.isRemote)
		{
			world.spawnEntityInWorld(boss);
		}

		return true;
	}

	public static ItemStack getGoldLoot(Random random)
	{
		int item = random.nextInt(9);
		switch(item)
		{
			case 0 :
				return new ItemStack(ItemsAether.iron_bubble);
			case 1 :
				return new ItemStack(ItemsAether.vampire_blade);
			case 2 :
				return new ItemStack(ItemsAether.pig_slayer);
			case 3 :
			{
				if(random.nextBoolean())
				{
					return new ItemStack(ItemsAether.phoenix_helmet);
				}

				if(random.nextBoolean())
				{
					return new ItemStack(ItemsAether.phoenix_leggings);
				}

				if(random.nextBoolean())
				{
					return new ItemStack(ItemsAether.phoenix_chestplate);
				}

				break;
			}
			case 4 :
			{
				if(random.nextBoolean())
				{
					return new ItemStack(ItemsAether.phoenix_boots);
				}

				return new ItemStack(ItemsAether.phoenix_gloves);
			}
			case 5 :
			{
				return new ItemStack(ItemsAether.life_shard);
			}
			case 6 :
			{
				if(random.nextBoolean())
				{
					return new ItemStack(ItemsAether.gravitite_helmet);
				}

				if(random.nextBoolean())
				{
					return new ItemStack(ItemsAether.gravitite_leggings);
				}

				if(random.nextBoolean())
				{
					return new ItemStack(ItemsAether.gravitite_chestplate);
				}

				break;
			}
			case 7 :
			{
				if(random.nextBoolean())
				{
					return new ItemStack(ItemsAether.gravitite_boots);
				}

				return new ItemStack(ItemsAether.gravitite_gloves);
			}

		}
		return new ItemStack(ItemsAether.obsidian_chestplate);
	}

	public IBlockState getCeilingBase() 
	{
		return BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_hellfire);
	}

	public IBlockState getWallBase()
	{
		return BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Hellfire);
	}

	public IBlockState getFloorBase() 
	{
		return Blocks.STONE_STAIRS.getDefaultState();
	}

}