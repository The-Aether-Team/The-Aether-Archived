package com.legacy.aether.world.dungeon;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.dungeon.BlockDungeonBase;
import com.legacy.aether.blocks.util.EnumStoneType;
import com.legacy.aether.entities.bosses.slider.EntitySlider;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.world.dungeon.util.AetherDungeon;
import com.legacy.aether.world.dungeon.util.PositionData;

public class BronzeDungeon extends AetherDungeon
{

    private int numRooms = 4;

	private int n;

	private boolean finished;

	public BronzeDungeon()
    {
		finished = false;
    }

	@Override
	public boolean generate(World world, Random random, BlockPos pos)
	{
		replaceAir = true;
		replaceSolid = true;
		n = 0;

		if(!isBoxSolid(world, new PositionData(pos.getX(), pos.getY(), pos.getZ()), new PositionData(16, 12, 16)) || !isBoxSolid(world, new PositionData(pos.getX() + 20, pos.getY(), pos.getZ() + 2), new PositionData(12, 12, 12)))
		{
			return false;
		}

		System.out.println(pos.toString() + " NEW DUNGEON 2");
		setBlocks(this.lockedBlock(), this.lockedLightBlock(), 20);

		addHollowBox(world, random, new PositionData(pos.getX(), pos.getY(), pos.getZ()), new PositionData(16, 12, 16));

		addHollowBox(world, random, new PositionData(pos.getX() + 6, pos.getY() - 2, pos.getZ() + 6), new PositionData(4, 4, 4));

		EntitySlider slider = new EntitySlider(world);
		slider.setPosition(pos.getX() + 8, pos.getY() + 2, pos.getZ() + 8);
		slider.setDungeon(slider.posX - 8, slider.posY - 2, slider.posZ - 8);

		if (!world.isRemote)
		{
			world.spawnEntityInWorld(slider);
		}

		world.setBlockState(pos.add(7, -1, 7), BlocksAether.treasure_chest.getDefaultState());

		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getY();

		x = pos.getX() + 20;
		y = pos.getY();
		z = pos.getZ() + 2;

		if(!isBoxSolid(world, new PositionData(x, y, z), new PositionData(12, 12, 12)))
		{
			return true;
		}

		setBlocks(this.mainBlock(), this.mainLightBlock(), 20);
		addHollowBox(world, random, new PositionData(x, y, z), new PositionData(12, 12, 12));

		setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);

		addSquareTube(world, random, new PositionData(x - 5, y, z + 3), new PositionData(6, 6, 6), 0);	
		
		for(int p = x + 2; p < x + 10; p += 3)
		{
			for(int q = z + 2; q < z + 10; q += 3)
			{
				world.setBlockState(new BlockPos(p, pos.getY(), q), BlocksAether.dungeon_trap.getDefaultState(), 2);
			}
		}		

		n++;

		generateNextRoom(world, random, new PositionData(x, y, z));
		generateNextRoom(world, random, new PositionData(x, y, z));

		if(n > numRooms || !finished)
		{
			endCorridor(world, random, new PositionData(x, y, z));
		}

