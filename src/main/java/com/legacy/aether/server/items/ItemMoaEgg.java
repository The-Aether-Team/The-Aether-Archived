package com.legacy.aether.server.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.legacy.aether.server.entities.passive.mountable.EntityMoa;
import com.legacy.aether.server.entities.util.MoaColor;
import com.legacy.aether.server.registry.creative_tabs.AetherCreativeTabs;

public class ItemMoaEgg extends Item
{

	protected ItemMoaEgg()
	{
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
		this.setCreativeTab(AetherCreativeTabs.misc);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if (!world.isRemote)
		{
			if (stack.getTagCompound() == null)
			{
				stack.setTagCompound(ItemMoaEgg.getStackFromColor(MoaColor.getRandomColor(world)).getTagCompound());
			}
		}
	}

	@Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if (playerIn.capabilities.isCreativeMode)
		{
			EntityMoa moa = new EntityMoa(worldIn, MoaColor.getColor(stack.getTagCompound().getInteger("color")));

			moa.moveToBlockPosAndAngles(pos.up(), 1.0F, 1.0F);
			moa.setPlayerGrown(true);

			if (!worldIn.isRemote)
			{
				worldIn.spawnEntityInWorld(moa);
			}
			
			return EnumActionResult.SUCCESS;
		}

        return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubItems(Item item, CreativeTabs tabs, List creativeList)
	{
		int meta = 0;
		
		for (MoaColor color : MoaColor.colors)
		{
			ItemStack stack = new ItemStack(item, 1, meta);
			NBTTagCompound tag = new NBTTagCompound();
			
			tag.setInteger("color", color.ID);
			tag.setBoolean("creativeSpawned", true);
			
			stack.setTagCompound(tag);
			creativeList.add(stack);
			
			meta++;
		}
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	public int getColorFromItemStack(ItemStack stack, int pass)
	{
		NBTTagCompound tag = stack.getTagCompound();

		if (tag != null)
		{
			MoaColor color = MoaColor.getColor(tag.getInteger("color"));
			
			return color.RGB;
		}

		return MoaColor.getColor(0).RGB;
	}

	public MoaColor getMoaColorFromItemStack(ItemStack stack)
	{
		NBTTagCompound tag = stack.getTagCompound();

		if (tag != null)
		{
			MoaColor color = MoaColor.getColor(tag.getInteger("color"));
			
			return color;
		}

		return MoaColor.getColor(0);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		NBTTagCompound tag = stack.getTagCompound();

		if (tag != null && stack.getTagCompound().hasKey("color"))
		{
			MoaColor color = MoaColor.getColor(tag.getInteger("color"));

			return color.name + " Moa Egg";
		}

		return super.getUnlocalizedName();
	}

	@Override
    public String getItemStackDisplayName(ItemStack stack)
    {
    	return getUnlocalizedName(stack);
    }

	public static ItemStack getStackFromColor(MoaColor color)
	{
		ItemStack stack = new ItemStack(ItemsAether.moa_egg);

		NBTTagCompound tag = new NBTTagCompound();

		tag.setInteger("color", color.ID);
		tag.setBoolean("creativeSpawned", true);

		stack.setTagCompound(tag);

		return stack;
	}

}