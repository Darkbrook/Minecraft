package com.darkbrook.minez.command.project;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;

public class Projection {	

	private List<ProjectionResetBlock> projectionResetBlocks;
	private List<ProjectionBlock> projectionBlocks;
	private ProjectionType projectionType;
	private boolean isLoadedProperly;

	public Projection(String projectionString, String typeString) {
		
		try {
			this.projectionResetBlocks = new ArrayList<ProjectionResetBlock>();
			this.projectionBlocks = loadProjectionString(projectionString);
			this.projectionType = loadTypeString(typeString);
			this.isLoadedProperly = true;
		} catch (Exception e) {
			this.isLoadedProperly = false;
		}
		
	}

	public boolean project(Location location) {
				
		if(!isLoadedProperly) {
			return false;
		}
		
		try {
			for(ProjectionBlock projectionBlock : projectionBlocks) projectionBlock.project(this, location, projectionType);	
			if(projectionType == ProjectionType.NOTHING || projectionType == ProjectionType.SILENT) ProjectListener.PROJECTION_MAPPING.put(location, this);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	public void projectReset() {
		for(ProjectionResetBlock projectionResetBlock : projectionResetBlocks) projectionResetBlock.project(projectionType);		
	}
	
	public void addProjectionResetBlock(ProjectionResetBlock projectionResetBlock) {
		projectionResetBlocks.add(projectionResetBlock);
	}
	
	private List<ProjectionBlock> loadProjectionString(String projectionString) throws Exception {
		
		if(projectionString.endsWith(";")) {
			projectionString = projectionString.substring(0, projectionString.length() - 1);
		}
				
		List<ProjectionBlock> projectionBlocks = new ArrayList<ProjectionBlock>();
		
		if(projectionString.contains(";")) {
			for(String projectionStrings : projectionString.split(";")) projectionBlocks.add(loadProjectionBlock(projectionStrings));			
		} else {
			projectionBlocks.add(loadProjectionBlock(projectionString));
		}
		
		return projectionBlocks;
		
	}
	
	private ProjectionType loadTypeString(String typeString) throws Exception {
		return typeString.equals("nothing") ? ProjectionType.NOTHING : (typeString.equals("hard") ? ProjectionType.HARD : (typeString.equals("silent") ? ProjectionType.SILENT : (typeString.equals("silent,hard") || typeString.equals("hard,silent") ? ProjectionType.SILENT_HARD : ProjectionType.NOTHING)));
	}
	
	private ProjectionBlock loadProjectionBlock(String projectionString) throws Exception {
		
		String[] projectionStrings = projectionString.split(",");
		
		List<Integer> xSet = getCoordinates(projectionStrings[0]);
		List<Integer> ySet = getCoordinates(projectionStrings[1]);
		List<Integer> zSet = getCoordinates(projectionStrings[2]);
		
		List<Location> locations = new ArrayList<Location>();
		for(int x : xSet) for(int y : ySet) for(int z : zSet) locations.add(new Location(null, x, y, z));
		
		Material material = Material.valueOf(projectionStrings[3].toUpperCase());
		return projectionStrings.length == 4 ? new ProjectionBlock(locations, material, 0, 0) : (projectionStrings.length == 5 ? new ProjectionBlock(locations, material, Integer.parseInt(projectionStrings[4]), 0) : (projectionStrings.length == 6 ? new ProjectionBlock(locations, material, Integer.parseInt(projectionStrings[4]), Integer.parseInt(projectionStrings[5])): null));
		
	}
	
	private List<Integer> getCoordinates(String coordinateString) throws Exception {
		
		Point set = getCoordinateSet(coordinateString);
		
		int setMax = max((int) set.getX(), (int) set.getY());
		int setMin = min((int) set.getX(), (int) set.getY());
		
		List<Integer> coordinates = new ArrayList<Integer>();
		for(int i = setMin; i <= setMax; i++) coordinates.add(i);
		return coordinates;
		
	}
	
	private Point getCoordinateSet(String coordinateString) throws Exception {
		
		if(coordinateString.contains("to")) {
			String[] coordinateStrings = coordinateString.split("to");
			return new Point(Integer.parseInt(coordinateStrings[0]), Integer.parseInt(coordinateStrings[1]));
		} else {
			int coordinate = Integer.parseInt(coordinateString);
			return new Point(coordinate, coordinate);
		}
		
	}
	
	private int max(int a, int b) {
		return a == b ? a : (a > b ? a : b);
	}
	
	private int min(int a, int b) {
		return a == b ? a : (a < b ? a : b);
	}
	
}
