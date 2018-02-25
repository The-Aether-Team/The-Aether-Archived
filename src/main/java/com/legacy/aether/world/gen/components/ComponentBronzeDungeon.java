package com.legacy.aether.world.gen.components;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.dungeon.BlockDungeonBase;
import com.legacy.aether.blocks.util.EnumStoneType;
import com.legacy.aether.world.gen.AetherStructure;

public class ComponentBronzeDungeon extends AetherStructure
{

    private int numRooms = 4;

	private int n;

	private boolean finished;

	public ComponentBronzeDungeon()
	{
		
	}

	public ComponentBronzeDungeon(int chunkX, int chunkY, int chunkZ)
	{
        this.setCoordBaseMode(EnumFacing.NORTH);
        this.boundingBox = new StructureBoundingBox(chunkX, chunkY, chunkZ, chunkX + 100, chunkY + 100, chunkZ + 100);
	}

	@Override
	public boolean generate() 
	{
		this.replaceAir = true;
		this.replaceSolid = true;

		this.setStructureOffset(10, 10, 10);

		if(!isBoxSolid(0, 0, 0, 10, 10, 10))
		{
			return false;
		}

		this.setBlocks(this.lockedBlock(), this.lockedLightBlock(), 20);

		this.addHollowBox(0, 0, 0, 16, 12, 16);

		this.addHollowBox(6, -2, 6, 4, 4, 4);

		/*EntitySlider slider = new EntitySlider(world);
		slider.setPosition(pos.getX() + 8, pos.getY() + 2, pos.getZ() + 8);
		slider.setDungeon(slider.posX - 8, slider.posY - 2, slider.posZ - 8);

		if (!world.isRemote)
		{
			world.spawnEntity(slider);
		}*/

		this.setBlockWithOffset(7, -1, 7, BlocksAether.treasure_chest.getDefaultState());

		int x = 20;
		int y = 0;
		int z = 2;

		if(!isBoxSolid(x, y, z, 12, 12, 12))
		{
			return true;
		}

		setBlocks(this.mainBlock(), this.mainLightBlock(), 20);
		addHollowBox(x, y, z, 12, 12, 12);

		setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);

		addSquareTube(x - 5, y, z + 3, 6, 6, 6, 0);	

		for(int p = x + 2; p < x + 10; p += 3)
		{
			for(int q = z + 2; q < z + 10; q += 3)
			{
				this.setBlockWithOffset(p, 0, q, BlocksAether.dungeon_trap.getDefaultState());
			}
		}

		this.n++;

		//generateNextRoom(world, random, new PositionData(x, y, z));
		//generateNextRoom(world, random, new PositionData(x, y, z));

		if(this.n > this.numRooms || !this.finished)
		{
			//endCorridor(world, random, new PositionData(x, y, z));
		}

