package com.gildedgames.the_aether.items;

import java.util.List;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.moa.AetherMoaType;
import com.gildedgames.the_aether.entities.passive.mountable.EntityMoa;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMoaEgg extends Item {

	@SideOnly(Side.CLIENT)
	private IIcon spotIcon;

	public ItemMoaEgg() {
		this.setMaxStackSize(1);
		this.setCreativeTab(AetherCreativeTabs.misc);
		this.setTextureName(Aether.find("misc/egg/moa_egg"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int p_77618_1_, int p_77618_2_) {
		return p_77618_2_ > 0 ? this.spotIcon : super.getIconFromDamageForRenderPass(p_77618_1_, p_77618_2_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registry) {
		super.registerIcons(registry);

		this.spotIcon = registry.registerIcon(Aether.find("misc/egg/moa_egg_spot"));
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return CreativeTabs.creativeTabArray;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int facing, float hitX, float hitY, float hitZ) {
		if (player.capabilities.isCreativeMode) {
			EntityMoa moa = new EntityMoa(world, AetherAPI.instance().getMoaType(stack.getTagCompound().getInteger("typeId")));

			moa.setPositionAndRotation(x, y + 1, z, 1.0F, 1.0F);
			moa.setPlayerGrown(true);

			if (!world.isRemote) {
				world.spawnEntityInWorld(moa);
			}

			return true;
		}

		return super.onItemUse(stack, player, world, x, y, z, facing, hitX, hitY, hitZ);
	}

	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		for (int moaTypeSize = 0; moaTypeSize < AetherAPI.instance().getMoaTypeSize(); ++moaTypeSize) {
			ItemStack stack = new ItemStack(this);
			NBTTagCompound compound = new NBTTagCompound();
			AetherMoaType moaType = AetherAPI.instance().getMoaType(moaTypeSize);

			if (moaType.getCreativeTab() == tab || tab == null || tab == CreativeTabs.tabAllSearch) {
				compound.setInteger("typeId", moaTypeSize);
				stack.setTagCompound(compound);

				subItems.add(stack);
			}
		}
	}

	@Override
	public boolean getShareTag() {
		return true;
	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		NBTTagCompound tag = stack.getTagCompound();

		if (tag != null) {
			AetherMoaType moaType = AetherAPI.instance().getMoaType(tag.getInteger("typeId"));

			return moaType.getMoaEggColor();
		}

		return AetherAPI.instance().getMoaType(0).getMoaEggColor();
	}

	public AetherMoaType getMoaTypeFromItemStack(ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound();

		if (tag != null) {
			AetherMoaType moaType = AetherAPI.instance().getMoaType(tag.getInteger("typeId"));

			return moaType;
		}

		return AetherAPI.instance().getMoaType(0);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		NBTTagCompound tag = stack.getTagCompound();

		if (tag != null && stack.getTagCompound().hasKey("typeId")) {
			AetherMoaType moaType = AetherAPI.instance().getMoaType(tag.getInteger("typeId"));

			return "item." + moaType.getRegistryName().getResourcePath().replace(" ", "_").toLowerCase() + "_moa_egg.name";
		}

		return super.getUnlocalizedName();
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return super.getItemStackDisplayName(stack).replace(".name", "");
	}

	public static ItemStack getStackFromType(AetherMoaType type) {
		ItemStack stack = new ItemStack(ItemsAether.moa_egg);

		NBTTagCompound tag = new NBTTagCompound();

		tag.setInteger("typeId", AetherAPI.instance().getMoaTypeId(type));

		stack.setTagCompound(tag);

		return stack;
	}

}