package com.legacy.aether.universal.crafttweaker;


import crafttweaker.CraftTweakerAPI;

public class AetherCraftTweakerPlugin {

    public static void preInitialization() {
        CraftTweakerAPI.registerClass(Accessory.class);
        CraftTweakerAPI.registerClass(Enchanter.class);
        CraftTweakerAPI.registerClass(Freezer.class);
    }

}
