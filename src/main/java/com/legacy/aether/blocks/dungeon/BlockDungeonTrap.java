package com.legacy.aether.blocks.dungeon;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.entities.bosses.EntityFireMinion;
import com.legacy.aether.entities.bosses.EntityValkyrie;
import com.legacy.aether.entities.hostile.EntitySentry;

public class BlockDungeonTrap extends Block {

	private Block pickBlock;

	public BlockDungeonTrap(Block pickBlock) {
		super(Material.rock);

		this.pickBlock = pickBlock;
		this.setBlockUnbreakable();
	}

	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity entityIn) {
		if (entityIn instanceof EntityPlayer) {
			world.setBlock(x, y, z, this.pickBlock);

			if (this == BlocksAether.carved_trap) {
				EntitySentry sentry = new EntitySentry(world, x + 2D, y + 1D, z + 2D);

				if (!world.isRemote) {
					world.spawnEntityInWorld(sentry);
				}
			} else if (this == BlocksAether.angelic_trap) {
				EntityValkyrie valkyrie = new EntityValkyrie(world);
				valkyrie.setPosition(x + 0.5D, y + 1D, z + 0.5D);

				if (!world.isRemote) {
					world.spawnEntityInWorld(valkyrie);
				}
			} else if (this == BlocksAether.hellfire_trap) {
				EntityFireMinion minion = new EntityFireMinion(world);
				minion.setPosition(x + 0.5D, y + 1D, z + 0.5D);

				if (!world.isRemote) {
					world.spawnEntityInWorld(minion);
				}
			}

			world.playSoundEffect(x, y, z, "random.door_close", 2.0F, world.rand.nextFloat() - world.rand.nextFloat() * 0.2F + 1.2F);
		}
	}

}