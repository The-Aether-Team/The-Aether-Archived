package com.legacy.aether.items.weapons.projectile;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

import com.legacy.aether.Aether;
import com.legacy.aether.entities.projectile.EntityPhoenixArrow;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.registry.creative_tabs.AetherCreativeTabs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPhoenixBow extends ItemBow
{

    @SideOnly(Side.CLIENT)
    private IIcon[] iconArray;

    public ItemPhoenixBow()
    {
    	super();

        this.maxStackSize = 1;
        this.setMaxDamage(384);
        this.setFull3D();
        this.setTextureName(Aether.find("weapons/bow"));
        this.setCreativeTab(AetherCreativeTabs.weapons);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getItemIconForUseDuration(int p_94599_1_)
    {
        return this.iconArray[p_94599_1_];
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        if (usingItem != null && stack.getItem() == ItemsAether.phoenix_bow)
        {
            int j = stack.getMaxItemUseDuration() - useRemaining;

            if (j >= 18)
            {
                return this.getItemIconForUseDuration(2);
            }

            if (j > 13)
            {
                return this.getItemIconForUseDuration(1);
            }

            if (j > 0)
            {
                return this.getItemIconForUseDuration(0);
            }
        }

        return getIcon(stack, renderPass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_)
    {
        this.itemIcon = p_94581_1_.registerIcon(Aether.find("weapons/phoenix_bow"));
        this.iconArray = new IIcon[bowPullIconNameArray.length];

        for (int i = 0; i < this.iconArray.length; ++i)
        {
            this.iconArray[i] = p_94581_1_.registerIcon(this.getIconString() + "_" + bowPullIconNameArray[i]);
        }
    }

	@Override
    public EnumRarity getRarity(ItemStack stack)
    {
    	return ItemsAether.aether_loot;
    }

    private ItemStack findAmmo(EntityPlayer player)
    {
        for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
        {
            ItemStack itemstack = player.inventory.getStackInSlot(i);

            if (this.isArrow(itemstack))
            {
                return itemstack;
            }
        }

        return null;
    }

    protected boolean isArrow(ItemStack stack)
    {
        return stack != null && stack.getItem() == Items.arrow;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer entityLiving, int timeLeft)
    {
        if (entityLiving instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            boolean flag = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
            ItemStack itemstack = this.findAmmo(entityplayer);

            int i = this.getMaxItemUseDuration(stack) - timeLeft;
            ArrowLooseEvent event = new ArrowLooseEvent(entityplayer, stack, i);
            MinecraftForge.EVENT_BUS.post(event);

            if (event.isCanceled())
            {
                return;
            }

            i = event.charge;
            if (i < 0) return;

            if (itemstack != null || flag)
            {
                if (itemstack == null)
                {
                    itemstack = new ItemStack(Items.arrow);
                }

                float f = getArrowVelocity(i);

                if ((double)f >= 0.1D)
                {
                    boolean flag1 = entityplayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;

                    if (!worldIn.isRemote)
                    {
                        EntityPhoenixArrow entityarrow = this.createArrow(worldIn, f * 2.0F, itemstack, entityplayer);

                        if (f == 1.0F)
                        {
                            entityarrow.setIsCritical(true);
                        }

                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);

                        if (j > 0)
                        {
                            entityarrow.setDamage(entityarrow.getDamage() + (double)j * 0.5D + 0.5D);
                        }

                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);

                        if (k > 0)
                        {
                            entityarrow.setKnockbackStrength(k);
                        }

                        stack.damageItem(1, entityplayer);

                        if (flag1)
                        {
                            entityarrow.canBePickedUp = 2;
                        }

                        worldIn.spawnEntityInWorld(entityarrow);
                    }

                    worldIn.playSoundAtEntity(entityLiving, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                    if (!flag1)
                    {
                    	--itemstack.stackSize;

                        if (itemstack.stackSize == 0)
                        {
                            entityplayer.inventory.consumeInventoryItem(itemstack.getItem());
                        }
                    }
                }
            }
        }
    }

    public EntityPhoenixArrow createArrow(World worldIn, float distance, ItemStack stack, EntityLivingBase shooter)
    {
        EntityPhoenixArrow entityPhoenixArrow = new EntityPhoenixArrow(worldIn, shooter, distance);

        return entityPhoenixArrow;
    }

    public static float getArrowVelocity(int charge)
    {
        float f = (float)charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;

        if (f > 1.0F)
        {
            f = 1.0F;
        }

        return f;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.bow;
    }
 
    @Override
    public ItemStack onItemRightClick(ItemStack heldItem, World worldIn, EntityPlayer playerIn)
    {
        boolean flag = this.findAmmo(playerIn) != null;

        ArrowNockEvent event = new ArrowNockEvent(playerIn, heldItem);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return event.result;
        }

        if (playerIn.capabilities.isCreativeMode || flag)
        {
        	playerIn.setItemInUse(heldItem, this.getMaxItemUseDuration(heldItem));
        }

        return heldItem;
    }

    @Override
    public int getItemEnchantability()
    {
        return 1;
    }

}