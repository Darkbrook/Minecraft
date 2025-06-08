package com.darkbrook.library.gameplay.table.event.cancelable;

import org.bukkit.Material;

import com.darkbrook.library.gameplay.table.table.Table;

public class TableLightEvent extends CancelableTableEvent
{

	private Material material;
	private int radius;

	public TableLightEvent(Table table, Material material, int radius)
	{
		super(table);
		this.material = material;
		this.radius = radius;
	}

	public Material getMaterial()
	{
		return material;
	}

	public void setMaterial(Material material)
	{
		this.material = material;
	}

	public int getRadius()
	{
		return radius;
	}

	public void setRadius(int radius)
	{
		this.radius = radius;
	}

}
