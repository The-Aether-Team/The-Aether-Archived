package com.legacy.aether.common.registry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.MathHelper;

import com.legacy.aether.common.blocks.BlocksAether;
import com.legacy.aether.common.containers.util.AccessoryType;
import com.legacy.aether.common.enchantments.AetherEnchantment;
import com.legacy.aether.common.enchantments.IEnchantmentHandler;
import com.legacy.aether.common.freezables.AetherFreezable;
import com.legacy.aether.common.freezables.IFreezableHandler;
import com.legacy.aether.common.items.ItemsAether;
import com.legacy.aether.common.items.accessories.ItemAccessory;
import com.legacy.aether.common.items.armor.ItemAetherArmor;
import com.legacy.aether.common.items.tools.ItemGravititeTool;
import com.legacy.aether.common.items.tools.ItemHolystoneTool;
import com.legacy.aether.common.items.tools.ItemSkyrootTool;
import com.legacy.aether.common.items.tools.ItemZaniteTool;

public class AetherRegistryHandler implements IEnchantmentHandler, IFreezableHandler
{

	@Override
	public AetherEnchantment getEnchantment(ItemStack stack)
	{
		Item item = stack.getItem();
		int metadata = stack.getMetadata();

		if (item instanceof ItemSkyrootTool || item == ItemsAether.skyroot_sword)
		{
			return new AetherEnchantment(item, 225);
		}
		else if (item instanceof ItemHolystoneTool || item == ItemsAether.holystone_sword)
		{
			return new AetherEnchantment(item, 550);
		}
		else if (item instanceof ItemZaniteTool || item == ItemsAether.zanite_sword)
		{
			return new AetherEnchantment(item, 2250);
		}
		else if (item instanceof ItemGravititeTool || item == ItemsAether.gravitite_sword)
		{
			return new AetherEnchantment(item, 5500);
		}
		else if (item == ItemsAether.dart && metadata == 0)
		{
			return new AetherEnchantment(new ItemStack(ItemsAether.dart, 1, 2), 250);
		}
		else if (item == ItemsAether.dart_shooter && metadata == 0)
		{
			return new AetherEnchantment(new ItemStack(ItemsAether.dart_shooter, 1, 2), 2000);
		}
		else if (item == ItemsAether.skyroot_bucket && metadata == 2)
		{
			return new AetherEnchantment(new ItemStack(ItemsAether.skyroot_bucket, 1, 3), 1000);
		}
		else if (item == Item.getItemFromBlock(BlocksAether.holystone))
		{
			return new AetherEnchantment(ItemsAether.healing_stone, 750);
		}
		else if (item == Item.getItemFromBlock(BlocksAether.gravitite_ore))
		{
			return new AetherEnchantment(BlocksAether.enchanted_gravitite, 1000);
		}
		else if (item == Item.getItemFromBlock(BlocksAether.quicksoil))
		{
			return new AetherEnchantment(BlocksAether.quicksoil_glass, 250);
		}
		else if (item == ItemsAether.blue_berry)
		{
			return new AetherEnchantment(ItemsAether.enchanted_blueberry, 300);
		}
		else if (item instanceof ItemAetherArmor)
		{
			ItemAetherArmor armor = ((ItemAetherArmor)item);

			if (armor.getRepairMaterial() == ItemsAether.zanite_gemstone)
			{
				return new AetherEnchantment(item, 6000);
			}
			else if (armor.getRepairMaterial() == Item.getItemFromBlock(BlocksAether.enchanted_gravitite))
			{
				return new AetherEnchantment(item, 13000);
			}
		}
		else if (item == Items.FISHING_ROD)
		{
			return new AetherEnchantment(item, 600);
		}
		else if (item instanceof ItemRecord)
		{
			return new AetherEnchantment(ItemsAether.aether_tune, 2500);
		}
		else if (item instanceof ItemSword)
		{
			ItemSword sword = (ItemSword) item;

			if (stack.getRarity() != ItemsAether.aether_loot)
			{
				return new AetherEnchantment(sword, 400 * (1 + MathHelper.floor_float(sword.getDamageVsEntity())));
			}
		}
		else if (item instanceof ItemArmor)
		{
			ItemArmor armor = (ItemArmor) item;

			return new AetherEnchantment(armor, 200 * armor.damageReduceAmount);
		}

		return null;
	}

	@Override
	public AetherFreezable getFreezable(ItemStack stack) 
	{
		Item item = stack.getItem();
		int metadata = stack.getMetadata();

		if (item == Item.getItemFromBlock(BlocksAether.aercloud) && metadata == 0)
		{
			return new AetherFreezable(new ItemStack(BlocksAether.aercloud, 1, 1), 50);
		}
		else if (item == Item.getItemFromBlock(BlocksAether.aether_leaves) && metadata == 0)
		{
			return new AetherFreezable(BlocksAether.crystal_leaves, 150);
		}
		else if ((item == ItemsAether.skyroot_bucket && metadata == 1))
		{
			return new AetherFreezable(Blocks.ICE, 500);
		}
		else if (item == ItemsAether.ascending_dawn)
		{
			return new AetherFreezable(ItemsAether.welcoming_skies, 800);
		}
		else if (item == Item.getItemFromBlock(Blocks.ICE))
		{
			return new AetherFreezable(Blocks.PACKED_ICE, 750);
		}
		else if (item == Items.WATER_BUCKET)
		{
			return new AetherFreezable(Blocks.ICE, 500);
		}
		else if (item == Items.LAVA_BUCKET)
		{
			return new AetherFreezable(Blocks.OBSIDIAN, 500);
		}
		else if (item instanceof ItemAccessory)
		{
			AccessoryType accessoryType = ((ItemAccessory)item).getType();

			if (accessoryType == AccessoryType.PENDANT)
			{
				return new AetherFreezable(ItemsAether.ice_pendant, 2500);
			}
			else if (accessoryType == AccessoryType.RING)
			{
				return new AetherFreezable(ItemsAether.ice_ring, 2500);
			}
		}

		return null;
	}

	@Override
	public int getEnchantmentFuelTime(ItemStack stack)
	{
		if (stack.getItem() == ItemsAether.ambrosium_shard)
		{
			return 500;
		}

		return 0;
	}

	@Override
	public int getFreezerFuelTime(ItemStack stack) 
	{
		if (stack.getItem() == Item.getItemFromBlock(BlocksAether.icestone))
		{
			return 500;
		}

		return 0;
	}

}