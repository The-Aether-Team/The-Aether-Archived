package com.legacy.aether.world.gen.components;

import net.minecraft.block.BlockColored;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.dungeon.BlockDungeonBase;
import com.legacy.aether.blocks.util.EnumCloudType;
import com.legacy.aether.blocks.util.EnumStoneType;
import com.legacy.aether.entities.bosses.valkyrie_queen.EntityValkyrieQueen;
import com.legacy.aether.world.gen.AetherGenUtils;
import com.legacy.aether.world.gen.AetherStructure;

public class ComponentSilverDungeon extends AetherStructure
{

	private static final IBlockState LOCKED_ANGELIC_STONE = BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic);

	private static final IBlockState LOCKED_LIGHT_ANGELIC_STONE = BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_angelic);

	private int[][][] rooms = new int[3][3][3];

	private int firstStaircaseZ, secondStaircaseZ, finalStaircaseZ;

	private int xTendency, zTendency;

    public ComponentSilverDungeon()
    {

    }

	public ComponentSilverDungeon(int chunkX, int chunkZ)
	{
        this.setCoordBaseMode(EnumFacing.NORTH);
        this.boundingBox = new StructureBoundingBox(chunkX, 100, chunkZ, chunkX + 100, 190, chunkZ + 100);
	}

	public void setStaircasePosition(int first, int second, int third)
	{
		this.firstStaircaseZ = first;
		this.secondStaircaseZ = second;
		this.finalStaircaseZ = third;
	}

	public void setCloudTendencies(int xTendency, int zTendency)
	{
		this.xTendency = xTendency;
		this.zTendency = zTendency;
	}

	@Override
	public boolean generate() 
	{
		this.replaceAir = true;

		this.setStructureOffset(21, 17, 20);

		for (int tries = 0; tries < 100; tries++)
		{
			AetherGenUtils.generateClouds(this, EnumCloudType.Cold, false, 10, this.random.nextInt(77), 0, this.random.nextInt(50), this.xTendency, this.zTendency);
		}

		this.setStructureOffset(31, 24, 30);

		this.replaceSolid = true;

		this.setBlocks(BlocksAether.holystone.getDefaultState(), BlocksAether.mossy_holystone.getDefaultState(), 30);

		this.addSolidBox(0, -5, 0, 55, 5, 30);

		for(int x = 0; x < 55; x += 4)
		{
			this.generateColumn(x, 0, 0, 14);
			this.generateColumn(x, 0, 27, 14);
		}

		for(int z = 0; z < 12; z += 4)
		{
			this.generateColumn(0, 0, z, 14);
			this.generateColumn(52, 0, z, 14);
		}

		for(int z = 19; z < 30; z += 4)
		{
			this.generateColumn(0, 0, z, 14);
			this.generateColumn(52, 0, z, 14);
		}

		this.setBlocks(LOCKED_ANGELIC_STONE, LOCKED_LIGHT_ANGELIC_STONE, 20);
		this.addHollowBox(4, -1, 4, 47, 16, 22);
		this.addPlaneX(11, 0, 5, 15, 20);
		this.addPlaneX(18, 0, 5, 15, 20);
		this.addPlaneX(25, 0, 5, 15, 20);
		this.addPlaneZ(5, 0, 11, 20, 15);
		this.addPlaneZ(5, 0, 18, 20, 15);

		this.setBlocks(LOCKED_ANGELIC_STONE, BlocksAether.dungeon_trap.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_angelic), 30);
		this.addPlaneY(5, 4, 5, 20, 20);
		this.addPlaneY(5, 9, 5, 20, 20);

		for(int y = 0; y < 2; y++)
		{
			for(int z = 14; z < 16; z++)
			{
				this.setBlockWithOffset(4, y, z, Blocks.AIR.getDefaultState());
			}
		}

		this.setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
		this.addSolidBox(0, -4, 14, 1, 4, 2);
		this.addSolidBox(1, -3, 14, 1, 3, 2);
		this.addSolidBox(2, -2, 14, 1, 2, 2);
		this.addSolidBox(3, -1, 14, 1, 1, 2);

		this.setBlocks(LOCKED_ANGELIC_STONE, LOCKED_LIGHT_ANGELIC_STONE, 15);

		for(int y = 0; y < 7; y++)
		{
			this.addPlaneY(-1, 15 + y, -1 + 2 * y, 57, 32 - 4 * y);
		}

		this.generateStaircase(19, 0, 5 + this.finalStaircaseZ * 7, 10);

		this.rooms[2][0][this.finalStaircaseZ] = 2;
		this.rooms[2][1][this.finalStaircaseZ] = 2;
		this.rooms[2][2][this.finalStaircaseZ] = 1;

		int x = 25;
		int y;
		int z;

		for(y = 0; y < 2; y++)
		{
			for(z = 7 + 7 * this.finalStaircaseZ; z < 9 + 7 * this.finalStaircaseZ; z++)
			{
				this.setBlockWithOffset(x, y, z, Blocks.AIR.getDefaultState());
			}
		}

		this.generateStaircase(12, 0, 5 + this.firstStaircaseZ * 7, 5);

		this.rooms[1][0][this.firstStaircaseZ] = 1;
		this.rooms[1][1][this.firstStaircaseZ] = 1;

		this.generateStaircase(5, 5, 5 + this.secondStaircaseZ * 7, 5);

		this.rooms[0][1][this.secondStaircaseZ] = 1; 
		this.rooms[0][2][this.secondStaircaseZ] = 1;

		for(int p = 0; p < 3; p++)
		{
			for(int q = 0; q < 3; q++)
			{
				for(int r = 0; r < 3; r++)
				{
					if (p == 0 && q != 0 && this.secondStaircaseZ == r)
					{
						if (r == 0)
						{
							this.generateDoorX(11 + 7 * p, 5 * q, 7 + 7 * r, 2, 2);
							this.generateDoorZ(-3 + 7 * r, 7 + 7 * p, 5 * q, 2, 2);
						}
						else if (r == 1)
						{
							this.generateDoorX(11 + 7 * p, 5 * q, 7 + 7 * r, 2, 2);
							this.generateDoorZ(4 + 7 * r, 7 + 7 * p, 5 * q, 2, 2);
							this.generateDoorZ(11 + 7 * r, 7 + 7 * p, 5 * q, 2, 2);
						}
						else if (r == 2)
						{
							this.generateDoorX(11 + 7 * p, 5 * q, 7 + 7 * r, 2, 2);
							this.generateDoorZ(4 + 7 * r, 7 + 7 * p, 5 * q, 2, 2);
						}
					}
					else if (p == 1 && q != 2 && this.firstStaircaseZ == r)
					{
						if (this.firstStaircaseZ != this.finalStaircaseZ)
						{
							this.generateDoorX(11 + 7 * p, 5 * q, 7 + 7 * r, 2, 2);
						}

						if (r == 0)
						{
							this.generateDoorZ(11 + 7 * r, 7 + 7 * p, 5 * q, 2, 2);
							this.generateDoorX(4 + 7 * p, 5 * q, 7 + 7 * r, 2, 2);
						}
						else if (r == 1)
						{
							this.generateDoorZ(4 + 7 * r, 7 + 7 * p, 5 * q, 2, 2);
							this.generateDoorZ(11 + 7 * r, 7 + 7 * p, 5 * q, 2, 2);
							this.generateDoorX(4 + 7 * p, 5 * q, 7 + 7 * r, 2, 2);
						}
						else if (r == 2)
						{
							this.generateDoorZ(4 + 7 * r, 7 + 7 * p, 5 * q, 2, 2);
							this.generateDoorX(4 + 7 * p, 5 * q, 7 + 7 * r, 2, 2);
						}
					}
					else if (p == 2 && this.finalStaircaseZ == r)
					{
						if (q == 0)
						{
							this.generateDoorX(11 + 7 * p, 5 * q, 7 + 7 * r, 2, 2);
						}
						else if (q == 2)
						{
							if (r == 0)
							{
								this.generateDoorX(4 + 7 * p, 5 * q, 7 + 7 * r, 2, 2);
								this.generateDoorZ(11 + 7 * r, 7 + 7 * p, 5 * q, 2, 2);
							}
							else if (r == 1)
							{
								this.generateDoorX(4 + 7 * p, 5 * q, 7 + 7 * r, 2, 2);
								this.generateDoorZ(4 + 7 * r, 7 + 7 * p, 5 * q, 2, 2);
								this.generateDoorZ(11 + 7 * r, 7 + 7 * p, 5 * q, 2, 2);
							}
							else if (r == 2)
							{
								this.generateDoorX(4 + 7 * p, 5 * q, 7 + 7 * r, 2, 2);
								this.generateDoorZ(4 + 7 * r, 7 + 7 * p, 5 * q, 2, 2);
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

								this.generateDoorX(11 + 7 * p, 5 * q, 7 + 7 * r, 2, 2);
							}	
						}

						if(p - 1 > 0)
						{
							int newType = this.rooms[p - 1][q][r];

							if(newType != 2 && !(newType == 1 && type == 1))
							{
								this.rooms[p][q][r] = 4;
								type = 4;

								this.generateDoorX(4 + 7 * p, 5 * q, 7 + 7 * r, 2, 2);
							}	
						}

						if(r + 1 < 3)
						{
							int newType = this.rooms[p][q][r + 1];

							if(newType != 2 && !(newType == 1 && type == 1))
							{
								this.rooms[p][q][r] = 5;
								type = 5;

								this.generateDoorZ(11 + 7 * r, 7 + 7 * p, 5 * q, 2, 2);
							}	
						}

						if(r - 1 > 0)
						{
							int newType = this.rooms[p][q][r - 1];

							if(newType != 2 && !(newType == 1 && type == 1))
							{
								this.rooms[p][q][r] = 6;
								type = 6;

								this.generateDoorZ(4 + 7 * r, 7 + 7 * p, 5 * q, 2, 2);
							}	
						}

						int roomType = this.random.nextInt(3);

						if(type >= 3)
						{
							this.setBlockWithOffset(7 + p * 7, -1 + q * 5, 7 + r * 7, BlocksAether.dungeon_trap.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic));

							switch(roomType)
							{
								case 1:
								{
									this.addPlaneY(7 + 7 * p, 5 * q, 7 + 7 * r, 2, 2);

									int u = 7 + 7 * p + this.random.nextInt(2);
									int v = 7 + 7 * r + this.random.nextInt(2);

									if(this.getBlockState(u, 5 * q + 1, v).getMaterial() == Material.AIR)
									{
										this.setBlockWithOffset(u, 5 * q + 1, v, Blocks.CHEST.getDefaultState());

										/*TileEntityChest chest = (TileEntityChest)world.getTileEntity(new BlockPos(u,5 * q + 1, v));

										for(u = 0; u < 3 + random.nextInt(3); u++)
										{
											chest.setInventorySlotContents(random.nextInt(chest.getSizeInventory()), this.getNormalLoot(random));
										}*/
									}

									break;
								}
								case 2:
								{
									this.addPlaneY(7 + 7 * p, 5 * q, 7 + 7 * r, 2, 2);
									this.setBlockWithOffset(7 + 7 * p + this.random.nextInt(2), 5 * q + 1, 7 + 7 * r + this.random.nextInt(2), BlocksAether.chest_mimic.getDefaultState());

									if (this.random.nextBoolean())
									{
										this.setBlockWithOffset(7 + 7 * p + this.random.nextInt(2), 5 * q + 1, 7 + 7 * r + this.random.nextInt(2), BlocksAether.chest_mimic.getDefaultState());
									}

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
					this.setBlockWithOffset(26 + x, 0, 5 + z, BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_angelic));
				}
				else if(distance > 21)
				{
					this.setBlockWithOffset(26 + x, 0, 5 + z, BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic));
				}
			}
		}

		this.setBlocks(LOCKED_ANGELIC_STONE, LOCKED_LIGHT_ANGELIC_STONE, 20);
		this.addPlaneY(44, 1, 11, 6, 8);
		this.addSolidBox(46, 2, 13, 4, 2, 4);
		this.addLineX(46, 4, 13, 4);
		this.addLineX(46, 4, 16, 4);
		this.addPlaneX(49, 4, 13, 4, 4);

		this.setBlocks(Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BLUE), Blocks.WOOL.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BLUE), 20);
		this.addPlaneY(47, 3, 14, 2, 2);

		for(x = 0; x < 2; x++)
		{
			for(z = 0; z < 2; z++)
			{
				this.setBlockWithOffset(44 + x * 5, 2, 11 + z * 7, BlocksAether.ambrosium_torch.getDefaultState());
			}
		}

		this.setBlocks(LOCKED_ANGELIC_STONE, LOCKED_LIGHT_ANGELIC_STONE, 20);
		this.addPlaneY(35, 1, 5, 6, 3); //left
		this.addPlaneY(35, 1, 22, 6, 3); //right

		//left
		this.addLineZ(34, 1, 5, 2);
		this.addLineZ(41, 1, 5, 2);
		this.addLineX(36, 1, 8, 4);
		//right
		this.addLineZ(34, 1, 23, 2);
		this.addLineZ(41, 1, 23, 2);
		this.addLineX(36, 1, 21, 4);

		this.setBlocks(Blocks.WATER.getDefaultState(), Blocks.WATER.getDefaultState(), 1);
		this.addPlaneY(35, 1, 5, 6, 3);
		this.addPlaneY(35, 1, 22, 6, 3);

		this.setBlockWithOffset(35, 1, 7, BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic));
		this.setBlockWithOffset(40, 1, 7, BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic));
		this.setBlockWithOffset(35, 1, 22, BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic));
		this.setBlockWithOffset(40, 1, 22, BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic));
		
		for(x = 36; x < 40; x += 3)
		{
			for(z = 8; z < 22; z += 13)
			{
				this.setBlockWithOffset(x, 2, z, BlocksAether.ambrosium_torch.getDefaultState());
			}
		}

		this.generateChandelier(28, 0, 10, 8);
		this.generateChandelier(43, 0, 10, 8);
		this.generateChandelier(43, 0, 19, 8);
		this.generateChandelier(28, 0, 19, 8);

		this.generateGoldenOakSapling(45, 1, 6);
		this.generateGoldenOakSapling(45, 1, 21);

		EntityValkyrieQueen valkyrieQueen = new EntityValkyrieQueen(this.worldObj, (double)this.getActualX(40, 15), (double)this.getActualY(1) + 0.5D, (double)this.getActualZ(40, 15));

		valkyrieQueen.setPosition(this.getActualX(40, 15), this.getActualY(2), this.getActualZ(40, 15));
		valkyrieQueen.setDungeonPosition(this.getActualX(26, 24), this.getActualY(0), this.getActualZ(26, 24));

		this.spawnEntity(valkyrieQueen, 40, 1, 15);

		this.setBlocks(LOCKED_ANGELIC_STONE, LOCKED_LIGHT_ANGELIC_STONE, 20);
		this.addHollowBox(41, -2, 13, 4, 4, 4);

		x = 42 + this.random.nextInt(2);
		z = 14 + this.random.nextInt(2);

		this.setBlockWithOffset(x, -1, z, BlocksAether.treasure_chest.getDefaultState());

		return true;
	}

	public void generateGoldenOakSapling(int x, int y, int z)
	{
		this.setBlocks(LOCKED_ANGELIC_STONE, LOCKED_LIGHT_ANGELIC_STONE, 2);
		this.addPlaneY(x, y, z, 3, 3);

		this.setBlockWithOffset(x + 1, y, z + 1, BlocksAether.aether_dirt.getDefaultState());
		this.setBlockWithOffset(x + 1, y + 1, z + 1, BlocksAether.golden_oak_sapling.getDefaultState());

		for(int lineX = x; lineX < x + 3; lineX += 2)
		{
			for(int lineZ = z; lineZ < z + 3; lineZ += 2)
			{
				this.setBlockWithOffset(lineX, y + 1, lineZ, BlocksAether.ambrosium_torch.getDefaultState());
			}
		}
	}

	public void generateChandelier(int x, int y, int z, int height)
	{
		for (int lineY = y + (height + 3); lineY < y + (height + 6); lineY++)
		{
			this.setBlockWithOffset(x, lineY, z, Blocks.OAK_FENCE.getDefaultState());
		}

		for (int lineX = (x - 1); lineX < (x + 2); lineX++)
		{
			this.setBlockWithOffset(lineX, y + (height + 1), z, Blocks.GLOWSTONE.getDefaultState());
		}

		for (int lineY = (y + height); lineY < y + (height + 3); lineY++)
		{
			this.setBlockWithOffset(x, lineY, z, Blocks.GLOWSTONE.getDefaultState());
		}

		for (int lineZ = (z - 1); lineZ < (z + 2); lineZ++)
		{
			this.setBlockWithOffset(x, y + (height + 1), lineZ, Blocks.GLOWSTONE.getDefaultState());
		}
	}

	public void generateColumn(int x, int y, int z, int yRange)
	{
		this.setBlocks(BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic), BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_angelic), 20);
		this.addPlaneY(x, y, z, 3, 3);
		this.addPlaneY(x, y + yRange, z, 3, 3);
		this.setBlocks(BlocksAether.pillar.getDefaultState(), BlocksAether.pillar.getDefaultState(), 1);
		this.addLineY(x + 1, y, z + 1, yRange - 1);
		this.setBlockWithOffset(x + 1, y + (yRange - 1), z + 1, BlocksAether.pillar_top.getDefaultState());
	}

	public void generateStaircase(int x, int y, int z, int height)
	{
		this.setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
		this.addSolidBox(x + 1, y, z + 1, 4, height, 4);
		this.setBlocks(BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Angelic), BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Light_angelic), 5);
		this.addSolidBox(x + 2, y, z + 2, 2, height + 4, 2);

		IBlockState slab = Blocks.STONE_SLAB.getDefaultState();

		IBlockState double_slab = Blocks.DOUBLE_STONE_SLAB.getDefaultState();

		this.setBlockWithOffset(x + 1, y, z + 1, slab);
		this.setBlockWithOffset(x + 2, y, z + 1, double_slab);
		this.setBlockWithOffset(x + 3, y + 1, z + 1, slab);
		this.setBlockWithOffset(x + 4, y + 1, z + 1, double_slab);
		this.setBlockWithOffset(x + 4, y + 2, z + 2, slab);
		this.setBlockWithOffset(x + 4, y + 2, z + 3, double_slab);
		this.setBlockWithOffset(x + 4, y + 3, z + 4, slab);
		this.setBlockWithOffset(x + 3, y + 3, z + 4, double_slab);
		this.setBlockWithOffset(x + 2, y + 4, z + 4, slab);
		this.setBlockWithOffset(x + 1, y + 4, z + 4, double_slab);

		if(height == 5)
		{
			return;
		}

		this.setBlockWithOffset(x + 1, y + 5, z + 3, slab);
		this.setBlockWithOffset(x + 1, y + 5, z + 2, double_slab);
		this.setBlockWithOffset(x + 1, y + 6, z + 1, slab);
		this.setBlockWithOffset(x + 2, y + 6, z + 1, double_slab);
		this.setBlockWithOffset(x + 3, y + 7, z + 1, slab);
		this.setBlockWithOffset(x + 4, y + 7, z + 1, double_slab);
		this.setBlockWithOffset(x + 4, y + 8, z + 2, slab);
		this.setBlockWithOffset(x + 4, y + 8, z + 3, double_slab);
		this.setBlockWithOffset(x + 4, y + 9, z + 4, slab);
		this.setBlockWithOffset(x + 3, y + 9, z + 4, double_slab);
	}

    public void generateDoorX(int x, int y, int z, int yF, int zF)
    {
		for(int yFinal = y; yFinal < y + yF; yFinal++)
		{
			for(int zFinal = z; zFinal < z + zF; zFinal++)
			{
				this.setBlockWithOffset(x, yFinal, zFinal, Blocks.AIR.getDefaultState());
			}
		}
    }

    public void generateDoorZ(int z, int x, int y, int xF, int yF)
    {
		for(int xFinal = x; xFinal < x + xF; xFinal++)
		{
			for(int yFinal = y; yFinal < y + yF; yFinal++)
			{
				this.setBlockWithOffset(xFinal, yFinal, z, Blocks.AIR.getDefaultState());
			}
		}
    }

}