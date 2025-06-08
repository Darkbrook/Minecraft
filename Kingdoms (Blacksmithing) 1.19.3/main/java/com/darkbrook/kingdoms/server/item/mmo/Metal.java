package com.darkbrook.kingdoms.server.item.mmo;

import com.darkbrook.kingdoms.common.item.nbt.CustomModelData;
import com.darkbrook.kingdoms.common.util.Color;
import com.darkbrook.kingdoms.common.util.ColorSupplier;

public enum Metal implements ColorSupplier, CustomModelData.Supplier
{
	COPPER("Copper", Color.COPPER, 100, 1000),
	BRONZE("Bronze", Color.BRONZE, 200, 1200),
	IRON("Iron", Color.IRON, 300, 1400),
	STEEL("Steel", Color.STEEL, 400, 1600),
	PLATINUM("Platinum", Color.PLATINUM, 500, 1800),
	METEORIC_IRON("Meteoric Iron", Color.METEORIC_IRON, 600, 2000);

	private final String displayName;
	private final Color color;
	private final int customModelData;
	private final int meltingPoint;

	Metal(String displayName, Color color, int customModelData, int meltingPoint)
	{
		this.displayName = displayName;
		this.color = color;
		this.customModelData = customModelData;
		this.meltingPoint = meltingPoint;
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
		
	@Override
	public int getCustomModelData()
	{
		return customModelData;
	}
	
	public int getMeltingPoint()
	{
		return meltingPoint;
	}
}
