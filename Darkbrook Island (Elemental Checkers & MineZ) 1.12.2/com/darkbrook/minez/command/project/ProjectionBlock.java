package com.darkbrook.minez.command.project;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.darkbrook.library.blueprint.Blueprint;
import com.darkbrook.library.misc.UpdateHandler;
import com.darkbrook.library.misc.UpdateHandler.UpdateListener;

public class ProjectionBlock {

	private List<Location> locations;
	private Material material;
	private int data;
	private int delay;
	
	public ProjectionBlock(List<Location> locations, Material material, int data, int delay) {
		this.locations = locations;
		this.material = material;
		this.data = data;
		this.delay = delay;
	}
	
	public void project(Projection projection, Location offset, ProjectionType type) {
				
		if(delay == 0) {
			projectInternally(projection, offset, type);
		} else {
		
			UpdateHandler.delay(new UpdateListener() {

				@Override
				public void onUpdate() {
					projectInternally(projection, offset, type);				
				}
			
			}, delay);
		
		}
		
	}

	@SuppressWarnings("deprecation")
	private void projectInternally(Projection projection, Location offset, ProjectionType projectionType) {
		
		for(Location location : locations) {
			
			Location locationProject = new Location(offset.getWorld(), location.getX() + offset.getX(), location.getY() + offset.getY(), location.getZ() + offset.getZ());
			Block block = locationProject.getBlock();
			
			if(projectionType == ProjectionType.NOTHING || projectionType == ProjectionType.SILENT) projection.addProjectionResetBlock(new ProjectionResetBlock(locationProject, block.getType(), block.getData()));
			
			block.setType(material);
			block.setData((byte) data);
			
			if(projectionType == ProjectionType.NOTHING || projectionType == ProjectionType.HARD) Blueprint.BLUEPRINT_SOUND.play(locationProject);
			Blueprint.BLUEPRINT_PARTICLE.play(locationProject);
									
		}
		
	}
	
}
