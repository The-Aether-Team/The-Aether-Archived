package com.legacy.aether.server.items.weapons;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;
import com.legacy.aether.server.registry.creative_tabs.AetherCreativeTabs;

public class ItemVampireBlade extends Item
{

    private int weaponDamage;

    public ItemVampireBlade()
    {
    	super();
        this.maxStackSize = 1;
        this.setMaxDamage(ToolMaterial.DIAMOND.getMaxUses());
        this.weaponDamage = (int) (4 + ToolMaterial.DIAMOND.getDamageVsEntity() * 2);
        this.setCreativeTab(AetherCreativeTabs.weapons);
    }

    public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase entityliving1)
    {
		EntityPlayer player = (EntityPlayer)entityliving1;

		if(player.getHealth() < player.getMaxHealth())
		{
			player.heal(1.0F);
		}

        itemstack.damageItem(1, entityliving1);

        return true;
    }

    public boolean onBlockDestroyed(ItemStack itemstack, World world, Block block, BlockPos pos, EntityLivingBase entityliving)
    {
        itemstack.damageItem(2, entityliving);
        return true;
    }

    public boolean canHarvestBlock(IBlockState block)
    {
        return block.getBlock() == Blocks.WEB;
    }

    @SuppressWarnings({ "deprecation" })
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)this.weaponDamage, 0));
        }

        return multimap;
    }

}