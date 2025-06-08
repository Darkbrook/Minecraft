package com.darkbrook.library.event.tick.async;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import com.darkbrook.library.event.DarkbrookEvent;
import com.darkbrook.library.plugin.registry.IRegistryValue;
import com.darkbrook.library.util.runnable.ConstantRunnable;
import com.darkbrook.library.util.runnable.RunnableState;

public class AsyncEntityTickCycleEvent extends DarkbrookEvent implements IRegistryValue
{

	private World world;
	private Entity entity;

	public AsyncEntityTickCycleEvent()
	{
		ConstantRunnable.execute(() -> { for(World world : Bukkit.getWorlds()) for(Entity entity : world.getEntities()) new AsyncEntityTickCycleEvent(world, entity).register(); }, RunnableState.ASYNC, 20, 0);
	}
	
	public AsyncEntityTickCycleEvent(World world, Entity entity)
	{
		this.world = world;
		this.entity = entity;
	}
	
	public World getWorld()
	{
		return world;
	}
	
	public Entity getEntity()
	{
		return entity;
	}
	
}
