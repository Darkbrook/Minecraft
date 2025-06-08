package com.darkbrook.kingdoms.server.item.mmo;

import com.darkbrook.kingdoms.common.util.Color;
import com.darkbrook.kingdoms.common.util.ColorSupplier;

public enum Quality implements ColorSupplier
{
	NOVICE("Novice", Color.GRAY),
	APPRENTICE("Apprentice", Color.GREEN),
	JOURNEYMAN("Journeyman", Color.DARK_PURPLE),
	MASTER("Master", Color.PINK),
	ARTISAN("Artisan", Color.GOLD);
	
	private final String displayName;
	private final Color color;
	
	Quality(String displayName, Color color)
	{
		this.displayName = displayName;
		this.color = color;
	}
			
	@Override
	public String toString()
	{
		return displayName;
	}
	
	@Override
	public Color getColor()
	{
		return color;
	}
}
