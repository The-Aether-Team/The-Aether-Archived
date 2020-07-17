package com.legacy.aether.client.renders.entities.layer;

import com.legacy.aether.api.AetherAPI;
import com.legacy.aether.api.player.IPlayerAether;
import com.legacy.aether.api.player.util.IAccessoryInventory;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.items.accessories.ItemAccessory;
import com.legacy.aether.player.PlayerAether;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelElytra;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerElytraAether implements LayerRenderer<EntityLivingBase>
{
    /** The basic Elytra texture. */
    private static final ResourceLocation TEXTURE_ELYTRA = new ResourceLocation("textures/entity/elytra.png");
    /** Instance of the player renderer. */
    protected final RenderLivingBase<?> renderPlayer;
    /** The model used by the Elytra. */
    private final ModelElytra modelElytra = new ModelElytra();

    public LayerElytraAether(RenderLivingBase<?> p_i47185_1_)
    {
        this.renderPlayer = p_i47185_1_;
    }

    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (!Loader.isModLoaded("quark") && !Loader.isModLoaded("bannerelytra"))
        {
            ItemStack itemstack = entitylivingbaseIn.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

            if (itemstack.getItem() == Items.ELYTRA)
            {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);

                if (entitylivingbaseIn instanceof AbstractClientPlayer)
                {
                    AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)entitylivingbaseIn;

                    IPlayerAether playerAether = AetherAPI.getInstance().get(abstractclientplayer);
                    IAccessoryInventory accessories = playerAether.getAccessoryInventory();

                    if (accessories.getStackInSlot(1).getItem() instanceof ItemAccessory && accessories.getStackInSlot(1).getItem() != ItemsAether.invisibility_cape && ((PlayerAether) playerAether).shouldRenderCape)
                    {
                        ItemAccessory cape = ((ItemAccessory) (accessories.getStackInSlot(1).getItem()));

                        if (cape.hasElytra())
                        {
                            int colour = cape.getColorFromItemStack(accessories.getStackInSlot(1), 0);
                            float red = ((colour >> 16) & 0xff) / 255F;
                            float green = ((colour >> 8) & 0xff) / 255F;
                            float blue = (colour & 0xff) / 255F;
                            GlStateManager.color(red, green, blue, 1.0F);
                            this.renderPlayer.bindTexture(cape.texture_elytra);
                        }
                        else
                        {
                            this.renderPlayer.bindTexture(TEXTURE_ELYTRA);
                        }
                    }
                    else
                    {
                        if (abstractclientplayer.isPlayerInfoSet() && abstractclientplayer.getLocationElytra() != null)
                        {
                            this.renderPlayer.bindTexture(abstractclientplayer.getLocationElytra());
                        }
                        else if (abstractclientplayer.hasPlayerInfo() && abstractclientplayer.getLocationCape() != null && abstractclientplayer.isWearing(EnumPlayerModelParts.CAPE))
                        {
                            this.renderPlayer.bindTexture(abstractclientplayer.getLocationCape());
                        }
                        else
                        {
                            this.renderPlayer.bindTexture(TEXTURE_ELYTRA);
                        }
                    }
                }
                else
                {
                    this.renderPlayer.bindTexture(TEXTURE_ELYTRA);
                }

                GlStateManager.pushMatrix();
                GlStateManager.translate(0.0F, 0.0F, 0.125F);
                this.modelElytra.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entitylivingbaseIn);
                this.modelElytra.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

                if (itemstack.isItemEnchanted())
                {
                    LayerArmorBase.renderEnchantedGlint(this.renderPlayer, entitylivingbaseIn, this.modelElytra, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
                }

                GlStateManager.disableBlend();
                GlStateManager.popMatrix();
            }
        }
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }
}