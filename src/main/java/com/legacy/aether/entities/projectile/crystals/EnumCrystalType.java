package com.legacy.aether.entities.projectile.crystals;

public enum EnumCrystalType 
{

	FIRE("random.fizz", "largesmoke", "random.explode", "flame"), ICE("dig.glass", "", "dig.glass", "snowshovel"), THUNDER(), CLOUD("dig.glass", "", "dig.glass", "snowshovel");

	private String deathSound = "";

	private String deathParticle = "";

	private String explosionSound = "";

	private String explosionParticle = "";

	private EnumCrystalType()
	{
		
	}

	EnumCrystalType(String deathSound, String deathParticle, String explosionSound, String explosionParticle)
	{
		this.deathSound = deathSound;
		this.deathParticle = deathParticle;
		this.explosionSound = explosionSound;
		this.explosionParticle = explosionParticle;
	}

	public String getDeathSound()
	{
		return this.deathSound;
	}

	public String getDeathParticle()
	{
		return this.deathParticle;
	}

	public String getExplosionSound()
	{
		return this.explosionSound;
	}

	public String getExplosionParticle()
	{
		return this.explosionParticle;
	}

	public int getId()
	{
		return this.ordinal();
	}

	public static EnumCrystalType get(int id)
	{
		return values()[id];
	}
}