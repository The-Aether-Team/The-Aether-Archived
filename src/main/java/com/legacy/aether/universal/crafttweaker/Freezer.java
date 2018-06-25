package com.legacy.aether.universal.crafttweaker;


import com.legacy.aether.api.freezables.AetherFreezable;
import com.legacy.aether.api.freezables.AetherFreezableFuel;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.aether_legacy.Freezer")
public class Freezer {

    public static final IForgeRegistry<AetherFreezable> freezables = GameRegistry.findRegistry(AetherFreezable.class);
    public static final IForgeRegistry<AetherFreezableFuel> freezableFuels = GameRegistry.findRegistry(AetherFreezableFuel.class);

    @ZenMethod
    public static void registerFreezable(IItemStack input, IItemStack output, int timeRequired) {
        CraftTweakerAPI.apply(new RegisterAction<>(freezables, new AetherFreezable(CraftTweakerMC.getItemStack(input), CraftTweakerMC.getItemStack(output), timeRequired)));
    }

    @ZenMethod
    public static void registerFreezerFuel(IItemStack input, int timeGiven) {
        CraftTweakerAPI.apply(new RegisterAction<>(freezableFuels, new AetherFreezableFuel(CraftTweakerMC.getItemStack(input), timeGiven)));
    }


}
