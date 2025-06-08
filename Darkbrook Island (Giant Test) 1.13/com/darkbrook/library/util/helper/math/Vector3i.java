package com.darkbrook.library.util.helper.math;

import org.bukkit.Location;
import org.bukkit.World;

import net.minecraft.server.v1_13_R1.BlockPosition;

public class Vector3i
{

	public int x;
	public int y;
	public int z;
	
	public Vector3i(Location location)
	{
		this(location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}
	
	public Vector3i(int x, int y, int z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3i copy()
	{
		return new Vector3i(x, y, z);
	}
	
	public Vector3i add(Vector3i vector)
	{
		x += vector.x;
		y += vector.y;
		z += vector.z;
		
		return this;
	}
	
	public Vector3i sub(Vector3i vector)
	{
		x -= vector.x;
		y -= vector.y;
		z -= vector.z;
		
		return this;
	}
	
	public Location getLocation(World world)
	{
		return new Location(world, x, y, z);
	}
	
	public BlockPosition getBlockPosition()
	{
		return new BlockPosition(x, y, z);
	}
	
}
