package com.darkbrook.island.buildthatwall;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import com.darkbrook.island.References;
import com.darkbrook.island.library.misc.LocationHandler;
import com.darkbrook.island.library.template.Selection;

public class WallHook implements Listener {
	
	private final HashMap<Player, Boolean> CLICK = new HashMap<Player, Boolean>();

	public static void load(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(new WallHook(), plugin);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
				
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.IRON_BLOCK) {
			
			Player player = event.getPlayer();
			CLICK.put(player, !CLICK.containsKey(player) ? true : !CLICK.get(player));
			if(CLICK.get(player)) return;
			
			player.getWorld().playSound(event.getClickedBlock().getLocation(), Sound.BLOCK_ANVIL_USE, 2.0F, 0.0F);
			
			if(!event.getClickedBlock().hasMetadata("WALL")) {
				Selection.loadBlueprint("Wall4", LocationHandler.getLocationOffset(event.getClickedBlock().getLocation(), 0, 1, 0));
				event.getClickedBlock().setMetadata("WALL", new FixedMetadataValue(References.plugin, 0));
				Selection.loadBlueprint("Wall0", LocationHandler.getLocationOffset(event.getClickedBlock().getLocation(), 0, 1, 0));
			} else {
				int wall = (int) event.getClickedBlock().getMetadata("WALL").get(0).value();
				wall = wall < 4 ? wall + 1 : 0;
				event.getClickedBlock().setMetadata("WALL", new FixedMetadataValue(References.plugin, wall));
				Selection.loadBlueprint("Wall" + wall, LocationHandler.getLocationOffset(event.getClickedBlock().getLocation(), 0, 1, 0));
			}
			
		}
		
	}

}
