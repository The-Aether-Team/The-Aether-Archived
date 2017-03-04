package com.legacy.aether.client.gui.trivia;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.io.Charsets;
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
        IResource iresource = null;

        try
        {
            List<String> list = Lists.<String>newArrayList();
            iresource = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("aether_legacy", "texts/trivia.txt"));
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8));
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
            	return "Pro Tip: " + (String)list.get(random.nextInt(list.size()));
            }
        }
        catch (IOException var8)
        {
            ;
        }
        finally
        {
            IOUtils.closeQuietly((Closeable)iresource);
        }

        return "missingno";
    }

}