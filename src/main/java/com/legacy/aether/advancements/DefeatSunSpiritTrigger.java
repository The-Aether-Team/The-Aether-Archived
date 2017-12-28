package com.legacy.aether.advancements;

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

public class DefeatSunSpiritTrigger implements ICriterionTrigger<DefeatSunSpiritTrigger.Instance>
{

    private final Map<PlayerAdvancements, DefeatSunSpiritTrigger.Listeners> listeners = Maps.<PlayerAdvancements, DefeatSunSpiritTrigger.Listeners>newHashMap();
    private final ResourceLocation id;

    public DefeatSunSpiritTrigger(ResourceLocation id)
    {
        this.id = id;
    }

    public ResourceLocation getId()
    {
        return this.id;
    }

    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<DefeatSunSpiritTrigger.Instance> listener)
    {
        DefeatSunSpiritTrigger.Listeners killedtrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (killedtrigger$listeners == null)
        {
            killedtrigger$listeners = new DefeatSunSpiritTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, killedtrigger$listeners);
        }

        killedtrigger$listeners.add(listener);
    }

    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<DefeatSunSpiritTrigger.Instance> listener)
    {
        DefeatSunSpiritTrigger.Listeners killedtrigger$listeners = this.listeners.get(playerAdvancementsIn);

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
    public DefeatSunSpiritTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context)
    {
        return new DefeatSunSpiritTrigger.Instance(this.id);
    }

    public void trigger(EntityPlayerMP player)
    {
        DefeatSunSpiritTrigger.Listeners killedtrigger$listeners = this.listeners.get(player.getAdvancements());

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
            private final Set<ICriterionTrigger.Listener<DefeatSunSpiritTrigger.Instance>> listeners = Sets.<ICriterionTrigger.Listener<DefeatSunSpiritTrigger.Instance>>newHashSet();

            public Listeners(PlayerAdvancements playerAdvancementsIn)
            {
                this.playerAdvancements = playerAdvancementsIn;
            }

            public boolean isEmpty()
            {
                return this.listeners.isEmpty();
            }

            public void add(ICriterionTrigger.Listener<DefeatSunSpiritTrigger.Instance> listener)
            {
                this.listeners.add(listener);
            }

            public void remove(ICriterionTrigger.Listener<DefeatSunSpiritTrigger.Instance> listener)
            {
                this.listeners.remove(listener);
            }

            public void trigger(EntityPlayerMP player)
            {
                List<ICriterionTrigger.Listener<DefeatSunSpiritTrigger.Instance>> list = null;

                for (ICriterionTrigger.Listener<DefeatSunSpiritTrigger.Instance> listener : this.listeners)
                {
                    if (((DefeatSunSpiritTrigger.Instance)listener.getCriterionInstance()).test(player))
                    {
                        if (list == null)
                        {
                            list = Lists.<ICriterionTrigger.Listener<DefeatSunSpiritTrigger.Instance>>newArrayList();
                        }

                        list.add(listener);
                    }
                }

                if (list != null)
                {
                    for (ICriterionTrigger.Listener<DefeatSunSpiritTrigger.Instance> listener1 : list)
                    {
                        listener1.grantCriterion(this.playerAdvancements);
                    }
                }
            }
        }

}