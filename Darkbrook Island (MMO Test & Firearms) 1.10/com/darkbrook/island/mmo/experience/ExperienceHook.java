package com.darkbrook.island.mmo.experience;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import com.darkbrook.island.References;
import com.darkbrook.island.mmo.GameRegistry;

import net.md_5.bungee.api.ChatColor;

public class ExperienceHook implements Listener {
	
	public static void load(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(new ExperienceHook(), plugin);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if(GameRegistry.isEnabled()) ExperienceBar.loadExperienceData(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		ExperienceBar.removeExperienceBar(event.getPlayer());
	}
	
	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
		
		if(GameRegistry.isEnabled())  {
			
			if(event.getDamager().getType() == EntityType.PLAYER) {
				addXp(event, (Player) event.getDamager());
			} else if(event.getDamager().getType() == EntityType.ARROW && ((Arrow) event.getDamager()).getShooter() instanceof Player) {
				addXp(event, (Player) ((Arrow) event.getDamager()).getShooter());
			}
			
		}
		
	}
	
	private void addXp(EntityDamageByEntityEvent event, Player player) {
		
		try {
			
			if(event.getFinalDamage() >= ((LivingEntity) event.getEntity()).getHealth()) {
								
				if(References.mmodata.contains(event.getEntityType().name().toLowerCase())) {
					
					String entityName = event.getEntityType().name().toLowerCase();
					
					if(References.mmodata.getString(entityName + ".name").equals("%NAME%")) {
						Experience.add(player, Experience.getXPValue(entityName));
					} else if(event.getEntity().getCustomName() != null && References.mmodata.getString(entityName + ".name").equals(ChatColor.stripColor(event.getEntity().getCustomName()))) {
						Experience.add(player, Experience.getXPValue(entityName));
					}
					
				}
				
			}
		
		} catch (ClassCastException e) {}
		
	}
	
}
