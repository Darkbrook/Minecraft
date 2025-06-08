package com.darkbrook.island.mmo.combat;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.darkbrook.island.References;
import com.darkbrook.island.library.item.ItemHandler;
import com.darkbrook.island.library.misc.UpdateHandler;
import com.darkbrook.island.library.misc.UpdateHandler.UpdateListener;
import com.darkbrook.island.mmo.GameRegistry;
import com.darkbrook.island.mmo.item.Incantation;
import com.darkbrook.island.mmo.item.SkyWalk;

import net.md_5.bungee.api.ChatColor;

public class Armor implements Listener {
	
	public static void load(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(new Armor(), plugin);
	}

	public static int getNewDefence(LivingEntity entity) {
		int defence = 10;
		for(ItemStack stack : entity.getEquipment().getArmorContents()) if(stack != null) defence += ItemHandler.getIntFromLore(stack, "DEF: +");		
		return defence; 
	}
	
	public static int getOldDefence(Player player) {
		return References.playerdata.contains(player.getUniqueId() + ".defence.max") ? References.playerdata.getInt(player.getUniqueId() + ".defence.max") : 10;
	}
	
	public static void updateArmorRaw(Player player) {
		
		UpdateHandler.delay(new UpdateListener() {

			@Override
			public void onUpdate() {
				References.playerdata.set(player.getUniqueId() + ".defence.max", getNewDefence(player));
				updateArmorMessage(player);
			}
			
		}, 1);
	
	}
	
	public static void updateArmorMessage(Player player) {
		player.setLevel(getOldDefence(player));
	}
	
	public static void updateArmorMessageRaw(Player player) {
		player.setLevel(getNewDefence(player));
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		updateArmorRaw((Player) event.getWhoClicked()); 	
		Incantation.apply(event, "BOOTS", GameRegistry.is_ofthesky, ChatColor.DARK_PURPLE + "INC: Sky Walk");
		SkyWalk.preventArmorRemoval(event);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		updateArmorRaw(event.getPlayer()); 	
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		updateArmorRaw(event.getPlayer());
	}

}
