package com.gildedgames.the_aether.advancements;

import java.lang.reflect.Method;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import com.gildedgames.the_aether.Aether;

public class AetherAdvancements 
{
	public static MountTrigger MOUNT_TRIGGER;

	public static LoreItemTrigger LORE_ITEM_TRIGGER;

	public static GravToolsetTrigger GRAV_TOOLSET_TRIGGER;

	public static CraftItemTrigger CRAFT_ITEM_TRIGGER;

	public static DefeatSunSpiritTrigger DEFEAT_SUN_SPIRIT_TRIGGER;

    @SuppressWarnings("unchecked")
	private static <T extends ICriterionTrigger<?>> T register(T criterion)
    {
    	Method method = ReflectionHelper.findMethod(CriteriaTriggers.class, "register", "func_192118_a", ICriterionTrigger.class);
    	method.setAccessible(true);

		try 
		{
			criterion = (T) method.invoke(null, criterion);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

    	return criterion;
    }

    public static void initialization()
    {
    	MOUNT_TRIGGER = register(new MountTrigger(Aether.locate("mount_entity")));
    	LORE_ITEM_TRIGGER = register(new LoreItemTrigger(Aether.locate("lore_item")));
    	GRAV_TOOLSET_TRIGGER = register(new GravToolsetTrigger(Aether.locate("gravitite_toolset")));
    	CRAFT_ITEM_TRIGGER = register(new CraftItemTrigger(Aether.locate("craft_item")));
    	DEFEAT_SUN_SPIRIT_TRIGGER = register(new DefeatSunSpiritTrigger(Aether.locate("defeat_sun_spirit")));
    }

}