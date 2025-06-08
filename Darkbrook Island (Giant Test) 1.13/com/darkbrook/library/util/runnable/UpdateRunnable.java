package com.darkbrook.library.util.runnable;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.darkbrook.library.plugin.DarkbrookPlugin;

public abstract class UpdateRunnable implements Runnable 
{
	private Plugin plugin;
	private BukkitScheduler executor;
	private Runnable runnable;
	private RunnableState state;
	private long delay;
	private int taskId;
	private boolean isRunning;

	protected UpdateRunnable(Runnable runnable, RunnableState state, long delay) 
	{
		this.executor = Bukkit.getScheduler();
		this.runnable = runnable;
		this.state = state;
		this.delay = delay;
		
		plugin = DarkbrookPlugin.getPlugin();
	}
	
	public void run() 
	{
		runnable.run();
	}
	
	public void execute() 
	{
		taskId = onExecute(executor, plugin, state, delay);
		isRunning = true;
	}
	
	public void shutdown() 
	{
		isRunning = false;
		executor.cancelTask(taskId);
	}
	
	public boolean isRunning() 
	{
		return isRunning;
	}

	protected abstract int onExecute(BukkitScheduler executor, Plugin plugin, RunnableState state, long delay);
	
	public static UpdateRunnable execute(UpdateRunnable runnable)
	{
		runnable.execute();
		return runnable;
	}
	
}
