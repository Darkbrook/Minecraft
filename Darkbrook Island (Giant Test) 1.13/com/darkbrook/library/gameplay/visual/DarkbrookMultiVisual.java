package com.darkbrook.library.gameplay.visual;

import org.bukkit.entity.Player;

import com.darkbrook.library.util.runnable.RunnableState;
import com.darkbrook.library.util.runnable.SingleRunnable;

public abstract class DarkbrookMultiVisual extends DarkbrookVisual 
{
	public void play(Player player, int delay, boolean isLocal) 
	{		
		SingleRunnable.execute(() -> play(player, isLocal), RunnableState.ASYNC, delay);
	}

	public abstract void play(Player player, boolean isLocal);
}
