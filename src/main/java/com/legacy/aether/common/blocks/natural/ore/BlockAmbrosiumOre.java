package com.legacy.aether.common.blocks.natural.ore;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.legacy.aether.common.Aether;
import com.legacy.aether.common.items.ItemsAether;
import com.legacy.aether.common.items.util.DoubleDropHelper;
import com.legacy.aether.common.registry.creative_tabs.AetherCreativeTabs;

public class BlockAmbrosiumOre extends Block
{

	public static final PropertyBool double_drop = PropertyBool.create(Aether.doubleDropNotifier());

	public BlockAmbrosiumOre()
	{
		super(Material.ROCK);

		this.setHardness(3F);
		this.setResistance(5F);
		this.setSoundType(SoundType.STONE);
		this.setCreativeTab(AetherCreativeTabs.blocks);
		this.setDefaultState(this.getDefaultState().withProperty(double_drop, Boolean.TRUE));
	}

	@Override
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped((IBlockState)this.getBlockState().getValidStates().iterator().next(), random, fortune))
        {
            int i = random.nextInt(fortune + 2) - 1;

            if (i < 0)
            {
                i = 0;
            }

            return this.quantityDropped(random) * (i + 1);
        }
        else
        {
            return this.quantityDropped(random);
        }
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

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ItemsAether.ambrosium_shard;
    }

}