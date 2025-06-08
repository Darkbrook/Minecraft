package com.darkbrook.minez.command.entity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import com.darkbrook.library.block.CommandBlock;
import com.darkbrook.library.misc.UpdateHandler;
import com.darkbrook.library.misc.UpdateHandler.UpdateListener;

public class EntityTracker {
	
	private CommandBlock block;
	private Location spawnLocation;
	private Entity entity;
	private EntityType type;
	private EntityTrackerStatus status;
	private int taskId;
	private boolean isLoaded;
	
	public EntityTracker(CommandBlock block, String arguments) {
				
		try {
			
			this.block = block;
			
			String[] argumentsSplit = arguments.split(",");
			
			this.type = EntityType.valueOf(argumentsSplit[0].toUpperCase());
			this.spawnLocation = new Location(block.getLocation().getWorld(), Integer.parseInt(argumentsSplit[1]), Integer.parseInt(argumentsSplit[2]), Integer.parseInt(argumentsSplit[3]));
			this.status = EntityTrackerStatus.valueOf(argumentsSplit[4].toUpperCase());
					
			this.isLoaded = true;
			
		} catch (Exception e) {
			this.isLoaded = false;
		}
		
	}
	
	public boolean spawnEntity() {
		
		if(!isLoaded) return false;
		
		entity = spawnLocation.getWorld().spawnEntity(spawnLocation, type);
		
		switch(status) {
			
			case ALIVE: 
				
				UpdateHandler.delay(new UpdateListener() {

					@Override
					public void onUpdate() {
						block.setSignalStrength(1);
					}
					
				}, 1);
								
				taskId = UpdateHandler.repeat(new UpdateListener() {

					@Override
					public void onUpdate() {
						
						if(entity.isDead()) {
							block.setSignalStrength(0);
							UpdateHandler.cancle(taskId);
						}
						
					}
					
				}, 2, 20);
				
				break;
			
			case DEAD:
				
				UpdateHandler.delay(new UpdateListener() {

					@Override
					public void onUpdate() {
						block.setSignalStrength(0);
					}
					
				}, 1);
				
				taskId = UpdateHandler.repeat(new UpdateListener() {

					@Override
					public void onUpdate() {
						
						if(entity.isDead()) {
							block.setSignalStrength(1);
							UpdateHandler.cancle(taskId);
						}
						
					}
					
				}, 2, 20);
				
				break;
		
		}
		
		return true;
		
	}
	
}
