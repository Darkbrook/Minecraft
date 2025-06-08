package com.darkbrook.apoc;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.darkbrook.island.gameplay.visual.VisualRepository;
import com.darkbrook.library.plugin.registry.IRegistryValue;

public class BlockListener implements Listener, IRegistryValue
{
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event)
	{		
		if(event.getPlayer().getGameMode() != GameMode.CREATIVE) event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event)
	{		
		if(event.getPlayer().getGameMode() != GameMode.CREATIVE) event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockForm(BlockFormEvent event)
	{
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockGrow(BlockGrowEvent event)
	{
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockSpread(BlockSpreadEvent event)
	{
		event.setCancelled(true);
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event)
	{
		
		if(event.getEntityType() == EntityType.PRIMED_TNT)
		{
			Block block = event.getEntity().getLocation().getBlock();
			VisualRepository.explosionFlame.play(block.getLocation());

			block.setType(Material.TNT);			
			event.setCancelled(true);
		}
		
	}
	
}
