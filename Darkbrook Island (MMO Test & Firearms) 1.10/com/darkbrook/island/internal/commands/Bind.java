package com.darkbrook.island.internal.commands;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import com.darkbrook.island.References;

public class Bind implements Listener {

	public static final HashMap<Player, ArrayList<Material>> BINDING = new HashMap<Player, ArrayList<Material>>();
	public static final HashMap<Player, HashMap<Material, String>> COMMAND = new HashMap<Player, HashMap<Material, String>>();
	public static final HashMap<Player, Boolean> CLICK = new HashMap<Player, Boolean>();

	public static void load(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(new Bind(), plugin);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		if(event.getAction() == Action.RIGHT_CLICK_AIR) {
			Player player = event.getPlayer();
			event.setCancelled(executeCommand(player));
		} else if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player player = event.getPlayer();
			CLICK.put(player, !CLICK.containsKey(player) ? true : !CLICK.get(player));
			if(CLICK.get(player)) event.setCancelled(executeCommand(player));
		}
		
	}
	
	private boolean hasItem(Player player) {
		if(!BINDING.containsKey(player) || player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() == Material.AIR) return false;
		for(Material material : BINDING.get(player))  if(player.getInventory().getItemInMainHand().getType() == material) return true;
		return false;
	}
	
	private String getCommand(Player player) {
		return COMMAND.get(player).get(player.getInventory().getItemInMainHand().getType());
	}
	
	private boolean executeCommand(Player player) {
		if(!hasItem(player)) return false;
		String command = getCommand(player);
		Bukkit.dispatchCommand(player, command.replaceFirst("/", ""));
		player.sendMessage(References.message + "Executed the command, \"" + command + ".\"");
		return true;
	}
	
	public static void addCommand(Player player, String command) {
		
		if(!BINDING.containsKey(player)) {
			ArrayList<Material> materials = new ArrayList<Material>();
			materials.add(player.getInventory().getItemInMainHand().getType());
			BINDING.put(player, materials);
			HashMap<Material, String> commands = new HashMap<Material, String>();
			commands.put(player.getInventory().getItemInMainHand().getType(), command);
			COMMAND.put(player, commands);
		} else {
			BINDING.get(player).add(player.getInventory().getItemInMainHand().getType());
			COMMAND.get(player).put(player.getInventory().getItemInMainHand().getType(), command);
		}
		
	}
	
}
