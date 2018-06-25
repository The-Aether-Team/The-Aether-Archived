package com.legacy.aether.universal.crafttweaker;


import com.legacy.aether.Aether;
import com.legacy.aether.api.accessories.AccessoryType;
import com.legacy.aether.api.accessories.AetherAccessory;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.aether_legacy.Accessory")
public class Accessory {

    public static final IForgeRegistry<AetherAccessory> accessories = GameRegistry.findRegistry(AetherAccessory.class);

    private static final AccessoryType[] VALUES = AccessoryType.values();

    private static AccessoryType getFromName(String accessory) {
        for (AccessoryType value : VALUES) {
            if (value.getDisplayName().equalsIgnoreCase(accessory)) {
                return value;
            }
        }
        return null;
    }

    @ZenMethod
    public static void registerAccessory(IItemStack input, String accessory) {
        CraftTweakerAPI.apply(new RegisterAction<>(accessories, new AetherAccessory(CraftTweakerMC.getItemStack(input), getFromName(accessory))));
    }


}