        return true;
	}

	public boolean generateNextRoom(World world, Random random, PositionData pos)
	{
		if(n > numRooms && !finished)
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

		if(!isBoxSolid(world, new PositionData(x, y, z), new PositionData(12, 8, 12)))
		{
			return false;
		}

		setBlocks(this.mainBlock(), this.mainLightBlock(), 20);

		addHollowBox(world, random, new PositionData(x, y, z), new PositionData(12, 8, 12));

		for(int p = x; p < x + 12; p++)
		{
			for(int q = y; q < y + 8; q++)
			{
				for(int r = z; r < z + 12; r++)
				{
					if(world.getBlockState(new BlockPos.MutableBlockPos().setPos(p, q, r)) == this.mainBlock() && random.nextInt(100) == 0)
					{
						world.setBlockState(new BlockPos(p, q, r), BlocksAether.dungeon_trap.getDefaultState());
					}
				}
			}
		}

		for(int p = x + 2; p < x + 10; p += 7)
		{
			for(int q = z + 2; q < z + 10; q += 7)
			{
				world.setBlockState(new BlockPos(p, pos.getY(), q), BlocksAether.dungeon_trap.getDefaultState());
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
				world.setBlockState(new BlockPos(p, y + 2, q), BlocksAether.chest_mimic.getDefaultState());
				break;
			}
			case 1 :
			{
				if(world.getBlockState(new BlockPos.MutableBlockPos().setPos(p, y + 2, q)).getBlock() == Blocks.AIR)
				{
					world.setBlockState(new BlockPos(p, y + 2, q), Blocks.CHEST.getDefaultState());
					TileEntityChest chest = (TileEntityChest)world.getTileEntity(new BlockPos(p, y + 2, q));
					
					for(p = 0; p < 3 + random.nextInt(3); p++)
					{
						chest.setInventorySlotContents(random.nextInt(chest.getSizeInventory()), this.getNormalLoot(random));
					}
				}
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
			return false;
		}

		return generateNextRoom(world, random, new PositionData(x, y, z));
	}

	public void endCorridor(World world, Random random, PositionData pos)
	{
		replaceAir = false;
		boolean tunnelling = true;
		boolean flag;
		int dir = 0;
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		if(dir == 0)
		{
			x += 11;
			z += 3;

			while(tunnelling)
			{
				if(isBoxEmpty(world, new PositionData(x, y, z), new PositionData(1, 8, 6)))
				{
					tunnelling = false;
				}

				flag = true;

				while(flag && (world.getBlockState(new BlockPos.MutableBlockPos().setPos(x, y, z)) == this.mainBlock() || world.getBlockState(new BlockPos.MutableBlockPos().setPos(x, y, z)) == this.lockedBlock() || world.getBlockState(new BlockPos.MutableBlockPos().setPos(x, y, z)) == this.lockedLightBlock() || world.getBlockState(new BlockPos.MutableBlockPos().setPos(x, y, z)) == this.mainLightBlock()))
				{
					if(world.getBlockState(new BlockPos.MutableBlockPos().setPos(x + 1, y, z)) == this.mainBlock() || world.getBlockState(new BlockPos.MutableBlockPos().setPos(x + 1, y, z)) == this.lockedBlock() || world.getBlockState(new BlockPos.MutableBlockPos().setPos(x + 1, y, z)) == this.lockedLightBlock() || world.getBlockState(new BlockPos.MutableBlockPos().setPos(x + 1, y, z)) == this.mainLightBlock())
					{
						x++;
					}
					else
					{
						flag = false;
					}
				}
				
				setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);

				addPlaneX(world, random, new PositionData(x, y, z), new PositionData(0, 8, 6));
				setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
				addPlaneX(world, random, new PositionData(x, y + 1, z + 1), new PositionData(0, 6, 4));
				x++;
			}
		}
		
		if(dir == 1)
		{
			x += 3;
			z += 11;
			while(tunnelling)
			{
				if(isBoxEmpty(world, new PositionData(x, y, z), new PositionData(6, 8, 1)))
				{
					tunnelling = false;
				}
				
				flag = true;
				
				while(flag && (world.getBlockState(new BlockPos.MutableBlockPos().setPos(x, y, z)) == this.mainBlock() || world.getBlockState(new BlockPos.MutableBlockPos().setPos(x, y, z)) == this.lockedBlock() || world.getBlockState(new BlockPos.MutableBlockPos().setPos(x, y, z)) == this.lockedLightBlock() || world.getBlockState(new BlockPos.MutableBlockPos().setPos(x, y, z)) == this.mainLightBlock()))
				{
					if(world.getBlockState(new BlockPos.MutableBlockPos().setPos(x, y, z + 1)) == this.mainBlock() || world.getBlockState(new BlockPos.MutableBlockPos().setPos(x, y, z + 1)) == this.lockedBlock() || world.getBlockState(new BlockPos.MutableBlockPos().setPos(x, y, z + 1)) == this.lockedLightBlock() || world.getBlockState(new BlockPos.MutableBlockPos().setPos(x, y, z + 1)) == this.mainLightBlock())
					{
						z++;
					}
					else
					{
						flag = false;
					}
				}
				
				setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);
				addPlaneZ(world, random, new PositionData(x, y, z), new PositionData(6, 8, 0));
				setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
				addPlaneZ(world, random, new PositionData(x + 1, y + 1, z), new PositionData(4, 6, 0));
				z++;
			}
		}

		if(dir == 2)
		{
			x += 3;
			z -= 0;
			while(tunnelling)
			{
				if(isBoxEmpty(world, new PositionData(x, y, z), new PositionData(6, 8, 1)))
				{
					tunnelling = false;
				}
				
				flag = true;
				
				while(flag && (world.getBlockState(new BlockPos.MutableBlockPos().setPos(x, y, z)) == this.mainBlock() || world.getBlockState(new BlockPos.MutableBlockPos().setPos(x, y, z)) == this.lockedBlock() || world.getBlockState(new BlockPos.MutableBlockPos().setPos(x, y, z)) == this.lockedLightBlock() || world.getBlockState(new BlockPos.MutableBlockPos().setPos(x, y, z)) == this.mainLightBlock()))
				{
					if(world.getBlockState(new BlockPos.MutableBlockPos().setPos(x, y, z - 1)) == this.mainBlock() || world.getBlockState(new BlockPos.MutableBlockPos().setPos(x, y, z - 1)) == this.lockedBlock() || world.getBlockState(new BlockPos.MutableBlockPos().setPos(x, y, z - 1)) == this.lockedLightBlock() || world.getBlockState(new BlockPos.MutableBlockPos().setPos(x, y, z - 1)) == this.mainLightBlock())
					{
						z--;
					}
					else 
					{
						flag = false;
					}
				}

				setBlocks(this.fillerBlock(), this.fillerBlock1(), 5);
				addPlaneZ(world, random, new PositionData(x, y, z), new PositionData(6, 8, 0));
				setBlocks(Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), 1);
				addPlaneZ(world, random, new PositionData(x + 1, y + 1, z), new PositionData(4, 6, 0));
				z--;
			}
		}	
		finished = true;
	}

	private ItemStack getNormalLoot(Random random)
	{
		int item = random.nextInt(15);
		switch(item)
		{
			case 0 :
				return new ItemStack(ItemsAether.zanite_pickaxe);
			case 1 :
				return new ItemStack(ItemsAether.zanite_axe);
			case 2 :
				return new ItemStack(ItemsAether.zanite_sword);
			case 3 :
				return new ItemStack(ItemsAether.zanite_shovel);
			case 4 :
				return new ItemStack(ItemsAether.swet_cape);
			case 5 :
				return new ItemStack(ItemsAether.ambrosium_shard, random.nextInt(10) + 1);
			case 6 :
				return new ItemStack(ItemsAether.dart, random.nextInt(5) + 1, 0);
			case 7 :
				return new ItemStack(ItemsAether.dart, random.nextInt(3) + 1, 1);
			case 8 :
				return new ItemStack(ItemsAether.dart, random.nextInt(3) + 1, 2);
			case 9 :
			{
				if(random.nextInt(20) == 0)
				{
					return new ItemStack(ItemsAether.aether_tune);
				}

				break;
			}
			case 10 :
				return new ItemStack(ItemsAether.skyroot_bucket, 1, 2);
			case 11 :
			{
				if(random.nextInt(10) == 0)
				{
					return new ItemStack(Items.RECORD_CAT);
				}

				break;
			}
			case 12 : 
			{
				if(random.nextInt(4) == 0)
				{
					return new ItemStack(ItemsAether.iron_ring);
				}
				break;
			}
			case 13 : 
			{
				if(random.nextInt(10) == 0)
				{
					return new ItemStack(ItemsAether.golden_ring);
				}
				break;
			}
		}
		return new ItemStack(BlocksAether.ambrosium_torch);
	}
	
	public static ItemStack getBronzeLoot(Random random)
	{
		int item = random.nextInt(9);
		switch(item)
		{
			case 0 :
				return new ItemStack(ItemsAether.gummy_swet, random.nextInt(7) + 1, random.nextInt(2));
			case 1 :
				return new ItemStack(ItemsAether.phoenix_bow);
			case 2 :
				return new ItemStack(ItemsAether.flaming_sword);
			case 3 :
				return new ItemStack(ItemsAether.notch_hammer);
			case 4 :
				return new ItemStack(ItemsAether.lightning_knife, random.nextInt(15) + 1);
			case 5 :
				return new ItemStack(ItemsAether.valkyrie_lance);
			case 6 :
				return new ItemStack(ItemsAether.agility_cape);
			case 7 :
				return new ItemStack(ItemsAether.sentry_boots);
		}

		return new ItemStack(ItemsAether.cloud_staff);
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