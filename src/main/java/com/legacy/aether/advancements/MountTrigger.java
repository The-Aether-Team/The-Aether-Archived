package com.legacy.aether.advancements;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

public class MountTrigger implements ICriterionTrigger<MountTrigger.Instance>
{
    private final Map<PlayerAdvancements, MountTrigger.Listeners> listeners = Maps.<PlayerAdvancements, MountTrigger.Listeners>newHashMap();
    private final ResourceLocation id;

    public MountTrigger(ResourceLocation id)
    {
        this.id = id;
    }

    public ResourceLocation getId()
    {
        return this.id;
    }

    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<MountTrigger.Instance> listener)
    {
        MountTrigger.Listeners killedtrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (killedtrigger$listeners == null)
        {
            killedtrigger$listeners = new MountTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, killedtrigger$listeners);
        }

        killedtrigger$listeners.add(listener);
    }

    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<MountTrigger.Instance> listener)
    {
        MountTrigger.Listeners killedtrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (killedtrigger$listeners != null)
        {
            killedtrigger$listeners.remove(listener);

            if (killedtrigger$listeners.isEmpty())
            {
                this.listeners.remove(playerAdvancementsIn);
            }
        }
    }

    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn)
    {
        this.listeners.remove(playerAdvancementsIn);
    }

    /**
     * Deserialize a ICriterionInstance of this trigger from the data in the JSON.
     */
    public MountTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context)
    {
        return new MountTrigger.Instance(this.id, EntityPredicate.deserialize(json.get("entity")));
    }

    public void trigger(EntityPlayerMP player, Entity entity)
    {
        MountTrigger.Listeners killedtrigger$listeners = this.listeners.get(player.getAdvancements());

        if (killedtrigger$listeners != null)
        {
            killedtrigger$listeners.trigger(player, entity);
        }
    }

    public static class Instance extends AbstractCriterionInstance
        {
            private final EntityPredicate entity;

            public Instance(ResourceLocation criterionIn, EntityPredicate entity)
            {
                super(criterionIn);

                this.entity = entity;
            }

            public boolean test(EntityPlayerMP player, Entity entity)
            {
                return this.entity.test(player, entity);
            }
        }

    static class Listeners
        {
            private final PlayerAdvancements playerAdvancements;
            private final Set<ICriterionTrigger.Listener<MountTrigger.Instance>> listeners = Sets.<ICriterionTrigger.Listener<MountTrigger.Instance>>newHashSet();

            public Listeners(PlayerAdvancements playerAdvancementsIn)
            {
                this.playerAdvancements = playerAdvancementsIn;
            }

            public boolean isEmpty()
            {
                return this.listeners.isEmpty();
            }

            public void add(ICriterionTrigger.Listener<MountTrigger.Instance> listener)
            {
                this.listeners.add(listener);
            }

            public void remove(ICriterionTrigger.Listener<MountTrigger.Instance> listener)
            {
                this.listeners.remove(listener);
            }

            public void trigger(EntityPlayerMP player, Entity entity)
            {
                List<ICriterionTrigger.Listener<MountTrigger.Instance>> list = null;

                for (ICriterionTrigger.Listener<MountTrigger.Instance> listener : this.listeners)
                {
                    if (((MountTrigger.Instance)listener.getCriterionInstance()).test(player, entity))
                    {
                        if (list == null)
                        {
                            list = Lists.<ICriterionTrigger.Listener<MountTrigger.Instance>>newArrayList();
                        }

                        list.add(listener);
                    }
                }

                if (list != null)
                {
                    for (ICriterionTrigger.Listener<MountTrigger.Instance> listener1 : list)
                    {
                        listener1.grantCriterion(this.playerAdvancements);
                    }
                }
            }
        }

}