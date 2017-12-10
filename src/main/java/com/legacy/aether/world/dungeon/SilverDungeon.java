package com.legacy.aether.world.dungeon;

import java.util.Random;

import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.dungeon.BlockDungeonBase;
import com.legacy.aether.blocks.util.EnumCloudType;
import com.legacy.aether.blocks.util.EnumStoneType;
import com.legacy.aether.entities.AetherEntities;
import com.legacy.aether.entities.bosses.valkyrie_queen.EntityValkyrieQueen;
import com.legacy.aether.items.ItemMoaEgg;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.world.biome.decoration.AetherGenClouds;
import com.legacy.aether.world.dungeon.util.AetherDungeon;
import com.legacy.aether.world.dungeon.util.PositionData;

public class SilverDungeon extends AetherDungeon
{

	private int[][][] rooms = new int[3][3][3];

	private AetherGenClouds clouds = new AetherGenClouds();

    public SilverDungeon()
    {
    	super();

    	this.clouds.setCloudType(EnumCloudType.Cold);
    	this.clouds.setCloudAmmount(10);
    	this.clouds.setFlat(false);
    }

    public void generateDoorX(World world, int x, int y, int z, int yF, int zF)
    {
		for(int yFinal = y; yFinal < y + yF; yFinal++)
		{
			for(int zFinal = z; zFinal < z + zF; zFinal++)
			{
				this.setBlockAndNotifyAdequately(world, new BlockPos(x, yFinal, zFinal), Blocks.AIR.getDefaultState());
			}
		}
    }

    public void generateDoorZ(World world, int z, int x, int y, int xF, int yF)
    {
		for(int xFinal = x; xFinal < x + xF; xFinal++)
		{
			for(int yFinal = y; yFinal < y + yF; yFinal++)
			{
				this.setBlockAndNotifyAdequately(world, new BlockPos(xFinal, yFinal, z), Blocks.AIR.getDefaultState());
			}
		}
    }

