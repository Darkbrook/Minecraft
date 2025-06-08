package com.darkbrook.island.mmo.experience;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.darkbrook.island.References;
import com.darkbrook.island.library.misc.MathHandler;
import com.darkbrook.island.mmo.GameRegistry;

public class Experience {
	
	public static void load(Plugin plugin) {
		
		ExperienceHook.load(plugin);
		References.mmodata.addDefault("zombie.name", "%NAME%");
		References.mmodata.addDefault("zombie.min", 2);
		References.mmodata.addDefault("zombie.max", 4);

		for(Player player : Bukkit.getServer().getOnlinePlayers()) if(GameRegistry.isEnabled()) ExperienceBar.loadExperienceData(player);
		
	}
	
	public static void add(Player player, int amount) {
		if(amount > 0) {
			ExperienceBar.addExperienceBarProgress(player, amount);
			player.sendMessage(References.getSpacedFormat(ChatColor.DARK_AQUA, ChatColor.AQUA, "+" + amount + " XP"));	
		}
	}
	
	public static void update(Player player) {
		ExperienceBar.update(player);
	}
	
	public static void unload() {
		for(Player player : Bukkit.getServer().getOnlinePlayers()) ExperienceBar.removeExperienceBar(player);
	}
	
	public static int getXPValue(String key) {
		return MathHandler.RANDOM.nextInt(References.mmodata.getInt(key + ".max") - References.mmodata.getInt(key + ".min") + 1) + References.mmodata.getInt(key + ".min");
	}
	
}