        return true;
	}

	public boolean generateNextRoom(int offsetX, int offsetY, int offsetZ)
	{
		if(n > numRooms && !finished)
		{
			endCorridor(offsetX, offsetY, offsetZ);
			return false;
		}

		int dir = random.nextInt(4);
		int x = offsetX;
		int y = offsetY;
		int z = offsetZ;
		
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

		if(!isBoxSolid(x, y, z, 12, 8, 12))
		{
			return false;
		}

		setBlocks(this.mainBlock(), this.mainLightBlock(), 20);

		addHollowBox(x, y, z, 12, 8, 12);

		for(int p = x; p < x + 12; p++)
		{
			for(int q = y; q < y + 8; q++)
			{
				for(int r = z; r < z + 12; r++)
				{
					if (this.getBlockStateWithOffset(p, q, r) == this.mainBlock() && this.random.nextInt(100) == 0)
					{
						this.setBlockWithOffset(p, q, r, BlocksAether.dungeon_trap.getDefaultState());
					}
				}
			}
		}

		for(int p = x + 2; p < x + 10; p += 7)
		{
			for(int q = z + 2; q < z + 10; q += 7)
			{
				this.setBlockWithOffset(p, 0, q, BlocksAether.dungeon_trap.getDefaultState());
			}
		}

		addPlaneY(x + 4, y + 1, z + 4, 4, 4);

		int type = random.nextInt(2);
		int p = x + 5 + random.nextInt(2);
		int q = z + 5 + random.nextInt(2);

		switch(type)
		{
			case 0 :
			{
				this.setBlockWithOffset(p, y + 2, q, BlocksAether.chest_mimic.getDefaultState());
				break;
			}
			case 1 :
			{
				if(this.getBlockStateWithOffset(p, y + 2, q).getBlock() == Blocks.AIR)
				{
					this.setBlockWithOffset(p, y + 2, q, Blocks.CHEST.getDefaultState());

					/*TileEntityChest chest = (TileEntityChest)world.getTileEntity(new BlockPos(p, y + 2, q));
					
					for(p = 0; p < 3 + random.nextInt(3); p++)
					{
						chest.setInventorySlotContents(random.nextInt(chest.getSizeInventory()), this.getNormalLoot(random));
					}*/
				}
				break;
			}			
		}

		setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);

		switch(dir)
		{
			case 0: 
			{
				addSquareTube(x - 5, y, z + 3, 6, 6, 6, 0);				
				break;
			}
			case 1: 
			{
				addSquareTube(x + 3, y, z - 5, 6, 6, 6, 2);				
				break;
			}
			case 2: 
			{
				addSquareTube(x + 11, y, z + 3, 6, 6, 6, 0);				
				break;
			}
			case 3: 
			{
				addSquareTube(x + 3, y, z + 11, 6, 6, 6, 2);				
				break;
			}
		}

		n++;

		if(!generateNextRoom(x, y, z))
		{
			return false;
		}

		return generateNextRoom(x, y, z);
	}

	public void endCorridor(int offsetX, int offsetY, int offsetZ)
	{
		replaceAir = false;
		boolean tunnelling = true;
		boolean flag;
		int dir = random.nextInt(3);
		int x = offsetX;
		int y = offsetY;
		int z = offsetZ;

		if(dir == 0)
		{
			x += 11;
			z += 3;
			
			while(tunnelling)
			{
				if(isBoxEmpty(x, y, z, 1, 8, 6) || x - offsetX > 100)
				{
					tunnelling = false;
				}

				flag = true;

				while(flag && (this.getBlockStateWithOffset(x, y, z) == this.mainBlock() || this.getBlockStateWithOffset(x, y, z) == this.lockedBlock() || this.getBlockStateWithOffset(x, y, z) == this.lockedLightBlock() || this.getBlockStateWithOffset(x, y, z) == this.mainLightBlock()))
				{
					if(this.getBlockStateWithOffset(x + 1, y, z) == this.mainBlock() || this.getBlockStateWithOffset(x + 1, y, z) == this.lockedBlock() || this.getBlockStateWithOffset(x + 1, y, z) == this.lockedLightBlock() || this.getBlockStateWithOffset(x + 1, y, z) == this.mainLightBlock())
					{
						x++;
					}
					else
					{
						flag = false;
					}
				}
				
				setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);

				addPlaneX(x, y, z, 8, 6);
				setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
				addPlaneX(x, y + 1, z + 1, 6, 4);
				x++;
			}
		}
		
		if(dir == 1)
		{
			x += 3;
			z += 11;
			while(tunnelling)
			{
				if(isBoxEmpty(x, y, z, 6, 8, 1) || z - offsetZ > 100)
				{
					tunnelling = false;
				}
				
				flag = true;
				
				while(flag && (this.getBlockStateWithOffset(x, y, z) == this.mainBlock() || this.getBlockStateWithOffset(x, y, z) == this.lockedBlock() || this.getBlockStateWithOffset(x, y, z) == this.lockedLightBlock() || this.getBlockStateWithOffset(x, y, z) == this.mainLightBlock()))
				{
					if(this.getBlockStateWithOffset(x, y, z + 1) == this.mainBlock() || this.getBlockStateWithOffset(x, y, z + 1) == this.lockedBlock() || this.getBlockStateWithOffset(x, y, z + 1) == this.lockedLightBlock() || this.getBlockStateWithOffset(x, y, z + 1) == this.mainLightBlock())
					{
						z++;
					}
					else
					{
						flag = false;
					}
				}
				
				setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);
				addPlaneZ(x, y, z, 6, 8);
				setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
				addPlaneZ(x + 1, y + 1, z, 4, 6);
				z++;
			}
		}
		
		if(dir == 2)
		{
			x += 3;
			z -= 0;
			while(tunnelling)
			{
				if(isBoxEmpty(x, y, z, 6, 8, 1) || offsetZ - z > 100)
				{
					tunnelling = false;
				}
				
				flag = true;
				
				while(flag && (this.getBlockStateWithOffset(x, y, z) == this.mainBlock() || this.getBlockStateWithOffset(x, y, z) == this.lockedBlock() || this.getBlockStateWithOffset(x, y, z) == this.lockedLightBlock() || this.getBlockStateWithOffset(x, y, z) == this.mainLightBlock()))
				{
					if(this.getBlockStateWithOffset(x, y, z - 1) == this.mainBlock() || this.getBlockStateWithOffset(x, y, z - 1) == this.lockedBlock() || this.getBlockStateWithOffset(x, y, z - 1) == this.lockedLightBlock() || this.getBlockStateWithOffset(x, y, z - 1) == this.mainLightBlock())
					{
						z--;
					}
					else 
					{
						flag = false;
					}
				}

				setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);
				addPlaneZ(x, y, z, 6, 8);
				setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
				addPlaneZ(x + 1, y + 1, z, 4, 6);
				z--;
			}
		}	
		finished = true;
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