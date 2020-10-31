package com.gildedgames.the_aether.blocks.natural;

import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.items.util.DoubleDropHelper;

public class BlockHolystone extends Block
{
	public static final PropertyBool double_drop = PropertyBool.create(Aether.doubleDropNotifier());

	public static final PropertyBool dungeon_block = PropertyBool.create("dungeon_block");

	public BlockHolystone()
	{
		super(Material.ROCK);

		this.setHardness(1.5F);
		this.setResistance(10.0F);
		this.setSoundType(SoundType.STONE);
		this.setCreativeTab(AetherCreativeTabs.blocks);
		this.setDefaultState(this.getDefaultState().withProperty(double_drop, Boolean.TRUE).withProperty(dungeon_block, Boolean.FALSE));
	}

	@Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
		world.setBlockState(pos, state.withProperty(double_drop, Boolean.FALSE).withProperty(dungeon_block, Boolean.FALSE));
    }

	@Override
    public IBlockState getStateFromMeta(int meta)
    {
		return this.getDefaultState().withProperty(double_drop, (meta & 1) == 0).withProperty(dungeon_block, (meta & 2) == 0);
    }

	@Override
    public int getMetaFromState(IBlockState state)
    {
		int meta = 0;

		if (!state.getValue(double_drop))
		{
			meta |= 1;
		}

		if (!state.getValue(dungeon_block))
		{
			meta |= 2;
		}

		return meta;
    }

	@Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
	{
		DoubleDropHelper.dropBlock(player, state, pos, double_drop);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, double_drop, dungeon_block);
	}

}