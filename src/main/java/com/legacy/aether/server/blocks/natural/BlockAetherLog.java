package com.legacy.aether.server.blocks.natural;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.server.Aether;
import com.legacy.aether.server.blocks.BlocksAether;
import com.legacy.aether.server.blocks.util.EnumLogType;
import com.legacy.aether.server.blocks.util.IAetherMeta;
import com.legacy.aether.server.items.tools.ItemAetherTool;
import com.legacy.aether.server.items.tools.ItemGravititeTool;
import com.legacy.aether.server.items.tools.ItemSkyrootTool;
import com.legacy.aether.server.items.tools.ItemValkyrieTool;
import com.legacy.aether.server.items.util.EnumAetherToolType;
import com.legacy.aether.server.registry.creative_tabs.AetherCreativeTabs;

public class BlockAetherLog extends Block implements IAetherMeta
{

	public static final PropertyEnum<EnumLogType> wood_type = PropertyEnum.create("aether_logs", EnumLogType.class);

	public static final PropertyBool double_drop = PropertyBool.create(Aether.doubleDropNotifier());

	public BlockAetherLog() 
	{
        super(Material.WOOD);
		this.setHardness(2.0F);
        this.setSoundType(SoundType.WOOD);
		this.setCreativeTab(AetherCreativeTabs.blocks);
		this.setDefaultState(this.getDefaultState().withProperty(wood_type, EnumLogType.Skyroot).withProperty(double_drop, Boolean.TRUE));
	}

	@Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(wood_type, EnumLogType.getType(meta)).withProperty(double_drop, Boolean.FALSE);
    }

	@Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stackIn)
	{
        player.addStat(StatList.getBlockStats(state.getBlock()));
        player.addExhaustion(0.025F);

        int size = state.getValue(double_drop).equals(true) ? 2 : 1;

        ItemStack stack = player.inventory.getCurrentItem();

		IBlockState defaults = BlocksAether.aether_log.getDefaultState().withProperty(wood_type, EnumLogType.Skyroot);

        if (stack != null && stack.getItem() instanceof ItemAetherTool && ((ItemAetherTool)stack.getItem()).toolType == EnumAetherToolType.AXE)
        {
        	if (stack.getItem() instanceof ItemGravititeTool || stack.getItem() instanceof ItemValkyrieTool)
        	{
            	state.getBlock().dropBlockAsItem(player.worldObj, pos, state, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, player.getHeldItemMainhand()));
        	}
        	else if (stack.getItem() instanceof ItemSkyrootTool)
        	{
                for (int i = 0; i < size; ++i)
                {
            		spawnAsEntity(worldIn, pos, new ItemStack(defaults.getBlock()));
                }
        	}
        	else
        	{
        		spawnAsEntity(worldIn, pos, new ItemStack(defaults.getBlock()));
        	}
        }
        else
        {
    		spawnAsEntity(worldIn, pos, new ItemStack(defaults.getBlock()));
        }
	}

	@Override
	public String getMetaName(ItemStack stack) 
	{
		return ((EnumLogType)this.getStateFromMeta(stack.getItemDamage()).getValue(wood_type)).getName();
	}

	@Override
    public int damageDropped(IBlockState state)
    {
		return ((EnumLogType)state.getValue(wood_type)).getMeta();
    }

	@Override
	@SideOnly(Side.CLIENT)
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
    {
		for (int meta = 0; meta < EnumLogType.values().length; ++meta)
		{
			list.add(new ItemStack(this, 1, meta));
		}
    }

	@Override
    public IBlockState getStateFromMeta(int meta)
    {
		return this.getDefaultState().withProperty(wood_type, EnumLogType.getType(meta % 2)).withProperty(double_drop, Boolean.valueOf((meta % 4) == 0));
    }

	@Override
    public int getMetaFromState(IBlockState state)
    {
		int meta = 0;
		meta = meta | ((EnumLogType)state.getValue(wood_type)).getMeta();

		if (!((Boolean)state.getValue(double_drop)).booleanValue())
		{
			meta |= 4;
		}

		return meta;
    }

	@Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return true;
    }

	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {wood_type, double_drop});
    }

}