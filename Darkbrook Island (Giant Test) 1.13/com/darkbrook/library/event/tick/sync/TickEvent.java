package com.darkbrook.library.event.tick.sync;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.darkbrook.library.event.DarkbrookEvent;
import com.darkbrook.library.plugin.registry.IRegistryValue;
import com.darkbrook.library.util.runnable.ConstantRunnable;
import com.darkbrook.library.util.runnable.RunnableState;

public class TickEvent extends DarkbrookEvent implements Listener, IRegistryValue
{
	
	public TickEvent()
	{		
		ConstantRunnable.execute(() -> register(), RunnableState.SYNC, 1, 0);
	}
	
	@EventHandler
	public void onTick(TickEvent event)
	{
		for(Player player : Bukkit.getOnlinePlayers()) new PlayerTickEvent(player).register();
	}

}