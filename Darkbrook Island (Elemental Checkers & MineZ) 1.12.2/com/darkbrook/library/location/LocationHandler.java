package com.darkbrook.library.location;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocationHandler {
	
	public static Location getOffsetLocation(Location location, double xOffset, double yOffset, double zOffset) {
		return new Location(location.getWorld(), location.getX() + xOffset, location.getY() + yOffset, location.getZ() + zOffset);
	}
	
	public static Location getBlockLocation(Location location) {
		return new Location(location.getWorld(), location.getBlockX() + 0.5, location.getBlockY(), location.getBlockZ() + 0.5);
	}
	
	public static Location getOffsetBlockLocation(Location location, double xOffset, double yOffset, double zOffset) {
		location = getOffsetLocation(getBlockLocation(location), xOffset, yOffset, zOffset);
		return location;	
	}
	
	public static List<Location> getPlatform(Player player, int radius, int yOffset) {
		List<Location> locations = new ArrayList<Location>();
		for(int x = player.getLocation().getBlockX() + radius; x >= player.getLocation().getBlockX() - radius; x--) for(int z = player.getLocation().getBlockZ() + radius; z >= player.getLocation().getBlockZ() - radius; z--) locations.add(new Location(player.getWorld(), x, player.getLocation().getY() - yOffset, z));
		return locations;
	}

}
