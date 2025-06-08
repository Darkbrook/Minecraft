package com.darkbrook.island.mmo.item;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.darkbrook.island.References;
import com.darkbrook.island.library.item.ItemActivator;
import com.darkbrook.island.library.item.ItemHandler;
import com.darkbrook.island.library.misc.UpdateHandler;
import com.darkbrook.island.library.misc.UpdateHandler.UpdateListener;
import com.darkbrook.island.mmo.GameRegistry;

public class Tome extends ItemActivator {

	public static int getCooldown(Player player) {
		return References.playerdata.contains(player.getUniqueId() + ".tome.cooldown") ? References.playerdata.getInt(player.getUniqueId() + ".tome.cooldown"): 0;
	}
	
	public static void subtractCooldown(Player player) {
		References.playerdata.set(player.getUniqueId() + ".tome.cooldown", hasCooldown(player) ? getCooldown(player) - 1 : null);
	}
	
	public static boolean hasCooldown(Player player) {
		return getCooldown(player) > 0;
	}
	
	public static void setCooldown(Player player, int cooldown) {
		References.playerdata.set(player.getUniqueId() + ".tome.cooldown", cooldown);
	}
	
	public static void sendDelayedMessage(Player player, String message) {
		
		UpdateHandler.delay(new UpdateListener() {

			@Override
			public void onUpdate() {
				player.sendMessage(message);
			}
			
		}, 20);
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if(!ItemHandler.hasItem(player, GameRegistry.action_tome)) {
			ItemStack stack = player.getInventory().getItem(8);
			player.getInventory().setItem(8, GameRegistry.action_tome);
			if(stack != null && stack.getType() != Material.AIR) ItemHandler.addItem(player, stack);
		}
	}
	
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent event) {
		event.setCancelled(event.getItemDrop().getItemStack().isSimilar(GameRegistry.action_tome));
	}

	@Override
	protected ItemStack getItem() {
		return GameRegistry.action_tome;
	}

	@Override
	protected boolean onItemInteract(Player player) {
		
		if(player.getInventory().getItemInMainHand().isSimilar(GameRegistry.action_tome)) {
						
			if(Tome.hasCooldown(player)) {
				player.sendMessage(References.getInfoFormat(ChatColor.GRAY, "Your tome has a " + Tome.getCooldown(player) + "s cooldown."));
				player.playSound(player.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1.0F, 2.0F);
			} else {
				GameRegistry.TOME.openInventory(player);
			}
				
			return true;
		} 
		
		return false;
	}
	
}
