package com.darkbrook.island.mmo.entity;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.darkbrook.island.library.misc.UpdateHandler;
import com.darkbrook.island.library.misc.UpdateHandler.UpdateListener;
import com.darkbrook.island.mmo.experience.Experience;

public class HeartBeat {
	
	private static final HashMap<Player, HeartBeat> HEARTBEATS = new HashMap<Player, HeartBeat>();

	private Player player;
	private int lastIntensity;
	private int intensity;
	
	public static void load() {
		for(Player player : Bukkit.getServer().getOnlinePlayers()) addHeartBeat(player);		
	}
	
	public static void addHeartBeat(Player player) {
		HEARTBEATS.put(player, new HeartBeat(player));
	}
	
	public static HeartBeat getHeartBeat(Player player) {
		return HEARTBEATS.containsKey(player) ? HEARTBEATS.get(player) : null;
	}
	
	public HeartBeat(Player player) {
		this.player = player;
		this.lastIntensity = 0;
	}
	
	public void playSound() {
		
		intensity = 20 - ((int) player.getHealth());
		player.setWalkSpeed((0.01F * intensity) + 0.2F);
		
		if(intensity < 20) {
		
			player.setExp((0.009F * intensity) + 0.001F);
		
			UpdateHandler.delay(new UpdateListener() {

				@Override
				public void onUpdate() {
					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASEDRUM, (0.09F * intensity) + 0.01F, 0.0F);
					player.setExp((0.03F * intensity) + 0.02F);
				}
			
			}, 8);
		
			UpdateHandler.delay(new UpdateListener() {

				@Override
				public void onUpdate() {
					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASEDRUM, (0.04F * intensity) + 0.01F, 0.0F);
					player.setExp((0.015F * intensity) + 0.01F);
				}
			
			}, 12);
		
		} else {
			player.setExp(0.0F);
		}
	
		if(lastIntensity != intensity) {
			lastIntensity = intensity;
			Experience.update(player);
		}
		
	}

}
