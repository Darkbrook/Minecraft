package com.darkbrook.island.vanilla.claim;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.darkbrook.island.common.registry.handler.IRegistryHandler;
import com.darkbrook.island.vanilla.claim.data.ClaimChunkData;
import com.darkbrook.island.vanilla.claim.data.ClaimProviderData;

public class ClaimHandler implements IRegistryHandler
{

	@EventHandler
	public void onPistonExtend(BlockPistonExtendEvent event)
	{	
		List<Block> blocks = event.getBlocks();
		
		if(blocks.isEmpty())
		{
			onEventCheck(event, event.getBlock(), event.getBlock().getRelative(event.getDirection()));
		}
		
		blocks.forEach(block -> onEventCheck(event, block, block.getRelative(event.getDirection())));
	}
	
	@EventHandler
	public void onPistonRetract(BlockPistonRetractEvent event)
	{
		event.getBlocks().forEach(block -> onEventCheck(event, event.getBlock(), block));
	}
	
	@EventHandler
	public void onBlockFromTo(BlockFromToEvent event)
	{
		onEventCheck(event, event.getBlock(), event.getToBlock());
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) && event.getPlayer().getGameMode() != GameMode.SPECTATOR) onEventCheck(event, event.getPlayer(), event.getClickedBlock());
	}
	
	private void onEventCheck(Cancellable cancellable, Block blockFrom, Block blockTo)
	{
		String from = new ClaimChunkData(blockFrom.getChunk()).getProvider();
		String to = new ClaimChunkData(blockTo.getChunk()).getProvider();
				
		if(from == to || (from != null & to == null) || (from != null && to != null && from.equals(to)))
		{
			return;
		}
		
		cancellable.setCancelled(true);
	}
	
	private void onEventCheck(Cancellable cancellable, Player player, Block block)
	{
		String providerName;

		if((providerName = new ClaimChunkData(block.getChunk()).getProvider()) == null)
		{
			return;
		}
		
		ClaimProviderData provider = new ClaimProviderData(providerName);
		
		if(provider.hasProvider(player.getName()))
		{
			return;
		}
		
		cancellable.setCancelled(true);
	}
	
}
