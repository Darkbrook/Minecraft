package com.darkbrook.minez.command.project;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.darkbrook.library.blueprint.Blueprint;

public class ProjectionResetBlock {

	private Block block;
	private Location location;
	private Material material;
	private int data;
	
	public ProjectionResetBlock(Location location, Material material, int data) {
		this.block = location.getBlock();
		this.location = location;
		this.material = material;
		this.data = data;
	}

	@SuppressWarnings("deprecation")
	public void project(ProjectionType projectionType) {
		
		block.setType(material);
		block.setData((byte) data);
		
		if(projectionType == ProjectionType.NOTHING || projectionType == ProjectionType.HARD) Blueprint.BLUEPRINT_SOUND.play(location);
		Blueprint.BLUEPRINT_PARTICLE.play(location);
		
	}
	
}
