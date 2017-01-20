package com.legacy.aether.server.registry.sounds;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.legacy.aether.server.Aether;

public class SoundsAether
{

	public static SoundEvent moa_say;

	public static SoundEvent aerbunny_hurt, aerbunny_death, aerbunny_lift;

	public static SoundEvent aerwhale_call, aerwhale_death;

	public static SoundEvent zephyr_call, zephyr_shoot;

	public static SoundEvent slider_collide, slider_move, slider_awaken, slider_death;

	public static SoundEvent aether_tune, ascending_dawn, welcoming_skies;

	public static SoundEvent achievement_gen, achievement_bronze, achievement_silver;

	public static SoundEvent projectile_shoot, dart_shooter_shoot;

	public static SoundEvent aether1, aether2, aether3, aether4, aether_menu;

	public static void initialization()
	{
		moa_say = register(Aether.locate("aemob.moa.say"));

		aerbunny_hurt = register(Aether.locate("aemob.aerbunny.hurt"));
		aerbunny_death = register(Aether.locate("aemob.aerbunny.death"));
		aerbunny_lift = register(Aether.locate("aemob.aerbunny.lift"));

		aerwhale_call = register(Aether.locate("aemob.aerwhale.call"));
		aerwhale_death = register(Aether.locate("aemob.aerwhale.death"));

		zephyr_call = register(Aether.locate("aemob.zephyr.call"));
		zephyr_shoot = register(Aether.locate("aemob.zephyr.shoot"));

		slider_awaken = register(Aether.locate("aeboss.slider.awaken"));
		slider_collide = register(Aether.locate("aeboss.slider.collide"));
		slider_move = register(Aether.locate("aeboss.slider.move"));
		slider_death = register(Aether.locate("aeboss.slider.death"));

		projectile_shoot = register(Aether.locate("projectile.shoot"));
		dart_shooter_shoot = register(Aether.locate("projectile.dart_shooter.shoot"));

		aether_tune = register(Aether.locate("records.aether_tune"));
		ascending_dawn = register(Aether.locate("records.ascending_dawn"));
		welcoming_skies = register(Aether.locate("records.welcoming_skies"));

		achievement_gen = register(Aether.locate("achievement"));
		achievement_bronze = register(Aether.locate("achievement_bronze"));
		achievement_silver = register(Aether.locate("achievement_silver"));

		aether1 = register(Aether.locate("music.aether1"));
		aether2 = register(Aether.locate("music.aether2"));
		aether3 = register(Aether.locate("music.aether3"));
		aether4 = register(Aether.locate("music.aether4"));

		aether_menu = register(Aether.locate("music.menu"));
	}

	public static SoundEvent register(ResourceLocation location)
	{
		SoundEvent sound = new SoundEvent(location);

		return GameRegistry.register(sound, location);
	}

}