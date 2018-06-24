package com.legacy.aether.universal.crafttweaker;

import crafttweaker.IAction;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegisterAction<T extends IForgeRegistryEntry<T>> implements IAction {
    private IForgeRegistry<T> registry;
    private T t;

    public RegisterAction(IForgeRegistry<T> registry, T t) {
        this.registry = registry;
        this.t = t;
    }

    @Override
    public void apply() {
        registry.register(t);
    }

    @Override
    public String describe() {
        return t.toString();
    }
}
