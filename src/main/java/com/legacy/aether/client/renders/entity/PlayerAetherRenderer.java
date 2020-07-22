package com.legacy.aether.client.renders.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.legacy.aether.Aether;
import com.legacy.aether.api.accessories.AccessoryType;
import com.legacy.aether.client.models.attachments.ModelAetherWings;
import com.legacy.aether.client.models.attachments.ModelHalo;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.items.accessories.ItemAccessory;
import com.legacy.aether.items.armor.ItemAetherArmor;
import com.legacy.aether.player.PlayerAether;
import com.legacy.aether.player.perks.AetherRankings;

public class PlayerAetherRenderer {

    private static final ResourceLocation TEXTURE_VALKYRIE = Aether.locate("textures/entities/valkyrie/valkyrie.png");

    private static final ResourceLocation TEXTURE_HALO = Aether.locate("textures/other/halo.png");

    private Minecraft mc;

    private ModelHalo modelHalo;

    public ModelBiped modelMisc;

    public ModelBiped modelGlow;

    private ModelAetherWings modelWings;

    private float partialTicks;

    private boolean isCapeRendering;

    private static final PlayerAetherRenderer instance = new PlayerAetherRenderer();

    public PlayerAetherRenderer() {
        super();

        this.mc = Minecraft.getMinecraft();
        this.modelHalo = new ModelHalo();
        this.modelGlow = new ModelBiped(0.7F);
        this.modelMisc = new ModelBiped(1.0F);
        this.modelWings = new ModelAetherWings(1.0F);
    }

    public int renderAetherArmor(PlayerAether playerAether, RenderPlayer renderPlayer, ItemStack stack, int slotType) {
        if (stack != null) {
            Item item = stack.getItem();

            if (item instanceof ItemAetherArmor) {
                ItemAetherArmor itemarmor = (ItemAetherArmor) item;

                this.mc.getTextureManager().bindTexture(RenderBiped.getArmorResource(playerAether.getEntity(), stack, slotType, null));
                ModelBiped modelbiped = slotType == 2 ? renderPlayer.modelArmor : renderPlayer.modelArmorChestplate;
                modelbiped.bipedHead.showModel = slotType == 0;
                modelbiped.bipedHeadwear.showModel = slotType == 0;
                modelbiped.bipedBody.showModel = slotType == 1 || slotType == 2;
                modelbiped.bipedRightArm.showModel = slotType == 1;
                modelbiped.bipedLeftArm.showModel = slotType == 1;
                modelbiped.bipedRightLeg.showModel = slotType == 2 || slotType == 3;
                modelbiped.bipedLeftLeg.showModel = slotType == 2 || slotType == 3;
                modelbiped = net.minecraftforge.client.ForgeHooksClient.getArmorModel(playerAether.getEntity(), stack, slotType, modelbiped);
                renderPlayer.setRenderPassModel(modelbiped);
                modelbiped.onGround = renderPlayer.modelBipedMain.onGround;
                modelbiped.isRiding = renderPlayer.modelBipedMain.isRiding;
                modelbiped.isChild = renderPlayer.modelBipedMain.isChild;

                int j = itemarmor.getColorFromItemStack(stack, 0);

                if (j != -1) {
                    float f1 = (float) (j >> 16 & 255) / 255.0F;
                    float f2 = (float) (j >> 8 & 255) / 255.0F;
                    float f3 = (float) (j & 255) / 255.0F;

                    GL11.glColor3f(f1, f2, f3);
                }

                if (stack.isItemEnchanted()) {
                    return 15;
                }

                return 1;
            }
        }

        return -1;
    }

    public void renderAccessories(PlayerAether playerAether, RenderPlayer renderer, double x, double y, double z, float partialTicks) {
        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        ItemStack itemstack = playerAether.getEntity().getCurrentEquippedItem();

        this.modelMisc.heldItemRight = this.modelWings.heldItemRight = this.modelGlow.heldItemRight = itemstack != null ? 1 : 0;

        if (itemstack != null && playerAether.getEntity().getItemInUseCount() > 0) {
            EnumAction enumaction = itemstack.getItemUseAction();

            if (enumaction == EnumAction.block) {
                this.modelMisc.heldItemRight = this.modelWings.heldItemRight = this.modelGlow.heldItemRight = 3;
            } else if (enumaction == EnumAction.bow) {
                this.modelMisc.aimedBow = this.modelWings.aimedBow = this.modelGlow.aimedBow = true;
            }
        }

        this.modelMisc.isSneak = this.modelWings.isSneak = this.modelWings.isSneak = playerAether.getEntity().isSneaking();

        this.doRender(playerAether, x, y, z, partialTicks);

        this.modelMisc.aimedBow = this.modelWings.aimedBow = this.modelGlow.aimedBow = false;
        this.modelMisc.isSneak = this.modelWings.isSneak = this.modelGlow.isSneak = false;
        this.modelMisc.heldItemRight = this.modelWings.heldItemRight = this.modelGlow.heldItemRight = 0;
    }

