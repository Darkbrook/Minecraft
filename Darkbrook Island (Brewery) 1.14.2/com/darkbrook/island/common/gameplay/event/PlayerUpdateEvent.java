package com.darkbrook.island.common.gameplay.event;

import org.bukkit.entity.Player;

import com.darkbrook.island.common.util.event.CommonEvent;

public class PlayerUpdateEvent extends CommonEvent
{
	
	private Player player;

	public PlayerUpdateEvent(Player player) 
	{
		this.player = player;
	}

	public Player getPlayer() 
	{
		return player;
	}	

}
