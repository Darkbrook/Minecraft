package com.darkbrook.island.common.gameplay.nms;

import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;

import com.darkbrook.island.common.registry.RegistryPlugin;

public class Metadata 
{
	
	public static Object getEntityMetadata(Entity entity, String key) 
	{
		return Metadata.hasEntityMetadata(entity, key) ? entity.getMetadata(key).get(0).value() : null;
	}
	
	public static void setEntityMetadata(Entity entity, String key, Object value) 
	{
		entity.setMetadata(key, new FixedMetadataValue(RegistryPlugin.getInstance(), value));
	}
	
	public static void removeEntityMetadata(Entity entity, String key) 
	{
		entity.removeMetadata(key, RegistryPlugin.getInstance());
	}
	
	public static boolean hasEntityMetadata(Entity entity, String key) 
	{
		return entity.hasMetadata(key);
	}

}
