package com.legacy.aether.registry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.io.Charsets;

import cpw.mods.fml.common.registry.GameRegistry;

public class AetherLore 
{

	public static String getLoreEntry(ItemStack stack)
	{
		String languageCode = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
		ResourceLocation location = new ResourceLocation(GameRegistry.findUniqueIdentifierFor(stack.getItem()).modId, "lore/" + languageCode + "/" + stack.getUnlocalizedName().replace("item.", "").replace("tile.", "").replace(".", "_") + ".txt");
        BufferedReader bufferedreader = null;
        boolean failed = false;

        try
        {
           	StringBuilder stringBuilder = new StringBuilder();
            bufferedreader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream(), Charsets.UTF_8));
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
        catch (IOException ioexception1)
        {
        	failed = true;
            System.out.println("Cannot find lore entry for " + location.toString());
        }
        finally
        {
            if (bufferedreader != null)
            {
                try
                {
                    bufferedreader.close();
                }
                catch (IOException ioexception)
                {
                    ;
                }
            }
        }

        location = new ResourceLocation(GameRegistry.findUniqueIdentifierFor(stack.getItem()).modId, "lore/en_US/" + stack.getUnlocalizedName().replace("item.", "").replace("tile.", "").replace(".", "_") + ".txt");

        if (failed && languageCode != "en_US")
        {
            try
            {
               	StringBuilder stringBuilder = new StringBuilder();
                bufferedreader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream(), Charsets.UTF_8));
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
            catch (IOException ioexception1)
            {
                System.out.println("Cannot find backup lore entry for " + location.toString());
            }
            finally
            {
                if (bufferedreader != null)
                {
                    try
                    {
                        bufferedreader.close();
                    }
                    catch (IOException ioexception)
                    {
                        ;
                    }
                }
            }
        }

        return "missingno";
	}

	/*public static String getLoreEntry(ItemStack stack)
	{
		ResourceLocation location = new ResourceLocation(GameRegistry.findUniqueIdentifierFor(stack.getItem()).modId, "lore/" + Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode() + "/" + stack.getUnlocalizedName().replace("item.", "").replace("tile.", "").replace(".", "_") + ".txt");
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
	}*/

}