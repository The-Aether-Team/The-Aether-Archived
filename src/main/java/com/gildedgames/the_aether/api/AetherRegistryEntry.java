package com.gildedgames.the_aether.api;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLContainer;
import net.minecraftforge.fml.common.InjectedModContainer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.registries.IForgeRegistryEntry;


public class AetherRegistryEntry<T  extends IForgeRegistryEntry<T>>  implements IForgeRegistryEntry<T>
{

    private ResourceLocation registryName = null;

    @SuppressWarnings("unchecked")
	public T setRegistryName(String name)
    {
        if (getRegistryName() != null)
        {
            throw new IllegalStateException("Attempted to set registry name with existing registry name! New: " + name + " Old: " + getRegistryName());
        }

        int index = name.lastIndexOf(':');
        String oldPrefix = index == -1 ? "" : name.substring(0, index);

        name = index == -1 ? name : name.substring(index + 1);

        ModContainer mc = Loader.instance().activeModContainer();
        String prefix = mc == null || (mc instanceof InjectedModContainer && ((InjectedModContainer)mc).wrappedContainer instanceof FMLContainer) ? "minecraft" : mc.getModId().toLowerCase();

        if (!oldPrefix.equals(prefix) && oldPrefix.length() > 0)
        {
            //FMLLog.bigWarning("Dangerous alternative prefix `{}` for name `{}`, expected `{}` invalid registry invocation/invalid name?", oldPrefix, name, prefix);
            prefix = oldPrefix;
        }

        this.registryName = new ResourceLocation(prefix, name);

        return (T) this;
    }

    @Override
    public final T setRegistryName(ResourceLocation name)
    {
    	return setRegistryName(name.toString());
    }

    public final T setRegistryName(String modID, String name)
    { 
    	return setRegistryName(modID + ":" + name);
    }

    @Override
    public final ResourceLocation getRegistryName()
    {
        return this.registryName != null ? this.registryName : null;
    }

	@Override
    @SuppressWarnings("unchecked")
    public final Class<T> getRegistryType()
    { 
    	return (Class<T>) this.getClass();
    }

}