package com.gildedgames.the_aether.blocks.natural;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.gildedgames.the_aether.entities.particles.ParticleGoldenOakLeaves;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.block.Block;
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
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.gildedgames.the_aether.blocks.BlocksAether;
import com.gildedgames.the_aether.blocks.util.EnumLeafType;
import com.gildedgames.the_aether.blocks.util.IAetherMeta;

public class BlockAetherLeaves extends BlockLeaves implements IAetherMeta
{

	public static final PropertyEnum<EnumLeafType> leaf_type = PropertyEnum.create("aether_leaves", EnumLeafType.class);

	int[] surroundings;
	int startMeta;

	public BlockAetherLeaves(EnumLeafType startType)
	{
		super();

		this.startMeta = startType.getMeta();
		this.setHardness(0.2F);
		this.setLightOpacity(1);
		this.setCreativeTab(AetherCreativeTabs.blocks);
        this.setDefaultState(this.getDefaultState().withProperty(leaf_type, EnumLeafType.Green).withProperty(CHECK_DECAY, true).withProperty(DECAYABLE, true));
	}

	@Override
	public String getMetaName(ItemStack stack)
	{
		return this.getStateFromMeta(stack.getItemDamage()).getValue(leaf_type).getName();
	}

	@Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setGraphicsLevel(boolean fancy)
    {
        this.leavesFancy = Minecraft.getMinecraft().gameSettings.fancyGraphics;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return Minecraft.getMinecraft().gameSettings.fancyGraphics ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(final IBlockState blockState, final IBlockAccess blockAccess, final BlockPos pos, final EnumFacing side)
    {
        return (Minecraft.getMinecraft().gameSettings.fancyGraphics || blockAccess.getBlockState(pos.offset(side)).getBlock() != this);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getStateFromMeta(meta).withProperty(DECAYABLE, false).withProperty(CHECK_DECAY, false);
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
			if (state.getValue(leaf_type) == EnumLeafType.Golden)
			{
				for (int ammount = 0; ammount < 4; ammount++)
				{
					double d = pos.getX() + (random.nextFloat() - 0.5D) * 10;
					double d1 = pos.getY() + (random.nextFloat() - 0.5D) * 10;
					double d2 = pos.getZ() + (random.nextFloat() - 0.5D) * 10;
					double d3 = (random.nextFloat() - 0.5D) * 0.5D;
					double d4 = (random.nextFloat() - 0.5D) * 0.5D;
					double d5 = (random.nextFloat() - 0.5D) * 0.5D;

					ParticleGoldenOakLeaves obj = new ParticleGoldenOakLeaves(world, d, d1, d2, d3, d4, d5);
					FMLClientHandler.instance().getClient().effectRenderer.addEffect(obj);
				}
			}
		}
	}

