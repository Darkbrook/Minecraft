package com.darkbrook.library.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.World;

public class WorldConfig {

	private static Map<World, Map<String, MinecraftConfig>> configs = new HashMap<World, Map<String, MinecraftConfig>>();
	
	public static MinecraftConfig getConfig(World world, String name) {
		
		File worldfile = world.getWorldFolder();
		File worlddata = new File(worldfile.getAbsolutePath() + "\\worlddata");
		
		if(!worldfile.exists()) return null;
		if(!worlddata.exists()) worlddata.mkdir();
		
		if(!configs.containsKey(world)) configs.put(world, new HashMap<String, MinecraftConfig>());
		if(!configs.get(world).containsKey(name)) configs.get(world).put(name, new MinecraftConfig(name, worlddata));
		
		return configs.get(world).get(name);
		
	}
	
}
