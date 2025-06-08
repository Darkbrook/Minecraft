package com.darkbrook.library.blueprint.selection;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import com.darkbrook.library.block.MemoryBlock;
import com.darkbrook.library.blueprint.Blueprint;
import com.darkbrook.library.message.FormatMessage;

public class BlockSelectionHandler implements Listener {
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		if(event.getHand() != EquipmentSlot.HAND) return;
		Player player = event.getPlayer();
		if(event.getClickedBlock() == null || !player.isOp() || player.getGameMode() != GameMode.CREATIVE || player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() != Material.SLIME_BALL) return;
			
		Action action = event.getAction();
		PlayerSelection selection = PlayerSelection.getPlayerSelection(player);
		Location location = event.getClickedBlock().getLocation();
		
		if(action == Action.LEFT_CLICK_BLOCK) selection.setPosition0(location); else if(action == Action.RIGHT_CLICK_BLOCK) selection.setPosition1(location); else return;		
		FormatMessage.info(player, "Position " + (action == Action.LEFT_CLICK_BLOCK ? "0" : "1") + " set to X=" + location.getBlockX() + ", Y=" + location.getBlockY() + ", Z=" + location.getBlockZ());
		event.setCancelled(true);
		
	}
	
	public static PlayerSelection getPlayerSelection(Player player) {
		return PlayerSelection.getPlayerSelection(player);
	}
	
	public static void setClipboard(Player player, Blueprint clipboard) {
		getPlayerSelection(player).setClipboard(clipboard);
	}
	
	public static boolean hasSelection(Player player) {
		return getPlayerSelection(player).hasSelection();
	}
	
	public static List<MemoryBlock> getSelection(Player player) {
		return getPlayerSelection(player).getSelection(true);
	}
	
	public static Blueprint getClipboard(Player player) {
		return getPlayerSelection(player).getClipboard();
	}

}
