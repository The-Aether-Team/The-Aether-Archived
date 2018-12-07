package com.legacy.aether.entities.hostile.swet;

public enum EnumSwetType {

    BLUE(), GOLDEN();

    EnumSwetType() {

    }

    public int getId() {
        return this.ordinal();
    }

    public static EnumSwetType get(int id) {
        return values()[id];
    }
}