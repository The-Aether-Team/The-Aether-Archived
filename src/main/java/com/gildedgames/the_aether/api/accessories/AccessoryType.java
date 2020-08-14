package com.gildedgames.the_aether.api.accessories;

import net.minecraft.util.ObjectIntIdentityMap;

public enum AccessoryType {
	RING("ring", 11, 3),
	EXTRA_RING("ring", 11, 3),
	PENDANT("pendant", 16, 7),
	CAPE("cape", 15, 5),
	SHIELD("shield", 13, 0),
	GLOVES("gloves", 10, 0),
	MISC("misc", 10, 0),
	EXTRA_MISC("misc", 10, 0);

	private int maxDamage, damagedReduced;

	private String displayName;

	AccessoryType(String displayName, int maxDamage, int damageReduced) {
		this.displayName = displayName;
		this.maxDamage = maxDamage;
		this.damagedReduced = damageReduced;
	}

	public int getMaxDamage() {
		return this.maxDamage;
	}

	public int getDamageReduced() {
		return this.damagedReduced;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public static ObjectIntIdentityMap createCompleteList() {
		ObjectIntIdentityMap identityMap = new ObjectIntIdentityMap();

		identityMap.func_148746_a(PENDANT, 0);
		identityMap.func_148746_a(CAPE, 1);
		identityMap.func_148746_a(SHIELD, 2);
		identityMap.func_148746_a(MISC, 3);
		identityMap.func_148746_a(RING, 4);
		identityMap.func_148746_a(EXTRA_RING, 5);
		identityMap.func_148746_a(GLOVES, 6);
		identityMap.func_148746_a(EXTRA_MISC, 7);

		return identityMap;

	}

}