    public boolean generate(World world, Random random, BlockPos pos)
    {
		replaceAir = true;

		if(!isBoxEmpty(world, new PositionData(pos.getX(), pos.getY(), pos.getZ()), new PositionData(55, 20, 30)))
		{
			return false;
		}
		
		if(world.getBlockState(new BlockPos.MutableBlockPos().setPos(0, -5, 0)).getBlock() == Blocks.AIR || world.getBlockState(new BlockPos.MutableBlockPos().setPos(55, -5, 0)).getBlock() == Blocks.AIR || world.getBlockState(new BlockPos.MutableBlockPos().setPos(0, -5, 30)).getBlock() == Blocks.AIR || world.getBlockState(new BlockPos.MutableBlockPos().setPos(55, -5, 30)).getBlock() == Blocks.AIR)
		{
			for(int n = 0; n < 100; n++)
			{
				int x = pos.getX() - 11 + random.nextInt(77);
				int y = pos.getY() - 7;
				int z = pos.getZ() - 10 + random.nextInt(50);
				this.clouds.generate(world, random, new BlockPos(x, y, z));
			}
		}

		replaceSolid = true;
		setBlocks(BlocksAether.holystone.getDefaultState(), BlocksAether.mossy_holystone.getDefaultState(), 30);
		addSolidBox(world, random, new PositionData(pos.getX(), pos.getY() - 5, pos.getZ()), new PositionData(55, 5, 30));

		for(int x = pos.getX(); x < pos.getX() + 55; x += 4)
		{
			addColumn(world, random, new PositionData(x, pos.getY(), pos.getZ()), 14);
			addColumn(world, random, new PositionData(x, pos.getY(), pos.getZ() + 27), 14);
		}

		for(int z = pos.getZ(); z < pos.getZ() + 12; z += 4)
		{
			addColumn(world, random, new PositionData(pos.getX(), pos.getY(), z), 14);
			addColumn(world, random, new PositionData(pos.getX() + 52, pos.getY(), z), 14);
		}

		for(int z = pos.getZ() + 19; z < pos.getZ() + 30; z += 4)
		{
			addColumn(world, random, new PositionData(pos.getX(), pos.getY(), z), 14);
			addColumn(world, random, new PositionData(pos.getX() + 52, pos.getY(), z), 14);
		}

		//Add walls
		IBlockState block2 = BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic);
		setBlocks(block2, BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_angelic), 20);
		addHollowBox(world, random, new PositionData(pos.getX() + 4, pos.getY() - 1, pos.getZ() + 4), new PositionData(47, 16, 22));
		addPlaneX(world, random, new PositionData(pos.getX() + 11, pos.getY(), pos.getZ() + 5), new PositionData(0, 15, 20));
		addPlaneX(world, random, new PositionData(pos.getX() + 18, pos.getY(), pos.getZ() + 5), new PositionData(0, 15, 20));
		addPlaneX(world, random, new PositionData(pos.getX() + 25, pos.getY(), pos.getZ() + 5), new PositionData(0, 15, 20));
		addPlaneZ(world, random, new PositionData(pos.getX() + 5, pos.getY(), pos.getZ() + 11), new PositionData(20, 15, 0));
		addPlaneZ(world, random, new PositionData(pos.getX() + 5, pos.getY(), pos.getZ() + 18), new PositionData(20, 15, 0));

		IBlockState block1 = BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic);
		setBlocks(block1, BlocksAether.dungeon_trap.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_angelic), 30);
		addPlaneY(world, random, new PositionData(pos.getX() + 5, pos.getY() + 4, pos.getZ() + 5), new PositionData(20, 0, 20));
		addPlaneY(world, random, new PositionData(pos.getX() + 5, pos.getY() + 9, pos.getZ() + 5), new PositionData(20, 0, 20));
		
		for(int y = pos.getY(); y < pos.getY() + 2; y++)
		{
			for(int z = pos.getZ() + 14; z < pos.getZ() + 16; z++)
			{
				this.setBlockAndNotifyAdequately(world, new BlockPos(pos.getX() + 4, y, z), Blocks.AIR.getDefaultState());
			}
		}

		setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
		addSolidBox(world, random, new PositionData(pos.getX(), pos.getY() - 4, pos.getZ() + 14), new PositionData(1, 4, 2));
		addSolidBox(world, random, new PositionData(pos.getX() + 1, pos.getY() - 3, pos.getZ() + 14), new PositionData(1, 3, 2));
		addSolidBox(world, random, new PositionData(pos.getX() + 2, pos.getY() - 2, pos.getZ() + 14), new PositionData(1, 2, 2));
		addSolidBox(world, random, new PositionData(pos.getX() + 3, pos.getY() - 1, pos.getZ() + 14), new PositionData(1, 1, 2));

		IBlockState block = BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic);
		setBlocks(block, BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_angelic), 15);

		for(int y = 0; y < 7; y++)
		{
			addPlaneY(world, random, new PositionData(pos.getX() - 1, pos.getY() + 15 + y, pos.getZ() - 1 + 2 * y), new PositionData(57, 0, 32 - 4 * y));
		}

		int firstStaircaseZ = 0;
		int secondStaircaseZ = 0;
		int finalStaircaseZ = 0;

		int row = random.nextInt(3);
		addStaircase(world, random, new PositionData(pos.getX() + 19, pos.getY(), pos.getZ() + 5 + row * 7), 10);
		rooms[2][0][row] = 2;
		rooms[2][1][row] = 2;
		rooms[2][2][row] = 1;
		finalStaircaseZ = row;

		int x = pos.getX() + 25;
		int y;
		int z;

		for(y = pos.getY(); y < pos.getY() + 2; y++)
		{
			for(z = pos.getZ() + 7 + 7 * row; z < pos.getZ() + 9 + 7 * row; z++)
			{
				this.setBlockAndNotifyAdequately(world, new BlockPos(x, y, z), Blocks.AIR.getDefaultState());
			}
		}

		row = random.nextInt(3);
		addStaircase(world, random, new PositionData(pos.getX() + 12, pos.getY(), pos.getZ() + 5 + row * 7), 5);
		rooms[1][0][row] = 1;
		rooms[1][1][row] = 1;
		firstStaircaseZ = row;

		row = random.nextInt(3);
		addStaircase(world, random, new PositionData(pos.getX() + 5, pos.getY() + 5, pos.getZ() + 5 + row * 7), 5);
		rooms[0][1][row] = 1; 
		rooms[0][2][row] = 1;
		secondStaircaseZ = row;

		for(int p = 0; p < 3; p++)
		{
			for(int q = 0; q < 3; q++)
			{
				for(int r = 0; r < 3; r++)
				{
					if (p == 0 && q != 0 && secondStaircaseZ == r)
					{
						if (r == 0)
						{
							this.generateDoorX(world, pos.getX() + 11 + 7 * p, pos.getY() + 5 * q, pos.getZ() + 7 + 7 * r, 2, 2);
							this.generateDoorZ(world, pos.getZ() - 3 + 7 * r, pos.getX() + 7 + 7 * p, pos.getY() + 5 * q, 2, 2);
						}
						else if (r == 1)
						{
							this.generateDoorX(world, pos.getX() + 11 + 7 * p, pos.getY() + 5 * q, pos.getZ() + 7 + 7 * r, 2, 2);
							this.generateDoorZ(world, pos.getZ() + 4 + 7 * r, pos.getX() + 7 + 7 * p, pos.getY() + 5 * q, 2, 2);
							this.generateDoorZ(world, pos.getZ() + 11 + 7 * r, pos.getX() + 7 + 7 * p, pos.getY() + 5 * q, 2, 2);
						}
						else if (r == 2)
						{
							this.generateDoorX(world, pos.getX() + 11 + 7 * p, pos.getY() + 5 * q, pos.getZ() + 7 + 7 * r, 2, 2);
							this.generateDoorZ(world, pos.getZ() + 4 + 7 * r, pos.getX() + 7 + 7 * p, pos.getY() + 5 * q, 2, 2);
						}
					}
					else if (p == 1 && q != 2 && firstStaircaseZ == r)
					{
						if (firstStaircaseZ != finalStaircaseZ)
						{
							this.generateDoorX(world, pos.getX() + 11 + 7 * p, pos.getY() + 5 * q, pos.getZ() + 7 + 7 * r, 2, 2);
						}

						if (r == 0)
						{
							this.generateDoorZ(world, pos.getZ() + 11 + 7 * r, pos.getX() + 7 + 7 * p, pos.getY() + 5 * q, 2, 2);
							this.generateDoorX(world, pos.getX() + 4 + 7 * p, pos.getY() + 5 * q, pos.getZ() + 7 + 7 * r, 2, 2);
						}
						else if (r == 1)
						{
							this.generateDoorZ(world, pos.getZ() + 4 + 7 * r, pos.getX() + 7 + 7 * p, pos.getY() + 5 * q, 2, 2);
							this.generateDoorZ(world, pos.getZ() + 11 + 7 * r, pos.getX() + 7 + 7 * p, pos.getY() + 5 * q, 2, 2);
							this.generateDoorX(world, pos.getX() + 4 + 7 * p, pos.getY() + 5 * q, pos.getZ() + 7 + 7 * r, 2, 2);
						}
						else if (r == 2)
						{
							this.generateDoorZ(world, pos.getZ() + 4 + 7 * r, pos.getX() + 7 + 7 * p, pos.getY() + 5 * q, 2, 2);
							this.generateDoorX(world, pos.getX() + 4 + 7 * p, pos.getY() + 5 * q, pos.getZ() + 7 + 7 * r, 2, 2);
						}
					}
					else if (p == 2 && finalStaircaseZ == r)
					{
						if (q == 0)
						{
							this.generateDoorX(world, pos.getX() + 11 + 7 * p, pos.getY() + 5 * q, pos.getZ() + 7 + 7 * r, 2, 2);
						}
						else if (q == 2)
						{
							if (r == 0)
							{
								this.generateDoorX(world, pos.getX() + 4 + 7 * p, pos.getY() + 5 * q, pos.getZ() + 7 + 7 * r, 2, 2);
								this.generateDoorZ(world, pos.getZ() + 11 + 7 * r, pos.getX() + 7 + 7 * p, pos.getY() + 5 * q, 2, 2);
							}
							else if (r == 1)
							{
								this.generateDoorX(world, pos.getX() + 4 + 7 * p, pos.getY() + 5 * q, pos.getZ() + 7 + 7 * r, 2, 2);
								this.generateDoorZ(world, pos.getZ() + 4 + 7 * r, pos.getX() + 7 + 7 * p, pos.getY() + 5 * q, 2, 2);
								this.generateDoorZ(world, pos.getZ() + 11 + 7 * r, pos.getX() + 7 + 7 * p, pos.getY() + 5 * q, 2, 2);
							}
							else if (r == 2)
							{
								this.generateDoorX(world, pos.getX() + 4 + 7 * p, pos.getY() + 5 * q, pos.getZ() + 7 + 7 * r, 2, 2);
								this.generateDoorZ(world, pos.getZ() + 4 + 7 * r, pos.getX() + 7 + 7 * p, pos.getY() + 5 * q, 2, 2);
							}
						}
					}
					else
					{
						int type = this.rooms[p][q][r];

						if(p + 1 < 3)
						{
							int newType = this.rooms[p + 1][q][r];

							if(newType != 2 && !(newType == 1 && type == 1))
							{
								this.rooms[p][q][r] = 3;
								type = 3;

								this.generateDoorX(world, pos.getX() + 11 + 7 * p, pos.getY() + 5 * q, pos.getZ() + 7 + 7 * r, 2, 2);
							}	
						}

						if(p - 1 > 0)
						{
							int newType = this.rooms[p - 1][q][r];

							if(newType != 2 && !(newType == 1 && type == 1))
							{
								this.rooms[p][q][r] = 4;
								type = 4;

								this.generateDoorX(world, pos.getX() + 4 + 7 * p, pos.getY() + 5 * q, pos.getZ() + 7 + 7 * r, 2, 2);
							}	
						}

						if(r + 1 < 3)
						{
							int newType = this.rooms[p][q][r + 1];

							if(newType != 2 && !(newType == 1 && type == 1))
							{
								this.rooms[p][q][r] = 5;
								type = 5;

								this.generateDoorZ(world, pos.getZ() + 11 + 7 * r, pos.getX() + 7 + 7 * p, pos.getY() + 5 * q, 2, 2);
							}	
						}

						if(r - 1 > 0)
						{
							int newType = this.rooms[p][q][r - 1];

							if(newType != 2 && !(newType == 1 && type == 1))
							{
								this.rooms[p][q][r] = 6;
								type = 6;

								this.generateDoorZ(world, pos.getZ() + 4 + 7 * r, pos.getX() + 7 + 7 * p, pos.getY() + 5 * q, 2, 2);
							}	
						}

						int roomType = random.nextInt(3);

						if(type >= 3)
						{
							this.setBlockAndNotifyAdequately(world, new BlockPos(pos.getX() + 7 + p * 7, pos.getY() - 1 + q * 5, pos.getZ() + 7 + r * 7), BlocksAether.dungeon_trap.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic));

							switch(roomType)
							{
								case 1 :
								{
									this.addPlaneY(world, random, new PositionData(pos.getX() + 7 + 7 * p, pos.getY() + 5 * q, pos.getZ() + 7 + 7 * r), new PositionData(2, 0, 2));
									int u = pos.getX() + 7 + 7 * p + random.nextInt(2);
									int v = pos.getZ() + 7 + 7 * r + random.nextInt(2);
									if(world.getBlockState(new BlockPos.MutableBlockPos().setPos(u, pos.getY() + 5 * q + 1, v)).getBlock() == Blocks.AIR)
									{
										this.setBlockAndNotifyAdequately(world, new BlockPos(u, pos.getY() + 5 * q + 1, v), Blocks.CHEST.getDefaultState());
										TileEntityChest chest = (TileEntityChest)world.getTileEntity(new BlockPos(u, pos.getY() + 5 * q + 1, v));

										for(u = 0; u < 3 + random.nextInt(3); u++)
										{
											chest.setInventorySlotContents(random.nextInt(chest.getSizeInventory()), this.getNormalLoot(random));
										}
									}
									break;
								}
								case 2 :
								{
									this.addPlaneY(world, random, new PositionData(pos.getX() + 7 + 7 * p, pos.getY() + 5 * q, pos.getZ() + 7 + 7 * r), new PositionData(2, 0, 2));
									this.setBlockAndNotifyAdequately(world, new BlockPos(pos.getX() + 7 + 7 * p + random.nextInt(2), pos.getY() + 5 * q + 1, pos.getZ() + 7 + 7 * r + random.nextInt(2)), BlocksAether.chest_mimic.getDefaultState());
									this.setBlockAndNotifyAdequately(world, new BlockPos(pos.getX() + 7 + 7 * p + random.nextInt(2), pos.getY() + 5 * q + 1, pos.getZ() + 7 + 7 * r + random.nextInt(2)), BlocksAether.chest_mimic.getDefaultState());
									break;
								}
							}
						}	
					}			
				}
			}
		}

		for(x = 0; x < 24; x++)
		{
			for(z = 0; z < 20; z++)
			{
				int distance = (int)(Math.sqrt(x * x + (z - 7) * (z - 7)) + Math.sqrt(x * x + (z - 12) * (z - 12)));
				if(distance == 21)
				{
					this.setBlockAndNotifyAdequately(world, new BlockPos(pos.getX() + 26 + x, pos.getY(), pos.getZ() + 5 + z), BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_angelic));
				}
				else if(distance > 21)
				{
					this.setBlockAndNotifyAdequately(world, new BlockPos(pos.getX() + 26 + x, pos.getY(), pos.getZ() + 5 + z), BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic));
				}
			}
		}

		setBlocks(BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic), BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_angelic), 20);
		addPlaneY(world, random, new PositionData(pos.getX() + 44, pos.getY() + 1, pos.getZ() + 11), new PositionData(6, 0, 8));
		addSolidBox(world, random, new PositionData(pos.getX() + 46, pos.getY() + 2, pos.getZ() + 13), new PositionData(4, 2, 4));
		addLineX(world, random, new PositionData(pos.getX() + 46, pos.getY() + 4, pos.getZ() + 13), 4);
		addLineX(world, random, new PositionData(pos.getX() + 46, pos.getY() + 4, pos.getZ() + 16), 4);
		addPlaneX(world, random, new PositionData(pos.getX() + 49, pos.getY() + 4, pos.getZ() + 13), new PositionData(0, 4, 4));
		setBlocks(Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BLUE), Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BLUE), 20);
		addPlaneY(world, random, new PositionData(pos.getX() + 47, pos.getY() + 3, pos.getZ() + 14), new PositionData(2, 0, 2));
		
		for(x = 0; x < 2; x++)
		{
			for(z = 0; z < 2; z++)
			{
				this.setBlockAndNotifyAdequately(world, new BlockPos(pos.getX() + 44 + x * 5, pos.getY() + 2, pos.getZ() + 11 + z * 7), BlocksAether.ambrosium_torch.getDefaultState());
			}
		}

		setBlocks(BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic), BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_angelic), 20);
		addPlaneY(world, random, new PositionData(pos.getX() + 35, pos.getY() + 1, pos.getZ() + 5), new PositionData(6, 0, 3)); //left
		addPlaneY(world, random, new PositionData(pos.getX() + 35, pos.getY() + 1, pos.getZ() + 22), new PositionData(6, 0, 3)); //right
		setBlocks(BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic), BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_angelic), 20);
		//left
		addLineZ(world, random, new PositionData(pos.getX() + 34, pos.getY() + 1, pos.getZ() + 5), 2);
		addLineZ(world, random, new PositionData(pos.getX() + 41, pos.getY() + 1, pos.getZ() + 5), 2);
		addLineX(world, random, new PositionData(pos.getX() + 36, pos.getY() + 1, pos.getZ() + 8), 4);
		//right
		addLineZ(world, random, new PositionData(pos.getX() + 34, pos.getY() + 1, pos.getZ() + 23), 2);
		addLineZ(world, random, new PositionData(pos.getX() + 41, pos.getY() + 1, pos.getZ() + 23), 2);
		addLineX(world, random, new PositionData(pos.getX() + 36, pos.getY() + 1, pos.getZ() + 21), 4);

		setBlocks(Blocks.WATER.getDefaultState(), Blocks.WATER.getDefaultState(), 1);
		addPlaneY(world, random, new PositionData(pos.getX() + 35, pos.getY() + 1, pos.getZ() + 5), new PositionData(6, 0, 3));
		addPlaneY(world, random, new PositionData(pos.getX() + 35, pos.getY() + 1, pos.getZ() + 22), new PositionData(6, 0, 3));

		this.setBlockAndNotifyAdequately(world, new BlockPos(pos.getX() + 35, pos.getY() + 1, pos.getZ() + 7), BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic));
		this.setBlockAndNotifyAdequately(world, new BlockPos(pos.getX() + 40, pos.getY() + 1, pos.getZ() + 7), BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic));
		this.setBlockAndNotifyAdequately(world, new BlockPos(pos.getX() + 35, pos.getY() + 1, pos.getZ() + 22), BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic));
		this.setBlockAndNotifyAdequately(world, new BlockPos(pos.getX() + 40, pos.getY() + 1, pos.getZ() + 22), BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic));
		
		for(x = pos.getX() + 36; x < pos.getX() + 40; x += 3)
		{
			for(z = pos.getZ() + 8; z < pos.getZ() + 22; z += 13)
			{
				this.setBlockAndNotifyAdequately(world, new BlockPos(x, pos.getY() + 2, z), BlocksAether.ambrosium_torch.getDefaultState());
			}
		}

		addChandelier(world, pos.getX() + 28, pos.getY(), pos.getZ() + 10, 8);
		addChandelier(world, pos.getX() + 43, pos.getY(), pos.getZ() + 10, 8);
		addChandelier(world, pos.getX() + 43, pos.getY(), pos.getZ() + 19, 8);
		addChandelier(world, pos.getX() + 28, pos.getY(), pos.getZ() + 19, 8);
		addSapling(world, random, new PositionData(pos.getX() + 45, pos.getY() + 1, pos.getZ() + 6));
		addSapling(world, random, new PositionData(pos.getX() + 45, pos.getY() + 1, pos.getZ() + 21));

		EntityValkyrieQueen valk = new EntityValkyrieQueen(world, (double)pos.getX() + 40D, (double)pos.getY() + 1.5D, (double)pos.getZ() + 15D);
		valk.setPosition(pos.getX() + 40, pos.getY() + 2, pos.getZ() + 15);
		valk.setDungeon(pos.getX() + 26, pos.getY(), pos.getZ() + 5);

		if (!world.isRemote)
		{
			world.spawnEntity(valk);
		}

		setBlocks(BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic), BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_angelic), 20);
		addHollowBox(world, random, new PositionData(pos.getX() + 41, pos.getY() - 2, pos.getZ() + 13), new PositionData(4, 4, 4));
		x = pos.getX() + 42 + random.nextInt(2);
		z = pos.getZ() + 14 + random.nextInt(2);
		this.setBlockAndNotifyAdequately(world, new BlockPos(x, pos.getY() - 1, z), BlocksAether.treasure_chest.getDefaultState());

		return true;
    }
	
	private void addSapling(World world, Random random, PositionData pos)
	{
		setBlocks(BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic), BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_angelic), 2);
		addPlaneY(world, random, pos, new PositionData(3, 0, 3));
		BlockPos blockPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
		this.setBlockAndNotifyAdequately(world, blockPos.add(1, 0, 1), BlocksAether.aether_dirt.getDefaultState());
		this.setBlockAndNotifyAdequately(world, blockPos.add(1, 1, 1), BlocksAether.golden_oak_sapling.getDefaultState());

		for(int x = pos.getX(); x < pos.getX() + 3; x += 2)
		{
			for(int z = pos.getZ(); z < pos.getZ() + 3; z += 2)
			{
				this.setBlockAndNotifyAdequately(world, new BlockPos(x, pos.getY() + 1, z), BlocksAether.ambrosium_torch.getDefaultState());
			}
		}
	}
	
	private void addChandelier(World world, int i, int j, int k, int height)
	{
		for(int y = j + height + 3; y < j + height + 6; y++)
		{
			this.setBlockAndNotifyAdequately(world, new BlockPos(i, y, k), Blocks.OAK_FENCE.getDefaultState());
		}
		for(int x = i - 1; x < i + 2; x++)
		{
			this.setBlockAndNotifyAdequately(world, new BlockPos(x, j + height + 1, k), Blocks.GLOWSTONE.getDefaultState());
		}	
		for(int y = j + height; y < j + height + 3; y++)
		{
			this.setBlockAndNotifyAdequately(world, new BlockPos(i, y, k), Blocks.GLOWSTONE.getDefaultState());
		}
		for(int z = k - 1; z < k + 2; z++)
		{
			this.setBlockAndNotifyAdequately(world, new BlockPos(i, j + height + 1, z), Blocks.GLOWSTONE.getDefaultState());
		}		
	}
	
	private void addColumn(World world, Random random, PositionData pos, int h)
	{
		setBlocks(BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic), BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_angelic), 20);
		addPlaneY(world, random, pos, new PositionData(3, 0, 3));
		addPlaneY(world, random, new PositionData(pos.getX(), pos.getY() + h, pos.getZ()), new PositionData(3, 0, 3));
		setBlocks(BlocksAether.pillar.getDefaultState(), BlocksAether.pillar.getDefaultState(), 1);
		addLineY(world, random, new PositionData(pos.getX() + 1, pos.getY(), pos.getZ() + 1), h - 1);
		this.setBlockAndNotifyAdequately(world, new BlockPos(pos.getX() + 1, pos.getY() + h - 1, pos.getZ() + 1), BlocksAether.pillar_top.getDefaultState());
	}

	private void addStaircase(World world, Random random, PositionData pos, int height)
	{
		setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
		addSolidBox(world, random, new PositionData(pos.getX() + 1, pos.getY(), pos.getZ() + 1), new PositionData(4, height, 4));
		setBlocks(BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic), BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_angelic), 5);
		addSolidBox(world, random, new PositionData(pos.getX() + 2, pos.getY(), pos.getZ() + 2), new PositionData(2, height + 4, 2));
		BlockPos blockPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ());
		IBlockState slab = Blocks.STONE_SLAB.getDefaultState();
		IBlockState double_slab = Blocks.DOUBLE_STONE_SLAB.getDefaultState();

		this.setBlockAndNotifyAdequately(world, blockPos.add(1, 0, 1), slab);
		this.setBlockAndNotifyAdequately(world, blockPos.add(2, 0, 1), double_slab);
		this.setBlockAndNotifyAdequately(world, blockPos.add(3, 1, 1), slab);
		this.setBlockAndNotifyAdequately(world, blockPos.add(4, 1, 1), double_slab);
		this.setBlockAndNotifyAdequately(world, blockPos.add(4, 2, 2), slab);
		this.setBlockAndNotifyAdequately(world, blockPos.add(4, 2, 3), double_slab);
		this.setBlockAndNotifyAdequately(world, blockPos.add(4, 3, 4), slab);
		this.setBlockAndNotifyAdequately(world, blockPos.add(3, 3, 4), double_slab);
		this.setBlockAndNotifyAdequately(world, blockPos.add(2, 4, 4), slab);
		this.setBlockAndNotifyAdequately(world, blockPos.add(1, 4, 4), double_slab);
		if(height == 5)
		{
			return;
		}
		this.setBlockAndNotifyAdequately(world, blockPos.add(1, 5, 3), slab);
		this.setBlockAndNotifyAdequately(world, blockPos.add(1, 5, 2), double_slab);
		this.setBlockAndNotifyAdequately(world, blockPos.add(1, 6, 1), slab);
		this.setBlockAndNotifyAdequately(world, blockPos.add(2, 6, 1), double_slab);
		this.setBlockAndNotifyAdequately(world, blockPos.add(3, 7, 1), slab);
		this.setBlockAndNotifyAdequately(world, blockPos.add(4, 7, 1), double_slab);
		this.setBlockAndNotifyAdequately(world, blockPos.add(4, 8, 2), slab);
		this.setBlockAndNotifyAdequately(world, blockPos.add(4, 8, 3), double_slab);
		this.setBlockAndNotifyAdequately(world, blockPos.add(4, 9, 4), slab);
		this.setBlockAndNotifyAdequately(world, blockPos.add(3, 9, 4), double_slab);
	}
	
	//Get loot for normal chests scattered around
	private ItemStack getNormalLoot(Random random)
	{
		int item = random.nextInt(16);
		switch(item)
		{
			case 0 :
				return new ItemStack(ItemsAether.zanite_pickaxe);
			case 1 :
				return new ItemStack(ItemsAether.skyroot_bucket);
			case 2 :
				return new ItemStack(ItemsAether.dart_shooter);
			case 3 :
				return ItemMoaEgg.getStackFromType(AetherEntities.WHITE_MOA);
			case 4 :
				return new ItemStack(ItemsAether.ambrosium_shard, random.nextInt(10) + 1);
			case 5 :
				return new ItemStack(ItemsAether.dart, random.nextInt(5) + 1, 0);
			case 6 :
				return new ItemStack(ItemsAether.dart, random.nextInt(3) + 1, 1);
			case 7 :
				return new ItemStack(ItemsAether.dart, random.nextInt(3) + 1, 2);
			case 8 :
			{
				if(random.nextInt(20) == 0)
					return new ItemStack(ItemsAether.aether_tune);
				break;
			}
			case 9 :
				return new ItemStack(ItemsAether.skyroot_bucket, 1, 2);
			case 10 :
			{
				if(random.nextInt(10) == 0)
					return new ItemStack(ItemsAether.ascending_dawn);
				break;
			}
			case 11 :
			{
				if(random.nextInt(2) == 0)
					return new ItemStack(ItemsAether.zanite_boots);
				if(random.nextInt(2) == 0)
					return new ItemStack(ItemsAether.zanite_helmet);
				if(random.nextInt(2) == 0)
					return new ItemStack(ItemsAether.zanite_leggings);
				if(random.nextInt(2) == 0)
					return new ItemStack(ItemsAether.zanite_chestplate);
				break;
			}
			case 12 : 
			{
				if(random.nextInt(4) == 0)
					return new ItemStack(ItemsAether.iron_pendant);
			}
			case 13 : 
			{
				if(random.nextInt(10) == 0)
					return new ItemStack(ItemsAether.golden_pendant);
			}
			case 14 : 
			{
				if(random.nextInt(15) == 0)
					return new ItemStack(ItemsAether.zanite_ring);
			}
		}
		return new ItemStack(BlocksAether.ambrosium_torch, random.nextInt(4) + 1);
	}

	public static ItemStack getSilverLoot(Random random)
	{
		int item = random.nextInt(13);
		switch(item)
		{
			case 0 :
				return new ItemStack(ItemsAether.gummy_swet, random.nextInt(15) + 1);
			case 1 :
				return new ItemStack(ItemsAether.lightning_sword);
			case 2 :
			{
				if(random.nextBoolean())
					return new ItemStack(ItemsAether.valkyrie_axe);
				if(random.nextBoolean())
					return new ItemStack(ItemsAether.valkyrie_shovel);
				if(random.nextBoolean())
					return new ItemStack(ItemsAether.valkyrie_pickaxe);
				break;
			}
			case 3 :
				return new ItemStack(ItemsAether.holy_sword);
			case 4 :
				return new ItemStack(ItemsAether.valkyrie_helmet);			
			case 5 :
				return new ItemStack(ItemsAether.regeneration_stone);
			case 6 :
			{
				if(random.nextBoolean())
					return new ItemStack(ItemsAether.neptune_helmet);
				if(random.nextBoolean())
					return new ItemStack(ItemsAether.neptune_leggings);
				if(random.nextBoolean())
					return new ItemStack(ItemsAether.neptune_chestplate);
				break;
			}
			case 7 :
			{
				if(random.nextBoolean())
					return new ItemStack(ItemsAether.neptune_boots);
				return new ItemStack(ItemsAether.neptune_gloves);
			}
			case 8 :
				return new ItemStack(ItemsAether.invisibility_cape);
			case 9 :
			{
				if(random.nextBoolean())
					return new ItemStack(ItemsAether.valkyrie_boots);
				return new ItemStack(ItemsAether.valkyrie_gloves);
			}
			case 10 :
				return new ItemStack(ItemsAether.valkyrie_leggings);
			case 11 :
				if(random.nextBoolean())
					return new ItemStack(ItemsAether.valkyrie_chestplate);
		}
		return new ItemStack(ItemsAether.golden_feather);
	}

}