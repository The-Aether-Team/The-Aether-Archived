package com.legacy.aether.world.gen.components;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.gen.structure.StructureBoundingBox;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.dungeon.BlockDungeonBase;
import com.legacy.aether.blocks.util.EnumStoneType;
import com.legacy.aether.world.gen.AetherStructure;

public class ComponentBronzeDungeonRoom extends AetherStructure
{

	private int direction;

	public int xOffset, yOffset, zOffset;

	public ComponentBronzeDungeonRoom()
	{
		
	}

	public ComponentBronzeDungeonRoom(int chunkX, int chunkY, int chunkZ, int xOffset, int yOffset, int zOffset, int direction)
	{
		if(direction == 0)
		{
			xOffset += 16;
			zOffset += 0;
		}
		else if(direction == 1)
		{
			xOffset += 0;
			zOffset += 16;
		}
		else if(direction == 2)
		{
			xOffset -= 16;
			zOffset += 0;
		}
		else if(direction == 3)
		{
			xOffset += 0;
			zOffset -= 16;
		}

		this.direction = direction;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;

        this.setCoordBaseMode(EnumFacing.NORTH);
        this.boundingBox = new StructureBoundingBox(chunkX, chunkY, chunkZ, chunkX + 100, chunkY + 100, chunkZ + 100);
	}

	@Override
	public boolean generate() 
	{
		this.setStructureOffset(this.xOffset, this.yOffset, this.zOffset);

		if(!this.isBoxSolid(0, 0, 0, 12, 8, 12))
		{
			return false;
		}

		this.setBlocks(BlocksAether.dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Carved), BlocksAether.dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Sentry), 20);

		this.addHollowBox(0, 0, 0, 12, 8, 12);

		for(int p = 0; p < 12; p++)
		{
			for(int q = 0; q < 8; q++)
			{
				for(int r = 0; r < 12; r++)
				{
					if (this.getBlockStateWithOffset(p, q, r) == BlocksAether.dungeon_block.getDefaultState().withProperty(BlockDungeonBase.dungeon_stone, EnumStoneType.Carved) && this.random.nextInt(100) == 0)
					{
						this.setBlockWithOffset(p, q, r, BlocksAether.dungeon_trap.getDefaultState());
					}
				}
			}
		}

		for(int p = 2; p < 10; p += 7)
		{
			for(int q = 2; q < 10; q += 7)
			{
				this.setBlockWithOffset(p, 0, q, BlocksAether.dungeon_trap.getDefaultState());
			}
		}

		this.addPlaneY(4, 1, 4, 4, 4);

		int type = this.random.nextInt(2);
		int p = 5 + this.random.nextInt(2);
		int q = 5 + this.random.nextInt(2);

		switch(type)
		{
			case 0 :
			{
				this.setBlockWithOffset(p, 2, q, BlocksAether.chest_mimic.getDefaultState());
				break;
			}
			case 1 :
			{
				if(this.getBlockStateWithOffset(p, 2, q).getBlock() == Blocks.AIR)
				{
					this.setBlockWithOffset(p, 2, q, Blocks.CHEST.getDefaultState());

					/*TileEntityChest chest = (TileEntityChest)world.getTileEntity(new BlockPos(p, y + 2, q));
					
					for(p = 0; p < 3 + random.nextInt(3); p++)
					{
						chest.setInventorySlotContents(random.nextInt(chest.getSizeInventory()), this.getNormalLoot(random));
					}*/
				}
				break;
			}			
		}

		this.setBlocks(BlocksAether.holystone.getDefaultState(), BlocksAether.mossy_holystone.getDefaultState(), 5);

		switch(this.direction)
		{
			case 0:
			{
				this.addSquareTube(-5, 0, 3, 6, 6, 6, 0);
				break;
			}
			case 1:
			{
				this.addSquareTube(3, 0, -5, 6, 6, 6, 2);
				break;
			}
			case 2:
			{
				this.addSquareTube(11, 0, 3, 6, 6, 6, 0);	
				break;
			}
			case 3:
			{
				this.addSquareTube(3, 0, 11, 6, 6, 6, 2);		
				break;
			}
		}

		return true;
	}

}