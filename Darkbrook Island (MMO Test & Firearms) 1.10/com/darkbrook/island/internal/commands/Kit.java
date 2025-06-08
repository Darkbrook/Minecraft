package com.darkbrook.island.internal.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.darkbrook.island.References;
import com.darkbrook.island.mmo.combat.Armor;

public class Kit {
	
	public static void add(Player player, String[] args) {
		player.updateInventory();
		if(References.kitdata.contains("Kits." + args[1])) References.kitdata.set("Kits." + args[1], null);
		for(int i = 0; i < 36; i++) if(player.getInventory().getItem(i) != null) References.kitdata.set("Kits." + args[1] + ".Inventory." + i, player.getInventory().getItem(i));
		if(player.getEquipment().getHelmet() != null) References.kitdata.set("Kits." + args[1] + ".Armor.Helmet",  player.getEquipment().getHelmet());
		if(player.getEquipment().getChestplate() != null) References.kitdata.set("Kits." + args[1] + ".Armor.Chestplate", player.getEquipment().getChestplate());
		if(player.getEquipment().getLeggings() != null) References.kitdata.set("Kits." + args[1] + ".Armor.Leggings", player.getEquipment().getLeggings());
		if(player.getEquipment().getBoots() != null) References.kitdata.set("Kits." + args[1] + ".Armor.Boots", player.getEquipment().getBoots());
		if(player.getInventory().getItemInOffHand() != null && player.getInventory().getItemInOffHand().getType() != Material.AIR) References.kitdata.set("Kits." + args[1] + ".Offhand", player.getInventory().getItemInOffHand());
		player.sendMessage(References.message + "Kit " + args[1] + " added.");
	}
	
	public static void remove(Player player, String[] args) {
		
		if(References.kitdata.contains("Kits." + args[1])) {
			References.kitdata.set("Kits." + args[1], null);
			player.sendMessage(References.message + "Kit " + args[1] + " removed.");
		} else {
			player.sendMessage(References.error + "Kit " + args[1] + " does not exist.");
		}
		
	}
	
	private static void set(Player player, Player target, String[] args) {
		
		target.getInventory().clear();
		for(int i = 0; i < 36; i++) if(References.kitdata.contains("Kits." + args[2] + ".Inventory." + i)) target.getInventory().setItem(i, References.kitdata.config.getItemStack("Kits." + args[2] + ".Inventory." + i));
		if(References.kitdata.contains("Kits." + args[2] + ".Armor.Helmet")) target.getEquipment().setHelmet(References.kitdata.config.getItemStack("Kits." + args[2] + ".Armor.Helmet"));
		if(References.kitdata.contains("Kits." + args[2] + ".Armor.Chestplate")) target.getEquipment().setChestplate(References.kitdata.config.getItemStack("Kits." + args[2] + ".Armor.Chestplate"));
		if(References.kitdata.contains("Kits." + args[2] + ".Armor.Leggings")) target.getEquipment().setLeggings(References.kitdata.config.getItemStack("Kits." + args[2] + ".Armor.Leggings"));
		if(References.kitdata.contains("Kits." + args[2] + ".Armor.Boots")) target.getEquipment().setBoots(References.kitdata.config.getItemStack("Kits." + args[2] + ".Armor.Boots"));
		if(References.kitdata.contains("Kits." + args[2] + ".Offhand")) target.getEquipment().setItemInOffHand(References.kitdata.config.getItemStack("Kits." + args[2] + ".Offhand"));
		
		if(player != target) {		
			player.sendMessage(References.message + "Kit " + args[2] + " has been given to " + args[1] + ".");
			target.sendMessage(References.message + "Kit " + args[2] + " has been added to your inventory by " + player.getName() + ".");
		} else {
			player.sendMessage(References.message + "Kit " + args[2] + " has been added to your inventory.");
		}
		
		player.updateInventory();
		Armor.updateArmorMessageRaw(player);

	}
	
	private static boolean isOnline(String playerName) {
		for(Player player : Bukkit.getServer().getOnlinePlayers()) if(player.getName().equals(playerName)) return true;
		return false;
	}
	
	public static void give(Player player, String[] args) {
		
		if(References.kitdata.contains("Kits." + args[2])) {
			
			if(args[1].equals("@a")) {
				for(Player players : Bukkit.getServer().getOnlinePlayers()) set(player, players, args);				
			} else {
				if(isOnline(args[1])) set(player, Bukkit.getServer().getPlayer(args[1]), args); else player.sendMessage(References.error + "Player " + args[1] + " is not online.");
			}
	
		} else {
			player.sendMessage(References.error + "Kit " + args[2] + " does not exist.");
		}
		
	}
	
	public static void give(Player player, String kit) {
		
		if(References.kitdata.contains("Kits." + kit)) {
			String[] args = {null, null, kit};
			set(player, player, args);
		} else {
			player.sendMessage(References.error + "Kit " + kit + " does not exist.");
		}
		
	}

}
