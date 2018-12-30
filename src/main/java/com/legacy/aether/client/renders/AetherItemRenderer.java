package com.legacy.aether.client.renders;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.FIRST_PERSON_MAP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.legacy.aether.api.accessories.AccessoryType;
import com.legacy.aether.items.ItemsAether;
import com.legacy.aether.items.accessories.ItemAccessory;
import com.legacy.aether.player.PlayerAether;

public class AetherItemRenderer extends ItemRenderer {

	private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");

	private Minecraft mc;

	private ItemStack itemToRender;

	private float equippedProgress;

	private float prevEquippedProgress;

	private int equippedItemSlot = -1;

	public AetherItemRenderer(Minecraft mcIn)
	{
		super(mcIn);

		this.mc = mcIn;
	}

	public void renderFirstPersonArm(RenderPlayer renderPlayer, EntityClientPlayerMP playerIn) {
		PlayerAether playerAether = PlayerAether.get(playerIn);
		ItemStack gloves = playerAether.getAccessoryInventory().getStackInSlot(AccessoryType.GLOVES);

		this.mc.getTextureManager().bindTexture(playerIn.getLocationSkin());

		if (gloves == null) {
			renderPlayer.renderFirstPersonArm(playerIn);

			return;
		}

		renderPlayer.renderFirstPersonArm(playerIn);

		this.mc.getTextureManager().bindTexture(((ItemAccessory) gloves.getItem()).texture);

		int colour = gloves.getItem().getColorFromItemStack(gloves, 0);
		float red = ((colour >> 16) & 0xff) / 255F;
		float green = ((colour >> 8) & 0xff) / 255F;
		float blue = (colour & 0xff) / 255F;

		if (gloves.getItem() != ItemsAether.phoenix_gloves) {
			GL11.glColor3f(red, green, blue);
		}

		GL11.glEnable(GL11.GL_BLEND);

		renderPlayer.modelBipedMain.onGround = 0.0F;
		renderPlayer.modelBipedMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, playerIn);
		renderPlayer.modelBipedMain.bipedRightArm.render(0.0625F);

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
	}

	@Override
	public void renderItemInFirstPerson(float partialTicks)
	{
		EntityClientPlayerMP player = this.mc.thePlayer;
		PlayerAether playerAether = PlayerAether.get(player);
		ItemStack gloves = playerAether.getAccessoryInventory().getStackInSlot(AccessoryType.GLOVES);
		ItemStack itemstack = this.itemToRender;

		super.renderItemInFirstPerson(partialTicks);

		if (gloves == null || (itemstack != null && !(itemstack.getItem() instanceof ItemMap)))
		{
			return;
		}

		float f1 = this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * partialTicks;
		float f2 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * partialTicks;

		RenderHelper.enableStandardItemLighting();

		int i = this.mc.theWorld.getLightBrightnessForSkyBlocks(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ), 0);
		int j = i % 65536;
		int k = i / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j / 1.0F, (float) k / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float f5;
		float f6;
		float f7;

		if (itemstack != null) {
			int l = itemstack.getItem().getColorFromItemStack(itemstack, 0);
			f5 = (float) (l >> 16 & 255) / 255.0F;
			f6 = (float) (l >> 8 & 255) / 255.0F;
			f7 = (float) (l & 255) / 255.0F;
			GL11.glColor4f(f5, f6, f7, 1.0F);
		} else {
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		}

		float f8;
		float f9;
		float f10;
		float f13;
		Render render;
		RenderPlayer renderplayer;

		if (itemstack != null && itemstack.getItem() instanceof ItemMap)
		{
			GL11.glPushMatrix();
			f13 = 0.8F;
			f5 = player.getSwingProgress(partialTicks);
			f6 = MathHelper.sin(f5 * (float) Math.PI);
			f7 = MathHelper.sin(MathHelper.sqrt_float(f5) * (float) Math.PI);
			GL11.glTranslatef(-f7 * 0.4F, MathHelper.sin(MathHelper.sqrt_float(f5) * (float) Math.PI * 2.0F) * 0.2F, -f6 * 0.2F);
			f5 = 1.0F - f2 / 45.0F + 0.1F;

			if (f5 < 0.0F) {
				f5 = 0.0F;
			}

			if (f5 > 1.0F) {
				f5 = 1.0F;
			}

			f5 = -MathHelper.cos(f5 * (float) Math.PI) * 0.5F + 0.5F;
			GL11.glTranslatef(0.0F, 0.0F * f13 - (1.0F - f1) * 1.2F - f5 * 0.5F + 0.04F, -0.9F * f13);
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(f5 * -85.0F, 0.0F, 0.0F, 1.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			this.mc.getTextureManager().bindTexture(player.getLocationSkin());

			for (int i1 = 0; i1 < 2; ++i1) {
				int j1 = i1 * 2 - 1;
				GL11.glPushMatrix();
				GL11.glTranslatef(-0.0F, -0.6F, 1.1F * (float) j1);
				GL11.glRotatef((float) (-45 * j1), 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef(59.0F, 0.0F, 0.0F, 1.0F);
				GL11.glRotatef((float) (-65 * j1), 0.0F, 1.0F, 0.0F);
				render = RenderManager.instance.getEntityRenderObject(this.mc.thePlayer);
				renderplayer = (RenderPlayer) render;
				f10 = 1.0F;
				GL11.glScalef(f10, f10, f10);
				this.renderFirstPersonArm(renderplayer, this.mc.thePlayer);
				GL11.glPopMatrix();
			}

			f6 = player.getSwingProgress(partialTicks);
			f7 = MathHelper.sin(f6 * f6 * (float) Math.PI);
			f8 = MathHelper.sin(MathHelper.sqrt_float(f6) * (float) Math.PI);
			GL11.glRotatef(-f7 * 20.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-f8 * 20.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(-f8 * 80.0F, 1.0F, 0.0F, 0.0F);
			f9 = 0.38F;
			GL11.glScalef(f9, f9, f9);
			GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-1.0F, -1.0F, 0.0F);
			f10 = 0.015625F;
			GL11.glScalef(f10, f10, f10);
			this.mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
			Tessellator tessellator = Tessellator.instance;
			GL11.glNormal3f(0.0F, 0.0F, -1.0F);
			tessellator.startDrawingQuads();
			byte b0 = 7;
			tessellator.addVertexWithUV((double) (0 - b0), (double) (128 + b0), 0.0D, 0.0D, 1.0D);
			tessellator.addVertexWithUV((double) (128 + b0), (double) (128 + b0), 0.0D, 1.0D, 1.0D);
			tessellator.addVertexWithUV((double) (128 + b0), (double) (0 - b0), 0.0D, 1.0D, 0.0D);
			tessellator.addVertexWithUV((double) (0 - b0), (double) (0 - b0), 0.0D, 0.0D, 0.0D);
			tessellator.draw();

			IItemRenderer custom = MinecraftForgeClient.getItemRenderer(itemstack, FIRST_PERSON_MAP);
			MapData mapdata = ((ItemMap) itemstack.getItem()).getMapData(itemstack, this.mc.theWorld);

			if (custom == null) {
				if (mapdata != null) {
					this.mc.entityRenderer.getMapItemRenderer().func_148250_a(mapdata, false);
				}
			} else {
				custom.renderItem(FIRST_PERSON_MAP, itemstack, mc.thePlayer, mc.getTextureManager(), mapdata);
			}

			GL11.glPopMatrix();
		}
		else if (!player.isInvisible())
		{
			GL11.glPushMatrix();
			f13 = 0.8F;
			f5 = player.getSwingProgress(partialTicks);
			f6 = MathHelper.sin(f5 * (float) Math.PI);
			f7 = MathHelper.sin(MathHelper.sqrt_float(f5) * (float) Math.PI);
			GL11.glTranslatef(-f7 * 0.3F, MathHelper.sin(MathHelper.sqrt_float(f5) * (float) Math.PI * 2.0F) * 0.4F, -f6 * 0.4F);
			GL11.glTranslatef(0.8F * f13, -0.75F * f13 - (1.0F - f1) * 0.6F, -0.9F * f13);
			GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			f5 = player.getSwingProgress(partialTicks);
			f6 = MathHelper.sin(f5 * f5 * (float) Math.PI);
			f7 = MathHelper.sin(MathHelper.sqrt_float(f5) * (float) Math.PI);
			GL11.glRotatef(f7 * 70.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-f6 * 20.0F, 0.0F, 0.0F, 1.0F);
			this.mc.getTextureManager().bindTexture(player.getLocationSkin());
			GL11.glTranslatef(-1.0F, 3.6F, 3.5F);
			GL11.glRotatef(120.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(200.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
			GL11.glScalef(1.0F, 1.0F, 1.0F);
			GL11.glTranslatef(5.6F, 0.0F, 0.0F);
			render = RenderManager.instance.getEntityRenderObject(this.mc.thePlayer);
			renderplayer = (RenderPlayer) render;
			f10 = 1.0F;
			GL11.glScalef(f10, f10, f10);
			this.renderFirstPersonArm(renderplayer, this.mc.thePlayer);
			GL11.glPopMatrix();
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
	}

	@Override
	public void updateEquippedItem() {
		super.updateEquippedItem();

		this.prevEquippedProgress = this.equippedProgress;
		EntityClientPlayerMP entityclientplayermp = this.mc.thePlayer;
		ItemStack itemstack = entityclientplayermp.inventory.getCurrentItem();
		boolean flag = this.equippedItemSlot == entityclientplayermp.inventory.currentItem && itemstack == this.itemToRender;

		if (this.itemToRender == null && itemstack == null) {
			flag = true;
		}

		if (itemstack != null && this.itemToRender != null && itemstack != this.itemToRender && itemstack.getItem() == this.itemToRender.getItem() && itemstack.getItemDamage() == this.itemToRender.getItemDamage()) {
			this.itemToRender = itemstack;
			flag = true;
		}

		float f = 0.4F;
		float f1 = flag ? 1.0F : 0.0F;
		float f2 = f1 - this.equippedProgress;

		if (f2 < -f) {
			f2 = -f;
		}

		if (f2 > f) {
			f2 = f;
		}

		this.equippedProgress += f2;

		if (this.equippedProgress < 0.1F) {
			this.itemToRender = itemstack;
			this.equippedItemSlot = entityclientplayermp.inventory.currentItem;
		}
	}

	@Override
	public void resetEquippedProgress() {
		super.resetEquippedProgress();

		this.equippedProgress = 0.0F;
	}

	@Override
	public void resetEquippedProgress2() {
		super.resetEquippedProgress2();

		this.equippedProgress = 0.0F;
	}

}