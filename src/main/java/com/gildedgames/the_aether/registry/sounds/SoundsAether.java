package com.gildedgames.the_aether.registry.sounds;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;

import com.gildedgames.the_aether.Aether;

public class SoundsAether
{

	public static SoundEvent moa_say, moa_flap, moa_egg;
	
	public static SoundEvent sheepuff_hurt, sheepuff_say, sheepuff_death;
	
	public static SoundEvent flyingcow_hurt, flyingcow_death, flyingcow_say;
	
	public static SoundEvent phyg_hurt, phyg_death, phyg_say;

	public static SoundEvent aerbunny_hurt, aerbunny_death, aerbunny_lift;

	public static SoundEvent aerwhale_call, aerwhale_death;

	public static SoundEvent swet_squish, swet_jump, swet_attack, swet_death;

	public static SoundEvent cockatrice_say, cockatrice_flap, cockatrice_attack;

	public static SoundEvent zephyr_call, zephyr_shoot;

	public static SoundEvent aechor_plant_attack;

	public static SoundEvent slider_collide, slider_move, slider_awaken, slider_death;

	public static SoundEvent sun_spirit_shoot;

	public static SoundEvent aether_tune, ascending_dawn, welcoming_skies, legacy;

	public static SoundEvent achievement_gen, achievement_bronze, achievement_silver;

	public static SoundEvent projectile_shoot, dart_shooter_shoot, aether_portal_idle, aether_portal_trigger, aether_portal_travel, dungeon_trap;

	public static SoundEvent aether1, aether2, aether3, aether4, aether_menu;

	public static IForgeRegistry<SoundEvent> soundRegistry;

	public static void initialization()
	{
		moa_say = register(Aether.locate("aemob.moa.say"));
		moa_flap = register(Aether.locate("aemob.moa.flap"));
		moa_egg = register(Aether.locate("aemob.moa.egg"));
		
		sheepuff_say = register(Aether.locate("aemob.sheepuff.say"));
		sheepuff_hurt = register(Aether.locate("aemob.sheepuff.hurt"));
		sheepuff_death = register(Aether.locate("aemob.sheepuff.death"));
		
		flyingcow_say = register(Aether.locate("aemob.flyingcow.say"));
		flyingcow_hurt = register(Aether.locate("aemob.flyingcow.hurt"));
		flyingcow_death = register(Aether.locate("aemob.flyingcow.death"));
		
		phyg_say = register(Aether.locate("aemob.phyg.say"));
		phyg_hurt = register(Aether.locate("aemob.phyg.hurt"));
		phyg_death = register(Aether.locate("aemob.phyg.death"));

		aerbunny_hurt = register(Aether.locate("aemob.aerbunny.hurt"));
		aerbunny_death = register(Aether.locate("aemob.aerbunny.death"));
		aerbunny_lift = register(Aether.locate("aemob.aerbunny.lift"));

		aerwhale_call = register(Aether.locate("aemob.aerwhale.call"));
		aerwhale_death = register(Aether.locate("aemob.aerwhale.death"));

		swet_squish = register(Aether.locate("aemob.swet.squish"));
		swet_jump = register(Aether.locate("aemob.swet.jump"));
		swet_attack = register(Aether.locate("aemob.swet.attack"));
		swet_death = register(Aether.locate("aemob.swet.death"));

		cockatrice_say = register(Aether.locate("aemob.cockatrice.say"));
		cockatrice_flap = register(Aether.locate("aemob.cockatrice.flap"));
		cockatrice_attack = register(Aether.locate("aemob.cockatrice.attack"));

		zephyr_call = register(Aether.locate("aemob.zephyr.call"));
		zephyr_shoot = register(Aether.locate("aemob.zephyr.shoot"));

		aechor_plant_attack = register(Aether.locate("aemob.aechor_plant.attack"));

		slider_awaken = register(Aether.locate("aeboss.slider.awaken"));
		slider_collide = register(Aether.locate("aeboss.slider.collide"));
		slider_move = register(Aether.locate("aeboss.slider.move"));
		slider_death = register(Aether.locate("aeboss.slider.death"));

		sun_spirit_shoot = register(Aether.locate("aeboss.sun_spirit.shoot"));

		projectile_shoot = register(Aether.locate("projectile.shoot"));
		dart_shooter_shoot = register(Aether.locate("projectile.dart_shooter.shoot"));

		aether_tune = register(Aether.locate("records.aether_tune"));
		ascending_dawn = register(Aether.locate("records.ascending_dawn"));
		welcoming_skies = register(Aether.locate("records.welcoming_skies"));
		legacy = register(Aether.locate("records.legacy"));
		
		aether_portal_idle = register(Aether.locate("aemisc.aether_portal.idle"));
		aether_portal_trigger = register(Aether.locate("aemisc.aether_portal.trigger"));
		aether_portal_travel = register(Aether.locate("aemisc.aether_portal.travel"));
		dungeon_trap = register(Aether.locate("aemisc.dungeon_trap.trigger"));

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

		if (soundRegistry != null)
		soundRegistry.register(sound.setRegistryName(location));

		return sound;
	}

}