package com.legacy.aether.items.armor;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.legacy.aether.Aether;
import com.legacy.aether.client.models.ModelColoredArmor;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class ItemAetherArmor extends ItemArmor
{

	private String[] defualt_location = new String[] {"textures/models/armor/iron_layer_1.png", "textures/models/armor/iron_layer_2.png"};

	private boolean shouldDefualt = false;

	private int colorization = -1;

	private String armorName;

	private Item source = null;

	public ItemAetherArmor(EntityEquipmentSlot armorType, ArmorMaterial material, String name, Item repair)
	{
		super(material, 0, armorType);

		this.source = repair;
		this.armorName = name;
		this.setCreativeTab(AetherCreativeTabs.armor);
	}

	public ItemAetherArmor(EntityEquipmentSlot armorType, ArmorMaterial material, String name, Item repair, int hex)
	{
		this(armorType, material, name, repair);

		this.source = repair;
		this.armorName = name;
		this.colorization = hex;
		this.shouldDefualt = true;
	}

	public Item getRepairMaterial()
	{
		return this.source;
	}

    public int getColorFromItemStack(ItemStack stack, int renderPass)
    {
		return this.colorization != -1 ? this.colorization : 0;
    }

    public int getColorization(ItemStack stack)
    {
    	return this.colorization;
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
    	boolean leggings = this.getUnlocalizedName().contains("leggings");
    	String type1 = leggings ? "layer_2" : "layer_1";

        return this.shouldDefualt ? (leggings ? defualt_location[1] : defualt_location[0]) : Aether.modAddress() + "textures/armor/" + this.armorName + "_" + type1 + ".png";
    }

    @SideOnly(Side.CLIENT)
    public net.minecraft.client.model.ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default)
    {
    	if (shouldDefualt)
    	{
    		return new ModelColoredArmor(this.getUnlocalizedName().contains("leggings") ? 0.5F : 1.0F, this, armorSlot);
    	}

        return super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
    }

    @Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
	    return source == null ? false : repair.getItem() == source;
	}

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return !this.armorName.contains("zanite") && !this.armorName.contains("gravitite")? ItemsAether.aether_loot : super.getRarity(stack);
    }
}