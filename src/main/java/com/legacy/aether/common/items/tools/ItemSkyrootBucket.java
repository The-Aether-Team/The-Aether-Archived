package com.legacy.aether.common.items.tools;

import java.util.List;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.common.items.ItemsAether;
import com.legacy.aether.common.items.util.EnumSkyrootBucketType;
import com.legacy.aether.common.items.util.FluidSkyrootBucketWrapper;
import com.legacy.aether.common.player.PlayerAether;
import com.legacy.aether.common.registry.creative_tabs.AetherCreativeTabs;

public class ItemSkyrootBucket extends Item
{

	public ItemSkyrootBucket()
	{
		super();

		this.setHasSubtypes(true);
		this.setContainerItem(ItemsAether.skyroot_bucket);
		this.setCreativeTab(AetherCreativeTabs.misc);
	}

	@SideOnly(Side.CLIENT)
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public void getSubItems(Item item, CreativeTabs tab, List list)
    {
    	for (int meta = 0; meta < EnumSkyrootBucketType.values().length; ++meta)
    	{
        	list.add(new ItemStack(this, 1, meta));
    	}
    }

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return stack.getMetadata() == 3 ? EnumRarity.RARE : super.getRarity(stack);
    }

	@Override
    public int getItemStackLimit(ItemStack stack)
    {
    	return EnumSkyrootBucketType.getType(stack.getItemDamage()) == EnumSkyrootBucketType.Empty ? 16 : 1;
    }

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		int meta = itemstack.getItemDamage();

		return this.getUnlocalizedName() + "_" + EnumSkyrootBucketType.getType(meta).toString();
	}

	@Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
	{
		int meta = stack.getItemDamage();

		/* Remedy and Poison Bucket checker */
		if (EnumSkyrootBucketType.getType(meta) != EnumSkyrootBucketType.Water && EnumSkyrootBucketType.getType(meta) != EnumSkyrootBucketType.Empty)
		{
			player.setActiveHand(hand);
			return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
		}

		/* Water and Empty Bucket Process */
		boolean isEmpty = EnumSkyrootBucketType.getType(meta) == EnumSkyrootBucketType.Empty;

		RayTraceResult movingobjectposition = this.rayTrace(world, player, isEmpty);
        ActionResult<ItemStack> ret = ForgeEventFactory.onBucketUse(player, world, stack, movingobjectposition);

        if (ret != null) 
        {
        	return ret;
        }

        if (movingobjectposition == null)
        {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
        }

        else if (movingobjectposition.typeOfHit != RayTraceResult.Type.BLOCK)
        {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
        }
        else
        {
            BlockPos blockpos = movingobjectposition.getBlockPos();

            if (!world.isBlockModifiable(player, blockpos))
            {
                return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
            }
            else if (isEmpty)
            {
                if (!player.canPlayerEdit(blockpos.offset(movingobjectposition.sideHit), movingobjectposition.sideHit, stack))
                {
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
                }
                else
                {
                    IBlockState iblockstate = world.getBlockState(blockpos);
                    Material material = iblockstate.getMaterial();

                    if (material == Material.WATER && ((Integer)iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0)
                    {
                    	world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
                    	player.addStat(StatList.getObjectUseStats(this));
                        player.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);

                        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, this.fillBucket(stack, player, ItemsAether.skyroot_bucket));
                    }
                    else
                    {
                        return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
                    }
                }
            }
            else
            {
                boolean flag1 = world.getBlockState(blockpos).getBlock().isReplaceable(world, blockpos);
                BlockPos blockpos1 = flag1 && movingobjectposition.sideHit == EnumFacing.UP ? blockpos : blockpos.offset(movingobjectposition.sideHit);

                if (!player.canPlayerEdit(blockpos1, movingobjectposition.sideHit, stack))
                {
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
                }
                else if (this.tryPlaceContainedLiquid(player, world, stack, blockpos1))
                {
                	player.addStat(StatList.getObjectUseStats(this));
                    return !player.capabilities.isCreativeMode ? new ActionResult<ItemStack>(EnumActionResult.SUCCESS, new ItemStack(ItemsAether.skyroot_bucket)) : new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
                }
                else
                {
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
                }
            }
        }
	}

    private ItemStack fillBucket(ItemStack emptyBuckets, EntityPlayer player, Item fullBucket)
    {
        if (player.capabilities.isCreativeMode)
        {
            return emptyBuckets;
        }
        else if (--emptyBuckets.stackSize <= 0)
        {
            return new ItemStack(fullBucket, 1, 1);
        }
        else
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(fullBucket, 1, 1)))
            {
                player.dropItem(new ItemStack(fullBucket, 1, 1), false);
            }

            return emptyBuckets;
        }
    }

    
	public boolean tryPlaceContainedLiquid(EntityPlayer player, World world, ItemStack stack, BlockPos pos)
	{
		if (EnumSkyrootBucketType.getType(stack.getItemDamage()) != EnumSkyrootBucketType.Water)
		{
			return false;
		}
		else
		{
			Material material = world.getBlockState(pos).getMaterial();
			boolean flag = !material.isSolid();

			if (!world.isAirBlock(pos) && !flag)
			{
				return false;
			}
			else
			{
				if (world.provider.doesWaterVaporize())
				{
					world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

					for (int l = 0; l < 8; ++l)
					{
						world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double) pos.getX() + Math.random(), (double) pos.getY() + Math.random(), (double) pos.getZ() + Math.random(), 0.0D, 0.0D, 0.0D);
					}
				}
				else
				{
					if (!world.isRemote && flag && !material.isLiquid())
					{
						world.getBlockState(pos).getBlock().breakBlock(world, pos, world.getBlockState(pos));
					}

					player.playSound(SoundEvents.ITEM_BUCKET_EMPTY, 1.0F, 1.0F);
					world.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState(), 11);
				}

				return true;
			}
		}
	}

    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
    	if (entityLiving instanceof EntityPlayer)
    	{
    		return this.onEaten(stack, worldIn, (EntityPlayer) entityLiving);
    	}

    	return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

	public ItemStack onEaten(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		PlayerAether player = PlayerAether.get(entityplayer);
		int meta = itemstack.getItemDamage();

		if (!entityplayer.capabilities.isCreativeMode)
		{
			--itemstack.stackSize;
		}

		if (EnumSkyrootBucketType.getType(meta) == EnumSkyrootBucketType.Poison)
		{
			player.afflictPoison();
		}
		else if (EnumSkyrootBucketType.getType(meta) == EnumSkyrootBucketType.Remedy)
		{
			player.attainCure(200);
		}
		else if (EnumSkyrootBucketType.getType(meta) == EnumSkyrootBucketType.Milk)
		{
	        if (!world.isRemote)
	        {
	        	entityplayer.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
	        }
		}

		return itemstack.stackSize <= 0 ? new ItemStack(this, 1, 0) : itemstack;
	}

	public int getMaxItemUseDuration(ItemStack itemstack)
	{
		int meta = itemstack.getItemDamage();

		if (EnumSkyrootBucketType.getType(meta) != EnumSkyrootBucketType.Water)
		{
			return 32;
		}
		else
		{
			return 0;
		}
	}

	public EnumAction getItemUseAction(ItemStack itemstack)
	{
		int meta = itemstack.getItemDamage();

		if (EnumSkyrootBucketType.getType(meta) != EnumSkyrootBucketType.Water)
		{
			return EnumAction.DRINK;
		}
		else
		{
			return EnumAction.NONE;
		}
	}

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) 
    {
        return new FluidSkyrootBucketWrapper(stack);
    }

}