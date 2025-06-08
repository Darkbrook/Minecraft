package com.darkbrook.island.common.gameplay.event;

import org.bukkit.World;

import com.darkbrook.island.common.util.event.CommonEvent;

public class WorldUpdateEvent extends CommonEvent
{
	
	private World world;

	public WorldUpdateEvent(World world) 
	{
		this.world = world;
	}

	public World getWorld() 
	{
		return world;
	}	

}
