package com.darkbrook.island.mmo.experience;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.darkbrook.island.References;
import com.darkbrook.island.library.misc.MathHandler;

import net.minecraft.server.v1_10_R1.BossBattle.BarColor;
import net.minecraft.server.v1_10_R1.BossBattle.BarStyle;
import net.minecraft.server.v1_10_R1.BossBattleServer;
import net.minecraft.server.v1_10_R1.ChatMessage;

public class ExperienceBar {
		
	private static final HashMap<Player, BossBattleServer> BARCACHE = new HashMap<Player, BossBattleServer>();

	public static void loadExperienceData(Player player) {
		References.playerdata.addDefault(player.getUniqueId() + ".username", player.getName());
		References.playerdata.set(player.getUniqueId() + ".ip", player.getAddress().getHostName());
		References.playerdata.addDefault(player.getUniqueId() + ".level", 1);
		References.playerdata.addDefault(player.getUniqueId() + ".progress", 0);
		if(!References.playerdata.getString(player.getUniqueId() + ".username").equals(player.getName())) References.playerdata.set(player.getUniqueId() + ".username", player.getName());
		setNewExperienceBar(player);
		setExperienceBarProgress(player, References.playerdata.getInt(player.getUniqueId() + ".progress"));
	}
	
	public static String getExperienceBarName(Player player) {
		return ChatColor.DARK_AQUA + "Lvl: " + ChatColor.AQUA + getLevel(player) + ChatColor.DARK_AQUA + " - " + "Hp: " + ChatColor.AQUA + (int) MathHandler.getRawPercent((int) player.getHealth(), 20) + "/100" + ChatColor.DARK_AQUA + " - Xp: " + ChatColor.AQUA + getProgressPercent(player) + "/100";
	}
	
	public static int getLevel(Player player) {
		return References.playerdata.contains(player.getUniqueId() + ".level") ?  References.playerdata.getInt(player.getUniqueId() + ".level") : 1;
	}
	
	public static int getLevelPercent(Player player) {
		return getLevel(player) * 100;
	}
	
	public static float getProgressFloat(Player player) {
		return BARCACHE.containsKey(player) ? BARCACHE.get(player).getProgress() : 0.0F;
	}
	
	public static int getProgress(Player player) {
		return References.playerdata.contains(player.getUniqueId() + ".progress") ? References.playerdata.getInt(player.getUniqueId() + ".progress") : 0;
	}
	
	public static int getProgressPercent(Player player) {
		return MathHandler.getPercent(getProgress(player), getLevelPercent(player));
	}
	
	public static void levelUp(Player player) {
		setExperienceBarProgress(player, 0);
		References.playerdata.set(player.getUniqueId() + ".level", getLevel(player) + 1);
		setExperienceBarName(player, getExperienceBarName(player));
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 2.0F);
	}
	
	private static void setNewExperienceBar(Player player) {
		BossBattleServer bar = new BossBattleServer(new ChatMessage(getExperienceBarName(player)), BarColor.BLUE, BarStyle.NOTCHED_10);
		bar.setProgress(0);
		bar.addPlayer(((CraftPlayer) player).getHandle());
		BARCACHE.put(player, bar);
	}
	
	public static void addExperienceBarProgress(Player player, int amount) {
						
		double levelPercent = getLevelPercent(player);
		double initprogress = Math.round(getProgressFloat(player) * levelPercent);
		int progress = (int) (initprogress + amount);
								
		References.playerdata.set(player.getUniqueId() + ".progress", progress);
		
		if(progress >= levelPercent) {
			
			levelUp(player);
			
			if(progress > levelPercent) {
				addExperienceBarProgress(player, progress - (int) levelPercent);
			}
			
		} else {
			setExperienceBarProgress(player, progress);
			setExperienceBarName(player, getExperienceBarName(player));
		}
		
	}
	
	public static void setExperienceBarProgress(Player player, int value) {
		BARCACHE.get(player).setProgress(MathHandler.getFloatPercent(value, getLevelPercent(player)));
	}
	
	public static void setExperienceBarName(Player player, String name) {
		BossBattleServer bar = new BossBattleServer(new ChatMessage(name), BarColor.BLUE, BarStyle.NOTCHED_10);
		bar.setProgress(BARCACHE.get(player).getProgress());
		removeExperienceBar(player);
		bar.addPlayer(((CraftPlayer) player).getHandle());
		BARCACHE.put(player, bar);
	}
	
	public static void update(Player player) {
		setExperienceBarName(player, getExperienceBarName(player));
	}
	
	public static void removeExperienceBar(Player player) {
		
		if(BARCACHE.containsKey(player)) {
			BARCACHE.get(player).removePlayer(((CraftPlayer) player).getHandle());
			BARCACHE.remove(player);
		}
		
	}

}
