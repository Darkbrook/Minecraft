package com.darkbrook.kingdoms.server.item.mmo;

public enum ArmorSlot
{
	HELMET("Helmet"),
	CHESTPLATE("Chestplate"),
	LEGGINGS("Leggings"),
	BOOTS("Boots");
	
	private final String displayName;

	ArmorSlot(String displayName)
	{
		this.displayName = displayName;
	}
	
	@Override
	public String toString()
	{
		return displayName;
	}
}
