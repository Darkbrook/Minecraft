package com.darkbrook.island;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import com.darkbrook.island.library.config.Config;
import com.darkbrook.island.library.config.WorldLoader;
import com.darkbrook.island.library.misc.FileHandler;

public class References {
	
	public static Plugin plugin;
	public static final ChatColor message = ChatColor.BLUE;
	public static final ChatColor error = ChatColor.RED;
	public static Config mmodata;
	public static Config playerdata;
	public static Config kitdata;
	public static Config lootdata;

	public static void load(Plugin pluginIn) {
		plugin = pluginIn;
		FileHandler.createExternalDirectorys(getDataFolder().getAbsolutePath());
		mmodata = new Config("mmodata");
		mmodata.addDefault("enabled", true);
		playerdata = new Config("playerdata");
		kitdata = new Config("kitdata");
		lootdata = new Config("lootdata");
		WorldLoader.createWorldsFolder();
	}

	public static File getDataFolder() {
		return plugin.getDataFolder();
	}
	
	public static File getServerFolder() {
		return new File(getDataFolder().getAbsolutePath().replace("\\plugins\\" + plugin.getName(), ""));
	}
	
	public static File getServerFolderFile(String name) {
		return new File(getServerFolder().getAbsolutePath() + "\\" + name);
	}
	
	public static String getInfoFormat(ChatColor color, String message) {
		return color + message;
	}
	
	public static String getFormat(ChatColor color, ChatColor messageColor, String message) {
		return color + "*" + messageColor + message + color + "*";
	}
	
	public static String getSpacedFormat(ChatColor color, ChatColor messageColor, String message) {
		return color + "          *" + messageColor + message + color + "*";
	}
	
}
