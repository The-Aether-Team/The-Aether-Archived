package com.gildedgames.the_aether.advancements;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

public class GravToolsetTrigger implements ICriterionTrigger<GravToolsetTrigger.Instance>
{
    private final Map<PlayerAdvancements, GravToolsetTrigger.Listeners> listeners = Maps.<PlayerAdvancements, GravToolsetTrigger.Listeners>newHashMap();
    private final ResourceLocation id;

    public GravToolsetTrigger(ResourceLocation id)
    {
        this.id = id;
    }

    public ResourceLocation getId()
    {
        return this.id;
    }

    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<GravToolsetTrigger.Instance> listener)
    {
        GravToolsetTrigger.Listeners killedtrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (killedtrigger$listeners == null)
        {
            killedtrigger$listeners = new GravToolsetTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, killedtrigger$listeners);
        }

        killedtrigger$listeners.add(listener);
    }

    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<GravToolsetTrigger.Instance> listener)
    {
        GravToolsetTrigger.Listeners killedtrigger$listeners = this.listeners.get(playerAdvancementsIn);

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
    public GravToolsetTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context)
    {
        return new GravToolsetTrigger.Instance(this.id);
    }

    public void trigger(EntityPlayerMP player)
    {
        GravToolsetTrigger.Listeners killedtrigger$listeners = this.listeners.get(player.getAdvancements());

        if (killedtrigger$listeners != null)
        {
            killedtrigger$listeners.trigger(player);
        }
    }

    public static class Instance extends AbstractCriterionInstance
        {
            public Instance(ResourceLocation criterionIn)
            {
                super(criterionIn);
            }

            public boolean test(EntityPlayerMP player)
            {
                return true;
            }
        }

    static class Listeners
        {
            private final PlayerAdvancements playerAdvancements;
            private final Set<ICriterionTrigger.Listener<GravToolsetTrigger.Instance>> listeners = Sets.<ICriterionTrigger.Listener<GravToolsetTrigger.Instance>>newHashSet();

            public Listeners(PlayerAdvancements playerAdvancementsIn)
            {
                this.playerAdvancements = playerAdvancementsIn;
            }

            public boolean isEmpty()
            {
                return this.listeners.isEmpty();
            }

            public void add(ICriterionTrigger.Listener<GravToolsetTrigger.Instance> listener)
            {
                this.listeners.add(listener);
            }

            public void remove(ICriterionTrigger.Listener<GravToolsetTrigger.Instance> listener)
            {
                this.listeners.remove(listener);
            }

            public void trigger(EntityPlayerMP player)
            {
                List<ICriterionTrigger.Listener<GravToolsetTrigger.Instance>> list = null;

                for (ICriterionTrigger.Listener<GravToolsetTrigger.Instance> listener : this.listeners)
                {
                    if (((GravToolsetTrigger.Instance)listener.getCriterionInstance()).test(player))
                    {
                        if (list == null)
                        {
                            list = Lists.<ICriterionTrigger.Listener<GravToolsetTrigger.Instance>>newArrayList();
                        }

                        list.add(listener);
                    }
                }

                if (list != null)
                {
                    for (ICriterionTrigger.Listener<GravToolsetTrigger.Instance> listener1 : list)
                    {
                        listener1.grantCriterion(this.playerAdvancements);
                    }
                }
            }
        }

}