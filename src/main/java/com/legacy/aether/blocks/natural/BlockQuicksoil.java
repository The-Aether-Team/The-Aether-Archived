package com.legacy.aether.blocks.natural;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.legacy.aether.Aether;
import com.legacy.aether.items.util.DoubleDropHelper;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class BlockQuicksoil extends Block
{

	public static final PropertyBool double_drop = PropertyBool.create(Aether.doubleDropNotifier());

	public BlockQuicksoil()
	{
		super(Material.SAND);

		this.setHardness(0.5F);
		this.setDefaultSlipperiness(1.1F);
		this.setSoundType(SoundType.SAND);
		this.setCreativeTab(AetherCreativeTabs.blocks);
		this.setDefaultState(this.getDefaultState().withProperty(double_drop, Boolean.TRUE));
	}

	@Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
		world.setBlockState(pos, state.withProperty(double_drop, Boolean.FALSE));
    }

	@Override
    public IBlockState getStateFromMeta(int meta)
    {
		return this.getDefaultState().withProperty(double_drop, Boolean.valueOf(meta == 0));
    }

	@Override
    public int getMetaFromState(IBlockState state)
    {
		int meta = 0;

		if (!((Boolean)state.getValue(double_drop)).booleanValue())
		{
			meta |= 1;
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
		return new BlockStateContainer(this, new IProperty[] {double_drop});
	}

}