package com.legacy.aether.common.items.tools;

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
import net.minecraft.util.NonNullList;
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
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
    	for (int meta = 0; meta < EnumSkyrootBucketType.values().length; ++meta)
    	{
    		subItems.add(new ItemStack(this, 1, meta));
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
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack heldItem = player.getHeldItem(hand);
		int meta = heldItem.getItemDamage();

		/* Remedy and Poison Bucket checker */
		if (EnumSkyrootBucketType.getType(meta) != EnumSkyrootBucketType.Water && EnumSkyrootBucketType.getType(meta) != EnumSkyrootBucketType.Empty)
		{
			player.setActiveHand(hand);
			return new ActionResult<ItemStack>(EnumActionResult.PASS, heldItem);
		}

		/* Water and Empty Bucket Process */
		boolean isEmpty = EnumSkyrootBucketType.getType(meta) == EnumSkyrootBucketType.Empty;

		RayTraceResult movingobjectposition = this.rayTrace(world, player, isEmpty);
        ActionResult<ItemStack> ret = ForgeEventFactory.onBucketUse(player, world, heldItem, movingobjectposition);

        if (ret != null) 
        {
        	return ret;
        }

        if (movingobjectposition == null)
        {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, heldItem);
        }

        else if (movingobjectposition.typeOfHit != RayTraceResult.Type.BLOCK)
        {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, heldItem);
        }
        else
        {
            BlockPos blockpos = movingobjectposition.getBlockPos();

            if (!world.isBlockModifiable(player, blockpos))
            {
                return new ActionResult<ItemStack>(EnumActionResult.FAIL, heldItem);
            }
            else if (isEmpty)
            {
                if (!player.canPlayerEdit(blockpos.offset(movingobjectposition.sideHit), movingobjectposition.sideHit, heldItem))
                {
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, heldItem);
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

                        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, this.fillBucket(heldItem, player, ItemsAether.skyroot_bucket));
                    }
                    else
                    {
                        return new ActionResult<ItemStack>(EnumActionResult.FAIL, heldItem);
                    }
                }
            }
            else
            {
                boolean flag1 = world.getBlockState(blockpos).getBlock().isReplaceable(world, blockpos);
                BlockPos blockpos1 = flag1 && movingobjectposition.sideHit == EnumFacing.UP ? blockpos : blockpos.offset(movingobjectposition.sideHit);

                if (!player.canPlayerEdit(blockpos1, movingobjectposition.sideHit, heldItem))
                {
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, heldItem);
                }
                else if (this.tryPlaceContainedLiquid(player, world, heldItem, blockpos1))
                {
                	player.addStat(StatList.getObjectUseStats(this));
                    return !player.capabilities.isCreativeMode ? new ActionResult<ItemStack>(EnumActionResult.SUCCESS, new ItemStack(ItemsAether.skyroot_bucket)) : new ActionResult<ItemStack>(EnumActionResult.SUCCESS, heldItem);
                }
                else
                {
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, heldItem);
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
        else if (emptyBuckets.getCount() - 1 <= 0)
        {
            return new ItemStack(fullBucket, 1, 1);
        }
        else
        {
            if (!player.inventory.addItemStackToInventory(new ItemStack(fullBucket)))
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
    		return this.onBucketUsed(stack, worldIn, (EntityPlayer) entityLiving);
    	}

    	return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

	public ItemStack onBucketUsed(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		PlayerAether player = PlayerAether.get(entityplayer);
		int meta = itemstack.getItemDamage();

		if (!entityplayer.capabilities.isCreativeMode)
		{
			itemstack.shrink(1);
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

		return itemstack.getCount() <= 0 ? new ItemStack(this, 1, 0) : itemstack;
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