package com.gildedgames.the_aether.client.gui.toast;

import net.minecraft.advancements.Advancement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.toasts.AdvancementToast;
import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class GuiAetherToast extends GuiToast
{

	private final GuiToast original;

	public GuiAetherToast(Minecraft mcIn, GuiToast originalIn)
	{
		super(mcIn);

		this.original = originalIn;
	}

	@Override
    public void drawToast(ScaledResolution resolution)
    {
    	this.original.drawToast(resolution);
    }

	@Override
    public <T extends IToast> T getToast(Class <? extends T > p_192990_1_, Object p_192990_2_)
    {
    	return this.original.getToast(p_192990_1_, p_192990_2_);
    }

	@Override
    public void clear()
    {
    	this.original.clear();
    }

	@Override
    public void add(IToast toastIn)
    {
		if (toastIn instanceof AdvancementToast)
		{
			Advancement advancement = getToastAdvancement((AdvancementToast)toastIn);

			if (advancement.getId() != null && advancement.getId().getNamespace().equals("aether_legacy"))
			{
				String achievementName = advancement.getId().getPath();

				int achievementType = (achievementName.contains("bronze_dungeon") ? 1 : achievementName.contains("silver_dungeon") ? 2 : 0);

				toastIn = new AetherAdvancementToast(advancement, achievementType);
			}
		}

    	this.original.add(toastIn);
    }

	@Override
	public Minecraft getMinecraft()
	{
		return this.original.getMinecraft();
	}

	private static Advancement getToastAdvancement(AdvancementToast toast)
	{
		Advancement advancement = ReflectionHelper.getPrivateValue(AdvancementToast.class, toast, "advancement", "field_193679_c");

		return advancement;
	}

	public static final void overrideToastGui()
	{
		Minecraft mc = Minecraft.getMinecraft();
		GuiAetherToast toast = new GuiAetherToast(mc, mc.getToastGui());

		try
		{
			ReflectionHelper.setPrivateValue(Minecraft.class, mc, toast, "toastGui", "field_193034_aS");
		}
		catch (Exception e)
		{
			System.out.println("Aether Legacy failed to add toast override. Will ignore.");
		}
	}

}