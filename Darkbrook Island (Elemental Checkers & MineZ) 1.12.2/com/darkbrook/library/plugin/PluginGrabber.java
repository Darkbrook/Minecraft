package com.darkbrook.library.plugin;

import org.bukkit.plugin.Plugin;

public class PluginGrabber {
	
	private static Plugin plugin;
	
	public static void grab(Plugin grabbedPlugin) {
		plugin = grabbedPlugin;
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}

}