    private void doRender(PlayerAether playerAether, double x, double y, double z, float partialTicks) {
        EntityPlayer player = playerAether.getEntity();

        this.modelMisc.onGround = this.modelWings.onGround = this.modelGlow.onGround = playerAether.getEntity().getSwingProgress(partialTicks);
        this.modelMisc.isRiding = this.modelWings.isRiding = this.modelGlow.isRiding = playerAether.getEntity().isRiding();
        this.modelMisc.isChild = this.modelWings.isChild = this.modelGlow.isChild = playerAether.getEntity().isChild();

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);

        float f2 = this.interpolateRotation(player.prevRenderYawOffset, player.renderYawOffset, partialTicks);
        float f3 = this.interpolateRotation(player.prevRotationYawHead, player.rotationYawHead, partialTicks);
        float f4;

        if (player.isRiding() && player.ridingEntity instanceof EntityLivingBase) {
            EntityLivingBase entitylivingbase1 = (EntityLivingBase) player.ridingEntity;
            f2 = this.interpolateRotation(entitylivingbase1.prevRenderYawOffset, entitylivingbase1.renderYawOffset, partialTicks);
            f4 = MathHelper.wrapAngleTo180_float(f3 - f2);

            if (f4 < -85.0F) {
                f4 = -85.0F;
            }

            if (f4 >= 85.0F) {
                f4 = 85.0F;
            }

            f2 = f3 - f4;

            if (f4 * f4 > 2500.0F) {
                f2 += f4 * 0.2F;
            }
        }

        float f13 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;
        this.renderLivingAt(player, x, y, z);
        f4 = this.handleRotationFloat(player, partialTicks);
        this.rotateCorpse(player, f4, f2, partialTicks);
        float f5 = 0.0625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        this.preRenderCallback(player, partialTicks);
        GL11.glTranslatef(0.0F, -24.0F * f5 - 0.0078125F, 0.0F);
        float f6 = player.prevLimbSwingAmount + (player.limbSwingAmount - player.prevLimbSwingAmount) * partialTicks;
        float f7 = player.limbSwing - player.limbSwingAmount * (1.0F - partialTicks);

        if (player.isChild()) {
            f7 *= 3.0F;
        }

        if (f6 > 1.0F) {
            f6 = 1.0F;
        }

        GL11.glEnable(GL11.GL_ALPHA_TEST);

        this.modelMisc.setLivingAnimations(player, f7, f6, partialTicks);
        this.modelWings.setLivingAnimations(player, f7, f6, partialTicks);
        this.modelHalo.setLivingAnimations(player, f7, f6, partialTicks);
        this.modelGlow.setLivingAnimations(player, f7, f6, partialTicks);

