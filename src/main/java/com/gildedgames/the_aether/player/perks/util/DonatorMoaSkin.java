package com.gildedgames.the_aether.player.perks.util;

import io.netty.buffer.ByteBuf;

public class DonatorMoaSkin {

    private boolean shouldDefualt;

    private int wingColor;

    private int wingMarkingColor;

    private int bodyColor;

    private int markingColor;

    private int eyeColor;

    private int outsideColor;

    public DonatorMoaSkin() {
        this.shouldDefualt = true;
    }

    public void writeMoaSkin(ByteBuf buf) {
        buf.writeBoolean(this.shouldDefualt);
        buf.writeInt(this.wingColor);
        buf.writeInt(this.wingMarkingColor);
        buf.writeInt(this.bodyColor);
        buf.writeInt(this.markingColor);
        buf.writeInt(this.eyeColor);
        buf.writeInt(this.outsideColor);
    }

    public static DonatorMoaSkin readMoaSkin(ByteBuf buf) {
        DonatorMoaSkin skin = new DonatorMoaSkin();

        skin.shouldUseDefualt(buf.readBoolean());
        skin.setWingColor(buf.readInt());
        skin.setWingMarkingColor(buf.readInt());
        skin.setBodyColor(buf.readInt());
        skin.setMarkingColor(buf.readInt());
        skin.setEyeColor(buf.readInt());
        skin.setOutsideColor(buf.readInt());

        return skin;
    }

    public void setWingColor(int color) {
        this.wingColor = color;
    }

    public int getWingColor() {
        return this.wingColor;
    }

    public void setWingMarkingColor(int color) {
        this.wingMarkingColor = color;
    }

    public int getWingMarkingColor() {
        return this.wingMarkingColor;
    }

    public void setBodyColor(int color) {
        this.bodyColor = color;
    }

    public int getBodyColor() {
        return this.bodyColor;
    }

    public void setMarkingColor(int color) {
        this.markingColor = color;
    }

    public int getMarkingColor() {
        return this.markingColor;
    }

    public void setEyeColor(int color) {
        this.eyeColor = color;
    }

    public int getEyeColor() {
        return this.eyeColor;
    }

    public void setOutsideColor(int color) {
        this.outsideColor = color;
    }

    public int getOutsideColor() {
        return this.outsideColor;
    }

    public void shouldUseDefualt(boolean defualt) {
        this.shouldDefualt = defualt;
    }

    public boolean shouldUseDefualt() {
        return this.shouldDefualt;
    }

}