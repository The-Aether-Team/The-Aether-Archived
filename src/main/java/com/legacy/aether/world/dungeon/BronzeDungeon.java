package com.legacy.aether.world.dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.dungeon.BlockDungeonBase;
import com.legacy.aether.blocks.dungeon.BlockTreasureChest;
import com.legacy.aether.blocks.natural.BlockHolystone;
import com.legacy.aether.blocks.util.EnumStoneType;
import com.legacy.aether.entities.bosses.slider.EntitySlider;
import com.legacy.aether.events.BronzeDungeonSizeEvent;
import com.legacy.aether.events.DialogueClickedEvent;
import com.legacy.aether.world.dungeon.util.AetherDungeonVirtual;
import com.legacy.aether.world.dungeon.util.PositionData;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BronzeDungeon extends AetherDungeonVirtual
{
	private boolean needsCorridor;
	private int roomMaximum;
	private int roomCount;

	public BronzeDungeon()
	{
		needsCorridor = false;
	}

	@Override
	public boolean generate(World world, Random random, BlockPos pos)
	{
		BronzeDungeonSizeEvent event = new BronzeDungeonSizeEvent(random.nextInt(6) + 2);
		MinecraftForge.EVENT_BUS.post(event);

		replaceAir = true;
		replaceSolid = true;

		roomMaximum = event.getDungeonRoomMaximum();
		roomCount = 0;

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

		generateEmptyRoom(world, random, pos, slider);

		return true;
	}

	public boolean generateEmptyRoom(World world, Random random, BlockPos pos, EntitySlider slider)
	{
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

		if (!determineRoomPosition(world, random, new PositionData(x, y, z)) && roomCount == 0)
		{
			this.placementStorage.clear();
			this.replacementStorage.clear();
			slider.setDead();
			return false;
		}

		if (needsCorridor)
		{
			endCorridor(world, random, new PositionData(x, y, z));
		}

		return true;
	}

	public boolean determineRoomPosition(World world, Random random, PositionData pos)
	{
		if (roomCount >= roomMaximum)
		{
			this.needsCorridor = true;
			return true;
		}

		ArrayList<Integer> sides = new ArrayList<>();
		sides.add(1);
		sides.add(2);
		sides.add(3);
		sides.add(4);

		Collections.shuffle(sides);

		if (generateRoomWithSide(world, random, pos, sides.get(0)))
		{
			return true;
		}
		else if (generateRoomWithSide(world, random, pos, sides.get(1)))
		{
			return true;
		}
		else if (generateRoomWithSide(world, random, pos, sides.get(2)))
		{
			return true;
		}
		else if (generateRoomWithSide(world, random, pos, sides.get(3)))
		{
			return true;
		}
		else
		{
			this.needsCorridor = true;
			return false;
		}
	}

	public boolean generateRoomWithSide(World world, Random random, PositionData pos, int switchCase)
	{
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		int dir = 0;

		switch (switchCase)
		{
			case 1:
			{
				x += 16;
				z += 0;
				dir = 0;
				break;
			}
			case 2:
			{
				x += 0;
				z += 16;
				dir = 1;
				break;
			}
			case 3:
			{
				x -= 16;
				z += 0;
				dir = 2;
				break;
			}
			case 4:
			{
				x += 0;
				z -= 16;
				dir = 3;
				break;
			}
		}

		return generateNextRoom(world, random, new PositionData(x, y, z), dir);
	}

	public boolean generateNextRoom(World world, Random random, PositionData pos, int dir)
	{
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();

		if(!isBoxSolid(world, new PositionData(x, y, z), new PositionData(12, 8, 12)) || isSpaceTaken(world, new PositionData(x, y, z), new PositionData(12, 8, 12)))
		{
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

		roomCount++;

		if(!determineRoomPosition(world, random, new PositionData(x, y, z)))
		{
			return false;
		}

		return determineRoomPosition(world, random, new PositionData(x, y, z));
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
		if (!this.needsCorridor)
		{
			return false;
		}

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

					if (x == pos.getX() + 11)
					{
						setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);
						addPlaneX(world, random, new PositionData(x, y, z), new PositionData(0, 8, 6));

						setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
						addPlaneX(world, random, new PositionData(x, y + 1, z + 1), new PositionData(0, 6, 4));
					}

					setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);
					addTunnelPlaneX(world, random, new PositionData(x, y, z), new PositionData(0, 8, 6));

					setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
					addTunnelPlaneX(world, random, new PositionData(x, y + 1, z + 1), new PositionData(0, 6, 4));

					x++;
				}

				if (maxLength)
				{
					return false;
				}

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

					if (x == pos.getX())
					{
						setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);
						addPlaneX(world, random, new PositionData(x, y, z), new PositionData(0, 8, 6));

						setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
						addPlaneX(world, random, new PositionData(x, y + 1, z + 1), new PositionData(0, 6, 4));
					}

					setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);
					addTunnelPlaneX(world, random, new PositionData(x, y, z), new PositionData(0, 8, 6));

					setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
					addTunnelPlaneX(world, random, new PositionData(x, y + 1, z + 1), new PositionData(0, 6, 4));

					x--;
				}

				if (maxLength)
				{
					return false;
				}

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

					if (z == pos.getZ() + 11)
					{
						setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);
						addPlaneZ(world, random, new PositionData(x, y, z), new PositionData(6, 8, 0));

						setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
						addPlaneZ(world, random, new PositionData(x + 1, y + 1, z), new PositionData(4, 6, 0));
					}

					setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);
					addTunnelPlaneZ(world, random, new PositionData(x, y, z), new PositionData(6, 8, 0));

					setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
					addTunnelPlaneZ(world, random, new PositionData(x + 1, y + 1, z), new PositionData(4, 6, 0));

					z++;
				}

				if (maxLength)
				{
					return false;
				}

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

					if (z == pos.getZ())
					{
						setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);
						addPlaneZ(world, random, new PositionData(x, y, z), new PositionData(6, 8, 0));

						setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
						addPlaneZ(world, random, new PositionData(x + 1, y + 1, z), new PositionData(4, 6, 0));
					}

					setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);
					addTunnelPlaneZ(world, random, new PositionData(x, y, z), new PositionData(6, 8, 0));

					setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
					addTunnelPlaneZ(world, random, new PositionData(x + 1, y + 1, z), new PositionData(4, 6, 0));

					z--;
				}

				if (maxLength)
				{
					return false;
				}

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
		return BlocksAether.holystone.getDefaultState().withProperty(BlockHolystone.dungeon_block, true);
	}

	public IBlockState fillerBlock1()
	{
		return BlocksAether.mossy_holystone.getDefaultState().withProperty(BlockHolystone.dungeon_block, true);
	}
}