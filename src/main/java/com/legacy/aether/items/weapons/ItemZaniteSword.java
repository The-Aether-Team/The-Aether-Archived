package com.legacy.aether.items.weapons;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

public class ItemZaniteSword extends ItemSword
{

	public float[] level = new float[] {3.0F, 4.0F, 5.0F, 6.0F, 7.0F};

    public ItemZaniteSword()
    {
        super(ToolMaterial.IRON);
        this.setCreativeTab(AetherCreativeTabs.weapons);
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot slot)
    {
    	return null;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (slot == EntityEquipmentSlot.MAINHAND)
        {
        	if (stack.getItem() instanceof ItemZaniteSword)
        	{
                multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.calculateIncrease(stack), 0));
        	}

            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }

    private float calculateIncrease(ItemStack tool)
    {
    	int current = tool.getItemDamage();

		if (isBetween(tool.getMaxDamage(), current, tool.getMaxDamage() - 50))
		{
			return level[4];
		}
		else if (isBetween(tool.getMaxDamage() - 51, current, tool.getMaxDamage() - 110))
		{
			return level[3];
		}
		else if (isBetween(tool.getMaxDamage() - 111, current, tool.getMaxDamage() - 200))
		{
			return level[2];
		}
		else if (isBetween(tool.getMaxDamage() - 201, current, tool.getMaxDamage() - 239))
		{
			return level[1];
		}
		else
		{
			return level[0];
		}
    }

    private boolean isBetween(int max, int origin, int min)
    {
    	return origin <= max && origin >= min ? true : false;
    }

    @Override
    public boolean getIsRepairable(ItemStack repairingItem, ItemStack material)
    {
        return material.getItem() == ItemsAether.zanite_gemstone;
    }

    @Override
    public float getDestroySpeed(ItemStack itemstack, IBlockState block)
    {
        return super.getDestroySpeed(itemstack, block) * (2.0F * (float)itemstack.getItemDamage() / (float)itemstack.getMaxDamage() + 0.5F);
    }

}