package com.gildedgames.the_aether.blocks.container;

import com.gildedgames.the_aether.tileentity.util.AetherTileEntity;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockAetherContainer extends BlockContainer {

	public BlockAetherContainer(Material materialIn) {
		super(materialIn);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack stack) {
		if (stack.hasDisplayName()) {
			TileEntity tileentity = worldIn.getTileEntity(x, y, z);

			if (tileentity instanceof AetherTileEntity) {
				((AetherTileEntity) tileentity).setCustomName(stack.getDisplayName());
			}
		}
	}

	public static void setState(World worldIn, int x, int y, int z, boolean isActive) {
		TileEntity tileentity = worldIn.getTileEntity(x, y, z);

		worldIn.setBlockMetadataWithNotify(x, y, z, isActive ? 1 : 0, 3);

		if (tileentity != null) {
			tileentity.validate();
			worldIn.setTileEntity(x, y, z, tileentity);
		}
	}

}