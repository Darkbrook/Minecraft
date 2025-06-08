package com.darkbrook.library.blueprint.task.base;

import org.bukkit.Location;
import org.bukkit.Material;

import com.darkbrook.library.blueprint.Blueprint;
import com.darkbrook.library.location.LocationHandler;

public abstract class BlueprintTaskMoving extends BlueprintTask {

	private int xOffset;
	private int yOffset;
	private int zOffset;
	private boolean isFirstLoaded;
	
	public BlueprintTaskMoving(Blueprint blueprint, Location runLocation, int xOffset, int yOffset, int zOffset, int delay) {
		super(blueprint, runLocation, delay);
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
		this.isFirstLoaded = false;
	}
	
	protected boolean isPositive(String direction) {
		return !direction.contains("-");
	}
	
	protected String getDirectionCoordinate(String direction) {
		return isPositive(direction) ? direction.replace("+", "") : direction.replace("-", "");
	}

	protected void modifyDirection(String direction, boolean isPositive) {
		modifyDirection(direction, isPositive ? 1 : -1);
	}
	
	protected void modifyDirection(String direction, int addition) {
		
		switch(direction) {
			case "X": xOffset += addition; break;
			case "Y": yOffset += addition; break;
			case "Z": zOffset += addition; break;
		}

	}
	
	protected void onTickMove() {
		
		Blueprint blueprint = getBlueprint();
		Location lastOffset = getCurrentLocation();

		if(isFirstLoaded) {
			blueprint.paste(lastOffset, Material.AIR, 0, true);
			blueprint.paste(getMoveLocation(), true);
		} else {
			blueprint.paste(lastOffset, true);
			isFirstLoaded = true;
		}
		
	}
	
	protected Location getCurrentLocation() {
		return LocationHandler.getOffsetLocation(getRunLocation(), xOffset, yOffset, zOffset);
	}
	
	protected abstract Location getMoveLocation();
	
}
