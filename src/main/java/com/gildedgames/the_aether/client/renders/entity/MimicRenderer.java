package com.gildedgames.the_aether.client.renders.entity;

import java.util.Calendar;

import com.gildedgames.the_aether.Aether;
import com.gildedgames.the_aether.client.models.entities.MimicModel;
import com.gildedgames.the_aether.entities.hostile.EntityMimic;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class MimicRenderer extends RenderLiving {

	private MimicModel modelbase;

	public MimicRenderer() {
		super(new MimicModel(), 0.0F);

		this.modelbase = (MimicModel) this.mainModel;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float pitch, float yaw) {
		EntityMimic mimic = (EntityMimic) entity;

		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glRotatef(180F - pitch, 0.0F, 1.0F, 0.0F);
		GL11.glScalef(-1F, -1F, 1.0F);

		this.modelbase.setRotationAngles(0, 0F, 0.0F, 0.0F, 0.0F, 0.0F, mimic);

		if (mimic.hurtResistantTime > 11) {
			GL11.glColor3f(1.0F, 0.5F, 0.5F);
		} else {
			GL11.glColor3f(1.0F, 1.0F, 1.0F);
		}

		Calendar calendar = Calendar.getInstance();

		if (calendar.get(2) + 1 == 12 && calendar.get(5) >= 24 && calendar.get(5) <= 26) {
			this.renderManager.renderEngine.bindTexture(Aether.locate("textures/entities/mimic/mimic_head_christmas.png"));
			this.modelbase.renderHead(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, mimic);
			this.renderManager.renderEngine.bindTexture(Aether.locate("textures/entities/mimic/mimic_legs_christmas.png"));
			this.modelbase.renderLegs(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, mimic);
		} else {
			this.renderManager.renderEngine.bindTexture(Aether.locate("textures/entities/mimic/mimic_head.png"));
			this.modelbase.renderHead(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, mimic);
			this.renderManager.renderEngine.bindTexture(Aether.locate("textures/entities/mimic/mimic_legs.png"));
			this.modelbase.renderLegs(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, mimic);
		}

		GL11.glPopMatrix();
	}

	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

}