        this.renderModel(playerAether, f7, f6, f4, f3 - f2, f13, f5, partialTicks);

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
    }

    private void renderModel(PlayerAether playerAether, float limbSwing, float prevLimbSwing, float rotation, float interpolateRotation, float prevRotationPitch, float scale, float partialTicks) {
        EntityPlayer player = playerAether.getEntity();

        GL11.glPushMatrix();

        this.setCapeRendering(playerAether.getAccessoryInventory().getStackInSlot(AccessoryType.CAPE) != null);

        this.modelMisc.setRotationAngles(limbSwing, prevLimbSwing, rotation, interpolateRotation, prevRotationPitch, scale, player);
        this.modelWings.setRotationAngles(limbSwing, prevLimbSwing, rotation, interpolateRotation, prevRotationPitch, scale, player);
        this.modelHalo.setRotationAngles(limbSwing, prevLimbSwing, rotation, interpolateRotation, prevRotationPitch, scale, player);
        this.modelGlow.setRotationAngles(limbSwing, prevLimbSwing, rotation, interpolateRotation, prevRotationPitch, scale, player);

        if (playerAether.getAccessoryInventory().getStackInSlot(AccessoryType.PENDANT) != null) {
            ItemAccessory pendant = (ItemAccessory) playerAether.getAccessoryInventory().getStackInSlot(AccessoryType.PENDANT).getItem();

            this.mc.getTextureManager().bindTexture(pendant.texture);

            int colour = pendant.getColorFromItemStack(new ItemStack(pendant, 1, 0), 1);
            float red = ((colour >> 16) & 0xff) / 255F;
            float green = ((colour >> 8) & 0xff) / 255F;
            float blue = (colour & 0xff) / 255F;

            if (player.hurtTime > 0) {
                GL11.glColor3f(1.0F, 0.5F, 0.5F);
            } else {
                GL11.glColor3f(red, green, blue);
            }

            this.modelMisc.bipedBody.render(scale);

            GL11.glColor3f(1.0F, 1.0F, 1.0F);
        }

        if (playerAether.getAccessoryInventory().getStackInSlot(AccessoryType.CAPE) != null && !playerAether.getAccessoryInventory().wearingAccessory(new ItemStack(ItemsAether.invisibility_cape))) {
            ItemAccessory cape = (ItemAccessory) playerAether.getAccessoryInventory().getStackInSlot(AccessoryType.CAPE).getItem();

            if (!player.isInvisible()) {
                if (playerAether.shouldRenderCape) {
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glPushMatrix();
                    GL11.glTranslatef(0.0F, 0.0F, 0.125F);
                    double d0 = player.field_71091_bM + (player.field_71094_bP - player.field_71091_bM) * (double) partialTicks - (player.prevPosX + (player.posX - player.prevPosX) * (double) partialTicks);
                    double d1 = player.field_71096_bN + (player.field_71095_bQ - player.field_71096_bN) * (double) partialTicks - (player.prevPosY + (player.posY - player.prevPosY) * (double) partialTicks);
                    double d2 = player.field_71097_bO + (player.field_71085_bR - player.field_71097_bO) * (double) partialTicks - (player.prevPosZ + (player.posZ - player.prevPosZ) * (double) partialTicks);
                    float f = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks;
                    double d3 = (double) MathHelper.sin(f * (float) Math.PI / 180.0F);
                    double d4 = (double) (-MathHelper.cos(f * (float) Math.PI / 180.0F));
                    float f1 = (float) d1 * 10.0F;
                    f1 = MathHelper.clamp_float(f1, -6.0F, 32.0F);
                    float f2 = (float) (d0 * d3 + d2 * d4) * 100.0F;
                    float f3 = (float) (d0 * d4 - d2 * d3) * 100.0F;

                    if (f2 < 0.0F) {
                        f2 = 0.0F;
                    }

                    float f4 = player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * partialTicks;
                    f1 = f1 + MathHelper.sin((player.prevDistanceWalkedModified + (player.distanceWalkedModified - player.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f4;

                    if (player.isSneaking()) {
                        f1 += 25.0F;
                    }

                    GL11.glRotatef(6.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glRotatef(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);

                    int colour = cape.getColorFromItemStack(playerAether.getAccessoryInventory().getStackInSlot(AccessoryType.CAPE), 0);

                    float red = ((colour >> 16) & 0xff) / 255F;
                    float green = ((colour >> 8) & 0xff) / 255F;
                    float blue = (colour & 0xff) / 255F;

                    if (player.hurtTime > 0) {
                        GL11.glColor3f(1.0F, 0.5F, 0.5F);
                    } else {
                        GL11.glColor3f(red, green, blue);
                    }

                    this.mc.getTextureManager().bindTexture(cape.texture);

                    GL11.glTranslatef(0.0F, 0.015625F, -0.0625F);
                    GL11.glScalef(0.8F, 0.9375F, 0.234375F);

                    this.modelMisc.renderCloak(scale);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glPopMatrix();
                }
            }
        }

        GL11.glScalef(0.9375F, 0.9375F, 0.9375F);

        if (playerAether.getAccessoryInventory().getStackInSlot(AccessoryType.GLOVES) != null) {
            ItemAccessory gloves = (ItemAccessory) playerAether.getAccessoryInventory().getStackInSlot(AccessoryType.GLOVES).getItem();

            this.mc.getTextureManager().bindTexture(gloves.texture);

            int colour = gloves.getColorFromItemStack(playerAether.getAccessoryInventory().getStackInSlot(AccessoryType.GLOVES), 0);
            float red = ((colour >> 16) & 0xff) / 255F;
            float green = ((colour >> 8) & 0xff) / 255F;
            float blue = (colour & 0xff) / 255F;

            if (player.hurtTime > 0) {
                GL11.glColor3f(1.0F, 0.5F, 0.5F);
            } else if (gloves != ItemsAether.phoenix_gloves) {
                GL11.glColor3f(red, green, blue);
            }

            this.modelMisc.bipedLeftArm.render(scale);
            this.modelMisc.bipedRightArm.render(scale);

            GL11.glColor3f(1.0F, 1.0F, 1.0F);
        }

        if (playerAether.getAccessoryInventory().getStackInSlot(AccessoryType.SHIELD) != null) {
            ItemAccessory shield = (ItemAccessory) playerAether.getAccessoryInventory().getStackInSlot(AccessoryType.SHIELD).getItem();

            if (player.motionX == 0.0 && (player.motionY == -0.0784000015258789 || player.motionY == 0.0) && player.motionZ == 0.0 && shield.hasInactiveTexture())
            {
                this.mc.getTextureManager().bindTexture(shield.texture);
            }
            else
            {
                this.mc.getTextureManager().bindTexture(shield.texture_inactive);
            }

            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glScalef(1.1F, 1.1F, 1.1F);

            if (player.hurtResistantTime > 0) {
                GL11.glColor4f(1.0F, 0.5F, 0.5F, 1.0F);
            } else {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            }

            this.modelGlow.render(player, limbSwing, prevLimbSwing, rotation, interpolateRotation, prevRotationPitch, scale);

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }

        if (playerAether.getAccessoryInventory().isWearingValkyrieSet()) {
            this.mc.getTextureManager().bindTexture(TEXTURE_VALKYRIE);

            this.modelWings.setWingSinage(playerAether.wingSinage);
            this.modelWings.wingLeft.render(scale);
            this.modelWings.wingRight.render(scale);

            if (player.hurtResistantTime > 0) {
                GL11.glColor3f(1.0F, 0.5F, 0.5F);
            } else {
                GL11.glColor3f(1.0F, 1.0F, 1.0F);
            }
        }

        if (AetherRankings.isRankedPlayer(player.getUniqueID()) && PlayerAether.get(player).shouldRenderHalo && !player.isInvisible()) {
            float f9 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * partialTicks - (player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * partialTicks);

            GL11.glPushMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glRotatef(f9, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(prevRotationPitch, 1.0F, 0.0F, 0.0F);
            GL11.glTranslatef(0.0F, -0.65F, 0.0F);
            GL11.glRotatef(-prevRotationPitch, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(-f9, 0.0F, 1.0F, 0.0F);

            this.mc.getTextureManager().bindTexture(TEXTURE_HALO);

            this.modelHalo.renderHalo(scale);
            GL11.glPopMatrix();
        }

        if (player.getUniqueID().toString().equals("cf51ef47-04a8-439a-aa41-47d871b0b837") || AetherRankings.isDeveloper(player.getUniqueID()) && playerAether.shouldRenderGlow && !player.isInvisible()) {
            this.mc.getTextureManager().bindTexture(((AbstractClientPlayer) player).getLocationSkin());

            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_NORMALIZE);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);

            this.modelGlow.render(player, limbSwing, prevLimbSwing, rotation, interpolateRotation, prevRotationPitch, scale);

            GL11.glDisable(GL11.GL_NORMALIZE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }

        GL11.glPopMatrix();
    }

    private void preRenderCallback(EntityLivingBase entity, float partialTicks) {
        float f1 = 0.9375F;
        GL11.glScalef(f1, f1, f1);
    }

    protected float getDeathMaxRotation(EntityLivingBase entity) {
        return 90.0F;
    }

    private float handleRotationFloat(EntityLivingBase entity, float partialTicks) {
        return (float) entity.ticksExisted + partialTicks;
    }

    private void renderLivingAt(EntityLivingBase entity, double x, double y, double z) {
        GL11.glTranslatef((float) x, (float) y, (float) z);
    }

    protected void rotateCorpse(EntityLivingBase entity, float p_77043_2_, float p_77043_3_, float partialTicks) {
        GL11.glRotatef(180.0F - p_77043_3_, 0.0F, 1.0F, 0.0F);

        if (entity.deathTime > 0) {
            float f3 = ((float) entity.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
            f3 = MathHelper.sqrt_float(f3);

            if (f3 > 1.0F) {
                f3 = 1.0F;
            }

            GL11.glRotatef(f3 * this.getDeathMaxRotation(entity), 0.0F, 0.0F, 1.0F);
        } else {
            String s = EnumChatFormatting.getTextWithoutFormattingCodes(entity.getCommandSenderName());

            if ((s.equals("Dinnerbone") || s.equals("Grumm")) && (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).getHideCape())) {
                GL11.glTranslatef(0.0F, entity.height + 0.1F, 0.0F);
                GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            }
        }
    }

    private float interpolateRotation(float p_77034_1_, float p_77034_2_, float p_77034_3_) {
        float f3;

        for (f3 = p_77034_2_ - p_77034_1_; f3 < -180.0F; f3 += 360.0F) {
            ;
        }

        while (f3 >= 180.0F) {
            f3 -= 360.0F;
        }

        return p_77034_1_ + p_77034_3_ * f3;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }

    private void setCapeRendering(boolean isRendering) {
        this.isCapeRendering = isRendering;
    }

    public boolean isCapeRendering() {
        return this.isCapeRendering;
    }

    public static PlayerAetherRenderer instance() {
        return instance;
    }

}