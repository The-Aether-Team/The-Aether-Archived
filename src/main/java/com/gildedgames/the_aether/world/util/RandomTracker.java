package com.gildedgames.the_aether.world.util;

import java.util.Random;

public class RandomTracker
{
    public int lastRand = -1;

    public int testRandom(Random random, int bound)
    {
        int inputRandom = random.nextInt(bound);

        if (inputRandom != this.lastRand)
        {
            this.lastRand = inputRandom;
            return inputRandom;
        }
        else
        {
            testRandom(random, bound);
        }

        return -1;
    }
}