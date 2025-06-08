package com.darkbrook.island.common.util.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class EntityHelper
{
	
	public static <T extends Entity> List<T> radial(Class<T> clazz, Location location, double radius)
	{
		return location.getWorld().getNearbyEntities(location, radius, radius, radius).stream().filter(entity -> clazz.isInstance(entity)).map(entity -> clazz.cast(entity)).collect(Collectors.toList());
	}
	
}
