package com.darkbrook.island.common.util.helper.math;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_14_R1.BlockPosition;

public class Vector3f
{
	
	public float x;
	public float y;
	public float z;
	
	public Vector3f(Location location)
	{
		this(location.getX(), location.getY(), location.getZ());
	}
	
	public Vector3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3f(double x, double y, double z)
	{
		this((float) x, (float) y, (float) z);
	}
	
	public Vector3f(int x, int y, int z)
	{
		this((float) x, (float) y, (float) z);
	}
	
	public Location getLocation(World world)
	{
		return new Location(world, x, y, z);
	}
	
	public BlockPosition getBlockPosition()
	{
		return new BlockPosition(x, y, z);
	}
	
	public Vector getVector()
	{
		return new Vector(x, y, z);
	}
	
}
