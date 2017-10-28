package com.legacy.aether.common.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import com.legacy.aether.api.AetherRegistry;
import com.legacy.aether.api.moa.AetherMoaType;
import com.legacy.aether.common.entities.passive.mountable.EntityMoa;
import com.legacy.aether.common.registry.creative_tabs.AetherCreativeTabs;

public class ItemMoaEgg extends Item
{

	public ItemMoaEgg()
	{
		this.setMaxStackSize(1);
		this.setCreativeTab(AetherCreativeTabs.misc);
	}

	@Override
    public CreativeTabs[] getCreativeTabs()
    {
    	return CreativeTabs.CREATIVE_TAB_ARRAY;
    }

	@Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		if (playerIn.capabilities.isCreativeMode)
		{
			EntityMoa moa = new EntityMoa(worldIn, AetherRegistry.getInstance().getMoaType(stack.getTagCompound().getInteger("typeId")));

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
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
	{
		for (int moaTypeSize = 0; moaTypeSize < AetherRegistry.getInstance().getMoaTypeSize(); ++moaTypeSize)
		{
			ItemStack stack = new ItemStack(this);
			NBTTagCompound compound = new NBTTagCompound();
			AetherMoaType moaType = AetherRegistry.getInstance().getMoaType(moaTypeSize);

			if (moaType.getCreativeTab() == tab)
			{
				compound.setInteger("typeId", moaTypeSize);
				stack.setTagCompound(compound);

				subItems.add(stack);
			}
		}
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	public int getColorFromItemStack(ItemStack stack)
	{
		NBTTagCompound tag = stack.getTagCompound();

		if (tag != null)
		{
			AetherMoaType moaType = AetherRegistry.getInstance().getMoaType(tag.getInteger("typeId"));

			return moaType.getMoaEggColor();
		}

		return AetherRegistry.getInstance().getMoaType(0).getMoaEggColor();
	}

	public AetherMoaType getMoaTypeFromItemStack(ItemStack stack)
	{
		NBTTagCompound tag = stack.getTagCompound();

		if (tag != null)
		{
			AetherMoaType moaType = AetherRegistry.getInstance().getMoaType(tag.getInteger("typeId"));

			return moaType;
		}

		return AetherRegistry.getInstance().getMoaType(0);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		NBTTagCompound tag = stack.getTagCompound();

		if (tag != null && stack.getTagCompound().hasKey("typeId"))
		{
			AetherMoaType moaType = AetherRegistry.getInstance().getMoaType(tag.getInteger("typeId"));

			return new TextComponentTranslation("item." + moaType.getRegistryName().getResourcePath().replace(" ", "_").toLowerCase() + "_moa_egg.name",  new Object[0]).getFormattedText();
		}

		return super.getUnlocalizedName();
	}

	@Override
    public String getItemStackDisplayName(ItemStack stack)
    {
    	return getUnlocalizedName(stack);
    }

	public static ItemStack getStackFromType(AetherMoaType type)
	{
		ItemStack stack = new ItemStack(ItemsAether.moa_egg);

		NBTTagCompound tag = new NBTTagCompound();

		tag.setInteger("typeId", AetherRegistry.getInstance().getMoaTypeId(type));

		stack.setTagCompound(tag);

		return stack;
	}

}