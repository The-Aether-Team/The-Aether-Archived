package com.legacy.aether.blocks.natural;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.blocks.util.EnumHolidayType;
import com.legacy.aether.blocks.util.EnumLeafType;
import com.legacy.aether.blocks.util.IAetherMeta;
import com.legacy.aether.entities.particles.ParticleHolidayLeaves;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class BlockHolidayLeaves extends BlockLeaves implements IAetherMeta
{

	public static final PropertyEnum<EnumHolidayType> leaf_type = PropertyEnum.create("holiday_leaves", EnumHolidayType.class);

	public BlockHolidayLeaves() 
	{
		super();

		this.setHardness(0.2F);
		this.setLightOpacity(1);
		this.setCreativeTab(AetherCreativeTabs.blocks);
        this.setDefaultState(this.getDefaultState().withProperty(leaf_type, EnumHolidayType.Holiday_Leaves).withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
	}

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getStateFromMeta(meta).withProperty(DECAYABLE, false).withProperty(CHECK_DECAY, false);
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
    	return true;
    }

	@Override
	public String getMetaName(ItemStack stack) 
	{
		return ((EnumHolidayType)this.getStateFromMeta(stack.getItemDamage()).getValue(leaf_type)).getName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random)
	{
		super.randomDisplayTick(state, world, pos, random);

		if (!world.isRemote)
		{
			return;
		}

		if (Minecraft.getMinecraft().gameSettings.particleSetting != 2)
		{
	        if (random.nextInt(10) == 0)
	        {
	            for (int l = 0; l < 15; ++l)
	            {
	                double d = (double)pos.getX() + ((double)random.nextFloat() - 0.5D) * 6.0D;
	                double d1 = (double)pos.getY() + ((double)random.nextFloat() - 0.5D) * 6.0D;
	                double d2 = (double)pos.getZ() + ((double)random.nextFloat() - 0.5D) * 6.0D;
	                double d3 = ((double)random.nextFloat() - 0.5D) * 0.5D;
	                double d4 = ((double)random.nextFloat() - 0.5D) * 0.5D;
	                double d5 = ((double)random.nextFloat() - 0.5D) * 0.5D;

	                FMLClientHandler.instance().getClient().effectRenderer.addEffect(new ParticleHolidayLeaves(world, d, d1, d2, d3, d4, d5));
	            }
	        }
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list)
    {
        for (int j = 0; j < EnumLeafType.values().length; ++j)
        {
        	EnumLeafType enumdyecolor = EnumLeafType.values()[j];

            list.add(new ItemStack(itemIn, 1, enumdyecolor.getMeta()));
        }
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(leaf_type,  EnumHolidayType.getType(meta % 2)).withProperty(DECAYABLE, Boolean.valueOf((meta & 2) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 4) > 0));
    }

    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | ((EnumHolidayType)state.getValue(leaf_type)).getMeta();

        if (!((Boolean)state.getValue(DECAYABLE)).booleanValue())
        {
            i |= 2;
        }

        if (((Boolean)state.getValue(CHECK_DECAY)).booleanValue())
        {
            i |= 4;
        }

        return i;
    }

	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {leaf_type, DECAYABLE, CHECK_DECAY});
    }

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) 
	{
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();

		list.add(new ItemStack(this));

		return list;
	}

	@Override
	public EnumType getWoodType(int meta)
	{
		return null;
	}

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        List<ItemStack> ret = new ArrayList<ItemStack>();

        return ret;
    }

}