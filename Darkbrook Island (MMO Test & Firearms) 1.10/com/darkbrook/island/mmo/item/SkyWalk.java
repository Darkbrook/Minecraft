package com.darkbrook.island.mmo.item;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.darkbrook.island.References;
import com.darkbrook.island.library.item.ItemHandler;

public class SkyWalk {
	
	public static boolean hasIncantation(Player player) {
		return player.getEquipment().getBoots() != null && Incantation.hasIncantation(player.getEquipment().getBoots(), ChatColor.DARK_PURPLE + "INC: Sky Walk");
	}
	
	public static boolean isActive(Player player) {
		return hasIncantation(player) && player.getEquipment().getBoots().getItemMeta().getItemFlags().contains(ItemFlag.HIDE_PLACED_ON) && player.hasPotionEffect(PotionEffectType.LEVITATION);
	}
		
	public static void setActive(Player player, boolean active) {
		
		player.getEquipment().setBoots(active ? ItemHandler.addFlag(player.getEquipment().getBoots(), ItemFlag.HIDE_PLACED_ON) : ItemHandler.removeFlag(player.getEquipment().getBoots(), ItemFlag.HIDE_PLACED_ON));
		
		if(active) {
			player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 2.0F);
			player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 600, 1, false, false)); 
			player.sendMessage(References.getSpacedFormat(ChatColor.DARK_GRAY, ChatColor.GRAY, "Sky Walk Activated."));
		} else {
			player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 2.0F);
			player.removePotionEffect(PotionEffectType.LEVITATION);
			player.sendMessage(References.getSpacedFormat(ChatColor.DARK_GRAY, ChatColor.GRAY, "Sky Walk Deactivated."));
		}

	}
	
	public static void update(Player player) {
		
		if(isActive(player)) {
			player.getWorld().spigot().playEffect(player.getLocation(), Effect.CLOUD, 0, 0, 0.1F, 0.1F, 0.1F, 0, 10, 100);
			player.setVelocity(player.getLocation().getDirection());
			player.setFallDistance(0.0F);
			for(PotionEffect potion : player.getActivePotionEffects()) {
				if(potion.getType().equals(PotionEffectType.LEVITATION)) {
					if(potion.getDuration() <= 20) setActive(player, false);
				}
			}
		}
		
	}
	
	public static void preventArmorRemoval(InventoryClickEvent event) {
		if(SkyWalk.isActive((Player) event.getWhoClicked()) && event.getClickedInventory() != null && event.getCurrentItem() != null && Incantation.hasIncantation(event.getCurrentItem(), ChatColor.DARK_PURPLE + "INC: Sky Walk")) {
			((Player) event.getWhoClicked()).sendMessage(References.getInfoFormat(ChatColor.GRAY, "You can not remove armor pieces with active enchantments."));
			event.setCancelled(true);
		}
	}
	
	public static void check(Player player) {
		
		if(!hasIncantation(player)) {
			Tome.sendDelayedMessage(player, References.getSpacedFormat(ChatColor.DARK_GRAY, ChatColor.GRAY, "Nothing seems to happen."));
			return;
		}
			
		if(!SkyWalk.isActive(player)) {
			SkyWalk.setActive(player, true);
			Tome.setCooldown(player, 300);
		} else {
			References.getInfoFormat(ChatColor.GRAY, "Sky Walk is already active.");
		}
		
	}

}
