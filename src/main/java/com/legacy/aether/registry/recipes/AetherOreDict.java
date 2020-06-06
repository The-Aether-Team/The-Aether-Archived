package com.legacy.aether.registry.recipes;

import com.legacy.aether.items.ItemsAether;
import net.minecraftforge.oredict.OreDictionary;

public class AetherOreDict
{
    public static void initialization()
    {
        OreDictionary.registerOre("egg", ItemsAether.moa_egg);
        OreDictionary.registerOre("listAllEgg", ItemsAether.moa_egg);
    }
}
