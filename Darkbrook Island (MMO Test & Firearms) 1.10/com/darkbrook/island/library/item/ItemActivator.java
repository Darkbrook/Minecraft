package com.darkbrook.island.library.item;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class ItemActivator implements Listener {

	private final HashMap<Player, Boolean> CLICK = new HashMap<Player, Boolean>();
	
	private boolean click(PlayerInteractEvent event)  {
		
		if(event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return false;
				
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player player = event.getPlayer();
			CLICK.put(player, !CLICK.containsKey(player) ? true : !CLICK.get(player));
			return CLICK.get(player);
		}		
		
		return true;
		
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(click(event) == false) return;
		Player player = event.getPlayer();
		if(player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR || !player.getInventory().getItemInMainHand().isSimilar(getItem())) return;
		if(onItemInteract(player)) event.setCancelled(true);
	}
	
	protected abstract ItemStack getItem();
	protected abstract boolean onItemInteract(Player player);
	
}
