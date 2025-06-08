package com.darkbrook.minez.entity;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.darkbrook.library.compressed.CompressedEffect;

import net.md_5.bungee.api.ChatColor;

public class MinezEntityPlayer extends MinezEntity {

	public static final Map<Player, Boolean> SWING_COUNTER = new HashMap<Player, Boolean>();
	private static final CompressedEffect DAMAGE_EFFECT = new CompressedEffect(Material.REDSTONE_BLOCK, 0, 0.2F, 1.0F, 0.2F, 0.2F, 20);
	
	public MinezEntityPlayer() {
		super(EntityType.PLAYER);
		super.setDamageInEffects(null, DAMAGE_EFFECT);
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		
		if(event.getEntityType() == EntityType.PLAYER && event.getDamager().getType() == EntityType.GIANT) {
			Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "Implement Giants Kick & Stomp!");
		}
		
	}
	
	@Override
	protected void onDamageEntity(EntityDamageByEntityEvent event) {
				
		Player player = (Player) event.getDamager();
		
		if(!SWING_COUNTER.containsKey(player)) SWING_COUNTER.put(player, false);
		
		if(SWING_COUNTER.get(player)) {
			event.setCancelled(true); 
		} else {
			SWING_COUNTER.put(player, true);	
		}
		
	}

}
