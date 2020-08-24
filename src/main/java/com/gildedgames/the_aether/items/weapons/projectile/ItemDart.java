package com.gildedgames.the_aether.items.weapons.projectile;

import java.util.List;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.entities.projectile.darts.EntityDartBase;
import com.gildedgames.the_aether.entities.projectile.darts.EntityDartEnchanted;
import com.gildedgames.the_aether.entities.projectile.darts.EntityDartGolden;
import com.gildedgames.the_aether.entities.projectile.darts.EntityDartPoison;
import com.gildedgames.the_aether.items.ItemsAether;
import com.gildedgames.the_aether.items.accessories.ItemAccessory;
import com.gildedgames.the_aether.items.util.EnumDartType;
import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.*;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.World;

public class ItemDart extends Item {

	@SideOnly(Side.CLIENT)
	private IIcon goldenIcon;

	@SideOnly(Side.CLIENT)
	private IIcon poisonIcon;

	@SideOnly(Side.CLIENT)
	private IIcon enchantedIcon;

	public static final IBehaviorDispenseItem DISPENSER_BEHAVIOR = new IBehaviorDispenseItem()
	{
		public ItemStack dispense(IBlockSource p_82482_1_, final ItemStack p_82482_2_)
		{
			if (p_82482_2_.getItem() == ItemsAether.dart && p_82482_2_.getItemDamage() == EnumDartType.Poison.meta)
			{
				return (new BehaviorProjectileDispense()
				{
					protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
					{
						EntityDartBase dart = new EntityDartPoison(p_82499_1_);

						dart.canBePickedUp = 1;
						dart.setPosition(p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());

						return dart;
					}

				}).dispense(p_82482_1_, p_82482_2_);
			}
			else if (p_82482_2_.getItem() == ItemsAether.dart && p_82482_2_.getItemDamage() == EnumDartType.Enchanted.meta)
			{
				return (new BehaviorProjectileDispense()
				{
					protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
					{
						EntityDartBase dart = new EntityDartEnchanted(p_82499_1_);

						dart.canBePickedUp = 1;
						dart.setPosition(p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());

						return dart;
					}

				}).dispense(p_82482_1_, p_82482_2_);
			}

			return (new BehaviorProjectileDispense()
			{
				protected IProjectile getProjectileEntity(World p_82499_1_, IPosition p_82499_2_)
				{
					EntityDartBase dart = new EntityDartGolden(p_82499_1_);

					dart.canBePickedUp = 1;
					dart.setPosition(p_82499_2_.getX(), p_82499_2_.getY(), p_82499_2_.getZ());

					return dart;
				}

			}).dispense(p_82482_1_, p_82482_2_);
		}
	};

	public ItemDart() {
		super();

		this.setHasSubtypes(true);
		this.setCreativeTab(AetherCreativeTabs.weapons);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, DISPENSER_BEHAVIOR);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registry) {
		this.goldenIcon = registry.registerIcon(Aether.find("projectile/golden_dart"));
		this.poisonIcon = registry.registerIcon(Aether.find("projectile/poison_dart"));
		this.enchantedIcon = registry.registerIcon(Aether.find("projectile/enchanted_dart"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int meta) {
		return meta == 1 ? this.poisonIcon : meta == 2 ? this.enchantedIcon : this.goldenIcon;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return stack.getItemDamage() == 2 ? EnumRarity.rare : super.getRarity(stack);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		int i = itemstack.getItemDamage();

		return this.getUnlocalizedName() + "_" + EnumDartType.values()[i].toString();
	}

	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void getSubItems(Item item, CreativeTabs tab, List subItems) {
		for (int var4 = 0; var4 < EnumDartType.values().length; ++var4) {
			subItems.add(new ItemStack(this, 1, var4));
		}
	}

}