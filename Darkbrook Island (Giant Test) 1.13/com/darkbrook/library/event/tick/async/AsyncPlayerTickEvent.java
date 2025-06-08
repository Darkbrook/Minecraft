package com.darkbrook.library.event.tick.async;

import org.bukkit.entity.Player;

import com.darkbrook.library.event.DarkbrookEvent;

public class AsyncPlayerTickEvent extends DarkbrookEvent
{

	private Player player;
	
	public AsyncPlayerTickEvent(Player player)
	{
		this.player = player;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
}
