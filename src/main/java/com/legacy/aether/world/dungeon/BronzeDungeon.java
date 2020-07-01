package com.legacy.aether.world.dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.dungeon.BlockDungeonBase;
import com.legacy.aether.blocks.dungeon.BlockTreasureChest;
import com.legacy.aether.blocks.util.EnumStoneType;
import com.legacy.aether.entities.bosses.slider.EntitySlider;
import com.legacy.aether.world.dungeon.util.AetherDungeonVirtual;
import com.legacy.aether.world.dungeon.util.PositionData;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BronzeDungeon extends AetherDungeonVirtual
{
	private int numRooms = 4;

	private int n;

	private boolean needsCorridor;

	private boolean hasCorridor;

	public BronzeDungeon()
	{
		hasCorridor = false;
		needsCorridor = false;
	}

	@Override
	public boolean generate(World world, Random random, BlockPos pos)
	{
		replaceAir = true;
		replaceSolid = true;
		n = 0;

		preGenerate(world, random, pos);

		return true;
	}

	public boolean preGenerate(World world, Random random, BlockPos pos)
	{
		if(!isBoxSolid(world, new PositionData(pos.getX(), pos.getY() - 3, pos.getZ()), new PositionData(16, 15, 16)) || isSpaceTaken(world, new PositionData(pos.getX(), pos.getY() - 3, pos.getZ()), new PositionData(16, 15, 16)))
		{
			return false;
		}

		setBlocks(this.lockedBlock(), this.lockedLightBlock(), 20);

		addHollowBox(world, random, new PositionData(pos.getX(), pos.getY(), pos.getZ()), new PositionData(16, 12, 16));

		addHollowBox(world, random, new PositionData(pos.getX() + 6, pos.getY() - 2, pos.getZ() + 6), new PositionData(4, 4, 4));

		EntitySlider slider = new EntitySlider(world);
		slider.setPosition(pos.getX() + 8, pos.getY() + 2, pos.getZ() + 8);
		slider.setDungeon(slider.posX - 8, slider.posY - 2, slider.posZ - 8);

		if (!world.isRemote)
		{
			world.spawnEntity(slider);
		}

		this.storeReplacementBlock(world, pos.add(7, -1, 7), ((BlockTreasureChest)BlocksAether.treasure_chest).correctFacing(world, pos.add(7, -1, 7), BlocksAether.treasure_chest.getDefaultState()));

		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getY();

		int rooms = random.nextInt(4);

		switch (rooms)
		{
			case 0:
			{
				//EAST
				x = pos.getX() + 20;
				y = pos.getY();
				z = pos.getZ() + 2;

				if(!isBoxSolid(world, new PositionData(x, y, z), new PositionData(12, 12, 12)) || isSpaceTaken(world, new PositionData(x, y, z), new PositionData(12, 12, 12)))
				{
					this.placementStorage.clear();
					this.replacementStorage.clear();
					slider.setDead();
					return false;
				}

				setBlocks(this.mainBlock(), this.mainLightBlock(), 20);
				addHollowBox(world, random, new PositionData(x, y, z), new PositionData(12, 12, 12));

				setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);

				addSquareTube(world, random, new PositionData(x - 5, y, z + 3), new PositionData(6, 6, 6), 0);

				for(int p = x + 2; p < x + 10; p += 3)
				{
					for(int q = z + 2; q < z + 10; q += 3)
					{
						if (this.placementStorage.get(new BlockPos.MutableBlockPos().setPos(p, pos.getY(), q)) != Blocks.AIR && this.placementStorage.get(new BlockPos.MutableBlockPos().setPos(p, pos.getY(), q)) != null)
						{
							this.storeReplacementBlock(world, new BlockPos(p, pos.getY(), q), BlocksAether.dungeon_trap.getDefaultState());
						}
					}
				}
				break;
			}
			case 1:
			{
				//WEST
				x = pos.getX() - 16;
				y = pos.getY();
				z = pos.getZ() + 2;

				if(!isBoxSolid(world, new PositionData(x, y, z), new PositionData(12, 12, 12)) || isSpaceTaken(world, new PositionData(x, y, z), new PositionData(12, 12, 12)))
				{
					this.placementStorage.clear();
					this.replacementStorage.clear();
					slider.setDead();
					return false;
				}

				setBlocks(this.mainBlock(), this.mainLightBlock(), 20);
				addHollowBox(world, random, new PositionData(x, y, z), new PositionData(12, 12, 12));

				setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);

				addSquareTube(world, random, new PositionData(x + 11, y, z + 3), new PositionData(6, 6, 6), 0);

				for(int p = x + 2; p < x + 10; p += 3)
				{
					for(int q = z + 2; q < z + 10; q += 3)
					{
						if (this.placementStorage.get(new BlockPos.MutableBlockPos().setPos(p, pos.getY(), q)) != Blocks.AIR && this.placementStorage.get(new BlockPos.MutableBlockPos().setPos(p, pos.getY(), q)) != null)
						{
							this.storeReplacementBlock(world, new BlockPos(p, pos.getY(), q), BlocksAether.dungeon_trap.getDefaultState());
						}
					}
				}
				break;
			}
			case 2:
			{
				//SOUTH
				x = pos.getX() + 2;
				y = pos.getY();
				z = pos.getZ() + 20;

				if(!isBoxSolid(world, new PositionData(x, y, z), new PositionData(12, 12, 12)) || isSpaceTaken(world, new PositionData(x, y, z), new PositionData(12, 12, 12)))
				{
					this.placementStorage.clear();
					this.replacementStorage.clear();
					slider.setDead();
					return false;
				}

				setBlocks(this.mainBlock(), this.mainLightBlock(), 20);
				addHollowBox(world, random, new PositionData(x, y, z), new PositionData(12, 12, 12));

				setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);

				addSquareTube(world, random, new PositionData(x + 3, y, z - 5), new PositionData(6, 6, 6), 2);

				for(int p = x + 2; p < x + 10; p += 3)
				{
					for(int q = z + 2; q < z + 10; q += 3)
					{
						if (this.placementStorage.get(new BlockPos.MutableBlockPos().setPos(p, pos.getY(), q)) != Blocks.AIR && this.placementStorage.get(new BlockPos.MutableBlockPos().setPos(p, pos.getY(), q)) != null)
						{
							this.storeReplacementBlock(world, new BlockPos(p, pos.getY(), q), BlocksAether.dungeon_trap.getDefaultState());
						}
					}
				}
				break;
			}
			case 3:
			{
				//NORTH
				x = pos.getX() + 2;
				y = pos.getY();
				z = pos.getZ() - 16;

				if(!isBoxSolid(world, new PositionData(x, y, z), new PositionData(12, 12, 12)) || isSpaceTaken(world, new PositionData(x, y, z), new PositionData(12, 12, 12)))
				{
					this.placementStorage.clear();
					this.replacementStorage.clear();
					slider.setDead();
					return false;
				}

				setBlocks(this.mainBlock(), this.mainLightBlock(), 20);
				addHollowBox(world, random, new PositionData(x, y, z), new PositionData(12, 12, 12));

				setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);

				addSquareTube(world, random, new PositionData(x + 3, y, z + 11), new PositionData(6, 6, 6), 2);

				for(int p = x + 2; p < x + 10; p += 3)
				{
					for(int q = z + 2; q < z + 10; q += 3)
					{
						if (this.placementStorage.get(new BlockPos.MutableBlockPos().setPos(p, pos.getY(), q)) != Blocks.AIR && this.placementStorage.get(new BlockPos.MutableBlockPos().setPos(p, pos.getY(), q)) != null)
						{
							this.storeReplacementBlock(world, new BlockPos(p, pos.getY(), q), BlocksAether.dungeon_trap.getDefaultState());
						}
					}
				}
				break;
			}
		}

		n++;

		if (needsCorridor)
		{
			endCorridor(world, random, new PositionData(x, y, z));
			return true;
		}

		generateNextRoom(world, random, new PositionData(x, y, z));
		generateNextRoom(world, random, new PositionData(x, y, z));

		return true;
	}

	public boolean generateNextRoom(World world, Random random, PositionData pos)
	{
		if (needsCorridor)
		{
			endCorridor(world, random, pos);
			return false;
		}

		int dir = random.nextInt(4);
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		if(dir == 0)
		{
			x += 16;
			z += 0;
		}
		else if(dir == 1)
		{
			x += 0;
			z += 16;
		}
		else if(dir == 2)
		{
			x -= 16;
			z += 0;
		}
		else if(dir == 3)
		{
			x += 0;
			z -= 16;
		}

		if(!isBoxSolid(world, new PositionData(x, y, z), new PositionData(12, 8, 12)) || isSpaceTaken(world, new PositionData(x, y, z), new PositionData(12, 8, 12)))
		{
			this.needsCorridor = true;
			return false;
		}

		setBlocks(this.mainBlock(), this.mainLightBlock(), 20);

		addHollowBox(world, random, new PositionData(x, y, z), new PositionData(12, 8, 12));

		for(int p = x + 2; p < x + 10; p += 7)
		{
			for(int q = z + 2; q < z + 10; q += 7)
			{
				if (this.placementStorage.get(new BlockPos.MutableBlockPos().setPos(p, pos.getY(), q)) != Blocks.AIR && this.placementStorage.get(new BlockPos.MutableBlockPos().setPos(p, pos.getY(), q)) != null)
				{
					this.storeReplacementBlock(world, new BlockPos(p, pos.getY(), q), BlocksAether.dungeon_trap.getDefaultState());
				}
			}
		}

		addPlaneY(world, random, new PositionData(x + 4, y + 1, z + 4), new PositionData(4, 0, 4));
		int type = random.nextInt(2);
		int p = x + 5 + random.nextInt(2);
		int q = z + 5 + random.nextInt(2);

		switch(type)
		{
			case 0 :
			{
				this.storeReplacementBlock(world, new BlockPos(p, y + 2, q), BlocksAether.chest_mimic.getDefaultState());
				break;
			}
			case 1 :
			{
				this.storeReplacementBlock(world, new BlockPos(p, y + 2, q), BlocksAether.dungeon_chest.getDefaultState());
				break;
			}
		}

		setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);

		switch(dir)
		{
			case 0:
			{
				addSquareTube(world, random, new PositionData(x - 5, y, z + 3), new PositionData(6, 6, 6), 0);
				break;
			}
			case 1:
			{
				addSquareTube(world, random, new PositionData(x + 3, y, z - 5), new PositionData(6, 6, 6), 2);
				break;
			}
			case 2:
			{
				addSquareTube(world, random, new PositionData(x + 11, y, z + 3), new PositionData(6, 6, 6), 0);
				break;
			}
			case 3:
			{
				addSquareTube(world, random, new PositionData(x + 3, y, z + 11), new PositionData(6, 6, 6), 2);
				break;
			}
		}

		n++;

		if(!generateNextRoom(world, random,  new PositionData(x, y, z)))
		{
			this.needsCorridor = true;
			return false;
		}

		return generateNextRoom(world, random, new PositionData(x, y, z));
	}

	public boolean endCorridor(World world, Random random, PositionData pos)
	{
		ArrayList<Integer> sides = new ArrayList<>();
		sides.add(1);
		sides.add(2);
		sides.add(3);
		sides.add(4);

		Collections.shuffle(sides);

		if (generateEndCorridor(world, random, pos, sides.get(0)))
		{
			return true;
		}
		else if (generateEndCorridor(world, random, pos, sides.get(1)))
		{
			return true;
		}
		else if (generateEndCorridor(world, random, pos, sides.get(2)))
		{
			return true;
		}
		else if (generateEndCorridor(world, random, pos, sides.get(3)))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean generateEndCorridor(World world, Random random, PositionData pos, int switchCase)
	{
		switch (switchCase)
		{
			case 1:
			{
				//EAST

				boolean tunnelling = true;
				boolean maxLength = false;
				int x = pos.getX();
				int y = pos.getY();
				int z = pos.getZ();

				x += 11;
				z += 3;

				if (isSpaceTaken(world, new PositionData(x + 1, y, z), new PositionData(2, 8, 6)))
				{
					return false;
				}

				while(tunnelling)
				{
					if(isBoxEmpty(world, new PositionData(x, y, z), new PositionData(1, 8, 6)))
					{
						tunnelling = false;
					}

					if (x - pos.getX() > 100)
					{
						maxLength = true;
						tunnelling = false;
					}

					setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);
					addPlaneX(world, random, new PositionData(x, y, z), new PositionData(0, 8, 6));

					setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
					addPlaneX(world, random, new PositionData(x, y + 1, z + 1), new PositionData(0, 6, 4));

					x++;
				}

				if (maxLength)
				{
					return false;
				}

				this.hasCorridor = true;
				this.needsCorridor = false;

				return true;
			}
			case 2:
			{
				//WEST

				boolean tunnelling = true;
				boolean maxLength = false;
				int x = pos.getX();
				int y = pos.getY();
				int z = pos.getZ();

				x -= 0;
				z += 3;

				if (isSpaceTaken(world, new PositionData(x - 1, y, z), new PositionData(1, 8, 6)))
				{
					return false;
				}

				while(tunnelling)
				{
					if(isBoxEmpty(world, new PositionData(x, y, z), new PositionData(1, 8, 6)))
					{
						tunnelling = false;
					}

					if (pos.getX() - x > 100)
					{
						maxLength = true;
						tunnelling = false;
					}

					setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);
					addPlaneX(world, random, new PositionData(x, y, z), new PositionData(0, 8, 6));

					setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
					addPlaneX(world, random, new PositionData(x, y + 1, z + 1), new PositionData(0, 6, 4));

					x--;
				}

				if (maxLength)
				{
					return false;
				}

				this.hasCorridor = true;
				this.needsCorridor = false;

				return true;
			}
			case 3:
			{
				//SOUTH

				boolean tunnelling = true;
				boolean maxLength = false;
				int x = pos.getX();
				int y = pos.getY();
				int z = pos.getZ();

				x += 3;
				z += 11;

				if (isSpaceTaken(world, new PositionData(x, y, z + 1), new PositionData(6, 8, 2)))
				{
					return false;
				}

				while(tunnelling)
				{
					if(isBoxEmpty(world, new PositionData(x, y, z), new PositionData(6, 8, 1)))
					{
						tunnelling = false;
					}

					if (z - pos.getZ() > 100)
					{
						maxLength = true;
						tunnelling = false;
					}

					setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);
					addPlaneZ(world, random, new PositionData(x, y, z), new PositionData(6, 8, 0));

					setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
					addPlaneZ(world, random, new PositionData(x + 1, y + 1, z), new PositionData(4, 6, 0));

					z++;
				}

				if (maxLength)
				{
					return false;
				}

				this.hasCorridor = true;
				this.needsCorridor = false;

				return true;
			}
			case 4:
			{
				//NORTH

				boolean tunnelling = true;
				boolean maxLength = false;
				int x = pos.getX();
				int y = pos.getY();
				int z = pos.getZ();

				x += 3;
				z -= 0;

				if (isSpaceTaken(world, new PositionData(x, y, z - 1), new PositionData(6, 8, 1)))
				{
					return false;
				}

				while(tunnelling)
				{
					if(isBoxEmpty(world, new PositionData(x, y, z), new PositionData(6, 8, 1)))
					{
						tunnelling = false;
					}

					if (pos.getZ() - z > 100)
					{
						maxLength = true;
						tunnelling = false;
					}

					setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);
					addPlaneZ(world, random, new PositionData(x, y, z), new PositionData(6, 8, 0));

					setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
					addPlaneZ(world, random, new PositionData(x + 1, y + 1, z), new PositionData(4, 6, 0));

					z--;
				}

				if (maxLength)
				{
					return false;
				}

				this.hasCorridor = true;
				this.needsCorridor = false;

				return true;
			}
		}

		return false;
	}

	public IBlockState lockedLightBlock()
	{
		return BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Sentry);
	}

	public IBlockState lockedBlock()
	{
		return BlocksAether.locked_dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Carved);
	}

	public IBlockState mainLightBlock()
	{
		return BlocksAether.dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Sentry);
	}

	public IBlockState mainBlock()
	{
		return BlocksAether.dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Carved);
	}

	public IBlockState fillerBlock()
	{
		return BlocksAether.holystone.getDefaultState();
	}

	public IBlockState fillerBlock1()
	{
		return BlocksAether.mossy_holystone.getDefaultState();
	}
}