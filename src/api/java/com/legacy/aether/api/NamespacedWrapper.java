package com.legacy.aether.api;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.RegistryNamespaced;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryInternal;
import net.minecraftforge.registries.ILockableRegistry;
import net.minecraftforge.registries.RegistryManager;

import org.apache.commons.lang3.Validate;

class NamespacedWrapper<V extends IForgeRegistryEntry<V>> extends RegistryNamespaced<ResourceLocation, V> implements ILockableRegistry
{
    private boolean locked = false;
    private ForgeRegistry<V> delegate;

    public NamespacedWrapper(ForgeRegistry<V> owner)
    {
        this.delegate = owner;
    }

    @Override
    public void register(int id, ResourceLocation key, V value)
    {
        if (locked)
            throw new IllegalStateException("Can not register to a locked registry. Modder should use Forge Register methods.");

        Validate.notNull(value);

        if (value.getRegistryName() == null)
            value.setRegistryName(key);

        this.delegate.register(value);

        int realId = this.delegate.getID(value);
        if (realId != id && id != -1)
            FMLLog.log.warn("Registered object did not get ID it asked for. Name: {} Type: {} Expected: {} Got: {}", key, value.getRegistryType().getName(), id, realId);
    }

    /**
     * Register an object on this registry.
     */
    @Override
    public void putObject(ResourceLocation key, V value)
    {
        register(-1, key, value);
    }


    // Reading Functions
    @Override
    @Nullable
    public V getObject(@Nullable ResourceLocation name)
    {
        return this.delegate.getValue(name);
    }

    /**
     * Gets the name we use to identify the given object.
     */
    @Override
    @Nullable
    public ResourceLocation getNameForObject(V value)
    {
        return this.delegate.getKey(value);
    }

    /**
     * Does this registry contain an entry for the given key?
     */
    @Override
    public boolean containsKey(ResourceLocation key)
    {
        return this.delegate.containsKey(key);
    }

    /**
     * Gets the integer ID we use to identify the given object.
     */
    @Override
    public int getIDForObject(@Nullable V value)
    {
        return this.delegate.getID(value);
    }

    /**
     * Gets the object identified by the given ID.
     */
    @Override
    @Nullable
    public V getObjectById(int id)
    {
        return this.delegate.getValue(id);
    }

    @Override
    public Iterator<V> iterator()
    {
        return this.delegate.iterator();
    }

    /**
     * Gets all the keys recognized by this registry.
     */
    @Override
    public Set<ResourceLocation> getKeys()
    {
        return this.delegate.getKeys();
    }

    @Override
    @Nullable
    public V getRandomObject(Random random)
    {
        List<V> lst = this.delegate.getValues();
        if (lst.isEmpty())
            return null;
        return lst.get(random.nextInt(lst.size()));
    }

    //internal
    @Override
    public void lock(){ this.locked = true; }

    public static class Factory<V extends IForgeRegistryEntry<V>> implements IForgeRegistry.CreateCallback<V>
    {
        public static final ResourceLocation ID = new ResourceLocation("forge", "registry_defaulted_wrapper");
        @Override
        public void onCreate(IForgeRegistryInternal<V> owner, RegistryManager stage)
        {
            owner.setSlaveMap(ID, new NamespacedWrapper<V>((ForgeRegistry<V>)owner));
        }
    }
}