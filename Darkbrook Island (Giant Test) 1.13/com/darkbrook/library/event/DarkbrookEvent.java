package com.darkbrook.library.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.darkbrook.library.plugin.DarkbrookPlugin;

public abstract class DarkbrookEvent extends Event
{

	private static final HandlerList HANDLER_LIST = new HandlerList();

	public static HandlerList getHandlerList() 
	{
	    return HANDLER_LIST;
	}

	public DarkbrookEvent register()
	{
		DarkbrookPlugin.executeEventStatic(this);
		return this;
	}

	public HandlerList getHandlers() 
	{
	    return HANDLER_LIST;
	}
	
}
