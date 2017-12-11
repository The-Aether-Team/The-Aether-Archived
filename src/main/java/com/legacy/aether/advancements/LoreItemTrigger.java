package com.legacy.aether.advancements;

import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

public class LoreItemTrigger implements ICriterionTrigger<LoreItemTrigger.Instance>
{
    private final Map<PlayerAdvancements, LoreItemTrigger.Listeners> listeners = Maps.<PlayerAdvancements, LoreItemTrigger.Listeners>newHashMap();
    private final ResourceLocation id;

    public LoreItemTrigger(ResourceLocation id)
    {
        this.id = id;
    }

    public ResourceLocation getId()
    {
        return this.id;
    }

    public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<LoreItemTrigger.Instance> listener)
    {
        LoreItemTrigger.Listeners killedtrigger$listeners = this.listeners.get(playerAdvancementsIn);

        if (killedtrigger$listeners == null)
        {
            killedtrigger$listeners = new LoreItemTrigger.Listeners(playerAdvancementsIn);
            this.listeners.put(playerAdvancementsIn, killedtrigger$listeners);
        }

        killedtrigger$listeners.add(listener);
    }

    public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<LoreItemTrigger.Instance> listener)
    {
        LoreItemTrigger.Listeners killedtrigger$listeners = this.listeners.get(playerAdvancementsIn);

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
    public LoreItemTrigger.Instance deserializeInstance(JsonObject json, JsonDeserializationContext context)
    {
        ItemPredicate itempredicate = ItemPredicate.deserialize(json.get("item"));

        return new LoreItemTrigger.Instance(this.id, itempredicate);
    }

    public void trigger(EntityPlayerMP player, ItemStack stack)
    {
        LoreItemTrigger.Listeners killedtrigger$listeners = this.listeners.get(player.getAdvancements());

        if (killedtrigger$listeners != null)
        {
            killedtrigger$listeners.trigger(stack);
        }
    }

    public static class Instance extends AbstractCriterionInstance
        {
            private final ItemPredicate item;

            public Instance(ResourceLocation criterionIn, ItemPredicate item)
            {
                super(criterionIn);

                this.item = item;
            }

            public boolean test(ItemStack stack)
            {
                return this.item.test(stack);
            }
        }

    static class Listeners
        {
            private final PlayerAdvancements playerAdvancements;
            private final Set<ICriterionTrigger.Listener<LoreItemTrigger.Instance>> listeners = Sets.<ICriterionTrigger.Listener<LoreItemTrigger.Instance>>newHashSet();

            public Listeners(PlayerAdvancements playerAdvancementsIn)
            {
                this.playerAdvancements = playerAdvancementsIn;
            }

            public boolean isEmpty()
            {
                return this.listeners.isEmpty();
            }

            public void add(ICriterionTrigger.Listener<LoreItemTrigger.Instance> listener)
            {
                this.listeners.add(listener);
            }

            public void remove(ICriterionTrigger.Listener<LoreItemTrigger.Instance> listener)
            {
                this.listeners.remove(listener);
            }

            public void trigger(ItemStack stack)
            {
                List<ICriterionTrigger.Listener<LoreItemTrigger.Instance>> list = null;

                for (ICriterionTrigger.Listener<LoreItemTrigger.Instance> listener : this.listeners)
                {
                    if (((LoreItemTrigger.Instance)listener.getCriterionInstance()).test(stack))
                    {
                        if (list == null)
                        {
                            list = Lists.<ICriterionTrigger.Listener<LoreItemTrigger.Instance>>newArrayList();
                        }

                        list.add(listener);
                    }
                }

                if (list != null)
                {
                    for (ICriterionTrigger.Listener<LoreItemTrigger.Instance> listener1 : list)
                    {
                        listener1.grantCriterion(this.playerAdvancements);
                    }
                }
            }
        }

}