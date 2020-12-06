package com.gildedgames.the_aether.items.accessories;

import java.util.List;

import com.gildedgames.the_aether.registry.creative_tabs.AetherCreativeTabs;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.base.Predicates;
import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.accessories.AccessoryType;
import com.gildedgames.the_aether.api.player.IPlayerAether;
import com.gildedgames.the_aether.items.ItemsAether;

public class ItemAccessory extends Item
{

	public static final String ROOT = Aether.modAddress() + "textures/slots/slot_";

	protected final AccessoryType accessoryType;

	public ResourceLocation texture, texture_slim, texture_elytra, texture_inactive, texture_inactive_slim;

	private int colorHex = 0xdddddd;

	private boolean isDungeonLoot = false;

	private boolean hasElytra = false, hasInactiveTexture = false;

    private SoundEvent equipSound;

    private boolean hasSound = false;

    public static final IBehaviorDispenseItem DISPENSER_BEHAVIOR = new BehaviorDefaultDispenseItem()
    {
        protected ItemStack dispenseStack(IBlockSource source, ItemStack stack)
        {
            ItemStack itemstack = ItemAccessory.dispenseAccessory(source, stack);
            return itemstack != null ? itemstack : super.dispenseStack(source, stack);
        }
    };

	public ItemAccessory(AccessoryType type)
	{
		this.accessoryType = type;
		this.texture = Aether.locate("textures/armor/accessory_base.png");
		this.texture_slim = Aether.locate("textures/armor/accessory_base_slim.png");
		this.setMaxStackSize(1);
		this.setCreativeTab(AetherCreativeTabs.accessories);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(this, DISPENSER_BEHAVIOR);
	}

    public static ItemStack dispenseAccessory(IBlockSource blockSource, ItemStack stack)
    {
        BlockPos blockpos = blockSource.getBlockPos().offset((EnumFacing)blockSource.getBlockState().getValue(BlockDispenser.FACING));
        List<EntityLivingBase> list = blockSource.getWorld().<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(blockpos), Predicates.<EntityLivingBase>and(EntitySelectors.NOT_SPECTATING, new EntitySelectors.ArmoredMob(stack)));

        if (list.isEmpty())
        {
            return null;
        }
        else
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)list.get(0);
            
            if (entitylivingbase instanceof EntityPlayer)
            {
            	ItemStack itemstack = stack.copy();
            	itemstack.setCount(1);
            	
            	IPlayerAether playerAether = AetherAPI.getInstance().get((EntityPlayer) entitylivingbase);

            	if (!playerAether.getAccessoryInventory().setAccessorySlot(itemstack))
            	{
            		BehaviorDefaultDispenseItem.doDispense(blockSource.getWorld(), itemstack, 6, (EnumFacing)blockSource.getBlockState().getValue(BlockDispenser.FACING), BlockDispenser.getDispensePosition(blockSource));
            	}

            	stack.shrink(1);

            	return stack;
            }
        }

		return stack;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
    {
    	ItemStack heldItem = player.getHeldItem(hand);

        if (heldItem != ItemStack.EMPTY)
        {
        	if (AetherAPI.getInstance().get(player).getAccessoryInventory().setAccessorySlot(heldItem.copy()))
        	{
            	heldItem.shrink(1);

            	if (this.getType() == AccessoryType.GLOVE || this.hasSound)
                {
                    SoundEvent soundEvent = SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;

                    if (this.getEquipSound() != null)
                    {
                        soundEvent = this.getEquipSound();
                    }

                    player.playSound(soundEvent, 1.0F, 1.0F);
                }

                return new ActionResult<>(EnumActionResult.SUCCESS, heldItem);
        	}
        }

        return new ActionResult<>(EnumActionResult.FAIL, heldItem);
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return (repair.getItem() == ItemsAether.zanite_gemstone && toRepair.getItem() == ItemsAether.zanite_ring)
                || (repair.getItem() == ItemsAether.zanite_gemstone && toRepair.getItem() == ItemsAether.zanite_pendant);
    }

	public AccessoryType getType()
	{
		return this.accessoryType;
	}

    public Item setColor(int color)
    {
    	this.colorHex = color;
    	return this;
    }

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return this.isDungeonLoot ? ItemsAether.aether_loot : super.getRarity(stack);
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int meta)
    {
    	return this.colorHex;
    }

    public ItemAccessory setDungeonLoot()
    {
    	this.isDungeonLoot = true;
    	return this;
    }

    public ItemAccessory setTexture(String location)
    {
    	this.texture = new ResourceLocation("aether_legacy", "textures/armor/accessory_" + location + ".png");
		this.texture_slim = Aether.locate("textures/armor/accessory_" + location + "_slim.png");

    	return this;
    }

    public ItemAccessory setElytraTexture(String location)
    {
        this.texture_elytra = new ResourceLocation("aether_legacy", "textures/armor/accessory_" + location + ".png");
        this.hasElytra = true;

        return this;
    }

    public boolean hasElytra()
    {
        return this.hasElytra;
    }

    public ItemAccessory setInactiveTexture(String location)
    {
        this.texture_inactive = new ResourceLocation("aether_legacy", "textures/armor/accessory_" + location + ".png");
        this.texture_inactive_slim = new ResourceLocation("aether_legacy", "textures/armor/accessory_" + location + "_slim.png");
        this.hasInactiveTexture = true;

        return this;
    }

    public boolean hasInactiveTexture()
    {
        return this.hasInactiveTexture;
    }

    public ItemAccessory setEquipSound(SoundEvent sound)
    {
        this.equipSound = sound;
        this.hasSound = true;

        return this;
    }

    public SoundEvent getEquipSound() {
        return equipSound;
    }
}