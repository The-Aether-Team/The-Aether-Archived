package com.legacy.aether.server.world.util;

import net.minecraft.util.math.MathHelper;

@Deprecated /* REMOVE REMOVE REMOVE REMOVE!!! */
public class Direction
{
    public static final int[] offsetX = new int[] {0, -1, 0, 1};
    public static final int[] offsetZ = new int[] {1, 0, -1, 0};
    public static final String[] directions = new String[] {"SOUTH", "WEST", "NORTH", "EAST"};

    public static final int[] directionToFacing = new int[] {3, 4, 2, 5};

    public static final int[] facingToDirection = new int[] { -1, -1, 2, 0, 1, 3};

    public static final int[] rotateOpposite = new int[] {2, 3, 0, 1};

    public static final int[] rotateRight = new int[] {1, 2, 3, 0};

    public static final int[] rotateLeft = new int[] {3, 0, 1, 2};
    public static final int[][] bedDirection = new int[][] {{1, 0, 3, 2, 5, 4}, {1, 0, 5, 4, 2, 3}, {1, 0, 2, 3, 4, 5}, {1, 0, 4, 5, 3, 2}};

    public static int getMovementDirection(double p_82372_0_, double p_82372_2_)
    {
        return MathHelper.abs((float)p_82372_0_) > MathHelper.abs((float)p_82372_2_) ? (p_82372_0_ > 0.0D ? 1 : 3) : (p_82372_2_ > 0.0D ? 2 : 0);
    }
}