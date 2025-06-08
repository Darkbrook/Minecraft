package com.darkbrook.library.gameplay.table.table;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import com.darkbrook.library.gameplay.player.CardinalDirection;

public enum TableDirection
{

	NORTH(new Vector(0.25D, -0.92D, 0.80D), 180.0F),
	SOUTH(new Vector(0.75D, -0.92D, 0.20D), 0.0F),
	EAST(new Vector(0.20D, -0.92D, 0.25D), 270.0f),
	WEST(new Vector(0.80D, -0.92D, 0.75D), 90.0F);

	private Vector offset;
	private float rotation;
	
	private TableDirection(Vector offset, float rotation)
	{
		this.offset = offset;
		this.rotation = rotation;
	}
	
	public void updateLocation(Location location, float offsetY)
	{
		location.add(offset);
		location.add(0, offsetY, 0);
		location.setYaw(rotation);
	}
	
	public static TableDirection fromDirection(CardinalDirection direction)
	{
		return valueOf(direction.name());
	}
		
}
