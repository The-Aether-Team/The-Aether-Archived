package com.legacy.aether.blocks.natural;

import com.legacy.aether.items.util.DoubleDropHelper;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockAetherDirt extends Block 
{

	public BlockAetherDirt()
	{
		super(Material.ground);

		this.setHardness(0.2F);
		this.setHarvestLevel("shovel", 0);
		this.setStepSound(soundTypeGravel);
		this.setBlockTextureName("aether_legacy:aether_dirt");
	}

	@Override
    public void harvestBlock(World worldIn, EntityPlayer player, int x, int y, int z, int meta)
	{
		DoubleDropHelper.dropBlock(player, x, y, z, this, meta);
	}

	@Override
    public int damageDropped(int meta)
    {
    	return 1;
    }

}