package com.gildedgames.the_aether.items.accessories;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.api.accessories.AccessoryType;
import net.minecraft.block.BlockCauldron;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAccessoryDyed extends ItemAccessory {

	private IIcon overlayIcon;
	private IIcon emptySlotIcon;

	public ItemAccessoryDyed(AccessoryType type) {
		super(type);
		this.texture = Aether.locate("textures/armor/accessory_leather.png");
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, int x, int y, int z, int facing, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = playerIn.getHeldItem();

		if (worldIn.getBlock(x, y, z) == Blocks.cauldron) {
			if (heldItem != null && heldItem.getItem() instanceof ItemAccessoryDyed) {
				int j1 = BlockCauldron.func_150027_b(worldIn.getBlockMetadata(x, y, z));

				if (j1 > 0) {
					((ItemAccessoryDyed) heldItem.getItem()).removeColor(heldItem);

					Blocks.cauldron.func_150024_a(worldIn, x, y, z, j1 - 1);

					return true;
				}
			}
		}

		return super.onItemUse(stack, playerIn, worldIn, x, y, z, facing, hitX, hitY, hitZ);
	}

//	public int getStackColor(ItemStack stack, int meta) {
//		NBTTagCompound nbttagcompound = stack.getTagCompound();
//
//		if (nbttagcompound == null) {
//			return super.getColor();
//		}
//
//		NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
//
//		return nbttagcompound1 == null ? super.getColor() : (nbttagcompound1.hasKey("color", 3) ? nbttagcompound1.getInteger("color") : super.getColor());
//	}



	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int meta)
	{
		if (meta > 0)
		{
			return 16777215;
		}
		else
		{
			int j = this.getColor(stack);

			if (j < 0)
			{
				j = 16777215;
			}

			return j;
		}
	}

	public boolean hasColor(ItemStack p_82816_1_)
	{
		return (p_82816_1_.hasTagCompound() && (p_82816_1_.getTagCompound().hasKey("display", 10) && p_82816_1_.getTagCompound().getCompoundTag("display").hasKey("color", 3)));
	}

	public int getColor(ItemStack p_82814_1_)
	{
		NBTTagCompound nbttagcompound = p_82814_1_.getTagCompound();

		if (nbttagcompound == null)
		{
			return 10511680;
		}
		else
		{
			NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
			return nbttagcompound1 == null ? 10511680 : (nbttagcompound1.hasKey("color", 3) ? nbttagcompound1.getInteger("color") : 10511680);
		}
	}

	public void removeColor(ItemStack p_82815_1_)
	{
		NBTTagCompound nbttagcompound = p_82815_1_.getTagCompound();

		if (nbttagcompound != null)
		{
			NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

			if (nbttagcompound1.hasKey("color"))
			{
				nbttagcompound1.removeTag("color");
			}
		}
	}

	public void setColorTag(ItemStack p_82813_1_, int p_82813_2_)
	{
		NBTTagCompound nbttagcompound = p_82813_1_.getTagCompound();

		if (nbttagcompound == null)
		{
			nbttagcompound = new NBTTagCompound();
			p_82813_1_.setTagCompound(nbttagcompound);
		}

		NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");

		if (!nbttagcompound.hasKey("display", 10))
		{
			nbttagcompound.setTag("display", nbttagcompound1);
		}

		nbttagcompound1.setInteger("color", p_82813_2_);
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registry)
	{
		super.registerIcons(registry);

		this.overlayIcon = registry.registerIcon(Aether.find("accessories/leather_gloves_overlay"));
		this.emptySlotIcon = registry.registerIcon(Aether.find("accessories/leather_gloves"));
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int p_77618_2_)
	{
		return p_77618_2_ == 1 ? this.overlayIcon : super.getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_);
	}


	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}
}