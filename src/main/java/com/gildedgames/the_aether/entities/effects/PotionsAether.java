package com.gildedgames.the_aether.entities.effects;

import net.minecraft.potion.Potion;
import net.minecraftforge.registries.IForgeRegistry;

public class PotionsAether
{
    public static IForgeRegistry<Potion> potionRegistry;

    public static Potion INEBRIATION = new PotionInebriation();

    public static void initialization()
    {
        potionRegistry.register(INEBRIATION.setPotionName("effect.aether.inebriation").setRegistryName("inebriation"));
    }
}
