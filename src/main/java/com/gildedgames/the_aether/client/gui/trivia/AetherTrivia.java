package com.gildedgames.the_aether.client.gui.trivia;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

import net.minecraft.util.text.TextComponentTranslation;
import org.apache.commons.io.IOUtils;

import com.google.common.collect.Lists;

public class AetherTrivia
{

    private static Random random = new Random();

    public AetherTrivia()
    {
    	
    }

    public static String getNewTrivia() 
    {
        String localization = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();

        if (getEntriesForLocalization(localization) != null)
        {
            return getEntriesForLocalization(localization);
        }
        else if (getEntriesForLocalization("en_us") != null)
        {
            return getEntriesForLocalization("en_us");
        }
        else
        {
            return "missingno";
        }
    }

    public static String getEntriesForLocalization(String localization)
    {
        IResource iresource = null;

        try
        {
            List<String> list = Lists.newArrayList();
            iresource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("aether_legacy", "texts/" + localization + ".txt"));
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(iresource.getInputStream(), StandardCharsets.UTF_8));
            String s;

            while ((s = bufferedreader.readLine()) != null)
            {
                s = s.trim();

                if (!s.isEmpty())
                {
                    list.add(s);
                }
            }

            if (!list.isEmpty())
            {
                return I18n.format("gui.aether_trivia.pro_tip") + " " + list.get(random.nextInt(list.size()));
            }
        }
        catch (IOException ignore) { }
        finally
        {
            IOUtils.closeQuietly(iresource);
        }

        return null;
    }
}