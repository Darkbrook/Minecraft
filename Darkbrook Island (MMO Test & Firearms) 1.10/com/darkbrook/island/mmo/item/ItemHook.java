package com.darkbrook.island.mmo.item;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.darkbrook.island.library.misc.UpdateHandler;
import com.darkbrook.island.library.misc.UpdateHandler.UpdateListener;

public class ItemHook implements Listener {
	
	public static void load(Plugin plugin) {
		
		plugin.getServer().getPluginManager().registerEvents(new ItemHook(), plugin);
		
		UpdateHandler.repeat(new UpdateListener() {

			@Override
			public void onUpdate() {
				for(Player player : Bukkit.getServer().getOnlinePlayers()) {
					SkyWalk.update(player);	
					if(Tome.hasCooldown(player)) Tome.subtractCooldown(player);
				}
			}
			
		}, 20, 20);
		
	}

}
