package com.gildedgames.the_aether.player.perks.util;

public enum EnumAetherPerkType {
    Information(0), Halo(1), Moa(2), Glow(3);

    private int perkID;

    EnumAetherPerkType(int id) {
        this.perkID = id;
    }

    public int getPerkID() {
        return this.perkID;
    }

    public static EnumAetherPerkType getPerkByID(int id) {
        return id == 0 ? Information : id == 1 ? Halo : id == 2 ? Moa : Glow;
    }
}