package com.darkbrook.island.mmo.entity;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.darkbrook.island.library.misc.MathHandler;
import com.darkbrook.island.library.misc.UpdateHandler;
import com.darkbrook.island.library.misc.UpdateHandler.UpdateListener;
import com.darkbrook.island.mmo.GameRegistry;
import com.darkbrook.island.mmo.item.DamageDie;

import net.md_5.bungee.api.ChatColor;

public class EntityData {
	
	public LivingEntity entity;
	public Location position;
	public String name;
	public int maxhealth;
	public int health;
	public int defence;
	public int damage;
	public int movement;
	public int team;
	public int lastroll;
	public int hits;
	
	public EntityData(LivingEntity entity, String name, int maxhealth, int health, int defence, int damage, int team) {
		this.entity = entity;
		this.position = entity.getLocation();
		this.name = ChatColor.stripColor(name);
		this.maxhealth = maxhealth;
		this.health = health;
		this.defence = defence;
		this.damage = damage;
		this.movement = getMovement();
		this.team = team;
		this.hits = 0;
	}

	private int getMovement() {
		int movement = 8;
		if(entity.getEquipment().getBoots() != null && entity.getEquipment().getBoots().getType() != Material.AIR) movement += 8;
		return movement;
	}
	
	public void canRoll() {
		if(entity instanceof Player) DamageDie.setRollTicket((Player) entity);
	}
	
	private int getPendingRoll() {
		
		if(!(entity instanceof Player)) {
			
			entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_SKELETON_DEATH, 1.0F, 0.0F);
			entity.getEquipment().setItemInOffHand(GameRegistry.die);		
			
			UpdateHandler.delay(new UpdateListener() {

				@Override
				public void onUpdate() {
					entity.getEquipment().setItemInOffHand(null);		
				}
				
			}, 80);
			
		}
		
		return entity instanceof Player ? DamageDie.getRoll((Player) entity) : (MathHandler.RANDOM.nextInt(20) + 1);
	}
	
	public boolean updateRoll() {
		lastroll = getPendingRoll();
		return lastroll > 0;
	}
	
}
