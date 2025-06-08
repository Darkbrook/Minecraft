package com.darkbrook.island.vanilla.handler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ItemDespawnEvent;

import com.darkbrook.island.common.registry.handler.IRegistryHandler;

public class ItemDespawnHandler implements IRegistryHandler
{
	
	@EventHandler
	public void onItemDespawn(ItemDespawnEvent event)
	{
		if(event.getEntity().getTicksLived() < 36000) event.setCancelled(true);		
	}

}
