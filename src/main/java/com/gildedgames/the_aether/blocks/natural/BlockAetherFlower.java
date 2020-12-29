package com.gildedgames.the_aether.blocks.natural;

import com.gildedgames.the_aether.world.AetherWorld;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.the_aether.blocks.BlocksAether;

public class BlockAetherFlower extends BlockBush
{

    public AxisAlignedBB FLOWER_AABB = new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 0.6000000238418579D, 0.699999988079071D);

	public BlockAetherFlower() 
	{
		this.setHardness(0.0F);
		this.setTickRandomly(true);
		this.setSoundType(SoundType.PLANT);
		this.FLOWER_AABB = new AxisAlignedBB(0.5F - 0.2F, 0.0F, 0.5F - 0.2F, 0.5F + 0.2F, 0.2F * 3.0F, 0.5F + 0.2F);
	}

    @SideOnly(Side.CLIENT)
    public Block.EnumOffsetType getOffsetType()
    {
        return Block.EnumOffsetType.XZ;
    }

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return FLOWER_AABB;
    }

	@Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
        IBlockState soil = world.getBlockState(pos.down());

		return AetherWorld.viableSoilBlocks.contains(soil.getBlock()) || super.canPlaceBlockAt(world, pos);
	}

	@Override
	public boolean canBlockStay(World world, BlockPos pos, IBlockState state)
	{
		Block soil = world.getBlockState(pos.down()).getBlock();
		return (world.getLight(pos) >= 8 || world.canBlockSeeSky(pos)) && (soil != null && this.canPlaceBlockAt(world, pos));
	}

}