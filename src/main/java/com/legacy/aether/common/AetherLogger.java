package com.legacy.aether.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AetherLogger
{
    public static final Logger log = LogManager.getLogger("[Aether Legacy]: ");
    public static final Logger logDev = LogManager.getLogger("[Aether Legacy Developer]: ");

    public static void print(Object message)
    {
        if(message.toString() != null)
        {
            log.info(message.toString());
        }
    }

    public static void printDev(Object messageDeveloper)
    {
        if (messageDeveloper.toString() == null)
        {
            logDev.info(messageDeveloper.toString());
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
