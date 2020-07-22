package com.legacy.aether.items.accessories;

import java.util.List;

import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.legacy.aether.Aether;
import com.legacy.aether.api.accessories.AccessoryType;
import com.legacy.aether.client.ClientProxy;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.player.PlayerAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAccessory extends Item {

	public static final String ROOT = Aether.modAddress() + "textures/slots/slot_";

	protected final AccessoryType accessoryType;

	protected final AccessoryType extraType;

	public ResourceLocation texture, texture_inactive;

	private int colorHex = 0xdddddd;

	private boolean isDungeonLoot = false;

	private boolean hasInactiveTexture = false;

	public static final IBehaviorDispenseItem DISPENSER_BEHAVIOR = new BehaviorDefaultDispenseItem() {
		protected ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
			ItemStack itemstack = ItemAccessory.dispenseAccessory(source, stack);
			return itemstack != null ? itemstack : super.dispenseStack(source, stack);
		}
	};

	public ItemAccessory(AccessoryType type) {
		this.accessoryType = type;
		this.extraType = type == AccessoryType.RING ? AccessoryType.EXTRA_RING : type == AccessoryType.MISC ? AccessoryType.EXTRA_MISC : null;
		this.texture = Aether.locate("textures/armor/accessory_base.png");

		this.setMaxStackSize(1);
		this.setCreativeTab(AetherCreativeTabs.accessories);
		BlockDispenser.dispenseBehaviorRegistry.putObject(this, DISPENSER_BEHAVIOR);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister registry) {
		super.registerIcons(registry);

		ObjectIntIdentityMap orderedList = AccessoryType.createCompleteList();

		for (int i = 0; i < ClientProxy.ACCESSORY_ICONS.length; ++i) {
			ClientProxy.ACCESSORY_ICONS[i] = registry.registerIcon(Aether.find("slots/" + ((AccessoryType) orderedList.func_148745_a(i)).getDisplayName()));
		}
	}

	@SuppressWarnings("unchecked")
	public static ItemStack dispenseAccessory(IBlockSource blockSource, ItemStack stack) {
		EnumFacing enumfacing = BlockDispenser.func_149937_b(blockSource.getBlockMetadata());
		int i = blockSource.getXInt() + enumfacing.getFrontOffsetX();
		int j = blockSource.getYInt() + enumfacing.getFrontOffsetY();
		int k = blockSource.getZInt() + enumfacing.getFrontOffsetZ();
		AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox((double) i, (double) j, (double) k, (double) (i + 1), (double) (j + 1), (double) (k + 1));
		List<EntityLivingBase> list = blockSource.getWorld().getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);

		if (list.isEmpty()) {
			return null;
		}

		EntityPlayer player = (EntityPlayer) list.get(0);

		ItemStack itemstack = stack.copy();
		itemstack.stackSize = 1;

		PlayerAether playerAether = PlayerAether.get((EntityPlayer) player);

		if (!playerAether.getAccessoryInventory().setAccessorySlot(itemstack)) {
			return null;
		}

		--stack.stackSize;

		return stack;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World worldIn, EntityPlayer player) {
		ItemStack heldItem = player.getHeldItem();

		if (heldItem != null) {
			if (PlayerAether.get(player).getAccessoryInventory().setAccessorySlot(heldItem.copy())) {
				--heldItem.stackSize;

				return heldItem;
			}
		}

		return super.onItemRightClick(stack, worldIn, player);
	}
	
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
	{
		return (repair.getItem() == ItemsAether.zanite_gemstone && toRepair.getItem() == ItemsAether.zanite_ring)
				|| (repair.getItem() == ItemsAether.zanite_gemstone && toRepair.getItem() == ItemsAether.zanite_pendant);
	}

	public AccessoryType getExtraType() {
		return this.extraType;
	}

	public AccessoryType getType() {
		return this.accessoryType;
	}

	public Item setColor(int color) {
		this.colorHex = color;
		return this;
	}

	public int getColor() {
		return this.colorHex;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack) {
		return this.isDungeonLoot ? ItemsAether.aether_loot : super.getRarity(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int meta) {
		return this.colorHex;
	}

	public ItemAccessory setDungeonLoot() {
		this.isDungeonLoot = true;

		return this;
	}

	public ItemAccessory setTexture(String location) {
		this.texture = Aether.locate("textures/armor/accessory_" + location + ".png");

		return this;
	}

	public ItemAccessory setInactiveTexture(String location)
	{
		this.texture_inactive = new ResourceLocation("aether_legacy", "textures/armor/accessory_" + location + ".png");
		this.hasInactiveTexture = true;

		return this;
	}

	public boolean hasInactiveTexture()
	{
		return this.hasInactiveTexture;
	}

}