package com.darkbrook.library.gameplay.tileentity;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_13_R1.CraftWorld;

import net.minecraft.server.v1_13_R1.TileEntity;

public abstract class TileEntityWrapper<T>
{
	protected T entity;
	protected Location location;
	
	public TileEntityWrapper(Location location)
	{
		entity = loadTileEntity(((CraftWorld) location.getWorld()).getTileEntityAt(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
		this.location = location;
	}
	
	protected abstract T loadTileEntity(TileEntity entity);
}
