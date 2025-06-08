package com.darkbrook.island.common.gameplay.event;

import com.darkbrook.island.common.util.event.CommonEvent;

public class ServerUpdateEvent extends CommonEvent
{
	
	private int currentUpdate;

	public ServerUpdateEvent(int currentUpdate) 
	{
		this.currentUpdate = currentUpdate;
	}

	public int getCurrentUpdate() 
	{
		return currentUpdate;
	}	

}
