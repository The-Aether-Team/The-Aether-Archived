package com.gildedgames.the_aether.blocks.natural;

import java.util.Random;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.items.util.DoubleDropHelper;
import com.gildedgames.the_aether.world.AetherWorld;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAetherGrass extends Block implements IGrowable {

	@SideOnly(Side.CLIENT)
	private IIcon blockIconTop;

	@SideOnly(Side.CLIENT)
	private IIcon blockIconSnowy;

	public BlockAetherGrass() {
		super(Material.grass);

		this.setHardness(0.2F);
		this.setTickRandomly(true);
		this.setStepSound(soundTypeGrass);
		this.setHarvestLevel("shovel", 0);
	}

	@Override
	public void updateTick(World worldIn, int x, int y, int z, Random rand) {
		if (!worldIn.isRemote) {
			if (worldIn.getBlockLightValue(x, y + 1, z) < 4 && worldIn.getBlockLightOpacity(x, y + 1, z) > 2) {
				worldIn.setBlock(x, y, z, BlocksAether.aether_dirt);
			} else if (worldIn.getBlockLightValue(x, y + 1, z) >= 9) {
				for (int l = 0; l < 4; ++l) {
					int i1 = x + rand.nextInt(3) - 1;
					int j1 = y + rand.nextInt(5) - 3;
					int k1 = z + rand.nextInt(3) - 1;

					if (worldIn.getBlock(i1, j1, k1) == BlocksAether.aether_dirt && worldIn.getBlockMetadata(i1, j1, k1) == 0 && worldIn.getBlockLightValue(i1, j1 + 1, k1) >= 4 && worldIn.getBlockLightOpacity(i1, j1 + 1, k1) <= 2) {
						worldIn.setBlock(i1, j1, k1, BlocksAether.aether_grass);
					}
				}
			}
		}
	}

	@Override
	public Item getItemDropped(int meta, Random random, int fortune) {
		return Item.getItemFromBlock(BlocksAether.aether_dirt);
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, int x, int y, int z, int meta) {
		DoubleDropHelper.dropBlock(player, x, y, z, this, meta);
	}

	@Override
	public int damageDropped(int meta) {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registry) {
		this.blockIcon = registry.registerIcon("aether_legacy:aether_grass_side");
		this.blockIconSnowy = registry.registerIcon("aether_legacy:aether_grass_side_snowy");
		this.blockIconTop = registry.registerIcon("aether_legacy:aether_grass_top");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side == 1 ? this.blockIconTop : (side == 0 ? BlocksAether.aether_dirt.getBlockTextureFromSide(side) : this.blockIcon);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		if (side == 1)
		{
			return this.blockIconTop;
		}
		else if (side == 0)
		{
			return BlocksAether.aether_dirt.getBlockTextureFromSide(side);
		}
		else
		{
			Material material = world.getBlock(x, y + 1, z).getMaterial();
			return material != Material.snow && material != Material.craftedSnow ? this.blockIcon : this.blockIconSnowy;
		}
	}

	@Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
		EnumPlantType plantType = plantable.getPlantType(world, x, y + 1, z);

		return plantType == EnumPlantType.Plains;
	}

	@Override
	public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_) {
		return true;
	}

	@Override
	public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_) {
		return true;
	}

	@Override
	public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_) {
		int l = 0;

		while (l < 128) {
			int i1 = p_149853_3_;
			int j1 = p_149853_4_ + 1;
			int k1 = p_149853_5_;
			int l1 = 0;

			while (true) {
				if (l1 < l / 16) {
					i1 += p_149853_2_.nextInt(3) - 1;
					j1 += (p_149853_2_.nextInt(3) - 1) * p_149853_2_.nextInt(3) / 2;
					k1 += p_149853_2_.nextInt(3) - 1;

					if (p_149853_1_.getBlock(i1, j1 - 1, k1) == BlocksAether.aether_grass && !p_149853_1_.getBlock(i1, j1, k1).isNormalCube()) {
						++l1;
						continue;
					}
				} else if (p_149853_1_.isAirBlock(i1, j1, k1)) {
					if (p_149853_2_.nextInt(8) != 0) {
						if (Blocks.tallgrass.canBlockStay(p_149853_1_, i1, j1, k1)) {
							p_149853_1_.setBlock(i1, j1, k1, Blocks.tallgrass, 1, 3);
						}
					} else if (p_149853_2_.nextInt(12) == 0) {
						if (BlocksAether.berry_bush_stem.canBlockStay(p_149853_1_, i1, j1, k1)) {
							p_149853_1_.setBlock(i1, j1, k1, BlocksAether.berry_bush_stem, 0, 3);
						}
					} else {
						AetherWorld.aether_biome.plantFlower(p_149853_1_, p_149853_2_, i1, j1, k1);
					}
				}

				++l;
				break;
			}
		}
	}

}