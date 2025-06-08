package com.darkbrook.minez.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.darkbrook.library.entity.Metadata;
import com.darkbrook.minez.entity.ai.MinezAIRegistry;
import com.darkbrook.minez.entity.ai.MinezGiantAI;

public class MinezEntityGiant extends MinezEntity {

	public MinezEntityGiant() {
		
		super(EntityType.GIANT);
		
		super.addDamageRejection(DamageCause.CONTACT);
		super.addDamageRejection(DamageCause.DROWNING);
		super.addDamageRejection(DamageCause.BLOCK_EXPLOSION);
		super.addDamageRejection(DamageCause.FALL);
		super.addDamageRejection(DamageCause.PROJECTILE);
		super.addDamageRejection(DamageCause.SUFFOCATION);

		super.setDamageOut(11.0D, false);
		
	}

	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		
		if(event.getSpawnReason() != SpawnReason.CUSTOM && event.getEntityType() == EntityType.GIANT) {
			event.setCancelled(true);
			MinezAIRegistry.spawn(new MinezGiantAI(event.getLocation().getWorld()), event.getLocation());
		} else {
			Metadata.setEntityMetadata(event.getEntity(), "MinezEntity", "MinezEntityGiant");
		}
		
	}
	
}
