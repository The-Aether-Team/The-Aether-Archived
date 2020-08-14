package com.gildedgames.the_aether.items.dungeon;

import java.util.List;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.items.util.EnumDungeonKeyType;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDungeonKey extends Item {

	@SideOnly(Side.CLIENT)
	private IIcon bronzeIcon;

	@SideOnly(Side.CLIENT)
	private IIcon silverIcon;

	@SideOnly(Side.CLIENT)
	private IIcon goldenIcon;

	public ItemDungeonKey() {
		super();

		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setCreativeTab(AetherCreativeTabs.misc);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registry) {
		this.bronzeIcon = registry.registerIcon(Aether.find("misc/keys/bronze_key"));
		this.silverIcon = registry.registerIcon(Aether.find("misc/keys/silver_key"));
		this.goldenIcon = registry.registerIcon(Aether.find("misc/keys/golden_key"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return meta == 1 ? this.silverIcon : meta == 2 ? this.goldenIcon : this.bronzeIcon;
	}

	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		for (int meta = 0; meta < EnumDungeonKeyType.values().length; ++meta) {
			subItems.add(new ItemStack(this, 1, meta));
		}
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return ItemsAether.aether_loot;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		int meta = itemstack.getItemDamage();

		return this.getUnlocalizedName() + "_" + EnumDungeonKeyType.getType(meta).toString();
	}

}