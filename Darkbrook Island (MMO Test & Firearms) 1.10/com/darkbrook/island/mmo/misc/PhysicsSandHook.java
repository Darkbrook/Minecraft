package com.darkbrook.island.mmo.misc;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.plugin.Plugin;

import com.darkbrook.island.library.misc.LocationHandler;
import com.darkbrook.island.library.misc.MathHandler;
import com.darkbrook.island.library.misc.UpdateHandler;
import com.darkbrook.island.library.misc.UpdateHandler.UpdateListener;

public class PhysicsSandHook implements Listener {

	public static void load(Plugin plugin) {
		plugin.getServer().getPluginManager().registerEvents(new PhysicsSandHook(), plugin);
	}
	
	private Location getDirection(Location locationIn) {
		
		Location location = null;
		float f = 0.0F;
				
		if(LocationHandler.getLocationOffset(locationIn, 1, 0, 0).getBlock().getType() == Material.AIR){
			float rand = MathHandler.RANDOM.nextFloat();
			if(rand > f) {
				f = rand;
				location = LocationHandler.getLocationOffset(locationIn, 1, 0, 0);
			}
		}
	
		if(LocationHandler.getLocationOffset(locationIn, -1, 0, 0).getBlock().getType() == Material.AIR) {
			float rand = MathHandler.RANDOM.nextFloat();
			if(rand > f) {
				f = rand;
				location = LocationHandler.getLocationOffset(locationIn, -1, 0, 0);
			}
		}
	
		if(LocationHandler.getLocationOffset(locationIn, 0, 0, 1).getBlock().getType() == Material.AIR) {
			float rand = MathHandler.RANDOM.nextFloat();
			if(rand > f) {
				f = rand;
				location = LocationHandler.getLocationOffset(locationIn, 0, 0, 1);
			}
		}
	
		if(LocationHandler.getLocationOffset(locationIn, 0, 0, -1).getBlock().getType() == Material.AIR) {
			float rand = MathHandler.RANDOM.nextFloat();
			if(rand > f) {
				f = rand;
				location = LocationHandler.getLocationOffset(locationIn, 0, 0, -1);
			}
		}
		
		return location;
		
	}
	
	private Location getFinalSandLocation(Location place, Location locationIn, int moves) {
		Location location = getDirection(locationIn);
		if(location == null || location.distance(place) > 5 || moves > 100) return locationIn;
		if(LocationHandler.getLocationOffset(location, 0, -1, 0).getBlock().getType() == Material.AIR) return location;
		return getFinalSandLocation(place, location, (moves + 1));
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockChange(EntityChangeBlockEvent event) {

		if(LocationHandler.getLocationOffset(event.getBlock().getLocation(), 0, -1, 0).getBlock().getType() != Material.AIR) {
			
			if(event.getEntityType() == EntityType.FALLING_BLOCK && ((FallingBlock) event.getEntity()).getBlockId() == Material.SAND.getId()) {
				
				UpdateHandler.delay(new UpdateListener() {

					@Override
					public void onUpdate() {
						event.getBlock().setType(Material.AIR);		
						getFinalSandLocation(event.getBlock().getLocation(), event.getBlock().getLocation(), 0).getBlock().setType(Material.SAND);
					}

				}, 1);
				
			}
			
		}
	
	}
	
}
