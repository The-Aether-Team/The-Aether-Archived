package com.gildedgames.the_aether.items.util;


public enum EnumGummySwetType {

	Blue(0, "blue"), Golden(1, "golden");

	private int meta;

	private String name;

	EnumGummySwetType(int meta, String name) {
		this.meta = meta;
		;
		this.name = name;
	}

	public static EnumGummySwetType getType(int meta) {
		return meta == 1 ? Golden : Blue;
	}

	public int getMeta() {
		return this.meta;
	}

	public String toString() {
		return this.name;
	}

}