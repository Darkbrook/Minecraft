package com.darkbrook.library.util.helper;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_13_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_13_R1.BlockPosition;
import net.minecraft.server.v1_13_R1.EntityPlayer;
import net.minecraft.server.v1_13_R1.WorldServer;

public class NmsHelper
{

	public static WorldServer world(World world)
	{
		return ((CraftWorld) world).getHandle();
	}
	
	public static WorldServer world(Location location)
	{
		return world(location.getWorld());
	}
	
	public static EntityPlayer player(Player player)
	{
		return ((CraftPlayer) player).getHandle();
	}
	
	public static BlockPosition position(Location location)
	{
		return new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}

}
