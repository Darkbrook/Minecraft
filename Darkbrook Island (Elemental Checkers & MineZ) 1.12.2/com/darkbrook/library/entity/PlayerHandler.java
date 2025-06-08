package com.darkbrook.library.entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.darkbrook.library.item.ItemHandler;

public class PlayerHandler {
	
	public static void addPotionEffect(Player player, PotionEffectType type, int duration, int amplifier) {
		addPotionEffect(player, new PotionEffect(type, duration, amplifier, false, false));
	}
	
	public static void addPotionEffect(Player player, PotionEffect effect) {
		player.removePotionEffect(effect.getType());
		player.addPotionEffect(effect);
	}
	
	public static void subtractItemFromMainHand(Player player) {
		player.getInventory().setItemInMainHand(ItemHandler.subtractAmount(player.getInventory().getItemInMainHand(), 1));
	}
	
	public static void setTitle(Player player, String title, String subtitle) {
		PacketHandler.sendTitleMessage(player, title);
		PacketHandler.sendSubTitleMessage(player, subtitle);
	}

	public static void setUniversalTitle(String title, String subtitle) {
		for(Player player : Bukkit.getOnlinePlayers()) setTitle(player, title, subtitle);
	}

	public static void setActionBarMessage(Player player, String message) {
		PacketHandler.sendActionMessage(player, message);
	}
	
	public static void setUniversalActionBarMessage(String message) {
		for(Player player : Bukkit.getOnlinePlayers()) setActionBarMessage(player, message);
	}
	
	public static void playLocalSound(Player player, Sound sound, float volume, float pitch) {
		player.playSound(player.getLocation(), sound, volume, pitch);
	}
	
	public static void playerUniversalLocalSound(Sound sound, float volume, float pitch) {
		for(Player player : Bukkit.getServer().getOnlinePlayers()) playLocalSound(player, sound, volume, pitch);
	}
	
	@SuppressWarnings("deprecation")
	public static void setFakeBlock(Player player, Location location, Material type, int data) {
		player.sendBlockChange(location, type, (byte) data);
	}
	
}
