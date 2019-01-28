package com.legacy.aether.blocks.dungeon;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.legacy.aether.blocks.BlocksAether;
import com.legacy.aether.blocks.util.EnumStoneType;
import com.legacy.aether.blocks.util.IAetherMeta;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class BlockDungeonBase extends Block implements IAetherMeta
{

	public static final PropertyEnum<EnumStoneType> dungeon_stone = PropertyEnum.create("aether_legacy_dungeon_stone", EnumStoneType.class);

	public BlockDungeonBase(boolean isLocked) 
	{
		super(Material.ROCK);

		if (isLocked)
		{
			this.setResistance(6000000.0F);
			this.setBlockUnbreakable();
		}

		this.setSoundType(SoundType.STONE);
		this.setHardness(isLocked ? -1F : 0.5F);
		this.setCreativeTab(isLocked ? null : AetherCreativeTabs.blocks);
		this.setDefaultState(this.getDefaultState().withProperty(dungeon_stone, EnumStoneType.Carved));
	}

	@Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
    	int meta = this.getMetaFromState(state);

        if (state.getBlock() != this)
        {
            return state.getLightValue(world, pos);
        }

        if (meta == 1 || meta == 3 || meta == 5)
        {
        	return (int)(15.0F * 0.75f);
        }

        return 0;
    }

	@Override
    public ItemStack getPickBlock(IBlockState stateIn, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		Block newBlock = BlocksAether.dungeon_block.getDefaultState().withProperty(dungeon_stone, ((EnumStoneType)state.getValue(dungeon_stone))).getBlock();

		if (block == BlocksAether.locked_dungeon_block || block == BlocksAether.dungeon_trap)
		{
			return new ItemStack(newBlock, 1, damageDropped(state));
		}

    	return new ItemStack(newBlock, 1, damageDropped(state));
    }

	@Override
	public String getMetaName(ItemStack stack)
	{
		return ((EnumStoneType)this.getStateFromMeta(stack.getItemDamage()).getValue(dungeon_stone)).getName();
	}

	@Override
    public int damageDropped(IBlockState state)
    {
        return ((EnumStoneType)state.getValue(dungeon_stone)).getMeta();
    }

	@Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (int j = 0; j < EnumStoneType.values().length; ++j)
        {
        	EnumStoneType enumdyecolor = EnumStoneType.values()[j];

            list.add(new ItemStack(this, 1, enumdyecolor.getMeta()));
        }
    }

	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(dungeon_stone, EnumStoneType.getType(meta));
    }

	@Override
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumStoneType)state.getValue(dungeon_stone)).getMeta();
    }

	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {dungeon_stone});
    }

}