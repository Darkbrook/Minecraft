package com.darkbrook.island.library.template;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import com.darkbrook.island.References;
import com.darkbrook.island.library.misc.FileHandler;
import com.darkbrook.island.library.misc.SkullCreator;

import net.md_5.bungee.api.ChatColor;

public class Selection implements Listener {

	private static final HashMap<Player, Location> POS1 = new HashMap<Player, Location>();
	private static final HashMap<Player, Location> POS2 = new HashMap<Player, Location>();
	private static final HashMap<Player, Boolean> CAN_CLICK = new HashMap<Player, Boolean>();
	
	public static void load(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(new Selection(), plugin);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		if(player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType() != Material.SLIME_BALL) return;
		if(event.getAction() != Action.LEFT_CLICK_BLOCK & event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		event.setCancelled(true);
			
		if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
			Location loc = event.getClickedBlock().getLocation();
			POS1.put(player, loc);
			player.sendMessage(ChatColor.BLUE + "Selection 1 set to " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ".");
		} else if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {			
			CAN_CLICK.put(player, !CAN_CLICK.containsKey(player) ? true : !CAN_CLICK.get(player));
			if(CAN_CLICK.get(player)) return;
			Location loc = event.getClickedBlock().getLocation();
			POS2.put(player, loc);
			player.sendMessage(ChatColor.BLUE + "Selection 2 set to " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ".");
		}
		
	}
	
	public static boolean hasSelection(Player player) {
		return POS1.containsKey(player) && POS2.containsKey(player);
	}
	
	private static int max(int a, int b) {
		return a == b ? a : (a > b ? a : b);
	}
	
	private static int min(int a, int b) {
		return a == b ? a : (a < b ? a : b);
	}
	
	public static ArrayList<Location> getSelection(Player player) {
	
		ArrayList<Location> locations = new ArrayList<Location>();
		
		if(hasSelection(player)) {
			Location p1 = POS1.get(player);
			Location p2 = POS2.get(player);
			for(int x = min(p1.getBlockX(), p2.getBlockX()); x <= max(p1.getBlockX(), p2.getBlockX()); x++) for(int y = min(p1.getBlockY(), p2.getBlockY()); y <= max(p1.getBlockY(), p2.getBlockY()); y++) for(int z = min(p1.getBlockZ(), p2.getBlockZ()); z <= max(p1.getBlockZ(), p2.getBlockZ()); z++) locations.add(new Location(player.getWorld(), x, y, z));
		}
		
		return locations;
		
	}
	
	public static ArrayList<Block> getSelectedBlocks(Player player) {
		ArrayList<Block> blocks = new ArrayList<Block>();
		if(!getSelection(player).isEmpty()) for(Location location : getSelection(player)) blocks.add(location.getBlock());
		return blocks;
	}
	
	@SuppressWarnings("deprecation")
	public static String getSelectionStringFromOffset(Player player, Location offset) {
		if(!hasSelection(player)) return null;
		String s = "";
		for(Block block : getSelectedBlocks(player)) s += (":" + (int) (block.getX() - offset.getBlockX()) + "," + (int) (block.getY() - offset.getBlockY()) + "," + (int) (block.getZ() - offset.getBlockZ()) + "," + block.getType() + "," + block.getData());
		return s + ":";
	}
	
	@SuppressWarnings("deprecation")
	private static void setBlock(String input, Location offset) {
		
		String s = "";
		List<String> strings = new ArrayList<String>();
		for(int i = 0; i < input.length(); i++) {
			String c = input.substring(i, i + 1);
			if(c.equals(",")) {
				if(!s.isEmpty()) strings.add(s);
				s = "";
			} else {
				s += c;
			}
		}
		
		int current = -1;
		int x = 0;
		int y = 0;
		int z = 0;
		Material type = null;
		byte data = 0;
				
		for(String string : strings) {
			
			if(current < 4) current++; 

			switch(current) {
				case 0: x = Integer.parseInt(string); break;
				case 1: y = Integer.parseInt(string); break;
				case 2: z = Integer.parseInt(string); break;
				case 3: type = Material.getMaterial(string); break;
				case 4: data = Byte.parseByte(string); break;
			}
			
			if(current >= 4) {
				Location location = new Location(offset.getWorld(), x + offset.getBlockX(), y + offset.getBlockY(), z + offset.getBlockZ());
				location.getBlock().setType(type);
				location.getBlock().setData(data);
				current = -1;
			}
			
		}
		
	}
	
	public static void paste(String input, Location offset) {
		
		String s = "";
		List<String> strings = new ArrayList<String>();
		
		for(int i = 0; i < input.length(); i++) {
			String c = input.substring(i, i + 1);
			if(c.equals(":")) {
				if(!s.isEmpty()) strings.add(s);
				s = "";
			} else {
				s += c;
			}
		}
				
		for(String string : strings) setBlock(string + ",", offset);
		
	}
	
	public static boolean addBlueprint(Player player, String name, Location offset) {
		String blueprint = getSelectionStringFromOffset(player, offset);
		if(blueprint == null) return false;
		FileHandler.createExternalDirectorys(References.getDataFolder() + "\\blueprints");
		File file = new File(References.getDataFolder() + "\\blueprints\\" + name + ".blueprint");
		if(file.exists()) return false;
		
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		blueprint = SkullCreator.getEncodedValue(blueprint);
		FileHandler.writeToExternalFile(file.getPath(), blueprint, false);
		return true;
	}
	
	public static boolean loadBlueprint(String name, Location offset) {
		File file = new File(References.getDataFolder() + "\\blueprints\\" + name + ".blueprint");
		if(!file.exists()) return false;
		String blueprint = SkullCreator.getDecodedValue(FileHandler.readFromExternalFile(file.getAbsolutePath()).get(0));
		paste(blueprint, offset);
		return true;
	}
	
}
