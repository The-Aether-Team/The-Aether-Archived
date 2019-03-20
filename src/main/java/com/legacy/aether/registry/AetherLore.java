package com.legacy.aether.registry;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.io.IOUtils;

public class AetherLore 
{

	public static String getLoreEntry(ItemStack stack)
	{
		ResourceLocation location = new ResourceLocation(stack.getItem().getRegistryName().getNamespace(), "lore/" + stack.getTranslationKey().replace("item.", "").replace("tile.", "").replace(".", "_") + ".txt");
        IResource iresource = null;

        try
        {
           	StringBuilder stringBuilder = new StringBuilder();
            iresource = Minecraft.getMinecraft().getResourceManager().getResource(location);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(iresource.getInputStream(), StandardCharsets.UTF_8));
            String s;

            while ((s = bufferedreader.readLine()) != null)
            {
                s = " " + s.trim();

                if (!s.isEmpty())
                {
                	stringBuilder.append(s);
                }
            }

            if (stringBuilder.length() != 0)
            {
            	return stringBuilder.toString();
            }
        }
        catch (IOException var8)
        {
            System.out.println("Cannot find lore entry for " + location.toString());
        }
        finally
        {
            IOUtils.closeQuietly((Closeable)iresource);
        }

        return "missingno";
	}

}