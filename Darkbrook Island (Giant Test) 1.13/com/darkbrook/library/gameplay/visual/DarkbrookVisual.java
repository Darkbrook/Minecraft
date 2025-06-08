package com.darkbrook.library.gameplay.visual;

import org.bukkit.Location;

import com.darkbrook.library.util.runnable.RunnableState;
import com.darkbrook.library.util.runnable.SingleRunnable;

public abstract class DarkbrookVisual 
{	
	public void play(Location location, int delay) 
	{		
		SingleRunnable.execute(() -> play(location), RunnableState.ASYNC, delay);
	}
	
	public abstract void play(Location location);
}
