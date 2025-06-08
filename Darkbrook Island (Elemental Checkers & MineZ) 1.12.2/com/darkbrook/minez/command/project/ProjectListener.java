package com.darkbrook.minez.command.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

import com.darkbrook.library.misc.UpdateHandler;
import com.darkbrook.library.misc.UpdateHandler.UpdateListener;

public class ProjectListener implements Listener {

	public static final Map<Location, Projection> PROJECTION_MAPPING = new HashMap<Location, Projection>();
	
	public static void resetProjections() {
		List<Projection> projections = new ArrayList<Projection>(PROJECTION_MAPPING.values());
		for(Projection projection : projections) projection.projectReset();		
		PROJECTION_MAPPING.clear();
	}
	
	@EventHandler
	public void onRedstoneEvent(BlockRedstoneEvent event) {
		
		Location location = event.getBlock().getLocation();
		
		if(PROJECTION_MAPPING.containsKey(location) && event.getNewCurrent() == 0) {
			
			Projection projection = PROJECTION_MAPPING.get(location);

			
			UpdateHandler.delay(new UpdateListener() {

				@Override
				public void onUpdate() {
					projection.projectReset();					
				}
				
			}, 1);
			
			
			PROJECTION_MAPPING.remove(location);
			
		}
		
	}
	
}
