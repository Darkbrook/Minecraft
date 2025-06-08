package com.darkbrook.minez.entity;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.darkbrook.library.compressed.CompressedEffect;
import com.darkbrook.library.entity.Metadata;
import com.darkbrook.library.location.LocationHandler;

public class MinezEntityPigZombie extends MinezEntity {

	@SuppressWarnings("deprecation")
	private static final CompressedEffect DAMAGE_EFFECT = new CompressedEffect(Effect.FLAME, 0.5F, 0.5F, 0.5F, 25, 256);
	private static final PotionEffect SPAWN_EFFECT = new PotionEffect(PotionEffectType.SPEED, 999999999, 0, false, false);
	private static final ItemStack HAND_ITEM = new ItemStack(Material.STICK);
	private static final ItemStack HELMET_ITEM = new ItemStack(Material.CHAINMAIL_HELMET);

	public MinezEntityPigZombie() {
		
		super(EntityType.PIG_ZOMBIE);
		
		super.addDamageRejection(DamageCause.BLOCK_EXPLOSION);
		super.addDamageRejection(DamageCause.SUFFOCATION);
		super.addDamageRejection(DamageCause.DROWNING);
		
		super.setDamageInEffects(null, DAMAGE_EFFECT);
		super.setDamageOut(11.5D, false);
		
	}

	@Override
	protected void onSpawn(CreatureSpawnEvent event) {

		PigZombie pigZombie = (PigZombie) event.getEntity();
		pigZombie.setBaby(false);
		
		EntityEquipment equipment = pigZombie.getEquipment();
		equipment.setItemInMainHand(HAND_ITEM);
		equipment.setHelmet(pigZombie.isBaby() ? HELMET_ITEM : null);

		pigZombie.setHealth(0.1D);
		pigZombie.setAngry(true);
		pigZombie.setAnger(999999999);
		pigZombie.addPotionEffect(SPAWN_EFFECT);

		Player player = super.getNearestSurvivalPlayer(pigZombie, 64);
		if(player != null) pigZombie.setTarget(player);

		Metadata.setEntityMetadata(pigZombie, "MinezEntity", "MinezEntityPigZombie");
		
	}

	@Override
	protected void onDeath(EntityDeathEvent event) {

		PigZombie pigZombie = (PigZombie) event.getEntity();
		Location location = pigZombie.getLocation();
		World world = location.getWorld();

		for(int i = 0; i < 2; i++) world.createExplosion(location.getX(), location.getY(), location.getZ(), 2.0F, false, false);
		
		if(!pigZombie.isBaby()) {
			
			Location offsetLocation = LocationHandler.getOffsetLocation(location, 0.0D, 1.0D, 0.0D);
			
			for(int i = 0; i < 4; i++) {
				world.spawnEntity(offsetLocation, EntityType.ZOMBIE);
			}
			
		}
		
	}

}
