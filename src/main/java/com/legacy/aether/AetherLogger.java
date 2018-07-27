package com.legacy.aether;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AetherLogger
{
    public static final Logger log = LogManager.getLogger("[Aether Legacy]: ");
    public static final Logger logDev = LogManager.getLogger("[Aether Legacy Developer]: ");

    //Just what you'd expect.
    public static void print(Object message)
    {
        if(message.toString() != null)
        {
            log.info(message.toString());
        }
    }

    //This method only prints to console when the developer mode config setting is enabled.
    public static void printDev(Object messageDeveloper)
    {
        if (messageDeveloper.toString() == null)
        {
            //if(AetherConfig.developerMode())
            //{
                //logDev.info(messageDeveloper.toString());
            //}
        }
    }

    public static void printWarn(Object messageWarning)
    {
        if(messageWarning.toString() != null)
        {
            log.warn("[WARNING] " + messageWarning.toString());
        }
    }

    public static void printError(Object messageError)
    {
        if (messageError.toString() == null)
        {
            log.error("[ERROR] ");
        }
    }
}
