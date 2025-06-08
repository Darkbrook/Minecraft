package com.darkbrook.island.mmo.combat;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ArmorType {
	
	LEATHER("Leather", ChatColor.YELLOW, ChatColor.GOLD,new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE), new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS)),
	CHAIN("Chainmail", ChatColor.GRAY, ChatColor.WHITE, new ItemStack(Material.CHAINMAIL_HELMET), new ItemStack(Material.CHAINMAIL_CHESTPLATE), new ItemStack(Material.CHAINMAIL_LEGGINGS), new ItemStack(Material.CHAINMAIL_BOOTS)),
	IRON("Iron", ChatColor.GRAY, ChatColor.WHITE, new ItemStack(Material.IRON_HELMET), new ItemStack(Material.IRON_CHESTPLATE), new ItemStack(Material.IRON_LEGGINGS), new ItemStack(Material.IRON_BOOTS)),
	GOLD("Gold Infused", ChatColor.GOLD, ChatColor.YELLOW, new ItemStack(Material.GOLD_HELMET), new ItemStack(Material.GOLD_CHESTPLATE), new ItemStack(Material.GOLD_LEGGINGS), new ItemStack(Material.GOLD_BOOTS)),
	DIAMOND("Blue Crystal Infused", ChatColor.DARK_AQUA, ChatColor.AQUA, new ItemStack(Material.DIAMOND_HELMET), new ItemStack(Material.DIAMOND_CHESTPLATE), new ItemStack(Material.DIAMOND_LEGGINGS), new ItemStack(Material.DIAMOND_BOOTS));

	public String name;
	public ChatColor color;
	public ChatColor subColor;
	public ItemStack helmet;
	public ItemStack chestplate;
	public ItemStack leggings;
	public ItemStack boots;

	private ArmorType(String name, ChatColor color, ChatColor subColor, ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
		this.name = name;
		this.color = color;
		this.subColor = subColor;
		this.helmet = helmet.clone();
		this.chestplate = chestplate.clone();
		this.leggings = leggings.clone();
		this.boots = boots.clone();
	}
	
}
