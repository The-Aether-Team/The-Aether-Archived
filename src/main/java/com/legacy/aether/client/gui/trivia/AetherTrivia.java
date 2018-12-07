package com.legacy.aether.client.gui.trivia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;

import com.google.common.collect.Lists;
import com.legacy.aether.Aether;

public class AetherTrivia
{

    private static Random random = new Random();

    public AetherTrivia()
    {
    	
    }

    public static String getNewTrivia() 
    {
    	 BufferedReader bufferedreader = null;

        try
        {
            List<String> list = Lists.<String>newArrayList();
            bufferedreader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(Aether.locate("texts/trivia.txt")).getInputStream(), StandardCharsets.UTF_8));
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

        return "missingno";
    }

}