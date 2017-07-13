package com.legacy.aether.common.items.armor;

import java.util.UUID;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class ItemZaniteArmor extends ItemAetherArmor
{

	private final UUID ARMOR_RESISTANCE = UUID.fromString("d111114d-f592-4876-a2eb-26bbc974b0fd");

    private final int[][] damageReductionAmountArray = new int[][] { new int[]{1, 2, 3, 1}, new int[]{1, 4, 5, 2}, new int[]{2, 5, 6, 2}, new int[]{3, 6, 8, 3}, new int[] {4, 8, 10, 4} };

	public ItemZaniteArmor(EntityEquipmentSlot armorType, ArmorMaterial material, String name, Item repair, int hex)
	{
		super(armorType, material, name, repair, hex);
	}

	@Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
    	return HashMultimap.<String, AttributeModifier>create();
    }

	@Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
    	HashMultimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

    	if (slot == this.armorType)
    	{
    		multimap.put(SharedMonsterAttributes.ARMOR.getName(), new AttributeModifier(ARMOR_RESISTANCE, "Armor modifier", (double)this.calculateIncrease(stack), 0));
    	}

		return multimap;
    }

    private float calculateIncrease(ItemStack tool)
    {
    	if (this.armorType == EntityEquipmentSlot.HEAD)
    	{
    		if (isBetween(tool, 0, 32))
    		{ return this.getDamageReductionAmount(4); }
    		else if (isBetween(tool, 33, 65))
    		{ return this.getDamageReductionAmount(3); }
    		else if (isBetween(tool, 66, 98))
    		{ return this.getDamageReductionAmount(2); }
    		else if (isBetween(tool, 99, 131))
    		{ return this.getDamageReductionAmount(1); }
    		else if (isBetween(tool, 132, 165))
    		{ return this.getDamageReductionAmount(0); }
    	}
    	else if (this.armorType == EntityEquipmentSlot.CHEST)
    	{
    		if (isBetween(tool, 0, 47))
    		{ return this.getDamageReductionAmount(4); }
    		else if (isBetween(tool, 48, 95))
    		{ return this.getDamageReductionAmount(3); }
    		else if (isBetween(tool, 96, 143))
    		{ return this.getDamageReductionAmount(2); }
    		else if (isBetween(tool, 144, 191))
    		{ return this.getDamageReductionAmount(1); }
    		else if (isBetween(tool, 192, 240))
    		{ return this.getDamageReductionAmount(0); }
    	}
    	else if (this.armorType == EntityEquipmentSlot.LEGS)
    	{
    		if (isBetween(tool, 0, 44))
    		{ return this.getDamageReductionAmount(4); }
    		else if (isBetween(tool, 45, 89))
    		{ return this.getDamageReductionAmount(3); }
    		else if (isBetween(tool, 90, 134))
    		{ return this.getDamageReductionAmount(2); }
    		else if (isBetween(tool, 135, 179))
    		{ return this.getDamageReductionAmount(1); }
    		else if (isBetween(tool, 180, 225))
    		{ return this.getDamageReductionAmount(0); }
    	}
    	else if (this.armorType == EntityEquipmentSlot.FEET)
    	{
    		if (isBetween(tool, 0, 38))
    		{ return this.getDamageReductionAmount(4); }
    		else if (isBetween(tool, 39, 77))
    		{ return this.getDamageReductionAmount(3); }
    		else if (isBetween(tool, 78, 116))
    		{ return this.getDamageReductionAmount(2); }
    		else if (isBetween(tool, 117, 155))
    		{ return this.getDamageReductionAmount(1); }
    		else if (isBetween(tool, 156, 195))
    		{ return this.getDamageReductionAmount(0); }
    	}

    	return 0.0F;
    }

    public boolean isBetween(ItemStack tool, int max, int min)
    {
    	int origin = tool.getItemDamage();
    	int maxDamage = tool.getMaxDamage();

    	return origin <= maxDamage - max && origin >= maxDamage - min ? true : false;
    }

    public int getDamageReductionAmount(int level)
    {
        return this.damageReductionAmountArray[level][this.armorType.getIndex()];
    }

}