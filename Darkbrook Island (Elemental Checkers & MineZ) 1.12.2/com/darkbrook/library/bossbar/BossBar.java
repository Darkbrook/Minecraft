package com.darkbrook.library.bossbar;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.darkbrook.library.plugin.PluginGrabber;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_12_R1.BossBattle.BarColor;
import net.minecraft.server.v1_12_R1.BossBattle.BarStyle;
import net.minecraft.server.v1_12_R1.BossBattleServer;
import net.minecraft.server.v1_12_R1.ChatMessage;
import net.minecraft.server.v1_12_R1.EntityPlayer;

public class BossBar {

	private static HashMap<String, BossBattleServer> owners = new HashMap<String, BossBattleServer>();
	private static HashMap<String, Integer> timers = new HashMap<String, Integer>();
	private static HashMap<String, Integer> updaters = new HashMap<String, Integer>();
	private static final int maxBarAmount = 5;
	
	private static EntityPlayer getEntityPlayer(Player player) {
		return ((CraftPlayer)player).getHandle();
	}
	
	private static void setOwner(int line, Player player, BossBattleServer bar) {
		bar.addPlayer(getEntityPlayer(player));
		owners.put(player.getUniqueId() + ":" + line, bar);
	}
	
	private static String getOwner(int line, Player player) {
		return player.getUniqueId() + ":" + line;
	}
	
	private static float percentToFloat(int percent) {
		double d = percent;
		return (float) (d / 100);
	}
	
	private static int floatToPercent(float f) {
		return (int) (f * 100);
	}
	
	public static int decimalToPercent(double current, double max) {
		return (int) ((current / max) * 100);
	}
	
	public static float decimalToFloat(double current, double max) {
		return (float) (current / max);
	}
	
	public static void onServerReload() {
		
		for(Player player : Bukkit.getServer().getOnlinePlayers()) {
			unsetAllBossBars(player);
		}
		
	}
	
	public static void setTrackerBossBar(int line, Player player, String name, BarColor color, BarStyle style, int progress) {
		
		if(!BossBar.hasBossBar(line, player)) {
			BossBar.setBossBarWithProgress(line, player, name, color, style, progress);
		} else {
			BossBar.setProgress(line, player, progress);
		}
		
	}
	
	public static void setDynamicTrackerBossBar(int line, Player player, String name, BarColor color, BarStyle style, int progress) {
				
		if(!BossBar.hasBossBar(line, player)) {
			BossBar.setBossBarWithProgress(line, player, name, color, style, progress);
		} else {
		
			if(!getBossBar(line, player).title.getText().equals(name) || getBossBar(line, player).color != color || getBossBar(line, player).style != style) {
				BossBar.setBossBarWithProgress(line, player, name, color, style, progress);
			}
			
			BossBar.setProgress(line, player, progress);
			
		}
		
	}
	
	public static void setTimedBossBar(int line, Player player, String timer, String name, BarColor color, BarStyle style, int time) {
			
	 int updaterID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(PluginGrabber.getPlugin(), new Runnable() {

			@Override
			public void run() {
				
				if(!timers.containsKey(timer)) {
					timers.put(timer, 0);
				} 
				
				if(timers.get(timer) < time) {
					timers.put(timer, (timers.get(timer) + 1));
					setDynamicTrackerBossBar(line, player, name, color, style, (100 - decimalToPercent(timers.get(timer), time)));
				} else {
					timers.remove(timer);
					unsetBossBar(line, player);
					Bukkit.getServer().getScheduler().cancelTask(updaters.get(timer));
				}
				
			}
				
		}, 0, 0);

	 	updaters.put(timer, updaterID);
	 
	}
	
	public static void setBossBar(int line, Player player, String name, BarColor color, BarStyle style) {
		
		for(int i = 0; i < maxBarAmount; i++) {
										
			if(i == line) {
				reloadBossBar(i, player);
				BossBattleServer bar = new BossBattleServer(new ChatMessage(name), color, style);
				setOwner(line, player, bar);
			} else {
					
				if(hasBossBar(i, player)) {
					reloadBossBar(i, player);
					setOwner(i, player, getBossBar(i, player));
				}
					
			}
			
		}
		
	}
	
	public static void setBossBarWithProgress(int line, Player player, String name, BarColor color, BarStyle style, int progress) {
		
		for(int i = 0; i < maxBarAmount; i++) {
											
			if(i == line) {
				reloadBossBar(i, player);
				BossBattleServer bar = new BossBattleServer(new ChatMessage(name), color, style);
				bar.setProgress(percentToFloat(progress));
				setOwner(line, player, bar);
			} else {
					
				if(hasBossBar(i, player)) {
					reloadBossBar(i, player);
					setOwner(i, player, getBossBar(i, player));
				}
					
			}
			
		}
		
	}
	
	public static BossBattleServer getBossBar(int line, Player player) {
		return owners.get(getOwner(line, player));
	}
	
	
	public static void unsetBossBar(int line, Player player) {
		getBossBar(line, player).removePlayer(getEntityPlayer(player));
		owners.remove(getOwner(line, player));
	}
	
	public static void unsetAllBossBars(Player player) {
		
		for(int i = 0; i < maxBarAmount; i++) {
			
			if(hasBossBar(i, player)) {
				unsetBossBar(i, player);
			}
			
		}
		
	}
	
	public static void reloadBossBar(int line, Player player) {
		
		if(hasBossBar(line, player)) {
			getBossBar(line, player).removePlayer(getEntityPlayer(player));
		}
		
	}
	
	public static boolean hasBossBar(int line, Player player) {
		return owners.containsKey(player.getUniqueId() + ":" + line);
	}
	
	public static void setProgress(int line, Player player, int progress) {
		getBossBar(line, player).setProgress(percentToFloat(progress));
	}
	
	public static int getProgress(int line, Player player) {
		return floatToPercent(getBossBar(line, player).getProgress());
	}

	public static String getBarName(int line, Player player) {
		return ChatColor.stripColor(getBossBar(line, player).title.getText());
	}
	
}
