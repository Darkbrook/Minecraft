package com.darkbrook.library.event.tick.async;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.darkbrook.library.event.DarkbrookEvent;
import com.darkbrook.library.plugin.registry.IRegistryValue;
import com.darkbrook.library.util.runnable.ConstantRunnable;
import com.darkbrook.library.util.runnable.RunnableState;

public class AsyncTickEvent extends DarkbrookEvent implements Listener, IRegistryValue
{
	
	public AsyncTickEvent()
	{		
		ConstantRunnable.execute(() -> register(), RunnableState.ASYNC, 1, 0);
	}

	@EventHandler
	public void onAsyncTick(AsyncTickEvent event)
	{
		for(Player player : Bukkit.getOnlinePlayers()) new AsyncPlayerTickEvent(player).register();
	}
	
}
