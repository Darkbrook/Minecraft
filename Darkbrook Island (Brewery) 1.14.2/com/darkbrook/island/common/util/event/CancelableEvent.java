package com.darkbrook.island.common.util.event;

public class CancelableEvent extends CommonEvent
{

	private boolean isCancled;

	public boolean isCancled() 
	{
		return isCancled;
	}

	public void setCancled(boolean isCancled) 
	{
		this.isCancled = isCancled;
	}	
	
}
