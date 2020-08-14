package com.gildedgames.the_aether.events;

import net.minecraftforge.fml.common.eventhandler.Event;

public class BronzeDungeonSizeEvent extends Event
{
    private int dungeonRoomMaximum;

    public BronzeDungeonSizeEvent(int dungeonRoomMaximum)
    {
        this.dungeonRoomMaximum = dungeonRoomMaximum;
    }

    public int getDungeonRoomMaximum()
    {
        return this.dungeonRoomMaximum;
    }

    public void setDungeonRoomMaximum(int dungeonRoomMaximum)
    {
        this.dungeonRoomMaximum = dungeonRoomMaximum;
    }
}
