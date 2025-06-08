package com.darkbrook.library.command.basic.kit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.darkbrook.darkbrookisland.DarkbrookIsland;
import com.darkbrook.library.config.MinecraftConfig;

public class KitInventory {
		
	private static MinecraftConfig config = DarkbrookIsland.kitdata;
	
	private Map<Integer, ItemStack> items;
	private ItemStack helmet;
	private ItemStack chestplate;
	private ItemStack leggings;
	private ItemStack boots;
	private ItemStack offhand;
	private String name;
	private boolean exists;
	
	public KitInventory(Player player, String name) {
		
		player.updateInventory();
		
		final PlayerInventory inventory = player.getInventory();
		final EntityEquipment equipment = player.getEquipment();

		this.items = new HashMap<Integer, ItemStack>();
						
		this.helmet = equipment.getHelmet();
		this.chestplate = equipment.getChestplate();
		this.leggings = equipment.getLeggings();
		this.boots = equipment.getBoots();
		this.offhand = equipment.getItemInOffHand();
		
		for(int i = 0; i < 36; i++) items.put(i, inventory.getItem(i));
		
		this.exists = false;
		this.name = name;
		
	}
	
	public KitInventory(String name) {
		
		String kitHeader = "Kits." + name;
		
		this.items = new HashMap<Integer, ItemStack>();
		this.exists = config.containsKey(kitHeader);
		this.name = name;

		if(!exists) return;
		
		if(config.containsKey(kitHeader + ".helmet")) helmet = config.getItemStack(kitHeader + ".helmet");
		if(config.containsKey(kitHeader + ".chestplate")) chestplate = config.getItemStack(kitHeader + ".chestplate");
		if(config.containsKey(kitHeader + ".leggings")) leggings = config.getItemStack(kitHeader + ".leggings");
		if(config.containsKey(kitHeader + ".boots")) boots = config.getItemStack(kitHeader + ".boots");
		if(config.containsKey(kitHeader + ".offhand")) offhand = config.getItemStack(kitHeader + ".offhand");
		
		for(int i = 0; i < 36; i++) if(config.containsKey(kitHeader + "." + i)) items.put(i, config.getItemStack(kitHeader + "." + i));		
		
	}
	
	public void add() {
		
		String kitHeader = "Kits." + name;
		
		if(config.containsKey(kitHeader)) config.setValue(kitHeader, null);
		
		if(helmet != null) config.setItemStack(kitHeader + ".helmet", helmet);
		if(chestplate != null) config.setItemStack(kitHeader + ".chestplate", chestplate);
		if(leggings != null) config.setItemStack(kitHeader + ".leggings", leggings);
		if(boots != null) config.setItemStack(kitHeader + ".boots", boots);
		if(offhand != null) config.setItemStack(kitHeader + ".offhand", offhand);
		
		for(int key : new ArrayList<Integer>(items.keySet())) config.setItemStack(kitHeader + "." + key, items.get(key));
		exists = true;
		
	}
	
	public void remove() {
		if(!exists) return;
		config.setValue("Kits." + name, null);		
		exists = false;		
	}
	
	public void give(Player player) {
		
		PlayerInventory inventory = player.getInventory();
		EntityEquipment equipment = player.getEquipment();
		
		inventory.clear();		
		equipment.clear();
		
		if(helmet != null) equipment.setHelmet(helmet);
		if(chestplate != null) equipment.setChestplate(chestplate);
		if(leggings != null) equipment.setLeggings(leggings);
		if(boots != null) equipment.setBoots(boots);
		if(offhand != null) equipment.setItemInOffHand(offhand);
		
		for(int i = 0; i < inventory.getSize(); i++) if(items.containsKey(i)) inventory.setItem(i, items.get(i));
		
	}
	
	public boolean exists() {
		return exists;
	}

}
