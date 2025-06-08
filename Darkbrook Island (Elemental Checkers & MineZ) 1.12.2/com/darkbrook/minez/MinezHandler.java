package com.darkbrook.minez;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.darkbrook.library.block.Dispenser;
import com.darkbrook.library.compressed.CompressedEffect;
import com.darkbrook.library.compressed.CompressedSound;
import com.darkbrook.library.entity.PlayerHandler;
import com.darkbrook.library.entity.Potion;
import com.darkbrook.library.location.LocationHandler;
import com.darkbrook.library.misc.UpdateHandler;
import com.darkbrook.library.misc.UpdateHandler.UpdateListener;
import com.darkbrook.minez.entity.MinezEntityPlayer;
import com.darkbrook.minez.entity.ai.MinezAIRegistry;
import com.darkbrook.minez.entity.ai.MinezGiantAI;

public class MinezHandler implements Listener {
	
	private static final Map<Location, Integer> SPAWNER_COOLDOWN = new HashMap<Location, Integer>();
	private static final CompressedSound PISTON_SOUND = new CompressedSound(Sound.BLOCK_FIRE_EXTINGUISH, 1.0F, 2.0F);
	
	@SuppressWarnings("deprecation")
	private static final CompressedEffect PISTON_EFFECT = new CompressedEffect(Effect.CLOUD, 0.2F, 0.3F, 0.2F, 0, 16);

	public MinezHandler() {
				
		MinezAIRegistry.registerAI(MinezGiantAI.class, EntityType.GIANT);
		
		UpdateHandler.repeat(new UpdateListener() {

			@Override
			public void onUpdate() {
				
				for(Player player : Bukkit.getOnlinePlayers()) {
					MinezEntityPlayer.SWING_COUNTER.put(player, false);
				}
				
				for(Location location : new ArrayList<Location>(SPAWNER_COOLDOWN.keySet())) {
					
					int cooldown = SPAWNER_COOLDOWN.get(location);
				
					if(cooldown > 0) {
						SPAWNER_COOLDOWN.put(location, cooldown - 1);
					} else {
						SPAWNER_COOLDOWN.remove(location);
					}
					
				}
				
			}
			
		}, 0, 2);
		
	}
	
	@EventHandler
	public void onBlockDispense(BlockDispenseEvent event) {
		
		ItemStack stack = event.getItem().clone();
		Dispenser dispenser = new Dispenser(event.getBlock().getLocation());
		
		UpdateHandler.delay(new UpdateListener() {

			@Override
			public void onUpdate() {
				dispenser.addItem(stack);
			}
			
		}, 1);
		
	}
	
	@EventHandler
	public void onSpawnerSpawn(SpawnerSpawnEvent event) {
				
		Location location = null;
		
		if(event.getEntity().getWorld().getDifficulty() == Difficulty.PEACEFUL || SPAWNER_COOLDOWN.containsKey((location = event.getSpawner().getLocation()))) {
			event.setCancelled(true);
		} else {
			SPAWNER_COOLDOWN.put(location, 20);	
		}
				
	}
	
	@EventHandler
	public void onPlayerConsume(PlayerItemConsumeEvent event) {
		
		ItemStack stack = event.getItem();
		
		if(stack.getType() == Material.GOLDEN_APPLE) {
			
			PlayerHandler.addPotionEffect(event.getPlayer(), new PotionEffect(PotionEffectType.REGENERATION, 320, 1, false, false));
			PlayerHandler.addPotionEffect(event.getPlayer(), new PotionEffect(PotionEffectType.ABSORPTION, 2400, 1, false, false));
		
		} else if(stack.getType() == Material.POTION) {
			
			Player player = event.getPlayer();
			
			Potion potion = new Potion(stack);
			potion.apply(player);
			
			PlayerHandler.subtractItemFromMainHand(player);
			event.setCancelled(true);
			
		}
		
	}
	
	@EventHandler
	public void onBlockPistonExtend(BlockPistonExtendEvent event) {
		
		Block block = event.getBlock();
		
		if(block.getType() == Material.PISTON_BASE && event.getDirection() == BlockFace.UP) {
			
			Player player = null;
			Location location = LocationHandler.getOffsetBlockLocation(block.getLocation(), 0.0D, 1.0D, 0.0D);
			
			for(Entity entity : block.getWorld().getNearbyEntities(location, 0.5D, 1.5D, 0.5D)) {
				if(entity.getType() == EntityType.PLAYER) player = (Player) entity;
			}
			
			if(player != null) {
				
				Vector v = player.getVelocity().normalize();
				v.setY(1.4);
				player.setVelocity(v);
				event.setCancelled(true);
				
				PISTON_SOUND.play(location);
				PISTON_EFFECT.play(location);
				
			}
			
		}
		
	}
	
	
}
