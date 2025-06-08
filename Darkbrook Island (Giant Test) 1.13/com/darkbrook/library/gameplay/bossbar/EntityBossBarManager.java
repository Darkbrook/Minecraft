package com.darkbrook.library.gameplay.bossbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.darkbrook.library.event.tick.async.AsyncEntityTickCycleEvent;
import com.darkbrook.library.plugin.DarkbrookPlugin;
import com.darkbrook.library.plugin.registry.IRegistryValue;

public class EntityBossBarManager implements Listener, IRegistryValue
{
	
	private static final Map<Class<?>, List<Entity>> entityMapping = new HashMap<Class<?>, List<Entity>>();
	private static final Map<Class<?>, EntityBossBarPreset> presetMapping = new HashMap<Class<?>, EntityBossBarPreset>();
	private static final List<EntityBossBar> bars = new ArrayList<EntityBossBar>();
	
	public EntityBossBarManager(Class<?>[] entities, EntityBossBarPreset[] presets)
	{
		
		for(int i = 0; i < entities.length; i++) 
		{
			entityMapping.put(entities[i], new ArrayList<Entity>());
			presetMapping.put(entities[i], presets[i]);
		}
		
	}
	
	@EventHandler
	public void onAsyncEntityFind(AsyncEntityTickCycleEvent event)
	{
		
		Entity entity = event.getEntity();
		Class<?> clazz = entity.getClass();
		
		List<Entity> entities = entityMapping.get(clazz);
		
		if(entities != null && !entities.contains(entity))
		{
			entities.add(entity);
			register(presetMapping.get(clazz).createBossBar((LivingEntity) entity));
		}
		
	}
	
	public static void unregisterAll()
	{
		for(EntityBossBar bar : bars) 
		{
			unregister(bar, false);
		}
		
		bars.clear();
	}
	
	public static void register(EntityBossBar bar)
	{
		DarkbrookPlugin.registerStatic(bar);
		bars.add(bar);
	}
	
	public static void unregister(EntityBossBar bar)
	{
		unregister(bar, true);
	}
	
	private static void unregister(EntityBossBar bar, boolean isIndependent)
	{
		DarkbrookPlugin.unregisterStatic(bar);
		
		if(isIndependent)
		{
			bars.remove(bar);
		}
		
		bar.clearBar();
	}

}
