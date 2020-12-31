package com.gildedgames.the_aether.client;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class AetherKeybinds
{
    public static KeyBinding[] keyBindings = new KeyBinding[1];
    public static KeyBinding keyBindingAccessories = new KeyBinding("key.aether.accessory_menu", Keyboard.KEY_I, "key.aether.category");

    public static void initialization()
    {
        keyBindings[0] = keyBindingAccessories;

        for (int i = 0; i < keyBindings.length; ++i)
        {
            ClientRegistry.registerKeyBinding(keyBindings[i]);
        }
    }
}
