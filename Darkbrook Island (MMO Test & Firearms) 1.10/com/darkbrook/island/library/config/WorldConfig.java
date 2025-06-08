package com.darkbrook.island.library.config;

import java.io.File;
import java.util.HashMap;

import org.bukkit.World;

public class WorldConfig {

	private static HashMap<World, HashMap<String, Config>> configs = new HashMap<World, HashMap<String, Config>>();
	
	public static Config getConfig(World world, String name) {
		File worldfile = world.getWorldFolder();
		File worlddata = new File(worldfile.getAbsolutePath() + "\\worlddata");
		if(!worldfile.exists()) return null;
		if(!worlddata.exists()) worlddata.mkdir();
		if(!configs.containsKey(world)) configs.put(world, new HashMap<String, Config>());
		if(!configs.get(world).containsKey(name)) configs.get(world).put(name, new Config(name, worlddata));
		return configs.get(world).get(name);
	}
	
}
