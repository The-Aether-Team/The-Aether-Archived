package com.legacy.aether.entities.effects;

import com.legacy.aether.api.AetherAPI;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.items.util.EnumSkyrootBucketType;
import com.legacy.aether.networking.AetherNetworkingManager;
import com.legacy.aether.networking.packets.PacketSendPoison;
import com.legacy.aether.player.PlayerAether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class PotionInebriation extends Potion
{
    private int duration;

    public double rotD, motD;

    public PotionInebriation() {
        super(true, 0x51297B);
        this.duration = 0;
        this.setIconIndex(0, 0);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        this.duration = duration;
        return true;
    }

    @Override
    public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier)
    {
        this.distractEntity(entityLivingBaseIn);

        if (this.duration % 50 == 0)
        {
            entityLivingBaseIn.attackEntityFrom(new DamageSource("inebriation"), 1.0F);
        }

        if (entityLivingBaseIn instanceof EntityPlayer)
        {
            if (this.duration >= 500)
            {
                ((PlayerAether) AetherAPI.getInstance().get((EntityPlayer) entityLivingBaseIn)).setPoisoned();
                AetherNetworkingManager.sendToAll(new PacketSendPoison((EntityPlayer) entityLivingBaseIn));
            }
        }
    }

    public void distractEntity(EntityLivingBase entityLivingBaseIn)
    {
        double gaussian = entityLivingBaseIn.world.rand.nextGaussian();
        double newMotD = 0.1D * gaussian;
        double newRotD = (Math.PI / 4D) * gaussian;

        this.motD = 0.2D * newMotD + (0.8D) * this.motD;
        entityLivingBaseIn.motionX += this.motD;
        entityLivingBaseIn.motionZ += this.motD;
        this.rotD = 0.125D * newRotD + (1.0D - 0.125D) * this.rotD;

        entityLivingBaseIn.rotationYaw = (float)((double)entityLivingBaseIn.rotationYaw + rotD);
        entityLivingBaseIn.rotationPitch = (float)((double)entityLivingBaseIn.rotationPitch + rotD);


        if (entityLivingBaseIn.world instanceof WorldServer)
        {
            ((WorldServer)entityLivingBaseIn.world).spawnParticle(EnumParticleTypes.ITEM_CRACK, entityLivingBaseIn.posX, entityLivingBaseIn.getEntityBoundingBox().minY + entityLivingBaseIn.height * 0.8D, entityLivingBaseIn.posZ, 2, 0.0D, 0.0D, 0.0D, 0.0625D, Item.getIdFromItem(Items.DYE), 1);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasStatusIcon()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        mc.renderEngine.bindTexture(new ResourceLocation("aether_legacy", "textures/gui/inventory/inebriation.png"));
        Gui.drawModalRectWithCustomSizedTexture(x + 7, y + 8, 0, 0, 16, 16, 16, 16);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        mc.renderEngine.bindTexture(new ResourceLocation("aether_legacy", "textures/gui/inventory/inebriation.png"));
        Gui.drawModalRectWithCustomSizedTexture(x + 4, y + 4, 0, 0, 16, 16, 16, 16);
    }

    @Override
    public List<ItemStack> getCurativeItems()
    {
        ArrayList<ItemStack> ret = new ArrayList<>();
        ret.remove(new ItemStack(Items.MILK_BUCKET));
        ret.add(new ItemStack(ItemsAether.skyroot_bucket, EnumSkyrootBucketType.Remedy.meta));
        ret.add(new ItemStack(ItemsAether.white_apple));
        return ret;
    }
}
