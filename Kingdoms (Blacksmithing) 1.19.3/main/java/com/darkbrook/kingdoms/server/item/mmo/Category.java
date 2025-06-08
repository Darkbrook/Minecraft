package com.darkbrook.kingdoms.server.item.mmo;

import com.darkbrook.kingdoms.common.util.Color;
import com.darkbrook.kingdoms.common.util.ColorSupplier;

public enum Category implements ColorSupplier
{
	WORKSTATION("Workstation"),
	COMBUSTABLE("Combustable"),
	ORE("Ore"),
	MATERIAL("Material"),
	ARMOR("Armor"),
	TOOL("Tool"),
	WEAPON("Weapon");

	private final String displayName;

	private Category(String displayName)
	{
		this.displayName = displayName;
	}
	
	@Override
	public String toString()
	{
		return displayName;
	}

	@Override
	public Color getColor()
	{
		return Color.GRAY;
	}
}
