package com.darkbrook.minez.entity;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.darkbrook.library.compressed.CompressedEffect;
import com.darkbrook.library.entity.Metadata;

public class MinezEntitySkeleton extends MinezEntity {

	//private static final CompressedSound DAMAGE_SOUND = new CompressedSound(Sound.ENTITY_PLAYER_HURT, 1.0F, 1.0F);
	private static final CompressedEffect DAMAGE_EFFECT = new CompressedEffect(Material.BONE_BLOCK, 0, 0.2F, 1.0F, 0.2F, 0.2F, 64);
	private static final PotionEffect SPAWN_EFFECT = new PotionEffect(PotionEffectType.SPEED, 999999999, 2, false, false);
	private static final ItemStack HAND_ITEM = new ItemStack(Material.IRON_SWORD);

	public MinezEntitySkeleton() {
		
		super(EntityType.SKELETON);
		
		super.addDamageRejection(DamageCause.BLOCK_EXPLOSION);
		super.addDamageRejection(DamageCause.FIRE);
		super.addDamageRejection(DamageCause.SUFFOCATION);
		super.addDamageRejection(DamageCause.DROWNING);
		super.addDamageRejection(DamageCause.FALL);
		
		super.setDamageInEffects(/*DAMAGE_SOUND*/ null, DAMAGE_EFFECT);
		super.setDamageOut(12.5D, false);
		
	}

	@Override
	protected void onSpawn(CreatureSpawnEvent event) {
		
		Skeleton skeleton = (Skeleton) event.getEntity();
		skeleton.addPotionEffect(SPAWN_EFFECT);
	
		skeleton.getEquipment().setItemInMainHand(HAND_ITEM);
		skeleton.getEquipment().setHelmet(null);
		skeleton.getEquipment().setChestplate(null);
		skeleton.getEquipment().setLeggings(null);
		skeleton.getEquipment().setBoots(null);

		Player player = super.getNearestSurvivalPlayer(skeleton, 128);
		if(player != null) skeleton.setTarget(player);
		
		Metadata.setEntityMetadata(skeleton, "MinezEntity", "MinezEntitySkeleton");
		
	}

}
