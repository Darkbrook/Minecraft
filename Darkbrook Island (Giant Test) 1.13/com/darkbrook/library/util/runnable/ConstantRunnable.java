package com.darkbrook.library.util.runnable;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class ConstantRunnable extends UpdateRunnable 
{

	private long period;
			
	public ConstantRunnable(Runnable runnable, RunnableState state, long period, long delay) 
	{
		super(runnable, state, delay);
		this.period = period;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected int onExecute(BukkitScheduler executor, Plugin plugin, RunnableState state, long delay) 
	{
		return state == RunnableState.ASYNC ? executor.scheduleAsyncRepeatingTask(plugin, this, delay, period) : executor.scheduleSyncRepeatingTask(plugin, this, delay, period);
	}
	
	public static ConstantRunnable execute(Runnable runnable, RunnableState state, long period, long delay)
	{
		return (ConstantRunnable) UpdateRunnable.execute(new ConstantRunnable(runnable, state, period, delay));
	}
	
}
