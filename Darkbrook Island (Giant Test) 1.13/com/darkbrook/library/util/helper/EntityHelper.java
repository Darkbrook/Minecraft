package com.darkbrook.library.util.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class EntityHelper
{
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> radial(Class<T> clazz, Location location, double radius)
	{
		Collection<Entity> entities = location.getWorld().getNearbyEntities(location, radius, radius, radius);
		List<T> nearby = new ArrayList<T>();
		
		for(Entity entity : entities) if(clazz.isInstance(entity))
		{
			nearby.add((T) entity);
		}
		
		return nearby;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static <T extends Entity> T spawn(Class<T> clazz, Location location)
	{
		String name = StringHelper.underscore(clazz.getSimpleName()).toUpperCase();
		return (T) location.getWorld().spawnEntity(location, EntityType.fromName(name));
	}

}
