package com.legacy.aether.world.util;

import java.util.Random;

public class AetherRandomTracker
{
    public static AetherRandomTracker INSTANCE = new AetherRandomTracker();
    public int lastRand = -1;

    public int testRandom(Random random, int bound)
    {
        int inputRandom = random.nextInt(bound);

        if (inputRandom != this.lastRand)
        {
            return inputRandom;
        }
        else
        {
            testRandom(random, bound);
        }

        return -1;
    }
}
