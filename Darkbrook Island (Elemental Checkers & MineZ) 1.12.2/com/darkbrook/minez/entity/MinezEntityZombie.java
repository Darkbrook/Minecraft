package com.darkbrook.minez.entity;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.darkbrook.library.compressed.CompressedEffect;
import com.darkbrook.library.entity.Metadata;
import com.darkbrook.library.misc.MathHandler;

public class MinezEntityZombie extends MinezEntity {

	//private static final CompressedSound DAMAGE_SOUND = new CompressedSound(Sound.ENTITY_PLAYER_HURT, 1.0F, 1.0F);
	private static final CompressedEffect DAMAGE_EFFECT = new CompressedEffect(Material.ENDER_STONE, 0, 0.2F, 1.0F, 0.2F, 0.2F, 64);
	private static final PotionEffect SPAWN_EFFECT = new PotionEffect(PotionEffectType.SPEED, 999999999, 3, false, false);

	public MinezEntityZombie() {
		
		super(EntityType.ZOMBIE);
		
		super.addDamageRejection(DamageCause.BLOCK_EXPLOSION);
		super.addDamageRejection(DamageCause.FIRE);
		super.addDamageRejection(DamageCause.SUFFOCATION);
		super.addDamageRejection(DamageCause.DROWNING);
		super.addDamageRejection(DamageCause.FALL);
		
		super.setDamageInEffects(/*DAMAGE_SOUND*/ null, DAMAGE_EFFECT);
		super.setDamageOut(1.5D, false);
		
	}

	@Override
	protected void onSpawn(CreatureSpawnEvent event) {
		
		Zombie zombie = (Zombie) event.getEntity();
		
		zombie.setHealth(MathHandler.RANDOM.nextInt(15) + 2);
		zombie.addPotionEffect(SPAWN_EFFECT);
		zombie.setBaby(false);

		EntityEquipment equipment = zombie.getEquipment();
		
		equipment.setItemInMainHand(null);
		equipment.setHelmet(null);
		equipment.setChestplate(null);
		equipment.setLeggings(null);
		equipment.setBoots(null);

		Player player = super.getNearestSurvivalPlayer(zombie, 128);
		if(player != null) zombie.setTarget(player);
		
		Metadata.setEntityMetadata(zombie, "MinezEntity", "MinezEntityZombie");
		
	}

}
