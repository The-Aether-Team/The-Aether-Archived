package com.legacy.aether.server.blocks.dungeon;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.legacy.aether.server.blocks.BlocksAether;
import com.legacy.aether.server.blocks.util.EnumStoneType;
import com.legacy.aether.server.entities.bosses.EntityValkyrie;
import com.legacy.aether.server.entities.hostile.EntitySentry;

public class BlockDungeonTrap extends BlockDungeonBase
{

	public BlockDungeonTrap()
	{
		super(true);
	}

	@Override
    public void onEntityWalk(World world, BlockPos pos, Entity entityIn)
    {
		IBlockState state = world.getBlockState(pos);
    	EnumStoneType type = (EnumStoneType) state.getValue(dungeon_stone);

    	if (entityIn instanceof EntityPlayer)
    	{
    		Block block = world.getBlockState(pos).getBlock();
 
        	world.setBlockState(pos, BlocksAether.dungeon_block.getDefaultState().withProperty(dungeon_stone, EnumStoneType.getType(block.getMetaFromState(state))));

        	if (type == EnumStoneType.Carved || type == EnumStoneType.Sentry)
        	{
        		EntitySentry sentry = new EntitySentry(world, pos.getX() + 2D, pos.getY() + 1D, pos.getZ() + 2D);
        		if (!world.isRemote)
        		world.spawnEntityInWorld(sentry);
        	}
        	else if (type == EnumStoneType.Angelic || type == EnumStoneType.Light_angelic)
        	{
        		EntityValkyrie valkyrie = new EntityValkyrie(world);
        		valkyrie.setPosition(pos.getX() + 0.5D, pos.getY() + 1D, pos.getZ() + 0.5D);
        		if (!world.isRemote)
        		world.spawnEntityInWorld(valkyrie);
        	}

        	world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundCategory.PLAYERS, 1.0F, 1.5F);
    	}
    }

}