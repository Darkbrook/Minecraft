package com.darkbrook.library.gameplay.bossbar;

import java.util.List;

import org.bukkit.craftbukkit.v1_13_R1.boss.CraftBossBar;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.darkbrook.library.event.tick.sync.TickCycleEvent;
import com.darkbrook.library.plugin.registry.IRegistryValue;
import com.darkbrook.library.util.helper.EntityHelper;
import com.darkbrook.library.util.runnable.RunnableState;
import com.darkbrook.library.util.runnable.SingleRunnable;

public class EntityBossBar implements Listener, IRegistryValue
{
	
	private LivingEntity entity;
	private CraftBossBar bar;
	private double broadcastRadius;
	
	@SuppressWarnings("deprecation")
	public EntityBossBar(LivingEntity entity, CraftBossBar bar, double broadcastRadius)
	{
		this.entity = entity;
		this.bar = bar;
		this.broadcastRadius = broadcastRadius;
		
		bar.setProgress(entity.getHealth() / entity.getMaxHealth());
	}
	
	public void clearBar()
	{
		bar.removeAll();
	}
	
	@EventHandler
	public void onTickCycle(TickCycleEvent event)
	{
				
		if(entity.isValid())
		{
		
			List<Player> barPlayers = bar.getPlayers();
			List<Player> players = EntityHelper.radial(Player.class, entity.getLocation(), broadcastRadius);
			
			for(Player player : players) if(!barPlayers.contains(player))
			{
				bar.addPlayer(player);
			}
			
			for(Player player : barPlayers) if(!players.contains(player))
			{
				bar.removePlayer(player);
			}
			
		}
		else
		{
			EntityBossBarManager.unregister(this);
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event)
	{
		if(!event.isCancelled() && event.getEntity() == entity) SingleRunnable.execute(() -> bar.setProgress(entity.getHealth() / entity.getMaxHealth()), RunnableState.ASYNC, 2);			
	}	
	
}
