package com.legacy.aether.items.weapons.projectile;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.legacy.aether.Aether;
import com.legacy.aether.items.util.EnumDartType;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDart extends Item
{

    @SideOnly(Side.CLIENT)
    private IIcon goldenIcon;

    @SideOnly(Side.CLIENT)
    private IIcon poisonIcon;

    @SideOnly(Side.CLIENT)
    private IIcon enchantedIcon;

    public ItemDart()
    {
        super();

        this.setHasSubtypes(true);
        this.setCreativeTab(AetherCreativeTabs.weapons);
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister registry)
    {
        this.goldenIcon = registry.registerIcon(Aether.find("projectile/golden_dart"));
        this.poisonIcon = registry.registerIcon(Aether.find("projectile/poison_dart"));
        this.enchantedIcon = registry.registerIcon(Aether.find("projectile/enchanted_dart"));
    }

	@Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta)
    {
        return meta == 1 ? this.poisonIcon : meta == 2 ? this.enchantedIcon : this.goldenIcon;
    }

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return stack.getItemDamage() == 2 ? EnumRarity.rare : super.getRarity(stack);
    }

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		int i = itemstack.getItemDamage();

		return this.getUnlocalizedName() + "_" + EnumDartType.values()[i].toString();
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getSubItems(Item item, CreativeTabs tab, List subItems)
    {
        for (int var4 = 0; var4 < EnumDartType.values().length ; ++var4)
        {
        	subItems.add(new ItemStack(this, 1, var4));
        }
    }

}