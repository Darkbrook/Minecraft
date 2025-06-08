package com.darkbrook.island.library.config;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import com.darkbrook.island.References;
import com.darkbrook.island.library.misc.FileHandler;

public class WorldLoader {

	private static ArrayList<World> worldCache = new ArrayList<World>();
	private static HashMap<String, World> worldMapping = new HashMap<String, World>();

	public static void createWorldsFolder() {
		if(!References.getServerFolderFile("worlds").exists()) FileHandler.createExternalDirectorys(References.getServerFolderFile("worlds").getAbsolutePath());
	}
	
	private static World addMapping(String name) {
		World world = (World) Bukkit.createWorld(new WorldCreator(name));
		worldCache.add(world);
		worldMapping.put(name, world);
		return world;
	}
	
	public static boolean hasMapping(String name) {
		return worldMapping.containsKey(name);
	}
	
	public static World loadWorld(String name) {
		return hasMapping(name) ? worldMapping.get(name) : addMapping(name);
	}
	
	public static boolean unloadWorld(String name) {
		
		if(hasMapping(name)) {
			Bukkit.unloadWorld(name, true);
			return true;
		}

		return false;
		
	}
	
	public static String getWorldName(String name) {
		if(name.contains("\\")) name = name.substring(name.lastIndexOf("\\", name.length()));
		name = name.replace("\\", "");
		return name.substring(0, 1).toUpperCase() + name.substring(1, name.length()).toLowerCase();
	}

}
