package com.gildedgames.the_aether.items.weapons.projectile;

import com.gildedgames.the_aether.entities.projectile.darts.EntityDartBase;
import com.gildedgames.the_aether.entities.projectile.darts.EntityDartEnchanted;
import com.gildedgames.the_aether.entities.projectile.darts.EntityDartGolden;
import com.gildedgames.the_aether.entities.projectile.darts.EntityDartPoison;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import com.gildedgames.the_aether.items.util.EnumDartType;

import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.*;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemDart extends Item
{
    public static final IBehaviorDispenseItem DISPENSER_BEHAVIOR = new BehaviorProjectileDispense()
    {
        @Override
        protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn)
        {
            EntityDartBase dart = new EntityDartGolden(worldIn);

            if (stackIn.getItem() == ItemsAether.dart && stackIn.getMetadata() == EnumDartType.Poison.meta)
            {
                dart = new EntityDartPoison(worldIn);
            }
            else if (stackIn.getItem() == ItemsAether.dart && stackIn.getMetadata() == EnumDartType.Enchanted.meta)
            {
                dart = new EntityDartEnchanted(worldIn);
            }

            dart.setNoGravity(true);
            dart.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
            dart.setPosition(position.getX(), position.getY(), position.getZ());

            return dart;
        }
    };

    public ItemDart()
    {
        super();
        this.setHasSubtypes(true);
        this.setCreativeTab(AetherCreativeTabs.weapons);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, DISPENSER_BEHAVIOR);
    }

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return stack.getMetadata() == 2 ? EnumRarity.RARE : super.getRarity(stack);
    }

	@Override
	public String getTranslationKey(ItemStack itemstack)
	{
		int i = itemstack.getItemDamage();

		return this.getTranslationKey() + "_" + EnumDartType.values()[i].toString();
	}

	@Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
    	if (this.isInCreativeTab(tab))
        {
            for (int meta = 0; meta < EnumDartType.values().length; ++meta)
            {
            	subItems.add(new ItemStack(this, 1, meta));
            }
        }
    }

}