package com.darkbrook.minez.entity;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import com.darkbrook.library.compressed.CompressedEffect;
import com.darkbrook.library.compressed.CompressedSound;
import com.darkbrook.library.misc.UpdateHandler;
import com.darkbrook.library.misc.UpdateHandler.UpdateListener;

public abstract class MinezEntity implements Listener {

	private List<DamageCause> damageRejections;
	private EntityType type;
	private CompressedSound damageSound;
	private CompressedEffect damageEffect;
	private double damageOut;
	private boolean isPureDamage;
	private boolean isCombustable;
	
	protected MinezEntity(EntityType type) {
		this.damageRejections = new ArrayList<DamageCause>();
		this.type = type;
		this.damageOut = -65536.0D;
		this.isPureDamage = false;
		this.isCombustable = false;
	}
	
	public void addDamageRejection(DamageCause cause) {
		this.damageRejections.add(cause);
	}
	
	public void setDamageInEffects(CompressedSound damageSound, CompressedEffect damageEffect) {
		this.damageSound = damageSound;
		this.damageEffect = damageEffect;
	}
	
	public void setDamageOut(double damageOut, boolean isPureDamage) {
		this.damageOut = damageOut;
		this.isPureDamage = isPureDamage;
	}
	
	@EventHandler
	public void onEntitySpawn(CreatureSpawnEvent event) {
		
		if(event.getEntityType() != type) return;
				
		if(event.getSpawnReason() == SpawnReason.DEFAULT) {
		
			UpdateHandler.delay(new UpdateListener() {

				@Override
				public void onUpdate() {
					onSpawn(event);				
				}
			
			}, 1);
		
		} else {
			onSpawn(event);				
		}
		
	}
	
	@EventHandler
	public void onEntityCombust(EntityCombustEvent event) {
		if(event.getEntityType() != type || isCombustable) return;		
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
				
		if(event.getEntityType() != type) return;		
		if(event.getCause() == DamageCause.BLOCK_EXPLOSION) event.setDamage(8.0F);
		if(damageRejections.contains(event.getCause())) event.setCancelled(true);
		
		onDamage(event);
		if(event.getCause() != DamageCause.ENTITY_ATTACK) return;
		
		Location location = event.getEntity().getLocation();
		if(damageSound != null) damageSound.play(location);		
		if(damageEffect != null) damageEffect.play(location);
		
	}
	
	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent event) {
		
		if(event.getDamager().getType() != type) return;
		
		Entity entityDamaged = event.getEntity();
		
		if(isPureDamage) {
			
			if(entityDamaged instanceof LivingEntity) {
				LivingEntity entityDamagedLiving = (LivingEntity) entityDamaged;
				entityDamagedLiving.damage(damageOut);
				event.setCancelled(true);
			}
			
		} else if(damageOut > 0.0D){
			event.setDamage(damageOut);
		} else if(damageOut != -65536.0D){
			event.setCancelled(true);
		}
		
		onDamageEntity(event);
		
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if(event.getEntityType() == type) onDeath(event);
	}

	protected Player getNearestSurvivalPlayer(Entity entity, int distanceMax) {
		
		Player player = null;

		for(Entity target : entity.getNearbyEntities(distanceMax, distanceMax, distanceMax)) {
			
			if(target.getType() != EntityType.PLAYER) continue;
			
			Player playerTemp = (Player) target;
			int distanceCurrent = (int) Math.round(playerTemp.getLocation().distance(entity.getLocation())) + 1;
			
			if(playerTemp.getGameMode() == GameMode.CREATIVE || playerTemp.getGameMode() == GameMode.SPECTATOR || distanceCurrent >= distanceMax) continue;
			
			player = playerTemp;
			distanceMax = distanceCurrent;
			
		}
		
		return player;
		
	}
	
	protected void onSpawn(CreatureSpawnEvent event) {}
	protected void onDamage(EntityDamageEvent event) {}
	protected void onDamageEntity(EntityDamageByEntityEvent event) {}
	protected void onDeath(EntityDeathEvent event) {}
	
}
