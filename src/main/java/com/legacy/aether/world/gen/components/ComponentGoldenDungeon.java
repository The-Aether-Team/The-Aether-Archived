package com.legacy.aether.world.gen.components;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.dungeon.BlockDungeonBase;
import com.legacy.aether.blocks.dungeon.BlockTreasureChest;
import com.legacy.aether.blocks.util.EnumStoneType;
import com.legacy.aether.entities.bosses.sun_spirit.EntitySunSpirit;
import com.legacy.aether.world.gen.AetherStructure;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class ComponentGoldenDungeon extends AetherStructure
{

	private int direction;

	public ComponentGoldenDungeon() 
	{
		
	}

	public ComponentGoldenDungeon(int chunkX, int chunkZ, int direction) 
	{
        this.setCoordBaseMode(EnumFacing.NORTH);

        this.direction = direction;
        this.boundingBox = new StructureBoundingBox(chunkX, 80, chunkZ, chunkX + 100, 220, chunkZ + 100);
	}

	@Override
	public boolean generate() 
	{
		this.replaceAir = true;
		this.replaceSolid = true;
		this.setStructureOffset(60, 0, 60);

		int r = 24;
		r = (int)Math.floor((double)r * 0.8D);
		int wid = (int)Math.sqrt((r * r) / 2);

		this.setBlocks(BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Hellfire), BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_hellfire), 10);

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
						this.setBlockWithOffset(i, j, k);
					} 
					else if(j > -4)
					{
						if(i == a || -i == a || k == a || -k == a) 
						{
							this.setBlockWithOffset(i, j, k);
						}
						else
						{
							this.setBlockWithOffset(i, j, k, Blocks.AIR.getDefaultState());

							if(j == -2 && (i == (a - 1) || -i == (a - 1) || k == (a - 1) || -k == (a - 1)) && (i % 3 == 0 || k % 3 == 0))
							{

							} 
						}
					} 
					else
					{
						this.setBlockWithOffset(i, j, k);

						if((i == (a - 2) || -i == (a - 2)) && (k == (a - 2) || -k == (a - 2))) 
						{
							this.setBlockWithOffset(i, j + 1, k, Blocks.NETHERRACK.getDefaultState());
							this.setBlockWithOffset(i, j + 2, k, Blocks.FIRE.getDefaultState());
						}
					}
				}
			}
		}

		for(int i = wid; i < wid + 32; i++) 
		{
			for(int j = -3; j < 2; j++)
			{
				for(int k = -3; k < 4; k++) 
				{
					int a, b;
					if(this.direction / 2 == 0) 
					{
						a = i;
						b = k;
					}
					else
					{
						a = k;
						b = i;
					}

					if(this.direction % 2 == 0) 
					{
						a = -a;
						b = -b;
					}

					if(!BlocksAether.isGood(this.getBlockStateWithOffset(a, j, b))) 
					{
						this.setBlocks(BlocksAether.holystone.getDefaultState(), BlocksAether.mossy_holystone.getDefaultState(), 5);

						if(j == -3) 
						{
							this.setBlockWithOffset(a, j, b);
						}
						else if(j < 1)
						{
							if(i == wid) 
							{
								if(k < 2 && k > -2 && j < 0)
								{
									this.setBlockWithOffset(a, j, b, Blocks.AIR.getDefaultState());
								} 
								else 
								{
									this.setBlocks(BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Hellfire), BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_hellfire), 10);

									this.setBlockWithOffset(a, j, b);
								}
							}
							else
							{
								if(k == 3 || k == -3)
								{
									this.setBlockWithOffset(a, j, b);
								}
								else
								{
									this.setBlockWithOffset(a, j, b, Blocks.AIR.getDefaultState());

									if(j == -1 && (k == 2 || k == -2) && (i - wid - 2) % 3 == 0) 
									{

									}
								}
							}
						} 
						else if(i == wid)
						{
							this.setBlocks(BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Hellfire), BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_hellfire), 10);
							this.setBlockWithOffset(a, j, b);
						} 
						else
						{
							this.setBlocks(BlocksAether.holystone.getDefaultState(), BlocksAether.mossy_holystone.getDefaultState(), 5);
							this.setBlockWithOffset(a, j, b);
						}
					}

					a = -a;
					b = -b;

					this.setBlocks(BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Hellfire), BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_hellfire), 10);

					if(i < wid + 6)
					{
						if(j == -3) 
						{
							this.setBlockWithOffset(a, j, b);
						}
						else if(j < 1)
						{
							if(i == wid)
							{
								if(k < 2 && k > -2 && j < 0) 
								{
									this.setBlockWithOffset(a, j, b);
								} 
								else 
								{
									this.setBlockWithOffset(a, j, b);
								}
							}
							else if(i == wid + 5)
							{
								this.setBlockWithOffset(a, j, b);
							} 
							else 
							{
								if(i == wid + 4 && k == 0 && j == -2)
								{
									this.setBlockWithOffset(a, j, b, ((BlockTreasureChest)BlocksAether.treasure_chest).correctFacing(this.worldObj, new BlockPos(this.getActualX(a, b), this.getActualY(j), this.getActualZ(a, b)), BlocksAether.treasure_chest.getDefaultState()));
								} 
								else if(k == 3 || k == -3) 
								{
									this.setBlockWithOffset(a, j, b);
								} 
								else 
								{
									this.setBlockWithOffset(a, j, b, Blocks.AIR.getDefaultState());

									if(j == -1 && (k == 2 || k == -2) && (i - wid - 2) % 3 == 0)
									{

									}
								}
							}
						}
						else 
						{
							this.setBlockWithOffset(a, j, b);
						}
					}
				}
			}

		}

		EntitySunSpirit boss = new EntitySunSpirit(this.worldObj, this.getActualX(0, 0), this.getActualY(-1), this.getActualZ(0, 0), this.direction);

		this.spawnEntity(boss, 0, -1, 0);

		return true;
	}

	/*public static ItemStack getGoldLoot(Random random)
	{
		int item = random.nextInt(10);

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
			
			case 8 :
				return new ItemStack(ItemsAether.chain_gloves);

		}

		return new ItemStack(ItemsAether.obsidian_chestplate);
	}*/

}