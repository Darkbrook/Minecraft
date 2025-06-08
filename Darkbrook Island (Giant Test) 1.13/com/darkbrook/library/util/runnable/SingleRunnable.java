package com.darkbrook.library.util.runnable;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class SingleRunnable extends UpdateRunnable 
{

	public SingleRunnable(Runnable runnable, RunnableState state, long delay) 
	{
		super(runnable, state, delay);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected int onExecute(BukkitScheduler executor, Plugin plugin, RunnableState state, long delay) 
	{
		return state == RunnableState.ASYNC ? executor.scheduleAsyncDelayedTask(plugin, this, delay) : executor.scheduleSyncDelayedTask(plugin, this, delay);
	}

	public static SingleRunnable execute(Runnable runnable, RunnableState state, long delay)
	{
		return (SingleRunnable) UpdateRunnable.execute(new SingleRunnable(runnable, state, delay));
	}
	
}