	@Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list)
    {
        for (int j = this.startMeta; j < Math.min(this.startMeta+4, EnumLeafType.lookup.length); ++j)
        {
        	EnumLeafType leafType = EnumLeafType.lookup[j];

            list.add(new ItemStack(this, 1, leafType.getMeta() - this.startMeta));
        }
    }

	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(leaf_type,  EnumLeafType.getType((meta & 3) + startMeta)).withProperty(DECAYABLE, (meta & 4) == 0).withProperty(CHECK_DECAY, (meta & 8) > 0);
    }

    public int getMetaFromState(IBlockState state)
    {
        int i = 0;

    	int adjusted = state.getValue(leaf_type).getMeta() - this.startMeta;
    	if (adjusted < 0) adjusted = 0;

        i = i | adjusted;

        if (!state.getValue(DECAYABLE))
        {
            i |= 4;
        }

        if (state.getValue(CHECK_DECAY))
        {
            i |= 8;
        }

        return i;
    }

	@Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {leaf_type, DECAYABLE, CHECK_DECAY});
    }

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
    	Block saplingBlock = null;

    	switch (state.getValue(leaf_type))
		{
			case Green:
				saplingBlock = BlocksAether.skyroot_sapling;
				break;

			case Golden:
				saplingBlock = BlocksAether.golden_oak_sapling;
				break;

			case Blue:
				saplingBlock = BlocksAether.blue_sapling;
				break;

			case DarkBlue:
				saplingBlock = BlocksAether.dark_blue_sapling;
				break;

			case Purple:
				saplingBlock = BlocksAether.purple_sapling;
				break;
		}

		return Item.getItemFromBlock(saplingBlock);
    }

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
	{
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		IBlockState state = world.getBlockState(pos);

		if (state.getValue(leaf_type) != null)
		{
			list.add(new ItemStack(this, 1, state.getValue(leaf_type).getMeta()));
		}

		return list;
	}

	@Override
    protected ItemStack getSilkTouchDrop(IBlockState state)
    {
        Item item = Item.getItemFromBlock(this);

        return new ItemStack(item, 1, state.getValue(leaf_type).getMeta());
    }

	@Override
	public EnumType getWoodType(int meta)
	{
		return null;
	}

	@Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(state));
    }

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            if (state.getValue(CHECK_DECAY) && state.getValue(DECAYABLE))
            {
                int k = pos.getX();
                int l = pos.getY();
                int i1 = pos.getZ();
                int loadedRange = 7;
                int checkRange = 6;

                if (this.surroundings == null)
                {
                    this.surroundings = new int[32768]; // 2^15
                }

                if (worldIn.isAreaLoaded(new BlockPos(k - loadedRange, l - loadedRange, i1 - loadedRange), new BlockPos(k + loadedRange, l + loadedRange, i1 + loadedRange)))
                {
                    BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                    for (int x = -checkRange; x <= checkRange; ++x)
                    {
                        for (int y = -checkRange; y <= checkRange; ++y)
                        {
                            for (int z = -checkRange; z <= checkRange; ++z)
                            {
                                IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos.setPos(k + x, l + y, i1 + z));
                                Block block = iblockstate.getBlock();

                                if (!block.canSustainLeaves(iblockstate, worldIn, blockpos$mutableblockpos.setPos(k + x, l + y, i1 + z)))
                                {
                                    if (block.isLeaves(iblockstate, worldIn, blockpos$mutableblockpos.setPos(k + x, l + y, i1 + z)))
                                    {
                                    	// if leaves -2
                                        this.surroundings[(x + 16) * 1024 + (y + 16) * 32 + z + 16] = -2;
                                    }
                                    else
                                    {
                                    	// if not sustain -1
                                        this.surroundings[(x + 16) * 1024 + (y + 16) * 32 + z + 16] = -1;
                                    }
                                }
                                else
                                {
                                	// if sustain 0
                                    this.surroundings[(x + 16) * 1024 + (y + 16) * 32 + z + 16] = 0;
                                }
                            }
                        }
                    }

                    for (int i3 = 1; i3 <= checkRange; ++i3)
                    {
                        for (int x = -checkRange; x <= checkRange; ++x)
                        {
                            for (int y = -checkRange; y <= checkRange; ++y)
                            {
                                for (int z = -checkRange; z <= checkRange; ++z)
                                {
                                    if (this.surroundings[(x + 16) * 1024 + (y + 16) * 32 + z + 16] == i3 - 1)
                                    {
                                        if (this.surroundings[(x + 16 - 1) * 1024 + (y + 16) * 32 + z + 16] == -2)
                                        {
                                            this.surroundings[(x + 16 - 1) * 1024 + (y + 16) * 32 + z + 16] = i3;
                                        }

                                        if (this.surroundings[(x + 16 + 1) * 1024 + (y + 16) * 32 + z + 16] == -2)
                                        {
                                            this.surroundings[(x + 16 + 1) * 1024 + (y + 16) * 32 + z + 16] = i3;
                                        }

                                        if (this.surroundings[(x + 16) * 1024 + (y + 16 - 1) * 32 + z + 16] == -2)
                                        {
                                            this.surroundings[(x + 16) * 1024 + (y + 16 - 1) * 32 + z + 16] = i3;
                                        }

                                        if (this.surroundings[(x + 16) * 1024 + (y + 16 + 1) * 32 + z + 16] == -2)
                                        {
                                            this.surroundings[(x + 16) * 1024 + (y + 16 + 1) * 32 + z + 16] = i3;
                                        }

                                        if (this.surroundings[(x + 16) * 1024 + (y + 16) * 32 + (z + 16 - 1)] == -2)
                                        {
                                            this.surroundings[(x + 16) * 1024 + (y + 16) * 32 + (z + 16 - 1)] = i3;
                                        }

                                        if (this.surroundings[(x + 16) * 1024 + (y + 16) * 32 + z + 16 + 1] == -2)
                                        {
                                            this.surroundings[(x + 16) * 1024 + (y + 16) * 32 + z + 16 + 1] = i3;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                int l2 = this.surroundings[16912];

                if (l2 >= 0)
                {
                    worldIn.setBlockState(pos, state.withProperty(CHECK_DECAY, false), 4);
                }
                else
                {
                	this.dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
                    worldIn.setBlockToAir(pos);
                }
            }
        }
    }
}