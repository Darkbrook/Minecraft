package com.darkbrook.library.entity;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import com.darkbrook.library.plugin.PluginGrabber;

public class Metadata {
	
	public static void setEntityMetadata(Entity entity, String key, Object value) {
		entity.setMetadata(key, new FixedMetadataValue(PluginGrabber.getPlugin(), value));
	}
	
	public static boolean hasEntityMetadata(Entity entity, String key) {
		return entity.hasMetadata(key);
	}
	
	public static Object getEntityMetadata(Entity entity, String key) {
		if(!hasEntityMetadata(entity, key)) return null;
		List<MetadataValue> values = entity.getMetadata(key);
		for(MetadataValue value : values) return value.value();
		return null;
	}
	
	public static void removeEntityMetadata(Entity entity, String key) {
		entity.removeMetadata(key, PluginGrabber.getPlugin());
	}

}
