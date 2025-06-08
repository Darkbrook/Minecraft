package com.darkbrook.library.command.basic.world;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import com.darkbrook.darkbrookisland.DarkbrookIsland;

public class WorldLoader {

	private static World getUnprocessedWorld(String name) {
		return Bukkit.getWorld(name) == null ? Bukkit.createWorld(new WorldCreator(name)) : Bukkit.getWorld(name);
	}
	
	public static String getDisplayWorldName(String name) {
		return name.replace("worlds", "").replace("/", "");
	}
	
	public static World getWorld(String name) {
		name = getProcessedWorldName(name);
		return getUnprocessedWorld(name);
	}
	
	public static List<String> getWorldFiles() {
		List<String> worldList = new ArrayList<String>();
		File files = new File(DarkbrookIsland.getServerPath() + "\\worlds");
		for(File file : files.listFiles()) worldList.add(file.getName());
		return worldList;
	}
	
	private static String getProcessedWorldName(String name) {
		return "worlds/" + getDisplayWorldName(name);
	}
	
}
